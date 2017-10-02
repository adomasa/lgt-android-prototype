package starsoft.litrail_android.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import starsoft.litrail_android.MainActivity;

import starsoft.litrail_android.R;
import starsoft.litrail_android.Model.SavedRoute;
import starsoft.litrail_android.Model.SavedRoutes;

public class SavedRoutesFragment extends Fragment {

    public static final String TAG = "SavedRoutesFragment";
    public String departureStation;
    public String arrivalStation;

    private OnListFragmentInteractionListener mListener;

    public SavedRoutesFragment() {
    }

    public static SavedRoutesFragment newInstance() {
        SavedRoutesFragment fragment = new SavedRoutesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes_list, container, false);
        getActivity().setTitle("Išsaugoti maršrutai");
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new SavedRoutesRecyclerViewAdapter(SavedRoutes.ITEMS, mListener));
        }
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(SavedRoute route);
    }
}
