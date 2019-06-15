package view;

import control.ControlException;
import control.IRouter;
import control.dtos.DevelopmentFilter;
import control.dtos.DisplayString;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import model.Task;
import model.User;

class TaskFrame extends javax.swing.JFrame {

    private static final long DATE_MIN = -62135734039898l;
    
    private final ProjectFrame parent;
    private final IRouter router;
    private final View source;
    private final Task task;
    private final User user;
    
    private final DefaultListModel subtasksListModel;
    private final DefaultListModel developmentsListModel;
    private final DefaultListModel evidenceListModel;
    
    public TaskFrame(View source, ProjectFrame parent, Task task, User user) {
        initComponents();
        this.setLocationRelativeTo(parent);
        this.setIconImage(parent.getIconImage());
        this.setTitle(task.getName());
        this.subtasksListModel = new DefaultListModel();
        this.developmentsListModel = new DefaultListModel();
        this.evidenceListModel = new DefaultListModel();
        
        this.parent = parent;
        this.source = source;
        this.router = source.getRouter();
        this.task = task;
        this.user = user;
        
        try {
            LocalDate created = task.getCreated();
            LocalDate completed = task.getCompleted();
            LocalDate due = task.getDue();
            DisplayString asignee = router.getAsigneeString(task.getId());
            List<DisplayString> subtasks = router.getSubtaskStrings(task.getId());
            List<DisplayString> developments = router.getDevelopmentStrings(task.getId());
            
            //task details
            if(created != null)
                this.createdDateLabel.setText(created.format(DateTimeFormatter.ISO_DATE));
            if(completed != null)
                this.completedDateLabel.setText(completed.format(DateTimeFormatter.ISO_DATE));
            if(due != null)
                this.dueDateLabel.setText(due.format(DateTimeFormatter.ISO_DATE));
            if(asignee != null)
                asigneeLabel.setText(asignee.toString());
            this.subtasksList.setModel(subtasksListModel);
            for(DisplayString string : subtasks)
                subtasksListModel.addElement(string);
            
            //developments
            this.calendar.setWeekOfYearVisible(false);
            this.developmentsList.setModel(developmentsListModel);
            for(DisplayString string : developments)
                developmentsListModel.addElement(string);
            
            //evidence
            this.evidenceList.setModel(evidenceListModel);
            
            //filters
            this.startDateChooser.getDateEditor().setEnabled(false);
            this.endDateChooser.getDateEditor().setEnabled(false);
            
            if(asignee == null || asignee.getId() != user.getId()){
                this.optionsMenu.remove(this.addDevelopmentMenuItem);
                this.developmentsPopupMenu.remove(this.addDevelopmentPopupMenuItem);
                
                //disabling keyboard shortcuts
                this.addDevelopmentMenuItem.setEnabled(false);
            }
            
        } catch(ControlException ex) {
            DefaultView.displayError(parent, ex);
        }
    }
    
    private void openAddDevelopment(){
        new AddDevelopmentDialog(source, this, task, calendar.getDate()).setVisible(true);
    }
    
    public void resetDevelopmentFilters(){
        startDateChooser.setDate(null);
        endDateChooser.setDate(null);
    }
    
    private void openSubtask(){
        DisplayString subtask = (DisplayString)subtasksList.getSelectedValue();
        if(subtask == null)
            DefaultView.displayError(this, "You must select a subtask to open.");
        else{
            try {
                Task subtask_ = router.getTask(subtask.getId());
                new TaskFrame(source, parent, subtask_, user).setVisible(true);
                this.dispose();
            } catch (ControlException ex) {
                DefaultView.displayError(this, ex);
            }
        }
    }
    
    private void downloadEvidence(){
        DisplayString evidence = (DisplayString)evidenceList.getSelectedValue();
        if(evidence == null)
            DefaultView.displayError(this, "You must select an evidence to download from the evidence list.");
        else{
            JFileChooser chooser  = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                try{
                    String directory = chooser.getSelectedFile().getAbsolutePath();
                    router.downloadEvidence(evidence.getId(), directory);
                } catch(ControlException ex){
                    DefaultView.displayError(this, ex);
                }
            }
        }
    }

    private void filterDevelopments(java.beans.PropertyChangeEvent evt){
        if(evt.getPropertyName().equals("date")){
            Date start = startDateChooser.getDate();
            Date end = endDateChooser.getDate();
            Date current = calendar.getDate();
            calendar.setSelectableDateRange(new Date(DATE_MIN), new Date(Long.MAX_VALUE));
            if(start == null && end == null)
                calendar.setDate(current);
            else if(start == null && end != null){
                calendar.setMaxSelectableDate(end);
                if(current.after(end))
                    calendar.setDate(end);
                else
                    calendar.setDate(current);
            }
            else if(start != null && end == null){
                calendar.setMinSelectableDate(start);
                if(current.before(start))
                    calendar.setDate(start);
                else
                    calendar.setDate(start);
            }
            else if(start.before(end) || start.compareTo(end) == 0){
                calendar.setSelectableDateRange(start, end);
                if(current.before(start))
                    calendar.setDate(start);
                else if(current.after(end))
                    calendar.setDate(end);
                else
                    calendar.setDate(start);
            }
            else{
                startDateChooser.setDate(null);
                endDateChooser.setDate(null);
                DefaultView.displayError(this, "The dates picked must be in chronological order.");
            }
            
            try{
                LocalDate startLocal = null;
                LocalDate endLocal = null;
                if(start != null)
                    startLocal = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if(end != null)
                    endLocal = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                DevelopmentFilter filter = new DevelopmentFilter(startLocal, endLocal);
                developmentsListModel.removeAllElements();
                for(DisplayString development : router.getDevelopmentStrings(task.getId(), filter))
                    developmentsListModel.addElement(development);
            } catch(ControlException ex){
                DefaultView.displayError(this, ex);
            }
        }
    }
    
    public void displayEvidence(javax.swing.event.ListSelectionEvent evt){
        if(!evt.getValueIsAdjusting()){
            evidenceListModel.removeAllElements();
            DisplayString development = (DisplayString)developmentsList.getSelectedValue();
            if(development != null){
                try {
                    for(DisplayString evidence : router.getEvidenceStrings(development.getId()))
                        evidenceListModel.addElement(evidence);
                } catch (ControlException ex) {
                    DefaultView.displayError(this, ex);
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        developmentsPopupMenu = new javax.swing.JPopupMenu();
        addDevelopmentPopupMenuItem = new javax.swing.JMenuItem();
        resetDevelopmentFiltersPopupMenuItem = new javax.swing.JMenuItem();
        subtasksPopupMenu = new javax.swing.JPopupMenu();
        openSubtaskPopupMenuItem = new javax.swing.JMenuItem();
        evidencePopupMenu = new javax.swing.JPopupMenu();
        downloadEvidencePopupMenuItem = new javax.swing.JMenuItem();
        calendarPanel = new javax.swing.JPanel();
        calendar = new com.toedter.calendar.JCalendar();
        detailsPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        createdDateLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dueDateLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        completedDateLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        asigneeLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        subtasksScrollPane = new javax.swing.JScrollPane();
        subtasksList = new javax.swing.JList();
        filtersPanel = new javax.swing.JPanel();
        startDateChooser = new com.toedter.calendar.JDateChooser();
        endDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        evidencePanel = new javax.swing.JPanel();
        evidenceScrollPane = new javax.swing.JScrollPane();
        evidenceList = new javax.swing.JList();
        developmentsPanel = new javax.swing.JPanel();
        developmentsScrollPane = new javax.swing.JScrollPane();
        developmentsList = new javax.swing.JList();
        menuBar = new javax.swing.JMenuBar();
        navigationMenu = new javax.swing.JMenu();
        returnMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        addDevelopmentMenuItem = new javax.swing.JMenuItem();
        resetDevelopmentFiltersMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        openSubtaskMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        downloadEvidenceMenuItem = new javax.swing.JMenuItem();

        addDevelopmentPopupMenuItem.setText("Add New Development on Selected Date");
        addDevelopmentPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDevelopmentPopupMenuItemActionPerformed(evt);
            }
        });
        developmentsPopupMenu.add(addDevelopmentPopupMenuItem);

        resetDevelopmentFiltersPopupMenuItem.setText("Reset Development Filters");
        resetDevelopmentFiltersPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetDevelopmentFiltersPopupMenuItemActionPerformed(evt);
            }
        });
        developmentsPopupMenu.add(resetDevelopmentFiltersPopupMenuItem);

        openSubtaskPopupMenuItem.setText("Open Selected Subtask");
        openSubtaskPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openSubtaskPopupMenuItemActionPerformed(evt);
            }
        });
        subtasksPopupMenu.add(openSubtaskPopupMenuItem);

        downloadEvidencePopupMenuItem.setText("Download Selected Evidence");
        downloadEvidencePopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadEvidencePopupMenuItemActionPerformed(evt);
            }
        });
        evidencePopupMenu.add(downloadEvidencePopupMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(970, 700));

        calendarPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Calendar"));

        calendar.setInheritsPopupMenu(true);

        javax.swing.GroupLayout calendarPanelLayout = new javax.swing.GroupLayout(calendarPanel);
        calendarPanel.setLayout(calendarPanelLayout);
        calendarPanelLayout.setHorizontalGroup(
            calendarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(calendarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(calendar, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE))
        );
        calendarPanelLayout.setVerticalGroup(
            calendarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(calendarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(calendar, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
        );

        detailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Task Details"));
        detailsPanel.setPreferredSize(new java.awt.Dimension(326, 231));

        jLabel3.setText("Created:");

        createdDateLabel.setText("?");
        createdDateLabel.setToolTipText("");

        jLabel4.setText("Due on:");

        dueDateLabel.setText("?");

        jLabel5.setText("Completed on:");

        completedDateLabel.setText("?");

        jLabel6.setText("Assigned to:");

        asigneeLabel.setText("?");

        jLabel8.setText("Subtasks:");

        subtasksList.setComponentPopupMenu(subtasksPopupMenu);
        subtasksScrollPane.setViewportView(subtasksList);

        javax.swing.GroupLayout detailsPanelLayout = new javax.swing.GroupLayout(detailsPanel);
        detailsPanel.setLayout(detailsPanelLayout);
        detailsPanelLayout.setHorizontalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailsPanelLayout.createSequentialGroup()
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dueDateLabel)
                            .addComponent(createdDateLabel)
                            .addComponent(completedDateLabel)))
                    .addGroup(detailsPanelLayout.createSequentialGroup()
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addComponent(asigneeLabel)))
                .addContainerGap(215, Short.MAX_VALUE))
            .addComponent(subtasksScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        detailsPanelLayout.setVerticalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(createdDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(dueDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(completedDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(asigneeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subtasksScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
        );

        filtersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filters"));

        startDateChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                startDateChooserPropertyChange(evt);
            }
        });

        endDateChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                endDateChooserPropertyChange(evt);
            }
        });

        jLabel1.setText("Start:");

        jLabel2.setText("End:");

        javax.swing.GroupLayout filtersPanelLayout = new javax.swing.GroupLayout(filtersPanel);
        filtersPanel.setLayout(filtersPanelLayout);
        filtersPanelLayout.setHorizontalGroup(
            filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, filtersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(startDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(endDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        filtersPanelLayout.setVerticalGroup(
            filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, filtersPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(startDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        evidencePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Evidence"));
        evidencePanel.setComponentPopupMenu(evidencePopupMenu);

        evidenceScrollPane.setInheritsPopupMenu(true);

        evidenceList.setInheritsPopupMenu(true);
        evidenceScrollPane.setViewportView(evidenceList);

        javax.swing.GroupLayout evidencePanelLayout = new javax.swing.GroupLayout(evidencePanel);
        evidencePanel.setLayout(evidencePanelLayout);
        evidencePanelLayout.setHorizontalGroup(
            evidencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
            .addGroup(evidencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(evidenceScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
        );
        evidencePanelLayout.setVerticalGroup(
            evidencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 203, Short.MAX_VALUE)
            .addGroup(evidencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(evidenceScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
        );

        developmentsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Developments"));
        developmentsPanel.setComponentPopupMenu(developmentsPopupMenu);

        developmentsScrollPane.setInheritsPopupMenu(true);

        developmentsList.setInheritsPopupMenu(true);
        developmentsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                developmentsListValueChanged(evt);
            }
        });
        developmentsScrollPane.setViewportView(developmentsList);

        javax.swing.GroupLayout developmentsPanelLayout = new javax.swing.GroupLayout(developmentsPanel);
        developmentsPanel.setLayout(developmentsPanelLayout);
        developmentsPanelLayout.setHorizontalGroup(
            developmentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 628, Short.MAX_VALUE)
            .addGroup(developmentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(developmentsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE))
        );
        developmentsPanelLayout.setVerticalGroup(
            developmentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(developmentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(developmentsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
        );

        navigationMenu.setText("Navigation");

        returnMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, java.awt.event.InputEvent.ALT_MASK));
        returnMenuItem.setText("Return to Task Selection");
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

        addDevelopmentMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        addDevelopmentMenuItem.setText("Add New Development on Selected Date");
        addDevelopmentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDevelopmentMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(addDevelopmentMenuItem);

        resetDevelopmentFiltersMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        resetDevelopmentFiltersMenuItem.setText("Reset Development Filters");
        resetDevelopmentFiltersMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetDevelopmentFiltersMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(resetDevelopmentFiltersMenuItem);
        optionsMenu.add(jSeparator2);

        openSubtaskMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openSubtaskMenuItem.setText("Open Selected Subtask");
        openSubtaskMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openSubtaskMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(openSubtaskMenuItem);
        optionsMenu.add(jSeparator3);

        downloadEvidenceMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        downloadEvidenceMenuItem.setText("Download Selected Evidence");
        downloadEvidenceMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadEvidenceMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(downloadEvidenceMenuItem);

        menuBar.add(optionsMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(developmentsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(calendarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(evidencePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(detailsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filtersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calendarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(detailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filtersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(evidencePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(developmentsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void returnMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnMenuItemActionPerformed
        parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_returnMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        source.dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void addDevelopmentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDevelopmentMenuItemActionPerformed
        openAddDevelopment();
    }//GEN-LAST:event_addDevelopmentMenuItemActionPerformed

    private void addDevelopmentPopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDevelopmentPopupMenuItemActionPerformed
        openAddDevelopment();
    }//GEN-LAST:event_addDevelopmentPopupMenuItemActionPerformed

    private void resetDevelopmentFiltersMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetDevelopmentFiltersMenuItemActionPerformed
        resetDevelopmentFilters();
    }//GEN-LAST:event_resetDevelopmentFiltersMenuItemActionPerformed

    private void resetDevelopmentFiltersPopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetDevelopmentFiltersPopupMenuItemActionPerformed
        resetDevelopmentFilters();
    }//GEN-LAST:event_resetDevelopmentFiltersPopupMenuItemActionPerformed

    private void openSubtaskMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openSubtaskMenuItemActionPerformed
        openSubtask();
    }//GEN-LAST:event_openSubtaskMenuItemActionPerformed

    private void openSubtaskPopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openSubtaskPopupMenuItemActionPerformed
        openSubtask();
    }//GEN-LAST:event_openSubtaskPopupMenuItemActionPerformed

    private void downloadEvidenceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadEvidenceMenuItemActionPerformed
        downloadEvidence();
    }//GEN-LAST:event_downloadEvidenceMenuItemActionPerformed

    private void downloadEvidencePopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadEvidencePopupMenuItemActionPerformed
        downloadEvidence();
    }//GEN-LAST:event_downloadEvidencePopupMenuItemActionPerformed

    private void startDateChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_startDateChooserPropertyChange
        filterDevelopments(evt);
    }//GEN-LAST:event_startDateChooserPropertyChange

    private void endDateChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_endDateChooserPropertyChange
        filterDevelopments(evt);
    }//GEN-LAST:event_endDateChooserPropertyChange

    private void developmentsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_developmentsListValueChanged
        displayEvidence(evt);
    }//GEN-LAST:event_developmentsListValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addDevelopmentMenuItem;
    private javax.swing.JMenuItem addDevelopmentPopupMenuItem;
    private javax.swing.JLabel asigneeLabel;
    private com.toedter.calendar.JCalendar calendar;
    private javax.swing.JPanel calendarPanel;
    private javax.swing.JLabel completedDateLabel;
    private javax.swing.JLabel createdDateLabel;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JList developmentsList;
    private javax.swing.JPanel developmentsPanel;
    private javax.swing.JPopupMenu developmentsPopupMenu;
    private javax.swing.JScrollPane developmentsScrollPane;
    private javax.swing.JMenuItem downloadEvidenceMenuItem;
    private javax.swing.JMenuItem downloadEvidencePopupMenuItem;
    private javax.swing.JLabel dueDateLabel;
    private com.toedter.calendar.JDateChooser endDateChooser;
    private javax.swing.JList evidenceList;
    private javax.swing.JPanel evidencePanel;
    private javax.swing.JPopupMenu evidencePopupMenu;
    private javax.swing.JScrollPane evidenceScrollPane;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JPanel filtersPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu navigationMenu;
    private javax.swing.JMenuItem openSubtaskMenuItem;
    private javax.swing.JMenuItem openSubtaskPopupMenuItem;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JMenuItem resetDevelopmentFiltersMenuItem;
    private javax.swing.JMenuItem resetDevelopmentFiltersPopupMenuItem;
    private javax.swing.JMenuItem returnMenuItem;
    private com.toedter.calendar.JDateChooser startDateChooser;
    private javax.swing.JList subtasksList;
    private javax.swing.JPopupMenu subtasksPopupMenu;
    private javax.swing.JScrollPane subtasksScrollPane;
    // End of variables declaration//GEN-END:variables

}
