package sample.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.ManageClass;
import sample.model.ClassRoom;
import sample.model.CreateAlert;
import sample.model.Student;
import sample.model.Validate;
import sample.service.WorkWithFile;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    Button next;
    Stage window;


    public Login() {
    }
//
//    public void next(ActionEvent actionEvent) {
//actionEvent -> window.setScene();
//
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

