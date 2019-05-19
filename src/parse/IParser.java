package parse;

import java.util.List;

public interface IParser<T> {
    T parse(String filepath) throws ParseException;
    List<T> parseMany(String filepath) throws ParseException;
}
