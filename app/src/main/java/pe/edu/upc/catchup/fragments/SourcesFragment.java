package pe.edu.upc.catchup.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.catchup.R;
import pe.edu.upc.catchup.adapters.SourcesAdapter;
import pe.edu.upc.catchup.models.Source;
import pe.edu.upc.catchup.network.NewsApiService;

/**
 * A simple {@link Fragment} subclass.
 */
public class SourcesFragment extends Fragment {

    RecyclerView sourcesRecyclerView;
    RecyclerView.LayoutManager sourcesLayoutManager;
    SourcesAdapter sourcesAdapter;
    List<Source> sources;

    public SourcesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // LayoutInflater is a static class...
        View view = inflater.inflate(R.layout.fragment_sources, container, false);
        sourcesRecyclerView = (RecyclerView) view.findViewById(R.id.sourcesRecyclerView);
        sources = new ArrayList<>();
        sourcesAdapter = new SourcesAdapter(sources);
        sourcesLayoutManager = new GridLayoutManager(view.getContext(), 2);
        sourcesRecyclerView.setAdapter(sourcesAdapter);
        sourcesRecyclerView.setLayoutManager(sourcesLayoutManager);
        updateSources();
        return view;
    }

    private void updateSources() {
        AndroidNetworking.get(NewsApiService.SOURCES_URL)
                .addQueryParameter("language","en")
                .setPriority(Priority.LOW)
                .setTag(getString(R.string.app_name))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ("error".equalsIgnoreCase(response.getString("status"))){
                                Log.d(getString(R.string.app_name), response.getString("message"));
                                return;
                            }

                            sources = Source.from(response.getJSONArray("sources"));
                            sourcesAdapter.setSources(sources);
                            sourcesAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(getString(R.string.app_name), e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(getString(R.string.app_name), anError.getLocalizedMessage());
                    }
                });
    }

}
