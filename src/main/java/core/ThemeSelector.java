package core;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import model.Vocabulary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@UtilityClass
public class ThemeSelector {
    @Getter
    private static final List<String> COUNTER_NAMES = new ArrayList<>(List.of(
            "book", "floor_of_building", "frequency",
            "general", "glasses_cups", "long_slender",
            "machine_vehicle", "order", "people",
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
        return vocabularies;
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
