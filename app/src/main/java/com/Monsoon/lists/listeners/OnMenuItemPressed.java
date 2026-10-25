package com.Monsoon.lists.listeners;

public interface OnMenuItemPressed {

    public void OnAddPressed();
    public void OnDonePressed();
    public void OnDeletePressed();
    public void OnBackPressed();
    default public void OnRandomizePressed(){
        // not used
    }
    public void OnEditPressed();
    default public void OnMoveToFrontPressed(){
        // not used
    }
    default public void OnMoveToBackPressed(){
        // not used
    }
    default public void OnRandomToFront(){
        // not used
    }
    public void OnSelectAll(boolean b);
    default public void OnRandomTagToFront(){
        // not used
    }
    default public void OnAllTagToFront(){
        // not used
    }
    default public void EstimateReleaseDate(){
        // not used
    }
    default public void LoadBookReleaseTracker(){
        // not used
    }
    public void OnChangeTitle();
    default public void OnAddToOtherPressed(){
        // not used
    }
    public void OnLockChange(boolean locked);
}
