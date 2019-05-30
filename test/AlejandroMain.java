
import control.ProjectReportBuilder;
import control.dtos.TaskDTO;
import daos.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import model.Project;
import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import control.JSONTaskParser;
import control.daos.connection.Connection;
import java.util.List;
import model.Task;
import report.Report;
import report.ReportException;
import report.Section;
import report.printers.PDFReportPrinter;
import report.sections.ListSection;
import report.sections.TextSection;

public class AlejandroMain {

    public static void main(String[] args) throws ReportException, ParseException, IOException, parse.ParseException {
        /*TEST REPORTS
        Report report = new Report();
        TextSection title = new TextSection("title", "Lorem Ipsum");
        TextSection paragraph = new TextSection("paragraph", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        ListSection list = new ListSection("list");
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
        
        /*TEST PROJECT REPORT BUILDER
        Project project = new Project(0, "Limpieza de Zonas Exteriores", LocalDate.of(2019, Month.MAY, 26));
        DummyUserDAO userDAO = new DummyUserDAO();
        DummyTaskDAO taskDAO = new DummyTaskDAO();
        DummyDevelopmentDAO developmentDAO = new DummyDevelopmentDAO();
        DummyEvidenceDAO evidenceDAO = new DummyEvidenceDAO();
        ProjectReportBuilder builder = new ProjectReportBuilder(userDAO, taskDAO, developmentDAO, evidenceDAO);
        Report report = builder.build(project);
        new PDFReportPrinter().print(report, "C:\\Users\\Alejandro\\Desktop\\report.pdf");*/
        
        /*TEST TASK JSON PARSER
        JSONTaskParser parser = new JSONTaskParser();
        List<TaskDTO> tasks = parser.parse("tasks.json");
        for(TaskDTO task : tasks)
            printTask(task, "");*/
        
        Connection.connect();
        
    }
    
    private static void printTask(TaskDTO task, String indent){
        System.out.println(indent + task);
        for(TaskDTO subtask : task.getSubtasks())
            printTask(subtask, indent + "\t");
    }
}
