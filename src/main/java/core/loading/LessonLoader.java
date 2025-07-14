package core.loading;

import model.Vocabulary;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class LessonLoader {
    private static final Map<Integer, List<Vocabulary>> lessons = new HashMap<>();
    static {
        lessons.put(0,new ArrayList<>());
        lessons.put(1,new ArrayList<>());
        lessons.put(2,new ArrayList<>());
        lessons.put(3,new ArrayList<>());
        lessons.put(4,new ArrayList<>());
        lessons.put(5,new ArrayList<>());
        //TODO cursed csv structure
        loadLesson(6);
        loadLesson(7);
        loadLesson(8);
        loadLesson(9);
        loadLesson(10);
        loadLesson(11);
        loadLesson(12);
    }

    /**
     * Loads lessons into the map
     * @param i number of the lesson
     */
    private static void loadLesson(int i){
        String path = "vocabularies/lessons/l%s.csv".formatted(i);
        try {
            lessons.put(i, new VocabularyLoader(path).loadStandardFormat());
        } catch (IOException e) {
            Logger.getLogger("LessonSelector").warning("Error loading lesson '%s'.".formatted(i));
        }
    }

    /**
     * Gets all vocabularies defined in the lessons Map
     * @return a List of all vocabularies
     */
    protected static List<Vocabulary> getAll(){
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (int i = 0; i < lessons.size(); i++) {
            vocabularies.addAll(getVocabulary(i));
        }
        return vocabularies;
    }

    protected static List<Vocabulary> getRange(int[] range){
        if (range.length >= 2){
            return getRange(range[0], range[1]);
        }else if (range.length == 1){
            return getRange(range[0]);
        }else {
            Logger.getLogger("LessonLoader").warning("Invalid Range. Added empty list");
            return new ArrayList<>();
        }
    }

    /**
     * Gets all vocabularies of the lessons within the range
     * @param lower lower bound of the lessons
     * @param upper upper bound of the lessons
     * @return List of vocabularies
     */
    protected static List<Vocabulary> getRange(int lower, int upper){
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (int i = lower; i < upper+1; i++) {
            if (lessons.get(i)!=null){
                List<Vocabulary> tmp = getVocabulary(i);
                if (!tmp.isEmpty()){
                    vocabularies.addAll(getVocabulary(i));
                    Logger.getLogger("LessonLoader").info("Lesson %s selected".formatted(i));
                }
            }
        }
        return vocabularies;
    }

    /**
     * Gets all vocabularies of the lessons within the range. The upper bound is the max size of all lessons
     * @param lower lower bound of the lessons
     * @return List of vocabularies
     */
    protected static List<Vocabulary> getRange(int lower){
        return getRange(lower, lessons.size());
    }

    /**
     * Gets the vocabulary for a specific lesson
     * @param lesson number of the lesson
     * @return null if lesson does not exist
     */
    protected static List<Vocabulary> getVocabulary(int lesson){
        if (lessons.size() < lesson){
            Logger.getLogger("LessonLoader").warning("Lesson '%s' does not exist. It was skipped.".formatted(lesson));
            return new ArrayList<>();
        }
        return lessons.get(lesson);
    }
}
