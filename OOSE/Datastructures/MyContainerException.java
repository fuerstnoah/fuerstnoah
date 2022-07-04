/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */

/**
 *
 * @author Noah
 */
public class MyContainerException extends RuntimeException{

    /**
     * Creates a new instance of <code>MyContainerException</code> without
     * detail message.
     */
    public MyContainerException() {
    }

    /**
     * Constructs an instance of <code>MyContainerException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MyContainerException(String msg) {
        super(msg);
    }
}
