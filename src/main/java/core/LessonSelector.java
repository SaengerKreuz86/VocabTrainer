package core;

import lombok.experimental.UtilityClass;
import model.Vocabulary;
import model.VocabularyBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class LessonSelector {
    private static final Map<Integer, List<Vocabulary>> lessons = new HashMap<>();
    static {
        lessons.put(0,null);
        lessons.put(1,null);
        lessons.put(2,null);
        lessons.put(3,null);
        lessons.put(4,null);
        lessons.put(5,null);
        lessons.put(6,null);
        lessons.put(7, getL7());
        lessons.put(8, getL8());
        lessons.put(9, getL9());
        lessons.put(10, getL10());
        lessons.put(11, getL11());
        lessons.put(12, getL12());
    }

    /**
     * Gets all vocabularies defined in the lessons Map
     * @return a List of all vocabularies
     */
    public static List<Vocabulary> getAll(){
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (Map.Entry<Integer, List<Vocabulary>> entry: lessons.entrySet()){
            if (entry.getValue() != null){
                vocabularies.addAll(entry.getValue());
            }
        }
        return vocabularies;
    }

    /**
     * Gets all vocabularies of the lessons within the range
     * @param lower lower bound of the lessons
     * @param upper upper bound of the lessons
     * @return List of vocabularies
     */
    public static List<Vocabulary> getRange(int lower, int upper){
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (int i = lower; i < upper+1; i++) {
            if (lessons.get(i)!=null){
                vocabularies.addAll(lessons.get(i));
            }
        }
        return vocabularies;
    }

    /**
     * Gets all vocabularies of the lessons within the range. The upper bound is the max size of all lessons
     * @param lower lower bound of the lessons
     * @return List of vocabularies
     */
    public static List<Vocabulary> getRange(int lower){
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (int i = lower; i < lessons.size()+1; i++) {
            if (lessons.get(i)!=null){
                vocabularies.addAll(lessons.get(i));
            }
        }
        return vocabularies;
    }

    /**
     * Gets the vocabulary for a specific lesson
     * @param lesson number of the lesson
     * @return null if lesson does not exist
     */
    public static List<Vocabulary> getVocabulary(int lesson){
        return lessons.get(lesson);
    }

    /**
     * Gets vocabulary of multiple lessons specified in the list
     * @param queriedLessons List of lessons
     * @return List of all vocabularies
     */
    public static List<Vocabulary> getVocabulary(List<Integer> queriedLessons){
        List<Vocabulary> out = new ArrayList<>();
        for (int i : queriedLessons){
            if (lessons.get(i)!= null){
                out.addAll(lessons.get(i));
            }
        }
        return out;
    }

    /**
     * Adds a vocabulary to a given List
     * @param vocabularies List of vocabularies
     * @param jap single japanese meaning
     * @param meanings translations into english/german of the japanese single one
     */
    private static void addSingle(List<Vocabulary> vocabularies, String jap, String... meanings){
        vocabularies.add(new VocabularyBuilder().buildSingleJapanese(jap, meanings));
    }
    /**
     * Adds a vocabulary to a given List
     * @param vocabularies List of vocabularies
     * @param jap1 first meaning of the japanese ones
     * @param jap2 second meaning of the japanese ones
     * @param meanings translations into english/german of the japanese words
     */
    private static void addDouble(List<Vocabulary> vocabularies, String jap1, String jap2, String... meanings){
        vocabularies.add(new VocabularyBuilder().buildDoubleJapanese(jap1, jap2, meanings));
    }

    private static List<Vocabulary> getL7(){
        List<Vocabulary> vocabularies = new ArrayList<>();
        // Japanese , English
        addSingle(vocabularies, "kirimasu", "cut", "slice", "schneiden");
        vocabularies.add(new Vocabulary(List.of("okurimasu"        ), List.of("send", "senden")));
        vocabularies.add(new Vocabulary(List.of("agemasu"          ), List.of("give", "geben")));
        vocabularies.add(new Vocabulary(List.of("moraimasu"        ), List.of("receive", "erhalten")));
        vocabularies.add(new Vocabulary(List.of("kashimasu"        ), List.of("lend", "ausleihen")));
        vocabularies.add(new Vocabulary(List.of("karimasu"         ), List.of("borrow", "borgen")));
        vocabularies.add(new Vocabulary(List.of("oshiemasu"        ), List.of("teach", "lehren")));
        vocabularies.add(new Vocabulary(List.of("naraimasu"        ), List.of("learn", "lernen")));
        vocabularies.add(new Vocabulary(List.of("kakemasu denwa"   ), List.of("make a phone call", "einen anruf machen")));
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
        return vocabularies;
    }

    private static List<Vocabulary> getL8(){
        // Japanese , English
        List<Vocabulary> vocabularies = new ArrayList<>();
        vocabularies.add(new Vocabulary(List.of("hansamu", "hansamu na"     ), List.of("handsome", "attraktiv", "gut aussehend")));
        vocabularies.add(new Vocabulary(List.of("kirei", "kirei na"         ), List.of("beautiful", "schön")));
        vocabularies.add(new Vocabulary(List.of("shizuka", "shizuka na"     ), List.of("quiet", "still", "leise")));
        vocabularies.add(new Vocabulary(List.of("nigiyaka", "nigiyaka na"   ), List.of("lively", "lebhaft", "rege")));
        vocabularies.add(new Vocabulary(List.of("yuumei", "yuumei na"       ), List.of("famous", "berühmt", "bekannt")));
        vocabularies.add(new Vocabulary(List.of("shinsetsu", "shinsetsu na" ), List.of("kind", "freundlich")));
        vocabularies.add(new Vocabulary(List.of("genki", "genki na"         ), List.of("healthy", "cheerful", "freundlich", "heiter", "gesund")));
        vocabularies.add(new Vocabulary(List.of("hima", "hima na"           ), List.of("free", "free time", "freizeit")));
        vocabularies.add(new Vocabulary(List.of("benri", "benri na"         ), List.of("convenient", "praktisch")));
        vocabularies.add(new Vocabulary(List.of("suteki", "suteki na"       ), List.of("nice", "wonderful", "wundervoll")));
        vocabularies.add(new Vocabulary(List.of("ookii"                  ), List.of("big", "large", "groß")));
        vocabularies.add(new Vocabulary(List.of("chiisai"                ), List.of("small", "little", "klein")));
        vocabularies.add(new Vocabulary(List.of("atarashii"              ), List.of("new", "neu")));
        vocabularies.add(new Vocabulary(List.of("furui"                  ), List.of("old (not of age)", "alt")));
        vocabularies.add(new Vocabulary(List.of("ii", "iiyoi"               ), List.of("good", "gut")));
        vocabularies.add(new Vocabulary(List.of("warui"                  ), List.of("bad", "schlecht")));
        vocabularies.add(new Vocabulary(List.of("atsui"                  ), List.of("hot", "heiß")));
        vocabularies.add(new Vocabulary(List.of("samui"                  ), List.of("cold (weather)", "kalt")));
        vocabularies.add(new Vocabulary(List.of("tsumetai"               ), List.of("cold (to the touch)", "kalt")));
        vocabularies.add(new Vocabulary(List.of("muzukashii"             ), List.of("difficult", "schwierig", "schwer")));
        vocabularies.add(new Vocabulary(List.of("yasashii"               ), List.of("easy", "einfach")));
        vocabularies.add(new Vocabulary(List.of("takai"                  ), List.of("expensive", "teuer", "tall", "heigh", "hoch", "groß")));
        vocabularies.add(new Vocabulary(List.of("yasui"                  ), List.of("cheap", "günstig")));
        vocabularies.add(new Vocabulary(List.of("hikui"                  ), List.of("low", "niedrig")));
        vocabularies.add(new Vocabulary(List.of("omoshiroi"              ), List.of("interesting", "interessant")));
        vocabularies.add(new Vocabulary(List.of("oishii"                 ), List.of("delicious", "lecker", "tasty")));
        vocabularies.add(new Vocabulary(List.of("isogashii"              ), List.of("busy", "beschäftigt")));
        vocabularies.add(new Vocabulary(List.of("tanoshii"               ), List.of("fun", "spaßig")));
        vocabularies.add(new Vocabulary(List.of("shiroi"                 ), List.of("white", "weiß")));
        vocabularies.add(new Vocabulary(List.of("kuroi"                  ), List.of("black", "schwarz")));
        vocabularies.add(new Vocabulary(List.of("akai"                   ), List.of("red", "rot")));
        vocabularies.add(new Vocabulary(List.of("aoi"                    ), List.of("blue", "blau")));
        vocabularies.add(new Vocabulary(List.of("sakura"                 ), List.of("cherry", "cherry blossom", "kirsche", "kirschblüte")));
        vocabularies.add(new Vocabulary(List.of("yama"                   ), List.of("mountain", "berg")));
        vocabularies.add(new Vocabulary(List.of("tabemono"               ), List.of("food", "essen")));
        vocabularies.add(new Vocabulary(List.of("kuruma"                 ), List.of("car", "auto", "automobil")));
        vocabularies.add(new Vocabulary(List.of("benkyo"                 ), List.of("study", "studieren", "lernen")));
        vocabularies.add(new Vocabulary(List.of("oshigoto", "shigoto"       ), List.of("work", "business", "arbeit")));
        return vocabularies;
    }

    private static List<Vocabulary> getL9(){
        // Japanese , English
        List<Vocabulary> vocabularies = new ArrayList<>();
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
        return vocabularies;
    }

    private static List<Vocabulary> getL10(){
        // Japanese , English
        List<Vocabulary> vocabularies = new ArrayList<>();
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
        vocabularies.add(new Vocabulary(List.of("kooen"), List.of("park", "parc")));
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
        return vocabularies;
    }
    private static List<Vocabulary> getL11(){
        // Japanese , English
        List<Vocabulary> vocabularies = new ArrayList<>();
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
        return vocabularies;
    }
    private static List<Vocabulary> getL12(){
        // Japanese , English
        List<Vocabulary> vocabularies = new ArrayList<>();
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
        return vocabularies;
    }
}
