package core.loading;

import core.util.ReaderWriterUtility;
import model.Vocabulary;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class VocabularyLoader {
    private static final String LANGUAGE_SPLIT = ":";
    private static final String MEANINGS_SPLIT = ",";
    private static final Pattern STANDARD_FORMAT
            = Pattern.compile("([a-zA-Z](-[a-zA-Z])*,)*[a-zA-Z](-[a-zA-Z])*:([a-zäöüßA-ZÖÄÜ],)*[a-zäöüßA-ZÖÄÜ]");
    private static final Pattern COUNTER_FORMAT
            = Pattern.compile("([a-zA-Z](-[a-zA-Z])*,)*[a-zA-Z](-[a-zA-Z])*");
    private final BufferedReader br;

    public VocabularyLoader(String path) throws IOException {
        br = ReaderWriterUtility.getReader(path);
    }

    /**
     * Loads vocabularies that have the defined standard format
     * @return List of vocabularies that have the format 'List of japanese meanings', 'List of translated meanings'
     */
    public List<Vocabulary> loadStandardFormat() throws IOException {
        List<Vocabulary> vocabularies =  new ArrayList<>();
        String line;
        while((line = br.readLine())!= null){
            Vocabulary vocabulary = getNext(line);
            if (vocabulary != null){
                vocabularies.add(vocabulary);
            }
        }
        return vocabularies;
    }

    /**
     * Loads a vocabulary that relates to a number. For example kokonotsu will be loaded with 9.
     * @param maxIterExclusive numbers will be treated as a range from 1, 2, ..., maxIterExclusive -1
     * @return vocabularies with the format 'List of Japanese meanings', number
     */
    public List<Vocabulary> loadCounterFormat(int maxIterExclusive) throws IOException {
        return loadCounterFormat(maxIterExclusive, false, "");
    }

    /**
     *
     * @param line String that has the COUNTER_FORMAT
     * @param i string/number the word will be associated with. eg. kokonotsu and "9"
     * @return Vocabulary
     */
    public Vocabulary getNextNumber(String line, String i) {
        if (COUNTER_FORMAT.matcher(line).find()){
            return new Vocabulary(
                    getMeanings(line),
                    new ArrayList<>(List.of(String.valueOf(i)))
            );
        }else {
            Logger.getLogger("VocabularyLoader").warning("String '%s' didn't match pattern '%s' so it was ignored".formatted(line, COUNTER_FORMAT.pattern()));
            return null;
        }
    }

    /**
     * @param s String of format s1,s2,s3,...
     * @return a List of these string split by MEANING_SPLIT
     */
    private static List<String> getMeanings(String s){
        return new ArrayList<>(List.of(s.split(MEANINGS_SPLIT)));
    }

    /**
     * @param line String of STANDARD_FORMAT
     * @return Vocabulary split according to the format
     */
    public static Vocabulary getNext(String line) {
        if(STANDARD_FORMAT.matcher(line).find()){
            String[] languageSplit = line.split(LANGUAGE_SPLIT);
            return new Vocabulary(
                    getMeanings(languageSplit[0]),
                    getMeanings(languageSplit[1])
            );
        }else {
            Logger.getLogger("VocabularyLoader").warning("String '%s' didn't match pattern '%s' so it was ignored".formatted(line, STANDARD_FORMAT.pattern()));
            return null;
        }
    }

    /**
     * Loads vocabularies with the specified number format.
     * @param maxIterExclusive numbers will be treated as a range from 1, 2, ..., maxIterExclusive -1
     * @param includeEndQuestion some csv have a question at the end. If true this question will be included. The question needs to have standard format
     * @param numberSuffix appends additional text after the number (already divided by a space).
     * @return List of vocabularies with the format List of japanese meanings, number + suffix and at last a question
     */
    public List<Vocabulary> loadCounterFormat(int maxIterExclusive, boolean includeEndQuestion, String numberSuffix) throws IOException {
        List<Vocabulary> vocabularies =  new ArrayList<>();
        String line;
        for (int i = 1; i < maxIterExclusive; i++) {
            line = ReaderWriterUtility.readLine(br);
            Vocabulary vocabulary = getNextNumber(line, i + " " + numberSuffix);
            if (vocabulary != null){
                vocabularies.add(vocabulary);
            }
        }
        line = ReaderWriterUtility.readLine(br);
        if (includeEndQuestion && !line.isEmpty()){
            vocabularies.add(getNext(line));
        }
        return vocabularies;
    }
}
