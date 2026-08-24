import java.time.LocalDateTime;
import java.util.*;

public class UsageReportGenerator {
    private ChargingStationManager stationManager;
    
    public UsageReportGenerator(ChargingStationManager stationManager) {
        this.stationManager = stationManager;
    }
    
    public void generateDetailedReport() {
        System.out.println("\n=== DETAILED USAGE REPORT ===");
        System.out.println("Date: " + LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("=" .repeat(50));
        
        List<ChargingStation> stations = stationManager.getAllStations();
        double totalRevenue = 0;
        int totalBookings = 0;
        
        for (ChargingStation station : stations) {
            System.out.println("\nStation: " + station.getLocation());
            System.out.println("ID: " + station.getStationId());
            System.out.println("Power Rating: " + station.getPowerRating() + " kW");
            
            int stationBookings = 0;
            double stationRevenue = 0;
            
            for (ChargingSlot slot : station.getSlots()) {
                if (!slot.isAvailable()) {
                    stationBookings++;
                    double cost = station.calculateCost(slot.getSlotNumber(), slot.getDurationHours());
                    stationRevenue += cost;
                    
                    System.out.printf("  Slot %d: %s - %d hours - $%.2f%n",
                            slot.getSlotNumber(),
                            slot.getBookedBy().getName(),
                            slot.getDurationHours(),
                            cost);
                }
            }
            
            totalBookings += stationBookings;
            totalRevenue += stationRevenue;
            
            System.out.printf("Station Summary: %d bookings - $%.2f revenue%n", 
                    stationBookings, stationRevenue);
            System.out.println("-".repeat(40));
        }
        
        System.out.printf("\nTOTAL SUMMARY: %d bookings - $%.2f total revenue%n", 
                totalBookings, totalRevenue);
    }
}