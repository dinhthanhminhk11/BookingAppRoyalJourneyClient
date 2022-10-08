package com.example.bookingapproyaljourney.ui.fragment;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bookingapproyaljourney.R;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.librarynav.SlidingRootNav;

public class CenteredTextFragment extends Fragment implements GestureDetector.OnGestureListener {

    private static final String TAG = "swipe position";
    private float x1, x2, y1, y2;
    private static int Min_DISTANCE = 150;
    private GestureDetector gestureDetector;

    private static SlidingRootNav slidingRootNavLayout;

    private static final String EXTRA_TEXT = "text";

    public static CenteredTextFragment createFor(String text, SlidingRootNav silSlidingRootNavLayout) {
        CenteredTextFragment fragment = new CenteredTextFragment();
        Bundle args = new Bundle();
        CenteredTextFragment.slidingRootNavLayout = silSlidingRootNavLayout;
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        this.gestureDetector = new GestureDetector(getActivity(), this);
        final String text = args != null ? args.getString(EXTRA_TEXT) : "";
        TextView textView = view.findViewById(R.id.textFragmentText);
        textView.setText(text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        break;

                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();

                        float valueX = x2 - x1;
                        float valueY = y2 - y1;

                        if (Math.abs(valueX) > Min_DISTANCE) {
                            if (x2 > x1) {
                                slidingRootNavLayout.openMenu();
                            } else {
                            }
                        }

                        break;
                }
                return true;
            }
        });


    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
