package Model;
public class Part {
    /**
     * ID.
     */
    private int ID;
    /**
     * name.
     */
    private String name;
    /**
     * price.
     */
    private double price;
    /**
     * Part quantity
     */
    private int Quantity;
    /**
     * quantity minimum.
     */
    private int min;
    /**
     * quantity maximum.
     */
    private int max;

    /**
     * Constructor for Part.
     * @param ID part ID.
     * @param name part Name.
     * @param price part price.
     * @param Quantity part quantity.
     * @param min part inventory min.
     * @param max part inventory max.
     */
    public Part(int ID, String name, double price, int Quantity, int min, int max) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.Quantity = Quantity;
        this.min = min;
        this.max = max;
    }

    /**
     * @return part ID.
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID part id to set.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return part name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return quantity.
     */
    public int getQuantity() {
        return Quantity;
    }

    /**
     * @param Quantity to set.
     */
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    /**
     * @return minimum.
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min to set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return maximum.
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max to set.
     */
    public void setMax(int max) {
        this.max = max;
    }

}