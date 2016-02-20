package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by michaelhilton on 1/25/16.
 */
public class Game {

    public java.util.List<Card> deck = new ArrayList<>();

    public java.util.List<java.util.List<Card>> cols = new ArrayList<>();

    public int score;

    public int mode;


    public String errorCode;

    public Game(){
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        score=0;
        errorCode=" ";
    }

    public void refresh(){
        cols.get(0).clear();
        cols.get(1).clear();
        cols.get(2).clear();
        cols.get(3).clear();
        deck.clear();
        buildDeck();
        shuffle();
        dealFour();
        score=0;
        errorCode=" ";
    }


    public void addToScore(int newNum){
        score += newNum;
    }
    public int getScore(){return score;}

    public void buildDeck() {
        if(mode == 1) {
            for (int i = 2; i < 15; i++) {
                deck.add(new Card(i, Suit.Clubs));
                deck.add(new Card(i, Suit.Hearts));
                deck.add(new Card(i, Suit.Diamonds));
                deck.add(new Card(i, Suit.Spades));
            }
        }
        if(mode == 2) {
            for (int j = 1; j < 13; j++) {
                if (j < 8 || j > 9) {
                    deck.add(new Card(j, Suit.Cups));
                    deck.add(new Card(j, Suit.Clubs));
                    deck.add(new Card(j, Suit.Coins));
                    deck.add(new Card(j, Suit.Swords));
                }
            }
        }
    }






    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }

    public void dealFour() {
        if(deck.isEmpty()){
            errorCode="There are no more cards in the deck!";
            return;
        }
        for(int i = 0; i < 4; i++){
            cols.get(i).add(deck.get(deck.size()-1));
            deck.remove(deck.size()-1);
        }
        errorCode=" ";
    }

    //customDeal to setup game for testing purposes
    public void customDeal(int c1, int c2, int c3, int c4) {
        cols.get(0).add(deck.get(c1));
        deck.remove(c1);
        cols.get(1).add(deck.get(c2));
        deck.remove(c2);
        cols.get(2).add(deck.get(c3));
        deck.remove(c3);
        cols.get(3).add(deck.get(c4));
        deck.remove(c4);
    }

    public void remove(int columnNumber) {
        if(colHasCards(columnNumber)) {
            Card c = getTopCard(columnNumber);
            boolean removeCard = false;
            for (int i = 0; i < 4; i++) {
                if (i != columnNumber) {
                    if (colHasCards(i)) {
                        Card compare = getTopCard(i);
                        if (compare.getSuit() == c.getSuit()) {
                            if (compare.getValue() > c.getValue()) {
                                removeCard = true;
                            }
                        }
                    }
                }
            }
            if (removeCard) {
                this.cols.get(columnNumber).remove(this.cols.get(columnNumber).size() - 1);
                addToScore(1);
                errorCode=" ";
            }
            else{
                errorCode="That card is not valid to remove!";
            }

        }
        else{
            errorCode="There is no card there to remove!";
        }
    }

    private boolean colHasCards(int colNumber) {
        return this.cols.get(colNumber).size()>0;
    }

    private Card getTopCard(int columnNumber) {
        return this.cols.get(columnNumber).get(this.cols.get(columnNumber).size()-1);
    }


    public void move(int colFrom, int colTo) {
        if(cols.get(colFrom).size()==0){
            errorCode="The 'FROM' column must be non-empty!";
            return;
        }
        if(cols.get(colTo).size()!=0){
            errorCode="The 'TO' column must be empty!";
            return;
        }
        Card cardToMove = getTopCard(colFrom);
        this.removeCardFromCol(colFrom);
        this.addCardToCol(colTo,cardToMove);
        errorCode=" ";
    }

    private void addCardToCol(int colTo, Card cardToMove) {
        cols.get(colTo).add(cardToMove);
    }

    private void removeCardFromCol(int colFrom) {
        this.cols.get(colFrom).remove(this.cols.get(colFrom).size()-1);
    }
}
