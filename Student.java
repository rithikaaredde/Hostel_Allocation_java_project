import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Student {
    public static void studentMenu(String username) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n🏠 Student Panel 🏠");
            System.out.println("1. View Assigned Room");
            System.out.println("2. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAssignedRoom(username);
                    break;
                case 2:
                    running = false;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }

    private static void viewAssignedRoom(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT room_no FROM room_allocations WHERE student_username = '" + username + "'");

            if (rs.next()) {
                System.out.println("✅ Your assigned room is: Room " + rs.getInt("room_no"));
            } else {
                System.out.println("❌ No room assigned.");
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("❌ Error fetching room info: " + e.getMessage());
        }
    }
}

