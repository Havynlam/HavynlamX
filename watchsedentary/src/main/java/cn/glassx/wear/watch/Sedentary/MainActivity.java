package cn.glassx.wear.watch.Sedentary;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity
{

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.CHINA);

    private BoxInsetLayout mContainerView;
    private TextView       mTextView;
    private Switch         mSwitch;
    private RelativeLayout mRelativeLayout;
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

    private void initData()
    {

        mSwitch.setChecked(mToggle);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    mToggle =true;
                    mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.oBackgroundColor));
                    mTextView.setText(getResources().getString(R.string.oRemind));
                }else
                {
                    mToggle = false;
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
        mSwitch = (Switch) findViewById(R.id.sedentarySc);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.sedentaryBg);
    }


}
