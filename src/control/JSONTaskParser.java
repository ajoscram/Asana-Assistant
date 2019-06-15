package control;

import control.dtos.TaskDTO;
import control.dtos.UserDTO;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import parse.ParseException;

public class JSONTaskParser implements TaskParser {
    
    public JSONTaskParser(){ }
    
    private UserDTO parseAsignee(JSONObject asignee){
        if(asignee == null)
            return null;
        String name = (String)asignee.get("name");
        Long asanaId = (Long)asignee.get("id");
        return new UserDTO(name, null, null, asanaId);
    }
    
    private Task.Type parseType(String type, boolean subtask) throws ParseException{
        if(subtask)
            return Task.Type.SUBTASK;
        else if(type.equals("section"))
            return Task.Type.SECTION;
        else if(type.equals("default_task"))
            return Task.Type.SINGLE;
        throw new ParseException(ParseException.Type.STRUCTURE);
    }
    
    private LocalDate parseLocalDate(String date){
        if(date == null)
            return null;
        else{
            try{
                return Instant.parse(date).atZone(ZoneId.systemDefault()).toLocalDate();
            } catch(DateTimeParseException e){
                return LocalDate.parse(date);
            }
        }
    }
    
    private TaskDTO parse(JSONObject task, int index, boolean isSubtask) throws ParseException {
        Long id = (Long)task.get("id");
        String name = (String)task.get("name");
        if(id == null || name == null)
            throw new ParseException(ParseException.Type.STRUCTURE);
        
        Task.Type type = parseType((String)task.get("resource_subtype"), isSubtask);
        UserDTO asignee = parseAsignee((JSONObject)task.get("assignee"));
        
        LocalDate created = parseLocalDate((String)task.get("created_at"));
        LocalDate due = parseLocalDate((String)task.get("due_on"));
        LocalDate completed = parseLocalDate((String)task.get("completed_at"));
        
        TaskDTO taskDTO = new TaskDTO(id, name, type, asignee, index, created, due, completed);
        int subtaskIndex = 0;
        for(Object subtask : (JSONArray)task.get("subtasks"))
            taskDTO.addSubtask(parse((JSONObject)subtask, subtaskIndex++, true));
        return taskDTO;
    }
    
    private Object read(String filepath) throws ParseException {
        try{
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            Object object = parser.parse(new FileReader(filepath));
            return object;
        } catch(IOException e) {
            throw new ParseException(ParseException.Type.FILE_IO_ERROR);
        } catch (org.json.simple.parser.ParseException ex) {
            throw new ParseException(ParseException.Type.STRUCTURE);
        }
    }
    
    //IParser
    @Override
    public List<TaskDTO> parse(String filepath) throws ParseException {
        try {
            JSONObject read = (JSONObject)read(filepath);
            if(read == null)
                return null;
            else if(read instanceof JSONObject){
                ArrayList<TaskDTO> tasks = new ArrayList();
                JSONArray array = (JSONArray)read.get("data");
                int index = 0;
                for(Object task : array)
                    tasks.add(parse((JSONObject)task, index++, false));
                return tasks;
            }
            else
                throw new ParseException(ParseException.Type.STRUCTURE);
        } catch(NullPointerException | ClassCastException ex) {
            throw new ParseException(ParseException.Type.STRUCTURE);
        }
    }
}