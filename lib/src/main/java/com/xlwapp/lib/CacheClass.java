package com.xlwapp.lib;

import java.util.Scanner;

class CacheClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nextLine = scanner.nextLine();
        StringBuilder builder = new StringBuilder();
        while (!nextLine.equals("#")) {
            if (!nextLine.isEmpty()) {
                builder.append(nextLine.replaceAll("\t", "")).append(",");
            }
            nextLine = scanner.nextLine();
        }
        System.out.println(builder.toString());
    }
}

