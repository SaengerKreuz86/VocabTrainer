package core;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.locks.ReentrantLock;

@UtilityClass
public class SystemInReader {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final ReentrantLock readLock = new ReentrantLock();

    /**
     * Reads a line from the console input and formats it into lowercase
     * @param split Splits the read string by the specified regex
     * @return lowercase String array
     */
    public static String[] formattedRead(String split) throws IOException {
        readLock.lock();
        try {
            return reader.readLine().toLowerCase().split(split);
        }finally {
            readLock.unlock();
        }
    }

    /**
     * Reads a full line and makes it lowercase
     * @return lowercase String
     */
    public static String readLine() throws IOException {
        readLock.lock();
        try {
            return reader.readLine().toLowerCase();
        }finally {
            readLock.unlock();
        }
    }
}
