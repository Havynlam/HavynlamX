package watch.app.wear.glassx.cn.watchmemorandum;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sogou.speech.offlinelistener.OutsideCallListener;
import com.sogou.speech.offlineutility.CoreControl;

/**
 * Created by Havyn on 2015/9/8.
 */
public class EditActivity extends WearableActivity implements OutsideCallListener
{
    private TextView mResultTextView;
    private Button mRecordButton;
    private BoxInsetLayout mContainerView;
    String appId = "ALUG8834";
    String accessKey = "A60zv9tl";

    private boolean isUIInit = false;
    private boolean mListening = false;
    private String mResult = "";// 识别结果，边说边出识别结果
    private String versonString = "";// 离线语音识别service版本号
    private boolean isServiceConnected = false;// 标识离线语音识别service是否可用

    // useful parameters for CoreControl
    CoreControl mControl = null;
    private boolean mIsLast = false;// 识别结束标记


    private static final String SOGOU_OFFLINE_SERVICE = "http://dl.pinyin.sogou.com/wapdl/offlinespeech/service.apk";
    private static final int MSG_ON_SERVICE_CONNECTED = 0;
    private static final int MSG_ON_FINISH_INIT = 1;
    private static final int MSG_ON_PASS_VALIDATION = 2;
    private static final int MSG_ON_RECOGNITION_START = 3;
    private static final int MSG_ON_UPDATE_VOLUME = 4;
    private static final int MSG_ON_SPEECH_END = 5;
    private static final int MSG_ON_RESULTS = 6;
    private static final int MSG_ON_ERROR = 7;

    private final int ERR_SERVICE_NOT_EXIST = 1;
    private final int ERR_USE_SERVICE_FAILED = 2;
    private final int ERR_NETWORK_IS_UNAVAILABLE = 3;
    private final int ERR_KEY_IS_INVALID = 4;
    private final int ERR_RECOGNIZE_FAILED = 5;
    private final int ERR_NO_SPEECH = 6;
    private final int ERR_NO_RESULT = 7;
    private final int ERR_AUDIO = 8;


    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case MSG_ON_SERVICE_CONNECTED:
                    printf("MSG_ON_SERVICE_CONNECTED");
                    if (isUIInit == false)
                    {
                        initUI();
                        isUIInit = true;
                    }

                    isServiceConnected = true;
                    break;

                case MSG_ON_FINISH_INIT:
                    printf("MSG_ON_FINISH_INIT");
                    if (isUIInit == false)
                    {
                        initUI();
                        isUIInit = true;
                    }
                    resetUI();
                    break;

                case MSG_ON_PASS_VALIDATION:
                    mListening = true;
                    break;

                case MSG_ON_RECOGNITION_START:
                    printf("MSG_ON_RECOGNITION_START");
                    mResult = "";
                    mIsLast = false;
                    break;

                case MSG_ON_UPDATE_VOLUME:
                    printf("MSG_ON_UPDATE_VOLUME");
                    break;

                case MSG_ON_SPEECH_END:
                    printf("MSG_ON_SPEECH_END");
                    waitingUI();
                    break;

                case MSG_ON_RESULTS:
                    printf("MSG_ON_RESULTS");
                    if (mIsLast)
                    {
                        resetUI();
                    } else
                    {
                        mResultTextView.setText(mResult);
                    }
                    break;

                case MSG_ON_ERROR:
                    printf("MSG_ON_ERROR");
                    if (isUIInit == false)
                    {
                        initUI();
                        isUIInit = true;
                    }

                    int errCode = msg.arg1;
                    switch (errCode)
                    {
                        case ERR_SERVICE_NOT_EXIST:
                            mResult = "ERROR:" + "Service不存在，请访问如下URL进行下载：\n"
                                    + SOGOU_OFFLINE_SERVICE;
                            break;

                        case ERR_USE_SERVICE_FAILED:
                            mResult = "ERROR:" + "使用Service失败";
                            break;
                        case ERR_NETWORK_IS_UNAVAILABLE:
                            mResult = "ERROR:" + "联网认证失败";
                            break;
                        case ERR_KEY_IS_INVALID:
                            mResult = "ERROR:" + "appId或accessKey错误";
                            break;
                        case ERR_RECOGNIZE_FAILED:
                            mResult = "ERROR:" + "语音识别失败";
                            break;
                        case ERR_NO_SPEECH:
                            mResult = "ERROR:" + "检测不到有效声音";
                            break;
                        case ERR_NO_RESULT:
                            mResult = "ERROR:" + "无识别结果";
                            break;
                        case ERR_AUDIO:
                            mResult = "ERROR:" + "录音异常";
                            break;
                        default:
                            mResult = "ERROR:" + "未知错误";
                            break;
                    }

                    resetUI();
                    break;

                default:
                    printf("default");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setAmbientEnabled();

        // initialize CoreControl
        Context mContext = getApplicationContext();
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mControl = new CoreControl(mContext, mTelephonyManager, appId,
                                   accessKey);
        mControl.setmListener(EditActivity.this);
        mControl.onInitializeService();    //报错
    }

    @Override
    protected void onDestroy()
    {
        // destroy Service
        if (mControl != null)
        {
            mControl.onDestroyService();
            mControl = null;
        }

        super.onDestroy();
    }

    private void initUI()
    {
        printf("initUI");
        bindViews();

        mRecordButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (mListening)
                {
                    stopListening();
                } else
                {
                    // reset mResult when necessary!!!
                    mResult = "";
                    mResultTextView.setText(mResult);
                    startListening();
                }
            }
        });


        mRecordButton.setEnabled(false);
        mResultTextView.setText(getString(R.string.hello_world) + versonString);
    }

    private void resetUI()
    {
        printf("resetUI");
        mResultTextView.setText(mResult);
        mRecordButton.setEnabled(true);

        mListening = false;
    }

    private void waitingUI()
    {
        printf("waitingUI");
        if (mListening == true)
        {
            mRecordButton.setEnabled(false);
        }
    }


    private void bindViews()
    {
        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mRecordButton = (Button) findViewById(R.id.btnRecord);
        mResultTextView = (TextView) findViewById(R.id.txtResult);
    }

    private void startListening()
    {
        if (isServiceConnected == false || mControl == null
                || mControl.startListening() == false)
        {
            return;
        }
    }

    private void stopListening()
    {
        if (isServiceConnected == false || mControl == null
                || mControl.stopListening() == false)
        {
            return;
        }

        waitingUI();
    }

    private void cancelListening()
    {
        if (isServiceConnected == false || mControl == null
                || mControl.cancelListening() == false)
        {
            return;
        }

        waitingUI();

        mResultTextView.setText("已取消");
        mRecordButton.setEnabled(true);

        mListening = false;
    }

    // implements of OutsideCallListener
    public void onServiceConnected(String version)
    {
        Message msg = mHandler.obtainMessage(MSG_ON_SERVICE_CONNECTED);
        versonString = version;
        msg.sendToTarget();
    }

    public void onFinishInit()
    {
        Message msg = mHandler.obtainMessage(MSG_ON_FINISH_INIT);
        msg.sendToTarget();
    }

    public void onPassedValidation()
    {
        Message msg = mHandler.obtainMessage(MSG_ON_PASS_VALIDATION);
        msg.sendToTarget();
    }

    public void onRecognitionStart()
    {
        Message msg = mHandler.obtainMessage(MSG_ON_RECOGNITION_START);
        msg.sendToTarget();
    }

    public void onUpdateVolume(int volumeValue)
    {
        Message msg = mHandler.obtainMessage(MSG_ON_UPDATE_VOLUME);
        msg.arg1 = volumeValue;
        msg.sendToTarget();
    }

    public void onSpeechEnd()
    {
        Message msg = mHandler.obtainMessage(MSG_ON_SPEECH_END);
        msg.sendToTarget();
    }

    public void onResults(String results, boolean isLast)
    {
        Message msg = mHandler.obtainMessage(MSG_ON_RESULTS);
        mIsLast = isLast;// 最后一个请求包的标记
        mResult += results;// 连续语音识别，结果需要累加
        msg.sendToTarget();
    }

    public void onError(int errorCode)
    {
        Message msg = mHandler.obtainMessage(MSG_ON_ERROR);
        msg.arg1 = errorCode;
        msg.sendToTarget();
    }

    // just for debug!!!
    private static final String TAG = "OfflineClientActivity";
    private static final boolean DEBUG = false;

    private void printf(String str)
    {
        if (DEBUG)
        {
            Log.e(TAG, "###################------ " + str + "------");
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
















