import java.util.*;
import java.io.*;

public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);

    static User currentUser;

    static ArrayList<Room> rooms = new ArrayList<>();

    public static void main(String[] args) {

        loadRooms();

        createUser();

        while (true) {

            System.out.println("\n===== HOTEL MENU =====");
            System.out.println("1.Search Rooms");
            System.out.println("2.Book Room");
            System.out.println("3.Cancel Booking");
            System.out.println("4.View Booking");
            System.out.println("5.Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    searchRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelBooking();
                    break;

                case 4:
                    viewBooking();
                    break;

                case 5:
                    System.out.println("Thank You");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    static void createUser() {

        System.out.println("Create Account");

        System.out.print("Username: ");
        String uname = sc.next();

        System.out.print("Password: ");
        String pass = sc.next();

        String pin;
        String confirm;

        while (true) {

            System.out.print("Enter Payment PIN: ");
            pin = sc.next();

            System.out.print("Confirm PIN: ");
            confirm = sc.next();

            if (pin.equals(confirm))
                break;

            System.out.println("PIN Mismatch. Try Again.");
        }

        currentUser = new User(uname, pass, pin);

        System.out.println("Account Created Successfully");
    }

    static void searchRooms() {

        System.out.println("\nAvailable Rooms:");

        for (Room r : rooms) {

            if (r.available) {

                System.out.println(
                        "Room No: " + r.roomNo +
                                " | Type: " + r.type +
                                " | Price: Rs. " + r.price);
            }
        }
    }

    static void bookRoom() {

        searchRooms();

        System.out.print("\nEnter Room Number: ");
        int roomNo = sc.nextInt();

        for (Room r : rooms) {

            if (r.roomNo == roomNo && r.available) {

                System.out.println(
                        "\nSelected Room: " + r.type);

                System.out.println(
                        "Amount To Pay: Rs. " + r.price);

                System.out.print("Enter Payment PIN: ");
                String enteredPin = sc.next();

                if (!currentUser.pin.equals(enteredPin)) {

                    System.out.println("Wrong PIN");
                    return;
                }

                System.out.print(
                        "Enter Amount (Rs. " + r.price + "): ");

                int amount = sc.nextInt();

                if (amount != r.price) {

                    System.out.println(
                            "Incorrect Amount. Payment Failed.");

                    return;
                }

                r.available = false;
                saveRooms();

                saveBooking(
                        currentUser.username,
                        r.type,
                        r.roomNo,
                        amount);

                System.out.println(
                        "Payment Successful");

                System.out.println(
                        "Room Booked Successfully");

                return;
            }
        }

        System.out.println(
                "Room Not Available");
    }

    static void cancelBooking() {

        System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();

        for (Room r : rooms) {

            if (r.roomNo == roomNo) {

                r.available = true;
                saveRooms();
                System.out.println("Booking Cancelled");
                return;
            }
        }

        System.out.println("Booking Not Found");
    }

    static void viewBooking() {

        try {

            File file = new File("bookings.txt");

            Scanner fileReader = new Scanner(file);

            boolean found = false;

            System.out.println(
                    "\n===== YOUR BOOKINGS =====");

            while (fileReader.hasNextLine()) {

                String line = fileReader.nextLine();

                String[] data = line.split(",");

                if (data[0].equals(
                        currentUser.username)) {

                    found = true;

                    System.out.println(
                            "\nUsername : " + data[0]);

                    System.out.println(
                            "Room Type : " + data[1]);

                    System.out.println(
                            "Room No   : " + data[2]);

                    System.out.println(
                            "Amount Paid : Rs. " + data[3]);
                }
            }

            if (!found) {

                System.out.println(
                        "No Booking Found");
            }

            fileReader.close();

        }

        catch (Exception e) {

            System.out.println(
                    "No Booking Found");
        }
    }

    static void saveRooms() {

        try {

            FileWriter fw = new FileWriter("rooms.txt");

            for (Room r : rooms) {

                fw.write(
                        r.roomNo + "," +
                                r.type + "," +
                                r.price + "," +
                                r.available + "\n");
            }

            fw.close();

        } catch (Exception e) {

            System.out.println("Error Saving Rooms");
        }
    }

    static void saveBooking(
            String username,
            String roomType,
            int roomNo,
            int amount) {

        try {

            FileWriter fw = new FileWriter(
                    "bookings.txt",
                    true);

            fw.write(
                    username + "," +
                            roomType + "," +
                            roomNo + "," +
                            amount + "\n");

            fw.close();

        }

        catch (Exception e) {

            System.out.println("File Error");
        }
    }

    static void loadRooms() {

        try {

            File file = new File("rooms.txt");

            if (!file.exists()) {

                rooms.add(new Room(101, "Standard", 1200));
                rooms.add(new Room(102, "Standard", 1200));

                rooms.add(new Room(201, "Deluxe", 1500));
                rooms.add(new Room(202, "Deluxe", 1500));

                rooms.add(new Room(301, "Suite", 2000));
                rooms.add(new Room(302, "Suite", 2000));

                saveRooms();
                return;
            }

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {

                String[] data = reader.nextLine().split(",");

                Room room = new Room(
                        Integer.parseInt(data[0]),
                        data[1],
                        Integer.parseInt(data[2]));

                room.available = Boolean.parseBoolean(data[3]);

                rooms.add(room);
            }

            reader.close();

        } catch (Exception e) {

            System.out.println("Error Loading Rooms");
        }
    }
}

class User {

    String username;
    String password;
    String pin;

    User(String username, String password, String pin) {

        this.username = username;
        this.password = password;
        this.pin = pin;
    }
}

class Room {

    int roomNo;
    String type;
    int price;
    boolean available;

    Room(int roomNo, String type, int price) {

        this.roomNo = roomNo;
        this.type = type;
        this.price = price;
        this.available = true;
    }
}