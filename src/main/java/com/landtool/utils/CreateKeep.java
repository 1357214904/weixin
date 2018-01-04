package com.landtool.utils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

class CreateKeep{

    private static final String packageFile = ".keep";

    public static void main(String[] args) {
        String path = getRealPath();
        File file = new File(path);
        try {
            traversalAllFolder(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历当前文件夹下的所有文件夹，对空的文件夹创建.keep文件
     *
     * @param dir
     * @throws Exception
     */
    private static void traversalAllFolder(File dir) {
        File[] fs = dir.listFiles();
        assert fs != null;
        int fsLength = fs.length;
        if (fsLength == 0) {
            createFile(dir.getAbsolutePath());
        } else {
            for (File f : fs) {
                if (f.isDirectory()) {
                    try {
                        traversalAllFolder(f);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    /**
     * 创建.keep文件
     *
     * @param folderPath
     *            路径名
     */
    private static void createFile(String folderPath) {
        String fileName = folderPath + "/" + packageFile;
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前jar包所在路径
     *
     * @return
     */
    private static String getRealPath() {
        String realPath = Objects.requireNonNull(CreateKeep.class.getClassLoader().getResource(""))
                 .getFile();
        java.io.File file = new java.io.File(realPath);
        realPath = file.getAbsolutePath();
        try {
            realPath = java.net.URLDecoder.decode(realPath, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return realPath;
    }

}
