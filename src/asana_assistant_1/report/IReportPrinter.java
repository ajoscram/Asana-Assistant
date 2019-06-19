package asana_assistant_1.report;

public interface IReportPrinter {
    void print(Report report, String path) throws ReportException;
}
