package sample;

import java.io.IOException;
import java.util.List;

public interface Manage <T>{
    void add(T e) throws IOException;
    void delete(int index) throws IOException;
    void edit(int index, T e) throws IOException;
    List <T> searchByName(String name) throws IOException;
    List <T> searchByCode(String code) throws IOException;
    int checkIndex(String code) throws IOException;
    void sort(int option) throws IOException;
    boolean isExist(String code) throws IOException;

}
