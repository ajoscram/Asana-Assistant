package parse;

public interface IParser<T> {
    T parse(String filepath) throws ParseException;
}
