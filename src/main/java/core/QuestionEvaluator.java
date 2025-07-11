package core;

import lombok.experimental.UtilityClass;
import model.Vocabulary;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static core.Main.*;
import static core.ReaderUtility.formattedRead;
import static core.ReaderUtility.readLine;

@UtilityClass
public class QuestionEvaluator {

    private static final String PROCEED_TO_QUESTIONNAIRE =
            """
                In the following random vocabularies that you selected beforehand will be questioned.\r
                If it shows a Japanese word you need to answer with the corresponding English or German word.\r
                Otherwise, answer with the corresponding Japanese word.\r
                Typing one of the possible solutions is sufficient. However, you can type multiple.
                They need to be separated by a comma and a space. 'a, b' is valid 'a b' or 'a,b' are not\r
                You can exit by typing '$exit'.\r
            """;

    /**
     * Processes the questioning. Determines how many rounds are done and questions the user.
     * @param vocabularies List of vocabularies to question from
     * @param info Help given for the user
     */
    public static void processQuestioning(BufferedReader br,List<Vocabulary> vocabularies, String info) throws IOException {
        if (!vocabularies.isEmpty()){
            System.out.println("Please define how many rounds you want to do. Must be a single number.");
            int limiter = getRounds(br,vocabularies.size()/2);
            if (limiter == -1){ //exit command was triggered
                return;
            }
            questionnaire(br,vocabularies, limiter, info);
        }
    }

    /**
     * Processes the questioning. Determines how many rounds are done and questions the user. Neglects giving helpful information
     * @param vocabularies List of vocabularies to question from
     */
    public static void processQuestioning(BufferedReader br, List<Vocabulary> vocabularies) throws IOException {
        processQuestioning(br,vocabularies, "Only god can help you.\r\n");
    }

    /**
     * Process of questioning the user with vocabulary.
     * @param vocabularies List from which the questions will be drawn.
     * @param limiter Max amount of questions
     */
    private static void questionnaire(BufferedReader br,List<Vocabulary> vocabularies, int limiter, String info) throws IOException {
        System.out.printf("The list of vocabularies contains %s vocabularies. %n", vocabularies.size());
        System.out.printf("Playing for %s rounds! %n%n", limiter);
        System.out.println(PROCEED_TO_QUESTIONNAIRE);
        int correct = 0;
        int loopCounter = 0;
        List<String> solution;
        Vocabulary vocabulary;
        do {
            System.out.println("Next vocabulary:\r");
            vocabulary = randomSelectVocabularyFrom(vocabularies);
            solution = getSolution(vocabulary);
            // norm the list. Everything is lowercase
            solution = solution.stream().map(String::toLowerCase).toList();
            System.out.println(getWAITING_FOR_INPUT());
            // process user input
            int answer = processQuestionsAnswer(br, solution, info);
            if (answer == -1){
                return;
            }else {
                correct += answer;
            }
            System.out.printf("The solution was %s%n \r\n", solution);
            System.out.println("------------------------------------");
            loopCounter++;
        }while (loopCounter < limiter);
        System.out.printf("You got %s out of %s right! %n%n", correct, limiter);
    }

    /**
     * Processes the input of the user after being asked a vocabulary question. If they type $exit the mode will be left.
     * If they type $help they will get the info string displayed. Otherwise, their input will be checked for correctness.
     * @param solution Valid solutions for a word
     * @param info info string displayed after typing the $help
     * @return -1 if exit was read, 1 if answer correct, 0 if answer incorrect.
     */
    private static int processQuestionsAnswer(BufferedReader br, List<String> solution, String info) throws IOException {
        String[] in = formattedRead(br, ", ");
        //check for commands
        if (in[0].equals("$exit")){
            return -1;
        }else if (in[0].equals("$help") && info != null){
            System.out.println(info);
            return processQuestionsAnswer(br, solution, info); // read new answer
        }
        if (solutionIsCorrect(solution, in)){ //--- check correctness of solutions ---
            System.out.println("Correct!");
            return 1;
        }else {
            System.out.println("Incorrect!");
            return 0;
        }
    }

    /**
     * Reads how many rounds were requested to play
     * @param pDefault default value for number of rounds
     * @return -1 if exit was read else number of rounds
     */
    private static int getRounds(BufferedReader br,int pDefault) throws IOException {
        String str = readLine(br);
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
                return getRounds(br, pDefault);
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
     * randomly selects which meanings the user will have to guess (japanese or english/german)
     * @param vocabulary Vocabulary which will be questioned
     * @return The solutions of a vocabulary
     */
    private static List<String> getSolution(Vocabulary vocabulary){
        // randomly choose english/german or japanese word to question
        if ((int)(Math.random()*2)== 1){
            System.out.println(vocabulary.getJapanese());
            return vocabulary.getEnglishGerman();
        }else {
            System.out.println(vocabulary.getEnglishGerman());
            return vocabulary.getJapanese();
        }
    }
}
