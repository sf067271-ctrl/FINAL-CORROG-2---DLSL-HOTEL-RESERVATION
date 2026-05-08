DLSL Hotel Reservation System

DESCRIPTION

  The DLSL Hotel Reservation System is a console-based Java application designed to manage hotel reservations efficiently through role-based access for Clients, Receptionists, and Managers. The system allows clients to register personal information, generate a unique Client ID, make room reservations, select additional services such as food offers, choose payment plans, and receive a unique Transaction Number for reservation verification. Receptionists and managers can manage reservations, monitor records, process guest check-ins and check-outs, sort and filter reservation data, and handle reservation cancellations. The system uses text files for data storage and implements input validation, file handling, date processing, and basic data management to maintain organized hotel operations.

FEATURES
- Client registration with input validation
- Unique Client ID generation
- Unique Transaction Number generation
- Room reservation and accommodation selection
- Food service selection (Lunch and Dinner)
- Reservation receipt generation
- Payment plan options:
- 30% Down Payment
- 50% Down Payment
- Full Payment
- Reservation status tracking
- Check-in and check-out management
- Reservation cancellation system
- Viewing reservation records
- Sorting reservations by date
- Filtering fully paid and partial reservations
- Checked-in and checked-out guest monitoring
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
- Make reservations
- Select room accommodations
- Choose food services
- Select payment plans
- Receive a Transaction Number for reservation verification

2. Receptionist
- View client and reservation records
- Check-in and check-out guests
- Verify Client ID and Transaction Number
- Filter fully paid and partial reservations
- Sort reservations by date

3. Manager
- View all client records
- View reservation records
- Monitor checked-in and checked-out guests
- Cancel reservations
- View cancelled reservation records
- Access reservation monitoring features

TECHNOLOGIES USED
- Java (Core Java)
- File Handling (File, BufferedReader, FileWriter)
- ArrayList
- Regular Expressions (Regex)
- Date Handling (LocalDate)
- Random Number Generation

FILES USED
- CLIENT.txt – stores client information
- RESERVE.txt – stores reservation records
- CHECKED_IN.txt – stores checked-in guests
- CHECKED_OUT.txt – stores checked-out guests
- CANCELLED.txt – stores cancelled reservations

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
- Check-in requires both: client ID and transaction ID.
- Remaining balances must be fully paid before successful check-in.
- The system supports basic hotel management operations only
