package com.Monsoon.lists;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.Monsoon.lists.Lists.ListOfLists;
import com.Monsoon.lists.Lists.NumberedList;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.OnMenuItemPressed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.Monsoon.lists.databinding.ActivityMainBinding;

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

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
private ActivityMainBinding binding;
private ListOfLists lists;

private Settings settings;

private MenuState menuState;

private OnMenuItemPressed menuListener;


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
            case ADD_LIST:
            case ADD_ITEM:
                for (int i = 0; i < menu.size(); i++){
                    menu.getItem(i).setVisible(false);
                }
                break;
            case NUMBERED_LIST_ADD_ITEM:
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(true);
                menu.getItem(2).setVisible(false);
                menu.getItem(3).setVisible(false);
                menu.getItem(4).setVisible(false);
                menu.getItem(5).setVisible(false);
                menu.getItem(6).setVisible(false);
                menu.getItem(7).setVisible(false);
                break;
            case BASIC_LIST_SELECT_MODE:
            case LIST_OF_LISTS_SELECT_MODE:
            case TODO_LIST_SELECT_MODE:
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(true);
                menu.getItem(2).setVisible(false);
                menu.getItem(3).setVisible(false);
                menu.getItem(4).setVisible(false);
                menu.getItem(5).setVisible(false);
                menu.getItem(6).setVisible(false);
                menu.getItem(7).setVisible(false);
                break;
            case NUMBERED_LIST_SELECT_MODE:
                Toast.makeText(this, "NumSelected = " + lists.getCurrentList().getNumSelected(), Toast.LENGTH_SHORT).show();
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(true);
                menu.getItem(2).setVisible(false);
                menu.getItem(3).setVisible(false);
                if (lists.getCurrentList().getNumSelected() == 1) {
                    menu.getItem(4).setVisible(true);
                }
                else menu.getItem(4).setVisible(false);
                menu.getItem(5).setVisible(true);
                menu.getItem(6).setVisible(false);
                menu.getItem(7).setVisible(false);
                break;
            case NUMBERED_LIST_NORMAL:
                menu.getItem(0).setVisible(true);
                menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(true);
                menu.getItem(3).setVisible(true);
                menu.getItem(4).setVisible(false);
                menu.getItem(5).setVisible(true);
                if (lists.getCurrentList().getCurrentItem() != null){
                    menu.getItem(6).setVisible(true);
                    menu.getItem(7).setVisible(true);
                }

                break;
            default:
                menu.getItem(0).setVisible(true);
                menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(false);
                menu.getItem(3).setVisible(false);
                menu.getItem(4).setVisible(false);
                menu.getItem(5).setVisible(true);
                menu.getItem(6).setVisible(false);
                menu.getItem(7).setVisible(false);
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

        if (id == R.id.action_delete){
            navController.popBackStack(currentId, true);
            if (menuState == MenuState.LIST_OF_LISTS_SELECT_MODE){
                for (int i = lists.getUserLists().size(); i > 0; i--){
                    baseList currentList = lists.getUserLists().get(i - 1);
                    if (currentList.isSelected()) lists.removeList(currentList);
                }
            } else {
                for (int i = lists.getCurrentList().getList().size(); i > 0; i--){
                    if (lists.getCurrentList().getList().get(i - 1).isSelected()) lists.getCurrentList().removeItem(i-1);
                }
            }
            navController.navigate(currentId);
            OnListChange();
        }

        if (id == R.id.action_get_next){

            lists.getCurrentList().setCurrentItem(lists.getCurrentList().getNextItem());
            navController.navigate(currentId);
            OnListChange();
        }

        if (id == R.id.action_get_random){
            lists.getCurrentList().setCurrentItem(lists.getCurrentList().getRandomItem());
            navController.navigate(currentId);
            OnListChange();
        }

        if (id == R.id.action_make_current_item){
            ((NumberedList)lists.getCurrentList()).Make_selected_current();
            navController.navigate(currentId);
            OnListChange();
        }

        if (id == R.id.action_remove_current_item){
            ((NumberedList)lists.getCurrentList()).removeCurrentItem();
            navController.navigate(currentId);
            OnListChange();
        }

        if (id == R.id.action_readd_current_item){
            ((NumberedList)lists.getCurrentList()).reAddCurrentItem();
            navController.navigate(currentId);
            OnListChange();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        switch (menuState){
            case BASIC_LIST_NORMAL:
            case TODO_LIST_NORMAL:
            case NUMBERED_LIST_NORMAL:
            case MEAL_PLAN_NORMAL:
                navController.navigate(R.id.action_return_to_lists_of_lists);
                return true;
        }
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public Settings getSettings(){
        return settings;
    }

    public ListOfLists getUserLists(){
        return lists;
    }

    public void OnListChange(){
        lists.moveCurrentListToFront();
        SaveData(lists, Constants.LIST_FILE_NAME);
    }

    public void onMenuContextChanged(MenuState menuState){
        this.menuState = menuState;
        invalidateMenu();
    }


    public void changeToolbarTitle(@NonNull String title){
        try{
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException ignored){}
    }

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