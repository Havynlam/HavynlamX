package cn.glassx.wear.watch.Sedentary.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.glassx.wear.watch.Sedentary.R;

/**
 * Created by Havynlam on 15/9/28.
 */
public class DragSwitch extends View implements View.OnTouchListener
{

    private Bitmap bg_on;
    private Bitmap bg_off;
    private Bitmap btn_on;
    private Bitmap btn_off;

    private boolean nowStatus = false; // 当前的状态

    private OnChangedListener listener;

    public DragSwitch(Context context) {
        super(context);
        init();
    }

    public DragSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        // 载入图片资源
        bg_on = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_gongdiandi);
        bg_off = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_heidiandi);
        btn_on = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_hongdian);
        btn_off = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_heidian);

        setOnTouchListener(this);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float x = 0;

        // 根据nowStatus设置背景，开或者关状态
        if (nowStatus == false) {
            canvas.drawBitmap(bg_off, matrix, paint); // 画出关闭时的背景
            x = 0;
            canvas.drawBitmap(btn_off, x, 0, paint); // 画出关闭时的滑块
        } else {
            canvas.drawBitmap(bg_on, matrix, paint); // 画出打开时的背景
            x = bg_on.getWidth() - btn_on.getWidth();
            canvas.drawBitmap(btn_on, x, 0, paint); // 画出开启时的滑块
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (event.getX() > bg_off.getWidth() || event.getY() > bg_off.getHeight()) {
                    return false;
                } else {
                    return true;
                }
            }
            case MotionEvent.ACTION_UP: {
                nowStatus = !nowStatus;

                if (listener != null) {
                    listener.onChanged(DragSwitch.this, nowStatus);
                }
                invalidate(); // 刷新界面
                break;
            }
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 设置一个监听，供外部调用
     * @param listener
     */
    public void setOnChangedListener(OnChangedListener listener) {
        this.listener = listener;
    }

    /**
     * 设置滑动开关的初始状态，供外部调用
     */
    public void setChecked(boolean checked) {
        nowStatus = checked;
    }

    public boolean isChecked()
    {
        return nowStatus;
    }

    public interface OnChangedListener {
        void onChanged(DragSwitch dragSwitch, boolean checkState);
    }

}