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


public class MainMenuController implements Initializable {
    /**
     * Part ID column.
     */
    public TableColumn<Part, Integer> PartIDCol;
    /**
     * Part name column
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
     * Product ID column.
     */
    public TableColumn<Product, Integer> ProductIDCol;
    /**
     * Product name column.
     */
    public TableColumn<Product, String> ProductNameCol;
    /**
     * Product quantity column.
     */
    public TableColumn<Product, Integer> ProductQuantityCol;
    /**
     * Product price column.
     */
    public TableColumn<Product, Double> ProductPriceCol;
    /**
     * table view.
     */
    public TableView<Product> MainProductTable;
    /**
     * table view.
     */
    public TableView<Part> MainPartTable;
    /**
     * Part search text.
     */
    public TextField PartSearchTxt;
    /**
     * Product search text.
     */
    public TextField ProductSearchTxt;

    /**
     * Returns the part.
     * @return part object.
     */
    public static Part getPartToModify() {
        return PartToModify;
    }

    /**
     * Returns the product.
     * @return product object.
     */
    public static Product getProductToModify() {return ProductToModify;}

    /**
     * Part selected.
     */
    private static Part PartToModify;
    /**
     * Product selected.
     */
    private static Product ProductToModify;

    /**
     * populates tables.
     * @param url resolves paths for root, null if not known.
     * @param resourceBundle localizes the root, null if the root is not local.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainProductTable.setItems(Inventory.getAllProducts());
        ProductIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ProductQuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        ProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        MainPartTable.setItems(Inventory.getAllParts());
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartQuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    /**
     * Loads CreatePartMenu.
     * @param actionEvent Add part button.
     * @throws IOException From FXMLLoader.
     */
    public void AddPartHandler(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/CreatePartMenu.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads ModifyPartMenu.
     * @param actionEvent Modify part button.
     * @throws IOException From FXMLLoader.
     */
    public void ModifyPartHandler(ActionEvent actionEvent) throws IOException {
        PartToModify = MainPartTable.getSelectionModel().getSelectedItem();
        if (PartToModify == null) {
            AlertMessages(4);
        }
        else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ModifyPartMenu.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Loads CreateProductMenu
     * @param actionEvent Add product button.
     * @throws IOException From FXMLLoader.
     */
    public void AddProductHandler(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/CreateProductMenu.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads ModifyProduct.
     * @param actionEvent Modify product button.
     * @throws IOException From FXMLLoader.
     */
    public void ModifyProductHandler(ActionEvent actionEvent) throws IOException {
        ProductToModify = MainProductTable.getSelectionModel().getSelectedItem();
        if (ProductToModify == null) {
            AlertMessages(3);
        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ModifyProductMenu.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * searches using the part search input.
     */
    public void PartSearchBut() {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partFound = FXCollections.observableArrayList();
        String searchText = PartSearchTxt.getText();
        for (Part part : allParts) {
            if (String.valueOf(part.getID()).contains(searchText) || part.getName().contains(searchText)) {
                partFound.add(part);
            }
        }
        MainPartTable.setItems(partFound);
        if(partFound.size() == 0) {
            AlertMessages(1);
        }
    }

    /**
     * searches using the product search input.
     */
    public void ProductSearchBut() {

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productFound = FXCollections.observableArrayList();
        String searchText = ProductSearchTxt.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getID()).contains(searchText) || product.getName().contains(searchText)) {
                productFound.add(product);
            }
        }
        MainProductTable.setItems(productFound);
        if(productFound.size() == 0) {
            AlertMessages(2);
        }
    }

    /**
     * displays alert messages.
     * @param AlertType Alert message selection.
     */
    private void AlertMessages(int AlertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (AlertType) {
            case 1 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Alert!");
                alert.setContentText("Part Input not found");
                alert.showAndWait();
            }
            case 2 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Alert!");
                alert.setContentText("Product Input not found");
                alert.showAndWait();
            }
            case 3 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Alert!");
                alert.setContentText("No product was selected");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Alert!");
                alert.setContentText("No part was selected");
                alert.showAndWait();
            }
            case 5 -> {
                alert.setTitle("Error!");
                alert.setHeaderText("Alert!");
                alert.setContentText("Cannot remove a product with parts associated");
                alert.showAndWait();
            }
        }
    }

    /**
     * Exit.
     */
    public void MainExitHandler() {
        System.exit(0);
    }

    /**
     * Delete part.
     */
    public void DeletePartHandler() {
        Part partSelected = MainPartTable.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            AlertMessages(4);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Are you sure you wish to delete?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(partSelected);
            }
        }
    }

    /**
     * Delete product.
     * Displays confirmation or error message
     * stops deletion if the selected product has any associated parts.
     * @param actionEvent Product delete button.
     */
    public void DeleteProductHandler(ActionEvent actionEvent) {
        Product productSelected = MainProductTable.getSelectionModel().getSelectedItem();

        if (productSelected == null) {
            AlertMessages(3);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Are you sure you wish to delete?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> partsAssociated = productSelected.getAllAssociatedParts();
                if (partsAssociated.size() >= 1) {
                    AlertMessages(5);
                }
                else {
                    Inventory.deleteProduct(productSelected);
                }
            }
        }
    }

    /**
     * Displays all parts if search input is blank.
     * @param keyEvent Part search.
     */
    public void PartSearchBlank(ActionEvent keyEvent) {
        if (PartSearchTxt.getText().isEmpty()) {
            MainPartTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Displays all products if search input is blank.
     * @param keyEvent Product search.
     */
    public void ProductSearchBlank(ActionEvent keyEvent) {
        if (ProductSearchTxt.getText().isEmpty()) {
            MainProductTable.setItems(Inventory.getAllProducts());
        }
    }
}