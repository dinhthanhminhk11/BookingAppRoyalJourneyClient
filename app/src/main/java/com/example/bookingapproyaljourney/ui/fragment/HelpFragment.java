package com.example.bookingapproyaljourney.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.ui.adapter.RevenueAdapter;
import com.google.android.material.appbar.MaterialToolbar;

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
        fillData();


        setAdapter = new RevenueAdapter(getContext(),Title,topics);
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
                if(check != -1 && groupPosition != check){
                    listView.collapseGroup(check);
                }
                check = groupPosition;
            }
        });
    }
    public void fillData(){
        Title = new ArrayList<>();
        topics = new HashMap<>();

        Title.add("Các câu hỏi thường gặp");
        Title.add("Thông tin chung");
        Title.add("Tìm khách sạn");
        Title.add("Đặt phòng khách sạn");
        Title.add("Giá phòng khách sạn");
        Title.add("Bảo hiểm du lịch");
        Title.add("Hủy đặt chỗ, đổi phòng & hoàn tiền");
        Title.add("Tiện nghi khách sạn");
        Title.add("Yêu cầu đặc biệt");
        Title.add("Chính sách khách sạn");

        List<String> c1 = new ArrayList<>();
        c1.add("Cách hủy phòng và hoàn tiền cho đặt phòng khách sạn");
        c1.add("Làm thế nào tôi có thể gửi lại vé điện tử của mình");
        c1.add("Cách đặt khách sạn");
        c1.add("Làm cách nào để kiểm tra trạng thái hoàn tiền của tôi");
        List<String> c2 = new ArrayList<>();
        c2.add("Quan hệ đối tác của MyVntour với khách sạn");
        c2.add("Cách đăng ký nơi lưu trú của tôi trên MyVntour");
        List<String> c3 = new ArrayList<>();
        c3.add("Làm thế nào để tôi tìm được khách sạn ưng ý nhất cho điểm đến của mình");
        c3.add("Tìm khách sạn trong một khu vực nhất định");
        c3.add("Không hoàn tiền và miễn phí đặt phòng nghĩa là gì");
        List<String> c4 = new ArrayList<>();
        c4.add("Cách đặt khách sạn");
        c4.add("Cách đổi lịch đặt phòng khách sạn của tôi");
        List<String> c5 = new ArrayList<>();
        c5.add("Giá phòng khách sạn bao gồm những gì");
        List<String> c6 = new ArrayList<>();
        c6.add("Giá phòng đã bao gồm phí bảo hiểm du lịch chưa?");
        List<String> c7 = new ArrayList<>();
        c7.add("Cách hủy phòng và hoàn tiền cho đặt phòng khách sạn");
        c7.add("Làm thế nào tôi có thể gửi lại vé điện tử của mình");
        c7.add("Làm cách nào để kiểm tra trạng thái hoàn tiền của tôi");
        List<String> c8 = new ArrayList<>();
        c8.add("Tôi có thể xem tiện nghi của khách sạn như thế nào?");
        List<String> c9 = new ArrayList<>();
        c9.add("Đặt phòng cho hút thuốc");
        c9.add("Yêu cầu giường phụ");
        c9.add("Nhận phòng trễ");
        List<String> c10 = new ArrayList<>();
        c10.add("Chính sách vệ sinh ở khách sạn có đảm bảo tiêu chuẩn");
        topics.put(Title.get(0),c1);
        topics.put(Title.get(1),c2);
        topics.put(Title.get(2),c3);
        topics.put(Title.get(3),c4);
        topics.put(Title.get(4),c5);
        topics.put(Title.get(5),c6);
        topics.put(Title.get(6),c7);
        topics.put(Title.get(7),c8);
        topics.put(Title.get(8),c9);
        topics.put(Title.get(9),c10);

    }

}