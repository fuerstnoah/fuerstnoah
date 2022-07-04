package de.hsrm.ads;

import java.io.*;
import java.util.*;

public class BracketChecker{

    public static boolean check(String toCheck){
        BracketChecker.Stack<Character> stack = new BracketChecker.Stack<>();
        char[] chars = toCheck.toCharArray();
        for(char c : chars){
            if(c == '(' || c == '[' || c == '{'){
                stack.push(c);
            }else if(c == ')' || c == ']' || c == '}'){
                if(stack.isEmpty() || !isMatching(c, stack.top())){
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    public static void checkFile(String filepath){

        String[] lines = readStringArray(filepath);
        StringBuilder sb = new StringBuilder(2000);
        for(int i = 0; i < lines.length; ++i){
            sb.append(lines[i]);
        }
        String content = sb.toString();

        System.out.format("File %s correct? %b", filepath, check(content));
    }

    public static void main(String[] args){
        //checkFile("src/ads07/DLinkedPQueue.java");
        checkFile("src\\main\\java\\de\\hsrm\\cs\\ads\\DLinkedPQueue.java");
    }

    private static boolean isMatching(char c, char d){
        char x = switch(c){
            case ')' ->
                '(';
            case ']' ->
                '[';
            case '}' ->
                '{';
            default ->
                '!';
        };
        return x == d;
    }

    private static String[] readStringArray(String filename){
        // open a file, read its lines, return them as an array.

        try{
            ArrayList<String> lines = new ArrayList<>(2000);

            // Kein Scanner, wÃ¤re viel zu langsam
            // Erzwingen von UTF-8 (sonst komische Ergebnisse unter Windows)
            Reader in = new InputStreamReader(new FileInputStream(filename),
                   "UTF-8");

            try( BufferedReader reader = new BufferedReader(in)){
                String s;
                while((s = reader.readLine()) != null){
                    // Ignoriere Leerzeilen und Kommentare
                    if(s.length() != 0 && s.charAt(0) != '#'){
                        lines.add(s);
                    }
                }
            }

            String[] result = new String[lines.size()];
            return lines.toArray(result);

        }catch(IOException e){

            String msg = "I/O-Fehler bei " + filename + "\n" + e.getMessage();
            throw new RuntimeException(msg);

        }

    }

    public static class Stack<T>{

        LinkedList<T> list;

        public Stack(){
            list = new LinkedList<>();
        }

        public void push(T e){
            list.addFirst(e);
        }

        public void pop() throws NoSuchElementException{
            list.removeFirst();
        }

        public T top() throws NoSuchElementException{
            return list.getFirst();
        }

        public boolean isEmpty(){
            return list.isEmpty();
        }

    }

}
