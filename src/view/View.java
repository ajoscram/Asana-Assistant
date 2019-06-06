package view;

import control.ControlException;
import control.IRouter;
import control.daos.connection.Connection;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import parse.ParseException;
import report.ReportException;

public class View {
    
    private View(){}
    
    public static void display(IRouter router){
        LoginFrame frame = new LoginFrame(router);
        frame.setLocationRelativeTo(null); //centering the screen
        frame.setVisible(true);
    }
    
    public static int dispose(){
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
    
    public static void displayInfo(Component parent, String message){
        JOptionPane.showMessageDialog(parent, message, "Asana Assistant",
                                      JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean displayConfirm(Component parent, String message){
        int reply =  JOptionPane.showConfirmDialog(parent, message, "Asana Assistant", 
                                      JOptionPane.YES_NO_OPTION);
        return reply == JOptionPane.YES_OPTION;
    }
    
    public static void displayError(Component parent, String message){
        JOptionPane.showMessageDialog(parent, message, "ERROR",
                                      JOptionPane.ERROR_MESSAGE);
    }
    
    public static void displayError(Component parent, ControlException exception){
        JOptionPane.showMessageDialog(parent, exception.getMessage(), "ERROR",
                                      JOptionPane.ERROR_MESSAGE);
    }
    
    public static void displayError(Component parent, ParseException exception){
        JOptionPane.showMessageDialog(parent, exception.getMessage(), "ERROR",
                                      JOptionPane.ERROR_MESSAGE);
    }
    
    public static void displayError(Component parent, ReportException exception){
        JOptionPane.showMessageDialog(parent, exception.getMessage(), "ERROR",
                                      JOptionPane.ERROR_MESSAGE);
    }
}