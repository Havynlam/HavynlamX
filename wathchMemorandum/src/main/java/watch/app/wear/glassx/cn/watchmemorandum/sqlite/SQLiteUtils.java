package watch.app.wear.glassx.cn.watchmemorandum.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import watch.app.wear.glassx.cn.watchmemorandum.UserInfo;

/**
 * Created by Havynlam on 15/9/7.
 */
public class SQLiteUtils
{
    public static final String DATABASE_NAME = "memorandum_db";
    public static final String DATETIME = "datetime";
    public static final String CONTENT = "content";
    public static final String ALERTTIME= "alerttime";

    public SQLiteUtils() {
    }

    public static DatabaseHelper createDBHelper(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context, DATABASE_NAME);
        return dbHelper;
    }

    public void insert(DatabaseHelper dbHelper, UserInfo user) {
        ContentValues values = new ContentValues();
        values.put(DATETIME, user.getDatetime());
        values.put(CONTENT, user.getContent());
        values.put(ALERTTIME, user.getAlerttime());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("user", null, values);
        db.close();
    }

    public void update(DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", "update");
        db.update("user", values, "id=?", new String[]{"1"});
        db.close();
    }

    public void delete(DatabaseHelper dbHelper, String datetime) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("DELETE FROM user WHERE datetime=" + datetime);
        db.close();
    }
}
