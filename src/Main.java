import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {

    ///  TO-DO:
    /// TRANSACTION NUMBER AFTER RESERVATION - DONE
    /// CREATE ANOTHER GENERATOR FOR TN - DONE
    /// TN-14 NUMBERS BETWEEN (0-9) - DONE
    /// FIX SUCCESS CHECK-IN MESSAGE IN FAIL CHECK-IN - DONE
    /// THE TEXT FILES MUST BE CREATED FIRST NOT PRE MADE - DONE
    /// MAKE SURE TO SAVE THE ROOMS THEY HAVE PICKED IN A TEXT FILE - DONE

    // TEXT FILES
    static File clientFile = new File("CLIENT.txt");
    static File reserveFile = new File("RESERVE.txt");
    static File cancelledFile = new File("CANCELLED.txt");
    static File checkedInFile = new File("CHECKED_IN.txt");
    static File checkedOutFile = new File("CHECKED_OUT.txt");
    static File clientAvail = new File("CLIENT_AVAIL.txt");

    // Used for something constant price
    static final int singleRoomPrice = 1500;
    static final int doubleRoomPrice = 2000;
    static final int kingRoomPrice = 3000;
    static final int suiteRoomPrice = 4000;
    static final int lunchPrice = 250;
    static final int dinnerPrice = 350;
    static final int dinnerLunchPrice = 600;
    static final int excessPrice = 500;

    // Global variable for other functions
    static String clientIdUsed;
    static String clientName;
    static String reservationDate;
    static String clientTN;
    static int totalLunch = 0;
    static int totalDinner = 0;
    static int totalBoth = 0;
    static String paymentStatus;

    static Scanner sc = new Scanner(System.in);

    // Create the text files if it still doesn't exist
    public static void createFile (){
        createFileIfNotExist(clientFile);
        createFileIfNotExist(reserveFile);
        createFileIfNotExist(cancelledFile);
        createFileIfNotExist(checkedInFile);
        createFileIfNotExist(checkedOutFile);
        createFileIfNotExist(clientAvail);
    }

    private static void createFileIfNotExist (File fileName) {
        try {
            if (fileName.createNewFile()) {
                System.out.println("File " + fileName.getName() + " created.");
            }
        } catch (Exception e) {
            System.out.println("Error: File does not exist");
        }
    }

    private static void userType() {
        System.out.println("==========================================");
        System.out.println("    Welcome to DLSL Hotel Reservation!");
        System.out.println("==========================================");
        System.out.println("[1] Client");
        System.out.println("[2] Receptionist");
        System.out.println("[3] Manager");
        System.out.println("[4] Exit");

        int userInput = userChoiceValidation("userType");

        boolean isValid = false;

        // Use while loops to constantly call the function
        // until an acceptable number is inputted
        while (!isValid) {
            if (userInput == 1) {
                client();
                isValid = true;
            } else if (userInput == 2) {
                receptionist();
                isValid = true;
            } else if (userInput == 3) {
                manager();
                isValid = true;
            } else if (userInput == 4) {
                return;
            } else {
                System.out.println("Invalid input");
                userInput = userChoiceValidation("userType");
            }
        }
    }

    private static void receptionist() {
        String recepUser = "receptionist";
        String recepPass = "receptionist123";
        boolean isAuthenticated = false;
        boolean exit = false;

        while (!isAuthenticated){
            System.out.println("\n=== Receptionist Login ===");
            System.out.print("Username: ");
            String username = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            if (username.equals(recepUser) && password.equals(recepPass)){
                isAuthenticated = true;
                System.out.println("Login successful!\n");
            } else {
                System.out.println("Invalid username or password.\n");
            }
        }

        while (!exit) {
            System.out.println("\n====== Welcome Receptionist ======");
            System.out.println("1. View Client Information");
            System.out.println("2. View Reservation Records");
            System.out.println("3. View Client's Reservation Details");
            System.out.println("4. Check-in Guest");
            System.out.println("5. Check-out Guest");
            System.out.println("6. Back");

            int userInput = userChoiceValidation("userType");
            boolean backToReceptionist = false;

            switch (userInput) {
                case 1:
                    displayClientInfos();
                    pressToBack();
                    break;
                case 2:
                    while (!backToReceptionist) {
                        System.out.println("\n=== VIEW RESERVATION RECORDS ===");
                        System.out.println("[1] View All Reservations");
                        System.out.println("[2] Sort Reservations by Date");
                        System.out.println("[3] Filter Fully Paid Reservations");
                        System.out.println("[4] Filter Reservations with Balance");
                        System.out.println("[5] Back");

                        int subChoice = userChoiceValidation("userType");

                        switch (subChoice) {
                            case 1:
                                displayReservations();
                                break;

                            case 2:
                                sortReservationsByDate();
                                break;

                            case 3:
                                filterReservations("FULLY_PAID");
                                break;

                            case 4:
                                filterReservations("PARTIAL");
                                break;

                            case 5:
                                backToReceptionist = true;
                                break;

                            default:
                                System.out.println("Invalid input.");
                        }
                    }
                    break;
                case 3:
                    displayClientResDetails();
                    break;
                case 4:
                    checkInGuest();
                    pressToBack();
                    break;

                case 5:
                    checkOutGuests();
                    break;

                case 6:
                    exit = true;
                    userType();
                    break;

                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    private static void manager() {
        String managerUser = "manager";
        String managerPass = "manager123";
        boolean isAuthenticated = false;
        boolean exit = false;

        while (!isAuthenticated) {
            System.out.println("\n=== Manager Login ===");
            System.out.print("Username: ");
            String username = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            if (username.equals(managerUser) && password.equals(managerPass)) {
                isAuthenticated = true;
            } else {
                System.out.println("Invalid credentials.");
            }
        }

        while (!exit) {
            System.out.println("\n====== Welcome Manager ======");
            System.out.println("1. View Client Information");
            System.out.println("2. View Reservation Records");
            System.out.println("3. View Client's Reservation Details");
            System.out.println("4. View Checked-In Guests");
            System.out.println("5. View Checked-Out Guests");
            System.out.println("6. Cancel Menu");
            System.out.println("7. Back");

            int choice = userChoiceValidation("userType");
            boolean backToManager = false;
            switch (choice) {
                case 1:
                    displayClientInfos();
                    pressToBack();
                    break;
                case 2:
                    while (!backToManager) {
                        System.out.println("\n=== VIEW RESERVATION RECORDS ===");
                        System.out.println("[1] View All Reservations");
                        System.out.println("[2] Sort Reservations by Date");
                        System.out.println("[3] Filter Fully Paid Reservations");
                        System.out.println("[4] Filter Reservations with Balance");
                        System.out.println("[5] Back");

                        int subChoice = userChoiceValidation("userType");

                        switch (subChoice) {
                            case 1:
                                displayReservations();
                                break;

                            case 2:
                                sortReservationsByDate();
                                break;

                            case 3:
                                filterReservations("FULLY_PAID");
                                break;

                            case 4:
                                filterReservations("PARTIAL");
                                break;

                            case 5:
                                backToManager = true;
                                break;
                            default:
                                System.out.println("Invalid input.");
                        }
                    }
                    break;
                case 3:
                    displayClientResDetails();
                    break;
                case 4:
                    displayCheckedInGuests();
                    pressToBack();
                    break;
                case 5:
                    displayCheckOutGuests();
                    break;
                case 6:
                    while (!backToManager) {
                        System.out.println("\n=== CANCELLATION MENU ===");
                        System.out.println("[1] Cancel a Reservation");
                        System.out.println("[2] View Cancelled Records");
                        System.out.println("[3] Back");

                        int subChoice = userChoiceValidation("userType");

                        switch (subChoice) {
                            case 1:
                                cancel();
                                pressToBack();
                                break;
                            case 2:
                                cancelledList();
                                pressToBack();
                                break;
                            case 3:
                                backToManager = true;
                                break;
                            default:
                                System.out.println("Invalid input.");
                        }
                    }
                    break;
                case 7:
                    exit = true;
                    userType();
                    break;
                default: System.out.println("Invalid input.");
            }
        }
    }

    private static void client() {
        System.out.println("====== Welcome Client ======");

        System.out.println("[1] Reservation");
        System.out.println("[2] Back");

        int userInput = userChoiceValidation("client");

        boolean isValid = false;
        // Use while loops to constantly call the function
        // until an acceptable number is inputted
        while (!isValid) {
            if (userInput == 1) {
                registration();
                isValid = true;
            } else if (userInput == 2) {
                userType();
                isValid = true;
            } else {
                System.out.println("Invalid input!");
                userInput = userChoiceValidation("client");
            }
        }
    }

    private static void checkOutGuests() {

        System.out.print("=== Enter Transaction Number===\n\n");
        System.out.print("Enter Transaction Number: ");
        String input = sc.nextLine();

        File tempFile = new File("temp.txt");
        String removedLine = null;
        boolean found = false;

        try (
                BufferedReader br = getBufferedReader(checkedInFile);
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 8 &&
                        (data[0].equals(input) || data[1].equals(input) || data[2].equalsIgnoreCase(input))) {
                    removedLine = line;
                    found = true;
                    continue;
                }

                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error processing check-out.");
            return;
        }

        if (!found) {
            tempFile.delete();
            System.out.println("Reservation not found in checked-in records.");
            return;
        }

        String[] parts = removedLine.split("\\|");

        if (parts.length >= 8) {

            String clientID = parts[0];
            String clientTN = parts[1];
            String name = parts[2];
            String totalBalance = parts[3];
            String paid = parts[4];
            String remainingBalance = parts[5];
            String checkInDate = parts[6];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String checkOutDate = LocalDate.now().format(formatter);

            if (checkedInFile.delete() && tempFile.renameTo(checkedInFile)) {

                try (FileWriter fw = new FileWriter(checkedOutFile, true)) {

                    if (checkedOutFile.length() > 0) fw.write("\n");

                    fw.write(clientID + "|" +
                            clientTN + "|" +
                            name + "|" +
                            totalBalance + "|" +
                            paid + "|" +
                            remainingBalance + "|" +
                            checkInDate + "|" +
                            checkOutDate);

                } catch (IOException e) {
                    System.out.println("Error writing to checked-out file.");
                }

                System.out.println("\nYou have successfully checked-out. Thank you for choosing DLSL Hotel.");
                updateClientAvailStatus(input, "CHECKED_OUT");
                updateReserveStatus(input, "CHECKED_OUT");

            } else {
                System.out.println("Error updating checked-in records.");
            }

        } else {
            System.out.println("Corrupted data format.");
        }
    }

    private static void updateReserveStatus(String identifier, String newStatus) {

        File tempFile = new File("TEMP_RESERVE.txt");

        try (
                BufferedReader br = new BufferedReader(new FileReader(reserveFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 8 &&
                        (data[0].equals(identifier) || data[1].equals(identifier))) {

                    data[7] = newStatus; // status column
                    line = String.join("|", data);
                }

                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error updating RESERVE status.");
            return;
        }

        reserveFile.delete();
        tempFile.renameTo(reserveFile);
    }

    // Client ID Generator
    private static String clientIDGenerator() {

        // This generator can generate 17.5 million unique IDs

        StringBuilder clientID = new StringBuilder("RES-");
        // RES-ABC123
        Random rand = new Random();

        // Generate Random Capital Letters from A-Z
        int letterLength = 3;
        for (int i = 0; i < letterLength; i++) {
            char letter = (char) ('A' + rand.nextInt(26));
            clientID.append(letter);
        }

        // Generate Random Digit from 0-9
        int digitLength = 3;
        for (int i = 0; i < digitLength; i++) {
            int num = rand.nextInt(10);
            clientID.append(num);
        }

        return clientID.toString();
    }

    // Validation for inputs that has a certain pattern
    private static String validationClientRegex(String valiType) {
        boolean isValid = false;
        String userDetail;

        // Regex Validation for the PERSONAL INFORMATION
        String regexName    = "^[A-Za-z\\s-.']+$";
        String regexAddress = "^[A-Za-z0-9.,#/\\\\\\s-]+$";
        String regexContact = "^(?:\\+63|63|0)?[\\s-]?9\\d{2}[\\s-]?\\d{3}[\\s-]?\\d{4}$";
        String regexEmail   = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        do {
            System.out.print(
                    valiType.equals("Full")    ? "Full Name          : " :
                    valiType.equals("Address") ? "Address            : " :
                    valiType.equals("Contact") ? "Contact Information: " :
                    valiType.equals("Email")   ? "Email Address      : " :
                    "Reservation Date (MM/DD/YYYY): "
            );
            userDetail = sc.nextLine();

            if (valiType.equals("Full")) {
                if (userDetail.length() > 100) {
                    System.out.println("Name is too long!");
                    continue;
                }
                isValid = Pattern.compile(regexName).matcher(userDetail).find();

            } else if (valiType.equals("Address")) {
                isValid = Pattern.compile(regexAddress).matcher(userDetail).find();

            } else if (valiType.equals("Contact")) {
                isValid = Pattern.compile(regexContact).matcher(userDetail).find();

            } else if (valiType.equals("Email")) {
                isValid = Pattern.compile(regexEmail).matcher(userDetail).find();

            } else if (valiType.equals("date")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                try {
                    LocalDate date = LocalDate.parse(userDetail, formatter);
                    if (date.isBefore(LocalDate.now())) {
                        System.out.println("Date cannot be in the past.");
                    } else if (date.isAfter(LocalDate.now().plusMonths(6))) {
                        System.out.println("Date must be within 6 months only.");
                    } else {
                        isValid = true;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format or invalid calendar date.");
                }
            }

            if (!isValid) System.out.println("Invalid input.");

        } while (!isValid);

        return userDetail;
    }

    // Validation for a number
    private static int userChoiceValidation(String valiType) {
        boolean isValid = false;
        boolean gotReject = false;
        int clientNum = 0;
        String userInput;

        do {
            try {
                if (gotReject){
                    if (valiType.equals("facilityQuantity")) {
                        System.out.print("Please input the number of facilities you want to reserve: ");
                    } else if (valiType.equals("numberOfGuests")) {
                        System.out.print("Number of Guests: ");
                    } else if (valiType.equals("facilityChoice")) {
                        System.out.print("Please provide your facility choice: ");
                    } else {
                        System.out.print("Enter your choice: ");
                    }
                } else if (!valiType.equals("numberOfGuests") && !valiType.equals("facilityQuantity") && !valiType.equals("facilityChoice")) {
                    System.out.print("Enter your choice: ");
                }
                userInput = sc.nextLine();
                clientNum = Integer.parseInt(userInput);

                // used to check if the number of guest is lower than 0
                if (clientNum > 0 && clientNum < 10) {
                    isValid = true;
                } else {
                    System.out.println("Invalid Number!");
                    gotReject = true;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                gotReject = true;
            }
        } while (!isValid);

        return clientNum;
    }

    // Validation for a Y/N input
    private static boolean yesOrNolValidation() {
        boolean isValid = false;
        boolean isValidated = false;
        String userInput;

        do {
            System.out.print("Enter your choice: ");
            userInput = sc.nextLine();

            if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("n")) {
                if (userInput.equalsIgnoreCase("y")) {
                    isValidated = true;
                }
                isValid = true;
            } else {
                System.out.println("Invalid input! Input must be |Y| or |N|");
            }

        } while (!isValid);

        return isValidated;
    }

    private static void printClientID(String fullName, String generatedClientID) {
        String confirm = "\n=== Client ID Reminder Message ===" +
                "\n\nImportant: Your Client ID for Your Reservation" +
                "\n\nDear, " + fullName + "," +
                "\n\nThank you for booking with us! Your Client ID is " + generatedClientID + "." +
                "\n\nPlease keep this ID safe -- you will need it for:" +
                "\n\n1. Making a reservation" +
                "\n2. Verifying your account" +
                "\n3. Checking in at the hotel" +
                "\n\nThank you for choosing DLSL HOTEL!.\n\n";

        System.out.print(confirm);

        System.out.println("To continue your reservation, please type (Y/N): ");
        boolean isConfirmed = yesOrNolValidation();

        if (isConfirmed) {
            reservationTransaction();
        } else {
            System.out.println("Thank you for registering!");
            client();
        }
    }

    private static void clientPersonalInformation() {
        System.out.println("\n=== Welcome to Registration, Client! ===\n");
        System.out.println("Please provide your information:");

        // Ask for the Personal Information of the CLIENT with VALIDATION REGEX

        String fullName = validationClientRegex("Full");
        String address = validationClientRegex("Address");
        String contact = validationClientRegex("Contact");
        String email = validationClientRegex("Email");

        String generatedClientID = clientIDGenerator();
        boolean isExists = true;
        boolean isClientIDExists;

        // Generate until the Client has its own UNIQUE CLIENT ID
        while (isExists) {
            // Checks if the generated CLIENT ID already exists
            isClientIDExists = clientIDChecker(generatedClientID, clientFile);

            // If isClientIDExists is true
            // it will call the clientIDGenerator() function
            // until it create its own unique CLIENT ID for the Client
            if (isClientIDExists) {
                generatedClientID = clientIDGenerator();
            }
            isExists = isClientIDExists;
        }

        // Update the CLIENT TEXT FILE if a unique CLIENT ID is generated
        clientFileUpdater(generatedClientID, fullName, address, contact, email);

        // Prints the clients ID with Important Reminder
        printClientID(fullName, generatedClientID);
    }

    private static void clientFileUpdater(String clientID, String fullName, String address, String contact, String email) {
        try (FileWriter fw = new FileWriter(clientFile, true)){
            fw.write( clientID + "|" + fullName + "|" + address + "|" + contact + "|" + email);
            fw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean clientIDChecker(String clientID, File file) {
        boolean isClientIdExists = false;
        String line;

        try (BufferedReader br = getBufferedReader(file)){
            while ((line = br.readLine()) != null) {
                String[] arr = line.split("\\|");

                if (arr[0].equals(clientID)) {
                    isClientIdExists = true;
                    if (file == clientFile) {
                        clientName = arr[1];
                    } else if (file == checkedInFile){
                        clientName = arr[2];
                        reservationDate = arr[3];
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read.");
        }

        return isClientIdExists;
    }

    private static BufferedReader getBufferedReader(File file) throws IOException {
        return  new BufferedReader(new FileReader(file));
    }

    private static void displayClientResDetails(){
        System.out.println("\n=== CLIENT'S RESERVATION DETAILS ===");

        if (!clientAvail.exists()) {
            System.out.println("No guests are currently checked in.");
            return;
        }

        ArrayList<String[]> rows = new ArrayList<>();

        // Gets the length of each COLUMN
        ArrayList<String> header =  new ArrayList<>();
        header.add("ID");
        header.add("Name");
        header.add("Transaction Number");
        header.add("Guests");
        header.add("Single");
        header.add("Double");
        header.add("King");
        header.add("Suite");
        header.add("Lunch");
        header.add("Dinner");
        header.add("Both");
        header.add("Excess");
        header.add("Total");
        header.add("Payment Status");
        header.add("Status");

        try (BufferedReader br = getBufferedReader(clientAvail)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 15) {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    String clientTN = data[2].trim();
                    String guests = data[3].trim();
                    String single = data[4].trim();
                    String doubles = data[5].trim();
                    String king = data[6].trim();
                    String suite = data[7].trim();
                    String lunch = data[8].trim();
                    String dinner = data[9].trim();
                    String both = data[10].trim();
                    String excess = data[11].trim();

                    double totalNum = Double.parseDouble(data[12].trim());
                    String total = String.format("₱ %,.2f", totalNum);

                    String paymentStatus = data[13].trim();
                    String status = data[14].trim();

                    // Store each row in the array
                    rows.add(new String[]{id, name, clientTN, guests,single, doubles, king, suite, lunch, dinner, both, excess,total, paymentStatus,status});
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        alignDisplay(header, rows);
    }

    private static void displayClientInfos() {
        System.out.println("\n=== CLIENT LIST ===");

        if (!clientFile.exists()) {
            System.out.println("No guests are currently checked in.");
            return;
        }

        ArrayList<String[]> rows = new ArrayList<>();

        // Gets the length of each COLUMN
        ArrayList<String> header =  new ArrayList<>();
        header.add("ID");
        header.add("Full Name");
        header.add("Address");
        header.add("Contact");
        header.add("Email");

        try (BufferedReader br = getBufferedReader(clientFile)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 5) {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    String address = data[2].trim();
                    String contact = data[3].trim();
                    String email = data[4].trim();

                    // Store each row in the array
                    rows.add(new String[]{id, name, address, contact, email});
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        alignDisplay(header, rows);
    }

    private static void displayCheckedInGuests() {
        System.out.println("\n=== CHECKED-IN GUESTS LIST ===");

        if (!checkedInFile.exists()) {
            System.out.println("No guests are currently checked in.");
            return;
        }

        ArrayList<String[]> rows = new ArrayList<>();
        ArrayList<String> header = new ArrayList<>();

        header.add("ID");
        header.add("Transaction Number");
        header.add("Name");
        header.add("Date In");
        header.add("Total Balance");
        header.add("Paid");
        header.add("Remaining Balance");


        try (BufferedReader br = getBufferedReader(checkedInFile)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 7) {
                    String id = data[0].trim();
                    String clientTN = data[1].trim();
                    String name = data[2].trim();
                    String date = data[3].trim();
                    double numBalance = Double.parseDouble(data[4].trim());
                    String balance = String.format("₱ %,.2f", numBalance);

                    double numPaid = Double.parseDouble(data[5].trim());
                    String paid = String.format("₱ %,.2f", numPaid);

                    double numRemainingBalance = Double.parseDouble(data[6].trim());
                    String remainBalance = String.format("₱ %,.2f", numRemainingBalance);

                    // Store each row in the arrayList
                    rows.add(new String[]{id, clientTN, name, date, balance, paid, remainBalance});
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading checked-in file.");
            return;
        }
        alignDisplay(header, rows);
    }

    private static void displayCheckOutGuests() {
        System.out.println("\n=== CHECKED-OUT GUESTS LIST ===");

        if (!checkedOutFile.exists()) {
            System.out.println("No checked-out guests found.");
            return;
        }

        ArrayList<String[]> rows = new ArrayList<>();

        ArrayList<String> headers = new ArrayList<>();
        headers.add("ID");
        headers.add("Transaction No.");
        headers.add("Name");
        headers.add("Total Balance");
        headers.add("Paid");
        headers.add("Remaining Balance");
        headers.add("Date In");
        headers.add("Date Out");

        try (BufferedReader br = getBufferedReader(checkedOutFile)) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length >= 8) {

                    String id = data[0].trim();
                    String transaction = data[1].trim();
                    String name = data[2].trim();

                    String total = String.format("₱ %,.2f",
                            Double.parseDouble(data[4].trim()));

                    String paid = String.format("₱ %,.2f",
                            Double.parseDouble(data[5].trim()));

                    String balance = String.format("₱ %,.2f",
                            Double.parseDouble(data[6].trim()));

                    String dateIn = data[3].trim();
                    String dateOut = data[7].trim();

                    rows.add(new String[]{
                            id,
                            transaction,
                            name,
                            total,
                            paid,
                            balance,
                            dateIn,
                            dateOut
                    });
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading checked-out file.");
            return;
        }

        alignDisplay(headers, rows);
    }

    private static void displayReservations() {
        System.out.println("\n=== RESERVATION LIST ===");

        if (!reserveFile.exists()) {
            System.out.println("No reservations found.");
            return;
        }

        ArrayList<String[]> rows = new ArrayList<>();

        ArrayList<String> headers = new ArrayList<>();

        headers.add("ID");
        headers.add("Transaction No.");
        headers.add("Name");
        headers.add("Date");
        headers.add("Total Balance");
        headers.add("Paid");
        headers.add("Remaining Balance");
        headers.add("Status");

        try (BufferedReader br = getBufferedReader(reserveFile)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 7) {
                    String id =  data[0].trim();
                    String transactionID = data[1].trim();
                    String name = data[2].trim();
                    String date = data[3].trim();
                    String total = String.format("₱%,.2f", Double.parseDouble(data[4].trim()));
                    String paid = String.format("₱%,.2f", Double.parseDouble(data[5].trim()));
                    String balance = String.format("₱%,.2f", Double.parseDouble(data[6].trim()));
                    String status = data[7].trim();

                    rows.add(new String[]{id, transactionID, name, date, total, paid, balance, status});
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading reservation file.");
            return;
        }

        alignDisplay(headers, rows);
    }

    // This Function is used to properly align every displaying of the data
    private static void alignDisplay(ArrayList<String> columns, ArrayList<String[]> rows) {

        int colCount = columns.size();
        int[] maxWidths = new int[colCount];

        // Gets the length of each data in the COLUMN arrayList
        // Then gets putted in the maxWidths array
        for (int i = 0; i < colCount; i++) {
            maxWidths[i] = columns.get(i).length();
        }

        // Compare each index of maxWidth and Rows data to know what is the longest string
        for (String[] row : rows) {
            for (int i = 0; i < colCount; i++) {
                maxWidths[i] = Math.max(maxWidths[i], row[i].length());
            }
        }

        // Form a format depending on the size of the rows
        // Including adding +2 to make sure that there are spaces between each column
        StringBuilder format = new StringBuilder();
        for (int i = 0; i < colCount; i++) {
            format.append("%-").append(maxWidths[i] + 2).append("s ");
        }
        format.append("%n");

        System.out.printf(format.toString(), columns.toArray());

        // This gets the total length of each column included the +3
        // Then prints '-' depending on the total length oof each cf each column
        int totalWidth = 0;
        for (int w : maxWidths) {
            totalWidth += w + 3;
        }
        System.out.println("-".repeat(totalWidth + 2));

        // Prints the data
        for (String[] row : rows) {
            System.out.printf(format.toString(), (Object[]) row);
        }
    }

    private static void cancel() {
        System.out.print("Enter the Client ID of the reservation to cancel: ");
        String targetID = sc.nextLine();

        boolean isCheckedIn = false;

        try (BufferedReader br = getBufferedReader(checkedInFile)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(targetID + "|")) {
                    isCheckedIn = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error during cancellation process.");
        }

        if (isCheckedIn) {
            System.out.println("\nError Cancellation Denied: This client is already checked in.");
            System.out.println("Checked-in guests cannot cancel their reservation.");
            return;
        }

        File tempFile = new File("TEMP.txt");
        boolean found = false;

        try (BufferedReader br = getBufferedReader(reserveFile);
             FileWriter fw = new FileWriter(tempFile);
             FileWriter cfw = new FileWriter(cancelledFile, true)) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(targetID + "|")) {
                    cfw.write(line + "\n");
                    found = true;
                } else {
                    fw.write(line + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing cancellation.");
        }

        if (found) {
            reserveFile.delete();
            tempFile.renameTo(reserveFile);
            System.out.println("Reservation successfully moved to cancelled records.");
        } else {
            tempFile.delete();
            System.out.println("Client ID not found in active reservations.");
        }
    }

    private static void cancelledList() {
        System.out.println("\n=== CANCELLED RESERVATIONS ===");
        if (!cancelledFile.exists()) {
            System.out.println("No cancelled records found.");
            return;
        }

        ArrayList<String[]> rows = new ArrayList<>();
        ArrayList<String> headers = new ArrayList<>();

        headers.add("ID");
        headers.add("Name");

        try (BufferedReader br = getBufferedReader(cancelledFile)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length >= 4) {
                    rows.add(new String[]{data[0].trim(), data[2].trim()});
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading cancelled file.");
        }

        alignDisplay(headers, rows);
    }

    // ADD IT ON A TEXT FILE
    private static void facilitiesDescriptions(int numberOfGuests) {
        String descriptionsOfFacilities = "\n\n=== Descriptions of each Facilities ===\n" +
                "\n=== Single Room ===" +
                "\n\nDescription:" +
                "\nA Single Room is designed for one guest," +
                "\nfeaturing a comfortable single bed, a work desk, and essential amenities." +
                "\nIt is ideal for solo travelers seeking a cozy and affordable stay." +
                "\n\nFacilities:" +
                "\n- 2 Single Bed" +
                "\n- Air Conditioning" +
                "\n- Private Bathroom" +
                "\n- Television" +
                "\n- Free Wi-Fi" +
                "\n- Basic Toiletries" +
                "\n\n=== Double Room ===" +
                "\n\nDescription:" +
                "\nA Double Room accommodates two guests, offering either" +
                "\none double bed or two separate beds." +
                "\nIt provides more space and comfort, suitable for" +
                "\ncouples or friends traveling together." +
                "\n\nFacilities:" +
                "\n- 2 Single Beds" +
                "\n- Air Conditioning" +
                "\n- Private Bathroom" +
                "\n- Television" +
                "\n- Free Wi-Fi" +
                "\n- Wardrobe/Closet" +
                "\n\n=== King Room ===" +
                "\n\n Description" +
                "\nA King Room features a spacious layout with a luxurious king-size bed" +
                "\nIt offers enhanced comfort and is perfect for guests" +
                "\nwho prefer extra space and premium sleeping arrangements." +
                "\n\nFacilities" +
                "\n- 1 King-Size Bed" +
                "\n- Air Conditioning" +
                "\n- Private Bathroom with Hot & Cold Shower" +
                "\n- Flat-Screen TV" +
                "\n- Free Wi-Fi" +
                "\n- Mini Refrigerator" +
                "\n- Work Desk" +
                "\n\n=== Suite Room ===" +
                "\n\nDescription:" +
                "\nA Suite Room is a premium accommodation that includes a" +
                "\nseparate living area and bedroom. It is designed for guests seeking" +
                "\nluxury, privacy, and additional space for relaxation or business purposes." +
                "\n\nFacilities:" +
                "\n- Separate Bedroom and Living Area" +
                "\n- King-Size Bed" +
                "\n- Sofa and Lounge Area" +
                "\n- Air Conditioning" +
                "\n- Private Bathroom (Bathtub and Shower)" +
                "\n- Mini Bar/Refrigerator" +
                "\n- Dining Area";

        System.out.println(descriptionsOfFacilities);
        facilitiesOptions(numberOfGuests);
    }

    private static void facilitiesOptions (int numberOfGuests) {
        String facility = "\n=== Facilities ===\n" +
                "\nNumber of Guests: " + numberOfGuests + "\n" +
                "\nFacility    \t" + "Price per Unit\t" + "Maximum # of Pax" +
                "\n1. Single Room\t" + "₱ 1,500.00    \t" + "2" +
                "\n2. Double     \t" + "₱ 2,000.00    \t" + "3" +
                "\n3. King       \t" + "₱ 3,000.00    \t" + "4" +
                "\n4. Suite      \t" + "₱ 4,000.00    \t" + "6";

        System.out.println(facility);
    }

    private static boolean facilities(String clientId, int numberOfGuests, String date) {

        double totalBalance = 0;
        int food = 0;
        int excess;
        double totalExcess;

        totalLunch = 0;
        totalDinner = 0;
        totalBoth = 0;

        int totalMaximumOfPax = 0;
        int totalFacilityToReserve;

        // Number of Facilities that is chosen
        int singleRoom = 0;
        int doubleRoom = 0;
        int kingRoom = 0;
        int suiteRoom = 0;

        // This shows the Facilities options to the client
        facilitiesOptions(numberOfGuests);

        // Prompt the client if they want to see the descriptions of each Facility
        System.out.println("\nDo you want to view the descriptions of each Facilities? (Y/N)");
        boolean isView = yesOrNolValidation();

        if (isView) facilitiesDescriptions(numberOfGuests);

        // Get the total number of facilities
        System.out.print("\nPlease input how many facilities you want to reserve: ");
        totalFacilityToReserve = userChoiceValidation("facilityQuantity");

        // Reminder of the additional 500 peso per extra person
        System.out.println("==================================================================");
        System.out.println("Please be reminded that there will be an additional of ₱ 500");
        System.out.println("for every extra person for room accommodation.");
        System.out.println("==================================================================\n");

        // Prompt the client to ask what facility of choice they want to choose
        // Also has its own validation
        do {
            facilitiesOptions(numberOfGuests);
            System.out.printf("\nPlease provide your facility of choice (%d) remaining: ", totalFacilityToReserve);
            int userNum = userChoiceValidation("facilityChoice");

            if (userNum == 1) {
                totalBalance += singleRoomPrice;
                totalFacilityToReserve--;
                singleRoom++;
                totalMaximumOfPax += 2;
            } else if (userNum == 2) {
                totalBalance += doubleRoomPrice;
                totalFacilityToReserve--;
                doubleRoom++;
                totalMaximumOfPax += 3;
            } else if (userNum == 3) {
                totalBalance += kingRoomPrice;
                totalFacilityToReserve--;
                kingRoom++;
                totalMaximumOfPax += 4;
            } else if (userNum == 4) {
                totalBalance += suiteRoomPrice;
                totalFacilityToReserve--;
                suiteRoom++;
                totalMaximumOfPax += 6;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        } while (totalFacilityToReserve != 0);

        // Check if the number of guests
        // is greater than to the total pax combined of the
        // facility of choice
        // if so, add 500 to the number of exceeded guests
        excess = Math.max(0, numberOfGuests - totalMaximumOfPax);
        totalExcess = excess * excessPrice;

        System.out.printf("\nRoom Balance : ₱ %,.2f", totalBalance);

        if (excess > 0) System.out.printf("\nExcess Guests: %d", excess);

        // Calls if the client want to avail the LUNCH & DINNER
        System.out.println("\n\n=== FOOD OFFERINGS ===");
        System.out.println("\nBreakfast are free for reservation.");
        System.out.println("\nWe offer Lunch and Dinner. \nWould you like to avail the offer? (Y/N)");
        boolean userChoice = yesOrNolValidation();

        if (userChoice) food = lunchAndDinner(numberOfGuests, 0);

        totalBalance += totalExcess;
        totalBalance += food;


        boolean confirmed = confirmationOfReservation(
                totalBalance, food,
                singleRoom, doubleRoom, kingRoom, suiteRoom,
                totalExcess, excess, date
        );

        if (confirmed) {
            saveClientAvail(
                    clientId,
                    numberOfGuests,
                    singleRoom,
                    doubleRoom,
                    kingRoom,
                    suiteRoom,
                    excess,
                    totalBalance,
                    paymentStatus
            );
        }

        return confirmed;
    }

    private static void saveClientAvail(String clientIdUsed,
                                        int numOfGuests,
                                        int singleRoom,
                                        int doubleRoom,
                                        int kingRoom,
                                        int suiteRoom, 
                                        int excess, 
                                        double totalBalance,
                                        String paymentStatus){
        try (FileWriter fw = new FileWriter(clientAvail, true)) {
            fw.write(clientIdUsed + "|" +
                    clientName + "|" +
                    clientTN + "|" +
                    numOfGuests + "|" +
                    singleRoom + "|" +
                    doubleRoom + "|" +
                    kingRoom + "|" +
                    suiteRoom + "|" +
                    totalLunch + "|" +
                    totalDinner + "|" +
                    totalBoth + "|" +
                    excess + "|" +
                    totalBalance + "|" +
                    paymentStatus + "|" +
                    "NOT_CHECKED_IN");

            fw.write("\n");

        } catch (IOException e) {
            System.out.println("Error saving CLIENT_AVAIL data.");
        }
    }

    private static void updateClientAvailStatus(String identifier, String newStatus) {

        File tempFile = new File("TEMP_CLIENT_AVAIL.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(clientAvail)); BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 14 && (data[2].equals(identifier))) {

                    data[14] = newStatus; // status column
                    line = String.join("|", data);
                }

                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error updating CLIENT_AVAIL status.");
            return;
        }

        clientAvail.delete();
        tempFile.renameTo(clientAvail);
    }

    private static void printGuestLeftFood (int guestsRemain){

        System.out.printf("\nYou have %d guest/s left that can avail the offer.\n", guestsRemain);

        String lunchAndDinner = "\nWhat offer would you like to avail?" +
                "\n\n[1] Lunch : ₱ 250.00" +
                "\n[2] Dinner: ₱ 350.00" +
                "\n[3] Both  : ₱ 600.00\n";

        System.out.println(lunchAndDinner);
    }

    private static int lunchAndDinner(int numberOfGuests, int foodBalance) {
        int guestsRemain = numberOfGuests;

        printGuestLeftFood(guestsRemain);

        boolean isValid = false;

        int food = 0;

        while (!isValid) {
            printGuestLeftFood(guestsRemain);
            int userInput = userChoiceValidation("lunchAndDinnerOffer");

            if (userInput == 1) {
                System.out.print("\nHow many Lunch would you like to avail?: \n");
                food = userChoiceValidation("food");

                if (isExcess(food, guestsRemain)){
                    foodBalance += lunchPrice * food;
                    totalLunch += food;
                    guestsRemain -= food;
                    isValid = true;
                }

            } else if (userInput == 2) {
                System.out.print("How many Dinner would you like to avail?: \n");
                food = userChoiceValidation("food");

                if (isExcess(food, guestsRemain)){
                    foodBalance += dinnerPrice * food;
                    totalDinner += food;
                    guestsRemain -= food;
                    isValid = true;
                }

            } else if (userInput == 3) {
                System.out.print("How many Lunch & Dinner would you like to avail?: \n");
                food = userChoiceValidation("food");

                if (isExcess(food, guestsRemain)){
                    foodBalance += dinnerLunchPrice * food;
                    totalBoth += food;
                    guestsRemain -= food;
                    isValid = true;
                }

            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        if (guestsRemain > 0) {
            System.out.printf("\n%d have availed the offer. %d guest can still avail the offer.", food, guestsRemain);
            System.out.print("\nWould you like to avail the offer again? (Y/N)\n");
            boolean offerAgain = yesOrNolValidation();

            if (offerAgain) {
                foodBalance += lunchAndDinner(guestsRemain, 0);
            }
        } else {
            System.out.println("You don't have anymore guests to avail the offer!");
        }

        return foodBalance;
    }

    private static boolean isExcess(int food, int guestsRemain){
        boolean isValid = true;

        if (food > guestsRemain){
            System.out.printf("\nYou only have %d guests, you can't order more than the number of guests.", guestsRemain);
            isValid = false;
        }

        return isValid;
    }

    private static boolean confirmationOfReservation(
            double totalBalance,
            int food,
            int singleRoom,
            int doubleRoom,
            int kingRoom,
            int suiteRoom,
            double totalExcess,
            int excess,
            String date) {

        System.out.println("\n\n================================================");
        System.out.println("           DLSL HOTEL RECEIPT           ");
        System.out.println("================================================");
        System.out.println("Name: " + clientName);
        System.out.println("Date: " + date); // Must be the date today
        System.out.println("----------------------------------------------------");

        System.out.printf("%-18s %-12s %-8s %-14s%n",
                "Item", "Price", "Qty", "Total");

        System.out.println("----------------------------------------------------");

        if (singleRoom > 0) {
            double total = singleRoom * singleRoomPrice;
            System.out.printf("%-18s ₱ %-10.2f %-8d ₱ %-12.2f%n",
                    "Single Room", (double) singleRoomPrice, singleRoom, total);
        }

        if (doubleRoom > 0) {
            double total = doubleRoom * doubleRoomPrice;
            System.out.printf("%-18s ₱ %-10.2f %-8d ₱ %-12.2f%n",
                    "Double Room", (double) doubleRoomPrice, doubleRoom, total);
        }

        if (kingRoom > 0) {
            double total = kingRoom * kingRoomPrice;
            System.out.printf("%-18s ₱ %-10.2f %-8d ₱ %-12.2f%n",
                    "King Room", (double) kingRoomPrice, kingRoom, total);
        }

        if (suiteRoom > 0) {
            double total = suiteRoom * suiteRoomPrice;
            System.out.printf("%-18s ₱ %-10.2f %-8d ₱ %-12.2f%n",
                    "Suite Room", (double) suiteRoomPrice, suiteRoom, total);
        }

        if (excess > 0) {
            System.out.printf("%-18s ₱ %-10.2f %-8d ₱ %-12.2f%n",
                    "Excess Guests", (double) excessPrice, excess, totalExcess);
        }

        if (food > 0) {
            System.out.printf("%-18s %-12s %-8s ₱ %-12.2f%n",
                    "Food Service", "", "", (double) food);
        }

        System.out.println("----------------------------------------------------");

        System.out.printf("%-18s %-12s %-8s ₱ %-12.2f%n",
                "SUBTOTAL", "", "", totalBalance);

        System.out.println("====================================================");

        return clientPaymentPlan(totalBalance, totalBalance * 0.3, totalBalance * 0.5, date);
    }

    // TO UPDATE THE RESERVE TEXT FILE
    private static boolean clientPaymentPlan(double totalBalance, double thirtyPercent, double fiftyPercent, String date) {

        String paymentOptions = "\n\n=== Payment Plan ===" +
                "\nTotal Balance: ₱ " + String.format("%,.2f", (double) totalBalance) +
                "\nTo confirm your reservation, you must choose your payment plan:" +
                "\n1. Pay the 30% of the total reservation: ₱ " + String.format("%,.2f", thirtyPercent) +
                "\n2. Pay the 50% of the total reservation: ₱ " + String.format("%,.2f", fiftyPercent) +
                "\n3. Pay the full amount";

        System.out.println(paymentOptions);

        System.out.print("What plan would you like to confirm?: ");
        int userPlan = userChoiceValidation("userPlan");

        boolean isValid = false;

        while (!isValid) {
            if (userPlan == 1) {
                System.out.printf("\nTo finalize your reservation you must pay: ₱ %,.2f of the total balance.", thirtyPercent);
                isValid = true;
            } else if (userPlan == 2) {
                System.out.printf("\nTo finalize your reservation you must pay: ₱ %,.2f of the total balance.", fiftyPercent);
                isValid = true;
            } else if (userPlan == 3) {
                System.out.printf("\nTo finalize your reservation you must pay: ₱ %,.2f", (double) totalBalance);
                isValid = true;
            } else {
                System.out.print("Invalid input");
                userPlan = userChoiceValidation("userPlan");
            }
        }

        System.out.print("\nType Y/N to confirm your payment plan: \n");

        boolean isConfirmed = yesOrNolValidation();

        if (isConfirmed) {
            double paidAmount;

            if (userPlan == 1) paidAmount = thirtyPercent;
            else if (userPlan == 2) paidAmount = fiftyPercent;
            else paidAmount = totalBalance;

            double balance = totalBalance - paidAmount;
            paymentStatus = (balance == 0) ? "FULLY_PAID" : "PARTIAL";

            // Generate the transaction number for the client
            clientTN = transactionNumber();

            clientReserveFileUpdater(totalBalance, paidAmount, balance, paymentStatus, date, clientTN);
            printTransactionMessage(clientTN);
        }
        return isConfirmed;
    }

    private static void printTransactionMessage(String clientTN){
        String message = "\n=== Reservation Confirmation ===" +
                "\n\nDear " + clientName + "," +
                "\n\nYour reservation has been successfully confirmed!" +
                "\n\nYour Transaction Number is: " + clientTN +
                "\n\nPlease keep this number safe. You will need it during check-in." +
                "\n\nAt the reception, you may present:" +
                "\n- Transaction Number" +
                "\n- Client ID" +
                "\n- Full Name" +
                "\n\nImportant:" +
                "\n- Check-in is only allowed on your reservation date." +
                "\n- Remaining balance must be paid before check-in." +
                "\n\nThank you for choosing DLSL Hotel!\n";

        System.out.println(message);
    }

    private static String transactionNumber(){

        StringBuilder clientTN = new StringBuilder("TN-");
        Random rand = new Random();

        // Generate Random Digit from 0-9
        int digitLength = 14;
        for (int i = 0; i < digitLength; i++) {
            int num = rand.nextInt(10);
            clientTN.append(num);
        }

        return clientTN.toString();
    }

    private static void clientReserveFileUpdater(double total, double paid, double balance, String status, String date, String clientTN) {
        try (FileWriter fw = new FileWriter(reserveFile, true)){
            fw.write( clientIdUsed + "|" + clientTN + "|"+ clientName + "|" + date + "|" +
                    total + "|" + paid + "|" + balance + "|" + status);
            fw.write("\n");
        } catch (IOException e) {
            System.out.println("Error writing reservation file.");
        }
    }

    private static void reservationTransaction() {

        while (true) {
            System.out.println("Type [1] to go back");
            System.out.print("Please provide your CLIENT ID: ");
            String userInput = sc.nextLine();

            if (userInput.equals("1")) {
                client();
                return;
            }

            // Check statuses
            boolean isClientExists = clientIDChecker(userInput, clientFile);
            boolean isClientCheckedIn = clientIDChecker(userInput, checkedInFile);
            boolean isClientReserved = clientIDChecker(userInput, reserveFile);

            if (isClientExists && !isClientReserved && !isClientCheckedIn) {

                clientIdUsed = userInput;

                System.out.println("===========================");
                System.out.println("    CLIENT RESERVATION");
                System.out.println("===========================");

                System.out.println("Welcome, " + clientName);
                System.out.println("\nPlease fill up the necessary information for your reservation!");

                String date = validationClientRegex("date");

                System.out.print("Number of Guests: ");
                int numberOfGuests = userChoiceValidation("numberOfGuests");

                boolean isReservationConfirmed = false;

                while (!isReservationConfirmed) {
                    isReservationConfirmed = facilities(clientIdUsed, numberOfGuests, date);

                    if (isReservationConfirmed) {
                        System.out.println("\nYou have successfully confirmed your reservation! Thank you for choosing DLSL Hotel!\n");
                    }
                }

                userType();

                break;

            } else if (isClientReserved && isClientExists) {
                System.out.println("You already have an active reservation. " +
                        "\nPlease proceed to check in or visit the receptionist once you are done with your reservation.");

            } else if (isClientExists && isClientCheckedIn) {
                System.out.println("You already have checked in your reservation.");

            }else {
                System.out.println("Your CLIENT ID was not found. Please try again.");
            }
        }
    }

    private static void registration() {
        System.out.println("==============================");
        System.out.println("    CLIENT REGISTRATION");
        System.out.println("==============================");

        // Ask if the user is registered or not
        System.out.println("Have you already registered? (Y/N): ");
        boolean isRegistered = yesOrNolValidation();

        if (isRegistered) {
            reservationTransaction();
        } else {
            clientPersonalInformation();
        }
    }

    private static LocalDate parseDate(String dateStr) {
        String[] parts = dateStr.split("/");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        return LocalDate.of(year, month, day);
    }

    private static void sortReservationsByDate() {

        ArrayList<String[]> rows = new ArrayList<>();

        ArrayList<String> headers = new ArrayList<>();
        headers.add("ID");
        headers.add("Name");
        headers.add("Date");
        headers.add("Total Balance");
        headers.add("Paid");
        headers.add("Remaining Balance");
        headers.add("Status");

        try (BufferedReader br = getBufferedReader(reserveFile)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 7) {
                    rows.add(new String[]{
                            data[0].trim(),
                            data[2].trim(),
                            data[3].trim(),
                            data[4].trim(),
                            data[5].trim(),
                            data[6].trim(),
                            data[7].trim()
                    });
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        rows.sort((a, b) -> {
            LocalDate d1 = parseDate(a[3]);
            LocalDate d2 = parseDate(b[3]);
            return d1.compareTo(d2);
        });

        System.out.println("\n=== SORTED RESERVATIONS BY DATE ===");

        alignDisplay(headers, rows);
    }

    private static void filterReservations(String statusFilter) {
        System.out.println("\n=== FILTER: " + statusFilter + " ===");

        ArrayList<String[]> rows = new ArrayList<>();

        ArrayList<String> header =  new ArrayList<>();

        header.add("ID");
        header.add("Name");
        header.add("Remaining Balance");

        try (BufferedReader br = getBufferedReader(reserveFile)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 7) {

                    String id = data[0].trim();
                    String name = data[1].trim();
                    double balance = Double.parseDouble(data[6].trim());

                    boolean match = false;

                    if (statusFilter.equalsIgnoreCase("FULLY_PAID") && balance == 0) {
                        match = true;
                    }
                    else if (statusFilter.equalsIgnoreCase("PARTIAL") && balance > 0) {
                        match = true;
                    }

                    if (match) {
                        String balStr = String.format("₱%,.2f", balance);

                        rows.add(new String[]{id, name, balStr});
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error filtering reservations.");
            return;
        }

        alignDisplay(header, rows);
    }

    private static void checkInGuest() {

        System.out.println();
        System.out.print("====== Guest Check-In ======\n");
        System.out.print("Enter Transaction Number: ");
        String input = sc.nextLine().trim();

        File tempFile = new File("TEMP_RESERVE.txt");
        boolean found = false;
        boolean checkedInSuccess = false;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        try (
                BufferedReader br = getBufferedReader(reserveFile);
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                // SAFETY CHECK
                if (data.length < 8) {
                    bw.write(line);
                    bw.newLine();
                    continue;
                }

                if (data[0].equals(input) || data[1].equals(input)) {

                    found = true;


                    LocalDate today = LocalDate.now();
                    LocalDate reservationDate;

                    try {
                        reservationDate = LocalDate.parse(data[3], formatter);
                    } catch (Exception e) {
                        System.out.println("Invalid date format in record.");
                        bw.write(line);
                        bw.newLine();
                        continue;
                    }


                    if (!reservationDate.equals(today)) {
                        System.out.println("Check-in not allowed. Reservation is not for today.");
                        bw.write(line);
                        bw.newLine();
                        continue;
                    }


                    double paid;
                    double balance;

                    try {
                        paid = Double.parseDouble(data[5]);
                        balance = Double.parseDouble(data[6]);
                    } catch (Exception e) {
                        System.out.println("Corrupted payment data.");
                        bw.write(line);
                        bw.newLine();
                        continue;
                    }

                    if (balance > 0) {
                        System.out.printf("Remaining balance: ₱ %,.2f\n", balance);
                        System.out.print("Pay remaining balance now? (Y/N): ");

                        boolean willPay = yesOrNolValidation();

                        if (willPay) {
                            paid += balance;
                            balance = 0;
                            System.out.println("Payment completed.");
                            updateClientAvailPaymentStatus(input, "FULLY_PAID");
                        } else {
                            System.out.println("Check-in cancelled. Full payment required.");
                            bw.write(line);
                            bw.newLine();
                            continue;
                        }
                    } else {
                        System.out.println("Fully paid. Proceeding to check-in...");
                    }


                    data[7] = "Guest Checked-In";

                    // Update payment values in record
                    data[5] = String.valueOf(paid);
                    data[6] = String.valueOf(balance);

                    // Write updated record to CHECKED_IN file
                    try (FileWriter fw = new FileWriter(checkedInFile, true)) {
                        fw.write(String.join("|",
                                data[0], // ID
                                data[1], // Transaction No
                                data[2], // Name
                                data[3], // Date
                                data[4], // Total
                                data[5], // Paid
                                data[6], // Balance
                                data[7]  // Status
                        ));
                        fw.write(System.lineSeparator());
                    }

                    checkedInSuccess = true;

                    // IMPORTANT: do NOT write to temp → removes from RESERVE
                    continue;
                }

                // Keep other records
                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error processing check-in.");
            return;
        }

        if (!found) {
            tempFile.delete();
            System.out.println("Reservation not found.");
            return;
        }

        // Replace original file
        if (reserveFile.delete() && tempFile.renameTo(reserveFile)) {
            if (checkedInSuccess) {
                System.out.println("Guest successfully checked in.");
                updateClientAvailStatus(input, "Guest Checked-In");
            }
        } else {
            System.out.println("Error updating reservation records.");
        }
    }

    private static void updateClientAvailPaymentStatus(String identifier, String newStatus) {
        File tempFile = new File("TEMP_CLIENT_AVAIL.txt");

        try (
                BufferedReader br = new BufferedReader(new FileReader(clientAvail));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 15 && (data[2].equals(identifier))) {

                    data[13] = newStatus; // payment status column
                    line = String.join("|", data);
                }

                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error updating payment status.");
            return;
        }

        clientAvail.delete();
        tempFile.renameTo(clientAvail);
    }

    private static void pressToBack() {
        System.out.println("\n[1] Back");
        int choice = userChoiceValidation("userType");

        while (choice != 1) {
            System.out.println("Invalid input.");
            choice = userChoiceValidation("userType");
        }
    }

    public static void main (String[] args){
        createFile();
        userType();
    }
}
