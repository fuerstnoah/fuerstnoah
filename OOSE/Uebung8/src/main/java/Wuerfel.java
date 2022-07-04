/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author noah
 */
public class Wuerfel implements IMyComparable{
    int _seiten;
    
    public Wuerfel(int seiten){
        _seiten = seiten;
        
    }
    
    public int wuerfle(){
        return (int) Math.round((Math.random() * this._seiten - 1) + 1);
    }

    public int getSeiten() {
        return _seiten;
    }

    public void setSeiten(int _seiten) {
        this._seiten = _seiten;
    }

    @Override
    public int compareTo(Object obj) {
        if(obj instanceof Wuerfel) return _seiten - ((Wuerfel) obj)._seiten;
        return this.hashCode() - obj.hashCode();
        }

    public static void main(String[] args) {
        Wuerfel wuerfel6 = new Wuerfel(6);
        Wuerfel wuerfel20 = new Wuerfel(20);
        System.out.println(wuerfel6.wuerfle());
        System.out.println(wuerfel20.wuerfle());
    }
    
}
