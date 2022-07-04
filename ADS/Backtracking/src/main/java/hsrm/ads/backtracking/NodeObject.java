/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hsrm.ads.backtracking;

/**
 *
 * @author Noah
 */
public class NodeObject implements Comparable<NodeObject>{

    private final int weight;
    private final int value;
    private final int id;

    /**
     *
     * @param _weight int
     * @param _value  int
     */
    public NodeObject(int _weight, int _value, int _id){
        weight = _weight;
        value = _value;
        id = _id;
    }

    /**
     *
     * @return int weight of Object
     */
    public int getWeight(){
        return weight;
    }

    /**
     *
     * @return int value of Object
     */
    public int getValue(){
        return value;
    }
    
    public int getId(){
    	return id;
    	}
    	

    /**
     *
     * @return double efficency of Object
     */
    public double getEfficency(){
        if(value <= 0 || weight <= 0){
            return 0;
        }
        return value / weight;
    }

    /**
     *
     * @param nobj NodeObject
     *
     * @return int -1 if smaller efficency 0 if equals 1 if greater efficency
     */
    @Override
    public int compareTo(NodeObject nobj){
        if(nobj == null){
            return -1;
        }
        if(equals(nobj)){
            return 0;
        }
        if(this.getEfficency() > nobj.getEfficency()){
            return 1;
        }
        return -1;
    }

    /**
     *
     * @return int hashcode
     */
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 97 * hash + this.weight;
        hash = 97 * hash + this.value;
        return hash;
    }

    /**
     *
     * @param obj Object
     *
     * @return true if same obj or same efficency
     */
    @Override
    public boolean equals(Object obj){
        return obj != null && this == obj || obj instanceof NodeObject nobj && this.getEfficency() == nobj.getEfficency();
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString(){
        return "NodeObject{" + "weight=" + weight + ", value=" + value + '}';
    }

}
