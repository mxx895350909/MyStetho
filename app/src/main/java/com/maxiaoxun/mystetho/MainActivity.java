package com.maxiaoxun.mystetho;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "";
    private static int NETWORK = 1;

    private Button mBtnNetwork;
    private Button mBtnSP;
    private Button mBtnDB;


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            if (NETWORK == what) {
                Log.d(TAG, "network request success");
            }

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        mBtnNetwork = (Button) findViewById(R.id.btn_network);
        mBtnSP = (Button) findViewById(R.id.btn_sp);
        mBtnDB = (Button) findViewById(R.id.btn_db);

        mBtnNetwork.setOnClickListener(this);
        mBtnSP.setOnClickListener(this);
        mBtnDB.setOnClickListener(this);
    }

    private void getFromNetwork() {
        makeText(this, "Request Baidu", Toast.LENGTH_SHORT).show();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.networkInterceptors().add(new StethoInterceptor());
        Request request = new Request.Builder().url("http://www.baidu.com").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String body = response.body().string();
                Message message = handler.obtainMessage();
                message.what = NETWORK;
                message.obj = body;
                handler.sendMessage(message);
            }
        });
    }

    private void setSP() {
        SharedPreferences sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        sp.edit().putString("name", "Tony").putInt("age", 18).apply();
        makeText(this, "name:Tony\nage:18", Toast.LENGTH_SHORT).show();
    }

    private void setDB() {
        new MyStethoAsyncTask().execute();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_network) {
            Log.d(TAG, "btn_network clicked");
            getFromNetwork();

        } else if (id == R.id.btn_sp) {
            Log.d(TAG, "btn_sp clicked");
            setSP();

        } else if (id == R.id.btn_db) {
            Log.d(TAG, "btn_db clicked");
            setDB();
        }
    }

    private class MyStethoAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            SQLiteDatabase db = DBManager.getInstance(MainActivity.this).dbHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("Name", "Tony");
            contentValues.put("Age", 18);
            db.insertWithOnConflict(DBHelper.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MainActivity.this, "Name:Tony\nAge:18", Toast.LENGTH_SHORT).show();
        }
    }
}
