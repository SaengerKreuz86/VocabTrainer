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
                This trainer bases on the Japanese lessons by Mrs. Watanabe-Bussmann.
            """;
    private static final String SELECT_LESSON =
            """
                Select the number of the lesson (-1 < x < 13). \r
                Lesson 0 represents vocabulary mentioned outside of the general lessons.\r
                Waiting for input ...
            """;
    private static final String PROCEED_TO_QUESTIONNAIRE =
            """
                In the following random vocabularies from the selected lesson will be questioned.\r
                If it shows a Japanese word you need to answer with the corresponding English or German word.\r
                Otherwise, answer with the corresponding Japanese word.\r
                Typing one of the possible solutions is sufficient.\r
                You can exit by typing '$exit'. \r
            """;
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final Random rand = new Random();

    public static void main(String[] args) throws IOException {
        System.out.println(INTRO + "\n");
        while (true){
            System.out.println(SELECT_LESSON + "\n");
            String lesson = formattedRead();
            Map<List<String>, List<String>> vocabularies = selectLesson(lesson);
            if (vocabularies == null){
                return;
            }
            System.out.printf("Successfully selected lesson %s%n", lesson);
            System.out.printf("The lesson contains %s vocabularies. %n", vocabularies.size());
            System.out.println("Please define how many rounds you want to do. Must be a number.");
            int limiter = getRounds(vocabularies.size()/2);
            if (limiter == -1){
                return;
            }
            questionnaire(vocabularies, limiter);
        }
    }

    private static void questionnaire(Map<List<String>, List<String>> vocabularies, int limiter) throws IOException {
        int correct = 0;
        int loopCounter = 0;
        System.out.printf("Playing for %s rounds! %n", limiter);
        System.out.println(PROCEED_TO_QUESTIONNAIRE);
        String in;
        List<String> solution;
        Vocabulary vocabulary;
        do {
            System.out.println("Next vocabulary:\r");
            vocabulary = randomSelectVocabularyFrom(vocabularies);
            if (rand.nextInt(2) == 1){
                System.out.println(vocabulary.getA());
                solution = vocabulary.getB();
            }else {
                System.out.println(vocabulary.getB());
                solution = vocabulary.getA();
            }
            System.out.println("Waiting for input...\r\n");
            in = formattedRead();
            if (in.equals("$exit")){
                return;
            }
            if (solution.contains(in)) {
                System.out.println("Correct!");
                correct++;
            } else {
                System.out.println("Incorrect!");
            }
            System.out.printf("The solution was %s%n \r\n", solution);
            loopCounter++;
        }while (loopCounter < limiter);
        System.out.printf("You got %s out of %s right! %n%n", correct, limiter);
    }

    private static String formattedRead() throws IOException {
        return reader.readLine().toLowerCase();
    }

    /**
     * Reads how many rounds were requested to play
     * @param pDefault default value for number of rounds
     * @return -1 if exit was read else number of rounds
     */
    private static int getRounds(int pDefault) throws IOException {
        String str = formattedRead();
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

    private static Vocabulary randomSelectVocabularyFrom(Map<List<String>, List<String>> vocab){
        int selector = rand.nextInt(vocab.size()-1);
        for (Map.Entry<List<String>, List<String>> entry: vocab.entrySet()){
            if (selector == 0){
                return new Vocabulary(entry.getKey(), entry.getValue());
            }
            selector--;
        }
        throw new IllegalStateException();
    }

    private static Map<List<String>, List<String>> selectLesson(String x) throws IOException {
        if (x.equals("$exit")){
            return null;
        }
        int parsed = -1;
        try {
            parsed = Integer.parseInt(x);
        } catch (NumberFormatException _) {
            System.out.println("Did not read a number!\r\n");
        }
        Map<List<String>, List<String>> vocabularies = new HashMap<>();
        switch (parsed){
            case 7 -> getL7(vocabularies);
            case 8 -> getL8(vocabularies);
            case 9 -> getL9(vocabularies);
            case 10 -> getL10(vocabularies);
            case 11 -> getL11(vocabularies);
            case 12 -> getL12(vocabularies);
            default -> {
                System.out.println("Invalid value for " + x);
                System.out.println("Write the number again. It must be larger than -1 and smaller than 13.");
                return selectLesson(reader.readLine());
            }
        }
        return vocabularies;
    }

    private static void getL7(Map<List<String>, List<String>> vocabularies){
        // Japanese , English
        vocabularies.put(List.of("kirimasu"         ), List.of("cut", "slice", "schneiden"));
        vocabularies.put(List.of("okurimasu"        ), List.of("send", "senden"));
        vocabularies.put(List.of("agemasu"          ), List.of("give", "geben"));
        vocabularies.put(List.of("moraimasu"        ), List.of("receive", "erhalten"));
        vocabularies.put(List.of("kashimasu"        ), List.of("lend", "ausleihen"));
        vocabularies.put(List.of("karimasu"         ), List.of("borrow", "borgen"));
        vocabularies.put(List.of("oshiemasu"        ), List.of("teach", "lehren"));
        vocabularies.put(List.of("naraimasu"        ), List.of("learn", "lernen"));
        vocabularies.put(List.of("kakemasu (denwa)" ), List.of("make (a phone call)"));
        vocabularies.put(List.of("te"               ), List.of("hand", "arm"));
        vocabularies.put(List.of("hashi"            ), List.of("chopsticks", "essstäbchen"));
        vocabularies.put(List.of("supuun"           ), List.of("spoon", "löffel"));
        vocabularies.put(List.of("naihu"            ), List.of("knife", "Messer"));
        vocabularies.put(List.of("fooku"            ), List.of("fork", "gabel"));
        vocabularies.put(List.of("hasami"           ), List.of("scissors", "schere"));
        vocabularies.put(List.of("pasokon"          ), List.of("personal computer", "pc"));
        vocabularies.put(List.of("panchi"           ), List.of("punch", "locher"));
        vocabularies.put(List.of("hocchikisu"       ), List.of("stapler", "tacker"));
        vocabularies.put(List.of("seroteipu"        ), List.of("scotch tape", "paket band"));
        vocabularies.put(List.of("keshigomu"        ), List.of("eraser", "radiergummi"));
        vocabularies.put(List.of("kami"             ), List.of("paper", "papier"));
        vocabularies.put(List.of("hana"             ), List.of("flower", "blume"));
        vocabularies.put(List.of("shatsu"           ), List.of("shirt", "hemd"));
        vocabularies.put(List.of("purezento"        ), List.of("present", "gift", "geschenk"));
        vocabularies.put(List.of("nimotsu"          ), List.of("baggage", "parcel", "paket"));
        vocabularies.put(List.of("okane"            ), List.of("money", "geld"));
        vocabularies.put(List.of("kippu"            ), List.of("ticket", "fahrkarte"));
        vocabularies.put(List.of("otoosan"          ), List.of("someone else's father", "vater eines anderen"));
        vocabularies.put(List.of("okaasan"          ), List.of("someone else's mother", "mutter eines anderen"));
        vocabularies.put(List.of("itadakimasu"      ), List.of("thank you (before eat/drink)", "mahlzeit! (aber höflicher)"));
        vocabularies.put(List.of("ryoko"            ), List.of("trip", "tour", "reise"));
        vocabularies.put(List.of("miyage", "omiyage"   ), List.of("souvenir", "present", "mitbringsel"));
    }

    private static void getL8(Map<List<String>, List<String>> vocabularies){
        // Japanese , English
        vocabularies.put(List.of("hansamu", "hansamu na"   ), List.of("handsome", "attraktiv", "gut aussehend"));
        vocabularies.put(List.of("kirei", "kirei na"   ), List.of("beautiful", "schön"));
        vocabularies.put(List.of("shizuka", "shizuka na"   ), List.of("quiet", "still", "leise"));
        vocabularies.put(List.of("nigiyaka", "nigiyaka na"   ), List.of("lively", "lebhaft", "rege"));
        vocabularies.put(List.of("yuumei", "yuumei na"   ), List.of("famous", "berühmt", "bekannt"));
        vocabularies.put(List.of("shinsetsu", "shinsetsu na"   ), List.of("kind", "freundlich"));
        vocabularies.put(List.of("genki", "genki na"   ), List.of("healthy", "cheerful", "freundlich", "heiter", "gesund"));
        vocabularies.put(List.of("hima", "hima na"   ), List.of("free", "free time", "freizeit"));
        vocabularies.put(List.of("benri", "benri na"   ), List.of("convenient", "praktisch"));
        vocabularies.put(List.of("suteki", "suteki na"   ), List.of("nice", "wonderful", "wundervoll"));
        vocabularies.put(List.of("ookii"), List.of("big", "large", "groß"));
        vocabularies.put(List.of("chiisai"), List.of("small", "little", "klein"));
        vocabularies.put(List.of("atarashii"), List.of("new", "neu"));
        vocabularies.put(List.of("furui"), List.of("old (not of age)", "alt"));
        vocabularies.put(List.of("ii", "iiyoi"), List.of("good", "gut"));
        vocabularies.put(List.of("warui"), List.of("bad", "schlecht"));
        vocabularies.put(List.of("atsui"), List.of("hot", "heiß"));
        vocabularies.put(List.of("samui"), List.of("cold (weather)", "kalt"));
        vocabularies.put(List.of("tsumetai"), List.of("cold (to the touch)", "kalt"));
        vocabularies.put(List.of("muzukashii"), List.of("difficult", "schwierig", "schwer"));
        vocabularies.put(List.of("yasashii"), List.of("easy", "einfach"));
        vocabularies.put(List.of("takai"), List.of("expensive", "teuer", "tall", "heigh", "hoch", "groß"));
        vocabularies.put(List.of("yasui"), List.of("cheap", "günstig"));
        vocabularies.put(List.of("hikui"), List.of("low", "niedrig"));
        vocabularies.put(List.of("omoshiroi"), List.of("interesting", "interessant"));
        vocabularies.put(List.of("oishii"), List.of("delicious", "lecker", "tasty"));
        vocabularies.put(List.of("isogashii"), List.of("busy", "beschäftigt"));
        vocabularies.put(List.of("tanoshii"), List.of("fun", "spaßig"));
        vocabularies.put(List.of("shiroi"), List.of("white", "weiß"));
        vocabularies.put(List.of("kuroi"), List.of("black", "schwarz"));
        vocabularies.put(List.of("akai"), List.of("red", "rot"));
        vocabularies.put(List.of("aoi"), List.of("blue", "blau"));
        vocabularies.put(List.of("sakura"), List.of("cherry", "cherry blossom", "kirsche", "kirschblüte"));
        vocabularies.put(List.of("yama"), List.of("mountain", "berg"));
        vocabularies.put(List.of("tabemono"), List.of("food", "essen"));
        vocabularies.put(List.of("kuruma"), List.of("car", "auto", "automobil"));
        vocabularies.put(List.of("benkyo"), List.of("study", "studieren", "lernen"));
        vocabularies.put(List.of("oshigoto", "shigoto"), List.of("work", "business", "arbeit"));
    }
    private static void getL9(Map<List<String>, List<String>> vocabularies){
        // Japanese , English
    }
    private static void getL10(Map<List<String>, List<String>> vocabularies){
        // Japanese , English
    }private static void getL11(Map<List<String>, List<String>> vocabularies){
        // Japanese , English
    }private static void getL12(Map<List<String>, List<String>> vocabularies){
        // Japanese , English
    }
    private static void getL13(Map<List<String>, List<String>> vocabularies){
        // Japanese , English
    }
    private static void getL14(Map<List<String>, List<String>> vocabularies){
        // Japanese , English
    }


}
