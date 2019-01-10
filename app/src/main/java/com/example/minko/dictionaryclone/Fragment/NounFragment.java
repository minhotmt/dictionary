package com.example.minko.dictionaryclone.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.minko.dictionaryclone.Adapter.CustomAdapter;
import com.example.minko.dictionaryclone.Model.Nouns;
import com.example.minko.dictionaryclone.R;
import com.example.minko.dictionaryclone.Activity.WebViewActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NounFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NounFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NounFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NounFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NounFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NounFragment newInstance(String param1, String param2) {
        NounFragment fragment = new NounFragment();
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
        ArrayList<String> lstMenu = new ArrayList<>();
        String title = null;
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            lstMenu = bundle.getStringArrayList("lstMenu");
            title = bundle.getString("titleMenu");
        }
        View view = inflater.inflate(R.layout.fragment_noun, container, false);
        ListView listView = (ListView)view.findViewById(R.id.lst_nouns);
        ArrayList<Nouns> arrNouns = new ArrayList<>();
        Nouns nouns;
        for (String item:lstMenu){
            nouns = new Nouns(item);
            arrNouns.add(nouns);
        };
        CustomAdapter customAdaper = new CustomAdapter(getContext(),R.layout.row,arrNouns);
        listView.setAdapter(customAdaper);
        final String finalTitle = title;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                switch (finalTitle){
                    case "Nouns":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/noun/general_information.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/noun/plurals.html");
                        } else if (position == 2) {
                            intent.putExtra("url", "file:///android_asset/noun/nouns_in_sentence.html");
                        }
                        break;
                    case "Article":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/article/indefinite_article.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/article/definite_article.html");
                        } else if (position == 2) {
                            intent.putExtra("url", "file:///android_asset/article/no_article.html");
                        }
                        break;
                    case "Pronouns":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/pronoun/general_information.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/pronoun/the_use_of_pronouns.html");
                        } else if (position == 2) {
                            intent.putExtra("url", "file:///android_asset/pronoun/pronouns_in_sentence.html");
                        }
                        break;
                    case "Numberals":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/numberal/general_information.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/numberal/cardinal_numberals.html");
                        } else if (position == 2) {
                            intent.putExtra("url", "file:///android_asset/numberal/ordinal_numberals.html");
                        } else if (position == 3) {
                            intent.putExtra("url", "file:///android_asset/numberal/numberals_in_sentence.html");
                        }
                        break;
                    case "Adjective":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/adjective/general_information.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/adjective/degrees_of_comparison.html");
                        } else if (position == 2) {
                            intent.putExtra("url", "file:///android_asset/adjective/adjective_in_sentence.html");
                        }
                        break;
                    case "Adverb":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/adverb/general_information.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/adverb/degrees_of_comparison.html");
                        } else if (position == 2) {
                            intent.putExtra("url", "file:///android_asset/adverb/classification_of_adverbs.html");
                        } else if (position == 3) {
                            intent.putExtra("url", "file:///android_asset/adverb/adverb_in_sentence.html");
                        }
                        break;
                    case "Verb":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/verb/general_information.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/verb/personal_impersonal_verbs.html");
                        } else if (position == 2) {
                            intent.putExtra("url", "file:///android_asset/verb/regular_irregular_verbs.html");
                        } else if (position == 3) {
                            intent.putExtra("url", "file:///android_asset/verb/transitive_intransitive_verbs.html");
                        } else if (position == 4) {
                            intent.putExtra("url", "file:///android_asset/verb/person_and_number.html");
                        }
                        break;
                    case "Preposition":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/preposition/general_information.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/preposition/frequently_used.html");
                        } else if (position == 2) {
                            intent.putExtra("url", "file:///android_asset/preposition/preposition_in_sentence.html");
                        }
                        break;
                    case "Conjunction":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/conjunction/general_information.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/conjunction/coordinative_conjunctions.html");
                        } else if (position == 2) {
                            intent.putExtra("url", "file:///android_asset/conjunction/subodinative_conjunctions.html");
                        }
                        break;
                    case "Particles":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/particles/general_information.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/particles/difference_of_particles.html");
                        }
                        break;
                    case "Interjection":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/interjection/general_information.html");
                        }
                        break;
                    case "Book1":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/book1/common_phrases.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/book1/meeting_communication.html");
                        } else if (position == 2) {
                            intent.putExtra("url", "file:///android_asset/book1/trip_journey.html");
                        } else if (position == 3) {
                            intent.putExtra("url", "file:///android_asset/book1/hotel_service.html");
                        } else if (position == 4) {
                            intent.putExtra("url", "file:///android_asset/book1/cafe_restaurant.html");
                        } else if (position == 5) {
                            intent.putExtra("url", "file:///android_asset/book1/city_transport.html");
                        } else if (position == 6) {
                            intent.putExtra("url", "file:///android_asset/book1/telephone_mail.html");
                        } else if (position == 7) {
                            intent.putExtra("url", "file:///android_asset/book1/bank_money.html");
                        } else if (position == 8) {
                            intent.putExtra("url", "file:///android_asset/book1/business_communication.html");
                        } else if (position == 9) {
                            intent.putExtra("url", "file:///android_asset/book1/health.html");
                        } else if (position == 10) {
                            intent.putExtra("url", "file:///android_asset/book1/problems.html");
                        } else if (position == 11) {
                            intent.putExtra("url", "file:///android_asset/book1/miscellaneous.html");
                        }
                        break;
                    case "Book2":
                        if (position == 0) {
                            intent.putExtra("url", "file:///android_asset/book2/general_expressions.html");
                        } else if (position == 1) {
                            intent.putExtra("url", "file:///android_asset/book2/conversation.html");
                        } else if (position == 2) {
                            intent.putExtra("url", "file:///android_asset/book2/about_me.html");
                        } else if (position == 3) {
                            intent.putExtra("url", "file:///android_asset/book2/trip.html");
                        } else if (position == 4) {
                            intent.putExtra("url", "file:///android_asset/book2/in_the_city.html");
                        } else if (position == 5) {
                            intent.putExtra("url", "file:///android_asset/book2/hotel.html");
                        } else if (position == 6) {
                            intent.putExtra("url", "file:///android_asset/book2/services.html");
                        } else if (position == 7) {
                            intent.putExtra("url", "file:///android_asset/book2/in_guests.html");
                        } else if (position == 8) {
                            intent.putExtra("url", "file:///android_asset/book2/money_business.html");
                        } else if (position == 9) {
                            intent.putExtra("url", "file:///android_asset/book2/food_and_drinks.html");
                        } else if (position == 10) {
                            intent.putExtra("url", "file:///android_asset/book2/shopping.html");
                        } else if (position == 11) {
                            intent.putExtra("url", "file:///android_asset/book2/entertainment.html");
                        } else if (position == 12) {
                            intent.putExtra("url", "file:///android_asset/book2/sport.html");
                        } else if (position == 13) {
                            intent.putExtra("url", "file:///android_asset/book2/journey.html");
                        } else if (position == 14) {
                            intent.putExtra("url", "file:///android_asset/book2/transport.html");
                        } else if (position == 15) {
                            intent.putExtra("url", "file:///android_asset/book2/phone_mail.html");
                        } else if (position == 16) {
                            intent.putExtra("url", "file:///android_asset/book2/health.html");
                        } else if (position == 17) {
                            intent.putExtra("url", "file:///android_asset/book2/problems.html");
                        } else if (position == 18) {
                            intent.putExtra("url", "file:///android_asset/book2/time_and_date.html");
                        } else if (position == 19) {
                            intent.putExtra("url", "file:///android_asset/book2/colours.html");
                        } else if (position == 20) {
                            intent.putExtra("url", "file:///android_asset/book2/weather.html");
                        } else if (position == 21) {
                            intent.putExtra("url", "file:///android_asset/book2/questions.html");
                        } else if (position == 22) {
                            intent.putExtra("url", "file:///android_asset/book2/inscriptions_and_signs.html");
                        }
                        break;
                        default:
                            break;

                }

                getActivity().startActivity(intent);
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
}
