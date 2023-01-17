package com.example.bookingapproyaljourney.ui.custom.mutilfragment.model;


import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;

public class SlidrConfig {

    private int colorPrimary = -1;
    private int colorSecondary = -1;
    private float touchSize = -1f;
    private float sensitivity = 1f;
    private int scrimColor = Color.BLACK;
    private float scrimStartAlpha = 0.8f;
    private float scrimEndAlpha = 0f;
    private float velocityThreshold = 5f;
    private float distanceThreshold = 0.25f;
    private boolean edgeOnly = false;
    private float edgeSize = 0.18f;

    private SlidrPosition position = SlidrPosition.LEFT;
    private SlidrListener listener;


    private SlidrConfig() {
    }

    public int getPrimaryColor() {
        return colorPrimary;
    }

    public int getSecondaryColor() {
        return colorSecondary;
    }


    @ColorInt
    public int getScrimColor() {
        return scrimColor;
    }

    public float getScrimStartAlpha() {
        return scrimStartAlpha;
    }

    public float getScrimEndAlpha() {
        return scrimEndAlpha;
    }


    public SlidrPosition getPosition() {
        return position;
    }

    public float getTouchSize() {
        return touchSize;
    }


    public float getVelocityThreshold() {
        return velocityThreshold;
    }


    public float getDistanceThreshold() {
        return distanceThreshold;
    }

    public float getSensitivity() {
        return sensitivity;
    }


    public SlidrListener getListener() {
        return listener;
    }

    public boolean isEdgeOnly() {
        return edgeOnly;
    }

    public float getEdgeSize(float size) {
        return edgeSize * size;
    }

    public void setColorPrimary(int colorPrimary) {
        this.colorPrimary = colorPrimary;
    }


    public void setColorSecondary(int colorSecondary) {
        this.colorSecondary = colorSecondary;
    }


    public void setTouchSize(float touchSize) {
        this.touchSize = touchSize;
    }


    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }


    public void setScrimColor(@ColorInt int scrimColor) {
        this.scrimColor = scrimColor;
    }


    public void setScrimStartAlpha(float scrimStartAlpha) {
        this.scrimStartAlpha = scrimStartAlpha;
    }


    public void setScrimEndAlpha(float scrimEndAlpha) {
        this.scrimEndAlpha = scrimEndAlpha;
    }


    public void setVelocityThreshold(float velocityThreshold) {
        this.velocityThreshold = velocityThreshold;
    }


    public void setDistanceThreshold(float distanceThreshold) {
        this.distanceThreshold = distanceThreshold;
    }

    public static class Builder {

        private SlidrConfig config;

        public Builder() {
            config = new SlidrConfig();
        }

        public Builder primaryColor(@ColorInt int color) {
            config.colorPrimary = color;
            return this;
        }

        public Builder secondaryColor(@ColorInt int color) {
            config.colorSecondary = color;
            return this;
        }

        public Builder position(SlidrPosition position) {
            config.position = position;
            return this;
        }

        public Builder touchSize(float size) {
            config.touchSize = size;
            return this;
        }

        public Builder sensitivity(float sensitivity) {
            config.sensitivity = sensitivity;
            return this;
        }

        public Builder scrimColor(@ColorInt int color) {
            config.scrimColor = color;
            return this;
        }

        public Builder scrimStartAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
            config.scrimStartAlpha = alpha;
            return this;
        }

        public Builder scrimEndAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
            config.scrimEndAlpha = alpha;
            return this;
        }

        public Builder velocityThreshold(float threshold) {
            config.velocityThreshold = threshold;
            return this;
        }

        public Builder distanceThreshold(@FloatRange(from = .1f, to = .9f) float threshold) {
            config.distanceThreshold = threshold;
            return this;
        }

        public Builder edge(boolean flag) {
            config.edgeOnly = flag;
            return this;
        }

        public Builder edgeSize(@FloatRange(from = 0f, to = 1f) float edgeSize) {
            config.edgeSize = edgeSize;
            return this;
        }

        public Builder listener(SlidrListener listener) {
            config.listener = listener;
            return this;
        }

        public SlidrConfig build() {
            return config;
        }
    }
}
