package core;

import lombok.experimental.UtilityClass;
import model.Vocabulary;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

@UtilityClass
public class LessonLoader {
    private static final Map<Integer, List<Vocabulary>> lessons = new HashMap<>();
    private static final String CSV_COMMA_DELIMITER = ",";
    static {
        lessons.put(0,null);
        lessons.put(1,null);
        lessons.put(2,null);
        lessons.put(3,null);
        lessons.put(4,null);
        lessons.put(5,null);
        wrappedPut(6, "l6");
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
     */
    private static List<Vocabulary> getCSVVocabulary(String name) throws IOException {
        BufferedReader br = ReaderUtility.getReader("lessons/%s.csv".formatted(name));
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
}
