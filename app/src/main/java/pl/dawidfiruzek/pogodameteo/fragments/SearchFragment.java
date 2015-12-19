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
import pl.dawidfiruzek.pogodameteo.utils.CityListEntryDataHandler;

public class SearchFragment extends Fragment implements RecyclerViewEventHandler {

    private CustomRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<CityListEntryDataHandler> dataSet = new ArrayList<>();

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
        //TODO get db entries here
        CityListEntryDataHandler cityListEntry = new CityListEntryDataHandler();
        cityListEntry.setContent("Testow", "Testowo", "Testowski", ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_border_white_24dp));
        dataSet.add(cityListEntry);
    }

    private void setRecycler() {
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new CustomRecyclerViewAdapter(dataSet, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void handleOnClick(CityListEntryDataHandler element) {
        Log.e("Recycler", "Clicked" + element.cityName + element.cityRegion + element.cityDistrict);
    }
}
