/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Noah
 */
public class Ruderboot extends Boot{

    public Ruderboot(String name, float betriebsStunden) {
        super(name, betriebsStunden);
    }
    
    @Override
    public int maxKapazitaet(){
        return 3;
    }
    
    
}
