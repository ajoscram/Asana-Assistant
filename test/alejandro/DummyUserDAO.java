package alejandro;

import asana_assistant_1.control.daos.UserDAO;
import asana_assistant_1.model.User;
import asana_assistant_1.control.ControlException;

public class DummyUserDAO extends UserDAO {
    private User u1 = new User(69, 69, "Alejandro Schmidt", "ajsocram@gmail.com", true);
    private User u2 = new User(34, 34, "Alejandrito Schmidt", "ajsocram@hotmail.com", false);
    private User u3 = new User(78, 78, "Gabriel Brenes", "jogabra@gmail.com", true);

    @Override
    public User getUser(long id) throws ControlException {
        if(u1.getId() == id)
            return u1;
        else if(u2.getId() == id)
            return u2;
        else if(u3.getId() == id)
            return u3;
        else
            return super.getUser(id);
    }

    @Override
    public User getAsignee(long taskId) throws ControlException{
        if(taskId == 0)
            return u1;
        else if(taskId == 1)
            return u2;
        else if(taskId == 2)
            return u3;
        else if(taskId == 3)
            return u1;
        else if(taskId == 4)
            return u2;
        else if(taskId == 5)
            return u3;
        else if(taskId == 6)
            return u1;
        else
            return super.getAsignee(taskId);
    }
}
