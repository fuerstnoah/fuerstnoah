/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Noah
 */
public class MySortedList extends MyList{
    
    protected IMyComparable[] _objs;
    public MySortedList(int capacity) {
        super(capacity);
    }
    
    public void add(IMyComparable obj){
        super.add(obj);
        sort();
    }
   
    @Override
    public IMyComparable get(int index){
        if (_objs[index] == null) throw new MyContainerException("Kein Objekt vorhanden");
        else return this._objs[index];
    }
    
    private void sort(){
        IMyComparable temp;  
        for(int i=0; i < _size; i++){  
            for(int j=1; j < (_size-i); j++){
                if(_objs[j-1] != null && _objs[j] !=null){
                    if(_objs[j-1].compareTo(_objs[j]) > 0){   
                        temp = _objs[j-1];  
                        _objs[j-1] = _objs[j];  
                        _objs[j] = temp;  
                    }  
                }
                
                          
            }  
        }
    }
    
    public static void main(String[] args) {
        MySortedList mySortedList = new MySortedList(10);
        Wuerfel wuerfel = new Wuerfel(6);
        Wuerfel wuerfel1 = new Wuerfel(5);
        mySortedList.add(wuerfel);
        mySortedList.add(wuerfel1);
        System.out.println(mySortedList.toString());
        
    }
    
    
}
