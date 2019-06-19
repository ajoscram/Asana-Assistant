package alejandro;


import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.IRouter;
import asana_assistant_1.control.ProjectReportBuilder;
import asana_assistant_1.control.dtos.TaskDTO;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import asana_assistant_1.model.Project;
import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import asana_assistant_1.control.JSONTaskParser;
import asana_assistant_1.control.Router;
import asana_assistant_1.control.daos.connection.Connection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import asana_assistant_1.model.Task;
import asana_assistant_1.report.Report;
import asana_assistant_1.report.ReportException;
import asana_assistant_1.report.Section;
import asana_assistant_1.report.printers.PDFReportPrinter;
import asana_assistant_1.report.sections.ListSection;
import asana_assistant_1.report.sections.TextSection;
import asana_assistant_1.view.DefaultView;

public class AlejandroMain {

    public static void main(String[] args) throws ReportException, ParseException, IOException, asana_assistant_1.parse.ParseException {
        String instant = Instant.now().toString().replace(':', '.');
        System.out.println(instant);
        
        
        /*REPORTS TEST
        Report report = new Report();
        TextSection title = new TextSection("title", "Lorem Ipsum");
        TextSection paragraph = new TextSection("paragraph", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        ListSectOion list = new ListSection("list");
        ListSection sublist = new ListSection("sublist");
        for(int i = 0; i <= 5; i++)
            list.addSection(new TextSection("item" + i, "Item #" + i));
        for(int i = 0; i <= 5; i++)
            sublist.addSection(new TextSection("item 5." + i, "Item #5." + i));
        list.addSection(sublist);
        for(int i = 6; i <= 10; i++)
            list.addSection(new TextSection("item" + i, "Item #" + i));
        
        report.addSection(title);
        report.addSection(paragraph);
        report.addSection(list);
        
        new PDFReportPrinter().print(report, "C:\\Users\\Alejandro\\Desktop\\report.pdf");*/
        
        /*PROJECT REPORT BUILDER TEST
        Project project = new Project(0, "Limpieza de Zonas Exteriores", LocalDate.of(2019, Month.MAY, 26));
        DummyUserDAO userDAO = new DummyUserDAO();
        DummyTaskDAO taskDAO = new DummyTaskDAO();
        DummyDevelopmentDAO developmentDAO = new DummyDevelopmentDAO();
        DummyEvidenceDAO evidenceDAO = new DummyEvidenceDAO();
        ProjectReportBuilder builder = new ProjectReportBuilder(userDAO, taskDAO, developmentDAO, evidenceDAO);
        Report report = builder.build(project);
        new PDFReportPrinter().print(report, "C:\\Users\\Alejandro\\Desktop\\report.pdf");*/
        
        /*TASK JSON PARSER TEST
        JSONTaskParser parser = new JSONTaskParser();
        List<TaskDTO> tasks = parser.parse("tasks.json");
        for(TaskDTO task : tasks)
            printTask(task, "");*/
       
        
        
        /*UI TEST
        try {
           UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) { 
            DefaultView.displayError(null, "You're not using Windows, so the interface might look a little clunky :(");
        }
        new DefaultView(new DummyRouter()).display();*/
    }
    
    private static void printTask(TaskDTO task, String indent){
        System.out.println(indent + task);
        for(TaskDTO subtask : task.getSubtasks())
            printTask(subtask, indent + "\t");
    }
}
