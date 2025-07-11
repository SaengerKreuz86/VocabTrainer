package core.loading;

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
     * Gets a list of all vocabularies that are in relationship to titles of family members (e.g. brother, sister, father, ...)
     * @return List of vocabularies
     */
    public static List<Vocabulary> getFamilies(){
        List<Vocabulary> vocabularies = new ArrayList<>(
                getVocabulariesFromPath(
                        "vocabularies/themes/social_relations/ownFamily.csv",
                        "own families were skipped")
        );
        vocabularies.addAll(
                getVocabulariesFromPath(
                        "vocabularies/themes/social_relations/otherFamily.csv",
                        "other families were skipped")
        );
        return squashSameJapaneseMeanings(vocabularies);
    }

    /**
     * Gets a list of all positional vocabularies
     * @return List of vocabularies
     */
    public static List<Vocabulary> getPositions() {
        return getVocabulariesFromPath(
                "vocabularies/themes/positions/positions.csv",
                "positions were skipped"
        );
    }

    /**
     * Gets a list of all hemispherical directions
     * @return List of vocabularies
     */
    public static List<Vocabulary> getDirections() {
        return getVocabulariesFromPath(
                "vocabularies/themes/positions/directions.csv",
                "directions were skipped"
        );
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
        return getVocabulariesFromPath(
                "vocabularies/themes/days/week.csv",
                "week was skipped"
        );
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
        //some vocabularies are ambiguous.
        // It's more fair to be lenient with the input (frequency and floors_of_buildings are mostly the same)
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
    public static List<Vocabulary> squashSameJapaneseMeanings(List<Vocabulary> vocabularies) {
        List<Vocabulary> out = new ArrayList<>();
        // merge words that have the same exact japanese meaning so that their english/japanese meanings are combined
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
     * Collects the vocabularies that can be found under the specified path. To collect these the standard format is used.
     * @param path Path to where the vocabularies lie.
     * @param messageOnIOError If an io error occurs this message will be displayed
     * @return List of vocabularies
     */
    private List<Vocabulary> getVocabulariesFromPath(String path, String messageOnIOError){
        try {
            return new VocabularyLoader(path).loadStandardFormat();
        } catch (IOException e) {
            logger.warning(messageOnIOError);
        }
        return new ArrayList<>();
    }
}
