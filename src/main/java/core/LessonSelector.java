package core;

import lombok.experimental.UtilityClass;
import model.Vocabulary;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

@UtilityClass
public class LessonSelector {
    private static final Map<Integer, List<Vocabulary>> lessons = new HashMap<>();
    private static final String CSV_COMMA_DELIMITER = ",";
    static {
        lessons.put(0,null);
        lessons.put(1,null);
        lessons.put(2,null);
        lessons.put(3,null);
        lessons.put(4,null);
        lessons.put(5,null);
        lessons.put(6,null);
        wrappedPut(7, "l7");
        wrappedPut(8, "l8");
        wrappedPut(9, "l9");
        wrappedPut(10, "l10");
        wrappedPut(11, "l11");
        wrappedPut(12, "l12");
    }

    /**
     * Adds another entry to the map but skips it if it produces an error
     * @param id id of the lesson
     * @param csvName name of the file. the ending '.csv' must be excluded
     */
    private static void wrappedPut(int id, String csvName){
        try {
            lessons.put(id, getCSVVocabulary(csvName));
        } catch (IOException e) {
            Logger.getLogger("LessonSelector").warning("Error putting lesson %s with csv name %s".formatted(id, csvName));
        }
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
    public static List<Vocabulary> getVocabularyByLessons(List<Integer> queriedLessons){
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (int i : queriedLessons){
            if (lessons.get(i)!= null){
                vocabularies.addAll(lessons.get(i));
            }
        }
        return vocabularies;
    }

    /**
     * The format of a line in the csv is: id,japaneseWord,meaning1,meaning2,...
     * If there are multiple japanese words within one file that have the same meaning they must have the same id.
     * @param name name of the file without the ending '.csv'
     * @return List of Vocabularies that the file contains
     * @throws IOException
     */
    private static List<Vocabulary> getCSVVocabulary(String name) throws IOException {
        InputStream is = LessonSelector.class.getClassLoader().getResourceAsStream("lessons/%s.csv".formatted(name));
        BufferedReader br;
        if (is != null) {
            br = new BufferedReader(new InputStreamReader(is));
        }else {
            return new ArrayList<>();
        }
        String line;
        Map<Integer, Vocabulary> idVoc= new HashMap<>();
        while ((line = br.readLine()) != null){
            // csv format:
            // id,jap,meaning1,meaning2,...
            String[] values = line.split(CSV_COMMA_DELIMITER);
            int id = getId(values[0]);
            if (id <= -1){
                continue;
            }
            if (idVoc.get(id)== null){
                idVoc.put(id, new Vocabulary(
                                new ArrayList<>(Collections.singleton(values[1])),
                                new ArrayList<>(Arrays.asList(values).subList(2, values.length)))
                );
            }else {
                if (!idVoc.get(id).getJapanese().contains(values[1])){
                    List<String> mutable = new ArrayList<>(idVoc.get(id).getJapanese());
                    mutable.add(values[1]);
                    idVoc.get(id).setJapanese(mutable);
                }
                List<String> englishGerman = idVoc.get(id).getEnglishGerman();
                for (int i = 2; i < values.length; i++) {
                    if (!englishGerman.contains(values[i])){
                        englishGerman.add(values[i]);
                    }
                }
                idVoc.get(id).setEnglishGerman(englishGerman);
            }
        }
        return idVoc.values().stream().toList();
    }

    /**
     *
     * @param s String that will be parsed
     * @return the specified id in the csv. Returns -1 if the id was malformed
     */
    private int getId(String s){
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            Logger.getLogger("LessonSelector").warning("Malformed csv line processed.");
            return -1;
        }
    }


    /* Template block
    private static void getLX(List<Vocabulary> vocabularies) {
        vocabularies.add(new Vocabulary(List.of("romanji"), List.of("eng", "ger")));
    }
     */

    private static void getL6(List<Vocabulary> vocabularies) {
        vocabularies.add(new Vocabulary(List.of("tabemasu"), List.of("eat", "essen")));
        vocabularies.add(new Vocabulary(List.of("nomimasu"), List.of("drink", "trinken")));
        vocabularies.add(new Vocabulary(List.of("suimasu", "suimasu (tabakowo)"), List.of("smoke (a cigarette)", "(eine Zigarette) rauchen)")));
        vocabularies.add(new Vocabulary(List.of("mimasu"), List.of("see", "look at", "watch", "sehen", "(an) schauen")));
        vocabularies.add(new Vocabulary(List.of("kikimasu"), List.of("hear", "listen", "(zu)höhren")));
        vocabularies.add(new Vocabulary(List.of("yomimasu"), List.of("read", "lesen")));
        vocabularies.add(new Vocabulary(List.of("kakimasu"), List.of("write", "draw", "paint", "schreiben")));
        vocabularies.add(new Vocabulary(List.of("kaimasu"), List.of("buy", "kaufen")));
        vocabularies.add(new Vocabulary(List.of("torimasu", "torimasu (shashinwo)"), List.of("take (a photo)", "(ein Foto) machen")));
        vocabularies.add(new Vocabulary(List.of("aimasu", "aimasu (tomodachi ni)"), List.of("meet", "see (a friend)", "(einen Freund) treffen")));
        vocabularies.add(new Vocabulary(List.of("gohan"), List.of("a meal", "cooked rice", "eine Mahlzeit", "gekochter Reis")));
        vocabularies.add(new Vocabulary(List.of("asagohan"), List.of("breakfast", "Frühstück")));
        vocabularies.add(new Vocabulary(List.of("hirugohan"), List.of("lunch", "Mittagessen")));
        vocabularies.add(new Vocabulary(List.of("bangohan"), List.of("dinner", "Abendessen")));
        vocabularies.add(new Vocabulary(List.of("pan"), List.of("bread", "Brot")));
        vocabularies.add(new Vocabulary(List.of("tamago"), List.of("egg", "Ei")));
        vocabularies.add(new Vocabulary(List.of("niku"), List.of("meat", "Fleisch")));
        vocabularies.add(new Vocabulary(List.of("gyuniku"), List.of("beef", "Rindfleisch")));
        vocabularies.add(new Vocabulary(List.of("butaniku"), List.of("pork", "Schweinefleisch")));
        vocabularies.add(new Vocabulary(List.of("toriniku"), List.of("chicken", "Hühnerfleisch", "Geflügelfleisch")));
        vocabularies.add(new Vocabulary(List.of("sakana"), List.of("fish", "Fisch")));
        vocabularies.add(new Vocabulary(List.of("yasai"), List.of("vegetable", "Gemüse")));
        vocabularies.add(new Vocabulary(List.of("kudamono"), List.of("fruit", "Obst")));
        vocabularies.add(new Vocabulary(List.of("mizu"), List.of("water", "Wasser")));
        vocabularies.add(new Vocabulary(List.of("ocha"), List.of("tea", "green tea", "(grüner) Tee")));
        vocabularies.add(new Vocabulary(List.of("kocha"), List.of("black tea", "schwarzer Tee")));
        vocabularies.add(new Vocabulary(List.of("gyunyu"), List.of("milk", "Milch")));
        vocabularies.add(new Vocabulary(List.of("jusu"), List.of("juice", "Saft")));
        vocabularies.add(new Vocabulary(List.of("biru"), List.of("beer", "bier")));
        vocabularies.add(new Vocabulary(List.of("osake", "sake"), List.of("alcohol", "Alkohol")));
        vocabularies.add(new Vocabulary(List.of("ega"), List.of("movie", "Film")));
        vocabularies.add(new Vocabulary(List.of("shidi"), List.of("CD")));
        vocabularies.add(new Vocabulary(List.of("tegami"), List.of("letter", "Brief")));
        vocabularies.add(new Vocabulary(List.of("shashin"), List.of("photograph", "Fotografie")));
        vocabularies.add(new Vocabulary(List.of("mise"), List.of("store", "shop", "Gschäft", "Laden")));
        vocabularies.add(new Vocabulary(List.of("resutoran"), List.of("resturant", "Resturant")));
        vocabularies.add(new Vocabulary(List.of("shukudai"), List.of("homework", "Hausaufgaben")));
        vocabularies.add(new Vocabulary(List.of("tenisu"), List.of("tennis", "Tennis")));
        vocabularies.add(new Vocabulary(List.of("sakka"), List.of("soccer", "Fußball")));
    }
}
