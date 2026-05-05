import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {

    // FINALIZE

    // USE TO CREATE A NEW TEXT FILE
    static File clientFile = new File("CLIENT.txt");
    static File reserveFile = new File("RESERVE.txt");
    static File cancelledFile = new File("CANCELLED.txt");
    static File checkedInFile = new File("CHECKED_IN.txt");
    static File checkedOutFile = new File("CHECKED_OUT.txt");

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

    static Scanner sc = new Scanner(System.in);

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
            System.out.println("3. Check-in Guest");
            System.out.println("4. Check-out Guest");
            System.out.println("5. Back");

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
                    checkInGuest();
                    pressToBack();
                    break;

                case 4:
                    checkOutGuests();
                    break;

                case 5:
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
            System.out.println("[1] View Client Information");
            System.out.println("[2] View Reservation Records");
            System.out.println("[3] View Checked-In Guests");
            System.out.println("[4] View Checked-Out Guests");
            System.out.println("[5] Cancel Menu");
            System.out.println("[6] Back");

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
                    displayCheckedInGuests();
                    pressToBack();
                    break;
                case 4:
                    displayCheckOutGuests();
                    break;
                case 5:
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
                case 6:
                    exit = true;
                    userType();
                    break;
                default: System.out.println("Invalid input.");
            }
        }
    }

    private static void client() {
        System.out.println("====== Welcome Client ======");

        System.out.println("[1] Register");
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
        String clientId;

        while (true) {
            System.out.print("Please provide your CLIENT ID to check-out: ");
            clientId = sc.nextLine();

            if (clientIDChecker(clientId, checkedInFile)) {
                break;
            } else {
                System.out.println("Your client ID is not found! Please try again.\n");
            }
        }

        File tempFile = new File("temp.txt");
        String removedLine = null;

        try (
                BufferedReader br = getBufferedReader(checkedInFile);
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.startsWith(clientId + "|")) {
                    bw.write(line);
                    bw.newLine();
                } else {
                    removedLine = line;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (removedLine != null) {

            String[] parts = removedLine.split("\\|");

            if (parts.length >= 3) {
                String id = parts[0];
                String name = parts[1];
                String checkInDate = parts[2];
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy");

                String checkOutDate = java.time.LocalDate.now().format(formatter);

                if (checkedInFile.delete() && tempFile.renameTo(checkedInFile)) {

                    try (FileWriter fw = new FileWriter(checkedOutFile, true)) {
                        fw.write(id + "|" + name + "|" + checkInDate + "|" + checkOutDate + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("\nYou have successfully checked-out. Thank you for choosing DLSL Hotel.");
                } else {
                    System.out.println("Error updating checked-in records.");
                }

            } else {
                System.out.println("Corrupted data format.");
            }

        } else {
            tempFile.delete();
            System.out.println("Client ID not found.");
        }
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
        String regexEmail   = "^[A-Za-z0-9_-]+@[A-Za-z0-9.]+$";

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
                    if (clientNum > 0){
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
                "\n\nDear " + fullName + "," +
                "\n\nThank you for booking with us! Your Client ID is " + generatedClientID + "." +
                "\n\nPlease keep this ID safe -- you will need it for:" +
                "\n\n1. Checking in at the hotel" +
                "\n2. Making changes to your reservation" +
                "\n3. Verifying your booking when contacting us" +
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
            fw.write("\n" + clientID + "|" + fullName + "|" + address + "|" + contact + "|" + email);
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
                        clientName = arr[1];
                        reservationDate = arr[2];
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
        header.add("Name");
        header.add("Date");

        try (BufferedReader br = getBufferedReader(checkedInFile)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 3) {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    String date = data[2].trim();

                    // Store each row in the arrayList
                    rows.add(new String[]{id, name, date});
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
            System.out.println("No guests are currently checked in.");
            return;
        }

        ArrayList<String[]> rows = new ArrayList<>();

        ArrayList<String> headers = new ArrayList<>();
        headers.add("ID");
        headers.add("Name");
        headers.add("Date In");
        headers.add("Date Out");

        try (BufferedReader br = getBufferedReader(checkedOutFile)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 4) {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    String dateIn = data[2].trim();
                    String dateOut = data[3].trim();

                    // Store each row in the array
                    rows.add(new String[]{id, name, dateIn, dateOut});
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading checked-in file.");
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
                    String name = data[1].trim();
                    String date = data[2].trim();
                    String total = String.format("₱%,.2f", Double.parseDouble(data[3].trim()));
                    String paid = String.format("₱%,.2f", Double.parseDouble(data[4].trim()));
                    String balance = String.format("₱%,.2f", Double.parseDouble(data[5].trim()));
                    String status = data[6].trim();

                    rows.add(new String[]{id, name, date, total, paid, balance, status});
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

        // This gets the total length of each column included the +2
        // Then prints '-' depending on the total length oof each cf each column
        int totalWidth = 0;
        for (int w : maxWidths) {
            totalWidth += w + 2;
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
                    rows.add(new String[]{data[0].trim(), data[1].trim()});
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

    private static boolean facilities(int numberOfGuests, String date) {

        int totalBalance = 0;
        int totalMaximumOfPax = 0;
        int totalFacilityToReserve;

        // Number of Facilities that is chosen
        int singleRoom = 0;
        int doubleRoom = 0;
        int kingRoom = 0;
        int suiteRoom = 0;

        int lunchOrDinner = 0;

        // This shows the Facilities options to the client
       facilitiesOptions(numberOfGuests);

        // Prompt the client if they want to see the descriptions of each Facility
        System.out.println("\nDo you want to view the descriptions of each Facilities? (Y/N)");
        boolean isView = yesOrNolValidation();

        if (isView) facilitiesDescriptions(numberOfGuests);

        // Get the total number of facilities
        System.out.print("\nPlease input the number of facilities you want to reserve: ");
        totalFacilityToReserve = userChoiceValidation("facilityQuantity");

        // Reminder of the additional 500 peso per extra person
        System.out.println("\nPlease be reminded that there will be an additional of ₱ 500");
        System.out.println("for every extra person for room accommodation.");

        // Prompt the client to ask what facility of choice they want to choose
        // Also has its own validation
        do {
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
        // is greater to the total pax combined of the
        // facility of choice
        // if so, add 500 to the number of exceeded guests
        totalBalance += guestExceeded(numberOfGuests, totalMaximumOfPax);

        System.out.printf("\nTotal Balance: ₱ %,.2f", (double) totalBalance);

        // Calls if the client want to avail the LUNCH & DINNER
        System.out.println("\n\nBreakfast are free for reservation.");
        System.out.println("\nWe offer Lunch and Dinner. Would you like to avail the offer? (Y/N)");
        boolean userChoice = yesOrNolValidation();

        if (userChoice) {
            lunchOrDinner = lunchAndDinner(numberOfGuests, 0);
            totalBalance += lunchOrDinner;
        }

        // Return if the reservation has been confirmed
        return confirmationOfReservation(totalBalance, lunchOrDinner, singleRoom, doubleRoom, kingRoom, suiteRoom, date);
    }

    private static int guestExceeded (int numberOfGuests, int totalMaximumPax) {
        int totalBalance = 0;

        if (numberOfGuests > totalMaximumPax) {
           int exceededGuests = numberOfGuests - totalMaximumPax;
           totalBalance += exceededGuests * excessPrice;
        }

        return totalBalance;
    }

    private static int lunchAndDinner(int numberOfGuests, int foodBalance) {
        int guestsRemain = numberOfGuests;

        System.out.printf("\nYou have %d guests left that can avail the offer.\n", guestsRemain);

        String lunchAndDinner = "\nWhat offer would you like to avail?" +
                "\n\n[1] Lunch : ₱ 250.00" +
                "\n[2] Dinner: ₱ 350.00" +
                "\n[3] Both  : ₱ 600.00\n";

        System.out.println(lunchAndDinner);

        boolean isValid = false;

        while (!isValid) {
            int userInput = userChoiceValidation("lunchAndDinnerOffer");

            if (userInput == 1) {
                System.out.print("How many Lunch would you like to avail?: \n");
                int food = userChoiceValidation("food");

                foodBalance += lunchPrice * food;
                guestsRemain -= food;

            } else if (userInput == 2) {
                System.out.print("How many Dinner would you like to avail?: \n");
                int food = userChoiceValidation("food");

                foodBalance += dinnerPrice * food;
                guestsRemain -= food;

            } else if (userInput == 3) {
                System.out.print("How many Lunch & Dinner would you like to avail?: \n");
                int food = userChoiceValidation("food");

                foodBalance += dinnerLunchPrice * food;
                guestsRemain -= food;

            } else {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            if (guestsRemain > 0) {
                System.out.print("Would you like to avail again? (Y/N)\n");
                boolean offerAgain = yesOrNolValidation();

                if (offerAgain) {
                    foodBalance += lunchAndDinner(guestsRemain, 0);
                }
            } else {
                System.out.println("You don't have anymore guests to avail the offer!");
            }

            isValid = true;
        }

        return foodBalance;
    }

    private static boolean confirmationOfReservation(
            int totalBalance,
            int lunchAndDinner,
            int singleRoom,
            int doubleRoom,
            int kingRoom,
            int suiteRoom,
            String date) {

        System.out.println("\n\n================================================");
        System.out.println("           DLSL HOTEL RECEIPT           ");
        System.out.println("================================================");
        System.out.println("Name: " + clientName);
        System.out.println("Date: " + date);
        System.out.println("------------------------------------------------");

        System.out.printf("%-18s %-10s %-8s %-12s%n",
                "Item", "Price", "Qty", "Total");

        System.out.println("------------------------------------------------");

        double subtotal = 0;

        if (singleRoom > 0) {
            double total = singleRoomPrice * singleRoom;
            subtotal += total;

            System.out.printf("%-18s ₱%,-9.2f %-8d ₱%,-12.2f%n", "Single Room", (double) singleRoomPrice, singleRoom, total);
        }

        if (doubleRoom > 0) {
            double total = doubleRoomPrice * doubleRoom;
            subtotal += total;

            System.out.printf("%-18s ₱%,-9.2f %-8d ₱%,-12.2f%n", "Double Room", (double) doubleRoomPrice, doubleRoom, total);
        }

        if (kingRoom > 0) {
            double total = kingRoomPrice * kingRoom;
            subtotal += total;

            System.out.printf("%-18s ₱%,-9.2f %-8d ₱%,-12.2f%n", "King Room", (double) kingRoomPrice, kingRoom, total);
        }

        if (suiteRoom > 0) {
            double total = suiteRoomPrice * suiteRoom;
            subtotal += total;

            System.out.printf("%-18s ₱%,-9.2f %-8d ₱%,-12.2f%n", "Suite Room", (double) suiteRoomPrice, suiteRoom, total);
        }

        if (lunchAndDinner > 0) {
            subtotal += lunchAndDinner;

            System.out.printf("%-18s %-10s %-8s ₱%,-12.2f%n", "Food Service", "", "", (double) lunchAndDinner);
        }

        System.out.println("------------------------------------------------");

        System.out.printf("%-18s %-10s %-8s ₱%,-12.2f%n", "SUBTOTAL", "", "", subtotal);

        System.out.println("================================================");

        return clientPaymentPlan(totalBalance, totalBalance * 0.3, totalBalance * 0.5, date);
    }

    // TO UPDATE THE RESERVE TEXT FILE
    private static boolean clientPaymentPlan(int totalBalance, double thirtyPercent, double fiftyPercent, String date) {

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

        System.out.print("\nType Y/N to confirm your payment plan: ");

        boolean isConfirmed = yesOrNolValidation();

        if (isConfirmed) {
            double paidAmount;

            if (userPlan == 1) paidAmount = thirtyPercent;
            else if (userPlan == 2) paidAmount = fiftyPercent;
            else paidAmount = totalBalance;

            double balance = totalBalance - paidAmount;
            String status = (balance == 0) ? "FULLY_PAID" : "PARTIAL";

            clientReserveFileUpdater(totalBalance, paidAmount, balance, status, date);
        }

        return isConfirmed;
    }

    private static void clientReserveFileUpdater(double total, double paid, double balance, String status, String date) {
        try {
            FileWriter fw = new FileWriter(reserveFile, true);
            fw.write("\n" + clientIdUsed + "|" + clientName + "|" + date + "|" +
                    total + "|" + paid + "|" + balance + "|" + status);
            fw.close();
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
            boolean isClientCancelled = clientIDChecker(userInput, cancelledFile);
            boolean isClientReserved = clientIDChecker(userInput, reserveFile);
            boolean isClientCheckedOut = clientIDChecker(userInput, checkedOutFile);

            if (isClientExists && !isClientReserved && !isClientCancelled && !isClientCheckedIn && !isClientCheckedOut) {

                clientIdUsed = userInput;

                System.out.println("===========================");
                System.out.println("    CLIENT RESERVATION");
                System.out.println("===========================");

                System.out.println("Welcome " + clientName);
                System.out.println("\nPlease fill up the necessary information for your reservation!");

                String date = validationClientRegex("date");

                System.out.print("Number of Guests: ");
                int numberOfGuests = userChoiceValidation("numberOfGuests");

                boolean isReservationConfirmed = false;

                while (!isReservationConfirmed) {
                    isReservationConfirmed = facilities(numberOfGuests, date);

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

            } else if (isClientExists && isClientCancelled) {
                System.out.println("You have cancelled your reservation. " +
                        "\nWould you like to make another reservation?");

                boolean makeReservation = yesOrNolValidation();
                if (makeReservation) {
                    userType();
                    return;
                }

            } else {
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
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim(),
                            data[4].trim(),
                            data[5].trim(),
                            data[6].trim()
                    });
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        rows.sort((a, b) -> {
            LocalDate d1 = parseDate(a[2]);
            LocalDate d2 = parseDate(b[2]);
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
                    double balance = Double.parseDouble(data[5].trim());

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
        System.out.print("Enter Client ID or Name: ");
        String input = sc.nextLine();

        ArrayList<String> updatedLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = getBufferedReader(reserveFile)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 7 && (data[0].equals(input) || data[1].equalsIgnoreCase(input))) {
                    found = true;
                    double paid = Double.parseDouble(data[4]);
                    double balance = Double.parseDouble(data[5]);

                    if (balance > 0) {
                        System.out.printf("Remaining balance:  %,.2f\n", balance);
                        System.out.print("Pay remaining balance now? (Y/N): ");

                        boolean willPay = yesOrNolValidation();

                        if (willPay) {
                            paid += balance;
                            data[4] = String.valueOf(paid);
                            data[5] = "0.00";

                            System.out.println("Payment completed.");

                        } else {
                            System.out.println("Check-in cancelled. Full payment required.");
                            updatedLines.add(String.join("|", data));
                            continue;
                        }
                    }
                    data[6] = "Guest Checked-In";

                    FileWriter fw = new FileWriter(checkedInFile, true);
                    if (checkedInFile.length() > 0){
                        fw.write("\n");
                    }
                    fw.write(String.join("|", data));
                    fw.close();

                    continue;
                }

                updatedLines.add(String.join("|", data));
            }

        } catch (IOException e) {
            System.out.println("Error reading reservations.");
            return;
        }

        if (!found) {
            System.out.println("Reservation not found.");
            return;
        }

        try (FileWriter fw = new FileWriter(reserveFile, false)) {
            for (String l : updatedLines) {
                fw.write(l + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error updating check-in.");
        }

        System.out.println("Guest successfully checked in.");
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
        userType();
    }
}
