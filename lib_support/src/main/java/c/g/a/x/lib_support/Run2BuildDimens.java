package c.g.a.x.lib_support;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2018/7/10.
 */

public final class Run2BuildDimens {

    private static final String FILE_START = "./" + "lib_support" + "/src/main/res/values";
    private static final String FILE_END = "/dimens.xml";

    private static final String FILE_SOURCE = FILE_START + FILE_END;

    private static final Object[][] OUTS = new Object[][]{
            {new StringBuilder(), 0.75D, "-sw240dp"},
            {new StringBuilder(), 1D, "-sw320dp"},
            {new StringBuilder(), 1.125D, "-sw360dp"},
            {new StringBuilder(), 1.5D, "-sw480dp"},
            {new StringBuilder(), 1.87D, "-sw600dp"},
            {new StringBuilder(), 2.25D, "-sw720dp"},
            {new StringBuilder(), 2.5D, "-sw800dp"},
            {new StringBuilder(), 2.56D, "-w820dp"}
    };

    private static void gen() {
        BufferedReader reader = null;
        try {
            System.out.println("删除已存在文件:");
            for (Object[] obj : OUTS) {

                String str = FILE_START + obj[2] + FILE_END;

                System.out.print(str + ":");
                File file = new File(str);
                if (!file.exists()) {
                    continue;
                }
                boolean b = file.delete();
                System.out.println(" " + b + "!");
            }

            System.out.println("读取基准文件：");
            //以此文件夹下的dimens.xml文件内容为初始值参照
            reader = new BufferedReader(new FileReader(new File(FILE_SOURCE)));

            String lineStr;
            while ((lineStr = reader.readLine()) != null) {
                for (Object[] obj : OUTS) {
                    StringBuilder sb = (StringBuilder) obj[0];
                    double d = (double) obj[1];
                    append(lineStr, sb, d);
                }
            }
            System.out.println("生成不同分辨率：");
            for (Object[] obj : OUTS) {
                StringBuilder sb = (StringBuilder) obj[0];
                String str = FILE_START + obj[2] + FILE_END;
                System.out.println(str + ":");
                writeFile(str, sb.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static void append(String lineStr, StringBuilder sb, double val) {
        if (lineStr.contains("</dimen>")) {
            int start = lineStr.indexOf(">") + 1;
            int end = lineStr.lastIndexOf("<") - 2;

            String start_dimen_tag = lineStr.substring(0, start);
            String end_dimen_tag = lineStr.substring(end);

            //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
            double num = Double.parseDouble(lineStr.substring(start, end));

            //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。
            sb.append(start_dimen_tag).append(num * val).append(end_dimen_tag);
        } else {
            sb.append(lineStr);
        }
        sb.append("\r\n");
    }

    /**
     * 写入方法
     */
    private static void writeFile(String fileStr, String text) {

        System.out.println(text);

        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            File file = new File(fileStr);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            pw.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                try {
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        gen();
    }
}
