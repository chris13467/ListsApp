package com.Monsoon.lists;

import android.nfc.Tag;

import com.Monsoon.lists.util.Tags;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class Settings implements Serializable {
    //TODO: theme Settings
    //TODO: strikethrough on todochecked

    private Tags userTages;
    private static final long serialVersionUID = 1L;


    public Settings(){
        userTages = new Tags();
    }

    public Tags getUserTages(){
        return userTages;
    }



}
