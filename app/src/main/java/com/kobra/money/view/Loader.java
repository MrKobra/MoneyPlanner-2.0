package com.kobra.money.view;

import android.view.View;

public class Loader {
    private View loaderView;
    private int controlValue;
    private int currentValue;

    private Event event;

    public Loader(int controlValue) {
        this.controlValue = controlValue;
        currentValue = 0;
    }

    public void setLoaderView(View loaderView) {
        this.loaderView = loaderView;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void add(int value) {
        currentValue += value;
        checkControlValue();
    }

    public void show() {
        if(loaderView != null) {
            loaderView.setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
        if(loaderView != null) {
            loaderView.setVisibility(View.GONE);
        }
    }

    public void checkControlValue() {
       if(currentValue >= controlValue) {
            if(event != null) event.onLoad();
       }
    }

    public boolean isLoad() {
        return currentValue >= controlValue;
    }

    public interface Event {
        void onLoad();
    }
}
