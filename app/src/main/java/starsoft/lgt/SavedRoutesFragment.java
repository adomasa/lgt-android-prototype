package starsoft.lgt;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import starsoft.lgt.adapters.SavedRoutesAdapter;
import starsoft.lgt.model.demo.SavedRoutesDemo;
import starsoft.lgt.utils.OnFragmentInteractionListener;

public class SavedRoutesFragment extends Fragment {

    public static final String TAG = "SavedRoutesFragment";

    private OnFragmentInteractionListener mFragmentInteractionListener;

    public static SavedRoutesFragment newInstance() {
        return new SavedRoutesFragment();
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
            recyclerView.setAdapter(new SavedRoutesAdapter(SavedRoutesDemo.ITEMS, mFragmentInteractionListener));
        }
        ((MainActivity)getActivity()).showHomeAsUp(false);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mFragmentInteractionListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentInteractionListener = null;
    }

}
