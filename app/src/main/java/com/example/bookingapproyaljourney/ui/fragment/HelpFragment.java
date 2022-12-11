package com.example.bookingapproyaljourney.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.event.KeyEvent;
import com.example.bookingapproyaljourney.ui.adapter.RevenueAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpFragment extends Fragment {
    MaterialToolbar toolbar;
    ExpandableListView listView;
    List<String> Title;
    HashMap<String, List<String>> topics;
    RevenueAdapter setAdapter;
    EditText etSearch;
    TextView contentText;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HelpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HelpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HelpFragment newInstance(String param1, String param2) {
        HelpFragment fragment = new HelpFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.list_support);
        etSearch = view.findViewById(R.id.etSearch);
        contentText = view.findViewById(R.id.contentText);
        fillData();


        setAdapter = new RevenueAdapter(getContext(), Title, topics);
        setAdapter.setCallback(new RevenueAdapter.Callback() {
            @Override
            public void onCLick(int position) {
                EventBus.getDefault().postSticky(new KeyEvent(AppConstant.BY_USER_NEW));
            }
        });
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferences.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        listView.setAdapter(setAdapter);
        check();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setAdapter.getFilter().filter(etSearch.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setAdapter.getFilter().filter(etSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                setAdapter.getFilter().filter(etSearch.getText().toString());
            }
        });
    }

    private void check() {
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int check = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (check != -1 && groupPosition != check) {
                    listView.collapseGroup(check);
                }
                check = groupPosition;
            }
        });
    }

    public void fillData() {
        Title = new ArrayList<>();
        topics = new HashMap<>();
        Title.add(getString(R.string.question_help1));
        Title.add(getString(R.string.question_help2));
        Title.add(getString(R.string.question_help3));
        Title.add(getString(R.string.question_help4));
        Title.add(getString(R.string.question_help5));
        Title.add(getString(R.string.question_help6));
        Title.add(getString(R.string.question_help7));
        Title.add(getString(R.string.question_help8));
        Title.add(getString(R.string.question_help9));
        Title.add(getString(R.string.question_help10));

        List<String> c1 = new ArrayList<>();
        c1.add(getString(R.string.question_help11));
        c1.add(getString(R.string.question_help12));
        c1.add(getString(R.string.question_help13));
        c1.add(getString(R.string.question_help14));
        List<String> c2 = new ArrayList<>();
        c2.add(getString(R.string.question_help21));
        c2.add(getString(R.string.question_help22));
        List<String> c3 = new ArrayList<>();
        c3.add(getString(R.string.question_help31));
        c3.add(getString(R.string.question_help32));
        c3.add(getString(R.string.question_help33));
        List<String> c4 = new ArrayList<>();
        c4.add(getString(R.string.question_help41));
        c4.add(getString(R.string.question_help42));
        List<String> c5 = new ArrayList<>();
        c5.add(getString(R.string.question_help51));
        List<String> c6 = new ArrayList<>();
        c6.add(getString(R.string.question_help61));
        List<String> c7 = new ArrayList<>();
        c7.add(getString(R.string.question_help71));
        c7.add(getString(R.string.question_help72));
        c7.add(getString(R.string.question_help73));
        List<String> c8 = new ArrayList<>();
        c8.add(getString(R.string.question_help81));
        List<String> c9 = new ArrayList<>();
        c9.add(getString(R.string.question_help91));
        c9.add(getString(R.string.question_help92));
        c9.add(getString(R.string.question_help93));
        List<String> c10 = new ArrayList<>();
        c10.add(getString(R.string.question_help101));
        topics.put(Title.get(0), c1);
        topics.put(Title.get(1), c2);
        topics.put(Title.get(2), c3);
        topics.put(Title.get(3), c4);
        topics.put(Title.get(4), c5);
        topics.put(Title.get(5), c6);
        topics.put(Title.get(6), c7);
        topics.put(Title.get(7), c8);
        topics.put(Title.get(8), c9);
        topics.put(Title.get(9), c10);
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            setAdapter.setColor(Color.WHITE);
            contentText.setTextColor(Color.WHITE);
        } else {
            setAdapter.setColor(Color.BLACK);
            contentText.setTextColor(Color.BLACK);
        }
    }
}