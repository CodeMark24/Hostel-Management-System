import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * Client for Hostel Management System
 * Provides interface for remote operations on students, rooms, and payments
 */
public class Client extends javax.swing.JFrame {
    static Client c;
    ObjectOutputStream clientObjectStreamWriter;
    ObjectInputStream clientObjectStreamReader;
    Socket sock;
    SerializableClass serialObj;
    boolean dotacceptFlag = false;
    
    public Client() {
        super.setTitle("Hostel Management Client");
        initComponents();
        clientObjectStreamWriter = null;
        clientObjectStreamReader = null;
        sock = null;
        serialObj = null;
        go();
    }
    
    /**
     * Establish connection to server
     */
    public void go() {
        try {
            setUpNetworking();
            Thread t = new Thread(new ClientRunnable());
            t.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cannot connect to server. Make sure server is running.", 
                                        "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Setup network connection
     */
    private void setUpNetworking() {
        try {
            sock = new Socket("127.0.0.1", 50000);
            serialObj = new SerializableClass();
            clientObjectStreamWriter = new ObjectOutputStream(sock.getOutputStream());
            clientObjectStreamReader = new ObjectInputStream(sock.getInputStream());
            txtStatus.setText("Connected to Server");
        } catch (IOException ex) {
            txtStatus.setText("Connection Failed");
            ex.printStackTrace();
        }
    }
    
    /**
     * Runnable class to receive responses from server
     */
    public class ClientRunnable implements Runnable {
        public void run() {
            try {
                while ((serialObj = (SerializableClass) clientObjectStreamReader.readObject()) != null) {
                    
                    if (serialObj.success) {
                        // Handle successful response based on operation type
                        if ("STUDENT".equals(serialObj.operationType) && serialObj.flagFind) {
                            txtStudentName.setText(serialObj.fullName);
                            txtCourse.setText(serialObj.courseOfStudy);
                            txtEmergencyContact.setText(serialObj.emergencyContactPhone);
                        } else if ("PAYMENT".equals(serialObj.operationType) && serialObj.flagFind) {
                            txtPaymentStatus.setText(serialObj.paymentStatus);
                            txtAmount.setText(String.valueOf(serialObj.amount));
                        }
                        
                        JOptionPane.showMessageDialog(null, serialObj.message, "Success", 
                                                    JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, serialObj.message, "Error", 
                                                    JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (IOException ex) {
                txtStatus.setText("Disconnected from Server");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        jTabbedPane1 = new javax.swing.JTabbedPane();
        
        // Student Panel
        studentPanel = new javax.swing.JPanel();
        lblStudentID = new javax.swing.JLabel();
        txtStudentID = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblStudentName = new javax.swing.JLabel();
        txtStudentName = new javax.swing.JTextField();
        lblCourse = new javax.swing.JLabel();
        txtCourse = new javax.swing.JTextField();
        lblEmergencyContact = new javax.swing.JLabel();
        txtEmergencyContact = new javax.swing.JTextField();
        btnFindStudent = new javax.swing.JButton();
        btnSaveStudent = new javax.swing.JButton();
        btnDeleteStudent = new javax.swing.JButton();
        
        // Payment Panel
        paymentPanel = new javax.swing.JPanel();
        lblPayStudentID = new javax.swing.JLabel();
        txtPayStudentID = new javax.swing.JTextField();
        lblAmount = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        lblPaymentStatus = new javax.swing.JLabel();
        txtPaymentStatus = new javax.swing.JTextField();
        btnCheckPayment = new javax.swing.JButton();
        btnRecordPayment = new javax.swing.JButton();
        
        // Status Bar
        txtStatus = new javax.swing.JTextField();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hostel Client");
        
        // Student Panel Layout
        lblStudentID.setText("Student ID:");
        lblPassword.setText("Password:");
        lblStudentName.setText("Full Name:");
        lblCourse.setText("Course:");
        lblEmergencyContact.setText("Emergency Contact:");
        
        btnFindStudent.setText("Find Student");
        btnFindStudent.addActionListener(evt -> btnFindStudentActionPerformed(evt));
        
        btnSaveStudent.setText("Save Student");
        btnSaveStudent.addActionListener(evt -> btnSaveStudentActionPerformed(evt));
        
        btnDeleteStudent.setText("Delete Student");
        btnDeleteStudent.addActionListener(evt -> btnDeleteStudentActionPerformed(evt));
        
        javax.swing.GroupLayout studentPanelLayout = new javax.swing.GroupLayout(studentPanel);
        studentPanel.setLayout(studentPanelLayout);
        studentPanelLayout.setHorizontalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStudentID)
                    .addComponent(lblPassword)
                    .addComponent(lblStudentName)
                    .addComponent(lblCourse)
                    .addComponent(lblEmergencyContact))
                .addGap(30, 30, 30)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtStudentID)
                    .addComponent(txtPassword)
                    .addComponent(txtStudentName)
                    .addComponent(txtCourse)
                    .addComponent(txtEmergencyContact, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnFindStudent)
                .addGap(18, 18, 18)
                .addComponent(btnSaveStudent)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteStudent)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        studentPanelLayout.setVerticalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStudentID)
                    .addComponent(txtStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStudentName)
                    .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCourse)
                    .addComponent(txtCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmergencyContact)
                    .addComponent(txtEmergencyContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFindStudent)
                    .addComponent(btnSaveStudent)
                    .addComponent(btnDeleteStudent))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        
        // Payment Panel Layout
        lblPayStudentID.setText("Student ID:");
        lblAmount.setText("Amount:");
        lblPaymentStatus.setText("Payment Status:");
        
        btnCheckPayment.setText("Check Payment");
        btnCheckPayment.addActionListener(evt -> btnCheckPaymentActionPerformed(evt));
        
        btnRecordPayment.setText("Record Payment");
        btnRecordPayment.addActionListener(evt -> btnRecordPaymentActionPerformed(evt));
        
        javax.swing.GroupLayout paymentPanelLayout = new javax.swing.GroupLayout(paymentPanel);
        paymentPanel.setLayout(paymentPanelLayout);
        paymentPanelLayout.setHorizontalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPayStudentID)
                    .addComponent(lblAmount)
                    .addComponent(lblPaymentStatus))
                .addGap(30, 30, 30)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPayStudentID)
                    .addComponent(txtAmount)
                    .addComponent(txtPaymentStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(btnCheckPayment)
                .addGap(18, 18, 18)
                .addComponent(btnRecordPayment)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paymentPanelLayout.setVerticalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPayStudentID)
                    .addComponent(txtPayStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAmount)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaymentStatus)
                    .addComponent(txtPaymentStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCheckPayment)
                    .addComponent(btnRecordPayment))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        
        jTabbedPane1.addTab("Student Operations", studentPanel);
        jTabbedPane1.addTab("Payment Operations", paymentPanel);
        
        txtStatus.setEditable(false);
        txtStatus.setText("Initializing...");
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addComponent(txtStatus)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        
        pack();
    }
    // </editor-fold>
    
    /**
     * Find student by ID and password
     */
    private void btnFindStudentActionPerformed(java.awt.event.ActionEvent evt) {
        if (dotacceptFlag) {
            go();
            dotacceptFlag = false;
        }
        
        serialObj.clearData();
        serialObj.operationType = "STUDENT";
        serialObj.studentID = Integer.parseInt(txtStudentID.getText());
        serialObj.password = new String(txtPassword.getPassword());
        serialObj.flagFind = true;
        
        try {
            clientObjectStreamWriter.writeObject(serialObj);
            clientObjectStreamWriter.flush();
            dotacceptFlag = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Save new student
     */
    private void btnSaveStudentActionPerformed(java.awt.event.ActionEvent evt) {
        if (dotacceptFlag) {
            go();
            dotacceptFlag = false;
        }
        
        serialObj.clearData();
        serialObj.operationType = "STUDENT";
        serialObj.studentID = Integer.parseInt(txtStudentID.getText());
        serialObj.fullName = txtStudentName.getText();
        serialObj.courseOfStudy = txtCourse.getText();
        serialObj.emergencyContactPhone = txtEmergencyContact.getText();
        serialObj.password = new String(txtPassword.getPassword());
        serialObj.flagSave = true;
        
        try {
            clientObjectStreamWriter.writeObject(serialObj);
            clientObjectStreamWriter.flush();
            dotacceptFlag = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Delete student
     */
    private void btnDeleteStudentActionPerformed(java.awt.event.ActionEvent evt) {
        if (dotacceptFlag) {
            go();
            dotacceptFlag = false;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", 
                                                   "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            serialObj.clearData();
            serialObj.operationType = "STUDENT";
            serialObj.studentID = Integer.parseInt(txtStudentID.getText());
            serialObj.flagDelete = true;
            
            try {
                clientObjectStreamWriter.writeObject(serialObj);
                clientObjectStreamWriter.flush();
                dotacceptFlag = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Check payment status
     */
    private void btnCheckPaymentActionPerformed(java.awt.event.ActionEvent evt) {
        serialObj.clearData();
        serialObj.operationType = "PAYMENT";
        serialObj.studentID = Integer.parseInt(txtPayStudentID.getText());
        serialObj.flagFind = true;
        
        try {
            clientObjectStreamWriter.writeObject(serialObj);
            clientObjectStreamWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Record new payment
     */
    private void btnRecordPaymentActionPerformed(java.awt.event.ActionEvent evt) {
        serialObj.clearData();
        serialObj.operationType = "PAYMENT";
        serialObj.studentID = Integer.parseInt(txtPayStudentID.getText());
        serialObj.amount = Double.parseDouble(txtAmount.getText());
        serialObj.paymentStatus = txtPaymentStatus.getText();
        serialObj.flagSave = true;
        
        try {
            clientObjectStreamWriter.writeObject(serialObj);
            clientObjectStreamWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            c = new Client();
            c.setVisible(true);
            c.setLocationRelativeTo(null);
        });
    }
    
    // Variables declaration
    private javax.swing.JButton btnCheckPayment;
    private javax.swing.JButton btnDeleteStudent;
    private javax.swing.JButton btnFindStudent;
    private javax.swing.JButton btnRecordPayment;
    private javax.swing.JButton btnSaveStudent;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblCourse;
    private javax.swing.JLabel lblEmergencyContact;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPayStudentID;
    private javax.swing.JLabel lblPaymentStatus;
    private javax.swing.JLabel lblStudentID;
    private javax.swing.JLabel lblStudentName;
    private javax.swing.JPanel paymentPanel;
    private javax.swing.JPanel studentPanel;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtCourse;
    private javax.swing.JTextField txtEmergencyContact;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPayStudentID;
    private javax.swing.JTextField txtPaymentStatus;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtStudentID;
    private javax.swing.JTextField txtStudentName;
    // End of variables declaration
}