import java.sql.*;
import java.util.Scanner;

public class OnlineReservationSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/reservation_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static boolean validateUser(String loginID, String password) {
        String query = "SELECT * FROM Users WHERE LoginID = ? AND Password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginID);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean bookTicket(int userID, int trainID, String classType, String dateOfJourney, String fromPlace, String toPlace) {
        String query = "INSERT INTO Reservations (UserID, TrainID, ClassType, DateOfJourney, FromPlace, ToPlace) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, trainID);
            stmt.setString(3, classType);
            stmt.setString(4, dateOfJourney);
            stmt.setString(5, fromPlace);
            stmt.setString(6, toPlace);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean cancelTicket(String pnrNumber) {
        String query = "DELETE FROM Reservations WHERE PNRNumber = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pnrNumber);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void displayReservation(String pnrNumber) {
        String query = "SELECT * FROM Reservations WHERE PNRNumber = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pnrNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Reservation ID: " + rs.getInt("ReservationID"));
                System.out.println("User ID: " + rs.getInt("UserID"));
                System.out.println("Train ID: " + rs.getInt("TrainID"));
                System.out.println("Class Type: " + rs.getString("ClassType"));
                System.out.println("Date of Journey: " + rs.getString("DateOfJourney"));
                System.out.println("From: " + rs.getString("FromPlace"));
                System.out.println("To: " + rs.getString("ToPlace"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Online Reservation System");
        
        // Login
        System.out.print("Enter Login ID: ");
        String loginID = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        if (validateUser(loginID, password)) {
            System.out.println("Login Successful");
            
            // Reservation
            System.out.print("Enter User ID: ");
            int userID = scanner.nextInt();
            System.out.print("Enter Train ID: ");
            int trainID = scanner.nextInt();
            scanner.nextLine();  // consume newline
            System.out.print("Enter Class Type: ");
            String classType = scanner.nextLine();
            System.out.print("Enter Date of Journey (YYYY-MM-DD): ");
            String dateOfJourney = scanner.nextLine();
            System.out.print("Enter From Place: ");
            String fromPlace = scanner.nextLine();
            System.out.print("Enter To Place: ");
            String toPlace = scanner.nextLine();
            
            if (bookTicket(userID, trainID, classType, dateOfJourney, fromPlace, toPlace)) {
                System.out.println("Reservation Successful");
            } else {
                System.out.println("Reservation Failed");
            }
            
            // Cancellation
            System.out.print("Enter PNR Number to Cancel: ");
            String pnrNumber = scanner.nextLine();
            displayReservation(pnrNumber);
            System.out.print("Confirm cancellation (yes/no): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("yes")) {
                if (cancelTicket(pnrNumber)) {
                    System.out.println("Cancellation Successful");
                } else {
                    System.out.println("Cancellation Failed");
                }
            }
        } else {
            System.out.println("Invalid Login Credentials");
        }
        
        scanner.close();
    }
}
