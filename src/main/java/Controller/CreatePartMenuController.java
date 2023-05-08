package Controller;

import Model.Inventory;
import Model.Manufactured;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class CreatePartMenuController {
    /**
     * button for the Manufactured parts.
     */
    public RadioButton ManufacturedRadio;
    /**
     * button for outsourced parts.
     */
    public RadioButton OutsourcedRadio;
    /**
     * Text for the part ID.
     */
    public TextField PartIdField;
    /**
     * Text for name.
     */
    public TextField PartNameField;
    /**
     * Text for quantity.
     */
    public TextField PartQuantityField;
    /**
     * Text for price.
     */
    public TextField PartPriceField;
    /**
     * Text for max.
     */
    public TextField PartMaxField;
    /**
     * Text for Machine ID or Manufacturer.
     */
    public TextField PartMachineCompany;
    /**
     * Text for minimum.
     */
    public TextField PartMinField;
    /**
     * Label for MachineID/Manufacturer.
     */
    public Label MachineCompanyLabel;
    /**
     * Toggle manufactured/outsourced parts.
     */
    public ToggleGroup PartTypeToggle;


    /**
     * Delete button.
     * @param actionEvent Cancel button.
     * @throws IOException From FXMLLoader.
     */
    public void CancelButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert!");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     * Saves.
     * Input validation.
     * @param actionEvent Save button.
     */
    public void SaveButton(ActionEvent actionEvent) {
        try {
            int ID;
            String name = PartNameField.getText();
            double price = Double.parseDouble(PartPriceField.getText());
            int quantity = Integer.parseInt(PartQuantityField.getText());
            int min = Integer.parseInt(PartMinField.getText());
            int max = Integer.parseInt(PartMaxField.getText());
            String CompanyName;
            int MachineId;
            boolean PartAdded = false;

            if (name.isEmpty()) {
                alertMessages(1);
            } else {
                if ((min < max && min > 0) && (quantity >= min && quantity <= max)) {
                    if (ManufacturedRadio.isSelected()) {
                        try {
                            ID = Inventory.newPartID();
                            MachineId = Integer.parseInt(PartMachineCompany.getText());
                            Manufactured newManufacturedPart = new Manufactured(ID, name, price, quantity, min, max, MachineId);
                            Inventory.addPart(newManufacturedPart);
                            PartAdded = true;
                        } catch (Exception e) {
                            alertMessages(4);
                        }
                    }
                    if (OutsourcedRadio.isSelected()) {
                        ID = Inventory.newPartID();
                        CompanyName = PartMachineCompany.getText();
                        Outsourced newOutsourcedPart = new Outsourced(ID, name, price, quantity, min, max, CompanyName);
                        Inventory.addPart(newOutsourcedPart);
                        PartAdded = true;
                    }
                    if (PartAdded) {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
                if (min >= max || min <= 0) {
                    alertMessages(3);
                }
                if (quantity < min ||  quantity > max) {
                    alertMessages(2);
                }
            }
        } catch (Exception e) {
            alertMessages(5);
        }
    }
    /**
     * Sets Manufacturer/Machine ID label to "Machine ID".
     */
    public void ManufacturedSelect (){
        MachineCompanyLabel.setText("Machine ID");

    }
    /**
     * Sets Manufacturer/Machine ID label to "Manufacturer".
     */
    public void OutsourcedSelect (){
        MachineCompanyLabel.setText("Manufacturer");

    }
    /**
     * displays alert messages.
     * @param alertType Alert cases selection.
     */
    private void alertMessages (int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid Part Name.");
                alert.setContentText("Part Name Field is empty.");
                alert.showAndWait();
            }
            case 2 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid Inventory Input.");
                alert.setContentText("Inventory must be an integer equal to or between Min and Max.");
                alert.showAndWait();
            }
            case 3 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("Minimum input must be greater than zero and less than Max.");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Alert!");
                alert.setContentText("Machine ID input can only contain numbers.");
                alert.showAndWait();
            }
            case 5 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Alert!");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
            }
        }
    }
}