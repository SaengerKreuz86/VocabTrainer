package core.selecting;

import core.loading.ThemeLoader;
import lombok.experimental.UtilityClass;
import model.Vocabulary;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static core.Main.*;
import static core.QuestionEvaluator.processQuestioning;
import static core.ReaderUtility.readLine;

@UtilityClass
public class ThemeSelector {

    private static final String SELECT_THEMES =
            """
                Select a theme. You can choose counter (type '$counter'), days (type '$days'),
                positions (type '$positions'), or directions (type '$directions')\r
            """;
    private static final String SELECT_DAYS_MODE =
            """
                You can choose between weekdays (type '$weekdays'), days of the month (type '$month'), or all (type '$all')\r
            """;
    private static final String SELECT_COUNTER_MODE =
            """
                Select all counters (type $all) or select specific ones (type 'name1, name2').\r
                When choosing the latter note that it is necessary to part each counter name by a comma and a space ( ', ').\r
                To see a list of all counter names type '$ls'.\r
            """;

    /**
     * Selects possible themes.
     */
    public static void doThemes(BufferedReader br) throws IOException {
        System.out.println(SELECT_THEMES);
        System.out.println(getWAITING_FOR_INPUT());
        String s = readLine(br);
        System.out.println();
        switch (s){
            case "$exit" -> {return;}
            case "$counter" -> {
                System.out.println(SELECT_COUNTER_MODE);
                System.out.println(getWAITING_FOR_INPUT());
                List<Vocabulary> vocabularies = getCounterMode(br,readLine(br));
                processQuestioning(br,vocabularies, "The categories are %s".formatted(ThemeLoader.getCOUNTER_NAMES()));
            }
            case "$days" -> {
                System.out.println(SELECT_DAYS_MODE);
                System.out.println(getWAITING_FOR_INPUT());
                List<Vocabulary> vocabularies = getDaysMode(readLine(br));
                processQuestioning(br,vocabularies);
            }
            case "$positions" -> {
                List<Vocabulary> vocabularies = ThemeLoader.getPositions();
                processQuestioning(br,vocabularies);
            }
            case "$directions" -> {
                List<Vocabulary> vocabularies = ThemeLoader.getDirections();
                processQuestioning(br,vocabularies);
            }
            default -> System.out.println("Mode is not supported.\r\n");
        }
        doThemes(br);
    }

    /**
     * If $days was selected then processes the following modes.
     * @param mode mode which will be selected by the user.
     *             $all for all vocabularies, $weekdays for week days only,
     *             and $month for names of the days of a month only.
     * @return List of vocabularies depending on the mode
     */
    private static List<Vocabulary> getDaysMode(String mode){
        System.out.println();
        return switch (mode){
            case "$exit" -> new ArrayList<>();
            case "$all", "" -> ThemeLoader.getDays();
            case "$weekdays" -> ThemeLoader.getWeek();
            case "$month" -> ThemeLoader.getMonth();
            default -> {
                System.out.printf("Unknown mode %s. Type a valid mode%n", mode);
                yield new ArrayList<>();
            }
        };
    }

    /**
     * Selects possible counter modes. Supported are $all yielding all possible counters, and specific by name yielding the correlating counter.
     * Typing $list will list all possible names of the counters.
     * @param mode mode of operation
     * @return List of the selected counter vocabularies
     */
    private static List<Vocabulary> getCounterMode(BufferedReader br,String mode) throws IOException {
        System.out.println();
        return switch (mode){
            case "$exit" -> new ArrayList<>();
            case "$all","" -> ThemeLoader.getCounters();
            case "$ls"-> {
                System.out.println(ThemeLoader.getCOUNTER_NAMES());
                System.out.println("Select a mode as mentioned above!\r");
                System.out.println(getWAITING_FOR_INPUT());
                yield getCounterMode(br,readLine(br));
            }
            default -> {
                String[] counter = mode.split(" ");
                List<Vocabulary> vocabularies = new ArrayList<>();
                for (String name: counter){
                    vocabularies.addAll(ThemeLoader.getCounterByName(name));
                }
                vocabularies = ThemeLoader.squashSameJapaneseMeanings(vocabularies);
                yield vocabularies;
            }
        };
    }
}
