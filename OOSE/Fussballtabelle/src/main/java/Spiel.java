
public class Spiel {

    private final Mannschaft _mann1;
    private final Mannschaft _mann2;
    private int _tore1 = 0;
    private int _tore2 = 0;
    private final IReporter rep;

    public Spiel(Mannschaft mann1, Mannschaft mann2, IReporter reporter) {
        _mann1 = mann1;
        _mann2 = mann2;
        rep = reporter;
        rep.giveNewMessage(_mann1.getName() + " vs. " + _mann2.getName());
    }

    public void starteSpielMin(int spielMin) {
        if (_mann1.angriff()) {
            rep.giveNewMessage("AV               ");
            if (_mann2.verteidigt())rep.giveNewMessage("VT");
            else {
                rep.giveNewMessage("!!!");
                int schuss = _mann1.schiesseTor();
                if (_mann2.kassiereTor(schuss)) {
                    _tore1 += 1;
                    rep.giveNewMessage("TOOOR fuer Mannschaft " + _mann1.getName() + " in der " + spielMin + "ten Spielminute!");
                    rep.giveNewMessage("Neuer Spielstand " + _mann1.getName() + " " + _tore1 + ":" + _tore2 + " " + _mann2.getName());
                } else rep.giveNewMessage("Gute Parade durch den Keeper von Mannschaft " + _mann2.getName());
            }
        }

        if (_mann2.angriff()) {
            rep.giveNewMessage("AV");
            if (_mann1.verteidigt()) rep.giveNewMessage("VT               AV");
            else {
                rep.giveNewMessage("!!!              AV");
                int schuss = _mann2.schiesseTor();
                if (_mann1.kassiereTor(schuss)) {
                    _tore2 += 1;
                    rep.giveNewMessage("TOOOR fuer Mannschaft " + _mann2.getName() + " in der " + spielMin + "ten Spielminute!");
                    rep.giveNewMessage("Neuer Spielstand " + _mann1.getName() + " " + _tore1 + ":" + _tore2 + " " + _mann2.getName());
                } else rep.giveNewMessage("Gute Parade durch den Keeper von Mannschaft " + _mann1.getName());
            }
        }
    }

    public void starteSpiel(Spiel a) {
        for (int i = 0; i <= 90; i++) {
            rep.giveNewMessage("\nSpielminute: " + i);
            starteSpielMin(i);
            try {
                Thread.sleep(250); // Erweiterung aus Aufgabe 5a -> wir verzögern damit das Spiel so dass eine Minute in 0,5 Sekunden vergeht.
            } catch (InterruptedException e) {
            }

        }
        //HIER NOCH PAAR IFS SCHREIBEN FÜR UNENTSCHIEDEN UND GEWONNEN BLABLA UND AUF KONSOLE AUSGEBEN LASSEN
        if (_tore1 == _tore2)rep.giveNewMessage("\nDas Spiel zwischen " + _mann1.getName() + " und " + _mann2.getName() + " ist unentschieden ausgefallen mit: " + _tore1 + ":" + _tore2);
        if (_tore1 > _tore2)rep.giveNewMessage("\nSieg fuer: " + _mann1.getName() + " " + _tore1 + ":" + _tore2);
        if (_tore1 < _tore2)rep.giveNewMessage("\nSieg fuer: " + _mann2.getName() + " " + _tore2 + ":" + _tore1);
    }

    public Mannschaft getMannschaft1() {
        return _mann1;
    }

    public Mannschaft getMannschaft2() {
        return _mann2;
    }

    public int getTore1() {
        return _tore1;
    }

    public int getTore2() {
        return _tore2;
    }

    public void addTore1(int x) {
        _tore1 = _tore1 + x;
    }

    public void addTore2(int x) {
        _tore2 = _tore2 + x;
    }

}
