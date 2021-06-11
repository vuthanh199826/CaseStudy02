package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
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
    ComboBox comboBox;
    @FXML
    TextArea textArea;
    ClassRoom classRoom = new ClassRoom("C0321K1");
    Validate validate = new Validate();
    CreateAlert createAlert = new CreateAlert();

    public void initialize() {
        ObservableList<String> list = FXCollections.observableArrayList("Sort by name", "Sort by code", "Sort by GPA");
        comboBox.setItems(list);
        comboBox.getSelectionModel().selectFirst();
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
        String code = delete.getText();
        if (classRoom.isExist(code)) {
            for (Student student : classRoom.getStudents()) {
                if (student.getCode().equals(code)) {
                    showConfirmationDelete(student);
                    display();
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
                classRoom.edit(classRoom.searchByCode(code), student);
                clear();
                display();
            }
        } else if (code.equals("")) {
            createAlert.showAlert("WARNING", "Vui lòng điền Code vào ô bên cạnh", createAlert.WARNING);
        } else {
            createAlert.showAlert("WARNING", "Code sai hoặc đã tồn tại !", createAlert.WARNING);
            edit.clear();
        }
    }

    public void sort() {
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


}
