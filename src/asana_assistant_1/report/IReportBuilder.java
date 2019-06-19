package asana_assistant_1.report;

public interface IReportBuilder {
    Report build(Object object) throws ReportException;
}
