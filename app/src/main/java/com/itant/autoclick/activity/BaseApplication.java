package com.itant.autoclick.activity;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.itant.autoclick.util.ChengJiuArray;
import com.itant.autoclick.util.CmdData;
import com.itant.autoclick.util.JhmUtil;
import com.itant.autoclick.util.LogUtils;
import com.itant.autoclick.util.OkHttpClientManager;
import com.itant.autoclick.util.SPUtils;
import com.itant.autoclick.util.Util;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2017/12/22 14:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2017/12/22$
 * @updateDes ${TODO}
 */

public class BaseApplication extends Application {
    private static Context mApplication;
    private static int sStatusBarHeight;

    private static boolean DEBUG = true;

    public static void setStatusBarHeight(int statusBarHeight) {
        sStatusBarHeight = statusBarHeight;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = getApplicationContext();
        initScreen();
        SPUtils.init(getApplicationContext());
        StaticVal.init();
        OkHttpClientManager.init(getApplicationContext());
        CmdData.init();
        ChengJiuArray.init();
        Util.init();
        JhmUtil.init();
        DEBUG = false;
    }

    public static boolean DEBUG() {
        return DEBUG;
    }

    public static Context getAppContext() {
        return mApplication;
    }

    private static int screenWidth;
    private static int screenHeight;

    public static int getScreenWidth() {
        if (screenWidth == 0) {
            initScreen();
        }
        return screenWidth;
    }

    public static int getX(float ratio){
        return (int) (getScreenWidth()*ratio);
    }
    public static int getY(float ratio){
        return (int) (getScreenHeight()*ratio);
    }
    public static int getScreenHeight() {
        if (screenHeight == 0) {
            initScreen();
        }
        return screenHeight;
    }
    public static int densityDpi ;
    public static void initScreen() {
        DisplayMetrics metrics = getAppContext().getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        densityDpi = metrics.densityDpi;
        LogUtils.logd("screenWidth: "+screenWidth+" screenHeight:"+screenHeight + " dpi:"+ metrics.densityDpi);
    }

    public static float getRatioY(float value) {
        return getScreenHeight()*value;
    }


}
