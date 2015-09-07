package watch.app.wear.glassx.cn.watchmemorandum.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Havynlam on 15/9/7.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final int VERSION = 1;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    public DatabaseHelper(Context context,String name,int version)
    {
        this(context, name, null, version);
    }
    public DatabaseHelper(Context context, String name) {
        this(context, name, 1);
    }

        @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table user(datetime varchar(30),content varchar(100),alerttime varchar(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
