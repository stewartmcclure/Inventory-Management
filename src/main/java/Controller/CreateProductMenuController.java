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


public class CreateProductMenuController implements Initializable {
    public Button RemovePart;
    /**
     * Parts associated with the product.
     */
    private final ObservableList<Part> ProductPartAssociation = FXCollections.observableArrayList();
    /**
     * Text for the ID.
     */
    public TextField ProductIDField;
    /**
     * Text for name.
     */
    public TextField ProductNameField;
    /**
     * Text for quantity.
     */
    public TextField ProductQuantityField;
    /**
     * Text for price.
     */
    public TextField ProductPriceField;
    /**
     * Text for quantity Max.
     */
    public TextField ProductMaxField;
    /**
     * Text for Min.
     */
    public TextField ProductMinField;
    /**
     * Table of all parts.
     */
    public TableView<Part> AllPartTable;
    /**
     * Part ID column.
     */
    public TableColumn<Part, Integer> PartIDCol;
    /**
     * Part name column.
     */
    public TableColumn<Part, String> PartNameCol;
    /**
     * Part quantity column.
     */
    public TableColumn<Part, Integer> PartQuantityCol;
    /**
     * Part price column.
     */
    public TableColumn<Part, Double> PartPriceCol;
    /**
     * Text for table search.
     */
    public TextField ProductPartText;
    /**
     * view for associated parts.
     */
    public TableView<Part> AssociatedTable;
    /**
     * Part id column.
     */
    public TableColumn<Part, Integer> aPartIDCol;
    /**
     * Part name column.
     */
    public TableColumn<Part, String> aPartNameCol;
    /**
     * Part Quantity column.
     */
    public TableColumn<Part, Integer> aPartQuantityCol;
    /**
     * Part price column.
     */
    public TableColumn<Part, Double> aPartPriceCol;

    /**
     * displays cancel confirmation.
     * @param actionEvent Cancel button press.
     * @throws IOException From FXMLLoader.
     */
    public void aProductCancel(ActionEvent actionEvent) throws IOException {
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
     * search using part search inputs.
     */
    public void ProductPartSearch() {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        String searchText = ProductPartText.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getID()).contains(searchText) || part.getName().contains(searchText)) {
                foundPart.add(part);
            }
        }
        AllPartTable.setItems(foundPart);
        if (foundPart.size() == 0) {
            alertMessages(2);
        }
    }
    /**
     * Removes the selected part.
     * Gives confirmation or error message.
     */
    public void RemovePartPress() {
        Part partSelected = AssociatedTable.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            alertMessages(5);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Confirmation");
            alert.setContentText("Do you wish to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ProductPartAssociation.remove(partSelected);
                AssociatedTable.setItems(ProductPartAssociation);
            }
        }
    }
    /**
     * Saves changes.
     * Input validation.
     * @param actionEvent Save button pressed.
     */
    public void SaveProductPress(ActionEvent actionEvent) {
        try {
            int ID = 0;
            String name = ProductNameField.getText();
            int quantity = Integer.parseInt(ProductQuantityField.getText());
            double cost = Double.parseDouble(ProductPriceField.getText());
            int min = Integer.parseInt(ProductMinField.getText());
            int max = Integer.parseInt(ProductMaxField.getText());

            if (name.isEmpty()) {
                alertMessages(6);
            }
            else {
                if ((min < max && min > 0) && (quantity >= min && quantity <= max)) {
                    Product newProduct = new Product(ID, name, cost, quantity, min, max);
                    for (Part part : ProductPartAssociation) {
                        newProduct.addAssociatedPart(part);
                    }
                    newProduct.setID(Inventory.newProductID());
                    Inventory.addProduct(newProduct);

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
     * Parts table populates.
     * @param url finds paths for root, null if not known.
     * @param resourceBundle localizes the object, null if the root was not local.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartQuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        AllPartTable.setItems(Inventory.getAllParts());

        aPartIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        aPartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        aPartQuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        aPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

    }
    /**
     * Adds part to associated parts.
     * error if no part is in selection.
     */
    public void AddToAssociate() {
        Part partSelected = AllPartTable.getSelectionModel().getSelectedItem();
        if (partSelected == null) {
            alertMessages(5);
        }
        else {
            ProductPartAssociation.add(partSelected);
            AssociatedTable.setItems(ProductPartAssociation);
        }
    }
    /**
     * displays alert messages.
     * @param alertType Alert message selection.
     */
    private void alertMessages(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
            }
            case 2 -> {
                alertInfo.setTitle("Alert!");
                alertInfo.setHeaderText("Part not found.");
                alertInfo.showAndWait();
            }
            case 3 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid Min Input.");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid Quantity input.");
                alert.setContentText("Quantity must be a number equal to or between Min and Max.");
                alert.showAndWait();
            }
            case 5 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("No part Selected.");
                alert.showAndWait();
            }
            case 6 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Name Input is Empty");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
            }
        }
    }
    /**
     * Displays all parts if part search is blank.
     */
    public void PartTableBlank() {
        if (ProductPartText.getText().isEmpty()) {
            AllPartTable.setItems(Inventory.getAllParts());
        }
    }
}