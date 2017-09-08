package edu.xu8uoregon.blackjack;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by junchengxu on 7/7/16.
 */

public class MainActivity extends AppCompatActivity{
    private String name1, name2;
    private boolean NewGame;
    BlackJackFragment gameFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            gameFragment = (BlackJackFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        }catch(Exception e){}
    }

    public void setNameGame(Boolean value){
        NewGame = value;
    }
    public void setName1(String name){
        name1 = name;
    }
    public void setName2(String name){
        name2 = name;
    }
    public void update(){
        if (NewGame == true) {
            gameFragment.blackJack.restartGame();
            gameFragment.name1EditText.setText(name1);
            gameFragment.blackJack.setName1(name1);
            gameFragment.name2EditText.setText(name2);
            gameFragment.blackJack.setName2(name2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_about:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("About");
                dialog.show();
                return true;
            case R.id.menu_setting:
                Intent settings = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(settings);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}