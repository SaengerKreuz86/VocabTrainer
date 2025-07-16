package core.util;

import lombok.experimental.UtilityClass;

import java.util.logging.Logger;

@UtilityClass
public class ArrayUtil {

    /**
     * Extracts the values of the array that can be parsed to an int
     * @param sa Array of strings
     * @return int array with non-null entries
     */
    public static int[] toIntArray(String[] sa){
        String[] saCopy = sa.clone();
        int counter = 0;
        for (int i = 0; i < sa.length; i++){
            try {
                Integer.parseInt(sa[i]);
                counter++;
            } catch (NumberFormatException e) {
                Logger.getLogger("ArraysUtil").info("Couldn't parse %s%n".formatted(sa[i]));
                saCopy[i] = null;
            }
        }
        int[] out = new int[counter];
        counter = 0; // Now counts offset for null values
        for (int i = 0; i < saCopy.length; i++) {
            if (saCopy[i] != null){
                out[i-counter] = Integer.parseInt(saCopy[i]);
            }else {
                counter++;
            }
        }
        return out;
    }
}
