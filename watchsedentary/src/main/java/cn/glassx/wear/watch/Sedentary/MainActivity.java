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
    private void initView()
    {
        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mSwitch = (DragSwitch) findViewById(R.id.sedentarySc);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.sedentaryBg);

    }
    @SuppressWarnings("deprecation")
    private void initData()
    {
        mSharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        final SharedPreferences.Editor edit = mSharedPreferences.edit();

        mSwitch.setChecked(mSharedPreferences.getBoolean("mToggle", mToggle));
        if (mSharedPreferences.getBoolean("mToggle", mToggle))
        {
            setStatus(R.color.oBackgroundColor, R.string.oRemind);
            setTime();
        }else
        {
            setStatus(R.color.cBackgroundColor, R.string.cRemind);
        }

        mSwitch.setOnChangedListener(new DragSwitch.OnChangedListener()
        {
            @Override
            public void onChanged(DragSwitch dragSwitch, boolean checkState)
            {
                if (checkState)
                {
                    mToggle = true;
                    edit.putBoolean("mToggle", mToggle);
                    edit.commit();
                    setStatus(R.color.oBackgroundColor, R.string.oRemind);
                    dayOfWeek();

                } else
                {
                    mToggle = false;
                    setStatus(R.color.cBackgroundColor, R.string.cRemind);
                    edit.putBoolean("mToggle", mToggle);
                    edit.commit();

                }

            }
        });
    }

    private void setStatus(int mColor, int mText)
    {
        mRelativeLayout.setBackgroundColor(getResources().getColor(mColor));
        mTextView.setText(mText);
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
        sendBroadcast(setClock(9));
//        sendBroadcast( setClock(10));
//        sendBroadcast(setClock(11));
//        sendBroadcast(setClock(15));
//        sendBroadcast( setClock(16));
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
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, time, sender);
    }
}
