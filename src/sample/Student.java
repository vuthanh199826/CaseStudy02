package sample;

public class Student extends Person{
    private String code;
    private double gpa;

    public Student(String name, String dob, String address, String email, boolean gender, String code, double gpa) {
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

    @Override
    public String toString() {
        return "" + super.toString() +
                "code='" + code + '\'' +
                ", gpa=" + gpa +
                '}';
    }
}
