package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClassRoom implements Manage<Student>, WorkWithFile<Student> {
    private String name;
    private List<Student> students;

    public ClassRoom(String name) {
        this.name = name;
        students = new ArrayList<>();
        students.add(new Student("thanh1", "09/08/1998", "HN", "thanh@gmail.com", true, "C0221K1", 8));
        students.add(new Student("thanh1", "09/08/1998", "HN", "thanh@gmail.com", true, "C0321K1", 9));
        students.add(new Student("thanh2", "09/08/1998", "HN", "thanh@gmail.com", true, "C0121K1", 7));
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
    public void add(Student e) throws IOException {
//        List<Student> list = new ArrayList<>(readFileCSV("Student.csv"));
//        list.add(e);
//        students = readFileCSV("Student.csv");
//        students.add(e);
//        writeToFileCSV("Student.csv",students);
        addToFileCSV("Students.csv", e);
    }

    @Override
    public void delete(int index) throws IOException {
        students = readFileCSV("Students.csv");
        for (Student student : students) {
            if (students.indexOf(student) == index) {
                students.remove(index);
                break;
            }
        }
        writeToFileCSV("Students.csv", students);
    }

    @Override
    public void edit(int index, Student e) throws IOException {
        students = readFileCSV("Students.csv");
        for (Student student : students) {
            if (students.indexOf(student) == index) {
                student.setCode(e.getCode());
                student.setGpa(e.getGpa());
                student.setName(e.getName());
                student.setDob(e.getDob());
                student.setAddress(e.getAddress());
                student.setEmail(e.getEmail());
                student.setGender(e.isGender());
            }
        }
        writeToFileCSV("Students.csv", students);
    }

    @Override
    public List<Student> searchByName(String name) throws IOException {
        students = readFileCSV("Students.csv");
        List<Student> list = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().equals(name)) {
                list.add(student);
            }
        }
        return list;
    }

    @Override
    public List<Student> searchByCode(String code) throws IOException {
        students = readFileCSV("Students.csv");
        List<Student> list = new ArrayList<>();
        for (Student student : students) {
            if (student.getCode().equals(code)) {
                list.add(student);
            }
        }
        return list;
    }

    public List<Student> searchByGPA(double min, double max) throws IOException {
        students = readFileCSV("Students.csv");
        List<Student> list = new ArrayList<>();
        for (Student student : students) {
            if (student.getGpa() >= min && student.getGpa() <= max) {
                list.add(student);
            }
        }
        return list;
    }


    @Override
    public int checkIndex(String code) {
        for (Student student : students) {
            if (student.getCode().equals(code)) {
                return students.indexOf(student);
            }
        }
        return -1;
    }

    @Override
    public void sort(int option) throws IOException {
        students = readFileCSV("Students.csv");
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (option == 1) {
                    return o1.getName().compareTo(o2.getName());
                } else if (option == 2) {
                    return o1.getCode().compareTo(o2.getCode());
                } else if (option == 3) {
                    if (o1.getGpa() > o2.getGpa()) {
                        return 1;
                    } else if (o1.getGpa() < o2.getGpa()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } else {
                    return o1.getDob().compareTo(o2.getDob());
                }
            }
        });
        writeToFileCSV("Students.csv", students);
    }

    @Override
    public boolean isExist(String code) throws IOException {
        students = readFileCSV("Students.csv");
        for (Student student : students) {
            if (student.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void writeToFileCSV(String path, List<Student> list) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (Student student : list) {
            bufferedWriter.write(student.getName() + "," + student.getDob() + "," + student.getAddress() + "," + student.getEmail() + "," + student.isGender() + "," + student.getCode() + "," + student.getGpa() + "\n");
        }
        bufferedWriter.close();
        fileWriter.close();
    }

    @Override
    public void addToFileCSV(String path, Student student) throws IOException {
        FileWriter fileWriter = new FileWriter(path, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(student.getName() + "," + student.getDob() + "," + student.getAddress() + "," + student.getEmail() + "," + student.isGender() + "," + student.getCode() + "," + student.getGpa() + "\n");
        bufferedWriter.close();
        fileWriter.close();
    }

    @Override
    public List<Student> readFileCSV(String path) throws IOException {
        List<Student> list = new ArrayList<>();
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] arr = line.split(",");
            String name = arr[0];
            String dob = arr[1];
            String address = arr[2];
            String email = arr[3];
            boolean gender = Boolean.parseBoolean(arr[4]);
            String code = arr[5];
            double gpa = Double.parseDouble(arr[6]);
            list.add(new Student(name, dob, address, email, gender, code, gpa));
        }
        return list;
    }


}
