/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Noah
 */
public class Verein implements Comparable<Verein>{
    
    private final String name;
    private int goals, rGoals, points, games;
    private final Mannschaft mann;

    public Verein(String _name){
        name = _name;
        mann = new Mannschaft(name);
        goals = 0;
        rGoals = 0;
        points = 0;
        games = 0;
    }

    public String getName() {
        return name;
    }

    public Mannschaft getMann() {
        return mann;
    }

    public int getGoals() {
        return goals;
    }

    public int getrGoals() {
        return rGoals;
    }

    public int getPoints() {
        return points;
    }

    public int getGames() {
        return games;
    }
    
    public int getDifference(){
        return goals - rGoals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public void setrGoals(int rGoals) {
        this.rGoals = rGoals;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setGames(int games) {
        this.games = games;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Verein v)if(this.name.equalsIgnoreCase(v.getName()))return true;
        return false;
    }

    @Override
    public int compareTo(Verein v) {
        if(points > v.getPoints() || 
                (points == v.getPoints() && getDifference() > v.getDifference()) || 
                (points == points && getDifference() == v.getDifference() && goals > v.getGoals())){
            return -1;
        }else if(points == v.getPoints() && getDifference() == v.getDifference() && goals == v.getGoals())return 0;
        return 1;
    }

    @Override
    public String toString() {
        return  name + ","+ goals + "," + rGoals + "," +  points + "," + games;
    }
    
}
