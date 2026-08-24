import java.util.*;

public class ChargingStationManager {
    private Map<String, ChargingStation> stations;
    private Map<String, User> users;
    
    public ChargingStationManager() {
        this.stations = new HashMap<>();
        this.users = new HashMap<>();
    }
    
    public void addStation(ChargingStation station) {
        stations.put(station.getStationId(), station);
    }
    
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }
    
    public ChargingStation getStation(String stationId) {
        return stations.get(stationId);
    }
    
    public User getUser(String userId) {
        return users.get(userId);
    }
    
    public List<ChargingStation> getAllStations() {
        return new ArrayList<>(stations.values());
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    public void displayAllStationIDs() {
        System.out.println("\n=== All Charging Stations ===");
        if (stations.isEmpty()) {
            System.out.println("No stations available.");
            return;
        }
        
        for (ChargingStation station : stations.values()) {
            System.out.println("ID: " + station.getStationId() + 
                             " | Location: " + station.getLocation() +
                             " | Available Slots: " + station.getAvailableSlotsCount() + "/" + station.getTotalSlots());
        }
    }
    
    public void generateUsageReport() {
        System.out.println("=== CHARGING STATION USAGE REPORT ===");
        System.out.println("Generated on: " + java.time.LocalDate.now());
        System.out.println("Total Registered Users: " + users.size());
        System.out.println("Total Stations: " + stations.size());
        System.out.println();
        
        for (ChargingStation station : stations.values()) {
            System.out.println("Station: " + station.getLocation() + " (" + station.getStationId() + ")");
            System.out.println("Total Slots: " + station.getTotalSlots());
            System.out.println("Available Slots: " + station.getAvailableSlotsCount());
            System.out.println("Utilization: " + 
                String.format("%.1f%%", (1 - (double)station.getAvailableSlotsCount()/station.getTotalSlots()) * 100));
            
            System.out.println("Current Bookings:");
            boolean hasBookings = false;
            for (ChargingSlot slot : station.getSlots()) {
                if (!slot.isAvailable()) {
                    hasBookings = true;
                    System.out.println("  - Slot " + slot.getSlotNumber() + 
                                     ": " + slot.getBookedBy().getName() +
                                     " (" + slot.getDurationHours() + " hours)");
                }
            }
            if (!hasBookings) {
                System.out.println("  No current bookings");
            }
            System.out.println("-----------------------------");
        }
    }
}