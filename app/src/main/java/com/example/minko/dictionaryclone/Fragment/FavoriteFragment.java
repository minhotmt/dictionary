package com.example.minko.dictionaryclone.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.minko.dictionaryclone.R;
import com.example.minko.dictionaryclone.Service.DBFavoriteManager;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView mFovListView;
    private ArrayAdapter<String> mAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FavoriteFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        mFovListView = view.findViewById(R.id.lst_favor);
        ImageView imgFavor = view.findViewById(R.id.img_favor);
        ArrayList<String> favoriteList = new ArrayList<>();
        DBFavoriteManager dbFavoriteManager = new DBFavoriteManager(getContext());
        favoriteList = (ArrayList<String>) dbFavoriteManager.getAllFavoriteString();
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(getActivity(),
                    R.layout.item_favor,
                    R.id.txt_favor,
                    favoriteList);
            mFovListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(favoriteList);
            mAdapter.notifyDataSetChanged();
        }
        mFovListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                ImageView iconfavor = view.findViewById(R.id.img_favor);
                //same for image and any widgetn your adapter layout xml
                iconfavor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        //do what u want
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        DBFavoriteManager dbFavoriteManager = new DBFavoriteManager(getContext());
                        dbFavoriteManager.deleteFavoriteByName(""+parent.getItemAtPosition(position));
                    }
                });
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void deleteFavorite(View view) {
        View parent = (View) view.getParent();
        TextView favoriteTextView = (TextView) parent.findViewById(R.id.txt_favor);
        String favorite = String.valueOf(favoriteTextView.getText());
        DBFavoriteManager dbFavoriteManager = new DBFavoriteManager(getContext());
        dbFavoriteManager.deleteFavoriteByName(favorite);
    }
}
