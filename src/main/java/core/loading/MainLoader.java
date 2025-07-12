package core.loading;

import lombok.NoArgsConstructor;
import model.Vocabulary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor
public class MainLoader {
    private final List<Vocabulary> vocabularies = new ArrayList<>();

    public List<Vocabulary> collect(){
        return vocabularies;
    }

    public MainLoader cut(List<Vocabulary> cut){
        cut.forEach(this.vocabularies::remove);
        return this;
    }

    //-------------- INSTANT LISTS ------------------//

    /**
     * Immediately gets the list
     * @param mlep defines how the vocabularies are selected
     * @return List of vocabularies
     */
    public static List<Vocabulary> runAndCollect(MainLoaderEmptyParams mlep){
        return mlep.run().collect();
    }

    //----------------- LESSONS ------------------------//
    public MainLoader loadAllLessons(){
        vocabularies.addAll(LessonLoader.getAll());
        return this;
    }

    public MainLoader loadLesson(int i){
        vocabularies.addAll(LessonLoader.getVocabulary(i));
        return this;
    }

    public MainLoader loadRangeLessons(Integer[] range){
        vocabularies.addAll(LessonLoader.getRange(range));
        return this;
    }

    //----------------- COUNTER ------------------------//
    public MainLoader loadAllCounter(){
        vocabularies.addAll(ThemeLoader.getCounters());
        return this;
    }
    public MainLoader loadCounterByName(String name){
        vocabularies.addAll(ThemeLoader.getCounterByName(name));
        return this;
    }
    public MainLoader loadBookCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("book"));
        return this;
    }
    public MainLoader loadAgeCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("age"));
        return this;
    }
    public MainLoader loadFloorsOfBuildingsCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("floors_of_buildings"));
        return this;
    }public MainLoader loadFrequencyCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("frequency"));
        return this;
    }
    public MainLoader loadGeneralCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("general"));
        return this;
    }
    public MainLoader loadGlassesCupsCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("glasses_cups"));
        return this;
    }
    public MainLoader loadLongSlenderCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("long_slender"));
        return this;
    }
    public MainLoader loadMachinesVehiclesCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("machines_vehicles"));
        return this;
    }
    public MainLoader loadOrderCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("order"));
        return this;
    }public MainLoader loadPeopleCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("people"));
        return this;
    }public MainLoader loadSmallCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("small"));
        return this;
    }
    public MainLoader loadThinFlatCounter(){
        vocabularies.addAll(ThemeLoader.getCounterByName("thin_flat"));
        return this;
    }
    //------------------- DAYS ---------------------------------------//
    public MainLoader loadDays(){
        vocabularies.addAll(ThemeLoader.getDays());
        return this;
    }
    public MainLoader loadWeekDays(){
        vocabularies.addAll(ThemeLoader.getWeek());
        return this;
    }
    public MainLoader loadMonthDays(){
        vocabularies.addAll(ThemeLoader.getMonth());
        return this;
    }
    //------------------- POSITIONS ------------------------------------//
    public MainLoader loadPositions(){
        vocabularies.addAll(ThemeLoader.getPositions());
        return this;
    }
    public MainLoader loadDirections(){
        vocabularies.addAll(ThemeLoader.getDirections());
        return this;
    }
    //------------------ SOCIAL RELATIONS ------------------------------//
    public MainLoader loadFamilies(){
        vocabularies.addAll(ThemeLoader.getFamilies());
        return this;
    }
    public MainLoader loadOwnFamily(){
        vocabularies.addAll(ThemeLoader.getOwnFamily());
        return this;
    }
    public MainLoader loadOtherFamily(){
        vocabularies.addAll(ThemeLoader.getOtherFamily());
        return this;
    }
}
