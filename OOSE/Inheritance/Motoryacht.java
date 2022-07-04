/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Noah
 */
public class Motoryacht extends Boot implements IGrossesBoot{
    
    private float stdLetzteInspektion;

    public Motoryacht(float stdLetzteInspektion, String name, float betriebsStunden) {
        super(name, betriebsStunden);
        this.stdLetzteInspektion = stdLetzteInspektion;
    }
    
    @Override
    public boolean brauchtInspektion() {
        return 1000 < betriebsStunden - stdLetzteInspektion;
    }

    @Override
    public void inspektionDurchgeführt() {
        
    }
    
    @Override
    public int maxKapazitaet(){
        return 10;
    }
    
}
