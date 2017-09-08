package edu.xu8uoregon.blackjack;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by junchengxu on 7/7/16.
 */

public class BlackJackFragment extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener{

    FrameLayout myLayout;
    EditText name1EditText, name2EditText;
    TextView score1TextView, score2TextView;
    TextView currentPointTextView, currentPlayerTextView;
    Button PickButton, StopButton;
    ImageButton restartButton;
    ImageView CardImage,HandImage;
    BlackJack blackJack;

    View view;


    private SharedPreferences savedValues;


    private boolean NewGameFromActivity;
    private String name1FromActivity;
    private String name2FromActivity;

    private String backgroundString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.game_fragment, container, false);

        myLayout = (FrameLayout) view.findViewById(R.id.mainLayout);

        name1EditText = (EditText)view.findViewById(R.id.name1);
        name2EditText = (EditText)view.findViewById(R.id.name2);
        name1EditText.setOnEditorActionListener(this);
        name2EditText.setOnEditorActionListener(this);
        PickButton = (Button)view.findViewById(R.id.pickButton);
        StopButton = (Button)view.findViewById(R.id.stopButton);
        restartButton = (ImageButton)view.findViewById(R.id.restartButton);
        PickButton.setOnClickListener(this);
        StopButton.setOnClickListener(this);
        restartButton.setOnClickListener(this);
        CardImage = (ImageView)view.findViewById(R.id.imageView);
        HandImage = (ImageView)view.findViewById(R.id.imageHand);
        currentPlayerTextView = (TextView)view.findViewById(R.id.turnLabel);
        currentPointTextView = (TextView)view.findViewById(R.id.score);
        score1TextView = (TextView)view.findViewById(R.id.displayScore1);
        score2TextView = (TextView)view.findViewById(R.id.displayScore2);
        savedValues = this.getActivity().getSharedPreferences("SavedValues", getContext().MODE_PRIVATE);
        blackJack = new BlackJack();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.pickButton:
                if (blackJack.isGameFinished() == false){
                    if (NameValidate()){
                        blackJack.pickcard();
                    }else{
                        Toast.makeText(getActivity(), "Please enter both player's name", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showWinner();
                    Toast.makeText(getActivity(), "Click the button at top right to start New Game", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.stopButton:
                if (blackJack.isGameFinished() == false){
                    if (NameValidate()){
                        blackJack.StopPick();
                        if(String.valueOf(blackJack.getScore2())=="0"){
                            Toast.makeText(getActivity(), "Give the phone to another play", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Game Over", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Please enter both player's name", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showWinner();
                    Toast.makeText(getActivity(), "Click the button at top right to start New Game", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.restartButton:
                startButton();
                break;
        }
        updateWidgets();

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch(v.getId()){
            case R.id.name1:
                blackJack.setName1(name1EditText.getText().toString());
                break;
            case R.id.name2:
                blackJack.setName2(name2EditText.getText().toString());
                break;
        }
        updateWidgets();
        return false;
    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("Name1", blackJack.getName1());
        editor.putString("Name2", blackJack.getName2());
        editor.putBoolean("currentPlaying", blackJack.isPlayer1Playing());
        editor.putBoolean("GameOver", blackJack.isGameFinished());
        editor.putInt("Score1", blackJack.getScore1());
        editor.putInt("Score2", blackJack.getScore2());
        editor.putInt("currentScore", blackJack.getCurrentPoint());
        editor.putInt("currentCard", blackJack.getCurrentCard());
        editor.putString("background", backgroundString);
        editor.commit();
        super.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();
        restoreSettings();
        blackJack.setName1(savedValues.getString("Name1", ""));
        blackJack.setName2(savedValues.getString("Name2", ""));
        blackJack.setCurrentPlaying(savedValues.getBoolean("currentPlaying", true));
        blackJack.setGameFinished(savedValues.getBoolean("GameOver", false));
        blackJack.setScore1(savedValues.getInt("Score1", 0));
        blackJack.setScore2(savedValues.getInt("Score2", 0));
        blackJack.setCurrentPoint(savedValues.getInt("currentScore", 0));
        blackJack.setCurrentCard(savedValues.getInt("currentCard", 0));
        if (blackJack.isPlayer1Playing() == false){
            blackJack.gameChecking();
        }
        updateWidgets();

        try{
            BlackJackActivity gameActivity = (BlackJackActivity) getActivity();
            name1FromActivity = gameActivity.getName1();
            name2FromActivity = gameActivity.getName2();
            NewGameFromActivity = gameActivity.getNewGame();
        }catch(Exception e){}
        if (NewGameFromActivity == true){
            blackJack.restartGame();
            blackJack.setName1(name1FromActivity);
            blackJack.setName2(name2FromActivity);
            updateWidgets();
        }
    }

    public void restoreSettings(){
        backgroundString = savedValues.getString("background", "0");
        setBackround(Integer.parseInt(backgroundString));
    }


    private void startButton(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int orientation = display.getRotation();
        if(orientation == Surface.ROTATION_270 | orientation == Surface.ROTATION_90){
            blackJack.restartGame();
        }else{
            getActivity().finish();
        }
    }

    private void updateWidgets(){
        name1EditText.setText(blackJack.getName1());
        name2EditText.setText(blackJack.getName2());
        score1TextView.setText(String.valueOf(blackJack.getScore1()));
        score2TextView.setText(String.valueOf(blackJack.getScore2()));
        currentPointTextView.setText(String.valueOf(blackJack.getCurrentPoint()));
        if (blackJack.isPlayer1Playing()){
            currentPlayerTextView.setText(blackJack.getName1() + "'s turn");
            HandImage.setImageResource(R.drawable.left_finger);
        }else{
            currentPlayerTextView.setText(blackJack.getName2() + "'s turn");
            HandImage.setImageResource(R.drawable.right_finger);
        }
        switch(blackJack.getCurrentCard()){
            case 1:
                CardImage.setImageResource(R.drawable.f14);
                break;
            case 2:
                CardImage.setImageResource(R.drawable.f2);
                break;
            case 3:
                CardImage.setImageResource(R.drawable.f3);
                break;
            case 4:
                CardImage.setImageResource(R.drawable.f4);
                break;
            case 5:
                CardImage.setImageResource(R.drawable.f5);
                break;
            case 6:
                CardImage.setImageResource(R.drawable.f6);
                break;
            case 7:
                CardImage.setImageResource(R.drawable.f7);
                break;
            case 8:
                CardImage.setImageResource(R.drawable.f8);
                break;
            case 9:
                CardImage.setImageResource(R.drawable.f9);
                break;
            case 10:
                CardImage.setImageResource(R.drawable.f10);
                break;
            case 11:
                CardImage.setImageResource(R.drawable.f11);
                break;
            case 12:
                CardImage.setImageResource(R.drawable.f12);
                break;
            case 13:
                CardImage.setImageResource(R.drawable.f13);
                break;
            case 14:
                CardImage.setImageResource(R.drawable.h14);
                break;
            case 15:
                CardImage.setImageResource(R.drawable.h2);
                break;
            case 16:
                CardImage.setImageResource(R.drawable.h3);
                break;
            case 17:
                CardImage.setImageResource(R.drawable.h4);
                break;
            case 18:
                CardImage.setImageResource(R.drawable.h5);
                break;
            case 19:
                CardImage.setImageResource(R.drawable.h6);
                break;
            case 20:
                CardImage.setImageResource(R.drawable.h7);
                break;
            case 21:
                CardImage.setImageResource(R.drawable.h8);
                break;
            case 22:
                CardImage.setImageResource(R.drawable.h9);
                break;
            case 23:
                CardImage.setImageResource(R.drawable.h10);
                break;
            case 24:
                CardImage.setImageResource(R.drawable.h11);
                break;
            case 25:
                CardImage.setImageResource(R.drawable.h12);
                break;
            case 26:
                CardImage.setImageResource(R.drawable.h13);
                break;
            case 27:
                CardImage.setImageResource(R.drawable.m14);
                break;
            case 28:
                CardImage.setImageResource(R.drawable.m2);
                break;
            case 29:
                CardImage.setImageResource(R.drawable.m3);
                break;
            case 30:
                CardImage.setImageResource(R.drawable.m4);
                break;
            case 31:
                CardImage.setImageResource(R.drawable.m5);
                break;
            case 32:
                CardImage.setImageResource(R.drawable.m6);
                break;
            case 33:
                CardImage.setImageResource(R.drawable.m7);
                break;
            case 34:
                CardImage.setImageResource(R.drawable.m8);
                break;
            case 35:
                CardImage.setImageResource(R.drawable.m9);
                break;
            case 36:
                CardImage.setImageResource(R.drawable.m10);
                break;
            case 37:
                CardImage.setImageResource(R.drawable.m11);
                break;
            case 38:
                CardImage.setImageResource(R.drawable.m12);
                break;
            case 39:
                CardImage.setImageResource(R.drawable.m13);
                break;
            case 40:
                CardImage.setImageResource(R.drawable.r14);
                break;
            case 41:
                CardImage.setImageResource(R.drawable.r2);
                break;
            case 42:
                CardImage.setImageResource(R.drawable.r3);
                break;
            case 43:
                CardImage.setImageResource(R.drawable.r4);
                break;
            case 44:
                CardImage.setImageResource(R.drawable.r5);
                break;
            case 45:
                CardImage.setImageResource(R.drawable.r6);
                break;
            case 46:
                CardImage.setImageResource(R.drawable.r7);
                break;
            case 47:
                CardImage.setImageResource(R.drawable.r8);
                break;
            case 48:
                CardImage.setImageResource(R.drawable.r9);
                break;
            case 49:
                CardImage.setImageResource(R.drawable.r10);
                break;
            case 50:
                CardImage.setImageResource(R.drawable.r11);
                break;
            case 51:
                CardImage.setImageResource(R.drawable.r12);
                break;
            case 52:
                CardImage.setImageResource(R.drawable.r13);
                break;
        }
    }

    public boolean NameValidate(){
        if (blackJack.getName1() == "" | blackJack.getName2() == ""){
            return false;
        }else{
            return true;
        }
    }

    public void showWinner(){
        if (blackJack.getWinner() != ""){
            if (blackJack.getWinner() == "Tie"){
                currentPlayerTextView.setText("Tie Game");
                Toast.makeText(getActivity(), "Tie Game", Toast.LENGTH_SHORT).show();
            }else{
                currentPlayerTextView.setText(blackJack.getWinner() + " Wins");
                Toast.makeText(getActivity(), blackJack.getWinner() + " wins", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setBackround(int num){
        if (num == 0){
            myLayout.setBackgroundResource(R.drawable.background);
        }else if(num == 1){
            myLayout.setBackgroundResource(R.drawable.background1);
        }else{
            myLayout.setBackgroundResource(R.drawable.background2);
        }
    }


}
