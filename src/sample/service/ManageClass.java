package sample.service;

import sample.model.Student;
import sample.service.ClassRoom;
import sample.service.Manage;
import sample.service.WorkWithFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ManageClass implements Manage<ClassRoom>, WorkWithFile<ClassRoom> {
    List<ClassRoom> classRoomList = new ArrayList<>();

    public ManageClass() {
        try {
            classRoomList = readFileCSV("ListOfClassName.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ClassRoom> getClassRoomList()  {
        classRoomList = readFileCSV("ListOfClassName.csv");
        return classRoomList;
    }

    public ClassRoom search(String name) {
        for (ClassRoom classRoom : classRoomList) {
            if (classRoom.getName().equals(name)) {
                return classRoom;
            }
        }
        return null;
    }

    public void addAll() throws IOException {
        FileWriter fileWriter = new FileWriter("AllStudent.csv");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (ClassRoom classRoom:getClassRoomList()){
            for (Student student:classRoom.getStudents()){
                bufferedWriter.write(student.getName() + "," + student.getDob() + "," + student.getAddress() + "," + student.getEmail() + "," + student.getGender() + "," + student.getCode() + "," + student.getGpa() + "\n");
            }
        }
        bufferedWriter.close();
        fileWriter.close();
    }

    public List<Student> readAll() throws IOException {
        addAll();
        List<Student> list = new ArrayList<>();
        for (ClassRoom classRoom:getClassRoomList()){
            list.addAll(classRoom.getStudents());
        }
        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Double.compare(o2.getGpa(), o1.getGpa());
            }
        });

        return list;
    }




    @Override
    public void add(ClassRoom e) throws IOException {
        classRoomList.add(e);
        addToFileCSV("ListOfClassName.csv", e);
    }

    @Override
    public void delete(int index) throws IOException {
        classRoomList = readFileCSV("ListOfClassName.csv");
        classRoomList.remove(index);
        writeToFileCSV("ListOfClassName.csv", classRoomList);
    }


    @Override
    public void edit(int index, ClassRoom e) throws IOException {
        classRoomList = readFileCSV("ListOfClassName.csv");
        for (ClassRoom classRoom:classRoomList){
            if(classRoomList.indexOf(classRoom)==index){
                classRoom.setName(e.getName());
                classRoom.setPath(e.getPath());
            }
        }
        writeToFileCSV("ListOfClassName.csv", classRoomList);
    }

    @Override
    public List<ClassRoom> searchByName(String name) {
        return null;
    }

    @Override
    public List<ClassRoom> searchByCode(String code) {
        return null;
    }

    @Override
    public int checkIndex(String name)  {
        classRoomList = readFileCSV("ListOfClassName.csv");
        for (ClassRoom classRoom : classRoomList) {
            if (classRoom.getName().equals(name)) {
                return classRoomList.indexOf(classRoom);
            }
        }
        return -1;
    }

    @Override
    public void sort(int option) {

    }

    @Override
    public boolean isExist(String name)  {
        classRoomList = readFileCSV("ListOfClassName.csv");
        for (ClassRoom classRoom : classRoomList) {
            if (classRoom.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void writeToFileCSV(String path, List<ClassRoom> list) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (ClassRoom classRoom : list) {
            bufferedWriter.write(classRoom.getName() + "," + classRoom.getPath() + "\n");
        }
        bufferedWriter.close();
        fileWriter.close();
    }

    @Override
    public void addToFileCSV(String path, ClassRoom e) throws IOException {
        FileWriter fileWriter = new FileWriter(path, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(e.getName() + "," + e.getPath() + "\n");
        bufferedWriter.close();
        fileWriter.close();
    }

    @Override
    public List<ClassRoom> readFileCSV(String path)  {
        List<ClassRoom> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] arr = line.split(",");
                String name = arr[0];
                String pathOfClass = arr[1];
                list.add(new ClassRoom(name, pathOfClass));
            }
            return list;
        } catch (Exception e) {
            return list;
        }
    }

    @Override
    public String toString() {
        return "ManageClass{" +
                "classRoomList=" + classRoomList +
                '}';
    }
}
