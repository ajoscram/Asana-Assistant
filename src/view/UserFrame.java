package view;

import control.ControlException;
import control.IRouter;
import control.dtos.DisplayString;
import java.util.List;
import javax.swing.DefaultListModel;
import model.Project;
import model.User;

public class UserFrame extends javax.swing.JFrame {

    private LoginFrame parent;
    private IRouter router;
    private View source;
    private User user;
    
    private final DefaultListModel managedListModel;
    private final DefaultListModel collaborateListModel;
    
    public UserFrame(View source, LoginFrame parent, User user) {
        initComponents();
        this.setLocationRelativeTo(parent);
        this.setIconImage(parent.getIconImage());
        this.parent = parent;
        this.source = source;
        this.router = source.getRouter();
        this.user = user;
        this.managedListModel = new DefaultListModel();
        this.collaborateListModel = new DefaultListModel();
        this.setTitle(user.getName() + " - " + user.getEmail());
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
            DefaultView.displayError(this, ex);
        }
    }
    
    public void reloadCollaborateList(){
        try{
            List<DisplayString> strings = router.getCollabProjectStrings(user.getId());
            collaborateListModel.clear();
            for(DisplayString string : strings)
                collaborateListModel.addElement(string);
        } catch(ControlException ex) {
            DefaultView.displayError(this, ex);
        }
    }
    
    private void openProject(){
        try {
            Project project;
            if(this.tabbedPane.getSelectedComponent() == this.managedScrollpane && this.managedList.getSelectedValue() != null){
                project = router.getProject(((DisplayString)managedList.getSelectedValue()).getId());
                new ProjectFrame(source, this, project, user, true).setVisible(true);
                this.setVisible(false);
            } else if(this.tabbedPane.getSelectedComponent() == this.collaborateScrollpane && this.collaborateList.getSelectedValue() != null) {
                project = router.getProject(((DisplayString)collaborateList.getSelectedValue()).getId());
                new ProjectFrame(source, this, project, user, false).setVisible(true);
                this.setVisible(false);
            }
            else
                DefaultView.displayError(this, "Please select a project first.");
        } catch (ControlException ex) {
            DefaultView.displayError(this, ex);
        }
    }
    
    private void addProject(){
        new AddProjectDialog(source, this, user).setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPanePopupMenu = new javax.swing.JPopupMenu();
        tabbedPaneOpenProjectMenuItem = new javax.swing.JMenuItem();
        tabbedPaneSeparator = new javax.swing.JPopupMenu.Separator();
        tabbedPaneAddProjectMenuItem = new javax.swing.JMenuItem();
        tabbedPane = new javax.swing.JTabbedPane();
        managedScrollpane = new javax.swing.JScrollPane();
        managedList = new javax.swing.JList();
        collaborateScrollpane = new javax.swing.JScrollPane();
        collaborateList = new javax.swing.JList();
        menuBar = new javax.swing.JMenuBar();
        navigationMenu = new javax.swing.JMenu();
        logoutMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
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

        tabbedPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Projects"));
        tabbedPane.setComponentPopupMenu(tabbedPanePopupMenu);

        managedScrollpane.setBorder(null);
        managedScrollpane.setInheritsPopupMenu(true);

        managedList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        managedList.setInheritsPopupMenu(true);
        managedScrollpane.setViewportView(managedList);

        tabbedPane.addTab("Manage", managedScrollpane);

        collaborateScrollpane.setBorder(null);
        collaborateScrollpane.setInheritsPopupMenu(true);

        collaborateList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        collaborateList.setInheritsPopupMenu(true);
        collaborateScrollpane.setViewportView(collaborateList);

        tabbedPane.addTab("Collaborate", collaborateScrollpane);

        navigationMenu.setText("Navigation");

        logoutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, java.awt.event.InputEvent.ALT_MASK));
        logoutMenuItem.setText("Log Out");
        logoutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMenuItemActionPerformed(evt);
            }
        });
        navigationMenu.add(logoutMenuItem);
        navigationMenu.add(jSeparator2);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exitMenuItem.setText("Exit Asana Assistant");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        navigationMenu.add(exitMenuItem);

        menuBar.add(navigationMenu);

        optionsMenu.setText("Options");

        openProjectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openProjectMenuItem.setText("Open Selected Project");
        openProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openProjectMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(openProjectMenuItem);
        optionsMenu.add(jSeparator1);

        addProjectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        addProjectMenuItem.setText("Add New Managed Project");
        addProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProjectMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(addProjectMenuItem);

        menuBar.add(optionsMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
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

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        source.dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addProjectMenuItem;
    private javax.swing.JList collaborateList;
    private javax.swing.JScrollPane collaborateScrollpane;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem logoutMenuItem;
    private javax.swing.JList managedList;
    private javax.swing.JScrollPane managedScrollpane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu navigationMenu;
    private javax.swing.JMenuItem openProjectMenuItem;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JMenuItem tabbedPaneAddProjectMenuItem;
    private javax.swing.JMenuItem tabbedPaneOpenProjectMenuItem;
    private javax.swing.JPopupMenu tabbedPanePopupMenu;
    private javax.swing.JPopupMenu.Separator tabbedPaneSeparator;
    // End of variables declaration//GEN-END:variables

}
