package Hotal_Package;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class HotalReserVationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "91491026";


    public static void main(String args[])throws ClassNotFoundException,SQLException{
        Scanner sc = new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage()+"this class not found.....");
        }
        System.out.println();
        System.out.println();
        System.out.println("╔══════════════════════════╗");
        System.out.println("║   SIGN_Up -> press 1     ║");
        System.out.println("╚══════════════════════════╝");
        System.out.println();
        System.out.println("╔══════════════════════════╗");
        System.out.println("║   SIGN_In -> press 2     ║");
        System.out.println("╚══════════════════════════╝");
        System.out.println();
        System.out.println("╔══════════════════════════╗");
        System.out.println("║      Exit -> press 3     ║");
        System.out.println("╚══════════════════════════╝");
        System.out.println();


        int ans = sc.nextInt();
        switch(ans){
            case 1:{
                try{
                    InputStreamReader r = new InputStreamReader(System.in);
                    BufferedReader br = new BufferedReader(r);
                    System.out.println("signUp new account-> ");
                    Connection conn = DriverManager.getConnection(url,username,password);
                    System.out.println("Enter first name-> ");
                    String fname = br.readLine();
                    System.out.println("Enter your last name-> ");
                    String sname = br.readLine();
                    System.out.println("Enter your email-> ");
                    String email = br.readLine();
                    System.out.println("Enter your password-> ");
                    int pass = Integer.parseInt(br.readLine());

                    String q = "INSERT INTO security(name,last,email,password)VALUES (?,?,?,?)";
                    PreparedStatement pst = conn.prepareStatement(q);
                    pst.setString(1,fname);
                    pst.setString(2,sname);
                    pst.setString(3,email);
                    pst.setInt(4,pass);
                    pst.executeUpdate();
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case 2:{
                InputStreamReader r = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(r);
                try {
                    System.out.println("signIn you account");
                    System.out.print("Enter email-> ");
                    String check_email = br.readLine();
                    System.out.println("Enter password-> ");
                    int check_pass = Integer.parseInt(br.readLine());

                    String sql = "SELECT email, password FROM security WHERE email = ? AND password = ?";
                    Connection conn = DriverManager.getConnection(url, username, password);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, check_email);
                    pst.setInt(2, check_pass);

                    ResultSet set = pst.executeQuery();

                    if (set.next()) {
                        System.out.println("User authenticated!");
                    }
                    else {
                        System.out.println("Invalid email or password!");
                        System.exit(0);
                    }
                    break;
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
            case 3:{
                try{
                    System.out.println("Exiting system");
                    int i = 5;
                    while(i!=0){
                        System.out.print(".");
                        Thread.sleep(458);
                        i--;
                    }
                    System.out.println();
                    System.out.println("thankyou for using hotel system");
                    System.exit(0);
                }catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }
                break;
            }
            default:
                System.out.println("INVALID INPUT ....");
                try{
                    int i = 5;
                    while(i!=0){
                        System.out.print(".");

                        Thread.sleep(458);
                        i--;
                    }
                    System.out.println();
                    System.out.println("PLEASE TRY AGAIN-> ");
                    System.exit(0);
                }catch(InterruptedException e){
                    System.out.println("e.getMessage()");
                }
        }

        try{
            Connection conn = DriverManager.getConnection(url,username,password);
            while(true){
                System.out.println();
                //Scanner sc  = new Scanner(System.in);
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
                        ReservRoom(conn);
                        break;
                    }
                    case 2:{
                        viewReservation(conn);
                        break;
                    }
                    case 3:{
                        getRoomNumber(conn);
                        break;
                    }
                    case 4:{
                        updateReservation(conn);
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

    private static void ReservRoom(Connection conn){
        try{
            System.out.println("Enter guest name: ");
           // String guestName = sc.next();
           // sc.nextLine();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String guestName = br.readLine();
            System.out.println("Enter room number:");
            int roomNumber = Integer.parseInt(br.readLine());
            System.out.println("Enter contact number:");
            String contactNumber = br.readLine();
            if(contactNumber.length()>10){
                System.out.println("your phone number is invalid please try again...");
            }
            String sql = "SELECT room_number FROM reservation WHERE room_number = ?";
            PreparedStatement pr  =  conn.prepareStatement(sql);
            pr.setInt(1,roomNumber);
            ResultSet set = pr.executeQuery();
            if(set.next()){
                System.out.println("room number booking already! please try another room");
                exit();
            }
            else{
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

    private static void getRoomNumber(Connection conn){
        try{
            InputStreamReader r = new InputStreamReader(System.in);
            BufferedReader sc = new BufferedReader(r);
            System.out.println("Enter reservation ID ");
            int reservID  = Integer.parseInt(sc.readLine());
            System.out.println("Enter guest name");
            String gtName  = sc.readLine();
            String q = "SELECT room_number FROM reservation " +
                    "WHERE reservation_id = " + reservID +
                    " AND guest_name = '" + gtName + "'";
            try{
                Statement sts = conn.createStatement();
                ResultSet set = sts.executeQuery(q);
                if(set.next()){
                    int roomNumber  = set.getInt("room_number");
                    System.out.println("room number for reservationID " +" "+ reservID   +" and guest "+ gtName + " is:" + roomNumber);
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

    private static void updateReservation(Connection conn){
        try{
            InputStreamReader r = new InputStreamReader(System.in);
            BufferedReader sc = new BufferedReader(r);
            System.out.println("Enter reservationID  to update");
            int reservationID = Integer.parseInt(sc.readLine());
           // sc.nextLine();//consume the newline character
            if(!reservationExists(conn,reservationID)){
                System.out.println("Reservation not found for given ID");
                return;
            }
            System.out.println("Enter new guest name -> ");
            String newguest = sc.readLine();
            System.out.println("Enter new room number -> ");
            int newRoom = Integer.parseInt(sc.readLine());
            System.out.println("Enter new contact number -> ");
            String newContact = sc.readLine();

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
        }catch(SQLException|IOException e){
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
