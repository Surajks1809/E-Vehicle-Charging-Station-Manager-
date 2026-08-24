import java.util.*;

public class ChargingStationManager {
    private Map<String, ChargingStation> stations;
    private Map<String, User> users;

    public ChargingStationManager() {
        this.stations = new HashMap<>();
        this.users = new HashMap<>();
    }

    public void addStation(ChargingStation station) {
        if (station == null) {
            System.out.println("Cannot add a null charging station.");
            return;
        }

        String stationId = station.getStationId();

        if (stations.containsKey(stationId)) {
            System.out.println(
                    "Station with ID " + stationId +
                    " already exists."
            );
            return;
        }

        stations.put(stationId, station);
        System.out.println(
                "Charging station " + stationId +
                " added successfully."
        );
    }

    public void addUser(User user) {
        if (user == null) {
            System.out.println("Cannot add a null user.");
            return;
        }

        String userId = user.getUserId();

        if (users.containsKey(userId)) {
            System.out.println(
                    "User with ID " + userId +
                    " already exists."
            );
            return;
        }

        users.put(userId, user);
        System.out.println(
                "User " + userId +
                " added successfully."
        );
    }

    public ChargingStation getStation(String stationId) {
        if (stationId == null || stationId.trim().isEmpty()) {
            return null;
        }

        return stations.get(stationId.trim().toUpperCase());
    }

    public User getUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return null;
        }

        return users.get(userId.trim());
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
            System.out.println(
                    "ID: " + station.getStationId()
                    + " | Location: " + station.getLocation()
                    + " | Available Slots: "
                    + station.getAvailableSlotsCount()
                    + "/" + station.getTotalSlots()
            );
        }
    }

    public void generateUsageReport() {
        System.out.println(
                "=== CHARGING STATION USAGE REPORT ==="
        );

        System.out.println(
                "Generated on: " +
                java.time.LocalDate.now()
        );

        System.out.println(
                "Total Registered Users: " + users.size()
        );

        System.out.println(
                "Total Stations: " + stations.size()
        );

        System.out.println();

        for (ChargingStation station : stations.values()) {
            System.out.println(
                    "Station: " +
                    station.getLocation() +
                    " (" + station.getStationId() + ")"
            );

            System.out.println(
                    "Total Slots: " +
                    station.getTotalSlots()
            );

            System.out.println(
                    "Available Slots: " +
                    station.getAvailableSlotsCount()
            );

            double utilization =
                    (1 - (double) station.getAvailableSlotsCount()
                    / station.getTotalSlots()) * 100;

            System.out.println(
                    "Utilization: " +
                    String.format("%.1f%%", utilization)
            );

            System.out.println("Current Bookings:");

            boolean hasBookings = false;

            for (ChargingSlot slot : station.getSlots()) {
                if (!slot.isAvailable()) {
                    hasBookings = true;

                    System.out.println(
                            "  - Slot " +
                            slot.getSlotNumber() +
                            ": " +
                            slot.getBookedBy().getName() +
                            " (" +
                            slot.getDurationHours() +
                            " hours)"
                    );
                }
            }

            if (!hasBookings) {
                System.out.println(
                        "  No current bookings"
                );
            }

            System.out.println(
                    "-----------------------------"
            );
        }
    }
}