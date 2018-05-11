/*
******************* Copyright (c) ***********************\
**
**         (c) Copyright 2016, 蒋朋, china, sxkj. sd
**                  All Rights Reserved
**
**                 By(青岛世新科技有限公司)
**                    www.qdsxkj.com
**
**                       _oo0oo_
**                      o8888888o
**                      88" . "88
**                      (| -_- |)
**                      0\  =  /0
**                    ___/`---'\___
**                  .' \\|     |// '.
**                 / \\|||  :  |||// \
**                / _||||| -:- |||||- \
**               |   | \\\  -  /// |   |
**               | \_|  ''\---/''  |_/ |
**               \  .-\__  '-'  ___/-. /
**             ___'. .'  /--.--\  `. .'___
**          ."" '<  `.___\_<|>_/___.' >' "".
**         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
**         \  \ `_.   \_ __\ /__ _/   .-` /  /
**     =====`-.____`.___ \_____/___.-`___.-'=====
**                       `=---='
**
**
**     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
**
**               佛祖保佑         永无BUG
**
**
**                   南无本师释迦牟尼佛
**

**----------------------版本信息------------------------
** 版    本: V0.1
**
******************* End of Head **********************\
*/

package com.itant.autoclick.db;

import android.content.Context;

import com.itant.autoclick.dao.DaoMaster;
import com.itant.autoclick.util.LogUtils;

import org.greenrobot.greendao.database.Database;

/**
 * User: xiehehe
 * Date: 2016-10-16
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        LogUtils.logd("Database oldVersion" + oldVersion + "newVersion" + newVersion);
        switch (oldVersion) {
            case 1:
//                db.execSQL("ALTER TABLE 'AUTHOR_BEAN' ADD  'DESCRIPT' TEXT ;");
                break;
        }
    }

}
