package com.crbot;

import java.util.ArrayList;
import java.util.Collections;

/**
 * represents a cr deck
 * @author Noah
 */
public class Deck {
    private final ArrayList<Card> cards;
    
    /**
     * Creates deck Object
     */
    public Deck(){
        cards = new ArrayList<>(8);
    }
    
    /**
     * adds card to deck
     * @param card Card 
     */
    public void addCard(Card card){
        if(cards.size() < 8)cards.add(card);
    }
    
    /**
     * sorts Deck by rank
     */
    private void sort(){
        Collections.sort(cards);
    }
    
    /**
     * returns all card names from deck as String
     * @return String
     */
    public String getNames(){
        StringBuilder sb = new StringBuilder();
        cards.forEach((Card c) -> sb.append("\n").append(c.getName()));
        return sb.toString();
    }

    /**
     * returns Deck as ArrayList
     * @return ArrayList<Card>
     */
    public ArrayList<Card> getCards(){
        sort();
        return cards;
    }
    
    /**
     * returns Deck Object as String
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Deck{cards=").append(cards);
        sb.append('}');
        return sb.toString();
    }
}