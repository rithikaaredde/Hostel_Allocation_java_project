import java.sql.*;
import java.util.*;

public class Hostel {
    private static final int totalRooms = 10;

    public int allocateRoom(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            for (int i = 1; i <= totalRooms; i++) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM room_allocations WHERE room_no = " + i);
                if (!rs.next()) {
                    PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO room_allocations (room_no, student_username) VALUES (?, ?)");
                    insertStmt.setInt(1, i);
                    insertStmt.setString(2, username);
                    insertStmt.executeUpdate();
                    return i;
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("❌ Error allocating room: " + e.getMessage());
        }
        return -1;
    }

    public void showRoomAllocation() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM room_allocations");

            if (!rs.isBeforeFirst()) {
                System.out.println("No rooms allocated.");
                return;
            }

            while (rs.next()) {
                System.out.println("Room " + rs.getInt("room_no") + " -> " + rs.getString("student_username"));
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("❌ Error fetching allocations: " + e.getMessage());
        }
    }
}

