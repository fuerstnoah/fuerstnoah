
import java.util.Arrays;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Noah
 */
public class MyList extends MyAbstractContainer{

    public MyList(int capacity) {
        super(capacity);
    }
    
    public void add(Object obj){
        this.ensureObjsHasSpace();
        this._objs[this._size] = obj;
        this._size += 1;
    }
    
    private void ensureObjsHasSpace(){
        if(this._size == this.capacity()){
            Object[] temp = Arrays.copyOf(this._objs, this.capacity() * 2);
            this._objs = temp;
        }
    }
    
    public boolean remove(Object obj){
        int index = this.find(obj);
        if(index == -1) return false;
        this._objs[index] = null;
        for (int i = index; i < this._size; i++) {
            this._objs[i] = i+1;
        }
        this._size--;
        return true;
    }
    
    public static void main(String[] args) {
        MyList ml = new MyList(5);
        ml.add(new Wuerfel(5));
        System.out.println(ml.toString());
    }
    
}
