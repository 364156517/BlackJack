package edu.xu8uoregon.blackjack;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by junchengxu on 7/7/16.
 */

public class BlackJackActivity extends AppCompatActivity {
    String name1, name2;
    Boolean NewGame;
    public boolean getNewGame(){
        return NewGame;
    }
    public String getName1(){
        return name1;
    }
    public String getName2(){
        return name2;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            name1 = getIntent().getExtras().getString("name1");
            name2 = getIntent().getExtras().getString("name2");
            NewGame = getIntent().getExtras().getBoolean("NewGame");
        }catch(Exception e){}
        setContentView(R.layout.game_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
                Intent settings = new Intent(BlackJackActivity.this, SettingActivity.class);
                startActivity(settings);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}

