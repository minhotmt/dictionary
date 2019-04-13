package com.example.minko.dictionaryclone.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minko.dictionaryclone.Activity.WebViewActivity;
import com.example.minko.dictionaryclone.Adapter.CustomAdapter;
import com.example.minko.dictionaryclone.Model.Nouns;
import com.example.minko.dictionaryclone.R;

import java.util.ArrayList;

public class NounFragment extends Fragment {

    // TODO: Rename and change types of parameters

    public NounFragment() {
        // Required empty public constructor
    }

    public static NounFragment newInstance(String param1, String param2) {
        NounFragment fragment = new NounFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        ArrayList<String> lstMenu = new ArrayList<>();
        String title = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            lstMenu = bundle.getStringArrayList("lstMenu");
            title = bundle.getString("titleMenu");
        }
        View view = inflater.inflate(R.layout.fragment_noun, container, false);
        ListView listView = (ListView) view.findViewById(R.id.lst_nouns);
        ArrayList<Nouns> arrNouns = new ArrayList<>();
        Nouns nouns;
        for (String item : lstMenu) {
            nouns = new Nouns(item);
            arrNouns.add(nouns);
        }
        ;
        CustomAdapter customAdaper = new CustomAdapter(getContext(), R.layout.row, arrNouns);
        listView.setAdapter(customAdaper);
        final String finalTitle = title;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                switch (finalTitle) {
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


}
