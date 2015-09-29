package cn.glassx.wear.watch.Sedentary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cn.glassx.wear.watch.Sedentary.customView.DragSwitch;
import cn.glassx.wear.watch.Sedentary.receiver.AlarmReceiver;

public class MainActivity extends WearableActivity
{
    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private DragSwitch mSwitch;
    private RelativeLayout mRelativeLayout;
    private SharedPreferences mSharedPreferences;
    private boolean mToggle = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();


        initView();
        initData();


    }

    @SuppressWarnings("deprecation")
    private void initData()

    {
//        mSharedPreferences=getSharedPreferences("config", MODE_PRIVATE);
//        final SharedPreferences.Editor edit = mSharedPreferences.edit();
//        edit.putBoolean("mToggle", mToggle);
        mSwitch.setChecked(false);
        mSwitch.setOnChangedListener(new DragSwitch.OnChangedListener()
        {
            @Override
            public void onChanged(DragSwitch dragSwitch, boolean checkState)
            {
                if (checkState)
                {
                    mToggle=true;
                    mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.oBackgroundColor));
                    mTextView.setText(getResources().getString(R.string.oRemind));

                    dayOfWeek();

                } else
                {
                    mToggle=false;
                    mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.cBackgroundColor));
                    mTextView.setText(getResources().getString(R.string.cRemind));

                }

            }
        });
    }





    private void initView()
    {
        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mSwitch = (DragSwitch) findViewById(R.id.sedentarySc);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.sedentaryBg);
    }

    private void dayOfWeek()
    {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek)
        {
            case 1:
//星期天
                break;
            case 2:
                setTime();
                break;
            case 3:
                setTime();
                break;
            case 4:
                setTime();
                break;
            case 5:
                setTime();
                break;
            case 6:
                setTime();
                break;
            case 7:

                break;
        }
    }

    private void setTime()
    {
        long set1 = setClock(9);
        long set2 = setClock(10);
        long set3 = setClock(11);
        long set4 = setClock(15);
        long set5 = setClock(16);
        sendBroadcast(set1);
        sendBroadcast(set2);
        sendBroadcast(set3);
        sendBroadcast(set4);
        sendBroadcast(set5);
    }

    private long setClock(int mHour)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        long setTime = calendar.getTimeInMillis();
        return setTime;
    }

    private void sendBroadcast(long time)
    {
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, time, sender);
    }
}
