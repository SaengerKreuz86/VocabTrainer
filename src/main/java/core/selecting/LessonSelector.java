package core.selecting;

import core.loading.MainLoader;
import core.util.ArrayUtil;
import model.Vocabulary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static core.util.QuestionEvaluator.processQuestioning;
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
    public static void doLessons(BufferedReader br, BufferedWriter bw, MainLoader mainLoader) throws IOException {
        //recurses as often as the user wants it
        writeFlushWait(bw, SELECT_LESSON);
        String[] mode = formattedRead(br, " ");
        List<Vocabulary> vocabularies = selectLesson(bw, mainLoader, mode);
        if (vocabularies != null){
            if (vocabularies.isEmpty()) {
                doLessons(br, bw, mainLoader);
            }else {
                writeAndFlush(bw, "Successfully selected lessons!\r\n");
                processQuestioning(br, bw, vocabularies);
                doLessons(br, bw, mainLoader);
            }
        }
    }

    /**
     * Processes trigger words for selecting a lesson.
     * $exit closes the program later on
     * $all yields all possible vocabularies
     * $range yields the vocabularies within the range of the lessons
     * @return List of vocabularies
     */
    private static List<Vocabulary> selectLesson(BufferedWriter bw, MainLoader mainLoader, String[] mode) throws IOException {
        System.out.println();
        return switch (mode[0]) {
            case "$help" -> {
                writeAndFlush(bw, SELECT_LESSON_HELP);
                yield new ArrayList<>();
            }
            case "$exit" -> null;
            case "$all", "" -> mainLoader.loadAllLessons().collect();
            case "$range" -> {
                if (mode.length > 1){
                    yield mainLoader.loadRangeLessons(ArrayUtil.toIntArray(mode)).collect();
                }else {
                    writeAndFlush(bw, "The range was not specified.\r\n");
                    yield new ArrayList<>();
                }
            }
            default -> {
                int[] intArrayExtract = ArrayUtil.toIntArray(mode);
                if (intArrayExtract.length == 0){
                    writeAndFlush(bw,"There weren't any valid numbers.\r\n");
                }else {
                    for (int i : intArrayExtract) {
                        mainLoader.loadLesson(i);
                    }
                }
                yield mainLoader.collect();
            }
        };
    }
}
