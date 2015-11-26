package pl.dawidfiruzek.pogodameteo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.dawidfiruzek.pogodameteo.R;
import pl.dawidfiruzek.pogodameteo.adapters.CustomRecyclerViewAdapter;
import pl.dawidfiruzek.pogodameteo.interfaces.RecyclerViewEventHandler;
import pl.dawidfiruzek.pogodameteo.views.CityListEntryView;

public class SearchFragment extends Fragment implements RecyclerViewEventHandler {

    private CustomRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<CityListEntryView> dataSet = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.cities_list_from_db);
        setUpDataSet();
        setRecycler();
        return v;
    }

    private void setUpDataSet() {
        CityListEntryView cityListEntryView = new CityListEntryView(getContext());
        cityListEntryView.setContent("Testow", "Testowo", "Testowski", ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_border_white_24dp));
        dataSet.add(cityListEntryView);
    }

    private void setRecycler() {
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new CustomRecyclerViewAdapter(dataSet, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void handleOnClick(CityListEntryView element) {
        Log.e("Recycler", "Clicked" + element.getCity() + element.getRegion() + element.getDistrict());
    }
}
