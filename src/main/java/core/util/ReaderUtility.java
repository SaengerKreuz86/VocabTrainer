package core.util;

import core.loading.LessonLoader;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.locks.ReentrantLock;

@UtilityClass
public class ReaderUtility {
    private final ReentrantLock readLock = new ReentrantLock();

    /**
     * Reads a line from the input and formats it into lowercase
     * @param split Splits the read string by the specified regex
     * @return lowercase String array
     */
    public static String[] formattedRead(BufferedReader reader, String split) throws IOException {
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
    public static String readLine(BufferedReader reader) throws IOException {
        readLock.lock();
        try {
            return reader.readLine().toLowerCase();
        }finally {
            readLock.unlock();
        }
    }

    /**
     * Creates a buffered reader that reads from a file
     * @param path that leads to the file being read.
     * @return Buffered SystemInReader which takes the file of the specified path as input
     */
    public static BufferedReader getReader(String path) throws IOException {
        InputStream is = LessonLoader.class.getClassLoader().getResourceAsStream(path);
        if (is == null){
            throw new IOException();
        }
        return new BufferedReader(new InputStreamReader(is));
    }
}
