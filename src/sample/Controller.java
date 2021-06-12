package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    @FXML
    TextField name;
    @FXML
    TextField dob;
    @FXML
    TextField address;
    @FXML
    TextField email;
    @FXML
    TextField gender;
    @FXML
    TextField code;
    @FXML
    TextField gpa;
    @FXML
    TextField delete;
    @FXML
    TextField edit;
    @FXML
    TextField inputSearch;
    @FXML
    ComboBox comboBox;
    @FXML
    ComboBox searchChoice;
    @FXML
    ComboBox min;
    @FXML
    ComboBox max;
    @FXML
    TextArea textArea;

    ClassRoom classRoom = new ClassRoom("C0321K1");
    Validate validate = new Validate();
    CreateAlert createAlert = new CreateAlert();

    public void initialize() {
        ObservableList<String> list = FXCollections.observableArrayList("Sort by name", "Sort by code", "Sort by GPA");
        setComboBox(comboBox, list);
        ObservableList<String> listSearchChoice = FXCollections.observableArrayList("Search by name", "Search by code", "Search by GPA");
        setComboBox(searchChoice, listSearchChoice);
        ObservableList<String> listMin = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        setComboBox(min, listMin);
        ObservableList<String> listMax = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        setComboBox(max, listMax);
        setPlaceHolderOfStudent("name", "dd/mm/yyyy", "address", "example@gmail.com", "true/false", "code", "0.0 - 10.0");
        setPlaceHolderOfSearch();
    }


    public void add() {
        Student student = createStudent();
        if (student != null) {
            classRoom.add(student);
            clear();
            display();
            createAlert.showAlert("Notification", "Successfully", createAlert.INFORMATION);
        } else {
            createAlert.showAlert("Error", "Vui lòng điền đầy đủ các trường thông tin bên trái", createAlert.ERROR);
        }
    }

    public void display() {
        String str = "";
        for (Student student : classRoom.getStudents()) {
            str += student.toString() + "\n";
        }
        textArea.setText(str);
    }

    public void delete() {
        clear();
        String code = delete.getText();
        if (classRoom.isExist(code)) {
            for (Student student : classRoom.getStudents()) {
                if (student.getCode().equals(code)) {
                    showConfirmationDelete(student);
                    display();
                    delete.clear();
                    break;
                }
            }

        } else {
            createAlert.showAlert("Error", "Code is invalid", createAlert.ERROR);
            delete.clear();
        }

    }

    public void edit() {
        String code = edit.getText();
        if (validate.validateRegex(code, validate.CODE_REGEX) && classRoom.isExist(code)) {
            Student student = createStudent();
            if (student != null) {
                classRoom.edit(classRoom.checkIndex(code), student);
                createAlert.showAlert("Notification", "Edit Successfully", createAlert.INFORMATION);
                clear();
                display();
                edit.clear();
            } else {
                createAlert.showAlert("WARNING", "Vui lòng điền thông tin mới học sinh phía bên trái", createAlert.WARNING);
            }
        } else if (code.equals("")) {
            createAlert.showAlert("WARNING", "Vui lòng điền Code học sinh cần sửa vào ô bên cạnh", createAlert.WARNING);
        } else {
            createAlert.showAlert("WARNING", "Code sai hoặc không tồn tại !", createAlert.WARNING);
            edit.clear();
        }
    }

    public void sort() {
        clear();
        int option = 0;
        if (comboBox.getValue().equals("Sort by name")) {
            option = 1;
        } else if (comboBox.getValue().equals("Sort by code")) {
            option = 2;
        } else if (comboBox.getValue().equals("Sort by GPA")) {
            option = 3;
        }
        if (comboBox.getValue() != null) {
            classRoom.sort(option);
            display();
        }
    }

    public void search() {
        if (searchChoice.getValue().equals("Search by name")) {
            if (inputSearch.getText().equals("")) {
                createAlert.showAlert("WARNING", "Vui lòng điền tên học sinh cần tìm vào ô bên dưới", createAlert.WARNING);
            } else {
                String name = inputSearch.getText();
                List list = new ArrayList(classRoom.searchByName(name));
                if (list.size() == 0) {
                    createAlert.showAlert("WARNING", "Không tìm thấy tên học sinh này", createAlert.WARNING);
                    inputSearch.clear();
                } else {
                    displayOfSearch(list);
                }
            }

        } else if (searchChoice.getValue().equals("Search by code")) {
            if (inputSearch.getText().equals("")) {
                createAlert.showAlert("WARNING", "Vui lòng điền code học sinh cần tìm vào ô bên dưới", createAlert.WARNING);
            } else {
                String code = inputSearch.getText();
                List list = new ArrayList(classRoom.searchByCode(code));
                if (list.size() == 0) {
                    createAlert.showAlert("WARNING", "Không tìm thấy tên học sinh này", createAlert.WARNING);
                    inputSearch.clear();
                } else {
                    displayOfSearch(list);
                }
            }

        } else if (searchChoice.getValue().equals("Search by GPA")) {
            inputSearch.clear();
            double minGPA = Double.parseDouble(min.getValue().toString());
            double maxGPA = Double.parseDouble(max.getValue().toString());
            if (minGPA <= maxGPA) {
               List<Student>list = new ArrayList<>(classRoom.searchByGPA(minGPA,maxGPA));
               if(list.size()==0){
                   createAlert.showAlert("WARNING", "Không tìm thấy học sinh trong thang điểm này", createAlert.WARNING);
               }else {
                   displayOfSearch(list);
               }
            } else if (minGPA > maxGPA) {
                createAlert.showAlert("WARNING", "điểm nhỏ nhất phải nhỏ hơn điểm lớn nhất", createAlert.WARNING);
            }
        }
    }

    public void displayOfSearch(List<Student> list) {
        String str = "";
        for (Student student : list) {
            str += student + "\n";
        }
        textArea.setText(str);
    }


    public void clear() {
        name.clear();
        dob.clear();
        address.clear();
        email.clear();
        gender.clear();
        code.clear();
        gpa.clear();
    }

    public Student createStudent() {
        String newName = name.getText();
        if (validate.validateRegex(newName, validate.NAME_REGEX)) {
            String newDob = dob.getText();
            if (validate.validateRegex(newDob, validate.DOB_REGEX)) {
                String newAddress = address.getText();
                if (validate.validateRegex(newAddress, validate.ADDRESS_REGEX)) {
                    String newEmai = email.getText();
                    if (validate.validateRegex(newEmai, validate.EMAIL_REGEX)) {
                        if (validate.validateRegex(gender.getText(), validate.GENDER_REGEX)) {
                            boolean newGender = Boolean.parseBoolean(gender.getText());
                            String newCode = code.getText();
                            if (validate.validateRegex(newCode, validate.CODE_REGEX) && !classRoom.isExist(newCode)) {
                                if (validate.validateRegex(gpa.getText(), validate.GPA_REGEX)) {
                                    Double newGpa = Double.parseDouble(gpa.getText());
                                    return new Student(newName, newDob, newAddress, newEmai, newGender, newCode, newGpa);
                                } else {
                                    gpa.clear();
                                }
                            } else {
                                code.clear();
                            }
                        } else {
                            gender.clear();
                        }

                    } else {
                        email.clear();
                    }
                } else {
                    address.clear();
                }
            } else {
                dob.clear();
            }
        } else {
            name.clear();
        }
        return null;
    }

    public void showConfirmationDelete(Student student) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Student");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa học sinh này ?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
            createAlert.showAlert("Notification", "No suggestion", createAlert.INFORMATION);
        } else if (option.get() == ButtonType.OK) {
            classRoom.delete(student);
            createAlert.showAlert("Notification", "Deleted", createAlert.INFORMATION);
        } else if (option.get() == ButtonType.CANCEL) {
            createAlert.showAlert("Notification", "Canceled", createAlert.INFORMATION);
        }
    }

    public void setPlaceHolderOfStudent(String fieldName, String fieldDob, String fieldAddress, String fieldEmail, String fieldGender, String fieldCode, String fieldGpa) {
        name.setPromptText(fieldName);
        dob.setPromptText(fieldDob);
        address.setPromptText(fieldAddress);
        email.setPromptText(fieldEmail);
        gender.setPromptText(fieldGender);
        code.setPromptText(fieldCode);
        gpa.setPromptText(fieldGpa);

    }

    public void setPlaceHolderOfSearch() {
        if (searchChoice.getValue().equals("Search by name")) {
            inputSearch.setPromptText("name");
        } else if (searchChoice.getValue().equals("Search by code")) {
            inputSearch.setPromptText("code");
        } else {
            inputSearch.setPromptText("none");
            inputSearch.clear();
        }
    }

    public void setComboBox(ComboBox comboBox, ObservableList<String> observableList) {
        comboBox.setItems(observableList);
        comboBox.getSelectionModel().selectFirst();
    }


}
