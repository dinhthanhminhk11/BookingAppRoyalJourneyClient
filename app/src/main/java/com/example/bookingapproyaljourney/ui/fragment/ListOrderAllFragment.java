package com.example.bookingapproyaljourney.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.databinding.FragmentListOrderAllBinding;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser;
import com.example.bookingapproyaljourney.response.order.OrderListResponse;
import com.example.bookingapproyaljourney.ui.adapter.OrderAdapter;
import com.example.bookingapproyaljourney.view_model.OrderViewModel;

public class ListOrderAllFragment extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private OrderViewModel orderViewModel;
    private FragmentListOrderAllBinding binding;
    private OrderAdapter adapter;

    public ListOrderAllFragment() {
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
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        orderViewModel.getOrderByIdUser(UserClient.getInstance().getId());
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
                    adapter = new OrderAdapter(listOrderByIdUser.getData(), new OrderAdapter.Callback() {
                        @Override
                        public void onClick(OrderListResponse orderListResponse) {
                            Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();

                        }
                    });
                    binding.recyclerView.setAdapter(adapter);
                }
            }
        });
    }


}