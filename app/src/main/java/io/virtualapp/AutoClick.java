package io.virtualapp;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * 作者：士元
 * 时间：2019/9/11 18:15
 * 邮箱：wuzuqing@linghit.com
 * 说明：
 */
public class AutoClick {


    public static void click(int x, int y) {
        execShellCmd(formatCmd("input tap %d %d", x, y));
    }

    public static void keyEvent(int code) {
        execShellCmd(formatCmd("input keyevent  %d", code));
    }

    public static void inputText(String text) {
        execShellCmd(formatCmd("input text \"%s\"", text));
    }

    // input swipe 250 250 300 300
    public static void swipeHor(int startX, int endX, int y) {
        execShellCmd(formatCmd("input swipe %d %d %d %d", startX, y, endX, y));
    }

    public static void swipeVer(int x, int startY, int endY) {
        execShellCmd(formatCmd("input swipe %d %d %d %d", x, startY, x, endY));
    }



    private static void execShellCmd(String cmd) {
        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("sh");
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * 字符串拼接
     * @param format
     * @param args
     * @return
     */
    private static String formatCmd(String format, Object... args) {
        return String.format(Locale.CHINA, format, args);
    }

}

/**
 * KEYCODE_UNKNOWN=0;
 * KEYCODE_SOFT_LEFT=1;
 * KEYCODE_SOFT_RIGHT=2;
 * KEYCODE_HOME=3;     //home键
 * KEYCODE_BACK=4;     //back键
 * KEYCODE_CALL=5;
 * KEYCODE_ENDCALL=6;
 * KEYCODE_0=7;
 * KEYCODE_1=8;
 * KEYCODE_2=9;
 * KEYCODE_3=10;
 * KEYCODE_4=11;
 * KEYCODE_5=12;
 * KEYCODE_6=13;
 * KEYCODE_7=14;
 * KEYCODE_8=15;
 * KEYCODE_9=16;
 * KEYCODE_STAR=17;
 * KEYCODE_POUND=18;
 * KEYCODE_DPAD_UP=19;
 * KEYCODE_DPAD_DOWN=20;
 * KEYCODE_DPAD_LEFT=21;
 * KEYCODE_DPAD_RIGHT=22;
 * KEYCODE_DPAD_CENTER=23;
 * KEYCODE_VOLUME_UP=24;
 * KEYCODE_VOLUME_DOWN=25;
 * KEYCODE_POWER=26;
 * KEYCODE_CAMERA=27;
 * KEYCODE_CLEAR=28;
 * KEYCODE_A=29;
 * KEYCODE_B=30;
 * KEYCODE_C=31;
 * KEYCODE_D=32;
 * KEYCODE_E=33;
 * KEYCODE_F=34;
 * KEYCODE_G=35;
 * KEYCODE_H=36;
 * KEYCODE_I=37;
 * KEYCODE_J=38;
 * KEYCODE_K=39;
 * KEYCODE_L=40;
 * KEYCODE_M=41;
 * KEYCODE_N=42;
 * KEYCODE_O=43;
 * KEYCODE_P=44;
 * KEYCODE_Q=45;
 * KEYCODE_R=46;
 * KEYCODE_S=47;
 * KEYCODE_T=48;
 * KEYCODE_U=49;
 * KEYCODE_V=50;
 * KEYCODE_W=51;
 * KEYCODE_X=52;
 * KEYCODE_Y=53;
 * KEYCODE_Z=54;
 * KEYCODE_COMMA=55;
 * KEYCODE_PERIOD=56;
 * KEYCODE_ALT_LEFT=57;
 * KEYCODE_ALT_RIGHT=58;
 * KEYCODE_SHIFT_LEFT=59;
 * KEYCODE_SHIFT_RIGHT=60;
 * KEYCODE_TAB=61;
 * KEYCODE_SPACE=62;
 * KEYCODE_SYM=63;
 * KEYCODE_EXPLORER=64;
 * KEYCODE_ENVELOPE=65;
 * KEYCODE_ENTER=66;
 * KEYCODE_DEL=67;
 * KEYCODE_GRAVE=68;
 * KEYCODE_MINUS=69;
 * KEYCODE_EQUALS=70;
 * KEYCODE_LEFT_BRACKET=71;
 * KEYCODE_RIGHT_BRACKET=72;
 * KEYCODE_BACKSLASH=73;
 * KEYCODE_SEMICOLON=74;
 * KEYCODE_APOSTROPHE=75;
 * KEYCODE_SLASH=76;
 * KEYCODE_AT=77;
 * KEYCODE_NUM=78;
 * KEYCODE_HEADSETHOOK=79;
 * KEYCODE_FOCUS=80;//*Camera*focus
 * KEYCODE_PLUS=81;
 * KEYCODE_MENU=82;
 * KEYCODE_NOTIFICATION=83;
 * KEYCODE_SEARCH=84;
 * KEYCODE_MEDIA_PLAY_PAUSE=85;
 * KEYCODE_MEDIA_STOP=86;
 * KEYCODE_MEDIA_NEXT=87;
 * KEYCODE_MEDIA_PREVIOUS=88;
 * KEYCODE_MEDIA_REWIND=89;
 * KEYCODE_MEDIA_FAST_FORWARD=90;
 * KEYCODE_MUTE=91;
 * ————————————————
 * 版权声明：本文为CSDN博主「阳光柠檬_」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/liukang325/article/details/79268173
 */
