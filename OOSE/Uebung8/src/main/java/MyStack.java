
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Noah
 */
public class MyStack extends MyAbstractContainer{

    public MyStack(int capacity) {
        super(capacity);
    }
    
    public void push(Object obj){
        try{
            this._objs[this._size] = obj;
            this._size += 1;
        }catch(Exception ex){
            ex.getMessage();
        }
    }
    
    public Object pop()throws Exception{
        if (this._size == 0) throw new Exception();
        else {
            Object temp = this._objs[this._size - 1];
            this._objs[this._size - 1] = null;
            return temp;
        } 
    }
    
    public static void main(String[] args) {
        MyStack myStack = new MyStack(10);
        try {
            myStack.pop();
        } catch (Exception ex) {
            Logger.getLogger(MyStack.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(myStack.toString());
    }
}
