package core;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import model.Vocabulary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;

@UtilityClass
public class ThemeSelector {
    @Getter
    private static final List<String> COUNTER_NAMES = new ArrayList<>(List.of(
            "book", "floors_of_building", "frequency",
            "general", "glasses_cups", "long_slender",
            "machines_vehicles", "order", "people",
            "small", "thin_flat"
    ));

    /**
     *
     * @return List of all counter vocabularies
     */
    public static List<Vocabulary> getCounter(){
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (String counterName : COUNTER_NAMES){
            try {
                vocabularies.addAll(getCounterByName(counterName));
            } catch (IOException e) {
                Logger.getLogger("ThemeSelector").warning("Counter for %s was skipped".formatted(counterName));
            }
        }
        //some vocabularies are ambiguous. It's more fair to be lenient with the input
        vocabularies = reduceListByName(vocabularies);
        return vocabularies;
    }

    /**
     * Vocabularies that have the same exact japanese meanings will be combined to one vocabulary
     * @param vocabularies List of vocabularies to be filtered and reduced
     * @return new ArrayList with no ambiguity
     */
    public static List<Vocabulary> reduceListByName(List<Vocabulary> vocabularies) {
        List<Vocabulary> out = new ArrayList<>();
        // add merged doubles
        for (int i = 0; i < vocabularies.size(); i++) {
            Set<String> combined = new HashSet<>(vocabularies.get(i).getEnglishGerman());
            for (int j = i+1; j < vocabularies.size(); j++) {
                if (vocabularies.get(i).getJapanese().equals(vocabularies.get(j).getJapanese())){
                    combined.addAll(vocabularies.get(j).getEnglishGerman());
                    out.add(new Vocabulary(
                            new ArrayList<>(vocabularies.get(i).getJapanese()),
                            new ArrayList<>(combined)
                    ));
                }
            }
        }
        // adds all words that are not yet contained in out but are in vocabularies
        for (Vocabulary value : vocabularies) {
            boolean canAdd = true;
            for (Vocabulary vocabulary : out) {
                if (vocabulary.getJapanese().equals(value.getJapanese())) {
                    canAdd = false;
                    break;
                }
            }
            if (canAdd) {
                out.add(value);
            }
        }
        return out;
    }

    /**
     * Gets counter vocabularies by name specified
     * @param counterName Name of the counterName file
     * @return List of vocabularies that correlate to the counterName
     */
    public static List<Vocabulary> getCounter(String counterName){
        try {
            return new ArrayList<>(getCounterByName(counterName));
        } catch (IOException e) {
            Logger.getLogger("ThemeSelector").warning("Counter for %s was skipped".formatted(counterName));
        }
        return new ArrayList<>();
    }

    /**
     * Gets counter vocabularies by name
     * @param name criteria
     * @return List of corresponding vocabularies
     */
    private static List<Vocabulary> getCounterByName(String name) throws IOException {
        InputStream is = LessonSelector.class.getClassLoader().getResourceAsStream("themes/counter/%s.csv".formatted(name));
        BufferedReader br;
        List<Vocabulary> vocabularies = new ArrayList<>();
        if (is != null) {
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            // add ten counter words
            for (int i = 1; i < 11 && (line = br.readLine())!=null; i++) {
                vocabularies.add(new Vocabulary(
                        new ArrayList<>(Collections.singleton(line)),
                        new ArrayList<>(Collections.singleton(i + " " + name))));
            }
            // add question word
            line = br.readLine();
            vocabularies.add(new Vocabulary(
                    new ArrayList<>(Collections.singleton(line)),
                    new ArrayList<>(List.of("question %s".formatted(name), "frage %s".formatted(name)))));
        }
        return vocabularies;

    }
}
