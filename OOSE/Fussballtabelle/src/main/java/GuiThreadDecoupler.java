
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Noah
 */
public class GuiThreadDecoupler {

    private final Runnable _runActionExecution;
    /*	private Runnable _runActionGuiUpdate;*/
    private Thread _threadExecute;

    /**
     * Constructor taking a runnable that is run decoupled from the GUI-Thread
     *
     * @param runActionExecution IN: The runnable
     */
    public GuiThreadDecoupler(Runnable runActionExecution) {
        _runActionExecution = runActionExecution;
    }

    /**
     * Starts the execution of the action passed by constructor
     */
    public void startActionExecution() {
        _threadExecute = new Thread(() -> {
            //NOTE: Do try-catch because it is good style to do it!
            try {
                _runActionExecution.run();
            } catch (Exception ex) {
                System.out.println("Exception in execution of thread:");
            }
        });
        _threadExecute.start();
    }

    /**
     * Starts an update of the GUI during the action execution.
     *
     * @param runActionGuiUpdate
     * @param iWaitAfter
     */
    public void startGuiUpdate(final Runnable runActionGuiUpdate, int iWaitAfter) {
        SwingUtilities.invokeLater(() -> {
            //NOTE: Do try-catch because it is good style to do it!
            try {
                runActionGuiUpdate.run();
            } catch (Exception ex) {
                System.out.println("Exception in updating the SWING-GUI:");
            }
        });

        try {
            Thread.sleep(iWaitAfter);
        } catch (InterruptedException ex) {
            //Do nothing as it is only concerned for sleep.
        }
    }

}
