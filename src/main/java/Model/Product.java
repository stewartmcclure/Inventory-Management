package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    /**
     * List of products associated Parts.
     */
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * ID of product.
     */
    private int ID;
    /**
     * Name of product.
     */
    private String name;
    /**
     * Product price.
     */
    private double price;
    /**
     * Product quantity.
     */
    private int Quantity;
    /**
     * product quantity min.
     */
    private int min;
    /**
     * product quantity max.
     */
    private int max;

    /**
     * Constructor for new Product.
     * @param ID Product ID.
     * @param name Product name.
     * @param price Product Price.
     * @param Quantity Product quantity.
     * @param min Product min.
     * @param max Product max.
     */
    public Product(int ID, String name, double price, int Quantity, int min, int max) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.Quantity = Quantity;
        this.min = min;
        this.max = max;
    }

    /**
     * gets product ID.
     * @return Returns product ID.
     */
    public int getID() {
        return ID;
    }

    /**
     * sets product ID.
     * @param ID product ID.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * gets product name.
     * @return returns product name.
     */
    public String getName() {
        return name;
    }

    /**
     * sets product name.
     * @param name Name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets product price.
     * @return Returns product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets Product price.
     * @param price Product price / cost.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * gets product quantity.
     * @return returns product quantity.
     */
    public int getQuantity() {
        return Quantity;
    }

    /**
     * sets product quantity.
     * @param Quantity Product quantity.
     */
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    /**
     * gets product minimum.
     * @return Product min.
     */
    public int getMin() {
        return min;
    }

    /**
     * sets product min.
     * @param min Product min.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * gets product max.
     * @return returns product max.
     */
    public int getMax() {
        return max;
    }

    /**
     * sets product max.
     * @param max Product max.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * adds part to Product associated parts.
     * @param part part to be added.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);

    }

    /**
     * deletes associated part.
     * @param selectedAssociatedPart selected Part associated with Product.
     * @return Returns true if part is removed and false if not.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        if(associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * gets all associated parts to a product.
     * @return returns all associated parts to a product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;

    }
}