import java.io.Serializable;

/**
 * SerializableClass for Hostel Management System
 * Used for client-server communication
 */
public class SerializableClass implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Student-related fields
    public int studentID;
    public String fullName;
    public String courseOfStudy;
    public String enrollmentDate;
    public String emergencyContactName;
    public String emergencyContactPhone;
    public String password;
    
    // Room-related fields
    public int roomID;
    public String roomNumber;
    public int roomTypeID;
    public String roomStatus;
    
    // Payment-related fields
    public int paymentID;
    public double amount;
    public String paymentDate;
    public String paymentMethod;
    public String paymentStatus;
    
    // Room Assignment fields
    public int assignmentID;
    public int termID;
    public String checkInDate;
    public String checkOutDate;
    public double rentAmount;
    
    // Operation flags
    public boolean flagSave;
    public boolean flagFind;
    public boolean flagDelete;
    public boolean flagUpdate;
    
    // Operation type identifier
    public String operationType; // "STUDENT", "ROOM", "PAYMENT", "ASSIGNMENT"
    
    // Response fields
    public boolean success;
    public String message;
    
    /**
     * Constructor - initializes all fields
     */
    public SerializableClass() {
        // Student fields
        studentID = 0;
        fullName = "";
        courseOfStudy = "";
        enrollmentDate = "";
        emergencyContactName = "";
        emergencyContactPhone = "";
        password = "";
        
        // Room fields
        roomID = 0;
        roomNumber = "";
        roomTypeID = 0;
        roomStatus = "";
        
        // Payment fields
        paymentID = 0;
        amount = 0.0;
        paymentDate = "";
        paymentMethod = "";
        paymentStatus = "";
        
        // Assignment fields
        assignmentID = 0;
        termID = 0;
        checkInDate = "";
        checkOutDate = "";
        rentAmount = 0.0;
        
        // Flags
        flagSave = false;
        flagFind = false;
        flagDelete = false;
        flagUpdate = false;
        
        // Operation type
        operationType = "";
        
        // Response
        success = false;
        message = "";
    }
    
    /**
     * Reset all flags
     */
    public void resetFlags() {
        flagSave = false;
        flagFind = false;
        flagDelete = false;
        flagUpdate = false;
    }
    
    /**
     * Clear all data
     */
    public void clearData() {
        studentID = 0;
        fullName = "";
        courseOfStudy = "";
        roomID = 0;
        roomNumber = "";
        amount = 0.0;
        resetFlags();
        operationType = "";
        success = false;
        message = "";
    }
}