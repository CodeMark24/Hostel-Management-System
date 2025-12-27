import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.BindException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 * Server for Hostel Management System
 * Handles client requests for students, rooms, payments, and assignments
 */
public class Server extends javax.swing.JFrame {
    Connection con;
    PreparedStatement pstmt;
    String cs, user, password;
    ResultSet rs;
    ObjectOutputStream serverObjectStreamWriter;
    ObjectInputStream serverObjectStreamReader;
    ServerSocket serverSock;
    Socket clientSocket;
    SerializableClass serialObj;
    
    public Server() {
        super.setTitle("Hostel Management Server");
        con = null;
        pstmt = null;
        rs = null;
        cs = "jdbc:mysql://localhost:3306/hostel_management_system";
        user = "root";
        password = "mark24";
        
        initComponents();
        serverObjectStreamWriter = null;
        serverObjectStreamReader = null;
        serverSock = null;
        clientSocket = null;
        serialObj = null;
        
        registerDriverAndConnection();
    }
    
    /**
     * Register JDBC driver and establish database connection
     */
    private void registerDriverAndConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cs, user, password);
            txtStatus.setText("Database Connected");
        } catch (SQLException ex) {
            txtStatus.setText("Database Connection Failed");
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            txtStatus.setText("JDBC Driver Not Found");
            e.printStackTrace();
        }
    }
    
    /**
     * Start server and listen for client connections
     */
    public void go() {
        try {
            serverSock = new ServerSocket(50000);
            txtStatus.setText("Server Running on Port 50000");
            
            while (true) {
                serialObj = new SerializableClass();
                clientSocket = serverSock.accept();
                
                txtStatus.setText("Client Connected: " + clientSocket.getInetAddress());
                
                serverObjectStreamReader = new ObjectInputStream(clientSocket.getInputStream());
                serverObjectStreamWriter = new ObjectOutputStream(clientSocket.getOutputStream());
                
                Thread t = new Thread(new ServerRunnable());
                t.start();
            }
        } catch (BindException ex) {
            JOptionPane.showMessageDialog(null, "Server Already Running");
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Runnable class to handle client requests
     */
    public class ServerRunnable implements Runnable {
        public void run() {
            try {
                while ((serialObj = (SerializableClass) serverObjectStreamReader.readObject()) != null) {
                    
                    // Determine operation type
                    if ("STUDENT".equals(serialObj.operationType)) {
                        handleStudentOperation();
                    } else if ("ROOM".equals(serialObj.operationType)) {
                        handleRoomOperation();
                    } else if ("PAYMENT".equals(serialObj.operationType)) {
                        handlePaymentOperation();
                    } else if ("ASSIGNMENT".equals(serialObj.operationType)) {
                        handleAssignmentOperation();
                    }
                    
                    // Send response back to client
                    serverObjectStreamWriter.writeObject(serialObj);
                    serverObjectStreamWriter.flush();
                }
            } catch (IOException ex) {
                txtStatus.setText("Client Disconnected");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        
        /**
         * Handle student-related operations
         */
        private void handleStudentOperation() {
            try {
                if (serialObj.flagSave) {
                    // Insert new student
                    String query = "INSERT INTO students (StudentID, PersonID, Fullname, CourseOfStudy, " +
                                 "EnrollmentDate, EmergencyContactName, EmergencyContactPhone, password) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, serialObj.studentID);
                    pstmt.setInt(2, serialObj.studentID);
                    pstmt.setString(3, serialObj.fullName);
                    pstmt.setString(4, serialObj.courseOfStudy);
                    pstmt.setString(5, serialObj.enrollmentDate);
                    pstmt.setString(6, serialObj.emergencyContactName);
                    pstmt.setString(7, serialObj.emergencyContactPhone);
                    pstmt.setString(8, serialObj.password);
                    
                    int rows = pstmt.executeUpdate();
                    serialObj.success = (rows > 0);
                    serialObj.message = serialObj.success ? "Student added successfully" : "Failed to add student";
                    txtLog.append("Student Added: " + serialObj.fullName + "\n");
                    
                } else if (serialObj.flagFind) {
                    // Find student by ID and password
                    String query = "SELECT * FROM students WHERE StudentID = ? AND password = ?";
                    pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, serialObj.studentID);
                    pstmt.setString(2, serialObj.password);
                    
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        serialObj.fullName = rs.getString("Fullname");
                        serialObj.courseOfStudy = rs.getString("CourseOfStudy");
                        serialObj.enrollmentDate = rs.getString("EnrollmentDate");
                        serialObj.emergencyContactName = rs.getString("EmergencyContactName");
                        serialObj.emergencyContactPhone = rs.getString("EmergencyContactPhone");
                        serialObj.success = true;
                        serialObj.message = "Student found";
                        txtLog.append("Student Retrieved: " + serialObj.fullName + "\n");
                    } else {
                        serialObj.success = false;
                        serialObj.message = "Student not found or invalid credentials";
                    }
                    
                } else if (serialObj.flagDelete) {
                    // Delete student
                    String query = "DELETE FROM students WHERE StudentID = ?";
                    pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, serialObj.studentID);
                    
                    int rows = pstmt.executeUpdate();
                    serialObj.success = (rows > 0);
                    serialObj.message = serialObj.success ? "Student deleted" : "Student not found";
                    txtLog.append("Student Deleted: ID " + serialObj.studentID + "\n");
                }
                
            } catch (SQLException ex) {
                serialObj.success = false;
                serialObj.message = "Database error: " + ex.getMessage();
                ex.printStackTrace();
            }
        }
        
        /**
         * Handle room-related operations
         */
        private void handleRoomOperation() {
            try {
                if (serialObj.flagSave) {
                    // Insert new room
                    String query = "INSERT INTO rooms (RoomNumber, RoomTypeID, Status) VALUES (?, ?, ?)";
                    pstmt = con.prepareStatement(query);
                    pstmt.setString(1, serialObj.roomNumber);
                    pstmt.setInt(2, serialObj.roomTypeID);
                    pstmt.setString(3, serialObj.roomStatus);
                    
                    int rows = pstmt.executeUpdate();
                    serialObj.success = (rows > 0);
                    serialObj.message = serialObj.success ? "Room added successfully" : "Failed to add room";
                    txtLog.append("Room Added: " + serialObj.roomNumber + "\n");
                    
                } else if (serialObj.flagFind) {
                    // Find available rooms
                    String query = "SELECT * FROM rooms WHERE Status = 'AVAILABLE'";
                    pstmt = con.prepareStatement(query);
                    rs = pstmt.executeQuery();
                    
                    StringBuilder roomList = new StringBuilder();
                    while (rs.next()) {
                        roomList.append(rs.getString("RoomNumber")).append(",");
                    }
                    serialObj.message = roomList.toString();
                    serialObj.success = true;
                    txtLog.append("Available Rooms Retrieved\n");
                }
                
            } catch (SQLException ex) {
                serialObj.success = false;
                serialObj.message = "Database error: " + ex.getMessage();
                ex.printStackTrace();
            }
        }
        
        /**
         * Handle payment-related operations
         */
        private void handlePaymentOperation() {
            try {
                if (serialObj.flagSave) {
                    // Insert payment
                    String query = "INSERT INTO payments (StudentID, TermID, Amount, PaymentDate, " +
                                 "PaymentMethod, paymentStatus) VALUES (?, ?, ?, ?, ?, ?)";
                    pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, serialObj.studentID);
                    pstmt.setInt(2, serialObj.termID);
                    pstmt.setDouble(3, serialObj.amount);
                    pstmt.setString(4, serialObj.paymentDate);
                    pstmt.setString(5, serialObj.paymentMethod);
                    pstmt.setString(6, serialObj.paymentStatus);
                    
                    int rows = pstmt.executeUpdate();
                    serialObj.success = (rows > 0);
                    serialObj.message = serialObj.success ? "Payment recorded" : "Payment failed";
                    txtLog.append("Payment Recorded: Student " + serialObj.studentID + "\n");
                    
                } else if (serialObj.flagFind) {
                    // Find payment status
                    String query = "SELECT * FROM payments WHERE StudentID = ? ORDER BY PaymentDate DESC LIMIT 1";
                    pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, serialObj.studentID);
                    
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        serialObj.amount = rs.getDouble("Amount");
                        serialObj.paymentStatus = rs.getString("paymentStatus");
                        serialObj.paymentDate = rs.getString("PaymentDate");
                        serialObj.success = true;
                    } else {
                        serialObj.success = false;
                        serialObj.message = "No payment records found";
                    }
                }
                
            } catch (SQLException ex) {
                serialObj.success = false;
                serialObj.message = "Database error: " + ex.getMessage();
                ex.printStackTrace();
            }
        }
        
        /**
         * Handle room assignment operations
         */
        private void handleAssignmentOperation() {
            try {
                if (serialObj.flagSave) {
                    // Assign room to student
                    String query = "INSERT INTO roomassignments (StudentID, RoomID, TermID, " +
                                 "CheckInDate, CheckOutDate, RentAmount) VALUES (?, ?, ?, ?, ?, ?)";
                    pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, serialObj.studentID);
                    pstmt.setInt(2, serialObj.roomID);
                    pstmt.setInt(3, serialObj.termID);
                    pstmt.setString(4, serialObj.checkInDate);
                    pstmt.setString(5, serialObj.checkOutDate);
                    pstmt.setDouble(6, serialObj.rentAmount);
                    
                    int rows = pstmt.executeUpdate();
                    serialObj.success = (rows > 0);
                    serialObj.message = serialObj.success ? "Room assigned" : "Assignment failed";
                    txtLog.append("Room Assigned: Student " + serialObj.studentID + " -> Room " + serialObj.roomID + "\n");
                }
                
            } catch (SQLException ex) {
                serialObj.success = false;
                serialObj.message = "Database error: " + ex.getMessage();
                ex.printStackTrace();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        txtStatus = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hostel Management Server");
        
        txtLog.setColumns(20);
        txtLog.setRows(5);
        txtLog.setEditable(false);
        jScrollPane1.setViewportView(txtLog);
        
        txtStatus.setEditable(false);
        txtStatus.setText("Server Initializing...");
        
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel1.setText("Server Activity Log:");
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addComponent(txtStatus)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );
        
        pack();
    }
    // </editor-fold>
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Server s = new Server();
                s.setVisible(true);
                s.setLocationRelativeTo(null);
                s.go();
            }
        });
    }
    
    // Variables declaration
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtLog;
    private javax.swing.JTextField txtStatus;
    // End of variables declaration
}