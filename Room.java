public class Room implements Displayable {
    private String roomNumber;
    private String type;
    private int capacity;
    private double rent;
    private boolean isAvailable;

    public Room(String roomNumber, String type, int capacity, double rent, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.rent = rent;
        this.isAvailable = isAvailable;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void reserve() {
        isAvailable = false;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getRent() {
        return rent;
    }

    @Override
    public String displayDetails() {
        return "Room " + roomNumber + " (" + type + ", Capacity: " + capacity +
               ", Rent: " + rent + " Baht, Status: " + (isAvailable ? "Available" : "Reserved") + ")";
    }
}
