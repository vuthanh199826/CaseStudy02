package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.ManageClass;
import sample.model.ClassRoom;
import sample.model.CreateAlert;
import sample.model.Student;
import sample.model.Validate;
import sample.service.WorkWithFile;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller implements WorkWithFile<String> {
    @FXML
    TextField name;
    @FXML
    TextField dob;
    @FXML
    TextField address;
    @FXML
    TextField email;
//    @FXML
//    TextField gender;
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
    ComboBox min;
    @FXML
    ComboBox max;
    @FXML
    TextArea textArea;
//
//    ClassRoom classRoom = new ClassRoom("C0321K1", "Students.csv");
//
//    ClassRoom classRoom1 = new ClassRoom("C0321K2", "Students2.csv");
    Validate validate = new Validate();
    CreateAlert createAlert = new CreateAlert();
    ManageClass manageClass = new ManageClass();




    public Controller()  throws IOException {
    }

    public void initialize() throws IOException {
        ObservableList<String> list = FXCollections.observableArrayList("Sort by name", "Sort by code", "Sort by GPA");
        setComboBox(comboBox, list);
        ObservableList<String> listSearchChoice = FXCollections.observableArrayList("Search by name", "Search by code", "Search by GPA");
        setComboBox(searchChoice, listSearchChoice);
        ObservableList<String> listMin = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        setComboBox(min, listMin);
        ObservableList<String> listGender = FXCollections.observableArrayList("Male","Female");
setComboBox(choiceGender,listGender);
        ObservableList<String> listMax = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        setComboBox(max, listMax);
setClassChoice();


        setPlaceHolderOfStudent("name", "dd/mm/yyyy", "address", "example@gmail.com", "true/false", "code", "0.0 - 10.0");
        setPlaceHolderOfSearch();
//        manageClass.add(classRoom);
//        manageClass.add(classRoom1);
        System.out.println(manageClass);

    }


    public void add() throws IOException {
        String name = classChoice.getValue().toString();
        Student student = createStudent();
        if (student != null) {
//            classRoom.add(student);
            manageClass.search(name).add(student);
            clear();
            display();
            createAlert.showAlert("Notification", "Successfully", createAlert.INFORMATION);
        } else {
            createAlert.showAlert("Error", "Vui lòng điền đầy đủ các trường thông tin bên trái", createAlert.ERROR);
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
            textArea.setText(str);
        }catch (Exception e){
            textArea.setText("Empty");
        }

    }

    public void delete() throws IOException {
        clear();
        String name = classChoice.getValue().toString();
        String code = delete.getText();
//        if (classRoom.isExist(code)) {
        if (manageClass.search(name).isExist(code)) {
            System.out.println("exist");
            showConfirmationDelete(code);
//                    classRoom.delete(classRoom.checkIndex(code));
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
//        if (validate.validateRegex(code, validate.CODE_REGEX) && classRoom.isExist(code)) {
        if (validate.validateRegex(code, validate.CODE_REGEX) && manageClass.search(name).isExist(code)) {
            Student student = createStudent();
            if (student != null) {
//                classRoom.edit(classRoom.checkIndex(code), student);
                manageClass.search(name).edit(manageClass.search(name).checkIndex(code), student);
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
//            classRoom.sort(option);
            manageClass.search(name).sort(option);
            display();
        }
    }

    public void search() throws IOException {
        if (searchChoice.getValue().equals("Search by name")) {
            if (inputSearch.getText().equals("")) {
                createAlert.showAlert("WARNING", "Vui lòng điền tên học sinh cần tìm vào ô bên dưới", createAlert.WARNING);
            } else {
                String name = inputSearch.getText();
//                List list = new ArrayList(classRoom.searchByName(name));
//                classRoom.writeToFileCSV("SearchList.csv", list);
                List list = new ArrayList(manageClass.search(classChoice.getValue().toString()).searchByName(name));
                manageClass.search(classChoice.getValue().toString()).writeToFileCSV("SearchList.csv", list);
                if (list.size() == 0) {
                    createAlert.showAlert("WARNING", "Không tìm thấy tên học sinh này", createAlert.WARNING);
                    inputSearch.clear();
                } else {
                    displayOfSearch();
                }
            }

        } else if (searchChoice.getValue().equals("Search by code")) {
            if (inputSearch.getText().equals("")) {
                createAlert.showAlert("WARNING", "Vui lòng điền code học sinh cần tìm vào ô bên dưới", createAlert.WARNING);
            } else {
                String code = inputSearch.getText();
//                List list = new ArrayList(classRoom.searchByCode(code));
//                classRoom.writeToFileCSV("SearchList.csv", list);
                List list = new ArrayList(manageClass.search(classChoice.getValue().toString()).searchByName(code));
                manageClass.search(classChoice.getValue().toString()).writeToFileCSV("SearchList.csv", list);
                if (list.size() == 0) {
                    createAlert.showAlert("WARNING", "Không tìm thấy tên học sinh này", createAlert.WARNING);
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
//                List<Student> list = new ArrayList<>(classRoom.searchByGPA(minGPA, maxGPA));
//                classRoom.writeToFileCSV("SearchList.csv", list);
                List list = new ArrayList(manageClass.search(classChoice.getValue().toString()).searchByGPA(minGPA,maxGPA));
                manageClass.search(classChoice.getValue().toString()).writeToFileCSV("SearchList.csv", list);
                if (list.size() == 0) {
                    createAlert.showAlert("WARNING", "Không tìm thấy học sinh trong thang điểm này", createAlert.WARNING);
                } else {
                    displayOfSearch();
                }
            } else if (minGPA > maxGPA) {
                createAlert.showAlert("WARNING", "điểm nhỏ nhất phải nhỏ hơn điểm lớn nhất", createAlert.WARNING);
            }
        }
    }


    public void createNewClass() throws IOException {
        String name = className.getText();
        String path = pathName.getText();
        if (name.equals("") || path.equals("")) {
            createAlert.showAlert("WARNING", "Vui lòng nhập đầy đủ thông tin vào ô bên cạnh", createAlert.WARNING);
        } else {
            if (manageClass.isExist(name)) {
                createAlert.showAlert("WARNING", "Tên lớp đã tồn tại", createAlert.WARNING);
            } else {
                if (validate.validateRegex(name, validate.CODE_REGEX)) {
                    ClassRoom classRoom = new ClassRoom(name,path);
                    classRoom.writeToFileCSV(path,classRoom.getStudents());
                    manageClass.add(classRoom);
                    setClassChoice();
//                    listClass.add(name);
//                    setComboBox(classChoice, listClass);
                    createAlert.showAlert("Notification", "Success", createAlert.INFORMATION);
                } else {
                    createAlert.showAlert("WARNING", "Tên lớp không hợp lệ", createAlert.WARNING);
                }
            }
        }


    }

    public void deleteClass() throws IOException {
        String name = deleteClass.getText();
        if(manageClass.isExist(name)){
            manageClass.delete(manageClass.checkIndex(name));
            setClassChoice();
            createAlert.showAlert("Notification","Success",CreateAlert.INFORMATION);
        }
    }

    public void displayOfSearch() throws IOException {
//        List<Student> list = classRoom.readFileCSV("SearchList.csv");
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
//        gender.clear();
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
//                        if (validate.validateRegex(gender.getText(), validate.GENDER_REGEX)) {
//                            boolean newGender = Boolean.parseBoolean(gender.getText());
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
//                        } else {
//                            gender.clear();
//                        }

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
        alert.setHeaderText("Bạn có chắc chắn muốn xóa học sinh này ?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
            createAlert.showAlert("Notification", "No suggestion", createAlert.INFORMATION);
        } else if (option.get() == ButtonType.OK) {
            String name = classChoice.getValue().toString();
            manageClass.search(name).delete(manageClass.search(name).checkIndex(code));
//            classRoom.delete(classRoom.checkIndex(code));
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
//        gender.setPromptText(fieldGender);
        code.setPromptText(fieldCode);
        gpa.setPromptText(fieldGpa);

    }
    public void setClassChoice() throws IOException {
        ObservableList<String> listClass = FXCollections.observableArrayList();
        for (ClassRoom classRoom:manageClass.readFileCSV("ListOfClassName.csv")){
            listClass.add(classRoom.getName());
        }
        setComboBox(classChoice, listClass);
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


    @Override
    public void writeToFileCSV(String path, List<String> list) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (String str: list) {
            bufferedWriter.write(str + "\n");
        }
        bufferedWriter.close();
        fileWriter.close();
    }

    @Override
    public void addToFileCSV(String path, String e) throws IOException {

    }

    @Override
    public List<String> readFileCSV(String path) throws IOException {
        List<String> list = new ArrayList<>();
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] arr = line.split(",");
            list.add(arr[0]);
        }
        return list;
    }
}
