package com.example.libraryautoimageslider.IndicatorView;

import androidx.annotation.Nullable;

import com.example.libraryautoimageslider.IndicatorView.animation.AnimationManager;
import com.example.libraryautoimageslider.IndicatorView.animation.controller.ValueController;
import com.example.libraryautoimageslider.IndicatorView.animation.data.Value;
import com.example.libraryautoimageslider.IndicatorView.draw.DrawManager;
import com.example.libraryautoimageslider.IndicatorView.draw.data.Indicator;


public class IndicatorManager implements ValueController.UpdateListener {

    private DrawManager drawManager;
    private AnimationManager animationManager;
    private Listener listener;

    interface Listener {
        void onIndicatorUpdated();
    }

    IndicatorManager(@Nullable Listener listener) {
        this.listener = listener;
        this.drawManager = new DrawManager();
        this.animationManager = new AnimationManager(drawManager.indicator(), this);
    }

    public AnimationManager animate() {
        return animationManager;
    }

    public Indicator indicator() {
        return drawManager.indicator();
    }

    public DrawManager drawer() {
        return drawManager;
    }

    @Override
    public void onValueUpdated(@Nullable Value value) {
        drawManager.updateValue(value);
        if (listener != null) {
            listener.onIndicatorUpdated();
        }
    }
}
