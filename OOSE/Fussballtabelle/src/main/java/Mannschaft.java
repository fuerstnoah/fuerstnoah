// Importstatement nicht unbedingt notwendig, weil java.lang automatisch importiert wird:
//import java.lang.Math;

public class Mannschaft{
	
	private String _name;
	private int _sturm;
	private int _mi;
	private int _vert;
	private int _torw;
	
	public Mannschaft(String name){
		_name=name;  
		 /* Funktioniert so, aber ineffizient, weil immer ein neuer Würfel erzeugt werden muss:
		 
		Wuerfel wWerte = new Wuerfel(6);  // am besten diese Werte ändern damit der Score realistischer ist
		_sturm = 1+wWerte.wuerfle();
		_mi = 1+2*wWerte.wuerfle();
		_vert = 4+2*wWerte.wuerfle();
		_torw = 4+2*wWerte.wuerfle();
		*/
		
		// So ist es besser:
		_sturm = 4 + Wuerfel.W6.wuerfle();
		_mi =   4 +  Wuerfel.W6.wuerfle();
		_vert = 14 + Wuerfel.W6.wuerfle();
		if (_vert>=20) _vert=19;
		_torw = 14 + Wuerfel.W6.wuerfle();
		if (_torw>=20) _torw=19;
	}		
	
	public boolean angriff(){
		
		int wurf1 = Wuerfel.W20.wuerfle();    //2mal würfeln
		int wurf2 =  Wuerfel.W20.wuerfle();
				
            return wurf1<=_mi && wurf2<= _sturm;
	}
		
	public boolean verteidigt(){    //das gleiche machen wie davor+-
		//Wuerfel wVert = new Wuerfel(20);
		int wurf1 = Wuerfel.W20.wuerfle();
		int wurf2 = Wuerfel.W20.wuerfle();
		
            return wurf1<=_mi && wurf2<=_vert;
	}
		
	public int schiesseTor(){
		return Wuerfel.W6.wuerfle();
	}
	
	public boolean kassiereTor(int tsStaerke){
		if(tsStaerke>=5){
			return true;
		}else if(tsStaerke>=1 && tsStaerke<=4){
                    return Wuerfel.W20.wuerfle() > _torw;
		}
		return false;
	}
	
	public String getName(){
		return _name;
	}
	
	public void setName(String name){
		_name=name;
	}
	
	//4 methoden um bei spielereignis 2 Punkte abzuziehen
	public void minusTorw(int x){
		_torw = _torw - x;
	}
	
	public void minusSturm(int x){
		_sturm = _sturm - x;
	}
	
	public void minusMi(int x){
		_mi = _mi - x;
	}	
	
	public void minusVert(int x){
		_vert = _vert - x;
	}
	
	
	public static void main(String[] args){
		
		
		Mannschaft m1 = new Mannschaft("BVB");
		Mannschaft m2 = new Mannschaft("FCB");
		
		//IDEE: Wir testen alle vier Möglichkeiten mittels While-Schleife:
		
		boolean b1=false, b2=false,b3=false,b4=false;
		while ( b1==false || b2==false || b3==false || b4==false){
			if(m1.angriff()){   
				if(m2.verteidigt()){
					System.out.println(m1.getName()+" hat attackiert aber "+m2.getName()+ " konnte noch verteidigen");
					 b1= true;
				}else{
					int schuss= m1.schiesseTor();
					if(m2.kassiereTor(schuss)){
						System.out.println("Tooooor durch " + m1.getName());
						b2= true;
					}else{
						System.out.println(m2.getName()+" konnte den Schuss parieren");
						b3= true;
					}
				}
			}
			else{
				System.out.println("Der Angriff von " +m1.getName()+" war nicht erfolgreich");
				b4= true;
			}
		}
	}
}
	
