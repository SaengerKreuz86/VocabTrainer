package core;

import model.Vocabulary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static core.Main.*;
import static core.QuestionEvaluator.processQuestioning;

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
    public static void doLessons() throws IOException {
        //recurses as often as the user wants it
        System.out.println(SELECT_LESSON);
        System.out.println(WAITING_FOR_INPUT);
        String[] lessons = formattedRead(" ");
        List<Vocabulary> vocabularies = selectLesson(lessons);
        if (vocabularies != null){
            System.out.printf("Successfully selected lessons %n");
            processQuestioning(vocabularies);
            doLessons();
        }
    }

    /**
     * Selects the vocabulary of a lesson.
     * @param x current read string on which the lesson will be evaluated
     * @return null if the user wants to exit.
     * Otherwise, the user is recursively trapped until he makes a valid input.
     * In this case the corresponding vocabulary is returned.
     */
    private static List<Vocabulary> selectLesson(String[] x) throws IOException {
        try {
            return evalTriggerForLessonSelector(x);
        }catch (NumberFormatException e){
            System.out.println("Did not read a number!\r\n");
        }
        System.out.println(SELECT_LESSON);
        return selectLesson(formattedRead(" "));
    }

    /**
     * Processes trigger words for selecting a lesson.
     * $exit closes the program later on
     * $all yields all possible vocabularies
     * $range yields the vocabularies within the range of the lessons
     * @param x List of the inputs
     * @return List of vocabularies
     */
    private static List<Vocabulary> evalTriggerForLessonSelector(String[] x) throws IOException {
        System.out.println();
        return switch (x[0]) {
            case "$help" -> {
                System.out.println(SELECT_LESSON_HELP);
                yield selectLesson(formattedRead(" "));
            }
            case "$exit" -> null;
            case "$all", "" -> LessonLoader.getAll();
            case "$range" -> {
                if (x.length == 3){
                    yield LessonLoader.getRange(Integer.parseInt(x[1]), Integer.parseInt(x[2]));
                }else if (x.length == 2){
                    yield LessonLoader.getRange(Integer.parseInt(x[1]));
                }else {
                    System.out.println("The range was not specified.\r\n");
                    System.out.println(SELECT_LESSON);
                    yield selectLesson(formattedRead(" "));
                }
            }
            default -> {
                List<Integer> ints = new ArrayList<>();
                for (String s : x) {
                    ints.add(Integer.parseInt(s));
                }
                yield LessonLoader.getVocabularyByLessons(ints);
            }
        };
    }
}
