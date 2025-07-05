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
                Type '$all' for all lessons. \r
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

    public static void main(String[] args) throws IOException {
        System.out.println(INTRO + "\n");
        while (true){
            System.out.println(SELECT_LESSON + "\n");
            String lesson = formattedRead();
            List<Vocabulary> vocabularies = selectLesson(lesson);
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
        String in;
        List<String> solution;
        Vocabulary vocabulary;
        do {
            System.out.println("Next vocabulary:\r");
            vocabulary = randomSelectVocabularyFrom(vocabularies);
            if ((int)(Math.random()*2)== 1){
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
            System.out.println("------------------------------------");
            loopCounter++;
        }while (loopCounter < limiter);
        System.out.printf("You got %s out of %s right! %n%n", correct, limiter);
    }

    /**
     * Reads a line from the console input and formats it into lowercase
     * @return lowercase String
     */
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
     * @return null if the user wants to exit. Otherwise, the user is recursively trapped until he makes a valid input. In this case the corresponding vocabulary is returned.
     */
    private static List<Vocabulary> selectLesson(String x) throws IOException {
        if (x.equals("$exit")){
            return null;
        }
        List<Vocabulary> vocabularies = new ArrayList<>();
        if (x.equals("$all")||x.isEmpty()){
            //TODO more
            getL7(vocabularies);
            getL8(vocabularies);
            getL9(vocabularies);
            getL10(vocabularies);
            getL11(vocabularies);
            getL12(vocabularies);
            return vocabularies;
        }
        int parsed = -1;
        try {
            parsed = Integer.parseInt(x);
        } catch (NumberFormatException _) {
            System.out.println("Did not read a number!\r\n");
        }
        switch (parsed){
            //TODO more
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

    private static void getL7(List<Vocabulary> vocabularies){
        // Japanese , English
        vocabularies.add(new Vocabulary(List.of("kirimasu"         ), List.of("cut", "slice", "schneiden")));
        vocabularies.add(new Vocabulary(List.of("okurimasu"        ), List.of("send", "senden")));
        vocabularies.add(new Vocabulary(List.of("agemasu"          ), List.of("give", "geben")));
        vocabularies.add(new Vocabulary(List.of("moraimasu"        ), List.of("receive", "erhalten")));
        vocabularies.add(new Vocabulary(List.of("kashimasu"        ), List.of("lend", "ausleihen")));
        vocabularies.add(new Vocabulary(List.of("karimasu"         ), List.of("borrow", "borgen")));
        vocabularies.add(new Vocabulary(List.of("oshiemasu"        ), List.of("teach", "lehren")));
        vocabularies.add(new Vocabulary(List.of("naraimasu"        ), List.of("learn", "lernen")));
        vocabularies.add(new Vocabulary(List.of("kakemasu denwa" ), List.of("make a phone call", "einen anruf machen")));
        vocabularies.add(new Vocabulary(List.of("te"               ), List.of("hand", "arm")));
        vocabularies.add(new Vocabulary(List.of("hashi"            ), List.of("chopsticks", "essstäbchen")));
        vocabularies.add(new Vocabulary(List.of("supuun"           ), List.of("spoon", "löffel")));
        vocabularies.add(new Vocabulary(List.of("naihu"            ), List.of("knife", "Messer")));
        vocabularies.add(new Vocabulary(List.of("fooku"            ), List.of("fork", "gabel")));
        vocabularies.add(new Vocabulary(List.of("hasami"           ), List.of("scissors", "schere")));
        vocabularies.add(new Vocabulary(List.of("pasokon"          ), List.of("personal computer", "pc")));
        vocabularies.add(new Vocabulary(List.of("panchi"           ), List.of("punch", "locher")));
        vocabularies.add(new Vocabulary(List.of("hocchikisu"       ), List.of("stapler", "tacker")));
        vocabularies.add(new Vocabulary(List.of("seroteipu"        ), List.of("scotch tape", "paket band")));
        vocabularies.add(new Vocabulary(List.of("keshigomu"        ), List.of("eraser", "radiergummi")));
        vocabularies.add(new Vocabulary(List.of("kami"             ), List.of("paper", "papier")));
        vocabularies.add(new Vocabulary(List.of("hana"             ), List.of("flower", "blume")));
        vocabularies.add(new Vocabulary(List.of("shatsu"           ), List.of("shirt", "hemd")));
        vocabularies.add(new Vocabulary(List.of("purezento"        ), List.of("present", "gift", "geschenk")));
        vocabularies.add(new Vocabulary(List.of("nimotsu"          ), List.of("baggage", "parcel", "paket")));
        vocabularies.add(new Vocabulary(List.of("okane"            ), List.of("money", "geld")));
        vocabularies.add(new Vocabulary(List.of("kippu"            ), List.of("ticket", "fahrkarte")));
        vocabularies.add(new Vocabulary(List.of("otoosan"          ), List.of("someone else's father", "vater einer anderen")));
        vocabularies.add(new Vocabulary(List.of("okaasan"          ), List.of("someone else's mother", "mutter einer anderen")));
        vocabularies.add(new Vocabulary(List.of("itadakimasu"      ), List.of("thank you (before eat/drink)", "mahlzeit! (aber höflicher)")));
        vocabularies.add(new Vocabulary(List.of("ryoko"            ), List.of("trip", "tour", "reise")));
        vocabularies.add(new Vocabulary(List.of("miyage", "omiyage"   ), List.of("souvenir", "present", "mitbringsel")));
    }

    private static void getL8(List<Vocabulary> vocabularies){
        // Japanese , English
        vocabularies.add(new Vocabulary(List.of("hansamu", "hansamu na"   ), List.of("handsome", "attraktiv", "gut aussehend")));
        vocabularies.add(new Vocabulary(List.of("kirei", "kirei na"   ), List.of("beautiful", "schön")));
        vocabularies.add(new Vocabulary(List.of("shizuka", "shizuka na"   ), List.of("quiet", "still", "leise")));
        vocabularies.add(new Vocabulary(List.of("nigiyaka", "nigiyaka na"   ), List.of("lively", "lebhaft", "rege")));
        vocabularies.add(new Vocabulary(List.of("yuumei", "yuumei na"   ), List.of("famous", "berühmt", "bekannt")));
        vocabularies.add(new Vocabulary(List.of("shinsetsu", "shinsetsu na"   ), List.of("kind", "freundlich")));
        vocabularies.add(new Vocabulary(List.of("genki", "genki na"   ), List.of("healthy", "cheerful", "freundlich", "heiter", "gesund")));
        vocabularies.add(new Vocabulary(List.of("hima", "hima na"   ), List.of("free", "free time", "freizeit")));
        vocabularies.add(new Vocabulary(List.of("benri", "benri na"   ), List.of("convenient", "praktisch")));
        vocabularies.add(new Vocabulary(List.of("suteki", "suteki na"   ), List.of("nice", "wonderful", "wundervoll")));
        vocabularies.add(new Vocabulary(List.of("ookii"), List.of("big", "large", "groß")));
        vocabularies.add(new Vocabulary(List.of("chiisai"), List.of("small", "little", "klein")));
        vocabularies.add(new Vocabulary(List.of("atarashii"), List.of("new", "neu")));
        vocabularies.add(new Vocabulary(List.of("furui"), List.of("old (not of age)", "alt")));
        vocabularies.add(new Vocabulary(List.of("ii", "iiyoi"), List.of("good", "gut")));
        vocabularies.add(new Vocabulary(List.of("warui"), List.of("bad", "schlecht")));
        vocabularies.add(new Vocabulary(List.of("atsui"), List.of("hot", "heiß")));
        vocabularies.add(new Vocabulary(List.of("samui"), List.of("cold (weather)", "kalt")));
        vocabularies.add(new Vocabulary(List.of("tsumetai"), List.of("cold (to the touch)", "kalt")));
        vocabularies.add(new Vocabulary(List.of("muzukashii"), List.of("difficult", "schwierig", "schwer")));
        vocabularies.add(new Vocabulary(List.of("yasashii"), List.of("easy", "einfach")));
        vocabularies.add(new Vocabulary(List.of("takai"), List.of("expensive", "teuer", "tall", "heigh", "hoch", "groß")));
        vocabularies.add(new Vocabulary(List.of("yasui"), List.of("cheap", "günstig")));
        vocabularies.add(new Vocabulary(List.of("hikui"), List.of("low", "niedrig")));
        vocabularies.add(new Vocabulary(List.of("omoshiroi"), List.of("interesting", "interessant")));
        vocabularies.add(new Vocabulary(List.of("oishii"), List.of("delicious", "lecker", "tasty")));
        vocabularies.add(new Vocabulary(List.of("isogashii"), List.of("busy", "beschäftigt")));
        vocabularies.add(new Vocabulary(List.of("tanoshii"), List.of("fun", "spaßig")));
        vocabularies.add(new Vocabulary(List.of("shiroi"), List.of("white", "weiß")));
        vocabularies.add(new Vocabulary(List.of("kuroi"), List.of("black", "schwarz")));
        vocabularies.add(new Vocabulary(List.of("akai"), List.of("red", "rot")));
        vocabularies.add(new Vocabulary(List.of("aoi"), List.of("blue", "blau")));
        vocabularies.add(new Vocabulary(List.of("sakura"), List.of("cherry", "cherry blossom", "kirsche", "kirschblüte")));
        vocabularies.add(new Vocabulary(List.of("yama"), List.of("mountain", "berg")));
        vocabularies.add(new Vocabulary(List.of("tabemono"), List.of("food", "essen")));
        vocabularies.add(new Vocabulary(List.of("kuruma"), List.of("car", "auto", "automobil")));
        vocabularies.add(new Vocabulary(List.of("benkyo"), List.of("study", "studieren", "lernen")));
        vocabularies.add(new Vocabulary(List.of("oshigoto", "shigoto"), List.of("work", "business", "arbeit")));
    }

    private static void getL9(List<Vocabulary> vocabularies){
        // Japanese , English
        vocabularies.add(new Vocabulary(List.of("wakarimasu"), List.of("understand", "verstehen")));
        vocabularies.add(new Vocabulary(List.of("suki", "suki na"), List.of("like", "mögen")));
        vocabularies.add(new Vocabulary(List.of("kirai", "kirai na"), List.of("dislike", "nicht mögen")));
        vocabularies.add(new Vocabulary(List.of("joozu", "joozu na"), List.of("good at", "gut in etwas sein")));
        vocabularies.add(new Vocabulary(List.of("heta", "heta na"), List.of("bad at", "schlecht in etwas sein")));
        vocabularies.add(new Vocabulary(List.of("ryori"), List.of("cooking", "cooked food", "kochen", "gekochtes essen")));
        vocabularies.add(new Vocabulary(List.of("nomimono"), List.of("drinks", "getränke")));
        vocabularies.add(new Vocabulary(List.of("supootsu"), List.of("sport")));
        vocabularies.add(new Vocabulary(List.of("yakyuu"), List.of("baseball")));
        vocabularies.add(new Vocabulary(List.of("ongaku"), List.of("music")));
        vocabularies.add(new Vocabulary(List.of("uta"), List.of("song")));
        vocabularies.add(new Vocabulary(List.of("karaoke"), List.of("karaoke")));
        vocabularies.add(new Vocabulary(List.of("e"), List.of("picture", "drawing", "bild", "malen")));
        vocabularies.add(new Vocabulary(List.of("komakai okane"), List.of("small change", "kleingeld")));
        vocabularies.add(new Vocabulary(List.of("chiketto"), List.of("ticket")));
        vocabularies.add(new Vocabulary(List.of("goshujin"), List.of("someone else's husband", "ehemann einer anderen")));
        vocabularies.add(new Vocabulary(List.of("shujin"), List.of("my husband", "mein ehemann")));
        vocabularies.add(new Vocabulary(List.of("okusan"), List.of("someone else's wife", "ehefrau einer anderen")));
        vocabularies.add(new Vocabulary(List.of("tsuma"), List.of("my wife", "meine ehefrau")));
        vocabularies.add(new Vocabulary(List.of("wakarimasu"), List.of("verstehen", "understand")));
        vocabularies.add(new Vocabulary(List.of("arimasu"), List.of("haben", "besitzen", "own")));
        vocabularies.add(new Vocabulary(List.of("sukoshi"), List.of("a little", "ein bisschen", "ein wenig")));
        vocabularies.add(new Vocabulary(List.of("daitai"), List.of("ungefähr", "aproximately", "ungefähr")));
        vocabularies.add(new Vocabulary(List.of("yoku"), List.of("often", "oft", "frequently")));
        vocabularies.add(new Vocabulary(List.of("sukoshi"), List.of("a little", "ein bisschen", "ein wenig")));
        vocabularies.add(new Vocabulary(List.of("takusan"), List.of("viel", "lots", "plenty")));
        vocabularies.add(new Vocabulary(List.of("amari"), List.of("nicht so viel", "not so much")));
        vocabularies.add(new Vocabulary(List.of("zenzen"), List.of("gar nicht", "not at all")));

    }

    private static void getL10(List<Vocabulary> vocabularies){
        // Japanese , English
        vocabularies.add(new Vocabulary(List.of("imasu"), List.of("exist (animate)", "be", "existieren", "sein")));
        vocabularies.add(new Vocabulary(List.of("arimasu"), List.of("exist (inanimate)", "existieren", "sein")));
        vocabularies.add(new Vocabulary(List.of("iroiro", "iroiro na"), List.of("various", "verschiedene")));
        vocabularies.add(new Vocabulary(List.of("otokonohito"), List.of("man", "mann")));
        vocabularies.add(new Vocabulary(List.of("onnanohito"), List.of("woman", "frau")));
        vocabularies.add(new Vocabulary(List.of("takusan"), List.of("viel", "lots", "plenty")));
        vocabularies.add(new Vocabulary(List.of("otokonoko"), List.of("boy", "junge")));
        vocabularies.add(new Vocabulary(List.of("onnanonoko"), List.of("girl", "mädchen")));
        vocabularies.add(new Vocabulary(List.of("inu"), List.of("dog", "hund")));
        vocabularies.add(new Vocabulary(List.of("neko"), List.of("cat", "katze")));
        vocabularies.add(new Vocabulary(List.of("ki"), List.of("tree", "wood", "baum", "holz")));
        vocabularies.add(new Vocabulary(List.of("denchi"), List.of("battery", "batterie")));
        vocabularies.add(new Vocabulary(List.of("hako"), List.of("box")));
        vocabularies.add(new Vocabulary(List.of("suicchi"), List.of("switch", "schalter")));
        vocabularies.add(new Vocabulary(List.of("reizooko"), List.of("refrigerator", "kühlschrank")));
        vocabularies.add(new Vocabulary(List.of("teiburu"), List.of("table", "tisch")));
        vocabularies.add(new Vocabulary(List.of("beddo"), List.of("bed", "bett")));
        vocabularies.add(new Vocabulary(List.of("otokonoko"), List.of("boy", "junge")));
        vocabularies.add(new Vocabulary(List.of("tana"), List.of("shelf", "regal")));
        vocabularies.add(new Vocabulary(List.of("doa"), List.of("door", "tür")));
        vocabularies.add(new Vocabulary(List.of("mado"), List.of("window", "fenster")));
        vocabularies.add(new Vocabulary(List.of("posuto"), List.of("post", "postkasten")));
        vocabularies.add(new Vocabulary(List.of("biru"), List.of("building", "gebäude")));
        vocabularies.add(new Vocabulary(List.of("kooen"), List.of("park")));
        vocabularies.add(new Vocabulary(List.of("kissatan"), List.of("coffee shop", "cafe")));
        vocabularies.add(new Vocabulary(List.of("honya"), List.of("book store", "buchladen")));
        vocabularies.add(new Vocabulary(List.of("noriba"), List.of("haltestelle für taxi", "haltestelle für zug", "haltestelle für bus",
                "place to catch taxi", "place to catch train", "place to catch bus")));
        vocabularies.add(new Vocabulary(List.of("ue"), List.of("on", "above", "auf", "darüber")));
        vocabularies.add(new Vocabulary(List.of("shita"), List.of("under", "below", "darunter", "unter")));
        vocabularies.add(new Vocabulary(List.of("mae"), List.of("front", "before", "davor")));
        vocabularies.add(new Vocabulary(List.of("ushiro"), List.of("back", "behind", "dahinter", "hinter")));
        vocabularies.add(new Vocabulary(List.of("migi"), List.of("right", "rechts")));
        vocabularies.add(new Vocabulary(List.of("hidari"), List.of("left", "links")));
        vocabularies.add(new Vocabulary(List.of("naka"), List.of("in", "inside", "innen", "in")));
        vocabularies.add(new Vocabulary(List.of("soto"), List.of("outside", "draußen", "außen")));
        vocabularies.add(new Vocabulary(List.of("tonari"), List.of("next", "daneben")));
        vocabularies.add(new Vocabulary(List.of("chikaku"), List.of("near", "nahe", "in der nähe")));
        vocabularies.add(new Vocabulary(List.of("aida"), List.of("between", "dazwischen")));
    }
    private static void getL11(List<Vocabulary> vocabularies){
        // Japanese , English
        vocabularies.add(new Vocabulary(List.of("imasu kodomoga"), List.of("have a child", "kind haben")));
        vocabularies.add(new Vocabulary(List.of("imasu nihonni"), List.of("stay in japan", "in japan bleiben")));
        vocabularies.add(new Vocabulary(List.of("kakarimasu okanega"), List.of("take money", "geld nehmen")));
        vocabularies.add(new Vocabulary(List.of("kakarimasu jikanga"), List.of("take time", "zeit nehmen", "zeit brauchen")));
        vocabularies.add(new Vocabulary(List.of("yasumimasu kaishawo"), List.of("take a day off", "einen tag frei nehmen")));
        vocabularies.add(new Vocabulary(List.of("ringo"), List.of("apple", "apfel")));
        vocabularies.add(new Vocabulary(List.of("mikan"), List.of("orange")));
        vocabularies.add(new Vocabulary(List.of("karei raisu"), List.of("curry and rice", "curry und reis")));
        vocabularies.add(new Vocabulary(List.of("kitte"), List.of("postage stamp", "briefmarke")));
        vocabularies.add(new Vocabulary(List.of("hagaki"), List.of("post card", "postkarte")));
        vocabularies.add(new Vocabulary(List.of("futoo"), List.of("envelope", "briefumschlag")));
        vocabularies.add(new Vocabulary(List.of("sokutatsu"), List.of("special delivery", "sonderlieferung")));
        vocabularies.add(new Vocabulary(List.of("kakitome"), List.of("registered mail", "einschreiben")));
        vocabularies.add(new Vocabulary(List.of("kookuubin"), List.of("airmail", "luftpost")));
        vocabularies.add(new Vocabulary(List.of("funabin"), List.of("sea mail", "seepost")));
        vocabularies.add(new Vocabulary(List.of("ryooshin"), List.of("parents", "eltern")));
        vocabularies.add(new Vocabulary(List.of("kyoodai"), List.of("brothers and sisters", "brüder und schwestern")));
        vocabularies.add(new Vocabulary(List.of("oniisan"), List.of("someone else's older brother", "älterer bruder einer anderen")));
        vocabularies.add(new Vocabulary(List.of("oneisan"), List.of("someone else's older sister", "ältere schwester einer anderen")));
        vocabularies.add(new Vocabulary(List.of("otootosan"), List.of("someone else's younger brother", "jüngerer bruder einer anderen")));
        vocabularies.add(new Vocabulary(List.of("imootosan"), List.of("someone else's younger sister", "jüngere schwester einer anderen")));
    }
    private static void getL12(List<Vocabulary> vocabularies){
        // Japanese , English
        vocabularies.add(new Vocabulary(List.of("kantan na"), List.of("easy", "einfach")));
        vocabularies.add(new Vocabulary(List.of("chikai"), List.of("near", "nah")));
        vocabularies.add(new Vocabulary(List.of("tooi"), List.of("far", "weit")));
        vocabularies.add(new Vocabulary(List.of("hayai"), List.of("early", "früh", "fast", "schnell")));
        vocabularies.add(new Vocabulary(List.of("osoi"), List.of("late", "spät", "langsam", "slow")));
        vocabularies.add(new Vocabulary(List.of("ooi"), List.of("many", "much", "viel")));
        vocabularies.add(new Vocabulary(List.of("sukunai"), List.of("few", "a little", "wenig")));
        vocabularies.add(new Vocabulary(List.of("atatakai"), List.of("warm")));
        vocabularies.add(new Vocabulary(List.of("suzushii"), List.of("cool", "kalt")));
        vocabularies.add(new Vocabulary(List.of("amai"), List.of("sweet", "süß")));
        vocabularies.add(new Vocabulary(List.of("karai"), List.of("spicy", "scharf")));
        vocabularies.add(new Vocabulary(List.of("omoi"), List.of("heavy", "schwer")));
        vocabularies.add(new Vocabulary(List.of("karui"), List.of("light", "leicht")));
        vocabularies.add(new Vocabulary(List.of("kisetsu"), List.of("season", "jahreszeit")));
        vocabularies.add(new Vocabulary(List.of("haru"), List.of("spring", "frühling")));
        vocabularies.add(new Vocabulary(List.of("natsu"), List.of("summer", "sommer")));
        vocabularies.add(new Vocabulary(List.of("aki"), List.of("autumn", " fall", "herbst")));
        vocabularies.add(new Vocabulary(List.of("fuyu"), List.of("winter")));
        vocabularies.add(new Vocabulary(List.of("tenki"), List.of("weather", "wetter")));
        vocabularies.add(new Vocabulary(List.of("ame"), List.of("rain", "regen")));
        vocabularies.add(new Vocabulary(List.of("yuki"), List.of("schnee", "snow")));
        vocabularies.add(new Vocabulary(List.of("kumori"), List.of("cloudy", "bewölkt")));
        vocabularies.add(new Vocabulary(List.of("hoteru"), List.of("hotel")));
        vocabularies.add(new Vocabulary(List.of("kuukoo"), List.of("airport", "flughafen")));
        vocabularies.add(new Vocabulary(List.of("umi"), List.of("meer", "sea", "ocean")));
        vocabularies.add(new Vocabulary(List.of("paateii"), List.of("partey", "feier")));
        vocabularies.add(new Vocabulary(List.of("omatsuri", "matsuri"), List.of("festival", "fest")));
        vocabularies.add(new Vocabulary(List.of("shiken"), List.of("examination", "examen")));
        vocabularies.add(new Vocabulary(List.of("sukiyaki"), List.of("sukiyaki")));
        vocabularies.add(new Vocabulary(List.of("sashimi"), List.of("sashimi", "roher fisch zum essen")));
        vocabularies.add(new Vocabulary(List.of("osushi", "sushi"), List.of("sushi")));
        vocabularies.add(new Vocabulary(List.of("tempura"), List.of("tempura")));
        vocabularies.add(new Vocabulary(List.of("momiji"), List.of("maple", "ahorn")));
    }
    private static void getL13(List<Vocabulary> vocabularies){
        // Japanese , English
    }
    private static void getL14(List<Vocabulary> vocabularies){
        // Japanese , English
    }


}
