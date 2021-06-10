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
        students.add(new Student("thanh3","09/08/1998","HN","thanh@gmail.com",true,"C0221K1",8));
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
    public Student search(String name) {
        for (Student student:students){
            if(student.getName().equals(name)){
                return student;
            }
        }
        return null;
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
                }else {
                    if(o1.getGpa()> o2.getGpa()){
                        return 1;
                    }else if(o1.getGpa() < o2.getGpa()){
                        return -1;
                    }else {
                        return 0;
                    }
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
