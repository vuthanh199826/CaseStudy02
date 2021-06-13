package sample.model;

import sample.model.Person;

public class Teacher extends Person {
    private String major;

//    public Teacher(String name, String dob, String address, String email, boolean gender, String major) {
public Teacher(String name, String dob, String address, String email, String gender, String major) {
        super(name, dob, address, email, gender);
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "" + super.toString() +
                "major='" + major + '\'' +
                '}';
    }
}
