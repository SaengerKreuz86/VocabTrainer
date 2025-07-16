package core.util;

import lombok.experimental.UtilityClass;
import model.Vocabulary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import static core.util.ReaderWriterUtility.*;

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
    public static void processQuestioning(BufferedReader br, BufferedWriter bw, List<Vocabulary> vocabularies, String info) throws IOException {
        if (!vocabularies.isEmpty()){
            writeAndFlush(bw, "Please define how many rounds you want to do. Must be a single number.");
            int limiter = getRounds(br, bw,vocabularies.size());
            if (limiter == -1){ //exit command was triggered
                return;
            }
            questionnaire(br, bw, vocabularies, limiter, info);
        }
    }

    /**
     * Processes the questioning. Determines how many rounds are done and questions the user. Neglects giving helpful information
     * @param vocabularies List of vocabularies to question from
     */
    public static void processQuestioning(BufferedReader br, BufferedWriter bw, List<Vocabulary> vocabularies) throws IOException {
        processQuestioning(br, bw, vocabularies, "Only god can help you.\r\n");
    }

    /**
     * Process of questioning the user with vocabulary.
     * @param vocabularies List from which the questions will be drawn.
     * @param limiter Max amount of questions
     */
    private static void questionnaire(BufferedReader br, BufferedWriter bw, List<Vocabulary> vocabularies, int limiter, String info) throws IOException {
        bw.write(
                "The list of vocabularies contains %s vocabularies.%nPlaying for %s rounds! %n%n"
                        .formatted(vocabularies.size(), limiter)
        );
        writeAndFlush(bw, PROCEED_TO_QUESTIONNAIRE);
        int correct = 0;
        int loopCounter = 0;
        List<String> solution;
        Vocabulary vocabulary;
        do {
            writeAndFlush(bw, "Next vocabulary:\r\n");
            vocabulary = randomSelectVocabularyFrom(vocabularies);
            solution = getSolution(bw, vocabulary);
            // norm the list. Everything is lowercase
            solution = solution.stream().map(String::toLowerCase).toList();
            // process user input
            int answer = processQuestionsAnswer(br, bw, solution, info);
            if (answer == -1){
                return;
            }else {
                correct += answer;
            }
            writeAndFlush(bw, "The solution was %s%n".formatted(solution));
            writeAndFlush(bw, "------------------------------------\r\n\n");
            loopCounter++;
        }while (loopCounter < limiter);
        writeAndFlush(bw, "You got %s out of %s right. That's %s percent! %n%n".formatted(correct, limiter, (float) correct/limiter*100));
    }

    /**
     * Processes the input of the user after being asked a vocabulary question. If they type $exit the mode will be left.
     * If they type $help they will get the info string displayed. Otherwise, their input will be checked for correctness.
     * @param solution Valid solutions for a word
     * @param info info string displayed after typing the $help
     * @return -1 if exit was read, 1 if answer correct, 0 if answer incorrect.
     */
    private static int processQuestionsAnswer(BufferedReader br, BufferedWriter bw, List<String> solution, String info) throws IOException {
        String[] in = formattedRead(br, ", ");
        //check for commands
        if (in[0].equals("$exit")){
            return -1;
        }else if (in[0].equals("$help") && info != null){
            writeAndFlush(bw, info);
            return processQuestionsAnswer(br, bw, solution, info); // read new answer
        }
        if (solutionIsCorrect(solution, in)){ //--- check correctness of solutions ---
            writeAndFlush(bw, "Correct!\r\n");
            return 1;
        }else {
            writeAndFlush(bw, "Incorrect!\r\n");
            return 0;
        }
    }

    /**
     * Reads how many rounds were requested to play
     * @param pDefault default value for number of rounds
     * @return -1 if exit was read else number of rounds
     */
    private static int getRounds(BufferedReader br, BufferedWriter bw, int pDefault) throws IOException {
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
                writeAndFlush(bw, "Invalid value for %s%n. Write the number again!%n".formatted(str));
                return getRounds(br, bw, pDefault);
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
    private static List<String> getSolution(BufferedWriter bw, Vocabulary vocabulary) throws IOException {
        String message;
        List<String> solution;
        // randomly choose english/german or japanese word to question
        if ((int)(Math.random()*2)== 1){
            message = vocabulary.getJapanese().toString();
            solution = vocabulary.getEnglishGerman();
        }else {
            message = vocabulary.getEnglishGerman().toString();
            solution = vocabulary.getJapanese();
        }
        writeFlushWait(bw, message);
        return solution;
    }
}
