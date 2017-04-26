package com.example.chaitanya.charity;

import android.support.v7.app.AlertDialog;

import java.util.TimerTask;

/**
 * Created by chaitanya on 16-10-2016.
 */

public class CloseDialodTimerTask extends TimerTask {

    private AlertDialog ad;

    public CloseDialodTimerTask(AlertDialog ad1) {
        this.ad = ad1;
    }

    @Override
    public void run() {
        if(ad.isShowing()) {
            ad.dismiss();
        }
    }
}