package com.example.luca.progettoesame;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DrawerListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<NavItem> mNavItems;
    MainActivity mMainActivity;

    public DrawerListAdapter(Context context, ArrayList<NavItem> navItems, MainActivity mainActivity) {
        mContext = context;
        mNavItems = navItems;
        mMainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_item, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        final NavItem item = mNavItems.get(position);
        titleView.setText( item.mTitle );
        subtitleView.setText( item.mSubtitle );
        iconView.setImageResource(item.mIcon);
        View.OnClickListener watch = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", item.mCountryCode);

                mMainActivity.changeCities(item.mCountryCode, mContext);
            }
        };
        view.setOnClickListener(watch);
       return view;
    }
}
