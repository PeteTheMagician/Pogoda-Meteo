package pl.dawidfiruzek.pogodameteo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.dawidfiruzek.pogodameteo.interfaces.RecyclerViewEventHandler;
import pl.dawidfiruzek.pogodameteo.views.CityListEntryView;

/**
 * Created by fks on 2015-11-25.
 */
public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {
    public RecyclerViewEventHandler delegate;
    private List<CityListEntryView> dataSet = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CityListEntryView cityListEntryView;

        public ViewHolder(CityListEntryView v){
            super(v);
            cityListEntryView = v;
        }
    }

    public CustomRecyclerViewAdapter(List<CityListEntryView> dataSet, RecyclerViewEventHandler delegate) {
        this.dataSet = dataSet;
        this.delegate = delegate;
    }

    @Override
    public CustomRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CityListEntryView view = new CityListEntryView(parent.getContext());

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomRecyclerViewAdapter.ViewHolder holder, final int position) {

        holder.cityListEntryView.setContent(dataSet.get(position));

        holder.cityListEntryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.handleOnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
