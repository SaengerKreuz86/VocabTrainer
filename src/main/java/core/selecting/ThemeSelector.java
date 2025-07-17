package core.selecting;

import core.loading.QuestionnaireLoader;
import core.loading.ThemeLoader;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

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
    public static void selectTheme(BufferedReader br, BufferedWriter bw, QuestionnaireLoader questionnaireLoader) throws IOException {
        writeFlushWait(bw, SELECT_THEMES);
        String s = readLine(br);
        switch (s){
            case "$exit" -> {}
            case "$counter" -> {
                writeFlushWait(bw, SELECT_COUNTER_MODE);
                getCounterMode(br, bw, questionnaireLoader);
                questionnaireLoader.setInfo("Counters: The categories are %s%n".formatted(ThemeLoader.getCOUNTER_NAMES()));
            }
            case "$days" -> {
                writeFlushWait(bw, SELECT_DAYS_MODE);
                getDaysMode(br, bw, questionnaireLoader);
            }
            case "$positions" -> questionnaireLoader.loadPositions();
            case "$directions" -> questionnaireLoader.loadDirections();
            case "$families" -> questionnaireLoader.loadFamilies();
            default -> writeAndFlush(bw, "Mode is not supported.\r\n");
        }
    }

    /**
     * If $days was selected then processes the following modes.
     */
    private static void getDaysMode(BufferedReader br, BufferedWriter bw, QuestionnaireLoader questionnaireLoader) throws IOException {
        String mode = readLine(br);
        switch (mode){
            case "$exit" -> {}
            case "$all", "" -> {
                questionnaireLoader.loadDays();
                questionnaireLoader.setInfo("If a number is displayed you have to answer with the name of the day of a month.\r\n");
            }
            case "$weekdays" -> questionnaireLoader.loadWeekDays();
            case "$month" -> questionnaireLoader.loadMonthDays();
            default -> writeAndFlush(bw, "Unknown mode %s. Type a valid mode.%n".formatted(mode));
        }
    }

    /**
     * Selects possible counter modes. Supported are $all yielding all possible counters, and specific by name yielding the correlating counter.
     * Typing $list will list all possible names of the counters.
     */
    private static void getCounterMode(BufferedReader br, BufferedWriter bw, QuestionnaireLoader questionnaireLoader) throws IOException {
        String mode = readLine(br);
        switch (mode){
            case "$exit" -> {}
            case "$all","" -> questionnaireLoader.loadAllCounter();
            case "$ls"-> {
                writeFlushWait(bw, "These are the categories:%n%s%nSelect a mode as mentioned above.%n".formatted(ThemeLoader.getCOUNTER_NAMES()));
                getCounterMode(br, bw, questionnaireLoader);
            }
            default -> {
                for (String s : mode.split(" ")) {
                    questionnaireLoader.loadCounterByName(s);
                }
            }
        }
    }
}
