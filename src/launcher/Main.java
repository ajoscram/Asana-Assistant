package launcher;

import control.ControlException;
import control.Router;
import control.daos.connection.Connection;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.View;

public class Main {

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) { 
            View.displayError(null, "You're not using Windows, so the interface might look a little clunky :(");
        }
        try {
            Connection.connect();
            View.display(Router.getInstance());
        } catch(ControlException ex) {
            View.displayError(null, ex);
        }
    }
}
