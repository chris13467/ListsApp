package com.Monsoon.lists.listeners;

public interface OnMenuItemPressed {

    public void OnAddPressed();
    public void OnDonePressed();
    public void OnDeletePressed();
    public void OnBackPressed();
    public void OnRandomizePressed();
    public void OnEditPressed();
    public void OnMoveToFrontPressed();
    public void OnMoveToBackPressed();
    public void OnRandomToFront();
    public void OnSelectAll(boolean b);
    public void OnRandomTagToFront();
    public void OnAllTagToFront();
}
