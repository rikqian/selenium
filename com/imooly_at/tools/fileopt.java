package com.imooly_at.tools;

import java.io.*;

/**
 * Created by qianqiang on 15-1-16.
 * Basic file operation methods.
 */

public class fileopt {
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @param fileName 文件名
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
//一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
//显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static String readFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        String rs = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            int line = 1;
//一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
//显示行号
                rs = tempString;
                line++;
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return rs;
    }

    /**
     * 随机读取文件内容
     * @param fileName 文件名
     */

    /**
     * A方法追加文件：使用RandomAccessFile
     *
     * @param fileName 文件名
     * @param content  追加的内容
     */
    public static void appendMethodA(String fileName,

                                     String content) {
        try {
// 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
// 文件长度，字节数
            long fileLength = randomFile.length();
//将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * B方法追加文件：使用FileWriter
     *
     * @param fileName
     * @param content
     */
    public static void rewriteFile(String fileName, String content) {
        try {
//打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, false);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fileName = "C:/temp/newTemp.txt";
        String content = "new append! , abcdefghijklmn";


        fileopt.readFileByLines(fileName);


//按方法A追加文件
        fileopt.appendMethodA(fileName, content);
        fileopt.appendMethodA(fileName, "append end. n");
//显示文件内容
        fileopt.readFileByLines(fileName);


//按方法B追加文件
        fileopt.rewriteFile(fileName, content);

//显示文件内容
        String out = fileopt.readFile(fileName);
        System.out.println(out.endsWith(","));
        System.out.println(out.startsWith(","));
    }
}