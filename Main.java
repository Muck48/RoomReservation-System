import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Room[] rooms = {
            new Room("101", "Single", 1, 3500, true),
            new Room("102", "Double", 2, 4000, true),
            new Room("103", "Single", 1, 3600, true),
            new Room("104", "Deluxe", 3, 5000, true),
            new Room("105", "Suite", 4, 6500, true)
        };

        ReservationManager manager = new ReservationManager();
        manager.loadReservations(rooms);

        boolean anotherUser = true;

        while (anotherUser) {
            // ==== รับข้อมูลผู้ใช้ ====
            System.out.println("\n-------------------------");
            System.out.println("     USER INFORMATION");
            System.out.println("-------------------------");

            System.out.print("Enter your first name : ");
            String fname = scanner.nextLine();

            System.out.print("Enter your last name  : ");
            String lname = scanner.nextLine();

            System.out.print("Enter your username   : ");
            String uname = scanner.nextLine();

            User user = new User(fname, lname, uname);
            System.out.println("\nWelcome, " + user.displayDetails());

            // ==== แสดงตารางห้อง ====
            System.out.println("\nRoom List:");
            System.out.printf("%-5s %-10s %-10s %-10s %-12s %-10s\n", 
                "No.", "Room", "Type", "Capacity", "Rent (Baht)", "Status");
            System.out.println("------------------------------------------------------------");

            for (int i = 0; i < rooms.length; i++) {
                Room r = rooms[i];
                String status = r.isAvailable() ? "Available" : "Reserved";
                System.out.printf("%-5d %-10s %-10s %-10d %-12.2f %-10s\n",
                    (i + 1), r.getRoomNumber(), r.getType(), r.getCapacity(), r.getRent(), status);
            }

            // ==== เลือกห้อง ====
            int choice = -1;
            while (true) {
                System.out.print("\nChoose a room number (1-" + rooms.length + "): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 1 && choice <= rooms.length) {
                        break;
                    } else {
                        System.out.println("Error: Invalid room number.");
                    }
                } else {
                    System.out.println("Error: Please enter a number.");
                    scanner.nextLine();
                }
            }

            Room selected = rooms[choice - 1];

            if (selected.isAvailable()) {
                // ==== จำนวนวัน ====
                int numDays = -1;
                while (true) {
                    System.out.print("Enter number of days to rent: ");
                    if (scanner.hasNextInt()) {
                        numDays = scanner.nextInt();
                        scanner.nextLine();
                        if (numDays > 0) {
                            break;
                        } else {
                            System.out.println("Error: Must be more than 0.");
                        }
                    } else {
                        System.out.println("Error: Please enter a valid number.");
                        scanner.nextLine();
                    }
                }

                double total = selected.getRent() * numDays;

                // ==== สรุปการจอง ====
                System.out.println();
                System.out.println(selected.displayDetails());
                System.out.println("Days: " + numDays);
                System.out.println("Total: " + total + " Baht");
                System.out.println();

                // ==== ยืนยันการจอง ====
                String confirm;
                while (true) {
                    System.out.print("Confirm reservation? (yes/no): ");
                    confirm = scanner.nextLine().trim().toLowerCase();
                    if (confirm.equals("yes") || confirm.equals("no")) {
                        break;
                    } else {
                        System.out.println("Error: Please enter 'yes' or 'no'.");
                    }
                }

                if (confirm.equals("yes")) {
                    selected.reserve();
                    System.out.println("Room " + selected.getRoomNumber() + " reserved successfully.");

                    // ==== เขียนลงไฟล์ reservations.txt ====
                    try {
                        FileWriter writer = new FileWriter("reservations.txt", true);
                        writer.write("----------------------------------------\n");
                        writer.write("User:       " + user.displayDetails() + "\n");
                        writer.write("Room:       " + selected.getRoomNumber() + " (" + selected.getType() + ")\n");
                        writer.write("Capacity:   " + selected.getCapacity() + " people\n");
                        writer.write("Rent/day:   " + String.format("%.2f", selected.getRent()) + " Baht\n");
                        writer.write("Days:       " + numDays + "\n");
                        writer.write("Total:      " + String.format("%.2f", total) + " Baht\n");
                        writer.write("----------------------------------------\n\n");
                        writer.close();
                        System.out.println("Info saved to reservations.txt");
                    } catch (IOException e) {
                        System.out.println("File error: " + e.getMessage());
                    }
                } else {
                    System.out.println("Reservation cancelled.");
                }

            } else {
                System.out.println("That room is already reserved.");
            }

            // ==== ถามว่ามีผู้ใช้อื่นไหม ====
            String response;
            while (true) {
                System.out.print("\nIs there another user who wants to reserve a room? (yes/no): ");
                response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes") || response.equals("no")) {
                    break;
                } else {
                    System.out.println("Error: Please enter 'yes' or 'no'.");
                }
            }

            anotherUser = response.equals("yes");
        }

        System.out.println("Reservation system ended.");
        scanner.close();
    }
}
