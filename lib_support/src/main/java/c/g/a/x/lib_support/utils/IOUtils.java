package c.g.a.x.lib_support.utils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by Administrator on 2018/7/9.
 */

public final class IOUtils {

    public static byte[] stream2byteArray(InputStream is) {
        byte[] buff = null;
        int tmp;
        try (ByteArrayOutputStream bops = new ByteArrayOutputStream()) {

            while ((tmp = is.read()) != -1) {
                bops.write(tmp);
            }
            buff = bops.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ignored) {
            }
        }
        return buff;
    }


    public static boolean writeData2File(InputStream is, String path) {
        try {
            File file = FileUtils.createFile(path);
            if (file == null) return false;

            return writeData2File(is, file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeData2File(InputStream is, File file) {
        return writeData2File(is, file, null);
    }


    public static boolean writeData2File(InputStream is, File file, ProgressListener listener) {
        OutputStream os = null;
        try {

            if (file == null) return false;
            file.getParentFile().mkdirs();
            if (file.exists()) file.delete();


            long readLen = 0;

            os = new FileOutputStream(file);
            byte[] fileReader = new byte[4096];
            int read;
            while ((read = is.read(fileReader)) != -1) {

                os.write(fileReader, 0, read);

                readLen += read;

                if (listener != null) listener.progress(readLen);

            }

            os.flush();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface ProgressListener {
        void progress(long pro);
    }
}
