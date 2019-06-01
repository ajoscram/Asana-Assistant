package view;

import control.ControlException;
import control.IRouter;
import control.dtos.DisplayString;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import model.Project;
import model.User;

public class UserFrame extends javax.swing.JFrame {

    private LoginFrame parent;
    private IRouter router;
    private User user;
    
    private final DefaultListModel managedListModel;
    private final DefaultListModel collaborateListModel;
    
    public UserFrame(LoginFrame parent, IRouter router, User user) {
        initComponents();
        this.setLocationRelativeTo(parent);
        this.parent = parent;
        this.router = router;
        this.user = user;
        this.managedListModel = new DefaultListModel();
        this.collaborateListModel = new DefaultListModel();
        this.userLabel.setText(user.getName() + " (" + user.getEmail() + ")");
        this.managedList.setModel(managedListModel);
        this.collaborateList.setModel(collaborateListModel);
        reloadManagedList();
        reloadCollaborateList();
    }
    
    public void reloadManagedList(){
        try{
            List<DisplayString> strings = router.getAdminProjectStrings(user.getId());
            managedListModel.clear();
            for(DisplayString string : strings)
                managedListModel.addElement(string);
        } catch(ControlException ex) {
            View.displayError(this, ex);
        }
    }
    
    public void reloadCollaborateList(){
        try{
            List<DisplayString> strings = router.getCollabProjectStrings(user.getId());
            collaborateListModel.clear();
            for(DisplayString string : strings)
                collaborateListModel.addElement(string);
        } catch(ControlException ex) {
            View.displayError(this, ex);
        }
    }
    
    private void openProject(){
        try {
            Project project;
            if(this.tabbedPane.getSelectedComponent() == this.managedPanel && this.managedList.getSelectedValue() != null){
                project = router.getProject(((DisplayString)managedList.getSelectedValue()).getId());
                new ProjectFrame(this, router, project, user, true).setVisible(true);
                this.setVisible(false);
            } else if(this.tabbedPane.getSelectedComponent() == this.collaboratePanel && this.collaborateList.getSelectedValue() != null) {
                project = router.getProject(((DisplayString)collaborateList.getSelectedValue()).getId());
                new ProjectFrame(this, router, project, user, false).setVisible(true);
                this.setVisible(false);
            }
            else
                View.displayError(this, "Please select a project first.");
        } catch (ControlException ex) {
            View.displayError(this, ex);
        }
    }
    
    private void addProject(){
        new AddProjectDialog(this, router, user).setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPanePopupMenu = new javax.swing.JPopupMenu();
        tabbedPaneOpenProjectMenuItem = new javax.swing.JMenuItem();
        tabbedPaneSeparator = new javax.swing.JPopupMenu.Separator();
        tabbedPaneAddProjectMenuItem = new javax.swing.JMenuItem();
        tabbedPane = new javax.swing.JTabbedPane();
        managedPanel = new javax.swing.JPanel();
        managedScrollpane = new javax.swing.JScrollPane();
        managedList = new javax.swing.JList();
        collaboratePanel = new javax.swing.JPanel();
        collaborateScrollpane = new javax.swing.JScrollPane();
        collaborateList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        logoutMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        openProjectMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        addProjectMenuItem = new javax.swing.JMenuItem();

        tabbedPaneOpenProjectMenuItem.setText("Open Selected Project");
        tabbedPaneOpenProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabbedPaneOpenProjectMenuItemActionPerformed(evt);
            }
        });
        tabbedPanePopupMenu.add(tabbedPaneOpenProjectMenuItem);
        tabbedPanePopupMenu.add(tabbedPaneSeparator);

        tabbedPaneAddProjectMenuItem.setText("Add New Managed Project");
        tabbedPaneAddProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabbedPaneAddProjectMenuItemActionPerformed(evt);
            }
        });
        tabbedPanePopupMenu.add(tabbedPaneAddProjectMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Asana Assistant");

        tabbedPane.setComponentPopupMenu(tabbedPanePopupMenu);

        managedPanel.setInheritsPopupMenu(true);

        managedScrollpane.setBorder(null);
        managedScrollpane.setInheritsPopupMenu(true);

        managedList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        managedList.setInheritsPopupMenu(true);
        managedScrollpane.setViewportView(managedList);

        javax.swing.GroupLayout managedPanelLayout = new javax.swing.GroupLayout(managedPanel);
        managedPanel.setLayout(managedPanelLayout);
        managedPanelLayout.setHorizontalGroup(
            managedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
            .addGroup(managedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(managedScrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
        );
        managedPanelLayout.setVerticalGroup(
            managedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
            .addGroup(managedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(managedScrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Manage", managedPanel);

        collaboratePanel.setInheritsPopupMenu(true);

        collaborateScrollpane.setBorder(null);
        collaborateScrollpane.setInheritsPopupMenu(true);

        collaborateList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        collaborateList.setInheritsPopupMenu(true);
        collaborateScrollpane.setViewportView(collaborateList);

        javax.swing.GroupLayout collaboratePanelLayout = new javax.swing.GroupLayout(collaboratePanel);
        collaboratePanel.setLayout(collaboratePanelLayout);
        collaboratePanelLayout.setHorizontalGroup(
            collaboratePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
            .addGroup(collaboratePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(collaborateScrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
        );
        collaboratePanelLayout.setVerticalGroup(
            collaboratePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
            .addGroup(collaboratePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(collaborateScrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Collaborate", collaboratePanel);

        jLabel1.setText("Logged in as:");

        userLabel.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        userLabel.setText("User");

        fileMenu.setText("File");

        logoutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, java.awt.event.InputEvent.ALT_MASK));
        logoutMenuItem.setText("Log Out");
        logoutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(logoutMenuItem);
        fileMenu.add(jSeparator2);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exitMenuItem.setText("Exit Asana Assistant");
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        openProjectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openProjectMenuItem.setText("Open Selected Project");
        openProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openProjectMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(openProjectMenuItem);
        editMenu.add(jSeparator1);

        addProjectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        addProjectMenuItem.setText("Add New Managed Project");
        addProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProjectMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(addProjectMenuItem);

        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(userLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openProjectMenuItemActionPerformed
        openProject();
    }//GEN-LAST:event_openProjectMenuItemActionPerformed

    private void tabbedPaneOpenProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabbedPaneOpenProjectMenuItemActionPerformed
        openProject();
    }//GEN-LAST:event_tabbedPaneOpenProjectMenuItemActionPerformed

    private void addProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProjectMenuItemActionPerformed
        addProject();
    }//GEN-LAST:event_addProjectMenuItemActionPerformed

    private void tabbedPaneAddProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabbedPaneAddProjectMenuItemActionPerformed
        addProject();
    }//GEN-LAST:event_tabbedPaneAddProjectMenuItemActionPerformed

    private void logoutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutMenuItemActionPerformed
        parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addProjectMenuItem;
    private javax.swing.JList collaborateList;
    private javax.swing.JPanel collaboratePanel;
    private javax.swing.JScrollPane collaborateScrollpane;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem logoutMenuItem;
    private javax.swing.JList managedList;
    private javax.swing.JPanel managedPanel;
    private javax.swing.JScrollPane managedScrollpane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openProjectMenuItem;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JMenuItem tabbedPaneAddProjectMenuItem;
    private javax.swing.JMenuItem tabbedPaneOpenProjectMenuItem;
    private javax.swing.JPopupMenu tabbedPanePopupMenu;
    private javax.swing.JPopupMenu.Separator tabbedPaneSeparator;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables

}
