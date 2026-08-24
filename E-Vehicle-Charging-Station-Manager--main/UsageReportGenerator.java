import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UsageReportGenerator {
    private ChargingStationManager stationManager;

    public UsageReportGenerator(ChargingStationManager stationManager) {
        if (stationManager == null) {
            throw new IllegalArgumentException(
                    "Station manager cannot be null."
            );
        }

        this.stationManager = stationManager;
    }

    public void generateDetailedReport() {
        System.out.println("\n=== DETAILED USAGE REPORT ===");

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println(
                "Date: " + LocalDateTime.now().format(formatter)
        );

        System.out.println("=".repeat(55));

        List<ChargingStation> stations =
                stationManager.getAllStations();

        if (stations.isEmpty()) {
            System.out.println(
                    "No charging stations available."
            );
            return;
        }

        double totalRevenue = 0;
        int totalBookings = 0;
        int totalSlots = 0;
        int totalAvailableSlots = 0;

        for (ChargingStation station : stations) {
            System.out.println(
                    "\nStation: " +
                    station.getLocation()
            );

            System.out.println(
                    "ID: " +
                    station.getStationId()
            );

            System.out.println(
                    "Power Rating: " +
                    station.getPowerRating() +
                    " kW"
            );

            int stationBookings = 0;
            double stationRevenue = 0;

            int stationTotalSlots =
                    station.getTotalSlots();

            int stationAvailableSlots =
                    station.getAvailableSlotsCount();

            totalSlots += stationTotalSlots;
            totalAvailableSlots += stationAvailableSlots;

            double stationUtilization =
                    (1 - (double) stationAvailableSlots
                    / stationTotalSlots) * 100;

            System.out.printf(
                    "Available Slots: %d/%d%n",
                    stationAvailableSlots,
                    stationTotalSlots
            );

            System.out.printf(
                    "Station Utilization: %.1f%%%n",
                    stationUtilization
            );

            for (ChargingSlot slot :
                    station.getSlots()) {

                if (!slot.isAvailable()) {
                    stationBookings++;

                    double cost =
                            station.calculateCost(
                                    slot.getDurationHours()
                            );

                    stationRevenue += cost;

                    System.out.printf(
                            "  Slot %d: %s - %d hours - $%.2f%n",
                            slot.getSlotNumber(),
                            slot.getBookedBy().getName(),
                            slot.getDurationHours(),
                            cost
                    );
                }
            }

            totalBookings += stationBookings;
            totalRevenue += stationRevenue;

            System.out.printf(
                    "Station Summary: %d bookings - " +
                    "$%.2f revenue%n",
                    stationBookings,
                    stationRevenue
            );

            System.out.println(
                    "-".repeat(45)
            );
        }

        double overallUtilization = 0;

        if (totalSlots > 0) {
            overallUtilization =
                    (1 - (double) totalAvailableSlots
                    / totalSlots) * 100;
        }

        System.out.println(
                "\n========== OVERALL SUMMARY =========="
        );

        System.out.printf(
                "Total Stations: %d%n",
                stations.size()
        );

        System.out.printf(
                "Total Slots: %d%n",
                totalSlots
        );

        System.out.printf(
                "Available Slots: %d%n",
                totalAvailableSlots
        );

        System.out.printf(
                "Occupied Slots: %d%n",
                totalSlots - totalAvailableSlots
        );

        System.out.printf(
                "Overall Utilization: %.1f%%%n",
                overallUtilization
        );

        System.out.printf(
                "Total Bookings: %d%n",
                totalBookings
        );

        System.out.printf(
                "Total Revenue: $%.2f%n",
                totalRevenue
        );

        System.out.println(
                "======================================"
        );
    }
}