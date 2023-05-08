package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {
    /**
     *parts associated with the selected product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * price column.
     */
    public TableColumn<Part, Double> aPartPriceColumn;
    /**
     * Quantity column.
     */
    public TableColumn<Part, Integer> aPartQuantityColumn;
    /**
     * name column.
     */
    public TableColumn<Part, String> aPartNameColumn;
    /**
     * ID column.
     */
    public TableColumn<Part, Integer> aPartIdColumn;
    /**
     * Associated part table.
     */
    public TableView<Part> associatePartTable;
    /**
     * Text for part search.
     */
    public TextField partSearchField;
    /**
     * Product selected.
     */
    Product productSelected;
    /**
     * Price column.
     */
    public TableColumn<Part, Double> partPriceColumn;
    /**
     * Quantity column.
     */
    public TableColumn<Part, Integer> partQuantityColumn;
    /**
     * Name column.
     */
    public TableColumn<Part, String> partNameColumn;
    /**
     * ID column.
     */
    public TableColumn<Part, Integer> partIdColumn;
    /**
     * All parts table.
     */
    public TableView<Part> allPartTable;
    /**
     * Text for product min.
     */
    public TextField productMinField;
    /**
     * Text for product max.
     */
    public TextField productMaxField;
    /**
     * Text for product price.
     */
    public TextField productPriceField;
    /**
     * Text for product quantity.
     */
    public TextField productQuantityField;
    /**
     * Text for product name.
     */
    public TextField productNameField;
    /**
     * Text for product ID.
     */
    public TextField productIdField;

    /**
     * Cancel button.
     * @param actionEvent Cancel button.
     * @throws IOException From FXMLLoader.
     */
    public void ModifyProductCancel(ActionEvent actionEvent) throws IOException {
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
     * text fields populate with select product.
     * All parts table populates with data from all the parts.
     * @param url resolves paths for root, null if not known.
     * @param resourceBundle localizes the root , null if the root was not local.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productSelected = MainMenuController.getProductToModify();
        associatedParts = productSelected.getAllAssociatedParts();

        productIdField.setText(String.valueOf(productSelected.getID()));
        productNameField.setText(productSelected.getName());
        productQuantityField.setText(String.valueOf(productSelected.getQuantity()));
        productPriceField.setText(String.valueOf(productSelected.getPrice()));
        productMinField.setText(String.valueOf(productSelected.getMin()));
        productMaxField.setText(String.valueOf(productSelected.getMax()));

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        allPartTable.setItems(Inventory.getAllParts());

        aPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        aPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        aPartQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        aPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        associatePartTable.setItems(associatedParts);

    }

    /**
     * displays alert messages.
     * @param alertType Alert cases selection.
     */
    private void alertMessages(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Error Adding Product.");
                alert.setContentText("There are blank fields and/or invalid values in input.");
                alert.showAndWait();
            }
            case 2 -> {
                alertInfo.setTitle("Alert!");
                alertInfo.setHeaderText("No part found.");
                alertInfo.showAndWait();
            }
            case 3 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Min Invalid");
                alert.setContentText("Min input should be greater than zero and less than Max.");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Quantity Invalid");
                alert.setContentText("Quantity must be equal to or between min and max.");
                alert.showAndWait();
            }
            case 5 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("No parts selected.");
                alert.showAndWait();
            }
            case 6 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name input cannot be empty.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Saves changes.
     * Input validation.
     * @param actionEvent Save button pressed.
     */
    public void saveButtonPress(ActionEvent actionEvent) {
        try {
            int id = productSelected.getID();
            String name = productNameField.getText();
            int quantity = Integer.parseInt(productQuantityField.getText());
            double cost = Double.parseDouble(productPriceField.getText());
            int min = Integer.parseInt(productMinField.getText());
            int max = Integer.parseInt(productMaxField.getText());

            if (name.isEmpty()) {
                alertMessages(6);
            }
            else {
                if ((min < max && min > 0) && (quantity >= min && quantity <= max)) {
                    Product newProduct = new Product(id, name, cost, quantity, min, max);
                    for (Part part : associatedParts) {
                        newProduct.addAssociatedPart(part);
                    }
                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(productSelected);

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
                    Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }

                if (min >= max || min <= 0) {
                    alertMessages(3);
                }
                if (quantity < min || quantity > max) {
                    alertMessages(4);
                }
            }
        } catch (Exception e) {
            alertMessages(1);
        }

    }

    /**
     * Removes the associated part.
     * Gives confirmation alert or error message.
     */
    public void removeAssocPart() {
        Part partSelected = associatePartTable.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            alertMessages(5);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Removal Confirmation");
            alert.setContentText("Do you wish to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(partSelected);
                associatePartTable.setItems(associatedParts);
            }
        }
    }

    /**
     * Adds selected part.
     * ERROR occurs if no part is selection is made
     */
    public void addAssocPart() {
        Part partSelected = allPartTable.getSelectionModel().getSelectedItem();
        if (partSelected == null) {
            alertMessages(5);
        }
        else {
            associatedParts.add(partSelected);
            associatePartTable.setItems(associatedParts);
        }
    }

    /**
     * search using part search input
     */
    public void partSearchText() {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        String searchText = partSearchField.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getID()).contains(searchText) || part.getName().contains(searchText)) {
                foundPart.add(part);
            }
        }
        allPartTable.setItems(foundPart);
        if (foundPart.size() == 0) {
            alertMessages(2);
        }
    }

    /**
     * Displays all parts if part search input is blank.
     */
    public void partSearchBlank() {
        if (partSearchField.getText().isEmpty()) {
            allPartTable.setItems(Inventory.getAllParts());
        }
    }
}