package com.itant.autoclick.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 吴祖清
 * @version 1.0
 * @createDate 2018/1/1 22:49
 * @des ${TODO}
 * @updateAuthor #author
 * @updateDate 2018/1/1
 * @updateDes ${TODO}
 */

public class SuUtil {

    private static Process process;
    private static String PAKAGE_NAME = "com.anzhuojwgly.ckhd";


    /**
     * 结束进程,执行操作调用即可
     */
    public static void kill() {
       kill(PAKAGE_NAME);
    }

    /**
     * 结束进程,执行操作调用即可
     */
    public static void kill(String packageName) {
        initProcess();
        killProcess(packageName);
        close();
    }

    /**
     * 初始化进程
     */
    private static void initProcess() {
        if (process == null)
            try {
                process = Runtime.getRuntime().exec("su");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * 结束进程
     */
    private static void killProcess(String packageName) {
        OutputStream out = process.getOutputStream();
        String cmd = "am force-stop " + packageName + " \n";
        try {
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输出流
     */
    private static void close() {
        if (process != null)
            try {
                process.getOutputStream().close();
                process = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
