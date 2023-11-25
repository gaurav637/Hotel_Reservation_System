package Hotal_Package;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HotalReserVationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "91491026";
    public static void main(String args[])throws ClassNotFoundException,SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage()+"this class not found.....");
        }

        try{
            Connection conn = DriverManager.getConnection(url,username,password);
            while(true){
                System.out.println();
                Scanner sc  = new Scanner(System.in);
                System.out.println("\t\t\t\t\t\t\t\t\t\t+------------------------------+");
                System.out.println("\t\t\t\t\t\t\t\t\t\t|  HOTEL MANAGEMENT SYSTEM     |");
                System.out.println("\t\t\t\t\t\t\t\t\t\t|______________________________|");
                System.out.println();
                System.out.println("╔══════════════════════════════════╗");
                System.out.println("║          Choose an Option:       ║");
                System.out.println("╠══════════════════════════════════╣");
                System.out.println("║ 1. Reserve a room                ║");
                System.out.println("║ 2. View Reservation              ║");
                System.out.println("║ 3. Get room number               ║");
                System.out.println("║ 4. Update reservation            ║");
                System.out.println("║ 5. Delete Reservation            ║");
                System.out.println("║ 0. Exit                          ║");
                System.out.println("╚══════════════════════════════════╝");
                System.out.println();
                System.out.println("╔════════════════════════╗");
                System.out.println("║   Select an Option     ===>> ");
                System.out.print("╚════════════════════════╝");
                int choose = sc.nextInt();
                switch(choose){
                    case 1:{
                        ReservRoom(conn,sc);
                        break;
                    }
                    case 2:{
                        viewReservation(conn);
                        break;
                    }
                    case 3:{
                        getRoomNumber(conn,sc);
                        break;
                    }
                    case 4:{
                        updateReservation(conn,sc);
                        break;
                    }
                    case 5:{
                        deleteReservation(conn,sc);
                        break;
                    }
                    case 0:{
                        exit();
                        sc.close();
                        return;
                    }
                    default:
                        System.out.println("Invalid choise. please try again... ");
                }

            }

        }catch(SQLException e){
            System.out.println(e.getMessage()+"SQL error ");
        }catch(InterruptedException a){
            throw new RuntimeException(a);
        }
    }

    private static void ReservRoom(Connection conn,Scanner sc){
        try{
            System.out.println("Enter guest name: ");
            String guestName = sc.next();
            sc.nextLine();
            //String guestname = sc.nextLine();
            System.out.println("Enter room number:");
            int roomNumber = sc.nextInt();
            System.out.println("Enter contact number:");
            String contactNumber = sc.next();
            String query = "INSERT INTO reservation(guest_name,room_number,contact_number)"+
                    "VALUES('"+guestName+"',"+roomNumber+",'"+contactNumber+"')";

            try{
                  Statement sts = conn.createStatement();
                  int affectedRow = sts.executeUpdate(query);
                  if(affectedRow>0){
                      System.out.println("reservation successfull...");
                  }
                  else{
                      System.out.println("reservation unsuccessfull..!");
                  }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void viewReservation(Connection conn) throws SQLException{
        String sql = "SELECT reservation_id,guest_name,room_number,contact_number,reservation_date FROM reservation";
        try{

            Statement sts = conn.createStatement();
            ResultSet set = sts.executeQuery(sql);
            System.out.println("current Reservations");
            System.out.println("+-----------------+----------------+---------------+-----------------------+-----------------------+");
            System.out.println("|  Reservation ID |   Guest        | Room Number   | Contact Number        | Reservation Date      |");
            System.out.println("+-----------------+----------------+---------------+-----------------------+-----------------------+");

            while(set.next()){
                int reservation_id = set.getInt("reservation_id");
                String guestName = set.getString("guest_name");
                int roomNumber = set.getInt("room_number");
                String contactNumber = set.getString("contact_number");
                String reservationdate = set.getTimestamp("reservation_date").toString();
                    System.out.printf("| %-14d  | %-15s| %-13d | %-20s  | %-19s |\n",
                            reservation_id,guestName,roomNumber,contactNumber,reservationdate);
            }
            System.out.println("+-----------------+----------------+---------------+-----------------------+-----------------------+");

        }catch(SQLException e){
            System.out.println(e.getMessage()+"view reservation error ");
        }
    }

    private static void getRoomNumber(Connection conn,Scanner sc){
        try{
            System.out.println("Enter reservation ID ");
            int reservID  = sc.nextInt();
            System.out.println("Enter guest name");
            String gtName  = sc.next();
            String q = "SELECT room_number FROM reservation " +
                    "WHERE reservation_id = " + reservID +
                    " AND guest_name = '" + gtName + "'";
            try{
                Statement sts = conn.createStatement();
                ResultSet set = sts.executeQuery(q);
                if(set.next()){
                    int roomNumber  = set.getInt("room_number");
                    System.out.println("room number for reservationID"+reservID+
                            "and guest"+gtName+"is:"+roomNumber);
                }
                else{
                    System.out.println("Reservation not found for given ID and guest name");
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }catch(Exception e){
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
    }

    private static void updateReservation(Connection conn,Scanner sc){
        try{
            System.out.println("Enter reservationID  to update");
            int reservationID = sc.nextInt();
            sc.nextLine();//consume the newline character
            if(!reservationExists(conn,reservationID)){
                System.out.println("Reservation not found for given ID");
                return;
            }
            System.out.println("Enter new guest name -> ");
            String newguest = sc.nextLine();
            System.out.println("Enter new room number -> ");
            int newRoom = sc.nextInt();
            System.out.println("Enter new contact number -> ");
            String newContact = sc.nextLine();

            String sql = "UPDATE reservation SET guest_name = '"+newguest+"',"+
                         "room_number = " + newRoom +"," +
                         "contact_number = '"+newContact+"'"+
                         "WHERE reservation_id = "+reservationID;

            try(Statement sts = conn.createStatement()){
                int affectedRows = sts.executeUpdate(sql);
                if(affectedRows>0){
                    System.out.println("reservation updated successfully");
                }
                else{
                    System.out.println("reservation update failed");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection conn,Scanner sc){
        try{
            System.out.println("Enter reservation ID to delete");
            int reservID = sc.nextInt();
            if(!reservationExists(conn,reservID)){
                System.out.println("resrvation not found for the given id");
                return;
            }
            String sql = "DELETE FROM reservation WHERE reservation_id = "+reservID;
            try(Statement sts = conn.createStatement()){
                int af = sts.executeUpdate(sql);
                if(af>0){
                    System.out.println("resrvation delete successfully");
                }
                else{
                    System.out.println("reservation deletion failed");
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private static boolean reservationExists(Connection conn,int reservationID){
        try{
            String sql = "SELECT reservation_id FROM reservation WHERE reservation_id = "+reservationID;
            try(Statement st = conn.createStatement();
                ResultSet set = st.executeQuery(sql)){
                return set.next();
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static void exit() throws InterruptedException{
        System.out.println("Exiting System");
        int i = 5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(458);
            i--;
        }
        System.out.println();
        System.out.println("thankyou for using hotel reservation system");

    }
}
