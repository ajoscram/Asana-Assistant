package daos;

import control.daos.DevelopmentDAO;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import model.Development;

public class DummyDevelopmentDAO extends DevelopmentDAO{

    private Development d0 = new Development(0, LocalDate.of(2019, Month.MAY, 28), 3, "Hojas limpias arriba.", LocalDate.of(2019, Month.MAY, 28));
    private Development d1 = new Development(1, LocalDate.of(2019, Month.MAY, 29), 2, "Hojas limpias abajo.", LocalDate.of(2019, Month.MAY, 29));
    private Development d2 = new Development(2, LocalDate.of(2019, Month.MAY, 28), 3, "Plantas Regadas", LocalDate.of(2019, Month.MAY, 28));
    private Development d3 = new Development(3, LocalDate.of(2019, Month.MAY, 30), 3, "Tubo cerrado.", LocalDate.of(2019, Month.MAY, 30));
    
    @Override
    public List<Development> getDevelopments(long taskId, LocalDate start, LocalDate end) {
        ArrayList<Development> developments = new ArrayList(); 
        if(taskId == 0)
            return developments;
        else if(taskId == 1){
            developments.add(d0);
            developments.add(d1);
            return developments;
        } else if(taskId == 2){
            developments.add(d2);
            return developments;
        }
        else if(taskId == 3)
            return developments;
        else if(taskId == 4)
            return developments;
        else if(taskId == 5)
            return developments;
        else if(taskId == 6){
            developments.add(d3);
            return developments;
        } else
            return super.getDevelopments(taskId, start, end);
    }
}
