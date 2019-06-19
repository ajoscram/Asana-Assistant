package asana_assistant_1.launcher;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.Router;
import asana_assistant_1.control.daos.connection.Connection;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import asana_assistant_1.view.DefaultView;
import asana_assistant_1.view.View;

class Main {

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) { 
            View.displayError(null, "You're not using Windows, so the interface might look a little clunky :(");
        }
        try {
            Connection.connect();
            new DefaultView(new Router()).display();
        } catch(ControlException ex) {
            DefaultView.displayError(null, ex);
        }
    }
}
