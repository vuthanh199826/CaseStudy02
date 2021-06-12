package sample;

import java.util.List;

public interface Manage <T>{
    void add(T e);
    void delete(T e);
    void edit(int index, T e);
    List <T> searchByName(String name);
    List <T> searchByCode(String code);
    int checkIndex(String code);
    void sort(int option);
    boolean isExist(String code);

}
