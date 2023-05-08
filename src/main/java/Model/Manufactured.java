package Model;

public class Manufactured extends Part {
    /**
     * Part machine ID for manufactured part.
     */
    private int MachineID;

    /**
     * Constructor for manufactured part.
     * @param ID Part ID.
     * @param name Part name.
     * @param price Part price.
     * @param quantity Part quantity.
     * @param min Part inventory min.
     * @param max Part inventory max.
     * @param MachineID InHouse Part machine ID.
     */
    public Manufactured(int ID, String name, double price, int quantity, int min, int max, int MachineID) {
        super(ID, name, price, quantity, min, max);
        this.MachineID = MachineID;
    }

    /**
     * used to get Machine ID of Manufactured part.
     * @return Machine ID.
     */
    public int getMachineID() {
        return MachineID;
    }

    /**
     * used to set Machine ID of Manufactured part.
     * @param MachineID Machine ID.
     */
    public void setMachineID(int MachineID) {
        this.MachineID = MachineID;

    }
}