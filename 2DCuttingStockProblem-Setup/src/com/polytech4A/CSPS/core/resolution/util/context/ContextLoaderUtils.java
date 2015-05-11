package com.polytech4A.CSPS.core.resolution.util.context;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Vector;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @version 1.0
 *          <p>
 *          Loader of Context files.
 */
public class ContextLoaderUtils {
    private static Long id = -1L;

    /**
     * Load the content of the context file.
     *
     * @param path path of the context File.
     * @return Context loaded.
     * @throws IOException                   if file not found.
     * @throws MalformedContextFileException if the Context file don't have the right structure.
     */
    public static Context loadContext(String path) throws IOException {
        File file = new File(path);
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        ArrayList<Image> images = new ArrayList<>();
        try {
            Double x = loadLine(it, "^LX=[0-9]{1,13}(\\.[0-9]*)?$");
            Double y = loadLine(it, "LY=[0-9]{1,13}(\\.[0-9]*)?");
            int cost = loadLine(it, "m=[0-9]{1,13}(\\.[0-9]*)?").intValue();
            while (it.hasNext()) {
                images.add(loadImage(it.nextLine()));
            }
            LineIterator.closeQuietly(it);
            return new Context(file.getName(), cost, 1, images, new Vector(x, y));
        } catch (MalformedContextFileException mctx) {
            throw mctx;
        } finally {
            LineIterator.closeQuietly(it);
        }
    }

    /**
     * Line Parser of global first infomrations.
     *
     * @param iterator iterator of the openfile.
     * @param regex    regex of the line.
     * @return value loaded.
     * @throws MalformedContextFileException if the Context file don't have the right structure.
     */
    private static Double loadLine(LineIterator iterator, String regex) throws MalformedContextFileException {
        MalformedContextFileException mctx = new MalformedContextFileException();
        if (iterator.hasNext()) {
            String line = iterator.nextLine();
            if (line.matches(regex)) {
                return Double.parseDouble(line.split("=")[1]);
            } else {
                throw mctx;
            }
        } else {
            throw mctx;
        }
    }

    /**
     * Load a box form the file.
     *
     * @param line line in the file.
     * @return Box loaded of the line
     * @throws MalformedContextFileException if the Context file don't have the right structure.
     */
    private static Image loadImage(String line) throws MalformedContextFileException {
        MalformedContextFileException mctx = new MalformedContextFileException();
        if (line.matches("[0-9]{1,13}(\\.[0-9]*)?\\t[0-9]{1,13}(\\.[0-9]*)?\\t\\d{1,5}")) {
            String[] array = line.split("\\t");
            Double w = Double.parseDouble(array[0]),
                    h = Double.parseDouble(array[1]);
            id++;
            return new Image(id, new Vector(w, h), Long.parseLong(array[2]));
        } else throw mctx;
    }

}