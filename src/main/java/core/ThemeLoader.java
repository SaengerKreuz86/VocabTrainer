package core;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import model.Vocabulary;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@UtilityClass
public class ThemeLoader {
    private static final String CLASS_NAME = "ThemeSelector";
    @Getter // categories for the counter
    private static final List<String> COUNTER_NAMES = new ArrayList<>(List.of(
            "book", "floors_of_buildings", "frequency",
            "general", "glasses_cups", "long_slender",
            "machines_vehicles", "order", "people",
            "small", "thin_flat"
    ));

    public static List<Vocabulary> getPositions() throws IOException {
        String path = "vocabularies/themes/positions/positions.csv";
        return new VocabularyLoader(path).loadStandardFormat();
    }

    public static List<Vocabulary> getDirections() throws IOException {
        String path = "vocabularies/themes/positions/directions.csv";
        return new VocabularyLoader(path).loadStandardFormat();
    }

    /**
     * Gets a list for the vocabularies of the days directory, week days and the days of a month.
     * @return List of vocabularies
     */
    public static List<Vocabulary> getDays(){
        List<Vocabulary> vocabularies = new ArrayList<>();
        try {
            vocabularies = new ArrayList<>(getMonth());
        } catch (IOException e) {
            Logger.getLogger(CLASS_NAME).warning("Months were skipped");
        }
        try {
            vocabularies.addAll(getWeek());
        } catch (IOException e) {
            Logger.getLogger(CLASS_NAME).warning("week days were skipped");
        }
        return vocabularies;
    }

    /**
     * Collects the vocabularies for week days
     * @return List of vocabularies
     */
    public static List<Vocabulary> getWeek() throws IOException {
        String path = "vocabularies/themes/days/week.csv";
        return new VocabularyLoader(path).loadStandardFormat();
    }


    /**
     * Gets the vocabularies for the days of a month
     * @return List of vocabularies
     */
    public static List<Vocabulary> getMonth() throws IOException {
        String path = "vocabularies/themes/days/month.csv";
        return new VocabularyLoader(path).loadNumberFormat(32, true, "");
    }

    /**
     *
     * @return List of all counter vocabularies
     */
    public static List<Vocabulary> getCounters(){
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (String counterName : COUNTER_NAMES){
            try {
                vocabularies.addAll(getCounterByName(counterName));
            } catch (IOException e) {
                Logger.getLogger(CLASS_NAME).warning("Counter for %s was skipped".formatted(counterName));
            }
        }
        //some vocabularies are ambiguous. It's more fair to be lenient with the input
        vocabularies = reduceListByName(vocabularies);
        return vocabularies;
    }

    /**
     * Gets counter vocabularies by name
     * @param name criteria
     * @return List of corresponding vocabularies
     */
    public static List<Vocabulary> getCounterByName(String name) throws IOException {
        String path = "vocabularies/themes/counter/%s.csv".formatted(name);
        return new VocabularyLoader(path).loadNumberFormat(11, true, name);
    }

    /**
     * Vocabularies that have the same exact japanese meanings will be combined to one vocabulary
     * @param vocabularies List of vocabularies to be filtered and reduced
     * @return new ArrayList with no ambiguity
     */
    static List<Vocabulary> reduceListByName(List<Vocabulary> vocabularies) {
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
}
