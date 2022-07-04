/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Noah
 */
public class Aufgabe4 extends JFrame{
    private static DancingTextPanel dtp;
    private static JButton start,stop,startUtil,stopUtil;
    private static JPanel south;
    private static javax.swing.Timer timer;
    private static java.util.Timer utilTimer;
    
    public Aufgabe4(){
        dtp = new DancingTextPanel();
        this.setLayout(new BorderLayout());
        this.add(dtp, BorderLayout.CENTER);
        south = new JPanel(new GridLayout());
        startUtil = new JButton("Start Util Timer");
        startUtil.addActionListener((e)->startUtil());
        stopUtil = new JButton("Stop Util Timer");
        stopUtil.addActionListener((e)->stopUtil());
        start = new JButton("Start Swing Timer");
        start.addActionListener((e)->start());
        stop = new JButton("Stop Swing Timer");
        stop.addActionListener((e)->stop());
        timer = new javax.swing.Timer(100,((e)->paint()));
        utilTimer = new java.util.Timer();
        south.add(start);
        south.add(stop);
        south.add(startUtil);
        south.add(stopUtil);
        this.add(south,BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }
    
    private void start(){
        timer.start();
    }
    
    private void stop(){
        timer.stop();
    }
    
    private void paint(){
        dtp.paint(dtp.getGraphics());
    }
    
    public static void main(String[] args) {
        new Aufgabe4();
    }

    private void startUtil() {
        utilTimer.scheduleAtFixedRate(new Task(), 0, 100);
    }

    private void stopUtil() {
        utilTimer.cancel();
        utilTimer.purge();
    }
    
    private class Task extends TimerTask{

        @Override
        public void run() {
            paint();
        }

        
        
    }
}
