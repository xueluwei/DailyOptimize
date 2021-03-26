package com.xlwapp.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

class UrlUtil {
    public static void main(String[] args) {
        changeDocStrToJson();
    }

    private static void changeDocStrToJson() {
        Scanner scanner = new Scanner(System.in);
        String nextLine = scanner.nextLine();
        ArrayList<String> list = new ArrayList<>();
        list.add("{");
        while (!nextLine.equals("#")) {
            String[] item = nextLine.split("\t");

            if (!item[2].isEmpty() && !item[2].equals("-")) {
                String num1 = item[0].replaceAll(" ", "");
                String url = item[2].replaceAll(" ", "");
                list.add("\"" + num1 + "\":" + "\"" + url + "\",");
            }

            nextLine = scanner.nextLine();
        }
        String last = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        list.add(last.replace(",",""));
        list.add("}");
        StringBuilder sb = new StringBuilder();
        for (String listItem : list) {
            System.out.println(listItem);
            sb.append(listItem).append("\n");
        }
        try{
            File file = new File("C:\\Users\\30942\\Desktop\\json.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(sb.toString());
            writer.close();
            outputStream.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
    }

}
