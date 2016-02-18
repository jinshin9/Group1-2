package models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by michaelhilton on 1/25/16.
 */
public class testGame {

    @Test
    public void testGameCreation(){
        Game g = new Game();
        assertNotNull(g);
    }

    @Test
    public void testGameBuildDeck(){
        Game g = new Game();
        g.buildDeck();
        if(g.mode == 1){
            assertEquals(52,g.deck.size());
        }
        if(g.mode == 2){
            assertEquals(40,g.deck.size());
        }
    }

    @Test
    public void testGameInit(){
        Game g = new Game();
        g.mode=1;
        g.buildDeck();
        g.shuffle();
        assertNotEquals(2,g.deck.get(0).getValue());
    }

    @Test
    public void testGameStart(){
        Game g = new Game();
        g.mode=2;
        g.buildDeck();
        g.shuffle();
        g.dealFour();
        assertEquals(1,g.cols.get(0).size());
        assertEquals(1,g.cols.get(1).size());
        assertEquals(1,g.cols.get(2).size());
        assertEquals(1,g.cols.get(3).size());
    }

    @Test
    public void testCustomDeal(){
        Game g = new Game();
        g.mode=1;
        g.buildDeck();
        g.customDeal(0,3,6,9);
        assertEquals("2Clubs",g.cols.get(0).get(0).toString());
        assertEquals("3Clubs",g.cols.get(1).get(0).toString());
        assertEquals("4Clubs",g.cols.get(2).get(0).toString());
        assertEquals("5Clubs",g.cols.get(3).get(0).toString());
    }

    @Test
    public void testRemoveFunction(){
        Game g = new Game();
        g.mode=1;
        g.buildDeck();
        g.customDeal(0,3,6,9);
        g.remove(2);
        assertEquals(0,g.cols.get(2).size());
    }

    @Test
    public void testGetScore() {
        Game g = new Game();
        g.buildDeck();
        assertEquals(0, g.getScore());
    }

    @Test
    public void testAddScore(){
        Game g = new Game();
        g.buildDeck();
        g.addToScore(5);
        assertEquals(5, g.getScore());
    }

    @Test
    public void testAddCardValueToScore(){
        Game g = new Game();
        g.mode=1;
        Card c = new Card(3, null);
        g.buildDeck();
        g.addToScore(c.getValue());
        assertEquals(c.getValue(), g.getScore());
    }

    @Test
    public void testRefresh(){
        Game g = new Game();
        g.mode=1;
        g.buildDeck();
        g.dealFour();
        g.dealFour();
        g.dealFour();
        assertEquals(g.cols.get(0).size(),3);
        assertEquals(g.cols.get(1).size(),3);
        assertEquals(g.cols.get(2).size(),3);
        assertEquals(g.cols.get(3).size(),3);
        g.refresh();
        assertEquals(g.cols.get(0).size(),1);
        assertEquals(g.cols.get(1).size(),1);
        assertEquals(g.cols.get(2).size(),1);
        assertEquals(g.cols.get(3).size(),1);
    }

    @Test
    public void testErrorCode(){
        Game g = new Game();
        g.mode=1;
        g.buildDeck();
        g.customDeal(1,2,3,5);
        g.remove(3);
        assertEquals(g.errorCode,"That card is not valid to remove!");
        g.remove(1);
        assertEquals(g.errorCode,"That card is not valid to remove!");
        g.remove(0);
        assertEquals(g.errorCode," ");
        g.remove(0);
        assertEquals(g.errorCode,"There is no card there to remove!");
        g.move(2,1);
        assertEquals(g.errorCode,"The 'TO' column must be empty!");
        g.move(0,2);
        assertEquals(g.errorCode,"The 'FROM' column must be non-empty!");
        g.move(2,0);
        assertEquals(g.errorCode," ");
        g.deck.clear();
        g.dealFour();
        assertEquals(g.errorCode,"There are no more cards in the deck!");
    }

}
