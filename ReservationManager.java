import java.io.*;

public class ReservationManager {

    public void loadReservations(Room[] rooms) {
        File file = new File("reservations.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (Room room : rooms) {
                    if (line.contains(room.getRoomNumber())) {
                        room.reserve();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveReservation(String username, String roomNumber) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt", true))) {
            writer.write(username + " reserved room " + roomNumber + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method เดิม เช่น displayAvailableRooms(...) ยังเก็บไว้ได้ปกติ
}
