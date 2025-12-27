# Hostel-Management-System
Implemented using Java Swing Framework
A comprehensive desktop application for managing hostel operations including student registration, staff management, room assignments, payments, and complaints tracking.

## Description

The Hostel Management System is a Java-based desktop application built with Swing GUI framework using NetBeans IDE. The system provides separate portals for administrators, staff, and students to manage various aspects of hostel operations. It features a client-server architecture for remote access and uses MySQL database for data persistence.

## Features

### Administrator Portal
- Add and manage staff members with employee details
- Register students with personal and emergency contact information
- Assign rooms to students
- Process and track payments
- View and manage staff complaints
- Generate PDF reports for registered staff and students
- Add room inventory with different room types

### Staff Portal
- View personal employment details
- Submit complaints to administration
- Access work schedule and salary information
- Update personal information

### Student Portal
- View personal registration details
- Check room assignment information
- View payment status and history
- Generate and print payment receipts as PDF
- Access course information

### Additional Features
- User registration and authentication system
- Role-based access control (Administrator, Staff, Student)
- Room type management (Single and Double rooms)
- Payment processing with multiple methods (Mobile Money, Bank, Cash)
- Complaint tracking system
- Client-server architecture for remote access
- Department information display
- Contact information management

## System Requirements

### Software Requirements
- Java Development Kit (JDK) 8 or higher
- NetBeans IDE 8.0 or higher
- MySQL Server 5.7 or higher
- MySQL Connector/J (JDBC Driver)

### Required Libraries
- MySQL Connector/J (mysql-connector-java)
- iText PDF library (for PDF generation)
- JCalendar (JDateChooser component)

## Database Setup

### Database Configuration

1. Create a MySQL database named `hostel_management_system`

2. Update database connection parameters in the source files:
   - Default connection string: `jdbc:mysql://localhost:3306/hostel_management_system`
   - Default username: `root`
   - Default password: `mark24`

3. Create the following tables:

**persons table** - Stores basic user information
```sql
CREATE TABLE persons (
    PersonID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(100),
    DateOfBirth DATE,
    Gender VARCHAR(10),
    Email VARCHAR(100),
    PhoneNumber VARCHAR(20),
    City VARCHAR(50),
    ROLE VARCHAR(20)
);
```

**registration table** - Stores user registration data
```sql
CREATE TABLE registration (
    FirstName VARCHAR(100),
    DateOfBirth DATE,
    Gender VARCHAR(10),
    Email VARCHAR(100),
    PhoneNumber VARCHAR(20),
    Country VARCHAR(50),
    password VARCHAR(255),
    ROLE VARCHAR(20)
);
```

**students table** - Stores student-specific information
```sql
CREATE TABLE students (
    StudentID INT PRIMARY KEY,
    PersonID INT,
    Fullname VARCHAR(100),
    CourseOfStudy VARCHAR(100),
    EnrollmentDate DATE,
    CurrentYearOfStudy INT,
    EmergencyContactName VARCHAR(100),
    EmergencyContactPhone VARCHAR(20),
    EmergencyContactRelation VARCHAR(50),
    password VARCHAR(255)
);
```

**staff table** - Stores staff member information
```sql
CREATE TABLE staff (
    StaffID INT PRIMARY KEY,
    PersonID INT,
    FullName VARCHAR(100),
    EmployeeID VARCHAR(50),
    DateOfHire DATE,
    JobTitle VARCHAR(100),
    Salary DECIMAL(10,2),
    EmploymentStatus VARCHAR(20),
    DepartmentID INT,
    password VARCHAR(255)
);
```

**rooms table** - Stores room inventory
```sql
CREATE TABLE rooms (
    RoomID INT PRIMARY KEY AUTO_INCREMENT,
    RoomNumber VARCHAR(20),
    RoomTypeID INT,
    Status VARCHAR(20)
);
```

**roomassignments table** - Tracks room assignments to students
```sql
CREATE TABLE roomassignments (
    AssignmentID INT PRIMARY KEY AUTO_INCREMENT,
    StudentID INT,
    RoomID INT,
    TermID INT,
    CheckInDate DATE,
    CheckOutDate DATE,
    RentAmount DECIMAL(10,2)
);
```

**payments table** - Records payment transactions
```sql
CREATE TABLE payments (
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    StudentID INT,
    TermID INT,
    Amount DECIMAL(10,2),
    PaymentDate DATETIME,
    PaymentMethod VARCHAR(50),
    paymentStatus VARCHAR(20)
);
```

**complaints table** - Stores staff complaints
```sql
CREATE TABLE complaints (
    ComplaintID INT PRIMARY KEY AUTO_INCREMENT,
    AssignedStaffID INT,
    SubmitDate DATETIME,
    Description TEXT
);
```

## Installation

1. Install Java JDK and NetBeans IDE

2. Install MySQL Server and create the database

3. Clone or download the project files

4. Open the project in NetBeans IDE

5. Add required libraries to the project:
   - Right-click on Libraries folder
   - Add JAR/Folder
   - Add MySQL Connector/J, iText PDF, and JCalendar libraries

6. Update database connection credentials in source files if different from defaults

7. Build the project in NetBeans

8. Run the application

## Usage

### First Time Setup

1. Run the application
2. The login page will appear
3. Click "Sign-up" to register as a new user
4. Select your role (Student or Staff)
5. Complete the registration form
6. Login with your credentials

### Administrator Access

1. From the main menu, select PORTALS > ADMINISTRATOR
2. Enter the administrator password (default credentials in database)
3. Access the admin dashboard to:
   - Add staff members
   - Register students
   - Assign rooms
   - Process payments
   - View complaints

### Staff Access

1. From the main menu, select PORTALS > STAFF PORTAL
2. Enter your Staff ID and password
3. Click "VIEW DETAILS" to see your information
4. Submit complaints if needed

### Student Access

1. From the main menu, select PORTALS > STUDENT PORTAL
2. Enter your Student ID and password
3. Click "VIEW DETAILS" to see your information
4. Click "PRINT" to generate a PDF receipt

## Project Structure

### Main Application Files

- **Hostel_login.java** - Main login interface
- **registration.java** - User registration form
- **Menu.java** - Main menu after login

### Administrator Module

- **AdminForm.java** - Main admin dashboard
- **adminpassword.java** - Admin authentication
- **AddRooms.java** - Room management interface
- **AssignRoom.java** - Room assignment to students
- **Payments.java** - Payment processing interface
- **ViewComplaints.java** - View staff complaints

### Staff Module

- **Staff.java** - Staff portal interface

### Student Module

- **Student.java** - Student portal interface

### Information Pages

- **Departments.java** - Department information display
- **Contacts.java** - Contact information page
- **RoomTypes.java** - Room types information

### Client-Server Components

- **Server.java** - Server application for remote access
- **Client.java** - Client application for remote connections
- **SerializableClass.java** - Data transfer object for client-server communication

## Security Features

- Password-based authentication for all users
- Role-based access control
- Separate portals for different user types
- Database password encryption recommended (not implemented in current version)
- IP-based vote limiting for secure access

## Known Limitations

- Passwords stored in plain text (encryption recommended for production)
- Single administrator account
- No password recovery mechanism
- Limited error handling in some modules
- No backup and restore functionality

## Troubleshooting

### Database Connection Issues
- Verify MySQL server is running
- Check database credentials in source files
- Ensure MySQL Connector/J is properly added to libraries

### Login Problems
- Verify user exists in database
- Check password is correct
- Ensure proper role is assigned in database

### PDF Generation Issues
- Verify iText library is properly installed
- Check file write permissions
- Ensure student details are loaded before printing

### Date Picker Issues
- Verify JCalendar library is installed
- Check date format settings

## Future Enhancements

- Password encryption and security improvements
- Email notification system
- SMS integration for payments
- Online payment gateway integration
- Mobile application version
- Backup and restore functionality
- Advanced reporting and analytics
- Room availability calendar
- Automated billing system

## Credits

This project was developed as a hostel management solution using Java Swing and MySQL database.

## License

This is an educational project. Contact the developer (Me) for usage permissions.



