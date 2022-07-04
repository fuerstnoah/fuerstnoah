/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Noah
 */
public class VerleihManagement {

    private Boot[] meineBoote;
    private static VerleihManagement vmSingle = new VerleihManagement();

    private VerleihManagement() {
        Boot boot = new Boot("Boot", 10);
        Ruderboot ruderboot = new Ruderboot("Ruderboot", 200);
        Segelboot segelboot = new Segelboot(500, "Segelboot", 1000);
        Motoryacht motoryacht = new Motoryacht(1500, "Motoryacht", 2000);
        meineBoote = new Boot[4];
        meineBoote[0] = boot;
        meineBoote[1] = ruderboot;
        meineBoote[2] = segelboot;
        meineBoote[3] = motoryacht;

    }

    public Boot waehlePersonenOptimiert(int personen) throws Exception {
        Boot temp = new Boot(null, 0);
        for (Boot boot : meineBoote) {
            if (personen < boot.maxKapazitaet() && boot.maxKapazitaet() < temp.maxKapazitaet()) {
                temp = boot;
            }
        }
        if (temp.getName() != null) {
            return temp;
        } else {
            throw new Exception("Kein passender Verleih möglich");
        }
    }

    public void checkeUndMacheInspektionen() {
        for (Boot boot : meineBoote) {
            if (boot instanceof IGrossesBoot) {
                if (((IGrossesBoot) boot).brauchtInspektion()) {
                    if (boot.isBelegt()) {
                        System.out.println("Inspektion für " + boot.toString() + " nach Rückgabe erforderlich");
                    } else {
                        System.out.println("Mache Inspektion für " + boot.toString());
                        ((IGrossesBoot) boot).inspektionDurchgeführt();
                    }
                }
            }
        }
    }

    public static VerleihManagement getVM() {
        return vmSingle;
    }

    public static void main(String[] args) throws Exception {
        VerleihManagement vm = getVM();
        vm.checkeUndMacheInspektionen();

    }

}
