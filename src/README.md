DLSL Hotel Reservation System

DESCRIPTION
The DLSL Hotel Reservation System is a console-based application developed in Java that 
allows users to manage hotel reservations efficiently. The system supports multiple user 
roles, including clients, receptionists, and managers, each with specific functionalities. 
Clients can register, generate a unique client ID, and make reservations by selecting room 
types, number of guests, and additional services such as food. Receptionists and managers can
view records, manage reservations, and handle check-in and check-out processes. The system uses 
text files for data storage and implements input validation, file handling, and basic data processing 
to ensure accurate and organized hotel operations.

FEATURES
- Client registration with input validation
- Unique Client ID generation
- Room reservation system
- Food service selection (Lunch and Dinner)
- Payment plan options (30%, 50%, or full payment)
- Check-in and check-out functionality
- Reservation cancellation
- Viewing, sorting, and filtering of records
- File-based data storage system

HOW TO RUN/ USE
1. Open the project in a Java IDE (e.g., IntelliJ, Eclipse, or VS Code)
2. Compile the Main.java file
3. Run the program
4. Select user type (Client, Receptionist, Manager)
5. Follow the on-screen menu instructions

USER ROLES
1. Client
- Register personal information
- Generate Client ID
- Make reservations and select services

2. Receptionist
- View client and reservation records
- Check-in and check-out guests

3. Manager
- View all system records
- Monitor checked-in and checked-out guests
- Cancel reservations and view cancelled records

TECHNOLOGIES USED
- Java (Core Java)
- File Handling (File, BufferedReader, FileWriter)
- ArrayList
- Regular Expressions (Regex)
= Date Handling (LocalDate)

FILES USED
CLIENT.txt – stores client information
RESERVE.txt – stores reservation records
CHECKED_IN.txt – stores checked-in guests
CHECKED_OUT.txt – stores checked-out guests
CANCELLED.txt – stores cancelled reservations

DEFAULT CREDENTIALS
Receptionist
- Username: receptionist
- Password: receptionist123

Manager
- Username: manager
- Password: manager123
  
NOTES
- The system is console-based and does not include a graphical user interface.
- All data is stored locally using text files.
- Input validation is implemented to ensure correct data entry.
- The system supports basic hotel management operations only
