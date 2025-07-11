package core;

import model.Vocabulary;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VocabularyLoader {
    private static final String LANGUAGE_SPLIT = ":";
    private static final String MEANINGS_SPLIT = ",";
    private static final Pattern STANDARD_FORMAT = Pattern.compile("([a-zA-Z],)*[a-zA-Z]:([a-zäöüßA-ZÖÄÜ],)*[a-zäöüßA-ZÖÄÜ]");

    public List<Vocabulary> loadWithFormat(String path) throws IOException {
        BufferedReader br = ReaderUtility.getReader(path);
        List<Vocabulary> vocabularies =  new ArrayList<>();
        String line;
        while((line = br.readLine())!= null){
            if(STANDARD_FORMAT.matcher(line).find()){
                String[] languageSplit = line.split(LANGUAGE_SPLIT);
                vocabularies.add(new Vocabulary(
                        getMeanings(languageSplit[0]),
                        getMeanings(languageSplit[1])
                ));
            }
        }
        return vocabularies;
    }

    private List<String> getMeanings(String s){
        return new ArrayList<>(List.of(s.split(MEANINGS_SPLIT)));
    }
}
