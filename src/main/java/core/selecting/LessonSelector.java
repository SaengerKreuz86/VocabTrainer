package core.selecting;

import core.loading.MainLoader;
import model.Vocabulary;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static core.Main.*;
import static core.util.QuestionEvaluator.processQuestioning;
import static core.util.ReaderUtility.formattedRead;
import static core.loading.MainLoader.runAndCollect;

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
    public static void doLessons(BufferedReader br) throws IOException {
        //recurses as often as the user wants it
        System.out.println(SELECT_LESSON);
        System.out.println(getWAITING_FOR_INPUT());
        String[] lessons = formattedRead(br," ");
        List<Vocabulary> vocabularies = selectLesson(br,lessons);
        if (vocabularies != null){
            System.out.printf("Successfully selected lessons %n");
            processQuestioning(br,vocabularies);
            doLessons(br);
        }
    }

    /**
     * Selects the vocabulary of a lesson.
     * @param x current read string on which the lesson will be evaluated
     * @return null if the user wants to exit.
     * Otherwise, the user is recursively trapped until he makes a valid input.
     * In this case the corresponding vocabulary is returned.
     */
    private static List<Vocabulary> selectLesson(BufferedReader br, String[] x) throws IOException {
        try {
            return evalTriggerForLessonSelector(br,x);
        }catch (NumberFormatException e){
            System.out.println("Did not read a number!\r\n");
        }
        System.out.println(SELECT_LESSON);
        return selectLesson(br,formattedRead(br," "));
    }

    /**
     * Processes trigger words for selecting a lesson.
     * $exit closes the program later on
     * $all yields all possible vocabularies
     * $range yields the vocabularies within the range of the lessons
     * @param x List of the inputs
     * @return List of vocabularies
     */
    private static List<Vocabulary> evalTriggerForLessonSelector(BufferedReader br, String[] x) throws IOException {
        System.out.println();
        return switch (x[0]) {
            case "$help" -> {
                System.out.println(SELECT_LESSON_HELP);
                yield selectLesson(br,formattedRead(br," "));
            }
            case "$exit" -> null;
            case "$all", "" -> runAndCollect(new MainLoader()::loadAllLessons);
            case "$range" -> {
                if (x.length > 1){
                    yield new MainLoader().loadRangeLessons(
                            (Integer[]) Arrays.stream(x)
                                    .sequential()
                                    .map(Integer::parseInt)
                                    .toArray())
                            .collect();
                }else {
                    System.out.println("The range was not specified.\r\n");
                    System.out.println(SELECT_LESSON);
                    yield selectLesson(br,formattedRead(br," "));
                }
            }
            default -> {
                MainLoader ml = new MainLoader();
                for (String s : x) {
                    ml.loadLesson(Integer.parseInt(s));
                }
                yield ml.collect();
            }
        };
    }
}
