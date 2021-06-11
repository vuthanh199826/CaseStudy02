package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.function.Function;

public class CreateAlert {
    public static final Alert.AlertType INFORMATION = Alert.AlertType.INFORMATION;
    public static final Alert.AlertType WARNING = Alert.AlertType.WARNING;
    public static final Alert.AlertType ERROR = Alert.AlertType.ERROR;
    public CreateAlert() {
    }
    public void showAlert(String title, String message , Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showConfirmation() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete File");
        alert.setHeaderText("Are you sure want to move this file to the Recycle Bin?");
        alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {

        } else if (option.get() == ButtonType.OK) {


        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

}
