package pl.dawidfiruzek.pogodameteo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.dawidfiruzek.pogodameteo.utils.NavigationListItem;
import pl.dawidfiruzek.pogodameteo.R;

/**
 * Created by fks on 2015-05-19.
 */
public class DrawerListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<NavigationListItem> mNavItems;

    public DrawerListAdapter(Context context, ArrayList<NavigationListItem> navItems){
        mContext = context;
        mNavItems = navItems;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }
        else{
            view = convertView;
        }
        //title
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(mNavItems.get(position).getTitle());

        //subtitle
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        subtitleView.setText(mNavItems.get(position).getSubtitle());

        //image
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        iconView.setImageResource(mNavItems.get(position).getIcon());

        return view;
    }
}
