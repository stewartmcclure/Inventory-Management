package Model;

public class Outsourced extends Part {
    /**
     * Outsourced part manufacturer.
     */
    private String manufacturer;

    /**
     * Constructor for new Outsourced parts.
     * @param =ID Part ID.
     * @param name Part name.
     * @param price Part price.
     * @param quantity Part inventory.
     * @param min Part inventory minimum.
     * @param max Part inventory max.
     * @param manufacturer part company name for outsourced part.
     */
    public Outsourced(int ID, String name, double price, int quantity, int min, int max, String manufacturer) {
        super(ID, name, price, quantity, min, max);
        this.manufacturer = manufacturer;
    }

    /**
     * Method for getting company name of outsourced part.
     * @return returns company name.
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Method for setting company name of outsourced part.
     * @param manufacturer  to be set.
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}