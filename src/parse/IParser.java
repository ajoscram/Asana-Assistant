package parse;

import java.util.List;

public interface IParser<T> {
    T parse(String fliepath);
    List<T> parseMany(String filepath);
}
