/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Noah
 */
public abstract class MyAbstractContainer {
    protected Object[] _objs;
    protected int _size;
    
    public MyAbstractContainer(int capacity){
        _objs = new Object[capacity];
        _size = 0;
    }
    
    public int capacity(){
        return _objs.length;
    }
    
    public Object get(int index)throws MyContainerException{
        if (_objs[index] == null) throw new MyContainerException("Kein Objekt vorhanden");
        else return _objs[index];
    }
    
    public int find(Object obj){
        for(int i = 0; i < _objs.length; i++){
            if(_objs[i] == obj)return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(_objs.getClass().getName() + " (Cap: " + _objs.length + ", Size: " + _size + "):");
        for (int i = 0; i < _size - 1; i++) {
            sb.append("/n" + _objs[i].toString());
        }
        return sb.toString();
    }
    
    
    
}
