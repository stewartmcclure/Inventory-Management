package Controller;

import Model.Inventory;
import Model.Manufactured;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


public class ModifyPartController implements Initializable {
    /**
     * Radio button for the manufactured parts.
     */
    public RadioButton ManufacturedRadio;
    /**
     * toggles between manufactured/outsourced.
     */
    public ToggleGroup partTypeToggle;
    /**
     * Radio button for the outsourced parts.
     */
    public RadioButton outsourcedRadio;
    /**
     * Text for part ID.
     */
    public TextField partIdField;
    /**
     * Text for part name.
     */
    public TextField partNameField;
    /**
     * Text for part quantity.
     */
    public TextField partQuantityField;
    /**
     * Text for part price.
     */
    public TextField partPriceField;
    /**
     * Text for part max.
     */
    public TextField partMaxField;
    /**
     * Text for part Machine ID/Manufacturer.
     */
    public TextField partMachine;
    /**
     * Text for part min.
     */
    public TextField partMinField;
    /**
     * Label for Manufacturer/Machine ID.
     */
    public Label machineManufacturerLabel;
    /**
     * selected part.
     */
    private Part partSelected;

    /**
     * inputs data from the selected part.
     * @param url resolves paths for root, null if not known.
     * @param resourceBundle localizes the root , null if the root was not local.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partSelected = MainMenuController.getPartToModify();
        if (partSelected instanceof Manufactured) {
            machineManufacturerLabel.setText("Machine ID");
            ManufacturedRadio.setSelected(true);
            partMachine.setText(String.valueOf(((Manufactured) partSelected).getMachineID()));
        }
        if (partSelected instanceof Outsourced) {
            machineManufacturerLabel.setText("Manufacturer");
            outsourcedRadio.setSelected(true);
            partMachine.setText(((Outsourced) partSelected).getManufacturer());
        }

        partIdField.setText(String.valueOf(partSelected.getID()));
        partNameField.setText(partSelected.getName());
        partQuantityField.setText(String.valueOf(partSelected.getQuantity()));
        partPriceField.setText(String.valueOf(partSelected.getPrice()));
        partMinField.setText(String.valueOf(partSelected.getMin()));
        partMaxField.setText(String.valueOf(partSelected.getMax()));
    }

    /**
     * Delete button.
     * @param actionEvent Cancel button.
     * @throws IOException From FXMLLoader.
     */
    public void mPartCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert!");
        alert.setContentText("Do you wish to cancel?");
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
     * Sets Manufacturer/Machine ID label to "Machine ID".
     */
    public void ManufacturedSelect() { machineManufacturerLabel.setText("Machine ID"); }

    /**
     * Sets Manufacturer/Machine ID label to "Manufacturer".
     */
    public void outsourcedSelect() {
        machineManufacturerLabel.setText("Manufacturer");
    }

    /**
     * Saves changes.
     * Input validation.
     * @param actionEvent Save button.
     */
    public void mPartSave(ActionEvent actionEvent) {
        try {
            int id = partSelected.getID();
            String name = partNameField.getText();
            double cost = Double.parseDouble(partPriceField.getText());
            int quantity = Integer.parseInt(partQuantityField.getText());
            int min = Integer.parseInt(partMinField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            boolean partAdded = false;
            String companyName;
            int machineId;

            if ((min < max && min > 0) && (quantity >= min && quantity <= max)) {
                if (ManufacturedRadio.isSelected()) {
                    try {
                        machineId = Integer.parseInt(partMachine.getText());
                        Manufactured newManufactured = new Manufactured(id, name, cost, quantity, min, max, machineId);
                        Inventory.addPart(newManufactured);
                        partAdded = true;
                    } catch (Exception e) {
                        alertMessages(2);
                    }
                }
                if (outsourcedRadio.isSelected()) {
                    companyName = partMachine.getText();
                    if (companyName.isBlank()) {
                        alertMessages(1);
                    }
                    else {
                        Outsourced newOutsourced = new Outsourced(id, name, cost, quantity, min, max, companyName);
                        Inventory.addPart(newOutsourced);
                        partAdded = true;
                    }
                }
                if (partAdded) {
                    Inventory.deletePart(partSelected);

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
            if (quantity < min || quantity > max) {
                alertMessages(4);
            }
        } catch (Exception e) {
            alertMessages(1);
        }
    }

    /**
     * displays alert messages.
     * @param alertType Alert case selection.
     */
    private void alertMessages(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Alert!");
                alert.setContentText("Blank fields and/or invalid values in Input.");
                alert.showAndWait();
            }
            case 2 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Alert!");
                alert.setContentText("The Machine ID Input can only contain numbers.");
                alert.showAndWait();
            }
            case 3 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Alert!");
                alert.setContentText("Min input should be greater than zero and less than Max.");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Alert!");
                alert.setContentText("Quantity input must be equal to or between min and max.");
                alert.showAndWait();
            }
        }
    }

}