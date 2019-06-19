package asana_assistant_1.parse;

public interface IParser<T> {
    T parse(String filepath) throws ParseException;
}
