package core;

import java.io.IOException;

import static core.SystemInReader.readLine;

public class Main {
    private static final String INTRO =
            """
                Welcome to the VocabTrainer!\r
                This trainer bases on the Japanese lessons by Mrs. Watanabe-Bussmann.\r
            """;
    protected static final String WAITING_FOR_INPUT = "Waiting for input...";
    private static final String SELECT_MODE =
            """
                Select the the mode. You can choose between lessons (type '$lesson') or themes (type '$theme')\r
            """;

    public static void main(String[] args) throws IOException {
        System.out.println(INTRO);
        while (true){
            System.out.println(SELECT_MODE);
            System.out.println(WAITING_FOR_INPUT);
            if (!selectMode(readLine())){
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
    private static boolean selectMode(String s) throws IOException {
        System.out.println();
        return switch (s){
            case "$exit" -> false;
            case "$lesson"-> {
                LessonSelector.doLessons();
                yield true;
            }
            case "$theme" -> {
                ThemeSelector.doThemes();
                yield true;
            }
            default -> {
                System.out.println("Unexpected value: " + s + "\r\n");
                yield true;
            }
        };
    }
}
