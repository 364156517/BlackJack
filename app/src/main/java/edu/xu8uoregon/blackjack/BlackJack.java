package edu.xu8uoregon.blackjack;

import java.util.Random;

/**
 * Created by junchengxu on 7/7/16.
 */
public class BlackJack {
    private String player1Name ,player2Name, winner;
    private int player1Score, player2Score, currentPoint, currentCard, turnScore, savescore;
    private boolean player1Playing, gameFinished;

    BlackJack(){
        restartGame();
    }
    public String getName1(){
        return player1Name;
    }
    public void setName1(String name){
        player1Name = name;
    }
    public String getName2(){
        return player2Name;
    }
    public void setName2(String name){
        player2Name = name;
    }
    public int getCurrentPoint(){
        return turnScore;
    }
    public void setCurrentPoint(int point){
        turnScore = point;
    }
    public boolean isPlayer1Playing(){
        return player1Playing;
    }
    public void setCurrentPlaying(boolean value){
        player1Playing = value;
    }
    public boolean isGameFinished(){
        return gameFinished;
    }
    public void setGameFinished(boolean value){
        gameFinished = value;
    }
    public int getScore1(){
        return player1Score;
    }
    public void setScore1(int score){
        player1Score = score;
    }
    public int getScore2(){
        return player2Score;
    }
    public void setScore2(int score){
        player2Score = score;
    }



    public int getCurrentCard(){
        return currentCard;
    }
    public void setCurrentCard(int num){
        currentCard = num;
    }

    public String getWinner(){
        return winner;
    }
    public void findWinner(){
        if (player1Score <= 21){
            if (player2Score > 21){
                winner = player1Name;
            }
            else {
                if(player1Score > player2Score){
                    winner = player1Name;
                }
                else if(player1Score < player2Score){
                    winner = player2Name;
                }
                else{
                    winner = "Tie";
                }
            }
        }
        else{
            winner = player2Name;
        }
        gameFinished = true;

    }

    public void pickcard(){
        if (isGameFinished() == false) {
            Random randomGenerator = new Random();
            currentCard = randomGenerator.nextInt(51) + 1;
            if (currentCard % 13 == 10 |currentCard % 13 == 11 | currentCard % 13 == 12 | currentCard % 13 == 0 ){currentPoint = 10;}
            if (currentCard % 13 == 1){currentPoint = 1;}
            if (currentCard % 13 == 2){currentPoint = 2;}
            if (currentCard % 13 == 3){currentPoint = 3;}
            if (currentCard % 13 == 4){currentPoint = 4;}
            if (currentCard % 13 == 5){currentPoint = 5;}
            if (currentCard % 13 == 6){currentPoint = 6;}
            if (currentCard % 13 == 7){currentPoint = 7;}
            if (currentCard % 13 == 8){currentPoint = 8;}
            if (currentCard % 13 == 9){currentPoint = 9;}
            turnScore = turnScore + currentPoint;
        }
    }

    public void StopPick(){
        if(player1Playing){
            player1Playing = false;
            savescore = turnScore;
            gameChecking();
            turnScore = 0;
        }else {
            gameFinished = true;
            player2Score = turnScore;
            player1Score = savescore;
            findWinner();
        }
    }

    public void gameChecking(){
        if (player1Playing) {
            if (savescore > 21) {
                gameFinished = true;
            }
        }
    }

    public void restartGame(){
        player1Name = "";
        player2Name = "";
        winner = "";
        player1Score = 0;
        player2Score = 0;
        currentPoint = 0;
        turnScore = 0;
        player1Playing = true;
        gameFinished = false;
        currentCard = 1;
    }
}
