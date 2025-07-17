package core;

import core.loading.QuestionnaireLoader;
import core.selecting.LessonSelector;
import core.selecting.ThemeSelector;
import core.util.QuestionEvaluator;
import model.Questionnaire;

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
        QuestionnaireLoader questionnaireLoader = new QuestionnaireLoader();
        writeAndFlush(bw, INTRO);
        while (true){
            writeFlushWait(bw, SELECT_MODE);
            questionnaireLoader.clear();
            if (selectedMode(br, bw, questionnaireLoader)){
                Questionnaire questionnaire = questionnaireLoader.collect();
                QuestionEvaluator.eval(
                        br, bw,
                        questionnaire.getVocabularies(),
                        questionnaire.getInfoText()
                );
            }else {
                return;
            }
        }
    }

    /**
     * User will be prompted to select a mode.
     * Valid modes are lesson and theme which can be selected by the corresponding code word
     * @return true if the user does not want to exit
     */
    private static boolean selectedMode(BufferedReader br, BufferedWriter bw, QuestionnaireLoader questionnaireLoader) throws IOException {
        String s = readLine(br);
        return switch (s){
            case "$exit" -> false;
            case "$lessons"-> {
                LessonSelector.selectLessons(br, bw, questionnaireLoader);
                yield true;
            }
            case "$theme" -> {
                ThemeSelector.selectTheme(br, bw, questionnaireLoader);
                yield true;
            }
            default -> {
                writeAndFlush(bw, "Unexpected value: %s%n%n".formatted(s));
                yield true;
            }
        };
    }
}
