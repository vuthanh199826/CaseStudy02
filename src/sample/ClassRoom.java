package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClassRoom implements Manage<Student>{
    private String name;
    private List<Student> students;

    public ClassRoom(String name) {
        this.name = name;
        students = new ArrayList<>();
        students.add(new Student("thanh1","09/08/1998","HN","thanh@gmail.com",true,"C0221K1",8));
        students.add(new Student("thanh1","09/08/1998","HN","thanh@gmail.com",true,"C0321K1",9));
        students.add(new Student("thanh2","09/08/1998","HN","thanh@gmail.com",true,"C0121K1",7));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }




    @Override
    public void add(Student e) {
        students.add(e);
    }

    @Override
    public void delete(Student student) {
        students.remove(student);
    }

    @Override
    public void edit(int index, Student e) {
        for (Student student:students){
            if(students.indexOf(student)==index){
                student.setCode(e.getCode());
                student.setGpa(e.getGpa());
                student.setName(e.getName());
                student.setDob(e.getDob());
                student.setAddress(e.getAddress());
                student.setEmail(e.getEmail());
                student.setGender(e.isGender());
            }
        }

    }

    @Override
    public List<Student>searchByName(String name) {
        List<Student> list = new ArrayList<>();
        for (Student student:students){
            if(student.getName().equals(name)){
                list.add(student);
            }
        }
        return list;
    }

    @Override
    public List<Student> searchByCode(String code) {
        List<Student>list = new ArrayList<>();
        for (Student student:students){
            if(student.getCode().equals(code)){
                list.add(student);
            }
        }
        return list;
    }

    public List<Student> searchByGPA(double min, double max){
        List<Student> list = new ArrayList<>();
        for (Student student:students){
            if(student.getGpa()>= min && student.getGpa()<=max){
                list.add(student);
            }
        }
        return list;
    }


    @Override
    public int checkIndex(String code) {
        for (Student student:students){
            if(student.getCode().equals(code)){
                return students.indexOf(student);
            }
        }
        return -1;
    }

    @Override
    public void sort(int option) {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if(option==1){
                    return o1.getName().compareTo(o2.getName());
                }else if(option==2){
                    return o1.getCode().compareTo(o2.getCode());
                }else if(option==3) {
                    if(o1.getGpa()> o2.getGpa()){
                        return 1;
                    }else if(o1.getGpa() < o2.getGpa()){
                        return -1;
                    }else {
                        return 0;
                    }
                }else {
                    return o1.getDob().compareTo(o2.getDob());
                }
            }
        });
    }

    @Override
    public boolean isExist(String code) {
        for (Student student:students){
            if(student.getCode().equals(code)){
                return true;
            }
        }
        return false;
    }
}
