package com.crbot;

import java.util.Objects;

/**
 * Card Object
 * @author Noah
 */
public class Card implements Comparable<Card>{
    private final long id;
    private final String name;
    private final long rank;
    
    /**
     * creates Card Object
     * @param _id long 
     * @param _name String 
     * @param _rank long 
     */
    public Card(long _id, String _name, long _rank){
        id = _id;
        name = _name;
        rank = _rank;
    }
    
    /**
     * returns card id
     * @return long
     */
    public long getId() {
        return id;
    }
    
    /**
     * returns card name
     * @return String
     */
    public String getName() {
        if(name == null)return "";
        return name;
    }

    /**
     * returns card rank
     * @return long
     */
    public long getRank() {
        return rank;
    }
    
    /**
     * Compares to Card Objects by Rank
     * @param card Card 
     * @return
     */
    @Override
    public int compareTo(Card card) {
        if(card == null || this.getRank() > card.getRank())
            return 1;
        if(this.getRank() == card.getRank())
            return 0;
        else return -1;
    }

    /**
     * generates HashCode
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + (int) (this.rank ^ (this.rank >>> 32));
        return hash;
    }

    /**
     * checks if given Object is equal
     * @param obj Object 
     * @return boolean
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Card c)
            return this.id == c.id;
        else return false;
    }
    
    /**
     * returns Card Object as String
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Card{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", rank=").append(rank);
        sb.append('}');
        return sb.toString();
    }   
}