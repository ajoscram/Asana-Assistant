package view;

import control.ControlException;
import control.IRouter;
import control.dtos.DisplayString;
import control.dtos.Filter;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import model.Project;
import model.User;

public class ProjectFrame extends javax.swing.JFrame {

    private UserFrame parent;
    private IRouter router;
    private Project project;
    private User user;
    
    private final DefaultListModel activeCollaboratorsListModel;
    private final DefaultListModel bannedCollaboratorsListModel;
    private final DefaultTreeModel tasksTreeModel;
    
    public ProjectFrame(UserFrame parent, IRouter router, Project project, User user, boolean isAdministrator) {
        initComponents();
        this.setLocationRelativeTo(parent);
        this.setIconImage(parent.getIconImage());
        this.setTitle(project.getName());
        this.activeCollaboratorsListModel = new DefaultListModel();
        this.bannedCollaboratorsListModel = new DefaultListModel();
        this.tasksTreeModel = new DefaultTreeModel(new DefaultMutableTreeNode("root"));
        
        this.parent = parent;
        this.router = router;
        this.project = project;
        this.user = user;
        
        try{
            List<DisplayString> activeCollaborators = router.getActiveUserStrings(project.getId());
            List<DisplayString> bannedCollaborators = router.getBannedUserStrings(project.getId());
            List<DisplayString> tasks = router.getTaskStrings(project.getId());
            
            //tasks
            
            this.tasksTree.setCellRenderer(new TaskTreeCellRenderer(router));
            this.tasksTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            this.tasksTree.setModel(tasksTreeModel);
            refreshTasksTree(Filter.EMPTY);
            this.tasksTree.setShowsRootHandles(true);
            
            //collaborator lists
            this.activeCollaboratorsList.setModel(activeCollaboratorsListModel);
            for(DisplayString collaborator : activeCollaborators)
                activeCollaboratorsListModel.addElement(collaborator);
            this.bannedCollaboratorsList.setModel(bannedCollaboratorsListModel);
            for(DisplayString collaborator : bannedCollaborators)
                bannedCollaboratorsListModel.addElement(collaborator);

            //filters
            this.collaboratorFilterComboBox.addItem(new DisplayString(-1, " -"));
            this.collaboratorFilterComboBox.setSelectedIndex(0);
            for(DisplayString collaborator : activeCollaborators)
                this.collaboratorFilterComboBox.addItem(collaborator);
            for(DisplayString collaborator : bannedCollaborators)
                this.collaboratorFilterComboBox.addItem(collaborator);
            this.taskFilterComboBox.addItem(new DisplayString(-1, " -"));
            this.taskFilterComboBox.setSelectedIndex(0);
            for(DisplayString task : tasks)
                this.taskFilterComboBox.addItem(task);
        } catch(ControlException ex){
            View.displayError(parent, ex);
            this.dispose();
        }
    }
    
    private DefaultMutableTreeNode getTaskNode(DisplayString task, Filter filter) throws ControlException{
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(task);
        for(DisplayString subtask : router.getSubtaskStrings(task.getId(), filter))
            node.add(getTaskNode(subtask, filter));
        return node;
    }
    
    private void refreshTasksTree(Filter filter){
        try{
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)tasksTreeModel.getRoot();
            root.removeAllChildren();
            List<DisplayString> tasks = router.getTaskStrings(project.getId(), filter);
            for(DisplayString task : tasks)
                root.add(getTaskNode(task, filter));
            tasksTreeModel.reload();
            for(int i = 0; i < tasksTree.getRowCount(); i++)
                tasksTree.expandRow(i);
        } catch(ControlException ex){
            View.displayError(this, ex);
        }
    }
    
    private void refreshFilters(){
        try{
            List<DisplayString> activeCollaborators = router.getActiveUserStrings(project.getId());
            List<DisplayString> bannedCollaborators = router.getBannedUserStrings(project.getId());
            
            this.collaboratorFilterComboBox.addItem(new DisplayString(-1, "-"));
            this.collaboratorFilterComboBox.setSelectedIndex(0);
            for(DisplayString collaborator : activeCollaborators)
                this.collaboratorFilterComboBox.addItem(collaborator);
            for(DisplayString collaborator : bannedCollaborators)
                this.collaboratorFilterComboBox.addItem(collaborator);
        } catch(ControlException ex){
            View.displayError(this, ex);
        }
    }
    
    private void refreshCollaboratorLists(){
        try{
            List<DisplayString> activeCollaborators = router.getActiveUserStrings(project.getId());
            List<DisplayString> bannedCollaborators = router.getBannedUserStrings(project.getId());
            
            this.activeCollaboratorsListModel.clear();
            for(DisplayString collaborator : activeCollaborators)
                activeCollaboratorsListModel.addElement(collaborator);  
            
            this.bannedCollaboratorsListModel.clear();
            for(DisplayString collaborator : bannedCollaborators)
                bannedCollaboratorsListModel.addElement(collaborator);
        } catch(ControlException ex){
            View.displayError(this, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tasksPopupMenu = new javax.swing.JPopupMenu();
        openTaskPopupMenuItem = new javax.swing.JMenuItem();
        resetFiltersPopupMenuItem = new javax.swing.JMenuItem();
        synchronizePopupMenuItem = new javax.swing.JMenuItem();
        reportPopupMenuItem = new javax.swing.JMenuItem();
        activeCollaboratorsPopupMenu = new javax.swing.JPopupMenu();
        banCollaboratorPopupMenuItem = new javax.swing.JMenuItem();
        bannedCollaboratorsPopupMenu = new javax.swing.JPopupMenu();
        unbanCollaboratorPopupMenuItem = new javax.swing.JMenuItem();
        tasksPanel = new javax.swing.JPanel();
        tasksScrollPane = new javax.swing.JScrollPane();
        tasksTree = new javax.swing.JTree();
        collaboratorsTabbedPane = new javax.swing.JTabbedPane();
        activeCollaboratorsScrollPane = new javax.swing.JScrollPane();
        activeCollaboratorsList = new javax.swing.JList<>();
        bannedCollaboratorsScrollPane = new javax.swing.JScrollPane();
        bannedCollaboratorsList = new javax.swing.JList<>();
        filtersPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        taskFilterComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        collaboratorFilterComboBox = new javax.swing.JComboBox();
        menuBar = new javax.swing.JMenuBar();
        navigationMenu = new javax.swing.JMenu();
        returnMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        openTaskMenuItem = new javax.swing.JMenuItem();
        resetFiltersMenuItem = new javax.swing.JMenuItem();
        synchronizeMenuItem = new javax.swing.JMenuItem();
        reportMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        banCollaboratorMenuItem = new javax.swing.JMenuItem();
        unbanCollaboratorMenuItem = new javax.swing.JMenuItem();

        openTaskPopupMenuItem.setText("Open Selected Task");
        tasksPopupMenu.add(openTaskPopupMenuItem);

        resetFiltersPopupMenuItem.setText("Reset Task Filters");
        tasksPopupMenu.add(resetFiltersPopupMenuItem);

        synchronizePopupMenuItem.setText("Synchronize Tasks");
        tasksPopupMenu.add(synchronizePopupMenuItem);

        reportPopupMenuItem.setText("Print Report");
        tasksPopupMenu.add(reportPopupMenuItem);

        banCollaboratorPopupMenuItem.setText("Ban Selected Collaborator");
        activeCollaboratorsPopupMenu.add(banCollaboratorPopupMenuItem);

        unbanCollaboratorPopupMenuItem.setText("Un-ban Selected Collaborator");
        bannedCollaboratorsPopupMenu.add(unbanCollaboratorPopupMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tasksPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Tasks"));
        tasksPanel.setComponentPopupMenu(tasksPopupMenu);

        tasksScrollPane.setInheritsPopupMenu(true);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        tasksTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        tasksTree.setCellRenderer(null);
        tasksTree.setInheritsPopupMenu(true);
        tasksTree.setRootVisible(false);
        tasksTree.setShowsRootHandles(false);
        tasksScrollPane.setViewportView(tasksTree);

        javax.swing.GroupLayout tasksPanelLayout = new javax.swing.GroupLayout(tasksPanel);
        tasksPanel.setLayout(tasksPanelLayout);
        tasksPanelLayout.setHorizontalGroup(
            tasksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tasksScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
        );
        tasksPanelLayout.setVerticalGroup(
            tasksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tasksScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
        );

        collaboratorsTabbedPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Collaborators"));

        activeCollaboratorsScrollPane.setComponentPopupMenu(activeCollaboratorsPopupMenu);

        activeCollaboratorsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        activeCollaboratorsList.setInheritsPopupMenu(true);
        activeCollaboratorsScrollPane.setViewportView(activeCollaboratorsList);

        collaboratorsTabbedPane.addTab("Active", activeCollaboratorsScrollPane);

        bannedCollaboratorsScrollPane.setComponentPopupMenu(bannedCollaboratorsPopupMenu);

        bannedCollaboratorsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bannedCollaboratorsList.setInheritsPopupMenu(true);
        bannedCollaboratorsScrollPane.setViewportView(bannedCollaboratorsList);

        collaboratorsTabbedPane.addTab("Banned", bannedCollaboratorsScrollPane);

        filtersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filters"));

        jLabel1.setText("Task:");

        jLabel2.setText("Collaborator:");

        javax.swing.GroupLayout filtersPanelLayout = new javax.swing.GroupLayout(filtersPanel);
        filtersPanel.setLayout(filtersPanelLayout);
        filtersPanelLayout.setHorizontalGroup(
            filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filtersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(collaboratorFilterComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(taskFilterComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        filtersPanelLayout.setVerticalGroup(
            filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filtersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(taskFilterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(collaboratorFilterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        navigationMenu.setText("Navigation");

        returnMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, java.awt.event.InputEvent.ALT_MASK));
        returnMenuItem.setText("Return to Proyect Selection");
        returnMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnMenuItemActionPerformed(evt);
            }
        });
        navigationMenu.add(returnMenuItem);
        navigationMenu.add(jSeparator1);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exitMenuItem.setText("Exit Asana Assistant");
        navigationMenu.add(exitMenuItem);

        menuBar.add(navigationMenu);

        optionsMenu.setText("Options");

        openTaskMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openTaskMenuItem.setText("Open Selected Task");
        openTaskMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openTaskMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(openTaskMenuItem);

        resetFiltersMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        resetFiltersMenuItem.setText("Reset Task Filters");
        optionsMenu.add(resetFiltersMenuItem);

        synchronizeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        synchronizeMenuItem.setText("Synchronize Tasks");
        optionsMenu.add(synchronizeMenuItem);

        reportMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        reportMenuItem.setText("Print Report");
        optionsMenu.add(reportMenuItem);
        optionsMenu.add(jSeparator2);

        banCollaboratorMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        banCollaboratorMenuItem.setText("Ban Selected Collaborator");
        banCollaboratorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                banCollaboratorMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(banCollaboratorMenuItem);

        unbanCollaboratorMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        unbanCollaboratorMenuItem.setText("Un-ban Selected Collaborator");
        optionsMenu.add(unbanCollaboratorMenuItem);

        menuBar.add(optionsMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(filtersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(collaboratorsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tasksPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tasksPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(filtersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collaboratorsTabbedPane))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void returnMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnMenuItemActionPerformed
        parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_returnMenuItemActionPerformed

    private void banCollaboratorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banCollaboratorMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_banCollaboratorMenuItemActionPerformed

    private void openTaskMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openTaskMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_openTaskMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> activeCollaboratorsList;
    private javax.swing.JPopupMenu activeCollaboratorsPopupMenu;
    private javax.swing.JScrollPane activeCollaboratorsScrollPane;
    private javax.swing.JMenuItem banCollaboratorMenuItem;
    private javax.swing.JMenuItem banCollaboratorPopupMenuItem;
    private javax.swing.JList<String> bannedCollaboratorsList;
    private javax.swing.JPopupMenu bannedCollaboratorsPopupMenu;
    private javax.swing.JScrollPane bannedCollaboratorsScrollPane;
    private javax.swing.JComboBox collaboratorFilterComboBox;
    private javax.swing.JTabbedPane collaboratorsTabbedPane;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JPanel filtersPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu navigationMenu;
    private javax.swing.JMenuItem openTaskMenuItem;
    private javax.swing.JMenuItem openTaskPopupMenuItem;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JMenuItem reportMenuItem;
    private javax.swing.JMenuItem reportPopupMenuItem;
    private javax.swing.JMenuItem resetFiltersMenuItem;
    private javax.swing.JMenuItem resetFiltersPopupMenuItem;
    private javax.swing.JMenuItem returnMenuItem;
    private javax.swing.JMenuItem synchronizeMenuItem;
    private javax.swing.JMenuItem synchronizePopupMenuItem;
    private javax.swing.JComboBox taskFilterComboBox;
    private javax.swing.JPanel tasksPanel;
    private javax.swing.JPopupMenu tasksPopupMenu;
    private javax.swing.JScrollPane tasksScrollPane;
    private javax.swing.JTree tasksTree;
    private javax.swing.JMenuItem unbanCollaboratorMenuItem;
    private javax.swing.JMenuItem unbanCollaboratorPopupMenuItem;
    // End of variables declaration//GEN-END:variables
}
