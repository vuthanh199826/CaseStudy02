package sample.model;

import sample.model.ClassRoom;
import sample.service.Manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageClass implements Manage<ClassRoom> {
    List<ClassRoom> classRoomList = new ArrayList<>();

    public ManageClass() {
    }

    public ClassRoom search(String name){
        for (ClassRoom classRoom:classRoomList){
            if(classRoom.getName().equals(name)){
                return classRoom;
            }
        }
        return null;
    }

    @Override
    public void add(ClassRoom e) throws IOException {
        classRoomList.add(e);
    }

    @Override
    public void delete(int index) throws IOException {

    }

    @Override
    public void edit(int index, ClassRoom e) throws IOException {

    }

    @Override
    public List<ClassRoom> searchByName(String name) throws IOException {
        return null;
    }

    @Override
    public List<ClassRoom> searchByCode(String code) throws IOException {
        return null;
    }

    @Override
    public int checkIndex(String name) {
        for (ClassRoom classRoom:classRoomList){
            if(classRoom.getName().equals(name)){
                return classRoomList.indexOf(classRoom);
            }
        }
        return -1;
    }

    @Override
    public void sort(int option) throws IOException {

    }

    @Override
    public boolean isExist(String name) throws IOException {
        for (ClassRoom classRoom:classRoomList){
            if(classRoom.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
