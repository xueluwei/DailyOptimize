package com.xlwapp.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

class XmlUtils {
    public static void main(String[] args) {
        copyDownloadedStringFileToProject("C:\\Users\\30942\\Downloads\\stringsdownload_20210326145924","E:\\google_version\\armNew\\armNew\\app\\src\\main\\res");
//        singleXNLStringOperation();
//        deleteSameStringInTwoModel();
//        checkStrings("E:\\google_version\\armNew\\armNew\\WorkoutFramework\\src\\main\\res");
//        compareNewStringWithOld();
    }

    private static void compareNewStringWithOld() {
        File newFile = new File("E:\\stringsNew.xml");
        File oldFile = new File("E:\\strings.xml");
        try {
            StringBuffer sb = new StringBuffer();
            FileInputStream inputStream = new FileInputStream(newFile);
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            while (reader.ready()){
                sb.append((char) reader.read());
            }

            String newXml = sb.toString();

            sb = new StringBuffer();
            inputStream = new FileInputStream(oldFile);
            reader = new InputStreamReader(inputStream, "UTF-8");
            while (reader.ready()){
                sb.append((char) reader.read());
            }

            String oldXml = sb.toString();

            reader.close();
            inputStream.close();

            sb = new StringBuffer();
            for (String newStrings : newXml.split("\n")) {
                if (newStrings.contains("string name=")) {
                    String stringName = newStrings.split("<string name=\"")[1].split("\">")[0];
                    if (!oldXml.contains(stringName)) {
                        sb.append(newStrings).append("\n");
                        System.out.println(newStrings);
                    }
                }
            }

            File file = new File("C:\\Users\\30942\\Desktop\\newStrings.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(sb.toString());
            writer.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkStrings(String path) {
        File file = new File(path);
        HashMap<String, String> enNames = new HashMap<>();
        HashMap<String, String> resultMap = new HashMap<>();

        for (String name: file.list()) {
            for (String stringXML: new File(path + File.separator + name).list()) {
                if (stringXML.equals("strings.xml")) {
                    File stringFile = new File(path + File.separator + name + File.separator + stringXML);
                    try {
                        FileInputStream inputStream = new FileInputStream(stringFile);
                        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
                        StringBuffer sb = new StringBuffer();
                        while (reader.ready()){
                            sb.append((char) reader.read());
                        }
                        reader.close();
                        inputStream.close();

                        String[] xmlLine = sb.toString().split("\n");

                        if (name.equals("values")) {
                            for (String line: xmlLine) {
                                if (line.contains("<string name=\"") && !line.contains("translatable=\"false\"")) {
                                    enNames.put(line.split("<string name=\"")[1].split("\">")[0], line);
                                }
                            }
                        } else {
                            for (String names: enNames.keySet()) {
                                if (!sb.toString().contains(names)) {
                                    String country = name.replaceAll("values-", "");
                                    String countryList = resultMap.get(names);
                                    if (countryList == null) {
                                        countryList = country;
                                    } else {
                                        countryList = countryList + "," + country;
                                    }
                                    resultMap.put(names, countryList);
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (String strName: resultMap.keySet()) {
            System.out.println(enNames.get(strName).replaceFirst("    ", "").replaceFirst("\t", "") + "\n" + resultMap.get(strName) + "\n");
        }
    }

    private static void deleteSameStringInTwoModel(String path1, String path2) {
        File file1 = new File(path1);
        File file2 = new File(path2);
        for (String fNames1: file1.list()) {
            for (String fNames2: file2.list()) {
                if (fNames1.replace("_strings.xml", "")
                        .replace("en", "values")
                        .replace("pt_br", "pt")
                        .equals(
                                fNames2.replace("values-","")
                                        .replace("-rID", "_ID")
                                        .replace("-rCN", "_CN")
                                        .replace("-rTW", "_TW")
                                        .replace("-rTW", "_TW")
                        )
                ) {
                    File stringsXML1 = new File(path1 + File.separator + fNames1 + File.separator + "strings.xml");
                    File stringsXML2 = new File(path2 + File.separator + fNames2 + File.separator + "strings.xml");
                    try {
                        FileInputStream inputStream1 = new FileInputStream(stringsXML1);
                        InputStreamReader reader1 = new InputStreamReader(inputStream1, "UTF-8");
                        StringBuffer sbDownload = new StringBuffer();
                        while (reader1.ready()){
                            sbDownload.append((char) reader1.read());
                        }
                        reader1.close();
                        inputStream1.close();

                        FileInputStream inputStream2 = new FileInputStream(stringsXML2);
                        InputStreamReader reader2 = new InputStreamReader(inputStream2, "UTF-8");
                        StringBuffer sbLocal = new StringBuffer();
                        while (reader2.ready()){
                            sbLocal.append((char) reader2.read());
                        }
                        reader2.close();
                        inputStream2.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void copyDownloadedStringFileToProject(String downloadFileDir, String outputFileDir) {
        File file = new File(downloadFileDir);
        File oFile = new File(outputFileDir);
        for (String fNames: file.list()) {
            for (String outFNames: oFile.list()) {
                if (fNames.replace("_strings.xml", "")
                        .replace("en", "values")
                        .replace("pt_br", "pt")
                        .equals(
                            outFNames.replace("values-","")
                                    .replace("-rID", "_ID")
                                    .replace("-rCN", "_CN")
                                    .replace("-rTW", "_TW")
                                    .replace("-rTW", "_TW")
                        )
                ) {
                    File downloadXML = new File(downloadFileDir + File.separator + fNames);
                    File stringsXML = new File(outputFileDir + File.separator + outFNames + File.separator + "strings.xml");
                    try {
                        FileInputStream inputStream = new FileInputStream(downloadXML);
                        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
                        StringBuffer sbDownload = new StringBuffer();
                        while (reader.ready()){
                            sbDownload.append((char) reader.read());
                        }
                        reader.close();
                        inputStream.close();

                        FileInputStream inputStream1 = new FileInputStream(stringsXML);
                        InputStreamReader reader1 = new InputStreamReader(inputStream1, "UTF-8");
                        StringBuffer sbLocal = new StringBuffer();
                        while (reader1.ready()){
                            sbLocal.append((char) reader1.read());
                        }
                        reader1.close();
                        inputStream1.close();

                        StringBuilder sbResult = new StringBuilder();
                        FileOutputStream outputStream = new FileOutputStream(stringsXML);
                        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                        String[] localStrings = sbLocal.toString().split("\n");
                        for (String str : localStrings) {
                            if(localStrings[localStrings.length - 1].equals(str)){
                                break;
                            }
                            if (str.contains("<string name=\"")) {
                                String name = "<string name=\"" + str.split("<string name=\"")[1].split("\">")[0] + "\">";
                                if (!sbDownload.toString().contains(name)
                                ) {
                                    sbResult.append(str).append("\n");
                                }
                            } else  {
                                sbResult.append(str).append("\n");
                            }
                        }
                        for (String downloadStrings : sbDownload.toString().split("\n")) {
                            if (!downloadStrings.contains("?xml version=\"1.0\"") &&
                                    !downloadStrings.contains("<resources>") &&
                                    !downloadStrings.contains("</resources>")){
                                if (sbResult.indexOf(downloadStrings) == -1){
                                    sbResult.append("\t").append(downloadStrings).append("\n");
                                }
                            }
                        }
                        sbResult.append("</resources>");
                        writer.write(sbResult.toString());
                        writer.close();
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public static void copySpecifyXmlFileStringToAnotherDir(String inputFileDir, String outputFileDir, ArrayList<String> deleteNameList) {
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
                                    //if 判断需要 copy的string name
                                    Boolean canAppend = false;
                                    for (String item : deleteNameList) {
                                        if (ns.contains(item)){
                                            canAppend = true;
                                        }
                                    }
                                    if (canAppend) {
                                        st.append(ns).append("\n");
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void singleXNLStringOperation() {
        ArrayList<String> deleteNameList = new ArrayList<>();
        deleteNameList.add("<string name=\"share_toast_text\">");
//        copySpecifyXmlFileStringToAnotherDir("E:\\google_version\\armNew\\armNew\\Daily\\src\\main\\res","E:\\google_version\\armNew\\armNew\\WorkoutChart\\src\\main\\res", deleteNameList);
        deleteUnusedString("E:\\google_version\\armNew\\armNew\\WorkoutFramework\\src\\main\\res", deleteNameList);
    }

    private static void deleteUnusedString(String path, ArrayList<String> deleteNameList)  {

        File file = new File(path);
        for (String name: file.list()) {
            for (String stringXML: new File(path + File.separator + name).list()) {
                if (stringXML.equals("strings.xml")) {
                    File stringFile = new File(path + File.separator + name + File.separator + stringXML);
                    try {
                        FileInputStream inputStream = new FileInputStream(stringFile);
                        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
                        StringBuffer sbDownload = new StringBuffer();
                        while (reader.ready()){
                            sbDownload.append((char) reader.read());
                        }
                        reader.close();
                        inputStream.close();

                        StringBuilder sbResult = new StringBuilder();
                        FileOutputStream outputStream = new FileOutputStream(stringFile);
                        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                        String[] localStrings = sbDownload.toString().split("\n");
                        for (String str : localStrings) {
                            if(localStrings[localStrings.length - 1].equals(str)){
                                break;
                            }
                            boolean canAdd = true;
                            for (String deleteStr: deleteNameList) {
                                if (str.contains(deleteStr)) {
                                    canAdd = false;
                                }
                            }
                            if (canAdd) {
                                sbResult.append(str).append("\n");
                            }
                        }

                        sbResult.append("</resources>");
                        writer.write(sbResult.toString());
                        writer.close();
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
