
import java.io.*;
import java.util.Arrays;
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
public class Tabelle {

    private final Verein[] vereine;
    private final int MAX = 18;
    private int current;
    
    public Tabelle() {
        this.vereine = new Verein[MAX];
        current = 0;
    }
    
    public int getCurrent() {
        return current;
    }

    public void addVerein(Verein v) {
        if (!(current == MAX)) {
           vereine[current] = v;
           sortTable();
           current++; 
        }
    }

    private void sortTable() {
        Verein[] temp = Arrays.copyOf(vereine, current);
        Arrays.sort(temp);
        System.arraycopy(temp, 0, vereine, 0, current);
    }

    public int findVerein(Verein v) {
        for (int i = 0; i < current; i++) {
            if (vereine[i].equals(v))return i;
        }
        return -1;
    }

    public boolean deleteVerein(Verein v) {
        int index = findVerein(v);
        if (index < 0)return false;
        for (int i = index; i < current - 1; i++) {
            vereine[i] = vereine[i + 1];
            vereine[i + 1] = null;
        }
        current--;
        return true;
    }

    public Verein[] getVereine() {
        return Arrays.copyOf(vereine, current);
    }

    public void resetTable() {
        for (int i = 0; i < current; i++)vereine[i] = new Verein(vereine[i].getName());
    }

    public void saveTable(File f) {
        if (f.isDirectory() || f.getName().endsWith(".txt")){
            try (FileWriter writer = new FileWriter(f, false)) {
                f.createNewFile();
                writer.write(this.toString());
            } catch (IOException ex) {
                ErrorHandler.logException(ex, false, Tabelle.class, "Error in saveTable(File f)", f);
                f = new File("Tabelle.txt");
                f.createNewFile();
                saveTable(f);
            }
        }
    }

    public void loadTable(File f) {
        if (f.getName().endsWith(".txt") && f.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                FileReader reader = new FileReader(f);
                while (reader.ready())text.append((char) reader.read());
            } catch (IOException ex) {
                ErrorHandler(ex, false, Tabelle.class, "Error in loadTable(f)",f);
                return;
            }
            current = Integer.parseInt(text.toString().split(":")[0].replace("current=", ""));
            Verein[] temp = new Verein[current];
            for (int i = 0; i < temp.length; i++) {
                String s = text.toString().replace("current=" + current + ":", "").split(";")[i];
                temp[i] = new Verein(s.split(",")[0]);
                temp[i].setGoals(Integer.parseInt(s.split(",")[1]));
                temp[i].setrGoals(Integer.parseInt(s.split(",")[2]));
                temp[i].setPoints(Integer.parseInt(s.split(",")[3]));
                temp[i].setGames(Integer.parseInt(s.split(",")[4]));
            }
            System.arraycopy(temp, 0, vereine, 0, current);
        }
    }

    public void calcErgebnis(Verein v1, Verein v2, int goals1, int goals2) {
        if (goals1 == goals2) {
            v1.setPoints(v1.getPoints() + 1);
            v2.setPoints(v2.getPoints() + 1);
        } else if (goals1 > goals2)v1.setPoints(v1.getPoints() + 3);
        else v2.setPoints(v2.getPoints() + 3);

        v1.setGoals(v1.getGoals() + goals1);
        v1.setrGoals(v1.getrGoals() + goals2);
        v1.setGames(v1.getGames() + 1);
        v2.setGoals(v2.getGoals() + goals2);
        v2.setrGoals(v2.getrGoals() + goals1);
        v2.setGames(v2.getGames() + 1);

        sortTable();
    }

    @Override
    public String toString() {
        String toString = "";
        for (Verein v : Arrays.copyOf(vereine, current))toString += v.toString() + ";";
        return "current=" + current + ":" + toString;
    }

    public static void main(String[] args) {
        Tabelle tabelle = new Tabelle();
        tabelle.addVerein(new Verein("test"));
        tabelle.addVerein(new Verein("abc"));
        System.out.println(tabelle);
    }
}
