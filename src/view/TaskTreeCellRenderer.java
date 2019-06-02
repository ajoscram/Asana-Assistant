package view;

import control.ControlException;
import control.IRouter;
import control.dtos.DisplayString;
import java.awt.Component;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import model.Task;

public class TaskTreeCellRenderer extends DefaultTreeCellRenderer{
    
    private IRouter router;
    
    private Font sectionFont;
    private Font singleFont;
    private Font subtaskFont;
    private Font unknownFont;

    public TaskTreeCellRenderer(IRouter router) {
        super();
    
        this.router = router;
        
        sectionFont = new Font(Font.DIALOG, Font.BOLD, 12);
        singleFont = new Font(Font.DIALOG, Font.PLAIN, 12);
        subtaskFont = new Font(Font.DIALOG, Font.ITALIC, 12);
        unknownFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    }
    
    @Override
    public Component getTreeCellRendererComponent(JTree jtree,
                                                  Object node,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        Component component = super.getTreeCellRendererComponent(jtree, node, selected, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node_ = (DefaultMutableTreeNode)node;
        Object object = node_.getUserObject();
        try {
            if(object instanceof DisplayString){
                DisplayString string = (DisplayString)object;
                Task task = router.getTask(string.getId());
                if(task.getType() == Task.Type.SECTION)
                    component.setFont(sectionFont);
                else if(task.getType() == Task.Type.SINGLE)
                    component.setFont(singleFont);
                else if(task.getType() == Task.Type.SUBTASK)
                    component.setFont(subtaskFont);
                else
                    component.setFont(unknownFont);
            }
        } catch(ControlException ex){ }
        return component;
    }
    
    
    @Override
    public void setLeafIcon(Icon icon) {
        super.setLeafIcon(null);
    }

    @Override
    public void setClosedIcon(Icon icon) {
        super.setClosedIcon(null);
    }

    @Override
    public void setOpenIcon(Icon icon) {
        super.setOpenIcon(null);
    }
}
