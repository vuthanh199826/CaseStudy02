package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.service.ManageClass;
import sample.service.ClassRoom;
import sample.model.CreateAlert;
import sample.model.Student;
import sample.model.Validate;

import java.io.*;
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
    TextField code;
    @FXML
    TextField gpa;
    @FXML
    TextField delete;
    @FXML
    TextField edit;
    @FXML
    TextField movedOnName;
    @FXML
    TextField editClassName;
    @FXML
    TextField editClassPath;
    @FXML
    TextField searchClass;
    @FXML
    TextField inputSearch;
    @FXML
    TextField className;
    @FXML
    TextField pathName;
    @FXML
    TextField deleteClass;
    @FXML
    ComboBox comboBox;
    @FXML
    ComboBox searchChoice;
    @FXML
    ComboBox classChoice;
    @FXML
    ComboBox choiceGender;
    @FXML
    ComboBox movedOnClass;
    @FXML
    ComboBox min;
    @FXML
    ComboBox max;
    @FXML
    TextArea textArea;
    Validate validate = new Validate();
    CreateAlert createAlert = new CreateAlert();
    ManageClass manageClass = new ManageClass();

    public Controller() {
    }

    public void initialize() {
        ObservableList<String> list = FXCollections.observableArrayList("Sort by name", "Sort by code", "Sort by GPA");
        setComboBox(comboBox, list);
        ObservableList<String> listSearchChoice = FXCollections.observableArrayList("Search by name", "Search by code", "Search by GPA");
        setComboBox(searchChoice, listSearchChoice);
        ObservableList<String> listMin = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        setComboBox(min, listMin);
        ObservableList<String> listGender = FXCollections.observableArrayList("Male", "Female");
        setComboBox(choiceGender, listGender);
        ObservableList<String> listMax = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        setComboBox(max, listMax);
        setClassChoice();
        setPlaceHolderOfStudent("name", "dd/mm/yyyy", "address", "example@gmail.com", "true/false", "code", "0.0 - 10.0");
        setPlaceHolderOfSearch();
    }

    public void add() throws IOException {
        String name = classChoice.getValue().toString();
        Student student = createStudent();
        if (student != null) {
            manageClass.search(name).add(student);
            clear();
            display();
            createAlert.showAlert("Notification", "Successfully", createAlert.INFORMATION);
        } else {
            createAlert.showAlert("Error", "Vui l??ng ??i???n ?????y ????? c??c tr?????ng th??ng tin b??n tr??i", createAlert.ERROR);
        }
    }

    public void display() throws IOException {
        String name = classChoice.getValue().toString();
        String path = manageClass.search(name).getPath();
        List<Student> list = manageClass.search(name).readFileCSV(path);
        String str = "";
        try {
            for (Student student : list) {
                str += student.toString() + "\n";
            }
            if (str.equals("")) {
                textArea.setText("Empty");
            } else {
                textArea.setText(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() throws IOException {
        clear();
        String name = classChoice.getValue().toString();
        String code = delete.getText();
        if (manageClass.search(name).isExist(code)) {
            showConfirmationDelete(code);
            display();
        } else {
            createAlert.showAlert("Error", "Code is invalid", createAlert.ERROR);
            System.out.println(" not exist");
        }
        delete.clear();
    }

    public void edit() throws IOException {
        String name = classChoice.getValue().toString();
        String code = edit.getText();
        if (validate.validateRegex(code, validate.CODE_REGEX) && manageClass.search(name).isExist(code)) {
            Student student = createStudent();
            if (student != null) {
                manageClass.search(name).edit(manageClass.search(name).checkIndex(code), student);
                createAlert.showAlert("Notification", "Edit Successfully", createAlert.INFORMATION);
                clear();
                display();
                edit.clear();
            } else {
                createAlert.showAlert("WARNING", "Vui l??ng ??i???n th??ng tin m???i h???c sinh ph??a b??n tr??i", createAlert.WARNING);
            }
        } else if (code.equals("")) {
            createAlert.showAlert("WARNING", "Vui l??ng ??i???n Code h???c sinh c???n s???a v??o ?? b??n c???nh", createAlert.WARNING);
        } else {
            createAlert.showAlert("WARNING", "Code sai ho???c kh??ng t???n t???i !", createAlert.WARNING);
            edit.clear();
        }
    }

    public void sort() throws IOException {
        clear();
        String name = classChoice.getValue().toString();
        int option = 0;
        if (comboBox.getValue().equals("Sort by name")) {
            option = 1;
        } else if (comboBox.getValue().equals("Sort by code")) {
            option = 2;
        } else if (comboBox.getValue().equals("Sort by GPA")) {
            option = 3;
        }
        if (comboBox.getValue() != null) {
            manageClass.search(name).sort(option);
            display();
        }
    }

    public void search() throws IOException {
        if (searchChoice.getValue().equals("Search by name")) {
            if (inputSearch.getText().equals("")) {
                createAlert.showAlert("WARNING", "Vui l??ng ??i???n t??n h???c sinh c???n t??m v??o ?? b??n d?????i", createAlert.WARNING);
            } else {
                String name = inputSearch.getText();
                List list = new ArrayList(manageClass.search(classChoice.getValue().toString()).searchByName(name));
                manageClass.search(classChoice.getValue().toString()).writeToFileCSV("SearchList.csv", list);
                if (list.size() == 0) {
                    createAlert.showAlert("WARNING", "Kh??ng t??m th???y t??n h???c sinh n??y", createAlert.WARNING);
                    inputSearch.clear();
                } else {
                    displayOfSearch();
                }
            }
        } else if (searchChoice.getValue().equals("Search by code")) {
            if (inputSearch.getText().equals("")) {
                createAlert.showAlert("WARNING", "Vui l??ng ??i???n code h???c sinh c???n t??m v??o ?? b??n d?????i", createAlert.WARNING);
            } else {
                String code = inputSearch.getText();
                List list = new ArrayList(manageClass.search(classChoice.getValue().toString()).searchByName(code));
                manageClass.search(classChoice.getValue().toString()).writeToFileCSV("SearchList.csv", list);
                if (list.size() == 0) {
                    createAlert.showAlert("WARNING", "Kh??ng t??m th???y t??n h???c sinh n??y", createAlert.WARNING);
                    inputSearch.clear();
                } else {
                    displayOfSearch();
                }
            }
        } else if (searchChoice.getValue().equals("Search by GPA")) {
            inputSearch.clear();
            double minGPA = Double.parseDouble(min.getValue().toString());
            double maxGPA = Double.parseDouble(max.getValue().toString());
            if (minGPA <= maxGPA) {
                List list = new ArrayList(manageClass.search(classChoice.getValue().toString()).searchByGPA(minGPA, maxGPA));
                manageClass.search(classChoice.getValue().toString()).writeToFileCSV("SearchList.csv", list);
                if (list.size() == 0) {
                    createAlert.showAlert("WARNING", "Kh??ng t??m th???y h???c sinh trong thang ??i???m n??y", createAlert.WARNING);
                } else {
                    displayOfSearch();
                }
            } else if (minGPA > maxGPA) {
                createAlert.showAlert("WARNING", "??i???m nh??? nh???t ph???i nh??? h??n ??i???m l???n nh???t", createAlert.WARNING);
            }
        }
    }

    public void createNewClass() throws IOException {
        String name = className.getText();
        String path = pathName.getText();
        if (name.equals("") || path.equals("")) {
            createAlert.showAlert("WARNING", "Vui l??ng nh???p ?????y ????? th??ng tin v??o ?? b??n c???nh", createAlert.WARNING);
        } else {
            if (manageClass.isExist(name)) {
                createAlert.showAlert("WARNING", "T??n l???p ???? t???n t???i", createAlert.WARNING);
            } else {
                if (validate.validateRegex(name, validate.CODE_REGEX)) {
                    ClassRoom classRoom = new ClassRoom(name, path);
                    List<Student> list = new ArrayList<>();
                    classRoom.writeToFileCSV(path, list);
                    manageClass.add(classRoom);
                    setClassChoice();
                    createAlert.showAlert("Notification", "Success", createAlert.INFORMATION);
                } else {
                    createAlert.showAlert("WARNING", "T??n l???p kh??ng h???p l???", createAlert.WARNING);
                }
            }
        }
        className.clear();
        pathName.clear();
    }

    public void deleteClass() throws IOException {
        String name = deleteClass.getText();
        if (manageClass.isExist(name)) {
            showConfirmationDeleteClass(name);
            setClassChoice();
        } else {
            if (deleteClass.getText().equals("")) {
                createAlert.showAlert("Warning", "Nh???p t??n class mu???n x??a v??o ?? b??n c???nh", CreateAlert.WARNING);
            } else {
                createAlert.showAlert("Warning", "Invalid", CreateAlert.WARNING);
            }
        }
        deleteClass.clear();
    }

    public void editClass() throws IOException {
        String name = searchClass.getText();
        String newName = editClassName.getText();
        String newPath = editClassPath.getText();
        if (name.equals("")) {
            createAlert.showAlert("Warning", "??i???n t??n class mu???n s???a v??o ?? b??n c???nh", CreateAlert.WARNING);
        } else {
            if (manageClass.isExist(name)) {
                if (newName.equals("") || newPath.equals("")) {
                    createAlert.showAlert("Warning", "??i???n ?????y ????? th??ng tin m???i v??o ?? b??n d?????i", CreateAlert.WARNING);
                } else {
                    manageClass.edit(manageClass.checkIndex(name), new ClassRoom(newName, newPath));
                    setClassChoice();
                    searchClass.clear();
                    editClassName.clear();
                    editClassPath.clear();
                    createAlert.showAlert("Notification", "Success", CreateAlert.INFORMATION);
                }
            } else {
                createAlert.showAlert("Warning", "Invalid ", CreateAlert.WARNING);
            }
        }

    }

    public void displayAll() throws IOException {
        manageClass.addAll();
        String str = "";
        for (Student student : manageClass.readAll()) {
            str += student + "\n";
        }
        textArea.setText(str);
    }

    public void move() throws IOException {
        String code = movedOnName.getText();
        if (name.equals("")) {
            createAlert.showAlert("Warning", "Nh???p code h???c sinh v??o ?? b??n c???nh", CreateAlert.WARNING);
        } else {
            boolean finded = false;
            for (ClassRoom classRoom : manageClass.getClassRoomList()) {
                for (Student student : classRoom.getStudents()) {
                    if (student.getCode().equals(code)) {
                        finded = true;
                        if (classRoom.getName().equals(movedOnClass.getValue().toString())) {
                            createAlert.showAlert("Warning", "H???c sinh n??y ???? ??? l???p n??y r???i", CreateAlert.WARNING);
                        } else if(!classRoom.getName().equals(movedOnClass.getValue().toString())){
                            manageClass.search(movedOnClass.getValue().toString()).add(student);
                            classRoom.delete(classRoom.checkIndex(code));
                            display();
                            createAlert.showAlert("Notification", "Success", CreateAlert.INFORMATION);
                            movedOnName.clear();
                        }
                        break;
                    }
                }
                if (finded){
                    break;
                }
            }
            if (!finded) {
                createAlert.showAlert("Warning", "Invalid", CreateAlert.WARNING);
            }
        }
    }

    public void displayOfSearch() throws IOException {
        List<Student> list = manageClass.search(classChoice.getValue().toString()).readFileCSV("SearchList.csv");
        String str = "";
        for (Student student : list) {
            str += student.toString() + "\n";
        }
        textArea.setText(str);
    }

    public void clear() {
        name.clear();
        dob.clear();
        address.clear();
        email.clear();
        code.clear();
        gpa.clear();
    }

    public Student createStudent() throws IOException {
        String newName = name.getText();
        if (validate.validateRegex(newName, validate.NAME_REGEX)) {
            String newDob = dob.getText();
            if (validate.validateRegex(newDob, validate.DOB_REGEX)) {
                String newAddress = address.getText();
                if (validate.validateRegex(newAddress, validate.ADDRESS_REGEX)) {
                    String newEmai = email.getText();
                    if (validate.validateRegex(newEmai, validate.EMAIL_REGEX)) {
                        String newGender = choiceGender.getValue().toString();
                        String newCode = code.getText();
                        if (validate.validateRegex(newCode, validate.CODE_REGEX) && !manageClass.search(classChoice.getValue().toString()).isExist(newCode)) {
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

    public void showConfirmationDelete(String code) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Student");
        alert.setHeaderText("B???n c?? ch???c ch???n mu???n x??a h???c sinh n??y ?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
            createAlert.showAlert("Notification", "No suggestion", createAlert.INFORMATION);
        } else if (option.get() == ButtonType.OK) {
            String name = classChoice.getValue().toString();
            manageClass.search(name).delete(manageClass.search(name).checkIndex(code));
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
        code.setPromptText(fieldCode);
        gpa.setPromptText(fieldGpa);
        className.setPromptText("name");
        pathName.setPromptText("example.csv");
        searchClass.setPromptText("edit name");
        editClassName.setPromptText("new name");
        editClassPath.setPromptText("new path");
        deleteClass.setPromptText("name");
        movedOnName.setPromptText("code");
    }

    public void setClassChoice() {
        ObservableList<String> listClass = FXCollections.observableArrayList();
        for (ClassRoom classRoom : manageClass.readFileCSV("ListOfClassName.csv")) {
            listClass.add(classRoom.getName());
        }
        setComboBox(classChoice, listClass);
        setComboBox(movedOnClass, listClass);
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

    public void showConfirmationDeleteClass(String name) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Student");
        alert.setHeaderText("B???n c?? ch???c ch???n mu???n x??a l???p h???c n??y ?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
            createAlert.showAlert("Notification", "No suggestion", createAlert.INFORMATION);
        } else if (option.get() == ButtonType.OK) {
            manageClass.delete(manageClass.checkIndex(name));
            createAlert.showAlert("Notification", "Deleted", createAlert.INFORMATION);
        } else if (option.get() == ButtonType.CANCEL) {
            createAlert.showAlert("Notification", "Canceled", createAlert.INFORMATION);
        }
    }
}



