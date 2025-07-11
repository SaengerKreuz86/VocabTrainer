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
    private static final Logger logger = Logger.getLogger("ThemeLoader");
    @Getter // categories for the counter
    private static final List<String> COUNTER_NAMES = new ArrayList<>(List.of(
            "book", "floors_of_buildings", "frequency",
            "general", "glasses_cups", "long_slender",
            "machines_vehicles", "order", "people",
            "small", "thin_flat"
    ));

    /**
     * Gets a list of all positional vocabularies
     * @return List of vocabularies
     */
    public static List<Vocabulary> getPositions() {
        try {
            String path = "vocabularies/themes/positions/positions.csv";
            return new VocabularyLoader(path).loadStandardFormat();
        } catch (IOException e) {
            logger.warning("positions were skipped");
        }
        return new ArrayList<>();
    }

    /**
     * Gets a list of all hemispherical directions
     * @return List of vocabularies
     */
    public static List<Vocabulary> getDirections() {
        try {
            String path = "vocabularies/themes/positions/directions.csv";
            return new VocabularyLoader(path).loadStandardFormat();
        } catch (IOException e) {
            logger.warning("directions were skipped");
        }
        return new ArrayList<>();
    }

    /**
     * Gets a list for the vocabularies of the days directory, week days and the days of a month.
     * @return List of vocabularies
     */
    public static List<Vocabulary> getDays(){
        List<Vocabulary> vocabularies = new ArrayList<>(getMonth());
        vocabularies.addAll(getWeek());
        return vocabularies;
    }

    /**
     * Collects the vocabularies for week days
     * @return List of vocabularies
     */
    public static List<Vocabulary> getWeek() {
        try {
            String path = "vocabularies/themes/days/week.csv";
            return new VocabularyLoader(path).loadStandardFormat();
        } catch (IOException e) {
            logger.warning("week was skipped");
        }
        return new ArrayList<>();
    }


    /**
     * Gets the vocabularies for the days of a month
     * @return List of vocabularies
     */
    public static List<Vocabulary> getMonth() {
        try {
            String path = "vocabularies/themes/days/month.csv";
            return new VocabularyLoader(path).loadNumberFormat(32, true, "");
        } catch (IOException e) {
            logger.warning("month was skipped");
        }
        return new ArrayList<>();
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
        vocabularies = squashSameJapaneseMeanings(vocabularies);
        return vocabularies;
    }

    /**
     * Gets counter vocabularies by name
     * @param name criteria
     * @return List of corresponding vocabularies
     */
    public static List<Vocabulary> getCounterByName(String name) throws IOException {
        try {
            String path = "vocabularies/themes/counter/%s.csv".formatted(name);
            return new VocabularyLoader(path).loadNumberFormat(11, true, name);
        } catch (IOException e) {
            logger.warning("counter '%s' was skipped".formatted(name));
        }
        return new ArrayList<>();
    }

    /**
     * Vocabularies that have the same exact japanese meanings will be combined to one vocabulary
     * @param vocabularies List of vocabularies to be filtered and reduced
     * @return new ArrayList with no ambiguity
     */
    static List<Vocabulary> squashSameJapaneseMeanings(List<Vocabulary> vocabularies) {
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
