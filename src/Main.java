import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // DONT FORGET
    // TO
    // LIKE
    /// DON'T
    /// FORGET
    /// TO
    /// FIX
    /// THE
    /// USER
    /// INPUT
    /// VALIDATION

    // USE TO CREATE A NEW TEXT FILE
    static File clientFile = new File("CLIENT");
    static File reserveFile = new File("RESERVE");
    static File cancelledFile = new File("CANCELLED");

    static Scanner sc = new Scanner(System.in);

    public static void userType (){
        System.out.println("==========================================");
        System.out.println("    Welcome to DLSL Hotel Reservation!");
        System.out.println("==========================================");
        System.out.println("[1] Client");
        System.out.println("[2] Receptionist");
        System.out.println("[3] Manager");
        System.out.println("[0] Exit");

        int userInput = userChoiceValidation("userType");

        boolean isValid = false;

        // Use while loops to constantly call the function
        // until an acceptable number is inputted
        while (!isValid) {
            if (userInput == 1) {
                client();
                isValid = true;
            } else if (userInput == 2) {
                staff();
                isValid = true;
            } else if (userInput == 3) {
                // Call MANAGER FUNCTION
                isValid = true;
            } else if (userInput == 0){
                return;
            } else {
                System.out.println("Invalid input");
                userInput = userChoiceValidation("userType");
            }
        }
    }

    // MAKE SURE TO ADD A ADMIN ACCOUNT IN ORDER TO ACCESS THE STAFF
    public static void staff (){
        System.out.println("====== Welcome Staff ======");

        System.out.println("1. Back");
        System.out.println("2. View Clients Reservations");


        System.out.print("Enter your choice: ");
        int userInput = sc.nextInt();
        sc.nextLine();

        if (userInput == 1)userType();
//        if (userInput == 2)staffClientReservation();
    }
    // CONTINUE
//    public static void staffClientReservation () {
//        boolean isClientIDexist = clientFileReader();
//    }

    public static void client(){
        System.out.println("====== Welcome Client ======");

        System.out.println("[1] Back");
        System.out.println("[2] Register");

        int userInput = userChoiceValidation("client");

        boolean isValid = false;
        // Use while loops to constantly call the function
        // until an acceptable number is inputted
        while (!isValid) {
            if (userInput == 1) {
                userType();
                isValid = true;
            } else if (userInput == 2) {
                registration();
                isValid = true;
            } else {
                System.out.println("Invalid input!");
                userInput = userChoiceValidation("client");
            }
        }
    }

    // Client ID Generator
    public static String clientIDGenerator(){

        // This generator can generate 17.5 million unique IDs

        StringBuilder clientID = new StringBuilder("RES-");
        // RES-ABC123
        Random rand = new Random();

        // Generate Random Capital Letters from A-Z
        int letterLength = 3;
        for (int i = 0; i < letterLength; i++){
            char letter = (char) ('A' + rand.nextInt(26));
            clientID.append(letter);
        }

        // Generate Random Digit from 0-9
        int  digitLength = 3;
        for (int i = 0; i < digitLength; i++){
            int num = (int)(Math.random() * 10);
            clientID.append(num);
        }

        return clientID.toString();
    }

    // Validation for inputs that has a certain pattern
    public static String validationClientRegex(String valiType){
        boolean isValid = false;
        String userDetail = "";

        // All regex for the VALIDATION of PERSONAL INFORMATION
        String regexName = "^[A-Za-z\\s-.']+$";
        String regexAddress = "^[A-Za-z0-9.,#/\\\\\\s-]+$";
        String regexContact = "((\\+639|\\+63\\s9)|09)[0-9]{9}";
        String regexEmail = "^[A-Za-z0-9_-]+@[A-Za-z0-9.]+$";

        // DON'T FORGET TO FIX THE REGEX
        // MAKE SURE THAT THEY DON'T SET THE DATE IN THE PAST
        // ONLY ALLOW 6 MONTHS AHEAD OF RESERVATION
        String regexDate = "^(0[1-9]|1[0-2])/((0[1-9]|[1-2][0-9])|30)/[0-9]{4}$";
//        String regexID = "RES-([A-Za-z0-9]{6})$";

        do {
            if (valiType.equals("Full")){
                System.out.print("Full Name          : ");
                userDetail = sc.nextLine();

                if (userDetail.length() > 100){ // Checks if the CLIENT name is too long
                    System.out.println("Name is too long!");
                } else {
                    Pattern pattern = Pattern.compile(regexName);
                    Matcher matcher = pattern.matcher(userDetail);

                    if (matcher.find()){
                        isValid = true;
                    } else {
                        System.out.println("Invalid input");
                    }
                }
            } else if (valiType.equals("Address")){
                System.out.print("Address            : ");
                userDetail = sc.nextLine();

                Pattern pattern = Pattern.compile(regexAddress);
                Matcher matcher = pattern.matcher(userDetail);

                if (matcher.find()){
                    isValid = true;
                } else {
                    System.out.println("Invalid input");
                }
            } else if (valiType.equals("Contact")){
                System.out.print("Contact Information: ");
                userDetail = sc.nextLine();

                Pattern pattern = Pattern.compile(regexContact);
                Matcher matcher = pattern.matcher(userDetail);

                if (matcher.find()){
                    isValid = true;
                } else {
                    System.out.println("Invalid input");
                }
            } else if (valiType.equals("Email")){
                System.out.print("Email Address      : ");
                userDetail = sc.nextLine();

                Pattern pattern = Pattern.compile(regexEmail);
                Matcher matcher = pattern.matcher(userDetail);

                if (matcher.find()){
                    isValid = true;
                } else {
                    System.out.println("Invalid input");
                }
            } else if (valiType.equals("date")){
                System.out.print("Reservation Date (MM/DD/YYYY): ");
                userDetail = sc.nextLine();

                Pattern pattern = Pattern.compile(regexDate);
                Matcher matcher = pattern.matcher(userDetail);

                if (matcher.find()){
                    isValid = true;
                } else {
                    System.out.println("Invalid input");
                }
            }
        } while (!isValid);

        return userDetail;
    }

    // Validation for a number
    public static int userChoiceValidation (String valiType){
        boolean isValid = false;
        int clientNum = 0;
        boolean gotReject = false;
        String userInput;

        do {
            try {
                if (valiType.equals("facilityQuantity") || valiType.equals("numberOfGuests") || valiType.equals("userType") || valiType.equals("client") || valiType.equals("lunchAndDinnerOffer") || valiType.equals("userPlan")){
                    if (valiType.equals("facilityQuantity") && gotReject){
                        System.out.print("Please input the quantity of your chosen facility: ");
                    }
                    if (valiType.equals("numberOfGuests") && gotReject){
                        System.out.print("Please input the quantity of your chosen guests: ");
                    }
                    if (!valiType.equals("facilityQuantity") && !valiType.equals("numberOfGuests")){
                        System.out.print("Enter your choice: ");
                    }
                    userInput = sc.nextLine();
                    clientNum  = Integer.parseInt(userInput);
                    isValid = true;
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input");
                gotReject = true;
            }
        } while (!isValid);

        return clientNum;
    }

    // Validation for a Y/N input
    public static boolean yesAndNolValidation (String valiType){
        boolean isValid = false;
        boolean isValidated = false;
        String userInput;

        do {
            if (valiType.equals("register") || valiType.equals("confirmation") || valiType.equals("descriptions") || valiType.equals("conPay") || valiType.equals("lunchAndDinner")){
                System.out.print("Enter your choice: ");
                userInput = sc.nextLine();

                if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("n")){
                    if (userInput.equalsIgnoreCase("y")){
                        isValidated = true;
                    }
                    isValid = true;
                } else {
                    System.out.println("Invalid input! Input must be |Y| or |N|");
                }
            }
        } while (!isValid);

        return isValidated;
    }

    public static void printClientID (String fullName, String generatedClientID){
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
        boolean isConfirmed = yesAndNolValidation("confirmation");

        if (isConfirmed){
            reservationTransaction();
        } else {
            System.out.println("Thank you for registering!");
            client();
        }
    }

    public static void clientPersonalInformation (){
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
        while (isExists){
            // Checks if the generated CLIENT ID already exists
            isClientIDExists = clientIDChecker(generatedClientID);

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

    public static void clientFileUpdater (String clientID, String fullName, String address, String contact, String email){
        try  {
            FileWriter fw = new FileWriter(clientFile, true);
            fw.write("\n" + clientID + "|" + fullName + "|" + address + "|" + contact + "|" + email + "|");
            fw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // FIX THE PRINTING OF THE TABLE
    // USE FOR RECEPTIONIST AND MANAGER TO VIEW ALL THE CLIENTS INFORMATION
    public static boolean clientFileReader (){
        System.out.print("Please provide your CLIENT ID: ");
        String clientID = sc.nextLine();

        // Checks if the CLIENT ID exists or not
        boolean isClientIdExists = false;

        System.out.printf("%-10s %-20s %-30s %-15s %-25s\n",
                "ID", "Name", "Address", "Contact", "Email");

        System.out.println("-----------------------------------------------------------------------------------------------");


        try (BufferedReader br = new BufferedReader(new FileReader(clientFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] clientInfos = line.split("\\|");

                // add a for loop to print the table

                if (clientInfos[0].equals(clientID)) {
                    isClientIdExists = true;
                    break;
                }
            }

            if (!isClientIdExists){
                System.out.println("Client ID does not exist!");
            }
        } catch (IOException e){
            System.out.println("Failed to read.");
            e.printStackTrace();
        }
        return isClientIdExists;
    }

    public static boolean clientIDChecker(String clientID){
        boolean isClientIdExists = false;
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(clientFile))){
            while ((line = br.readLine()) != null) {
                String [] arr = line.split("\\|");

                for (int i = 0; i < arr.length; i++){
                    if (arr[0].equals(clientID)){
                        isClientIdExists = true;
                        break;
                    }
                }

                if (isClientIdExists) break;
            }
        } catch (IOException e){
            System.out.println("Failed to read.");
        }

        return isClientIdExists;
    }

    // ADD IT ON A TEXT FILE
    public static void facilitiesDescriptions (int numberOfGuests){
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

        String facility = "\n=== Facilities ===\n" +
                "\nNumber of Guests: " + numberOfGuests + "\n" +
                "\nFacility    \t" + "Price per Unit\t" + "Maximum # of Pax" +
                "\n1. Single Room\t" + "₱ 1,500.00    \t" + "2" +
                "\n2. Double     \t" + "₱ 2,000.00    \t" + "3" +
                "\n3. King       \t" + "₱ 3,000.00    \t" + "4" +
                "\n4. Suite      \t" + "₱ 4,000.00    \t" + "6";

        System.out.println(descriptionsOfFacilities);
        System.out.println(facility);
    }

    public static boolean facilities (int numberOfGuests){

        int totalBalance = 0;
        int totalMaximumOfPax = 0;
        int totalFacilityToReserve;
        String userInput;

        // Number of Facilities that is chosen
        int singleRoom = 0;
        int doubleRoom = 0;
        int kingRoom = 0;
        int suiteRoom = 0;

        int lunchOrDinner;

        int exceededGuests = 0;

        // This shows the Facilities options to the client
        String facility = "\n=== Facilities ===\n" +
                "\nNumber of Guests: " + numberOfGuests + "\n" +
                "\nFacility    \t" + "Price per Unit\t" + "Maximum # of Pax" +
                "\n1. Single Room\t" + "₱ 1,500.00    \t" + "2" +
                "\n2. Double     \t" + "₱ 2,000.00    \t" + "3" +
                "\n3. King       \t" + "₱ 3,000.00    \t" + "4" +
                "\n4. Suite      \t" + "₱ 4,000.00    \t" + "6";

        System.out.println(facility);

        // Prompt the client if they want to see the descriptions of each Facility
        System.out.println("\nDo you want to view the descriptions of each Facilities? (Y/N)");
        boolean isView = yesAndNolValidation("descriptions");

        if (isView) facilitiesDescriptions(numberOfGuests);

        // Get the total number of facilities
        System.out.print("\nHow many facilities do you want to reserve?:");
        totalFacilityToReserve = userChoiceValidation("facilityQuantity");

        // Reminder of the additional 500 peso per extra person
        System.out.println("\nPlease be reminded that there will be an additional of ₱ 500");
        System.out.println("for every extra person for room accommodation.");

        // Prompt the client to ask what facility of choice they want to choose
        // Also has its own validation
        do {
            try {
                System.out.printf("\nPlease provide your facility of choice (%d) remaining: ",  totalFacilityToReserve);
                userInput = sc.nextLine();
                int userNum  = Integer.parseInt(userInput);

                if (userNum == 1){
                    totalBalance += 1500;
                    totalFacilityToReserve--;
                    singleRoom++;
                    totalMaximumOfPax += 2;
                } else if (userNum == 2){
                    totalBalance += 2000;
                    totalFacilityToReserve--;
                    doubleRoom++;
                    totalMaximumOfPax += 3;
                } else if (userNum == 3){
                    totalBalance += 3000;
                    totalFacilityToReserve--;
                    kingRoom++;
                    totalMaximumOfPax += 4;
                } else if (userNum == 4){
                    totalBalance += 4000;
                    totalFacilityToReserve--;
                    suiteRoom++;
                    totalMaximumOfPax += 6;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input");
            }
        } while (totalFacilityToReserve != 0);

        // Check if the number of guests
        // is greater to the total pax combined of the
        // facility of choice
        // if so, add 500 to the number of exceeded guests
        if (numberOfGuests > totalMaximumOfPax){
            exceededGuests = numberOfGuests - totalMaximumOfPax;
            for (int i = 0; i < exceededGuests; i++){
                totalBalance += 500;
            }
        }

        System.out.printf("\nTotal Balance: ₱ %,.2f", (double) totalBalance);

        // Calls if the client want to avail the LUNCH & DINNER
        lunchOrDinner = lunchAndDinner();
        totalBalance += lunchOrDinner;

        // Return if the reservation has been confirmed
        boolean isReserveConfirmed = confirmationOfReservation(totalBalance, lunchOrDinner, singleRoom, doubleRoom, kingRoom, suiteRoom, exceededGuests);

        return isReserveConfirmed;
    }

    // DON'T FORGET TO ADD A CANCELLATION OF THE LUNCH AND DINNER
    // DON'T FORGET TO ADD QUANTITY
    public static int lunchAndDinner (){
        int totalBalance = 0;

        // Prompt the client if they want to avail the Lunch and Dinner
        // DON'T FORGET TO REMIND THE CLIENT THAT THE BREAKFAST ARE FREE <-------->
        System.out.println("\nWe offer Lunch and Dinner. Would you like to avail the offer? (Y/N)");
        boolean userChoice = yesAndNolValidation("lunchAndDinner");

        if (!userChoice){
            return 0;
        }

        String lunchAndDinner = "\nWould you like to avail the offer?" +
                "\n\n1. Lunch : ₱ 250.00" +
                "\n2. Dinner: ₱ 350.00" +
                "\n3. Both  : ₱ 600.00";

        System.out.println(lunchAndDinner);

        System.out.print("What offer would you like to avail?: ");
        int userInput = userChoiceValidation("lunchAndDinnerOffer");
        // DON'T FORGET TO ADD A VALIDATION
        boolean isValid = false;

        while (!isValid){
            if (userInput == 0){
                lunchAndDinner();
                return 0;
            }else if (userInput == 1){
                totalBalance += 250;
                isValid = true;
            } else if (userInput == 2){
                totalBalance += 350;
                isValid = true;
            } else if (userInput == 3){
                totalBalance += 600;
                isValid = true;
            } else {
                System.out.println("Invalid choice. Try again.");
                userInput = userChoiceValidation("lunchAndDinnerOffer");
            }
        }

        return totalBalance;
    }

    // CHANGE THE FUNCTION NAME
    public static boolean confirmationOfReservation (int totalBalance, int lunchAndDinner, int singleRoom, int doubleRoom, int kingRoom, int suiteRoom, int exceededGuests){

        double thirtyPercent = (double) totalBalance * 0.3;
        double fiftyPercent = (double) totalBalance * 0.5;

        String receiptConfirmation = "\n\nHere are the information about your reservation:" +
                "\n\nFacility" + "\tPrice Per Unit" + "\tQuantity" + "\tTotal Price";

        System.out.println(receiptConfirmation);

        if (singleRoom > 0){
            int totalFacilityPrice = 1500 * singleRoom;
            System.out.printf("\nSingle Room\t₱ 1,500.00\t\t%d\t\t\t₱ %.2f", singleRoom, (double) totalFacilityPrice);
        }
        if (doubleRoom > 0){
            int totalFacilityPrice = 2000 * doubleRoom;
            System.out.printf("\nDouble Room\t₱ 2,000.00\t\t%d\t\t\t₱ %.2f", doubleRoom, (double) totalFacilityPrice);
        }
        if (kingRoom > 0){
            int totalFacilityPrice = 3000 * kingRoom;
            System.out.printf("\nKing Room\t₱ 3,000.00\t\t%d\t\t\t₱ %.2f", kingRoom, (double) totalFacilityPrice);
        }
        if (suiteRoom > 0){
            int totalFacilityPrice = 4000 * suiteRoom;
            System.out.printf("\nSuite Room\t₱ 4,000.00\t\t%d\t\t\t₱ %.2f", suiteRoom, (double) totalFacilityPrice);
        }
        if (lunchAndDinner > 0){
            System.out.printf("\n\t\t\t\t\t\t\t            ₱ %.2f", (double) lunchAndDinner);
        }
        System.out.printf("\n\t\t\t\t\t\t\t     Total: ₱ %.2f", (double) totalBalance);

        boolean isConfirmed = clientPaymentPlan(totalBalance, thirtyPercent, fiftyPercent);

        return isConfirmed;
    }

    // MAKE SURE TO USE THE clientReserveFileUpdater FUNCTION
    // TO UPDATE THE RESERVE TEXT FILE
    public static boolean clientPaymentPlan (int totalBalance, double thirtyPercent, double fiftyPercent){
        String paymentOptions = "\n\n=== Payment Plan ===" +
                "\nTotal Balance: ₱ " + totalBalance + ".00" +
                "\nTo confirm your reservation, you must choose your payment plan:" +
                "\n1. Pay the 30% of the total reservation: ₱ " + thirtyPercent + "0" +
                "\n2. Pay the 50% of the total reservation: ₱ " + fiftyPercent + "0" +
                "\n3. Pay the full amount";

        System.out.println(paymentOptions);

        System.out.print("What plan would you like to confirm?: ");
        int userPlan =  userChoiceValidation("userPlan");

        boolean isValid = false;

        while (!isValid){
            if (userPlan == 1) {
                System.out.printf("\nTo finalize your reservation you must pay: ₱ %.2f of the total balance.", thirtyPercent);
                isValid = true;
            } else if (userPlan == 2) {
                System.out.printf("\nTo finalize your reservation you must pay: ₱ %.2f of the total balance.", fiftyPercent);
                isValid = true;
            } else if (userPlan == 3) {
                System.out.printf("\nTo finalize your reservation you must pay: ₱ %.2f", (double) totalBalance);
                isValid = true;
            } else {
                System.out.print("Invalid input");
                userPlan = userChoiceValidation("userPlan");
            }
        }

        System.out.print("\nType Y/N to confirm your payment plan: ");

        boolean isConfirmed = yesAndNolValidation("conPay");

        return isConfirmed;
    }
    // CONTINUE
    // FUNCTION TO UPDATE THE RESERVE FILE
    // WHEN THE CLIENT ARE DONE WITH THE RESERVATION
    public static void clientReserveFileUpdater(){
//        try  {
//            FileWriter fw = new FileWriter(clientFile, true);
//            fw.write("\n" + clientID + "|" + fullName + "|" + address + "|" + contact + "|" + email + "|");
//            fw.close();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
    }

    // CONTINUE
    // Make sure to check if the client has already
    // made his reservation

    // Make sure to put also the clients Full Name
    // if CLIENT ID is found
    public static void reservationTransaction (){
        System.out.println("Type [1] to go back");
        System.out.print("Please provide your CLIENT ID: ");
        String userInput = sc.nextLine();

        if (userInput.equals("1")){
            client();
            return;
        }

        boolean isClientExists = clientIDChecker(userInput);

        if (isClientExists){
            System.out.println("===========================");
            System.out.println("    CLIENT RESERVATION");
            System.out.println("===========================");

            // This is to make sure that the name is displayed when the CLIENT ID is found
            String line;
            boolean isNameFound = false;
            try (BufferedReader br = new BufferedReader(new FileReader(clientFile))){
                while ((line = br.readLine()) != null) {
                    String [] arr = line.split("\\|");

                    for (String s : arr) {
                        if (s.equals(userInput)) {
                            System.out.println("Welcome " + arr[1]);
                            isNameFound = true;
                            break;
                        }
                    }

                    if (isNameFound) break;

                }
            } catch (IOException e){
                System.out.println("Failed to read.");
            }
            System.out.println("Please fill up the necessary information for your reservation!");

            // DON'T FORGOT TO USE THIS
            // DON'T FORGET TO ADD AA DATE OF RESERVATION
            String date = validationClientRegex("date");

            System.out.print("Number of Guests:");
            int numberOfGuests = userChoiceValidation("numberOfGuests");

            boolean isReservationConfirmed =  facilities(numberOfGuests);

            if (isReservationConfirmed){
                // update the client file to reservation confirmed
                System.out.println("\nYou have successfully confirmed your reservation!\n");
            } else {
                isReservationConfirmed = facilities(numberOfGuests);
            }
        } else {
            System.out.println("Your CLIENT ID was not found. Please try again.");
            // This is called a recursion, where you call its own function
            reservationTransaction();
        }
    }

    // CONTINUE
    public static void clientTransaction(){
        reservationTransaction();

        // SAVE THE TRANSACTION TO THE RESERVE TEXT FILE
    }

    public static void registration(){
        System.out.println("==============================");
        System.out.println("    CLIENT REGISTRATION");
        System.out.println("==============================");

        // Ask if the user is registered or not
        System.out.println("Have you already registered? (Y/N): ");
        boolean isRegistered = yesAndNolValidation("register");

        if (isRegistered){
            reservationTransaction();
        } else {
            clientPersonalInformation();
        }
    }

    public static void main(String[] args) {
        userType();

        // === CREATE FILE ===
//        try {
//            if (file.createNewFile()) {
//                System.out.println("File created: " + file.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
//        } catch (IOException e){
//            System.out.println("File already exists.");
//            e.printStackTrace();
//        }        try {
//            if (file.createNewFile()) {
//                System.out.println("File created: " + file.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
//        } catch (IOException e){
//            System.out.println("File already exists.");
//            e.printStackTrace();
//        }

//         === UPDATE FILE ===
//
//        try  {
//            FileWriter fw = new FileWriter(file, true);
//            fw.write("Hello Victor, \n");
//            fw.close();
//        } catch (IOException e){
//            e.printStackTrace();
//        }

        //  === READ THE FILE ===
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//        } catch (IOException e){
//            System.out.println("Failed to read.");
//            e.printStackTrace();
//        }
    }
}
