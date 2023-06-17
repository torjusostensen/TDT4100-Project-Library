package Library;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadAndWrite implements IReadAndWrite {

    public String readFile(String filename) throws IOException {

        FileReader fileReader = new FileReader(new File(getPath(filename)));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();

        if (filename.equals("")) {
            throw new IllegalArgumentException("File name cannot be empty");
        }

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void writeFile(String filename, Library library) throws IOException {
        try {
            if (filename.equals("")) {
                throw new IllegalArgumentException("File name cannot be empty");
            }

        FileWriter fw = new FileWriter(new File(getPath(filename)));
        BufferedWriter out = new BufferedWriter(fw);

        // The sole purpose of this method is to print out the receipt from library, therefore it writes library.toString()
        // The toString method in library has been adjusted to only show to borrowed books.

        out.write(library.toString());
        out.close();

    } catch (IOException e) {
        throw new IOException("Error in file format"); // Error in format
        }
    }

    @Override
    public void clearFile(String filename) throws IOException {
        try {
            if (filename.length() == 0) {
                throw new IllegalArgumentException("File name cannot be empty");
            }

        FileWriter fw = new FileWriter(new File(getPath(filename)));
        BufferedWriter out = new BufferedWriter(fw);
        out.write("");
        out.close();
        } catch (IOException e) {
            throw new IOException("Error in file format"); // Error in format
        }
    }

    public String getPath(String filename) {
        return ReadAndWrite.class.getResource("libraryFiles/").getFile() + filename + ".txt";
    }
}