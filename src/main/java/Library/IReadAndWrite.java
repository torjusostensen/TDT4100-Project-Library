package Library;

import java.io.IOException;

public interface IReadAndWrite {

    String readFile(String filename) throws IOException;

    void writeFile(String filename, Library library) throws IOException;

    void clearFile(String filename) throws IOException;

}
