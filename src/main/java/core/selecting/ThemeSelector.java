package core.selecting;

import core.loading.MainLoader;
import core.loading.ThemeLoader;
import lombok.experimental.UtilityClass;
import model.Vocabulary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static core.util.QuestionEvaluator.processQuestioning;
import static core.util.ReaderWriterUtility.*;

@UtilityClass
public class ThemeSelector {

    private static final String SELECT_THEMES =
            """
                Select a theme. You can choose counter (type '$counter'), days (type '$days'),
                positions (type '$positions'), directions (type '$directions'), or families (type '$families')\r
            """;
    private static final String SELECT_DAYS_MODE =
            """
                You can choose between weekdays (type '$weekdays'), days of the month (type '$month'), or all (type '$all')\r
            """;
    private static final String SELECT_COUNTER_MODE =
            """
                Select all counters (type $all) or select specific ones (type 'name1 name2 ...').\r
                When choosing the latter note that it is necessary to part each counter name by a comma and a space ( ', ').\r
                To see a list of all counter names type '$ls'.\r
            """;

    /**
     * Selects possible themes.
     */
    public static void doThemes(BufferedReader br, BufferedWriter bw, MainLoader mainLoader) throws IOException {
        writeFlushWait(bw, SELECT_THEMES);
        String s = readLine(br);
        switch (s){
            case "$exit" -> {return;}
            case "$counter" -> {
                writeFlushWait(bw, SELECT_COUNTER_MODE);
                processQuestioning(
                        br, bw,
                        getCounterMode(br, bw, mainLoader),
                        "The categories are %s".formatted(ThemeLoader.getCOUNTER_NAMES())
                );
            }
            case "$days" -> {
                writeFlushWait(bw, SELECT_DAYS_MODE);
                processQuestioning(br, bw, getDaysMode(br, bw, mainLoader));
            }
            case "$positions" -> processQuestioning(br, bw, mainLoader.loadPositions().collect());
            case "$directions" -> processQuestioning(br, bw, mainLoader.loadDirections().collect());
            case "$families" -> processQuestioning(br, bw, mainLoader.loadFamilies().collect());
            default -> writeAndFlush(bw, "Mode is not supported.\r\n");
        }
        doThemes(br, bw, mainLoader);
    }

    /**
     * If $days was selected then processes the following modes.
     * @return List of vocabularies depending on the mode
     */
    private static List<Vocabulary> getDaysMode(BufferedReader br, BufferedWriter bw, MainLoader mainLoader) throws IOException {
        String mode = readLine(br);
        return switch (mode){
            case "$exit" -> new ArrayList<>();
            case "$all", "" -> mainLoader.loadDays().collect();
            case "$weekdays" -> mainLoader.loadWeekDays().collect();
            case "$month" -> mainLoader.loadMonthDays().collect();
            default -> {
                writeAndFlush(bw, "Unknown mode %s. Type a valid mode.%n".formatted(mode));
                yield new ArrayList<>();
            }
        };
    }

    /**
     * Selects possible counter modes. Supported are $all yielding all possible counters, and specific by name yielding the correlating counter.
     * Typing $list will list all possible names of the counters.
     * @return List of the selected counter vocabularies
     */
    private static List<Vocabulary> getCounterMode(BufferedReader br, BufferedWriter bw, MainLoader mainLoader) throws IOException {
        String mode = readLine(br);
        return switch (mode){
            case "$exit" -> new ArrayList<>();
            case "$all","" -> mainLoader.loadAllCounter().collect();
            case "$ls"-> {
                writeFlushWait(bw,
                        "These are the categories:%n%s%nSelect a mode as mentioned above.%n"
                                .formatted(ThemeLoader.getCOUNTER_NAMES())
                );
                yield getCounterMode(br, bw, mainLoader);
            }
            default -> {
                for (String s : mode.split(" ")) {
                    mainLoader.loadCounterByName(s);
                }
                yield mainLoader.collect();
            }
        };
    }
}
