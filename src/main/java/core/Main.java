package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static core.ReaderUtility.readLine;

public class Main {
    private static final String INTRO =
            """
                Welcome to the VocabTrainer!\r
                This trainer bases on the Japanese lessons by Mrs. Watanabe-Bussmann.\r
            """;
    private static final String SELECT_MODE =
            """
                Select the the mode. You can choose between lessons (type '$lesson') or themes (type '$theme')\r
            """;
    protected static final String WAITING_FOR_INPUT = "Waiting for input...";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(INTRO);
        while (true){
            System.out.println(SELECT_MODE);
            System.out.println(WAITING_FOR_INPUT);
            if (!selectMode(br, readLine(br))){
                return;
            }
        }
    }

    /**
     * User will be prompted to select a mode.
     * Valid modes are lesson and theme which can be selected by the corresponding code word
     * @param s mode of operation
     * @return true if the user does not want to exit
     */
    private static boolean selectMode(BufferedReader br, String s) throws IOException {
        System.out.println();
        return switch (s){
            case "$exit" -> false;
            case "$lesson"-> {
                LessonSelector.doLessons(br);
                yield true;
            }
            case "$theme" -> {
                ThemeSelector.doThemes(br);
                yield true;
            }
            default -> {
                System.out.println("Unexpected value: " + s + "\r\n");
                yield true;
            }
        };
    }
}
