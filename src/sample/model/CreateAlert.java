package sample.model;

import javafx.scene.control.Alert;


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

}
