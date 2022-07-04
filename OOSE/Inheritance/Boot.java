
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Noah
 */
public class Boot {
    protected String name;
    protected float betriebsStunden;
    protected boolean belegt;

    public Boot(String name, float betriebsStunden) {
        this.name = name;
        this.betriebsStunden = betriebsStunden;
        this.belegt = false;
    }
    
    public int maxKapazitaet() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    public void verleihe() throws UnsupportedOperationException{
        if(belegt) throw new UnsupportedOperationException();
        else belegt = true;
    }
    
    public void gebeZurueck(float leihdauer){
        betriebsStunden += leihdauer;
        belegt = false;
    }
    
 
    @Override
    public String toString(){
        return this.getClass().getName() + getName() + ", BStd:" + betriebsStunden + ", Belegt:" + belegt;
    }
    
    
    public boolean equals(Boot boot){
        return boot.equals(this) && boot.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.name) + Float.floatToIntBits(this.betriebsStunden * 11) + 13 * (this.belegt ? 1 : 0);
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Boot other = (Boot) obj;
//        if (Float.floatToIntBits(this.betriebsStunden) != Float.floatToIntBits(other.betriebsStunden)) {
//            return false;
//        }
//        if (this.belegt != other.belegt) {
//            return false;
//        }
//        return Objects.equals(this.name, other.name);
//    }
    
    

    public String getName() {
        return name;
    }

    public float getBetriebsStunden() {
        return betriebsStunden;
    }

    public boolean isBelegt() {
        return belegt;
    }
    
    
    
    
    
    
    
    
}
