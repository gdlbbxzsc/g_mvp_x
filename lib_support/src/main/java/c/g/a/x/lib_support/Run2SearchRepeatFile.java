package c.g.a.x.lib_support;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/7/10.
 */

public final class Run2SearchRepeatFile {

    private static final String ROOT = "./" + "lib_support";

    //是否只检查res资源文件
    private static final boolean ONLY_RES = false;

    private static final String[] ignore_path_list = {
            "build"
            , "test"
            , "androidTest"
            , ".svn"
            , ".idea"
            , ".gradle"
    };
    private static final String[] ignore_file_list = {
            "ids"
            , "attr"
            , "colors"
            , "dimens"
            , "styles"
            , "strings"
            , "AndroidManifest"
            , "proguard-rules"
            , "consumer-rules"
            , ".gitignore"
            , "build"
            , "gradlew"
    };
    private static final String[] ignore_suffix_list = {
            ".gradle"
            , ".iml"
            , ".pro"
            , ".bat"
    };


    private static final Map<String, RepeatFileInfo> allFileMap = new HashMap<>(2000);

    private static void searchRepeatFile() {
        ergodicFiles(new File(ROOT).getParentFile());

        Set<String> delSet = new HashSet<>(200);
        for (String name : allFileMap.keySet()) {
            RepeatFileInfo repeatFileInfo = allFileMap.get(name);

            if (repeatFileInfo.pathList.isEmpty()) continue;

            delSet.clear();
            afor:
            for (String path : repeatFileInfo.pathList) {
                if (ONLY_RES) {
                    boolean b = path.contains("\\res\\")
                            || path.contains("\\libs\\")
                            || path.contains("\\lib\\")
                            || path.contains("\\assets\\")
                            || path.contains("\\jniLibs\\");
                    if (!b) continue;
                }

                int d_pos = path.lastIndexOf(".");
                if (d_pos > 0 && d_pos < path.length()) {
                    String suffix = path.substring(d_pos);

                    for (String ig : ignore_suffix_list) {
                        if (suffix.contains(ig)) continue afor;
                    }
                }

                path = path.substring(path.indexOf("\\.\\") + 3);
                path = path.substring(0, path.indexOf("\\"));
                delSet.add(path);
            }

            if (delSet.size() <= 1) continue;

            System.out.print("filename:" + name);
            for (String pp : delSet) {
                System.out.print(" :" + pp);
            }
            System.out.println();
        }
    }

    private static void ergodicFiles(File parent) {

        if (parent == null || !parent.exists()) return;

        for (String ig : ignore_path_list) {
            if (parent.getName().contains(ig)) return;
        }

        File[] files = parent.listFiles();
        if (null == files || files.length <= 0) return;

        for (File file : files) {
            if (file.isDirectory()) {
                ergodicFiles(file);
            } else {
                recordFileName(file);
            }
        }
    }

    private static void recordFileName(File file) {

        String name = file.getName();

        for (String ig : ignore_file_list) {
            if (name.contains(ig)) return;
        }

        RepeatFileInfo fileInfo = allFileMap.get(name);

        if (fileInfo == null) {
            fileInfo = new RepeatFileInfo();
            allFileMap.put(name, fileInfo);
        }

        fileInfo.fileName = name;
        fileInfo.pathList.add(file.getAbsolutePath());
    }

    private static final class RepeatFileInfo {
        String fileName;
        List<String> pathList = new ArrayList<>(10);
    }

    public static void main(String[] args) {
        searchRepeatFile();
    }
}
