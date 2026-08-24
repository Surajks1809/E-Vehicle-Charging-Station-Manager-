import java.util.*;

public class EVChargingStationManager {
    private static Scanner scanner = new Scanner(System.in);
    private static ChargingStationManager stationManager = new ChargingStationManager();
    private static User currentUser = null;
    private static int userCounter = 1000; // For generating unique user IDs
    
    public static void main(String[] args) {
        initializeSampleData();
        showMainMenu();
    }
    
    private static void initializeSampleData() {
        // Create sample stations
        stationManager.addStation(new ChargingStation("ST001", "Downtown Plaza", 4, 7.5));
        stationManager.addStation(new ChargingStation("ST002", "Mall Parking", 6, 11.0));
        stationManager.addStation(new ChargingStation("ST003", "Highway Rest Area", 8, 22.0));
        
        // Create sample users
        stationManager.addUser(new User("U1001", "John Doe", "john@example.com", "555-0101"));
        stationManager.addUser(new User("U1002", "Jane Smith", "jane@example.com", "555-0102"));
        
        // Create sample bookings with different times
        ChargingStation station1 = stationManager.getStation("ST001");
        User user1 = stationManager.getUser("U1001");
        if (station1 != null && user1 != null) {
            station1.bookSlot(user1, 1, 2); // Book slot 1 for 2 hours
        }
        
        ChargingStation station2 = stationManager.getStation("ST002");
        User user2 = stationManager.getUser("U1002");
        if (station2 != null && user2 != null) {
            station2.bookSlot(user2, 3, 1); // Book slot 3 for 1 hour
        }
    }
    
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n" + "═".repeat(50));
            System.out.println("   ⚡ EV CHARGING STATION MANAGER ⚡");
            System.out.println("═".repeat(50));
            System.out.println("1. User Login/Register");
            System.out.println("2. View Available Stations");
            System.out.println("3. Book Charging Slot");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Generate Usage Report");
            System.out.println("6. Calculate Charging Cost");
            System.out.println("7. View Real-time Slot Status");
            System.out.println("8. Exit");
            
            if (currentUser != null) {
                System.out.println("--- Currently logged in as: " + currentUser.getName() + " ---");
            }
            
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    userLoginOrRegister();
                    break;
                case 2:
                    showAvailableStations();
                    break;
                case 3:
                    bookChargingSlot();
                    break;
                case 4:
                    cancelBooking();
                    break;
                case 5:
                    generateUsageReport();
                    break;
                case 6:
                    calculateChargingCost();
                    break;
                case 7:
                    viewRealTimeSlotStatus();
                    break;
                case 8:
                    System.out.println("Thank you for using EV Charging Station Manager!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void viewRealTimeSlotStatus() {
        System.out.println("\n" + "🕒".repeat(25));
        System.out.println("   REAL-TIME SLOT STATUS");
        System.out.println("🕒".repeat(25));
        
        List<ChargingStation> stations = stationManager.getAllStations();
        
        if (stations.isEmpty()) {
            System.out.println("No charging stations available.");
            return;
        }
        
        for (ChargingStation station : stations) {
            System.out.println("\n📍 " + station.getLocation() + " (" + station.getStationId() + ")");
            station.displayAvailableSlotsWithTime();
            System.out.println();
        }
        
        System.out.println("Last updated: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
    
    private static void userLoginOrRegister() {
        System.out.println("\n=== User Login / Registration ===");
        System.out.println("1. Login with existing ID");
        System.out.println("2. Register as new user");
        System.out.println("3. View all users");
        System.out.print("Choose an option: ");
        
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (option) {
            case 1:
                loginExistingUser();
                break;
            case 2:
                registerNewUser();
                break;
            case 3:
                displayAllUsers();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    
    private static void loginExistingUser() {
        System.out.print("Enter your User ID: ");
        String userId = scanner.nextLine();
        
        currentUser = stationManager.getUser(userId);
        if (currentUser != null) {
            System.out.println("Welcome back, " + currentUser.getName() + "!");
        } else {
            System.out.println("User ID not found. Please register as a new user.");
        }
    }
    
    private static void registerNewUser() {
        System.out.println("\n=== New User Registration ===");
        
        // Generate unique user ID
        String userId = "U" + (++userCounter);
        System.out.println("Your generated User ID: " + userId + " (Please save this for future login)");
        
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine();
        
        // Validate input
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            System.out.println("All fields are required. Registration failed.");
            return;
        }
        
        currentUser = new User(userId, name, email, phone);
        stationManager.addUser(currentUser);
        System.out.println("Registration successful! You are now logged in.");
        System.out.println("Your User ID: " + userId + " - Please remember this for future logins.");
    }
    
    private static void displayAllUsers() {
        System.out.println("\n=== Registered Users ===");
        List<User> users = stationManager.getAllUsers();
        
        if (users.isEmpty()) {
            System.out.println("No users registered yet.");
            return;
        }
        
        for (User user : users) {
            System.out.println("ID: " + user.getUserId() + 
                             " | Name: " + user.getName() + 
                             " | Email: " + user.getEmail() +
                             " | Phone: " + user.getPhone());
        }
    }
    
    private static void showAvailableStations() {
        System.out.println("\n=== Available Charging Stations ===");
        List<ChargingStation> stations = stationManager.getAllStations();
        
        if (stations.isEmpty()) {
            System.out.println("No charging stations available.");
            return;
        }
        
        for (ChargingStation station : stations) {
            station.displayStationInfo();
            System.out.println("═".repeat(50));
        }
    }
    
    private static void displayAvailableStationIDs() {
        System.out.println("\n=== Available Station IDs ===");
        List<ChargingStation> stations = stationManager.getAllStations();
        
        if (stations.isEmpty()) {
            System.out.println("No charging stations available.");
            return;
        }
        
        System.out.println("Station IDs you can choose from:");
        for (ChargingStation station : stations) {
            System.out.println("- " + station.getStationId() + " (" + station.getLocation() + 
                             ") - Rate: $" + station.getRatePerKwh() + "/kWh");
        }
        System.out.println(); // Empty line for better readability
    }
    
    private static void bookChargingSlot() {
        if (!checkUserLoggedIn()) return;
        
        System.out.println("\n=== Book Charging Slot ===");
        showAvailableStations();
        
        // Show available station IDs first
        displayAvailableStationIDs();
        
        System.out.print("Enter Station ID: ");
        String stationId = scanner.nextLine().toUpperCase(); // Convert to uppercase for consistency
        
        ChargingStation station = stationManager.getStation(stationId);
        if (station == null) {
            System.out.println("Station not found. Available stations:");
            displayAvailableStationIDs();
            return;
        }
        
        // Show available slots with timing info
        System.out.println("\n🕒 Available slots at " + station.getLocation() + ":");
        station.displayAvailableSlotsWithTime();
        
        System.out.print("Enter Slot Number: ");
        int slotNumber = scanner.nextInt();
        System.out.print("Enter Duration (hours): ");
        int duration = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        boolean success = station.bookSlot(currentUser, slotNumber, duration);
        if (success) {
            System.out.println("Booking completed successfully!");
        }
    }
    
    private static void cancelBooking() {
        if (!checkUserLoggedIn()) return;
        
        System.out.println("\n=== Cancel Booking ===");
        
        // Show user's current bookings first
        displayUsersCurrentBookings();
        
        System.out.print("Enter Station ID: ");
        String stationId = scanner.nextLine().toUpperCase();
        
        ChargingStation station = stationManager.getStation(stationId);
        if (station == null) {
            System.out.println("Station not found. Available stations:");
            displayAvailableStationIDs();
            return;
        }
        
        System.out.print("Enter Slot Number: ");
        int slotNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        boolean success = station.cancelSlot(slotNumber, currentUser);
        if (success) {
            System.out.println("Booking cancelled successfully!");
        } else {
            System.out.println("Failed to cancel booking. Please check the details.");
        }
    }
    
    private static void displayUsersCurrentBookings() {
        System.out.println("Your current bookings:");
        boolean hasBookings = false;
        
        List<ChargingStation> stations = stationManager.getAllStations();
        for (ChargingStation station : stations) {
            for (ChargingSlot slot : station.getSlots()) {
                if (!slot.isAvailable() && slot.getBookedBy().equals(currentUser)) {
                    hasBookings = true;
                    double cost = station.calculateCost(slot.getDurationHours());
                    System.out.println("📍 Station: " + station.getStationId() + 
                                     " (" + station.getLocation() + ")");
                    System.out.println("   Slot: " + slot.getSlotNumber());
                    System.out.println("   Time: " + slot.getBookingTimeRange());
                    System.out.println("   Duration: " + slot.getDurationHours() + " hours");
                    System.out.println("   Remaining: " + slot.getRemainingTime());
                    System.out.println("   Cost: $" + String.format("%.2f", cost));
                    System.out.println("   ─────────────────────────");
                }
            }
        }
        
        if (!hasBookings) {
            System.out.println("You have no current bookings.");
        }
        System.out.println(); // Empty line for better readability
    }
    
    private static void generateUsageReport() {
        System.out.println("\n=== Usage Report ===");
        stationManager.generateUsageReport();
    }
    
    private static void calculateChargingCost() {
        System.out.println("\n=== Calculate Charging Cost ===");
        
        // Show available station IDs first
        displayAvailableStationIDs();
        
        System.out.print("Enter Station ID: ");
        String stationId = scanner.nextLine().toUpperCase();
        
        ChargingStation station = stationManager.getStation(stationId);
        if (station == null) {
            System.out.println("Station not found. Available stations:");
            displayAvailableStationIDs();
            return;
        }
        
        System.out.print("Enter Duration (hours): ");
        int duration = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("\n=== COST CALCULATION ===");
        System.out.println("Station: " + station.getLocation() + " (" + stationId + ")");
        double cost = station.calculateCost(duration);
        System.out.printf("\n💳 TOTAL ESTIMATED COST: $%.2f%n", cost);
    }
    
    private static boolean checkUserLoggedIn() {
        if (currentUser == null) {
            System.out.println("Please login or register first (Option 1).");
            return false;
        }
        return true;
    }
}