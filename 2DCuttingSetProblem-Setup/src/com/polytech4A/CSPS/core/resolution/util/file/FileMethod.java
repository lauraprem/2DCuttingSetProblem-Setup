package com.polytech4A.CSPS.core.resolution.util.file;

import com.polytech4A.CSPS.core.resolution.Resolution;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alexandre
 *         13/03/2015
 */
public abstract class FileMethod {
    private final String path = "out/";
    private String extension;
    private String dir;

    protected FileMethod(String extension, String dir) {
        this.extension = extension;
        this.dir = dir;
    }

    abstract void save(String filename, Resolution resolution);

    protected void mkdir() {
        File f = new File(String.format("%s/%s",
                path,
                dir));
		f.mkdirs();
    }

    protected String getFilename(String filename) {
        return String.format("%s/%s/%s-%s.%s",
                path,
                dir,
                filename,
                new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()),
                extension);
    }
}
