import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

/**
 *
 * @author Noah
 */

public class Frame extends JFrame{
    private Tabelle table;
    private JMenuBar menu;
    private JMenu file, edit;
    private JMenuItem load, save, newTable, resetTable, addVerein, fillTable, delVerein, exit;
    private JPanel panel;
    private JTextArea area;
    private JButton newGame;
    private JFileChooser fChooser;
    private static final String[] vereinsNamen = {"BVB","FC Bayern","Mainz05","Wolfsburg","Gladbach","Frankfurt","Schalke","Lautern","Berlin", "Leipzig","Hoffenheim", "Leverkusen", "Darmstadt", "Stuttgart","Hamburg","Freiburg","Augsburg","Bremen","Hannover","Fuerth"};
    
    public Frame(){
        table = new Tabelle();
        buildUI();
    }
    
    private void buildUI(){
        menu = new JMenuBar();
        file = new JMenu("Datei");
        edit = new JMenu("Bearbeiten");
        load = new JMenuItem("Laden...");
        save = new JMenuItem("Speichern...");
        exit = new JMenuItem("Verlassen");
        newTable = new JMenuItem("Neue Tabelle");
        fillTable = new JMenuItem("Tabelle füllen");
        resetTable = new JMenuItem("Zurücksetzen");
        addVerein = new JMenuItem("Neuer Verein...");
        delVerein = new JMenuItem("Lösche Verein...");
        load.addActionListener((ActionEvent e) -> load());
        save.addActionListener((ActionEvent e) -> save());
        exit.addActionListener((ActionEvent e) -> System.exit(0));
        newTable.addActionListener((ActionEvent e) -> newTable());
        fillTable.addActionListener((ActionEvent e) -> fillTable());
        resetTable.addActionListener((ActionEvent e) -> resTable());
        addVerein.addActionListener((ActionEvent e) -> newVerein());
        delVerein.addActionListener((ActionEvent e) -> delVerein());
        file.add(save);
        file.add(load);
        file.add(new JSeparator());
        file.add(exit);
        edit.add(newTable);
        edit.add(fillTable);
        edit.add(resetTable);
        edit.add(new JSeparator());
        edit.add(addVerein);
        edit.add(delVerein);
        menu.add(file);
        menu.add(edit);
        
        panel = new JPanel(new BorderLayout(),true);
        area = new JTextArea("Name\tTore\tGegentore\tTordifferenz\tPunkte\tSpiele");
        area.setEditable(false);
        newGame = new JButton("Neues Spiel");
        newGame.addActionListener((ActionEvent e) -> newGame());
        panel.add(area,BorderLayout.CENTER);
        panel.add(newGame,BorderLayout.SOUTH);
        
        fChooser = new JFileChooser();
        fChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(){
                System.exit(0);
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(menu, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        setSize(750,500);
        setVisible(true);
    }
    
    private void newTable(){
        table = new Tabelle();
        reload();
    }
    
    private void resTable(){
        table.resetTable();
        reload();
    }
    
    private void reload(){
        area.setText("Name\tTore\tGegentore\tTordifferenz\tPunkte\tSpiele");
        for (Verein v : table.getVereine()) {
            area.append("\n" + v.getName());
            area.append("\t" + Integer.toString(v.getGoals()));
            area.append("\t" + Integer.toString(v.getrGoals()));
            area.append("\t" + Integer.toString(v.getGoals() - v.getrGoals()));
            area.append("\t" + Integer.toString(v.getPoints()));
            area.append("\t" + Integer.toString(v.getGames()));
        }
    }
    
    private void load(){
        if(fChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            table.loadTable(fChooser.getSelectedFile());
        }
        reload();
    }
    
    private void save(){
        if(fChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            table.saveTable(fChooser.getSelectedFile());
        }    
    }
    
    private void newVerein(){
        NewVereinDialog nvd = new NewVereinDialog(this);
        if(nvd.isCreated())table.addVerein(nvd.getV());
        reload();
        nvd.dispose();
    }
    
    private void fillTable(){
        for (int i = table.getCurrent(); i < 18; i++) {
            table.addVerein(new Verein(vereinsNamen[i])); 
        }
        reload();
    }
    
    private void delVerein(){
        DelVereinDialog dv = new DelVereinDialog(this, table.getVereine());
        if(dv.isSuccess())table.deleteVerein(table.getVereine()[dv.index()]);
        reload();
        dv.dispose();
    }
    
    private void newGame(){
        NewGameDialog ngd = new NewGameDialog(this, table.getVereine());
        if(ngd.isSuccess())table.calcErgebnis(table.getVereine()[ngd.getIndex()[0]], table.getVereine()[ngd.getIndex()[1]], ngd.getIndex()[2], ngd.getIndex()[3]);
        reload();
        ngd.dispose();
    }
    
    public static void main(String[] args) {
        new Frame();
    }
    
    private class NewVereinDialog extends JDialog{
        private final JButton create, cancel;
        private final JTextField name, goals, rGoals, points, games;
        private final JLabel nameL, goalsL, rGoalsL, pointsL, gamesL;
        private final JPanel panel, buttons;
        private Verein v;
        private boolean bool = false;
        
        private NewVereinDialog(Frame f){
            super(f, "Neuer Verein");
            setModal(true);
            create = new JButton("Erstellen");
            create.addActionListener((ActionEvent e) -> {
                create();
                setVisible(false);
                    });
            cancel = new JButton("Abbrechen");
            cancel.addActionListener((ActionEvent e) -> dispose());
            name = new JTextField("");
            goals = new JTextField("0");
            rGoals = new JTextField("0");
            points = new JTextField("0");
            games = new JTextField("0");
            nameL = new JLabel("Name:");
            goalsL = new JLabel("Tore:");
            rGoalsL = new JLabel("Gegentore:");
            pointsL = new JLabel("Punkte:");
            gamesL = new JLabel("Spiele:");
            
            buttons = new JPanel(new GridLayout(1,2));
            buttons.add(create);
            buttons.add(cancel);
            
            panel = new JPanel(new GridLayout(5,2));
            panel.add(nameL);
            panel.add(name);
            panel.add(goalsL);
            panel.add(goals);
            panel.add(rGoalsL);
            panel.add(rGoals);
            panel.add(pointsL);
            panel.add(points);
            panel.add(gamesL);
            panel.add(games);
            
            add(panel, BorderLayout.CENTER);
            add(buttons, BorderLayout.SOUTH);
            setSize(f.getSize());
            setVisible(true);
        }
        
        private void create(){
            v = new Verein(name.getText());
            v.setGoals(Integer.parseInt(goals.getText()));
            v.setrGoals(Integer.parseInt(rGoals.getText()));
            v.setPoints(Integer.parseInt(points.getText()));
            v.setGames(Integer.parseInt(games.getText()));
            bool = true;
        }
        
        public boolean isCreated(){
            return bool;
        }

        public Verein getV() {
            return v;
        }

   
    }
    
    private class DelVereinDialog extends JDialog{
        private final JButton del, cancel;
        private final JPanel panel, buttons;
        private boolean success;
        private final JList<String> list;
        private final DefaultListModel<String> model;
        private int index;
        
        private DelVereinDialog(Frame f, Verein[] vereine){
            super(f, "Neuer Verein",true);
            success = false;
            del = new JButton("Löschen");
            del.addActionListener((ActionEvent e) -> {
                del();
                setVisible(false);
            });
            cancel = new JButton("Abbrechen");
            cancel.addActionListener((ActionEvent e) -> dispose());
            
            model = new DefaultListModel<>();
            for (Verein verein : vereine) model.addElement(verein.getName());
            list = new JList<>(model);
            
            buttons = new JPanel(new GridLayout(1,2));
            buttons.add(del);
            buttons.add(cancel);
            
            panel = new JPanel(new BorderLayout());
            panel.add(list, BorderLayout.CENTER);
            
            add(panel, BorderLayout.CENTER);
            add(buttons, BorderLayout.SOUTH);
            setSize(f.getSize());
            setVisible(true);
        }
        
        private void del(){ 
            index = list.getSelectedIndex();
            success = true;
        }

        public int index() {
            return index;
        }

        public boolean isSuccess() {
            return success;
        }
   
    }
    
    private class NewGameDialog extends JDialog implements IReporter{
        private final JList<String> list;
        private final DefaultListModel<String> model;
        private final JButton sel, cancel;
        private final JPanel buttons;
        private boolean bool, success;
        private final int[] index;
        private Spiel spiel;
        private final Verein[] _vereine;
        private JTextArea area;
        private JScrollPane sp;
        private GuiThreadDecoupler gtd;
        
        private NewGameDialog(Frame f, Verein[] vereine){
            super(f,"Neues Spiel",true);
            _vereine = vereine;
            index = new int[4];
            bool = false;
            success = false;
            sel = new JButton("Auswählen");
            sel.addActionListener((ActionEvent e) -> sel());
            cancel = new JButton("Abbrechen");
            cancel.addActionListener((ActionEvent e) -> dispose());
            
            model = new DefaultListModel<>();
            for (Verein v : vereine) model.addElement(v.getName());
            list = new JList<>(model);
            sp = new JScrollPane(list);
            add(sp,BorderLayout.CENTER);
            buttons = new JPanel(new GridLayout(1,2));
            buttons.add(sel);
            buttons.add(cancel);
            add(buttons, BorderLayout.SOUTH);
            setSize(f.getSize());
            setVisible(true);
        }
        
        private void sel(){
            if(!bool){
                index[0] = list.getSelectedIndex();
                bool = true;
            }
            else if(index[0] == list.getSelectedIndex())JOptionPane.showMessageDialog(this,"Ungültige Eingabe", "Error",JOptionPane.WARNING_MESSAGE);
            else {
                index[1] = list.getSelectedIndex();
                startGame(); 
            }
        }
        
        private void startGame(){
            area = new JTextArea("");
            sp = new JScrollPane(area);
            add(sp,BorderLayout.CENTER);
            validate();
            repaint();
            spiel = new Spiel(_vereine[index[0]].getMann(), _vereine[index[1]].getMann(),this);
            Runnable runAction = () -> {
                spiel.starteSpiel(spiel);
                success = true;
                setVisible(false);
            };
            gtd = new GuiThreadDecoupler(runAction);
            gtd.startActionExecution();
            index[2] = spiel.getTore1();
            index[3] = spiel.getTore2();
        }
        
        public boolean isSuccess(){
            return success;
        }
        
        public int[] getIndex(){
            return index;
        }

        @Override
        public void giveNewMessage(String str) {
            if (gtd == null)area.append("\n" + str);
            else gtd.startGuiUpdate(() -> area.append("\n" + str), 100);
        }
    }
}
