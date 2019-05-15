package report.printers;

import report.IReportPrinter;
import report.Report;

public class PDFReportPrinter implements IReportPrinter {

    public PDFReportPrinter(){}
    
    @Override
    public void print(Report report, String path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
