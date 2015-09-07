package watch.app.wear.glassx.cn.watchmemorandum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import watch.app.wear.glassx.cn.watchmemorandum.R;
import watch.app.wear.glassx.cn.watchmemorandum.Utils;

/**
 * Created by Havynlam on 15/9/6.
 */
public class MyTaskAdapter extends BaseAdapter
{
    private TextView mTaskTextView;
    private TextView mTimeTextView;
    private LayoutInflater mLayoutInflater;




    public MyTaskAdapter(Context context)
    {
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {

        if(Utils.getList()!=null)
        {
          return Utils.getList().size();
        }else
        {
            return 3;
        }
    }

    @Override
    public Object getItem(int position)
    {

        return Utils.getList().get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View inflate = mLayoutInflater.inflate(R.layout.task_list,null);
        mTaskTextView = (TextView) inflate.findViewById(R.id.task_text);
        mTimeTextView = (TextView) inflate.findViewById(R.id.task_text);

        mTaskTextView.setText(Utils.getItem(position).get("content"));
        mTimeTextView.setText(Utils.getItem(position).get("datetime"));
        return inflate;
    }
}
