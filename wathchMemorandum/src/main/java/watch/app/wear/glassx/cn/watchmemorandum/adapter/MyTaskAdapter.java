package watch.app.wear.glassx.cn.watchmemorandum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

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
    ArrayList<HashMap<String, String>> list = Utils.getList();



    public MyTaskAdapter(Context context)
    {
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {

        if(list!=null)
        {
          return list.size();
        }else
        {
            return 4;
        }
    }

    @Override
    public Object getItem(int position)
    {
        return position;
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



        return inflate;
    }
}
