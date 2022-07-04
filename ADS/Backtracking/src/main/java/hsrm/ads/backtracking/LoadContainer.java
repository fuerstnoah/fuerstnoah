/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hsrm.ads.backtracking;

import java.util.*;

/**
 *
 * @author Noah
 */
public class LoadContainer{

    /**
     *
     * @param ausgewaehlt array containing the chosen objects
     * @param gewichte    array containing the weights of the objects
     * @param werte       array containing the values of the objects
     * @param restKapa    max capacity of backpack
     * @param objIndex    -
     *
     * @return value of backpack
     */
    public static int rucksack(int[] ausgewaehlt, int[] gewichte, int[] werte, int restKapa, int objIndex){
        List<NodeObject> rucksack = new ArrayList<>(ausgewaehlt.length);
        for(int i = 0; i < werte.length; i++){
            NodeObject temp = new NodeObject(gewichte[i], werte[i], i);
            rucksack.add(temp);
        }
        //call backtracking method
        rucksack = backpack(new ArrayList<>(), rucksack, new ArrayList<>(), restKapa);
        for(NodeObject nodeObject : rucksack){
            ausgewaehlt[nodeObject.getId()] = 1;
        }
        return valueOfBackpack(rucksack);
    }

    public static int greedyRucksack(int[] ausgewaehlt, int[] gewichte, int[] werte, int restKapa, int objIndex){
        List<NodeObject> rucksack = new ArrayList<>(ausgewaehlt.length);
        for(int i = 0; i < ausgewaehlt.length; i++){
            NodeObject temp = new NodeObject(gewichte[i], werte[i], i);
            rucksack.add(temp);
        }
        //call backtracking method
        rucksack = greedyBackpack(rucksack, restKapa);
        for(NodeObject nodeObject : rucksack){
            ausgewaehlt[nodeObject.getId()] = 1;
        }
        return valueOfBackpack(rucksack);
    }

    /**
     *
     * @param nobjs         List of NodeObjects to fill backpack
     * @param remainingCapa Capacity of backpack
     *
     * @return greedy backpack
     */
    public static List<NodeObject> greedyBackpack(List<NodeObject> nobjs, int remainingCapa){
        List<NodeObject> remainingNodeObjects = new ArrayList<>(nobjs), backpack = new ArrayList<>(nobjs.size());
        //check if enough capacity and NodeObjects are available
        while(remainingCapa > 0 && !remainingNodeObjects.isEmpty() && minWeight(remainingNodeObjects).getWeight() < remainingCapa){
            NodeObject temp = highestEfficency(remainingNodeObjects, remainingCapa);
            remainingNodeObjects.remove(temp);
            backpack.add(temp);
            remainingCapa -= temp.getWeight();
        }
        return backpack;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        int size = 10;
        int[] ausgewaehlt = new int[size];
        int[] werte = new int[size];
        int[] gewichte = new int[size];
        int[] ausgewaehltGreedy = new int[size];
        Arrays.fill(ausgewaehltGreedy, 0);
        fillArrays(ausgewaehlt, werte, gewichte);
        System.out.println("Backtracking\nDer maximale Wert beträgt: " + rucksack(ausgewaehlt, gewichte, werte, 20, 0));
        for(int i = 0; i < ausgewaehlt.length; i++){
            System.out.println(i + ": gewaehlt[" + i + "] =\t" + ausgewaehlt[i] + ", gewichte[" + i + "] =\t" + gewichte[i] + ", werte[" + i + "] =\t" + werte[i]);
        }
        System.out.println("Greedy\nDer Wert beträgt: " + greedyRucksack(ausgewaehltGreedy, gewichte, werte, 20, 0));
        for(int i = 0; i < ausgewaehlt.length; i++){
            System.out.println(i + ": gewaehlt[" + i + "] =\t" + ausgewaehltGreedy[i] + ", gewichte[" + i + "] =\t" + gewichte[i] + ", werte[" + i + "] =\t" + werte[i]);
        }
    }

    public static void printResult(){

    }

    public static void fillArrays(int[] ausgewaehlt, int[] gewichte, int[] werte){
        Arrays.fill(ausgewaehlt, 0);
        fillArray(gewichte);
        fillArray(werte);
    }

    private static void fillArray(int[] a){
        for(int i = 0; i < a.length; i++){
            a[i] = (int) Math.round(Math.random() * 19) + 1;
        }
    }

    /**
     *
     * @param currentBackpack      backpack to currently work with
     * @param remainingNodeObjects remaining NodeObjects to fill backpack
     * @param bestBackpack         best backpack found so far
     * @param remainingCapa        remaining capacity of backpack
     *
     * @return best backpack
     */
    private static List<NodeObject> backpack(List<NodeObject> currentBackpack, List<NodeObject> remainingNodeObjects, List<NodeObject> bestBackpack, int remainingCapa){
        //check if current backpack is complete
        if((remainingNodeObjects.isEmpty() || minWeight(remainingNodeObjects).getWeight() > remainingCapa) && remainingCapa >= 0){
            return currentBackpack;
        }
        //check if enough capacity and NodeObjects are available
        if(remainingCapa <= 0 || remainingNodeObjects.isEmpty()){
            return bestBackpack;
        }
        //iterate all possible backpacks
        for(NodeObject nobj : remainingNodeObjects){
            //create copy Lists to keep the original unchanged
            List<NodeObject> newRemainingNodeObjects = new ArrayList<>(remainingNodeObjects), newCurrentBackpack = new ArrayList<>(currentBackpack);
            newRemainingNodeObjects.remove(nobj);
            newCurrentBackpack.add(nobj);
            newCurrentBackpack = backpack(newCurrentBackpack, newRemainingNodeObjects, bestBackpack, remainingCapa - nobj.getWeight());
            //check if current backpack is better than the current best backpack
            if(valueOfBackpack(newCurrentBackpack) > valueOfBackpack(bestBackpack)){
                bestBackpack = newCurrentBackpack;
            }
        }
        return bestBackpack;
    }

    /**
     *
     * @param nobjs         backpack
     * @param remainingCapa remaining capacity of backpack
     *
     * @return NodeObject with the best value to weight ratio
     */
    private static NodeObject highestEfficency(List<NodeObject> nobjs, int remainingCapa){
        NodeObject highestEfficency = minWeight(nobjs);
        for(NodeObject nobj : nobjs){
            if(nobj.compareTo(highestEfficency) > 0 && nobj.getWeight() <= remainingCapa){
                highestEfficency = nobj;
            }
        }
        return highestEfficency;
    }

    /**
     *
     * @param nobjs backpack
     *
     * @return value of the entire backpack
     */
    private static int valueOfBackpack(List<NodeObject> nobjs){
        int value = 0;
        for(NodeObject nobj : nobjs){
            value += nobj.getValue();
        }
        return value;
    }

    /**
     *
     * @param nobjs backpack
     *
     * @return NodeObject with the lowest weight
     */
    private static NodeObject minWeight(List<NodeObject> nobjs){
        if(nobjs.isEmpty()){
            return null;
        }
        NodeObject minWeight = nobjs.get(0);
        for(NodeObject nobj : nobjs){
            if(nobj.getWeight() < minWeight.getWeight()){
                minWeight = nobj;
            }
        }
        return minWeight;
    }

}
