package report;

public interface IReportBuilder {
    Report build(Object object) throws ReportException;
}
