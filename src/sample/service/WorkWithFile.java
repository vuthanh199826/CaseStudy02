package sample.service;


import java.io.IOException;
import java.util.List;

public interface WorkWithFile<T> {
    void writeToFileCSV(String path, List<T> list) throws IOException;

    void addToFileCSV(String path, T e) throws IOException;

    List<T> readFileCSV(String path) throws IOException;

}
