package alejandro;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.daos.EvidenceDAO;
import java.util.ArrayList;
import java.util.List;
import asana_assistant_1.model.Evidence;

public class DummyEvidenceDAO extends EvidenceDAO{

    private Evidence e0 = new Evidence(0, "0", "limpio_arriba1.png");
    private Evidence e1 = new Evidence(1, "1","limpio_arriba2.png");
    private Evidence e2 = new Evidence(2, "2","limpio_arriba3.png");
    private Evidence e3 = new Evidence(3, "3","limpio_abajo.png");
    private Evidence e4 = new Evidence(4, "4","plantas_humedas.jpeg");
    private Evidence e5 = new Evidence(5, "5","plantas_humedas1.jpeg");
    
    @Override
    public List<Evidence> getEvidences(long developmentId) throws ControlException{
        ArrayList<Evidence> evidences = new ArrayList();
        if(developmentId == 0){
            evidences.add(e0);
            evidences.add(e1);
            evidences.add(e2);
            return evidences;
        } else if(developmentId == 1){
            evidences.add(e3);
            return evidences;
        } else if(developmentId == 2){
            evidences.add(e4);
            evidences.add(e5);
            return evidences;
        } else if(developmentId == 3)
            return evidences;
        return super.getEvidences(developmentId);
    }
    
}
