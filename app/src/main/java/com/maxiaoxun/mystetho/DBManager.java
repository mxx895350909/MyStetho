package com.maxiaoxun.mystetho;

import android.content.Context;

/**
 * Created by maxiaoxun on 17/9/22 14:42.
 * email maxiaoxun@meituan.com
 */

public class DBManager {

    private static DBManager instance;
    public DBHelper dbHelper;

    private DBManager(Context context){
        dbHelper = new DBHelper(context);
    }

    public static DBManager getInstance(Context context){
        if(instance == null){
            instance = new DBManager(context);
        }
        return instance;
    }


}
