package com.xlwapp.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

class WorkoutUtils {
    private final static String HEX = "0123456789abcdef";

    public static void main(String[] args) {
//        ArrayList<String> idList = formatIdFromDoc();
//        getStringByIdFormDoc(idList);
//        extractWorkoutToFolder("C:\\Users\\30942\\Downloads\\20210319182643_workouts");
        actionIdOperate(
                "C:\\Users\\30942\\Downloads\\2021032614_1",
                "E:\\google_version\\armNew\\armNew\\app\\src\\main\\assets\\workout\\exercise_config.xml"
        );
//        zipToAWSBackUp("C:\\Users\\30942\\Downloads\\20210225163043_xWjjFS");
//        copySameFolderToAnotherFolder("C:\\Users\\30942\\Downloads\\20210220104132_0Gmujx", "E:\\google_version\\armNew\\armNew\\app\\src\\main\\assets\\workout\\men");
    }

    private static void getStringByIdFormDoc(ArrayList<String> idList) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        ArrayList<String> arrayList = new ArrayList<>();
        while (!line.contains("#")) {
            if (idList.contains(line.split("\t")[0])) {
                arrayList.add(line.split("\t")[1]);
                arrayList.add(line.split("\t")[2]);
            }
            line = scanner.nextLine();
        }
        System.out.println(arrayList);
    }

    private static ArrayList<String> formatIdFromDoc() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        ArrayList<String> arrayList = new ArrayList<>();
        while (!line.contains("#")) {
            if (!line.isEmpty()) {
                arrayList.add(line);
            }
            line = scanner.nextLine();
        }
        System.out.println(arrayList.toString().replaceAll(", ", ","));
        return arrayList;
    }

    private static void extractWorkoutToFolder(String path) {
        File folder = new File(path);
        for (String folderName: folder.list()) {
            String[] list = new File(path + File.separator + folderName).list();
            if (list != null) {
                for (String fileName : list) {
                    if (fileName.equals("workout")) {
                        File workoutFile = new File(path + File.separator + folderName + File.separator + fileName);
                        File outFile = new File(path + File.separator + folderName.split("_")[0]);
                        workoutFile.renameTo(outFile);
                    }
                }
            } else {
                System.out.println(path + File.separator + folderName + " is null");
            }
        }
    }

    private static void copySameFolderToAnotherFolder(String inputPath, String outputPath) {
        File outFolder = new File(outputPath);
        File inFolder = new File(inputPath);
        for (String outName: outFolder.list()) {
            for (String inName: inFolder.list()) {
                if (inName.equals(outName)) {
                    File outFiles = new File(outputPath + File.separator + outName);
                    File inFiles = new File(inputPath + File.separator + inName);
                    for (String outFileName: outFiles.list()) {
                        for (String inFileName: inFiles.list()) {
                            if (outFileName.equals(inFileName)) {
                                try {
                                    File inFile = new File(inputPath + File.separator + inName + File.separator + inFileName);
                                    File outFile = new File(outputPath + File.separator + outName + File.separator + outFileName);
                                    outFile.delete();
                                    inFile.renameTo(outFile);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    //压缩之前先到后台加密png
    private static void zipToAWSBackUp(String originPath) {
        File folder = new File(originPath);
        File zipFolder = new File(originPath + File.separator + "zips");
        try {
            zipFolder.mkdirs();
            for (String fileName : folder.list()) {
                Utils.fileToZip(originPath + File.separator + fileName, zipFolder.getPath(), fileName);
            }
            Utils.fileToZip(zipFolder.getPath(), originPath, "zips");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void actionIdOperate(String remoteDownloadPath, String localPath) {
        ArrayList<String> jsonList = getActionId(remoteDownloadPath);
        String localString = getExerciseConfigXMLId(localPath);
        addJsonToLocal(jsonList, localString, remoteDownloadPath + File.separator + "exercise_config.xml");
    }

    private static void addJsonToLocal(ArrayList<String> jsonList, String localString, String outPutFilePath) {

        ArrayList<Integer> localIdList = new ArrayList<>();
        String[] contents1 = localString.split("<item>");
        for (String contents2 : contents1) {
            if (!contents1[0].equals(contents2)) {
                localIdList.add(Integer.parseInt(contents2.split("</item>")[0]));
            }
        }

        for (String id: jsonList) {
            if (!localIdList.contains(Integer.parseInt(id))) {
                localIdList.add(Integer.parseInt(id));
                System.out.println(id);
            }
        }
        localIdList.sort(Integer::compareTo);

        String[] localLine = localString.split("\n");
        StringBuilder resultLine = new StringBuilder();
        for (String line: localLine) {
            if (!line.contains("<item>")) {
                if (line.contains("</exercises>")) {
                    for (int id : localIdList) {
                        resultLine.append("\t\t" + "<item>").append(id).append("</item>\n");
                    }
                }
                resultLine.append(line).append("\n");
            }
        }

        System.out.println(resultLine.toString());

        File file = new File(outPutFilePath);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(resultLine.toString());
            writer.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getExerciseConfigXMLId(String localIdPath) {
        File file = new File(localIdPath);
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            StringBuffer sb = new StringBuffer();
            while (reader.ready()){
                sb.append((char) reader.read());
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static ArrayList<String> getActionId(String idPath) {
        File files = new File(idPath);
        ArrayList<String> idList = new ArrayList<>();
        for (String name : files.list()) {
            if (name.contains("json")) {
                File file = new File(idPath + File.separator + name);
                InputStreamReader reader = null;
                try {
                    reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
                    StringBuffer sb = new StringBuffer();
                    while (reader.ready()){
                        sb.append((char) reader.read());
                    }

                    String content = sb.toString();
                    String[] contents1 = content.split("\"actionId\":\"");
                    for (String contents2 : contents1) {
                        if (!contents1[0].equals(contents2)) {
                            String id = contents2.split("\",\"time\"")[0];
                            if (id.equals("795")) {
                                System.out.print(name);
                            }
                            idList.add(id);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return idList;
    }
}
