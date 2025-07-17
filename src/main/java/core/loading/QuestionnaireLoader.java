package core.loading;

import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Questionnaire;
import model.Vocabulary;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class QuestionnaireLoader {
    private final List<Vocabulary> vocabularies = new ArrayList<>();
    @Setter
    private String info = "Only god can help you.";

    /**
     *
     * @return current state of the list
     */
    public Questionnaire collect(){
        return new Questionnaire(vocabularies, info);
    }

    /**
     * Removes the specified vocabularies from the whole list
     * @param cut These will be removed
     * @return List that does not include the vocabularies of cut
     */
    public QuestionnaireLoader cut(List<Vocabulary> cut){
        cut.forEach(this.vocabularies::remove);
        return this;
    }

    /**
     * Empties the vocabulary list
     * @return this instance with empty vocabulary list
     */
    public QuestionnaireLoader clear(){
        vocabularies.clear();
        return this;
    }

    //----------------- LESSONS ------------------------//
    public QuestionnaireLoader loadAllLessons(){
        vocabularies.addAll(LessonLoader.getAll());
        return this;
    }

    public QuestionnaireLoader loadLesson(int i){
        vocabularies.addAll(LessonLoader.getVocabulary(i));

        return this;
    }

    public QuestionnaireLoader loadRangeLessons(int[] range){
        vocabularies.addAll(LessonLoader.getRange(range));
        return this;
    }

    //----------------- COUNTER ------------------------//
    public QuestionnaireLoader loadAllCounter(){
        vocabularies.addAll(ThemeLoader.getCounters());
        return this;
    }
    public QuestionnaireLoader loadCounterByName(String name){
        vocabularies.addAll(ThemeLoader.getCounterByName(name));
        return this;
    }
    public QuestionnaireLoader loadBookCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("book"));
        return this;
    }
    public QuestionnaireLoader loadAgeCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("age"));
        return this;
    }
    public QuestionnaireLoader loadFloorsOfBuildingsCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("floors_of_buildings"));
        return this;
    }public QuestionnaireLoader loadFrequencyCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("frequency"));
        return this;
    }
    public QuestionnaireLoader loadGeneralCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("general"));
        return this;
    }
    public QuestionnaireLoader loadGlassesCupsCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("glasses_cups"));
        return this;
    }
    public QuestionnaireLoader loadLongSlenderCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("long_slender"));
        return this;
    }
    public QuestionnaireLoader loadMachinesVehiclesCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("machines_vehicles"));
        return this;
    }
    public QuestionnaireLoader loadOrderCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("order"));
        return this;
    }public QuestionnaireLoader loadPeopleCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("people"));
        return this;
    }public QuestionnaireLoader loadSmallCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("small"));
        return this;
    }
    public QuestionnaireLoader loadThinFlatCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("thin_flat"));
        return this;
    }
    //------------------- DAYS ---------------------------------------//
    public QuestionnaireLoader loadDays(){
        vocabularies.addAll(ThemeLoader.getDays());
        return this;
    }
    public QuestionnaireLoader loadWeekDays(){
        vocabularies.addAll(ThemeLoader.getWeek());
        return this;
    }
    public QuestionnaireLoader loadMonthDays(){
        vocabularies.addAll(ThemeLoader.getMonth());
        return this;
    }
    //------------------- POSITIONS ------------------------------------//
    public QuestionnaireLoader loadPositions(){
        vocabularies.addAll(ThemeLoader.getPositions());
        return this;
    }
    public QuestionnaireLoader loadDirections(){
        vocabularies.addAll(ThemeLoader.getDirections());
        return this;
    }
    //------------------ SOCIAL RELATIONS ------------------------------//
    public QuestionnaireLoader loadFamilies(){
        vocabularies.addAll(ThemeLoader.getFamilies());
        return this;
    }
    public QuestionnaireLoader loadOwnFamily(){
        vocabularies.addAll(ThemeLoader.getOwnFamily());
        return this;
    }
    public QuestionnaireLoader loadOtherFamily(){
        vocabularies.addAll(ThemeLoader.getOtherFamily());
        return this;
    }
}
