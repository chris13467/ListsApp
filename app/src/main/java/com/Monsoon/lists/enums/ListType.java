package com.Monsoon.lists.enums;

import androidx.annotation.ColorRes;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.R;

import java.io.Serializable;

public enum ListType implements Serializable {
    NUMBERED_LIST(Constants.NUMBERED, R.color.numbered_list);

    public final String label;
    public final int color;

    private ListType(String label, @ColorRes int color){
        this.label = label;
        this.color = color;
    }
}
