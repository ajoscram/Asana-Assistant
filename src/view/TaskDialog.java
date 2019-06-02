package view;

import control.IRouter;
import model.Task;
import model.User;

public class TaskDialog extends javax.swing.JDialog {

    private IRouter router;
    private Task task;
    private User user;
    
    public TaskDialog(ProjectFrame parent, IRouter router, Task task, User user) {
        super(parent, true);
        initComponents();
        this.setLocationRelativeTo(parent);
        this.setIconImage(parent.getIconImage());
        this.setTitle(task.getName());
        
        this.router = router;
        this.task = task;
        this.user = user;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
