package c.g.a.x.lib_support.android.utils;


import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import c.g.a.x.lib_support.utils.FileUtils;

/**
 * file=ROOT+appPath+fileSubPath+fileName
 **/
public final class FileHelper {

    private static final String ROOT = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

    private final String NAME;

    private String rootMainPath;
    private String fileSubPath;
    private String fileName;


    public static FileHelper getInstance(Context context) {
        return new FileHelper(context);
    }

    private FileHelper(Context context) {
        NAME = context.getPackageName();
    }

    ///////////
    public FileHelper toAppPath() {
        rootMainPath = NAME + File.separator;
        return this;
    }

    public FileHelper toCameraPath() {
        rootMainPath = Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator;
        return this;
    }

    public FileHelper fileSubPath(String fileSubPath) {
        this.fileSubPath = fileSubPath;
        return this;
    }

    public FileHelper fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public File create() {
        return create(true);
    }

    public File create(boolean delIfExists) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(ROOT);
            sb.append(rootMainPath);

            if (!TextUtils.isEmpty(fileSubPath)) {
                if (fileSubPath.charAt(0) == '/') fileSubPath = fileSubPath.substring(1);
                sb.append(fileSubPath);
                if (fileSubPath.charAt(fileSubPath.length() - 1) != '/') sb.append(File.separator);
            }

            sb.append(fileName);

            String path = sb.toString();
            Logger.e(path);
            return FileUtils.createFile(path, delIfExists);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
