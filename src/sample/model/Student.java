package sample.model;

public class Student extends Person {
    private String code;
    private double gpa;

    public Student(String name, String dob, String address, String email, String gender, String code, double gpa) {
        super(name, dob, address, email, gender);
        this.code = code;
        this.gpa = gpa;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getAge() {
        String[] arr = getDob().split("/");
        return 2021 - Integer.parseInt(arr[2]);
    }

    @Override
    public String toString() {
        String str = "";
        str += "code[" + getCode() + "]," + " name='" + getName() + "', age='" + getAge() + "', gender='" + getGender() + "', address='" + getAddress() + "', email='" + getEmail() + "', GPA='" + getGpa() + "'";
        return str;
    }
}
