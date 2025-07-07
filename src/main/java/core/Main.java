package core;

import model.Vocabulary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static final String INTRO =
            """
                Welcome to the VocabTrainer!\r
                This trainer bases on the Japanese lessons by Mrs. Watanabe-Bussmann.\r
            """;
    private static final String SELECT_LESSON =
            """
                Select the number of the lesson (-1 < x < 13). \r
                Lesson 0 represents vocabulary mentioned outside of the general lessons.\r
                Every other number will be ignored.\r
                Type '$help' to display information about possible commands and inputs.\r
                Waiting for input ...\r
            """;
    private static final String SELECT_LESSON_HELP =
            """
                Type '$all' for all lessons. \r
                Type '$range' for a range of lessons. '$range 1 7' will yield all vocabularies from lesson 1 to 7.
                '$range 8' will yield all vocabularies from lesson 8 to the latest lesson.\r
                Typing '1 2 3 8' will yield the vocabularies of the lessons 1, 2, 3, and 8.\r
                Waiting for input ...\r
            """;
    private static final String PROCEED_TO_QUESTIONNAIRE =
            """
                In the following random vocabularies from the selected lesson will be questioned.\r
                If it shows a Japanese word you need to answer with the corresponding English or German word.\r
                Otherwise, answer with the corresponding Japanese word.\r
                Typing one of the possible solutions is sufficient. However, you can type multiple.
                They need to be separated by a comma and a space. 'a, b' is valid 'a b' or 'a,b' are not\r
                You can exit by typing '$exit'. \r
            """;
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        System.out.println(INTRO + "\n");
        while (true){
            System.out.println(SELECT_LESSON + "\n");
            String[] lessons = formattedRead(" ");
            List<Vocabulary> vocabularies = selectLesson(lessons);
            if (vocabularies == null){
                return;
            }
            System.out.printf("Successfully selected lessons %n");
            System.out.printf("The lesson contains %s vocabularies. %n", vocabularies.size());
            System.out.println("Please define how many rounds you want to do. Must be a single number.");
            int limiter = getRounds(vocabularies.size()/2);
            if (limiter == -1){
                return;
            }
            questionnaire(vocabularies, limiter);
        }
    }

    /**
     * Process of questioning the user with vocabulary.
     * @param vocabularies List from which the questions will be drawn.
     * @param limiter Max amount of questions
     */
    private static void questionnaire(List<Vocabulary> vocabularies, int limiter) throws IOException {
        int correct = 0;
        int loopCounter = 0;
        System.out.printf("Playing for %s rounds! %n%n", limiter);
        System.out.println(PROCEED_TO_QUESTIONNAIRE);
        String[] in;
        List<String> solution;
        Vocabulary vocabulary;
        do {
            System.out.println("Next vocabulary:\r");
            vocabulary = randomSelectVocabularyFrom(vocabularies);
            if ((int)(Math.random()*2)== 1){
                System.out.println(vocabulary.getJapanese());
                solution = vocabulary.getEnglishGerman();
            }else {
                System.out.println(vocabulary.getEnglishGerman());
                solution = vocabulary.getJapanese();
            }
            // norm the list. Everything is lowercase
            solution = solution.stream().map(String::toLowerCase).toList();
            System.out.println("Waiting for input...\r\n");
            in = formattedRead(", ");
            if (in[0].equals("$exit")){
                return;
            }
            //--- check correctness of solutions ---
            if (solutionIsCorrect(solution, in)){
                System.out.println("Correct!");
                correct++;
            }else {
                System.out.println("Incorrect!");
            }
            System.out.printf("The solution was %s%n \r\n", solution);
            System.out.println("------------------------------------");
            loopCounter++;
        }while (loopCounter < limiter);
        System.out.printf("You got %s out of %s right! %n%n", correct, limiter);
    }

    /**
     * Matches a given array against a list of strings.
     * @param solution List of solutions
     * @param in array of strings that will be checked
     * @return If all values in the array are represented in the list return true else false
     */
    private static boolean solutionIsCorrect(List<String> solution, String[] in) {
        for (String s : in) {
            if (!solution.contains(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reads a line from the console input and formats it into lowercase
     * @return lowercase String
     */
    private static String[] formattedRead(String split) throws IOException {
        return reader.readLine().toLowerCase().split(split);
    }

    /**
     * Reads how many rounds were requested to play
     * @param pDefault default value for number of rounds
     * @return -1 if exit was read else number of rounds
     */
    private static int getRounds(int pDefault) throws IOException {
        String str = formattedRead(" ")[0];
        if (str.isEmpty()){
            return pDefault;
        }else if (str.equals("$exit")){
            return -1;
        }
        else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                System.out.printf("Invalid value for %s%n", str);
                System.out.println("Write the number again!");
                return getRounds(pDefault);
            }
        }
    }

    /**
     * Randomly selects a Vocabulary from the given Map.
     * @param vocab Map of Vocabulary
     * @return Vocabulary
     */
    private static Vocabulary randomSelectVocabularyFrom(List<Vocabulary> vocab){
        int selector = (int)(Math.random()*vocab.size());
        return vocab.get(selector);
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
        return switch (x[0]) {
            case "$help" -> {
                System.out.println(SELECT_LESSON_HELP);
                yield selectLesson(formattedRead(" "));
            }
            case "$exit" -> null;
            case "$all", "" -> LessonSelector.getAll();
            case "$range" -> {
                if (x.length == 3){
                    yield LessonSelector.getRange(Integer.parseInt(x[1]), Integer.parseInt(x[2]));
                }else if (x.length == 2){
                    yield LessonSelector.getRange(Integer.parseInt(x[1]));
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
                yield LessonSelector.getVocabularyByLessons(ints);
            }
        };
    }
}
