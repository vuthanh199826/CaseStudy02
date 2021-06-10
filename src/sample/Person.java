package sample;

public abstract class Person {
    private String name;
    private String dob;
    private String address;
    private String email;
    private boolean gender;

    public Person(String name, String dob, String address,String email, boolean gender) {
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.email = email;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                '}';
    }
}
