package cn.glassx.wear.watch.Sedentary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by Havyn on 2015/8/23.
 */
public class AlarmReceiver extends BroadcastReceiver
{
    Vibrator mVibrator;

    @Override
    public void onReceive(Context context, Intent intent)
    {

        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mVibrator.vibrate(2000);
        Toast.makeText(context, " - --- -", Toast.LENGTH_LONG).show();
    }
}