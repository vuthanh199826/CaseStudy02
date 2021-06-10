package sample;

public interface Manage <T>{
    void add(T e);
    void delete(T e);
    void edit(int index, T e);
    T search(String name);
    void sort(int option);
    boolean isExist(String code);

}