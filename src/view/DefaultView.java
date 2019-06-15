package view;

import control.ControlException;
import control.IRouter;
import control.daos.connection.Connection;
import javax.swing.JFrame;

public class DefaultView extends View{
    
    public DefaultView(IRouter router){
        super(router);
    }
    
    public void display(){
        LoginFrame frame = new LoginFrame(this);
        frame.setLocationRelativeTo(null); //centers the screen
        frame.setVisible(true);
    }
    
    public int dispose(){
        try {
            Connection connection = Connection.getInstance();
            if(connection != null)
                connection.close();
            System.exit(0);
        } catch (ControlException ex) {
            ex.printStackTrace();
        }
        return JFrame.EXIT_ON_CLOSE;
    }
}