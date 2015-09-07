package watch.app.wear.glassx.cn.watchmemorandum;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import watch.app.wear.glassx.cn.watchmemorandum.adapter.MyTaskAdapter;

public class MainActivity extends WearableActivity
{

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private ListView mListView;
    private ImageButton mImageButton;

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
        mListView = (ListView) findViewById(R.id.task);
        mImageButton = (ImageButton) findViewById(R.id.addTask);
    }

    private void initData()
    {
        mListView.setAdapter(new MyTaskAdapter(getApplicationContext()));
        mImageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
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
