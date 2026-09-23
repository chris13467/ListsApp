package com.Monsoon.lists;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.Monsoon.lists.Lists.ListOfLists;
import com.Monsoon.lists.Lists.NumberedList;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.MainActivityCallback;
import com.Monsoon.lists.listeners.OnMenuItemPressed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.Monsoon.lists.databinding.ActivityMainBinding;
import com.Monsoon.lists.util.Popups;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

//TODO: make dimentions based on screen size
//TODO: make Themes and implement them

public class MainActivity extends AppCompatActivity implements MainActivityCallback {

    private AppBarConfiguration appBarConfiguration;
private ActivityMainBinding binding;
private ListOfLists lists;

private Settings settings;

private MenuState menuState;

private OnMenuItemPressed menuListener;

private Popups popups;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFRENCES, Context.MODE_PRIVATE);
//TODO: make saving and loading list data work

        if (prefs.getBoolean(Constants.FIRST_OPEN, true)){
            prefs.edit().putBoolean(Constants.FIRST_OPEN, false).apply();
            settings = new Settings();
            SaveData(settings, Constants.SETTINGS_FILE_NAME);
            lists = ListOfLists.getListFirstOnFirstLoad(settings);
            SaveData(lists, Constants.LIST_FILE_NAME);
        } else {
            settings = (Settings) LoadData(Constants.SETTINGS_FILE_NAME);
            lists = (ListOfLists) LoadData(Constants.LIST_FILE_NAME);

        }


        menuState = MenuState.LIST_OF_LISTS_NORMAL;


         binding = ActivityMainBinding.inflate(getLayoutInflater());
         setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        popups = new Popups(this, getLayoutInflater());

    }

    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return super.getOnBackInvokedDispatcher();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        switch (menuState){
            case NUMBERED_LIST_NORMAL:
                menu.getItem(Constants.MENU_SETTINGS).setVisible(true);
                menu.getItem(Constants.MENU_ADD).setVisible(true);
                menu.getItem(Constants.MENU_DONE).setVisible(false);
                menu.getItem(Constants.MENU_DELETE).setVisible(false);
                menu.getItem(Constants.MENU_RANDOMIZE).setVisible(true);
                menu.getItem(Constants.MENU_EDIT).setVisible(false);
                menu.getItem(Constants.MENU_MOVE_TO_FRONT).setVisible(false);
                menu.getItem(Constants.MENU_MOVE_TO_BACK).setVisible(false);
                menu.getItem(Constants.MENU_RANDOM_TO_FRONT).setVisible(true);
                break;
            case NUMBERED_LIST_ADD_ITEM:
                menu.getItem(Constants.MENU_SETTINGS).setVisible(false);
                menu.getItem(Constants.MENU_ADD).setVisible(false);
                menu.getItem(Constants.MENU_DONE).setVisible(true);
                menu.getItem(Constants.MENU_DELETE).setVisible(false);
                menu.getItem(Constants.MENU_RANDOMIZE).setVisible(false);
                menu.getItem(Constants.MENU_EDIT).setVisible(false);
                menu.getItem(Constants.MENU_MOVE_TO_FRONT).setVisible(false);
                menu.getItem(Constants.MENU_MOVE_TO_BACK).setVisible(false);
                menu.getItem(Constants.MENU_RANDOM_TO_FRONT).setVisible(false);
                break;
            case NUMBERED_LIST_SELECT_MODE:
                boolean oneSelected = lists.getCurrentList().getNumSelected() == 1;
                menu.getItem(Constants.MENU_SETTINGS).setVisible(false);
                menu.getItem(Constants.MENU_ADD).setVisible(false);
                menu.getItem(Constants.MENU_DONE).setVisible(false);
                menu.getItem(Constants.MENU_DELETE).setVisible(true);
                menu.getItem(Constants.MENU_RANDOMIZE).setVisible(false);
                menu.getItem(Constants.MENU_EDIT).setVisible(oneSelected);
                menu.getItem(Constants.MENU_MOVE_TO_FRONT).setVisible(oneSelected);
                menu.getItem(Constants.MENU_MOVE_TO_BACK).setVisible(oneSelected);
                menu.getItem(Constants.MENU_RANDOM_TO_FRONT).setVisible(false);
                break;
            default:
                menu.getItem(Constants.MENU_SETTINGS).setVisible(true);
                menu.getItem(Constants.MENU_ADD).setVisible(true);
                menu.getItem(Constants.MENU_DONE).setVisible(false);
                menu.getItem(Constants.MENU_DELETE).setVisible(false);
                menu.getItem(Constants.MENU_RANDOMIZE).setVisible(false);
                menu.getItem(Constants.MENU_EDIT).setVisible(false);
                menu.getItem(Constants.MENU_MOVE_TO_FRONT).setVisible(false);
                menu.getItem(Constants.MENU_MOVE_TO_BACK).setVisible(false);
                menu.getItem(Constants.MENU_RANDOM_TO_FRONT).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        int currentId = navController.getCurrentDestination().getId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_add){
            menuListener.OnAddPressed();
        }

        if (id == R.id.action_done){
            menuListener.OnDonePressed();
        }

        if (id == R.id.action_delete) {
            menuListener.OnDeletePressed();
        }
        if (id == R.id.action_randomize_list){
            menuListener.OnRandomizePressed();
        }
        if (id == R.id.action_edit){
            menuListener.OnEditPressed();
        }
        if (id == R.id.action_move_to_front){
            menuListener.OnMoveToFrontPressed();
        }
        if (id == R.id.action_move_to_back){
            menuListener.OnMoveToBackPressed();
        }
        if (id == R.id.action_random_to_front){
            menuListener.OnRandomToFront();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        menuListener.OnBackPressed();
        return false;
    }

    @Override
    public Settings getSettings(){
        return settings;
    }

    @Override
    public ListOfLists getUserLists(){
        return lists;
    }

    @Override
    public MenuState getMenuState() {
        return this.menuState;
    }

    @Override
    public Popups getPopupGenerator() {
        return popups;
    }

    @Override
    public void OnListChange(){
        lists.moveCurrentListToFront();
        SaveData(lists, Constants.LIST_FILE_NAME);
    }

    @Override
    public void onMenuContextChanged(MenuState menuState){
        this.menuState = menuState;

        invalidateMenu();
    }

    @Override
    public void changeToolbarTitle(@NonNull String title){
        try{
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException ignored){}
    }

    @Override
    public void setMenuListener(OnMenuItemPressed listener){
        menuListener = listener;
    }

    private void SaveData(Serializable s, String fileName){
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object LoadData(String fileName){
        Object out;
        try {
            FileInputStream fis = openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            out = ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
}