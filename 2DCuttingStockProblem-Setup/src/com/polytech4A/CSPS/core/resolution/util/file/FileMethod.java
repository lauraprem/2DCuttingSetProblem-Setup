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
    protected final static String DATE_FORMAT = "yyyy-MM-dd_hh-mm-ss";
    private static final String path = "out";
    private String extension;
    private String dir;

    protected FileMethod(String extension, String dir) {
        this.extension = extension;
        this.dir = dir;
    }

    abstract String save(String filename, Resolution resolution);

    protected boolean mkdir() {
        return mkdir("", null);
    }

    protected boolean mkdir(String subDir) {
        return mkdir(subDir, null);
    }

    protected boolean mkdir(String subDir, Date date) {
        String file = path + '/';
        file += dir + '/';
        if(!"".equals(subDir)) file += subDir;
        if(date != null) file += '-' + new SimpleDateFormat(DATE_FORMAT).format(date);

        File f = new File(file);
        return f.mkdirs();
    }

    protected String getFilename(String filename) {
        return getFilename("", filename, new Date());
    }

    protected String getFilename(String subDir, String filename, Date date) {
        return getFilename(subDir, filename, date, -1L);
    }

    protected String getFilename(String subDir, String filename, Date date, Long id) {
        String file = path + '/';
        file += dir + '/';
        if(!"".equals(subDir)) file += subDir + '-' + new SimpleDateFormat(DATE_FORMAT).format(date) + '/';
        file += filename;
        if(id >= 0) file += '_' + id.toString();
        file += '-' + new SimpleDateFormat(DATE_FORMAT).format(date);
        file += '.' + extension;
        return file;
    }

    public String getExtension() {
        return extension;
    }
}
