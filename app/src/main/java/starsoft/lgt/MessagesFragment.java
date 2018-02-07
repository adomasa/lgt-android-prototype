package starsoft.lgt;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import starsoft.lgt.adapters.MessagesAdapter;
import starsoft.lgt.model.Message;
import starsoft.lgt.model.demo.MessagesDemo;
import starsoft.lgt.utils.OnFragmentInteractionListener;

public class MessagesFragment extends Fragment {

    public static final String TAG = "MessagesFragment";

    private OnFragmentInteractionListener mListener;

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages_list, container, false);
        getActivity().setTitle("Pranešimai");
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MessagesAdapter(MessagesDemo.ITEMS, mListener));
        }
        ((MainActivity)getActivity()).showHomeAsUp(false);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
