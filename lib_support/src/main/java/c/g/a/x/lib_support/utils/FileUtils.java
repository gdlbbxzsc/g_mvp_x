package c.g.a.x.lib_support.utils;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;


public class FileUtils {

    //////////创建文件

    public static File createFile(String filePath) throws IOException {
        return createFile(filePath, true);
    }

    public static File createFile(String filePath, boolean isDelIfExists) throws IOException {

        File file = new File(filePath);

        if (file.exists()) {
            if (isDelIfExists) {
                file.delete();
            } else {
                return file;
            }
        }

        file.getParentFile().mkdirs();

        file.createNewFile();

        return file;
    }

    //////////创建目录
    public static boolean createDir(String dirPath) {
        return new File(dirPath).mkdirs();
    }

    ///////////删除文件或一个空目录,不能有子文件或目录
    public static boolean delete(String filePath) {
        return new File(filePath).delete();
    }

    /////////////删除一棵树
    public static boolean deleteAll(String path) {
        return deleteAll(new File(path));
    }

    public static boolean deleteAll(File file) {
        //如果为树结构末端，可直接删除
        if (!file.isDirectory()) return file.delete();

        //递归删除目录中的子目录下
        File[] children = file.listFiles();
        for (File aChildren : children) {
            boolean success = deleteAll(aChildren);
            if (!success) return false;
        }
        if (file.listFiles().length <= 0) {
            return file.delete();
        }
        return true;
    }

    public static long getSize(String path) {
        return getSize(new File(path));
    }

    public static long getSize(File file) {

        long size = 0;
        //如果为树结构末端，可直接删除
        if (!file.isDirectory()) return file.length();

        //递归删除目录中的子目录下
        File[] children = file.listFiles();
        for (File aChildren : children) {
            size += getSize(aChildren);
        }
        return size;
    }


    public static String convertFileSize(long size) {
        double kiloByte = size / 1024D;
        if (kiloByte < 1) return size + "Byte";

        double megaByte = kiloByte / 1024;
        if (megaByte < 1)
            return new BigDecimal(Double.toString(kiloByte))
                    .setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString()
                    + "KB";

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1)
            return new BigDecimal(Double.toString(megaByte))
                    .setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString()
                    + "MB";

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1)
            return new BigDecimal(Double.toString(gigaByte))
                    .setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString()
                    + "GB";

        return new BigDecimal(teraBytes)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString()
                + "TB";
    }

    public static String makeNameByMD5(String url) {
        try {
            return MD5Utils.encrypt(url.getBytes("utf-8")) + makeFileSuffix(url);
        } catch (Exception e) {
            e.printStackTrace();
            return "unknow" + System.currentTimeMillis();
        }
    }

    public static String makeFileSuffix(String url) {
        int lp = url.lastIndexOf(".");
        if (lp == -1) return "";
        return url.substring(lp).toLowerCase().trim();
    }
}
