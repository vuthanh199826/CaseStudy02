package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    TextArea textArea;
    ClassRoom classRoom = new ClassRoom("C0321K1");
    Validate validate = new Validate();

    public void clear(){
        name.clear();
        dob.clear();
        address.clear();
        email.clear();
        gender.clear();
        code.clear();
        gpa.clear();
    }


    public void add(){
        String newName = name.getText();
        if(validate.validateRegex(newName,validate.getNameRegex())){
            String newDob = dob.getText();
            if(validate.validateRegex(newDob,validate.getDobRegex())){
                String newAddress = address.getText();
                if(validate.validateRegex(newAddress,validate.getAddressRegex())){
                    String newEmai = email.getText();
                    if(validate.validateRegex(newEmai,validate.getEmailRegex())){
                        if(validate.validateRegex(gender.getText(),validate.getGenderRegex())){
                            boolean newGender = Boolean.parseBoolean(gender.getText());
                            String newCode = code.getText();
                            if(validate.validateRegex(newCode,validate.getCodeRegex())){
                                if(validate.validateRegex(gpa.getText(),validate.getGpaRegex())){
                                    Double newGpa = Double.parseDouble(gpa.getText());
                                    Student student = new Student(newName,newDob,newAddress,newEmai,newGender,newCode,newGpa);
                                    classRoom.add(student);
                                    clear();
                                }else {
                                    gpa.clear();
                                }
                            }else {
                                code.clear();
                            }
                        }else {
                            gender.clear();
                        }

                    }else {
                        email.clear();
                    }
                }else {
                    address.clear();
                }
            }else {
                dob.clear();
            }
        }else {
            name.clear();
        }

    }

    public void display(){
        String str = "";
        for (Student student: classRoom.getStudents()){
            str+= student.toString() + "\n";
        }
        textArea.setText(str);
    }
    public void delete(){
        String code = delete.getText();
        if(classRoom.isExist(code)){
            for (Student student: classRoom.getStudents()){
                if(student.getCode().equals(code)){
                    classRoom.delete(student);
                }
            }
            display();
        }else {
            delete.clear();
        }

    }







}
