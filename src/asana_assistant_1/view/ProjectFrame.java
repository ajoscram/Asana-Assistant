package asana_assistant_1.view;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.IRouter;
import asana_assistant_1.control.JSONTaskParser;
import asana_assistant_1.control.dtos.DisplayString;
import asana_assistant_1.control.dtos.TaskFilter;
import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import asana_assistant_1.model.Project;
import asana_assistant_1.model.Task;
import asana_assistant_1.model.User;
import asana_assistant_1.parse.ParseException;

class ProjectFrame extends javax.swing.JFrame {
    
    private static final String NONE = "";
    private static final String JSON_DESCRIPTION = "JSON (.json)";

    private final UserFrame parent;
    private final IRouter router;
    private final View source;
    private final Project project;
    private final User user;
    
    private final DefaultListModel activeCollaboratorsListModel;
    private final DefaultListModel bannedCollaboratorsListModel;
    private final DefaultTreeModel tasksTreeModel;
    
    public ProjectFrame(View source, UserFrame parent, Project project, User user, boolean isAdministrator) {
        initComponents();
        this.setLocationRelativeTo(parent);
        this.setIconImage(parent.getIconImage());
        this.setTitle(project.getName());
        this.activeCollaboratorsListModel = new DefaultListModel();
        this.bannedCollaboratorsListModel = new DefaultListModel();
        this.tasksTreeModel = new DefaultTreeModel(new DefaultMutableTreeNode("root"));
        
        this.parent = parent;
        this.source = source;
        this.router = source.getRouter();
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
            this.tasksTree.setShowsRootHandles(true);
            
            //collaborator lists
            this.activeCollaboratorsList.setModel(activeCollaboratorsListModel);
            for(DisplayString collaborator : activeCollaborators)
                activeCollaboratorsListModel.addElement(collaborator);
            this.bannedCollaboratorsList.setModel(bannedCollaboratorsListModel);
            for(DisplayString collaborator : bannedCollaborators)
                bannedCollaboratorsListModel.addElement(collaborator);

            //filters
            this.collaboratorFilterComboBox.addItem(NONE);
            this.collaboratorFilterComboBox.setSelectedIndex(0);
            for(DisplayString collaborator : activeCollaborators)
                this.collaboratorFilterComboBox.addItem(collaborator);
            for(DisplayString collaborator : bannedCollaborators)
                this.collaboratorFilterComboBox.addItem(collaborator);
            this.taskFilterComboBox.addItem(NONE);
            this.taskFilterComboBox.setSelectedIndex(0);
            for(DisplayString task : tasks)
                this.taskFilterComboBox.addItem(task);
            
            //removing functionalities from non-administrators
            if(!isAdministrator){
                this.optionsMenu.remove(synchronizeMenuItem);
                this.tasksPopupMenu.remove(synchronizePopupMenuItem);
                this.activeCollaboratorsPopupMenu.remove(banCollaboratorPopupMenuItem);
                this.bannedCollaboratorsPopupMenu.remove(unbanCollaboratorPopupMenuItem);
                this.optionsMenu.remove(optionsSeparator);
                this.optionsMenu.remove(banCollaboratorMenuItem);
                this.optionsMenu.remove(unbanCollaboratorMenuItem);
                
                //disabling keyboard shortcuts
                this.synchronizeMenuItem.setEnabled(false);
                this.banCollaboratorMenuItem.setEnabled(false);
                this.unbanCollaboratorMenuItem.setEnabled(false);
            }
        } catch(ControlException ex){
            DefaultView.displayError(parent, ex);
            this.dispose();
        }
    }
    
    private DefaultMutableTreeNode getTaskNode(DisplayString task, TaskFilter filter) throws ControlException{
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(task);
        for(DisplayString subtask : router.getSubtaskStrings(task.getId(), filter))
            node.add(getTaskNode(subtask, filter));
        return node;
    }
    
    private void filterTasks(TaskFilter filter){
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
            DefaultView.displayError(this, ex);
        }
    }
    
    private void resetFilters(){
        taskFilterComboBox.setSelectedItem(NONE);
        collaboratorFilterComboBox.setSelectedItem(NONE);
    }
    
    private void refreshFilters(){
        try{
            List<DisplayString> tasks = router.getTaskStrings(project.getId());
            taskFilterComboBox.removeAllItems();
            taskFilterComboBox.addItem(NONE);
            taskFilterComboBox.setSelectedIndex(0);
            for(DisplayString task : tasks)
                taskFilterComboBox.addItem(task);
            
            List<DisplayString> activeCollaborators = router.getActiveUserStrings(project.getId());
            List<DisplayString> bannedCollaborators = router.getBannedUserStrings(project.getId());
            collaboratorFilterComboBox.removeAllItems();
            collaboratorFilterComboBox.addItem(NONE);
            collaboratorFilterComboBox.setSelectedIndex(0);
            for(DisplayString collaborator : activeCollaborators)
                collaboratorFilterComboBox.addItem(collaborator);
            for(DisplayString collaborator : bannedCollaborators)
                collaboratorFilterComboBox.addItem(collaborator);
        } catch(ControlException ex){
            DefaultView.displayError(this, ex);
        }
    }
    
    public void filtersChanged(java.awt.event.ItemEvent evt){
        if(evt.getStateChange() == ItemEvent.SELECTED){
            TaskFilter filter;
            Object task = taskFilterComboBox.getSelectedItem();
            Object collaborator = collaboratorFilterComboBox.getSelectedItem();
            if(task instanceof DisplayString && collaborator instanceof DisplayString)
                filter = new TaskFilter(((DisplayString)task).getId(), ((DisplayString)collaborator).getId());
            else if(task instanceof DisplayString)
                filter = new TaskFilter(((DisplayString)task).getId(), null);
            else if(collaborator instanceof DisplayString)
                filter = new TaskFilter(null, ((DisplayString)collaborator).getId());
            else
                filter = TaskFilter.EMPTY;
            filterTasks(filter);
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
            DefaultView.displayError(this, ex);
        }
    }
    
    private void banSelectedCollaborator(){
        DisplayString collaborator = (DisplayString)activeCollaboratorsList.getSelectedValue();
        if(collaborator == null)
            DefaultView.displayError(this, "You must select an active collaborator to ban.");
        else{
            try {
                router.banUser(project.getId(), collaborator.getId());
                refreshCollaboratorLists();
            } catch (ControlException ex) {
                DefaultView.displayError(this, ex);
            }
        }
    }
    
    private void unbanSelectedCollaborator(){
        DisplayString collaborator = (DisplayString)bannedCollaboratorsList.getSelectedValue();
        if(collaborator == null)
            DefaultView.displayError(this, "You must select a banned collaborator to un-ban.");
        else{
            try {
                router.unbanUser(project.getId(), collaborator.getId());
                refreshCollaboratorLists();
            } catch (ControlException ex) {
                DefaultView.displayError(this, ex);
            }
        }
    }
    
    private void openSelectedTask(){
        TreePath path = tasksTree.getSelectionPath();
        if(path == null)
            DefaultView.displayError(this, "You must select a task to open.");
        else{
            try {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
                DisplayString string = (DisplayString)node.getUserObject();
                Task task = router.getTask(string.getId());
                new TaskFrame(source, this, task, user).setVisible(true);
                this.setVisible(false);
            } catch (ControlException ex) {
                DefaultView.displayError(this, ex);
            }
        }
    }
    
    private void synchronizeTasks(){
        if(DefaultView.displayConfirm(this, "Synchronizing tasks is permanent.\nDo you wish to continue?")){
            JFileChooser chooser  = new JFileChooser();
            chooser.setDialogTitle("Select Tasks File");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter(JSON_DESCRIPTION, "json"));
            chooser.setAcceptAllFileFilterUsed(false);
            if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                try{
                    String file = chooser.getSelectedFile().getAbsolutePath();
                    if(chooser.getFileFilter().getDescription().equals(JSON_DESCRIPTION))
                        router.synchronize(project.getId(), file, new JSONTaskParser());
                    refreshFilters();
                    //refreshing filters side-effect refreshes the task tree
                    refreshCollaboratorLists();
                    DefaultView.displayInfo(this, "Tasks synchronized successfullly.");
                } catch(ControlException ex){
                    DefaultView.displayError(this, ex);
                } catch(ParseException ex){
                    DefaultView.displayError(this, ex);
                }
            }
        }
    }
    
    private void printReport(){
        new ReportDialog(source, this, project).setVisible(true);
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
        activeCollaboratorsList = new javax.swing.JList();
        bannedCollaboratorsScrollPane = new javax.swing.JScrollPane();
        bannedCollaboratorsList = new javax.swing.JList();
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
        optionsSeparator = new javax.swing.JPopupMenu.Separator();
        banCollaboratorMenuItem = new javax.swing.JMenuItem();
        unbanCollaboratorMenuItem = new javax.swing.JMenuItem();

        openTaskPopupMenuItem.setText("Open Selected Task");
        openTaskPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openTaskPopupMenuItemActionPerformed(evt);
            }
        });
        tasksPopupMenu.add(openTaskPopupMenuItem);

        resetFiltersPopupMenuItem.setText("Reset Task Filters");
        resetFiltersPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetFiltersPopupMenuItemActionPerformed(evt);
            }
        });
        tasksPopupMenu.add(resetFiltersPopupMenuItem);

        synchronizePopupMenuItem.setText("Synchronize Tasks");
        synchronizePopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                synchronizePopupMenuItemActionPerformed(evt);
            }
        });
        tasksPopupMenu.add(synchronizePopupMenuItem);

        reportPopupMenuItem.setText("Print Report");
        reportPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportPopupMenuItemActionPerformed(evt);
            }
        });
        tasksPopupMenu.add(reportPopupMenuItem);

        banCollaboratorPopupMenuItem.setText("Ban Selected Collaborator");
        banCollaboratorPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                banCollaboratorPopupMenuItemActionPerformed(evt);
            }
        });
        activeCollaboratorsPopupMenu.add(banCollaboratorPopupMenuItem);

        unbanCollaboratorPopupMenuItem.setText("Un-ban Selected Collaborator");
        unbanCollaboratorPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unbanCollaboratorPopupMenuItemActionPerformed(evt);
            }
        });
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
            .addComponent(tasksScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
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

        taskFilterComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                taskFilterComboBoxItemStateChanged(evt);
            }
        });

        jLabel2.setText("Collaborator:");

        collaboratorFilterComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                collaboratorFilterComboBoxItemStateChanged(evt);
            }
        });

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
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
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
        resetFiltersMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetFiltersMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(resetFiltersMenuItem);

        synchronizeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        synchronizeMenuItem.setText("Synchronize Tasks");
        synchronizeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                synchronizeMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(synchronizeMenuItem);

        reportMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        reportMenuItem.setText("Print Report");
        reportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(reportMenuItem);
        optionsMenu.add(optionsSeparator);

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
        unbanCollaboratorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unbanCollaboratorMenuItemActionPerformed(evt);
            }
        });
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
        banSelectedCollaborator();
    }//GEN-LAST:event_banCollaboratorMenuItemActionPerformed

    private void openTaskMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openTaskMenuItemActionPerformed
        openSelectedTask();
    }//GEN-LAST:event_openTaskMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        source.dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void unbanCollaboratorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unbanCollaboratorMenuItemActionPerformed
        unbanSelectedCollaborator();
    }//GEN-LAST:event_unbanCollaboratorMenuItemActionPerformed

    private void banCollaboratorPopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banCollaboratorPopupMenuItemActionPerformed
        banSelectedCollaborator();
    }//GEN-LAST:event_banCollaboratorPopupMenuItemActionPerformed

    private void unbanCollaboratorPopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unbanCollaboratorPopupMenuItemActionPerformed
        unbanSelectedCollaborator();
    }//GEN-LAST:event_unbanCollaboratorPopupMenuItemActionPerformed

    private void resetFiltersMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetFiltersMenuItemActionPerformed
        resetFilters();
    }//GEN-LAST:event_resetFiltersMenuItemActionPerformed

    private void resetFiltersPopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetFiltersPopupMenuItemActionPerformed
        resetFilters();
    }//GEN-LAST:event_resetFiltersPopupMenuItemActionPerformed

    private void taskFilterComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_taskFilterComboBoxItemStateChanged
        filtersChanged(evt);
    }//GEN-LAST:event_taskFilterComboBoxItemStateChanged

    private void collaboratorFilterComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_collaboratorFilterComboBoxItemStateChanged
        filtersChanged(evt);
    }//GEN-LAST:event_collaboratorFilterComboBoxItemStateChanged

    private void openTaskPopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openTaskPopupMenuItemActionPerformed
        openSelectedTask();
    }//GEN-LAST:event_openTaskPopupMenuItemActionPerformed

    private void synchronizeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_synchronizeMenuItemActionPerformed
        synchronizeTasks();
    }//GEN-LAST:event_synchronizeMenuItemActionPerformed

    private void synchronizePopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_synchronizePopupMenuItemActionPerformed
        synchronizeTasks();
    }//GEN-LAST:event_synchronizePopupMenuItemActionPerformed

    private void reportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportMenuItemActionPerformed
        printReport();
    }//GEN-LAST:event_reportMenuItemActionPerformed

    private void reportPopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportPopupMenuItemActionPerformed
        printReport();
    }//GEN-LAST:event_reportPopupMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList activeCollaboratorsList;
    private javax.swing.JPopupMenu activeCollaboratorsPopupMenu;
    private javax.swing.JScrollPane activeCollaboratorsScrollPane;
    private javax.swing.JMenuItem banCollaboratorMenuItem;
    private javax.swing.JMenuItem banCollaboratorPopupMenuItem;
    private javax.swing.JList bannedCollaboratorsList;
    private javax.swing.JPopupMenu bannedCollaboratorsPopupMenu;
    private javax.swing.JScrollPane bannedCollaboratorsScrollPane;
    private javax.swing.JComboBox collaboratorFilterComboBox;
    private javax.swing.JTabbedPane collaboratorsTabbedPane;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JPanel filtersPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu navigationMenu;
    private javax.swing.JMenuItem openTaskMenuItem;
    private javax.swing.JMenuItem openTaskPopupMenuItem;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JPopupMenu.Separator optionsSeparator;
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
