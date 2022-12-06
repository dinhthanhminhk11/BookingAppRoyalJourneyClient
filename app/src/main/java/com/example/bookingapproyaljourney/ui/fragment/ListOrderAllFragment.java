package com.example.bookingapproyaljourney.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.FragmentListOrderAllBinding;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser;
import com.example.bookingapproyaljourney.response.order.OrderListResponse;
import com.example.bookingapproyaljourney.ui.activity.StatusBillActivity;
import com.example.bookingapproyaljourney.ui.adapter.OrderAdapter;
import com.example.bookingapproyaljourney.view_model.OrderViewModel;

public class ListOrderAllFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private OrderViewModel orderViewModel;
    private FragmentListOrderAllBinding binding;
    private OrderAdapter adapter;
    private CallbackOrderClick callbackOrderClick;

    public ListOrderAllFragment(CallbackOrderClick callbackOrderClick) {
        this.callbackOrderClick = callbackOrderClick;
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
        binding = FragmentListOrderAllBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
//        Spannable wordtoSpan = new SpannableString(this.getString(R.string.question_bookmard));
//
////        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 53, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wordtoSpan.setSpan(new UnderlineSpan(), 53, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 53, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wordtoSpan.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.textHelps.setText(R.string.textBookmark);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        orderViewModel.getOrderByIdUser(UserClient.getInstance().getId());

        binding.reLoad.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderViewModel.getOrderByIdUser(UserClient.getInstance().getId());
                binding.reLoad.setRefreshing(false);
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        orderViewModel.getmProgressMutableData().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        orderViewModel.getOrderByIdMutableLiveData().observe(getActivity(), new Observer<ListOrderByIdUser>() {
            @Override
            public void onChanged(ListOrderByIdUser listOrderByIdUser) {
                if (listOrderByIdUser.isMessege()) {
                    if (listOrderByIdUser.getData().size() > 0) {
                        binding.contentNullList.setVisibility(View.GONE);
                    } else {
                        binding.contentNullList.setVisibility(View.VISIBLE);
                    }
                    adapter = new OrderAdapter(listOrderByIdUser.getData(), new OrderAdapter.Callback() {
                        @Override
                        public void onClick(OrderListResponse orderListResponse) {
                            Intent intent = new Intent(getActivity().getApplication(), StatusBillActivity.class);
                            intent.putExtra(AppConstant.ID_ORDER, orderListResponse.getIdOder());
                            startActivity(intent);
                        }
                    });
                    binding.recyclerView.setAdapter(adapter);
                }
            }
        });

        binding.btnSearch.setOnClickListener(v -> {
            callbackOrderClick.clickHome();
        });

        binding.textHelps.setOnClickListener(v -> {
            callbackOrderClick.clickHelps();
        });
    }


}