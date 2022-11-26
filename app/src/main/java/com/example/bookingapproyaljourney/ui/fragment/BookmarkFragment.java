package com.example.bookingapproyaljourney.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.databinding.FragmentBookmarkBinding;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.ui.adapter.BookmarkAdapter;
import com.example.bookingapproyaljourney.view_model.BookmarkViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookmarkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentBookmarkBinding binding;
    private BookmarkViewModel bookmarkViewModel;
    private BookmarkAdapter bookmarkAdapter;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookmarkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookmarkFragment newInstance(String param1, String param2) {
        BookmarkFragment fragment = new BookmarkFragment();
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
        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        Spannable wordtoSpan = new SpannableString("Bạn không tìm thấy đặt phòng/đặt chỗ của mình ở đây? Truy cập Trung tâm trợ giúp");

//        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 53, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new UnderlineSpan(), 53, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 53, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wordtoSpan.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.textHelps.setText(wordtoSpan);

        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        bookmarkViewModel.getListBookmarkById(UserClient.getInstance().getId());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        bookmarkViewModel.getBookmarkResponseMutableLiveData().observe(getActivity(), new Observer<BookmarkResponse>() {
            @Override
            public void onChanged(BookmarkResponse bookmarkResponse) {
                if (bookmarkResponse.getData().size() > 0) {
                    binding.contentNullList.setVisibility(View.GONE);
                } else {
                    binding.contentNullList.setVisibility(View.VISIBLE);
                }
                bookmarkAdapter = new BookmarkAdapter(bookmarkResponse.getData(), new BookmarkAdapter.Callback() {
                    @Override
                    public void onLoading(Integer integer) {
                        binding.progressBar.setVisibility(integer);
                    }

                    @Override
                    public void onDirect(HouseDetailResponse houseDetailResponse) {

                    }
                });
                binding.recyclerView.setAdapter(bookmarkAdapter);
            }
        });

        bookmarkViewModel.getmProgressMutableData().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
    }
}