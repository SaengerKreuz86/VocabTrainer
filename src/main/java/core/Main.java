package core;

import core.loading.MainLoader;
import core.selecting.LessonSelector;
import core.selecting.ThemeSelector;

import java.io.*;

import static core.util.ReaderWriterUtility.*;

public class Main {
    private static final String INTRO =
            """
                Welcome to the VocabTrainer!\r
                This trainer bases on the Japanese lessons by Mrs. Watanabe-Bussmann.\r
            """;
    private static final String SELECT_MODE =
            """
                Select the the mode. You can choose between lessons (type '$lessons') or themes (type '$theme')\r
            """;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new PrintWriter(System.out));
        writeAndFlush(bw, INTRO);
        while (true){
            writeFlushWait(bw, SELECT_MODE);
            if (!selectMode(br, bw)){
                return;
            }
        }
    }

    /**
     * User will be prompted to select a mode.
     * Valid modes are lesson and theme which can be selected by the corresponding code word
     * @return true if the user does not want to exit
     */
    private static boolean selectMode(BufferedReader br, BufferedWriter bw) throws IOException {
        String s = readLine(br);
        MainLoader mainLoader = new MainLoader();
        return switch (s){
            case "$exit" -> false;
            case "$lessons"-> {
                LessonSelector.doLessons(br, bw, mainLoader);
                yield true;
            }
            case "$theme" -> {
                ThemeSelector.doThemes(br, bw, mainLoader);
                yield true;
            }
            default -> {
                writeAndFlush(bw, "Unexpected value: %s%n%n".formatted(s));
                yield true;
            }
        };
    }
}
