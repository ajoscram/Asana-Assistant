package asana_assistant_1.view;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.IRouter;
import asana_assistant_1.control.dtos.DevelopmentDTO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.text.NumberFormatter;
import asana_assistant_1.model.Task;

class AddDevelopmentDialog extends javax.swing.JDialog {
    
    private TaskFrame parent;
    private IRouter router;
    private Task task;
    
    private DefaultListModel evidenceListModel;
    
    public AddDevelopmentDialog(View source, TaskFrame parent, Task task, Date date) {
        super(parent, true);
        initComponents();
        this.setLocationRelativeTo(parent);
        this.setIconImage(parent.getIconImage());
        this.setTitle("New Development");
        
        this.parent = parent;
        this.router = source.getRouter();
        this.task = task;
        
        this.evidenceListModel = new DefaultListModel();
        this.evidenceList.setModel(evidenceListModel);
        this.dateChooser.setDate(date);
        this.dateChooser.getDateEditor().setEnabled(false);
        JFormattedTextField hoursTextField = ((JSpinner.NumberEditor)this.hoursSpinner.getEditor()).getTextField();
        ((NumberFormatter)hoursTextField.getFormatter()).setAllowsInvalid(false);
    }

    private void addEvidence(){
        JFileChooser chooser  = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            String filename = chooser.getSelectedFile().getAbsolutePath();
            evidenceListModel.addElement(filename);
        }
    }
    
    private void removeEvidence(){
        String string = evidenceList.getSelectedValue();
        if(string == null)
            DefaultView.displayError(this, "You must select an evidence entry to remove.");
        else
            evidenceListModel.removeElement(string);
    }
    
    private void addDevelopment(){
        try{
            if(DefaultView.displayConfirm(this, "Adding a development is non-reversible.\nDo you want to save your changes?")){
                LocalDate date = dateChooser.getDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                int hours = (Integer)hoursSpinner.getValue();
                String description = descriptionTextArea.getText();
                ArrayList<String> evidence = new ArrayList();
                for(Object evidence_ : evidenceListModel.toArray())
                    evidence.add((String)evidence_);
                DevelopmentDTO development = new DevelopmentDTO(date, hours, description, evidence);
                router.addDevelopment(task.getId(), development);
                DefaultView.displayInfo(this, "Development added successfully.");
                parent.resetDevelopmentFilters();
                this.dispose();
            }
        } catch(ControlException ex) {
            DefaultView.displayError(this, ex);
        }   
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        evidenceListPopupMenu = new javax.swing.JPopupMenu();
        addEvidencePopupMenuItem = new javax.swing.JMenuItem();
        removeEvidencePopupMenuItem = new javax.swing.JMenuItem();
        dateChooser = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        hoursSpinner = new javax.swing.JSpinner();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        evidenceScrollPane = new javax.swing.JScrollPane();
        evidenceList = new javax.swing.JList<>();
        addEvidenceButton = new javax.swing.JButton();
        removeEvidenceButton = new javax.swing.JButton();
        addDevelopmentButton = new javax.swing.JButton();

        addEvidencePopupMenuItem.setText("Add New Evidence");
        addEvidencePopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEvidencePopupMenuItemActionPerformed(evt);
            }
        });
        evidenceListPopupMenu.add(addEvidencePopupMenuItem);

        removeEvidencePopupMenuItem.setText("Remove Selected Evidence");
        removeEvidencePopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeEvidencePopupMenuItemActionPerformed(evt);
            }
        });
        evidenceListPopupMenu.add(removeEvidencePopupMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Date:");

        jLabel2.setText("Hours of Work:");

        jLabel3.setText("Description:");

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionScrollPane.setViewportView(descriptionTextArea);

        jLabel4.setText("Evidence:");

        evidenceScrollPane.setComponentPopupMenu(evidenceListPopupMenu);

        evidenceList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        evidenceList.setInheritsPopupMenu(true);
        evidenceScrollPane.setViewportView(evidenceList);

        addEvidenceButton.setText("Add");
        addEvidenceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEvidenceButtonActionPerformed(evt);
            }
        });

        removeEvidenceButton.setText("Remove");
        removeEvidenceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeEvidenceButtonActionPerformed(evt);
            }
        });

        addDevelopmentButton.setText("Add Development");
        addDevelopmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDevelopmentButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(hoursSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(dateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(evidenceScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                            .addComponent(descriptionScrollPane)
                            .addComponent(addDevelopmentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addEvidenceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeEvidenceButton)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hoursSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeEvidenceButton)
                    .addComponent(addEvidenceButton)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(evidenceScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addDevelopmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addEvidenceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEvidenceButtonActionPerformed
        addEvidence();
    }//GEN-LAST:event_addEvidenceButtonActionPerformed

    private void addEvidencePopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEvidencePopupMenuItemActionPerformed
        addEvidence();
    }//GEN-LAST:event_addEvidencePopupMenuItemActionPerformed

    private void removeEvidenceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeEvidenceButtonActionPerformed
        removeEvidence();
    }//GEN-LAST:event_removeEvidenceButtonActionPerformed

    private void removeEvidencePopupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeEvidencePopupMenuItemActionPerformed
        removeEvidence();
    }//GEN-LAST:event_removeEvidencePopupMenuItemActionPerformed

    private void addDevelopmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDevelopmentButtonActionPerformed
        addDevelopment();
    }//GEN-LAST:event_addDevelopmentButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDevelopmentButton;
    private javax.swing.JButton addEvidenceButton;
    private javax.swing.JMenuItem addEvidencePopupMenuItem;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JList<String> evidenceList;
    private javax.swing.JPopupMenu evidenceListPopupMenu;
    private javax.swing.JScrollPane evidenceScrollPane;
    private javax.swing.JSpinner hoursSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton removeEvidenceButton;
    private javax.swing.JMenuItem removeEvidencePopupMenuItem;
    // End of variables declaration//GEN-END:variables

}
