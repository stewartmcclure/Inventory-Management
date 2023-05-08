package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("ALL")
public class Inventory {
    /**
     * List that stores all products.
     */
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * List that stores all parts.
     */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Variable for new Part IDs.
     */
    private static int partID = 0;
    /**
     * Variable for new Product IDs.
     */
    private static int productID = 0;

    /**
     * get new part ID.
     * @return Returns new part ID going up by 1.
     */
    public static int newPartID() {
        partID += 1;
        return partID;
    }

    /**
     * Method get new Product ID.
     * @return Returns new Product ID going up by 1.
     */
    public static int newProductID() {
        productID += 1;
        return productID;
    }

    /**
     * add new part to all parts.
     * @param newPart The part.
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * add new product to all products.
     * @param newProduct The product.
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * lookup part in inventory using ID.
     * @param partId part ID.
     * @return Returns the searched Part.
     */
    public static Part lookupPart(int partID) {

        Part partFound = null;

        for (Part part : allParts) {
            if (part.getID() == partID) {
                partFound = part;
            }
        }
        return partFound;
    }

    /**
     * lookup product in inventory using ID.
     * @param productId the product ID that is found.
     * @return
     */
    public static Product lookupProduct(int productID) {

        Product productFound = null;

        for (Product product : allProducts) {
            if (product.getID() == productID) {
                productFound = product;
            }
        }
        return productFound;
    }

    /**
     * lookup part in inventory using name.
     * @param partName part name searched.
     * @return Returns the Part.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partsFound.add(part);
            }
        }
        return partsFound;
    }
    /**
     * lookup part in inventory using ID.
     * @param productName product name in search.
     * @return Returns the Product.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    /**
     * update part.
     * @param index index for part
     * @param selectedPart The part.
     */
    public static void updatePart (int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * used to update product
     * @param index Product index.
     * @param newProduct The product.
     */
    public static void updateProduct (int index, Product newProduct) {

        allProducts.set(index, newProduct);
    }

    /**
     * used to delete part.
     * @param selectedPart The part.
     * @return true if part deleted and false if not.
     */
    public static boolean deletePart(Part selectedPart) {

        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * used to delete product.
     * @param selectedProduct The part.
     * @return true if product deleted and false if not.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * used to get all products.
     * @return Returns all products.
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    /**
     * used to get all parts.
     * @return all parts.
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }
}