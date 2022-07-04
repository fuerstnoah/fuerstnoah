/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Noah
 */
public class Segelboot extends Boot implements IGrossesBoot {
    
    private float stdLetzteInspektion;

    public Segelboot(float stdLetzteInspektion, String name, float betriebsStunden) {
        super(name, betriebsStunden);
        this.stdLetzteInspektion = stdLetzteInspektion;
    }

    @Override
    public int maxKapazitaet(){
        return 10;
    }
    
    

    @Override
    public boolean brauchtInspektion() {
        return 400 < betriebsStunden - stdLetzteInspektion;
    }

    @Override
    public void inspektionDurchgeführt() {
        stdLetzteInspektion = betriebsStunden;
    }

    @Override
    public String toString() {
        return super.toString() + "StdLetzteInsp:" + stdLetzteInspektion;
    }
    
    
    
}
