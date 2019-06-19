package asana_assistant_1.view;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.IRouter;
import java.awt.Component;
import javax.swing.JOptionPane;
import asana_assistant_1.parse.ParseException;
import asana_assistant_1.report.ReportException;

public abstract class View {
    
    protected IRouter router;
    
    public View(IRouter router){
        this.router = router;
    }
    
    public IRouter getRouter(){
        return router;
    }
    
    public abstract void display();
    public abstract int dispose();
    
    public static final void displayInfo(Component parent, String message){
        JOptionPane.showMessageDialog(parent, message, "Asana Assistant",
                                      JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static final boolean displayConfirm(Component parent, String message){
        int reply =  JOptionPane.showConfirmDialog(parent, message, "Asana Assistant", 
                                      JOptionPane.YES_NO_OPTION);
        return reply == JOptionPane.YES_OPTION;
    }
    
    public static final void displayError(Component parent, String message){
        JOptionPane.showMessageDialog(parent, message, "ERROR",
                                      JOptionPane.ERROR_MESSAGE);
    }
    
    public static final void displayError(Component parent, ControlException exception){
        JOptionPane.showMessageDialog(parent, exception.getMessage(), "ERROR",
                                      JOptionPane.ERROR_MESSAGE);
    }
    
    public static final void displayError(Component parent, ParseException exception){
        JOptionPane.showMessageDialog(parent, exception.getMessage(), "ERROR",
                                      JOptionPane.ERROR_MESSAGE);
    }
    
    public static final void displayError(Component parent, ReportException exception){
        JOptionPane.showMessageDialog(parent, exception.getMessage(), "ERROR",
                                      JOptionPane.ERROR_MESSAGE);
    }
}
