package com.imooly_at;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class WebZipRename
{
    public static void main(String[] args) throws IOException
    {
        String rootPath = "D:\\Documents\\My WebZIP Sites\\edianpay(0)\\www.edianpay.cn";
        String indexFile = "D:\\Documents\\My WebZIP Sites\\edianpay(0)\\www.edianpay.cn\\www.edianpay.html";

        WebZipRename webZipRename = new WebZipRename();
        if(webZipRename.rename(new File(rootPath), true))
            webZipRename.modifyFile(indexFile);

        System.out.println("Done!!!");
    }

    private Map<String, String> contentPattern = new HashMap<String, String>();

    /**
     * 将文件改名
     * @param f
     */
    public boolean rename(File f, boolean caller)
    {
        if(caller){
            contentPattern.clear();
        }
        boolean success = true;

        if(f.isFile()){
            String fileName = f.getName();
            int indexOfLastDot = fileName.lastIndexOf('.');

            if(indexOfLastDot != -1){
                String fileSuffix = fileName.substring(indexOfLastDot);
                int indexOfFirstFileSuffix = fileName.indexOf(fileSuffix);

                if(indexOfFirstFileSuffix != indexOfLastDot){ //说明存在2个文件后缀
                    String NewFileName = fileName.substring(0, indexOfFirstFileSuffix) + fileSuffix;
                    String pathName = f.getParent();
                    File dst = new File(pathName + "\\" + NewFileName);
                    if(f.renameTo(dst)){
                        System.out.println(fileName + " => " +NewFileName);
                        contentPattern.put(fileName, NewFileName);
                    }else{
                        System.err.println(fileName + " <> " +NewFileName);
                        success = false;
                    }
                }
            }
        }else if(f.isDirectory()){
            String[] subFiles = f.list();
            for (String subFile : subFiles) {
                String subFileName = f.getAbsolutePath() + "\\" + subFile;
                if(!rename(new File(subFileName), false))
                    success = false;
            }
        }

        return success;
    }

    /**
     * 修改文件内容
     * @param fileName
      * @return
     */
    public boolean modifyFile(String fileName)
    {
        FileReader reader = null;
        try{
            reader = new FileReader(fileName);
        }catch (Exception e) {
            System.err.println("打开旧文件失败!");
            System.out.println(e);
        }

        if(reader == null)
            return false;

        BufferedReader br = new BufferedReader(reader);

        String line;
        StringBuilder sb = new StringBuilder();

        try{
            while((line = br.readLine()) != null){
                sb.append(line).append("\n");
            }
            br.close();
        }catch (Exception e) {
            System.err.println("读取旧文件失败!");
            System.out.println(e);
            return false;
        }

//      for (String oldString : contentPattern.keySet()) {
//          System.err.println(oldString);
//          int start = sb.indexOf(oldString);
//
//          while(start != -1){
//              sb.replace(start, start + oldString.length(), contentPattern.get(oldString));
//              System.out.println(oldString + "==>" + contentPattern.get(oldString));
//              start = sb.indexOf(oldString);
//          }
//      }

        for (String newString : contentPattern.values()) {
            int start = sb.indexOf(newString);

            while(start != -1){
                int end = sb.indexOf("\"", start);
                String oldString = sb.substring(start, end);
                sb.delete(start + newString.length(), end);
                System.out.println(oldString + "==>" + newString);
                start = sb.indexOf(newString, end);
            }
        }

        int indexOfSlash = fileName.lastIndexOf('\\');
        String pathName = fileName.substring(0, indexOfSlash);
        String newFileName = pathName + "\\_new" + fileName.substring(indexOfSlash + 1);
        File newFile = new File(newFileName);
        if(!newFile.exists()){
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                System.err.println("创建新文件失败！");
                System.out.println(e);
                return false;
            }
        }

        PrintWriter writer = null;
        try{
            writer = new PrintWriter(newFile);
        }catch (Exception e) {
            System.err.println("打开新文件失败！");
            System.out.println(e);
        }

        if(writer == null)
            return false;

        try{
            writer.print(sb.toString());
            writer.close();
        }catch (Exception e) {
            System.err.println("写入新文件失败！");
            System.out.println(e);
            return false;
        }

        return true;
    }

}