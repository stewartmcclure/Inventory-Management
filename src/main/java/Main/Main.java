package Main;

import Model.Inventory;
import Model.Manufactured;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;
/**
 * Inventory application.
 * ERROR ADDRESSED -- Project structure errors led to failed compilation.
 * ERROR ADDRESSED -- Text Fields and Label size adjustments for better visual.
 * FUTURE ENHANCEMENT -- Add a price total for parts associated with products.
 * FUTURE ENHANCEMENT-- Automatically find the number of products that could be assembled with current part inventory.
 */
public class Main extends Application {
    /**
     * loads the main screen.
     *
     * @param primaryStage main stage of the application.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();





    }

    /**
     * adds test data.
     */
    private static void addTest() {
        Manufactured test1 = new Manufactured(21, "Wheel", 12.15, 3, 1, 15, 23);
        Manufactured test2 = new Manufactured(33, "Tire", 205.00, 5, 1, 25, 23);
        Outsourced test3 = new Outsourced(58, "Chain", 19.50, 4, 1, 30, "GE");
        Inventory.addPart(test1);
        Inventory.addPart(test2);
        Inventory.addPart(test3);
        Product test4 = new Product(31,"Tricycle", 799.89, 7, 1, 20);
        test4.addAssociatedPart(test1);
        test4.addAssociatedPart(test2);
        Inventory.addProduct(test4);
    }

    /**
     *loads test data.
     * @param args arguments
     */
    public static void main(String[] args) {
        // Importing test data function
        addTest();

        launch(args);
    }
}