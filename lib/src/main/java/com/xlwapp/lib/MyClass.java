package com.xlwapp.lib;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;


public class MyClass {

    //copyXmlFileToAnotherDir
    public final static String inputFileDir = "E:\\google_version\\dumbbell3D\\Dumbbell3D\\app\\src\\main\\res";
    public final static String outputFileDir = "E:\\google_version\\lose3d\\LoseWeight3D\\app\\src\\main\\res";

    //unzipDownloadedFloder
    public final static String unzipFileDir = "E:\\ActionVoicemale-20200821022738";

    //copySameFileToAnotherDir
    public final static String sourceFileDir = "E:\\Voicemale-20200819094959";

    //changeName
    public final static String finalFile = "E:\\audio_data_android";

    public static void main(String[] args) {
        TTS2();
//        youtubeJsonUtil();
    }


    private static void compairImage() {
        File imageFile = new File("E:\\kotlin\\DailyOptimize\\lib\\data\\sc.png");
        ITesseract instance = new Tesseract(); // JNA Interface Mapping
        try {
            String result = instance.doOCR(imageFile);
            String[] strings = result.split("\n");
            for (String s :
                    strings) {
            }
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void youtubeJsonUtil() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        while(scanner.hasNextLine()){
            String nextline = scanner.nextLine();
            if(nextline.contains("#")){
                break;
            }
            String[] id_url = nextline.split("\t");
            String[] ids = id_url[0].split("/");
            if(ids.length >= 2){
                for (String id: ids
                     ) {
                    builder.append("\"" + id + "\":" + "\"" + id_url[1] + "\",\n");
                }
            }else {
                String id = id_url[0];
                builder.append("\"" + id + "\":" + "\"" + id_url[1] + "\",\n");
            }
        }
        builder.append("}");
        System.out.println(builder.toString());
    }

    private static void TTS2() {
        copySpecifyXmlFileStringToAnotherDir();
//        unzipDownloadedFloder();
//        deleteAndRenameUnzipFloder();
//        copySameFileToAnotherDirAndZip();
//        deleteAnotherDir();
//        changeName();
    }

    private static void changeName() {
        File file = new File(unzipFileDir);
        file.renameTo(new File(finalFile));
    }

    private static void deleteAnotherDir() {
        File file = new File(unzipFileDir);
        for (String values:file.list()) {
            File file1 = new File(unzipFileDir + "/" + values);
            for (String string:file1.list()) {
                File file2 = new File(unzipFileDir + "/" + values + "/" + string );
                if(file2.isDirectory() && string.equals("base_data")){
                    for (String fi: file2.list()) {
                        File file3 = new File(unzipFileDir + "/" + values + "/" + string + "/" + fi );
                        file3.delete();
                    }
                    file2.delete();
                }
            }
        }
    }

    private static void deleteAndRenameUnzipFloder() {
        File file = new File(unzipFileDir);
        for (String values:file.list()) {
            File file1 = new File(unzipFileDir + "/" + values);
            for (String name : file1.list()){
                String zipPath = unzipFileDir + "/" + values + "/" + name;
                File file2 = new File(zipPath);
                if(name.contains(".zip")){
                    file2.delete();
                }
                if(file2.isDirectory() && name.equals("name")){
                    file2.renameTo(new File(unzipFileDir + "/" + values + "/" + "base"));
                }
            }
        }
    }

    private static void copySpecifyXmlFileStringToAnotherDir() {

        try {
            File file = new File(inputFileDir);
            File ofile = new File(outputFileDir);
            for (String values:file.list()) {
                for (String ovalues: ofile.list()) {
                    if(values.contains("values") && values.equals(ovalues)){
                        File file1 = new File(inputFileDir + "/" + values);
                        for (String string:file1.list()) {
                            if(string.contains("strings")){
                                File file2 = new File(inputFileDir + "/" + values + "/" + string);
                                File ofile2 = new File(outputFileDir + "/" + values + "/" + string);
                                FileInputStream inputStream = new FileInputStream(file2);
                                InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
                                StringBuffer sb = new StringBuffer();
                                while (reader.ready()){
                                    sb.append((char) reader.read());
                                }
                                reader.close();
                                inputStream.close();

                                FileInputStream inputStream1 = new FileInputStream(ofile2);
                                InputStreamReader reader1 = new InputStreamReader(inputStream1, "UTF-8");
                                StringBuffer sb1 = new StringBuffer();
                                while (reader1.ready()){
                                    sb1.append((char) reader1.read());
                                }
                                reader1.close();
                                inputStream1.close();

                                StringBuilder st = new StringBuilder();
                                FileOutputStream outputStream = new FileOutputStream(ofile2);
                                OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                                String[] strings = sb1.toString().split("\n");
                                for (String str : strings) {
                                    if(strings[strings.length - 1].equals(str)){
                                        break;
                                    }
                                    st.append(str).append("\n");
                                }
                                for (String ns : sb.toString().split("\n")) {
                                    if ((ns.contains("btn_allow") ||
                                            ns.contains("permission_explained_title") ||
                                            ns.contains("permission_goto_set_1") ||
                                            ns.contains("permission_goto_set_2") ||
                                            ns.contains("permission_goto_set_2_api_level_30") ||
                                            ns.contains("permission_goto_set_3"))){
                                        System.out.println(file2.getAbsolutePath() + ":\n" + ns);
                                        st.append(ns.replace("</resources>", "")).append("\n");
                                    }
                                }
                                st.append("</resources>");
                                writer.write(st.toString());
                                writer.close();
                                outputStream.close();
                            }

                        }
                    }

                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void unzipDownloadedFloder() {
        File file = new File(unzipFileDir);
        for (String values:file.list()) {
            File file1 = new File(unzipFileDir + "/" + values);
            for (String name : file1.list()){
                String zipPath = unzipFileDir + "/" + values + "/" + name;
                File file2 = new File(zipPath);
                if(name.contains(".zip")){
                    Utils.unZipFiles(zipPath, unzipFileDir + "/" + values);
                    file2.delete();
                }
                if(file2.isDirectory() && name.equals("name")){
                    file2.renameTo(new File(unzipFileDir + "/" + values + "/" + "base"));
                }
            }
        }
    }

    private static void copySameFileToAnotherDirAndZip() {
        try {
            File file = new File(sourceFileDir);
            for (String values:file.list()) {
                File file1 = new File(sourceFileDir + "/" + values);
                for (String string:file1.list()) {
                    File file2 = new File(sourceFileDir + "/" + values + "/" + string);
                    FileInputStream inputStream = new FileInputStream(file2);
                    File file3 = new File(unzipFileDir + "/" + values + "/base_data" );
                    byte[] bytes = new byte[1024];
                    if(file3.mkdir()){
                        File file4 = new File(file3 + "/" + string);
                        FileOutputStream outputStream = new FileOutputStream(file4);
                        int count = inputStream.read(bytes);
                        while (count != -1){
                            outputStream.write(bytes,0,count);
                            outputStream.flush();
                            count = inputStream.read(bytes);
                        }
                        outputStream.close();
                    }else {
                        File file4 = new File(file3 + "/" + string);
                        FileOutputStream outputStream = new FileOutputStream(file4);
                        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                        int count = inputStream.read(bytes);
                        while (count != -1){
                            outputStream.write(bytes,0,count);
                            outputStream.flush();
                            count = inputStream.read(bytes);
                        }
                        writer.close();
                        outputStream.close();
                    }
                    inputStream.close();
                }
                Boolean f  = Utils.fileToZip(unzipFileDir + "/" + values + "/base_data", unzipFileDir + "/" + values + "/base", "base_data");
                System.out.println(f);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
