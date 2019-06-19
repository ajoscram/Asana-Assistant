package asana_assistant_1.view;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.IRouter;
import asana_assistant_1.control.dtos.DevelopmentFilter;
import asana_assistant_1.control.dtos.DisplayString;
import asana_assistant_1.control.dtos.TaskFilter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import asana_assistant_1.model.Project;
import asana_assistant_1.report.ReportException;
import asana_assistant_1.report.printers.PDFReportPrinter;

class ReportDialog extends javax.swing.JDialog {

    private static final String NONE = "";
    private static final String PDF_DESCRIPTION = "PDF (.pdf)";
    
    private IRouter router;
    private Project project;
    
    public ReportDialog(View source, ProjectFrame parent, Project project) {
        super(parent, true);
        initComponents();
        this.setLocationRelativeTo(parent);
        this.setIconImage(parent.getIconImage());
        
        this.router = source.getRouter();
        this.project = project;
        
        try{
            taskComboBox.addItem(NONE);
            taskComboBox.setSelectedIndex(0);
            List<DisplayString> tasks = router.getTaskStrings(project.getId());
            for(DisplayString task : tasks)
                taskComboBox.addItem(task);
            
            collaboratorComboBox.addItem(NONE);
            collaboratorComboBox.setSelectedIndex(0);
            List<DisplayString> bannedUsers = router.getBannedUserStrings(project.getId());
            for(DisplayString user : bannedUsers)
                collaboratorComboBox.addItem(user);
            List<DisplayString> activeUsers = router.getActiveUserStrings(project.getId());
            for(DisplayString user : activeUsers)
                collaboratorComboBox.addItem(user);
            
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(PDF_DESCRIPTION, "pdf"));
        } catch(ControlException ex) {
            DefaultView.displayError(parent, ex);
            this.dispose();
        }
    }

    private void printReport(java.awt.event.ActionEvent evt){
        
        if(evt.getActionCommand().equals(JFileChooser.CANCEL_SELECTION))
            this.dispose();
        else if(!fileChooser.getSelectedFile().isDirectory())
            DefaultView.displayError(this, "You must specify a valid directory in the \"Folder name\" field.");
        else{
            LocalDate start = null;
            LocalDate end = null;
            Long userId = null;
            Long taskId = null;
            
            Date start_ = startDateChooser.getDate();
            if(start_ != null)
                start = start_.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Date end_ = startDateChooser.getDate();
            if(end_ != null)
                end = end_.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Object task = taskComboBox.getSelectedItem();
            if(task instanceof DisplayString)
                taskId = ((DisplayString)task).getId();

            Object user = collaboratorComboBox.getSelectedItem();
            if(user instanceof DisplayString)
                userId = ((DisplayString)user).getId();

            String path = fileChooser.getSelectedFile().getAbsolutePath();

            TaskFilter taskFilter = new TaskFilter(taskId, userId);
            DevelopmentFilter developmentFilter = new DevelopmentFilter(start, end);

            try{
                if(fileChooser.getFileFilter().getDescription().equals(PDF_DESCRIPTION)){
                    String filepath = path + "\\" + LocalDate.now() + " Report.pdf";
                    router.printReport(project.getId(), filepath, new PDFReportPrinter(), taskFilter, developmentFilter);
                }
                DefaultView.displayInfo(this, "Report printed successfully.");
                this.dispose();
            } catch(ControlException ex){
                DefaultView.displayError(this, ex);
            } catch(ReportException ex){
                DefaultView.displayError(this, ex);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filtersPanel = new javax.swing.JPanel();
        startDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        endDateChooser = new com.toedter.calendar.JDateChooser();
        taskComboBox = new javax.swing.JComboBox();
        collaboratorComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fileChooser = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Print Report");

        filtersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filters"));

        jLabel1.setText("Start:");

        jLabel2.setText("Task:");

        jLabel3.setText("Collaborator:");

        jLabel4.setText("End:");

        javax.swing.GroupLayout filtersPanelLayout = new javax.swing.GroupLayout(filtersPanel);
        filtersPanel.setLayout(filtersPanelLayout);
        filtersPanelLayout.setHorizontalGroup(
            filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filtersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(collaboratorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(taskComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(startDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(endDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        filtersPanelLayout.setVerticalGroup(
            filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filtersPanelLayout.createSequentialGroup()
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(startDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(endDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(taskComboBox)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(filtersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(collaboratorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fileChooser.setApproveButtonText("Print");
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(filtersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(fileChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(filtersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        printReport(evt);
    }//GEN-LAST:event_fileChooserActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox collaboratorComboBox;
    private com.toedter.calendar.JDateChooser endDateChooser;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JPanel filtersPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private com.toedter.calendar.JDateChooser startDateChooser;
    private javax.swing.JComboBox taskComboBox;
    // End of variables declaration//GEN-END:variables

}
