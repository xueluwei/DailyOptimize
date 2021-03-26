package com.xlwapp.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

class EncryptAssist {
    public static class Main {
        static String baseSrcFolder = "";
        static String getDesFolder = "";
        public static void main(String args[]){
        /*enCodeFile(
                "/Users/raocc/Documents/Android Studio/MaleWorkout/trunk/MaleWorkout/app/assets/female_action_img/calf_stretch_left/2.png",
                "/Users/raocc/Documents/Android Studio/MaleWorkout/trunk/MaleWorkout/app/assets/female_action_img/calf_stretch_left/3.png"
        );*/
            enCode(
                    "E:\\td_action_img",
                    "E:\\imgs2"
            );
//        enCodeFile("/Users/zhuxiaoyuan/Documents/tmp/062.mp4",
//                "/Users/zhuxiaoyuan/Documents/tmp/062_video");
        }

    }

    public static void enCode(String rootSrcFolder,String rootDesFolder){
        File[] files = new File(rootSrcFolder).listFiles();
        File desFolder = new File(rootDesFolder);
        if(!desFolder.exists()){
            desFolder.mkdirs();
        }
        for(File folder: files){
            desFolder = new File(rootDesFolder,folder.getName());
            if(!desFolder.exists()){
                desFolder.mkdirs();
            }
            enCodeFolder(folder.getAbsolutePath(),desFolder.getAbsolutePath());
        }
        enCodeFolder(rootSrcFolder,rootDesFolder);
    }


    /**
     * 递归方式把所有图片都加密
     * @param srcFolder
     * @param desFolder
     */
    public static void enCodeFolder(String srcFolder, String desFolder){
        if(srcFolder.contains(".DS_Store")){
            return;
        }
        File srcFiles[] = new File(srcFolder).listFiles();
        for(File srcFile : srcFiles){
            if (srcFile.isFile() && !srcFile.getName().startsWith(".")) {
                String fileName = srcFile.getName();
                File desFile = new File(desFolder,fileName.substring(0,fileName.lastIndexOf(".")));
                if(!desFile.exists()){
                    try{
                        desFile.createNewFile();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                enCodeFile(srcFile.getAbsolutePath(),desFile.getAbsolutePath());
            }
        }
    }

    public static void enCodeFile(String src, String des) {
        File src_file = new File(src);
        File des_file = new File(des);
        try{
            FileInputStream fis = new FileInputStream(src_file);
            FileOutputStream fos = new FileOutputStream(des_file);
            byte[] buffer = new byte[1024];
            int count;
            boolean hasSet = false;
            //每一位的前64字节取反
            while((count = fis.read(buffer))!= -1){
                if (!hasSet) {
                    hasSet = true;
                    for (int i = 0; i < 64; i++) {
                        buffer[i] = (byte)~buffer[i];
                    }
                }
                fos.write(buffer, 0, count);
            }
            fos.flush();
            fos.close();
            fis.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void enCodeFile2(String src, String des) {
        File src_file = new File(src);
        File des_file = new File(des);
        try{
            FileInputStream fis = new FileInputStream(src_file);
            FileOutputStream fos = new FileOutputStream(des_file);
            byte[] buffer = new byte[1024];
            int count;
            while((count = fis.read(buffer))!= -1){
                for (int i = 0; i < count; i++) {
                        /*if(buffer[i] >= 0){
                            buffer[i] = (byte)(Byte.MAX_VALUE - buffer[i]);
                        }else{
                            buffer[i] = (byte)(Byte.MIN_VALUE - buffer[i]);
                        }*/
                    buffer[i] = (byte)~buffer[i];
                }
                fos.write(buffer, 0, count);
            }
            fos.flush();
            fos.close();
            fis.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
