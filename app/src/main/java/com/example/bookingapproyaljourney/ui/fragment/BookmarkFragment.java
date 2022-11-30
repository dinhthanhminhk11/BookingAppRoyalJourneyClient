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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallbackOrderClick;
import com.example.bookingapproyaljourney.databinding.FragmentBookmarkBinding;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.ui.adapter.BookmarkAdapter;
import com.example.bookingapproyaljourney.view_model.BookmarkViewModel;

public class BookmarkFragment extends Fragment{

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
    private CallbackOrderClick callbackOrderClick;

    public BookmarkFragment(CallbackOrderClick callbackOrderClick) {
        this.callbackOrderClick = callbackOrderClick;
    }



//    public static BookmarkFragment newInstance(String param1, String param2) {
//        BookmarkFragment fragment = new BookmarkFragment(this);
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

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
        Spannable wordtoSpan = new SpannableString(getString(R.string.question_bookmard));

        wordtoSpan.setSpan(new UnderlineSpan(), 53, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 53, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.textHelps.setText(wordtoSpan);

        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        bookmarkViewModel.getListBookmarkById(UserClient.getInstance().getId());

        binding.reLoad.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bookmarkViewModel.getListBookmarkById(UserClient.getInstance().getId());
                binding.reLoad.setRefreshing(false);
            }
        });

        binding.btnSearch.setOnClickListener(v -> callbackOrderClick.clickHome());
        binding.textHelps.setOnClickListener(v -> callbackOrderClick.clickHelps());
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