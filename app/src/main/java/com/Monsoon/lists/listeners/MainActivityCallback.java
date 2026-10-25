package com.Monsoon.lists.listeners;

import androidx.annotation.NonNull;

import com.Monsoon.lists.Lists.ListOfLists;
import com.Monsoon.lists.Settings;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.util.Popup.Popups;

public interface MainActivityCallback {
    public void setMenuListener(OnMenuItemPressed listener);
    public void changeToolbarTitle(@NonNull String title);
    public void onMenuContextChanged(MenuState menuState);
    public void OnListChange();
    public void OnSettingsChanged();
    public ListOfLists getUserLists();
    public Settings getSettings();
    public MenuState getMenuState();
    public Popups getPopupGenerator();
    public void assignIds();

}
