package com.Monsoon.lists.util;

import com.Monsoon.lists.Constants;

public class Util {

    public static String getTagsString(Tags tags, boolean addTag){
        StringBuilder sb = new StringBuilder(Constants.TAGS);
        for (int i = 0; i < tags.getList().size(); i++){
            sb.append(tags.getList().get(i));
            if (i < tags.getList().size() - 1) sb.append(", ");
        }
        if (addTag){
            sb.append("   ");
            sb.append(Constants.ADD_TAG);
        }

        return sb.toString();
    }
}
