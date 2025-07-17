package core.selecting;

import core.loading.QuestionnaireLoader;
import core.util.ArrayUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import static core.util.ReaderWriterUtility.*;

public class LessonSelector {

    private static final String SELECT_LESSON =
            """
                Select the number of the lesson (-1 < x < 13). \r
                Lesson 0 represents vocabulary mentioned outside of the general lessons.\r
                Every other number will be ignored.\r
                Type '$help' to display information about possible commands and inputs.\r
            """;
    private static final String SELECT_LESSON_HELP =
            """
                Type '$all' for all lessons. \r
                Type '$range' for a range of lessons. '$range 1 7' will yield all vocabularies from lesson 1 to 7.
                '$range 8' will yield all vocabularies from lesson 8 to the latest lesson.\r
                Typing '1 2 3 8' will yield the vocabularies of the lessons 1, 2, 3, and 8.\r
            """;

    /**
     * Processes selecting lessons
     */
    public static void selectLessons(BufferedReader br, BufferedWriter bw, QuestionnaireLoader questionnaireLoader) throws IOException {
        //recurses as often as the user wants it
        writeFlushWait(bw, SELECT_LESSON);
        String[] mode;
        do {
             mode = formattedRead(br, " ");
        }while (processMode(bw, questionnaireLoader, mode));
    }

    /**
     * Processes trigger words for selecting a lesson.
     * $exit closes the program later on
     * $all yields all possible vocabularies
     * $range yields the vocabularies within the range of the lessons
     * @return true if method must loop
     */
    private static boolean processMode(BufferedWriter bw, QuestionnaireLoader questionnaireLoader, String[] mode) throws IOException {
        boolean loop = false;
        switch (mode[0]) {
            case "$exit" -> {}
            case "$help" -> {
                writeAndFlush(bw, SELECT_LESSON_HELP);
                loop = true;
            }
            case "$all", "" -> questionnaireLoader.loadAllLessons();
            default -> {
                int[] extractedInts = ArrayUtil.toIntArray(mode);
                if (extractedInts.length > 0){
                    if ("$range".equals(mode[0])){
                        questionnaireLoader.loadRangeLessons(extractedInts);
                    }else { // load specified lessons
                        for (int i : extractedInts) {
                            questionnaireLoader.loadLesson(i);
                        }
                    }
                }else {
                    writeAndFlush(bw,"There weren't any valid numbers.\r\n");
                    loop = true;
                }
            }
        }
        return loop;
    }
}
