package watch.app.wear.glassx.cn.watchmemorandum;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import watch.app.wear.glassx.cn.watchmemorandum.adapter.MyTaskAdapter;
import watch.app.wear.glassx.cn.watchmemorandum.sqlite.DatabaseHelper;

public class MainActivity extends WearableActivity
{
    ArrayList<HashMap<String,String>> showlist,list = Utils.getList();
    DatabaseHelper dbHelper =new DatabaseHelper(MainActivity.this,"memorandum_db");




    private BoxInsetLayout mContainerView;
    private ListView mListView;
    private ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();


        initLoadDb();//初始化载入数据库的数据



        initView();
        initData();
    }

    private void initLoadDb()
    {

        list = Utils.getList();
        if(list.isEmpty())
            loadFromDatabase(list);  //先检查缓存，若没有数据再从数据库加载

    }

    private void initView()
    {
        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mListView = (ListView) findViewById(R.id.task);
        mImageButton = (ImageButton) findViewById(R.id.addTask);
    }

    private void initData()
    {
        mListView.setAdapter(new MyTaskAdapter(getApplicationContext()));
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                return false;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

            }
        });
        mImageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utils.DateToMillis(list);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                Bundle b = new Bundle();
                b.putString("datetime", "");
                b.putString("content", "");
                b.putString("alerttime","");
                intent.putExtra("android.intent.extra.INTENT", b);
                startActivity(intent);                                //启动转到的Activity





            }
        });
    }


    private void loadFromDatabase(ArrayList<HashMap<String,String>> list){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("user", new String[] { "datetime", "content","alerttime" }, null,
                null, null, null,"datetime desc");
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String datetime = cursor.getString(0);
                String content = cursor.getString(1);
                String alerttime = cursor.getString(2);
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("datetime", datetime);
                map.put("content", content);
                map.put("alerttime", alerttime);
                list.add(map);
            }
        }
    }
    @Override
    public void onEnterAmbient(Bundle ambientDetails)
    {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient()
    {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient()
    {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay()
    {
        if (isAmbient())
        {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));

        } else
        {
            mContainerView.setBackground(null);
        }
    }
}
