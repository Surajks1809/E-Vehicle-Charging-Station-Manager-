import java.util.ArrayList;
import java.util.List;

public class ChargingStation {
    private String stationId;
    private String location;
    private int totalSlots;
    private double powerRating;
    private double ratePerKwh;
    private List<ChargingSlot> slots;

    private static final double DEFAULT_RATE_PER_KWH = 0.15;

    public ChargingStation(String stationId, String location,
                           int totalSlots, double powerRating) {
        this(stationId, location, totalSlots, powerRating,
                DEFAULT_RATE_PER_KWH);
    }

    public ChargingStation(String stationId, String location,
                           int totalSlots, double powerRating,
                           double ratePerKwh) {

        if (stationId == null || stationId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Station ID cannot be empty.");
        }

        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Station location cannot be empty.");
        }

        if (totalSlots <= 0) {
            throw new IllegalArgumentException(
                    "Total slots must be greater than zero.");
        }

        if (powerRating <= 0) {
            throw new IllegalArgumentException(
                    "Power rating must be greater than zero.");
        }

        if (ratePerKwh <= 0) {
            throw new IllegalArgumentException(
                    "Charging rate must be greater than zero.");
        }

        this.stationId = stationId.trim().toUpperCase();
        this.location = location.trim();
        this.totalSlots = totalSlots;
        this.powerRating = powerRating;
        this.ratePerKwh = ratePerKwh;
        this.slots = new ArrayList<>();

        for (int i = 1; i <= totalSlots; i++) {
            slots.add(new ChargingSlot(i));
        }
    }

    public boolean bookSlot(User user, int slotNumber,
                            int durationHours) {

        if (user == null) {
            System.out.println(
                    "A valid user is required for booking.");
            return false;
        }

        if (slotNumber < 1 || slotNumber > totalSlots) {
            System.out.println("Invalid slot number.");
            return false;
        }

        if (durationHours <= 0) {
            System.out.println(
                    "Duration must be greater than 0 hours.");
            return false;
        }

        ChargingSlot slot = slots.get(slotNumber - 1);

        if (slot.isAvailable()) {
            slot.bookSlot(user, durationHours);

            double cost = calculateCost(durationHours);

            System.out.println(
                    "Slot " + slotNumber +
                    " booked successfully for " +
                    durationHours + " hours.");

            System.out.printf(
                    "Estimated cost: $%.2f%n", cost);

            System.out.println(
                    "Booking time: " +
                    slot.getBookingTimeRange());

            return true;
        }

        System.out.println(
                "Slot " + slotNumber +
                " is already occupied.");

        System.out.println(
                "Current status: " +
                slot.getDetailedStatus());

        return false;
    }

    public boolean cancelSlot(int slotNumber, User user) {
        if (slotNumber < 1 || slotNumber > totalSlots) {
            System.out.println("Invalid slot number.");
            return false;
        }

        if (user == null) {
            System.out.println(
                    "A valid user is required.");
            return false;
        }

        ChargingSlot slot = slots.get(slotNumber - 1);

        if (!slot.isAvailable()
                && user.equals(slot.getBookedBy())) {

            slot.cancelSlot();

            System.out.println(
                    "Booking cancelled successfully.");

            return true;
        }

        System.out.println(
                "No booking found for this user " +
                "in the specified slot.");

        return false;
    }

    public double calculateCost(int durationHours) {
        if (durationHours <= 0) {
            System.out.println(
                    "Duration must be greater than 0 hours.");
            return 0.0;
        }

        double energyConsumed =
                powerRating * durationHours;

        double cost =
                energyConsumed * ratePerKwh;

        System.out.println(
                "Cost Calculation Details:");

        System.out.println(
                "  Power Rating: " +
                powerRating + " kW");

        System.out.println(
                "  Duration: " +
                durationHours + " hours");

        System.out.println(
                "  Energy Consumed: " +
                String.format("%.2f", energyConsumed) +
                " kWh");

        System.out.println(
                "  Rate: $" +
                String.format("%.2f", ratePerKwh) +
                " per kWh");

        System.out.printf(
                "  Total Cost: $%.2f%n", cost);

        return cost;
    }

    // Overloaded method for backward compatibility
    public double calculateCost(int slotNumber,
                                int durationHours) {
        return calculateCost(durationHours);
    }

    public void displayStationInfo() {
        System.out.println(
                "╔═══════════════════════════════════════╗");

        System.out.println(
                "║ Station: " + stationId +
                " - " + location + " ║");

        System.out.println(
                "║ Power: " + powerRating +
                " kW | Rate: $" +
                ratePerKwh + "/kWh ║");

        System.out.println(
                "║ Slots: " +
                getAvailableSlotsCount() +
                "/" + totalSlots +
                " available          ║");

        System.out.println(
                "╚═══════════════════════════════════════╝");

        System.out.println("\n📊 SLOT STATUS:");

        for (ChargingSlot slot : slots) {
            System.out.println(
                    slot.getDetailedStatus());

            System.out.println(
                    "   ─────────────────────────");
        }
    }

    public void displayAvailableSlotsWithTime() {
        System.out.println(
                "\n🕒 AVAILABLE SLOTS WITH TIMING:");

        boolean hasAvailable = false;

        for (ChargingSlot slot : slots) {
            if (slot.isAvailable()) {
                hasAvailable = true;

                System.out.println(
                        "   Slot " +
                        slot.getSlotNumber() +
                        " - ✅ READY TO BOOK");
            }
        }

        if (!hasAvailable) {
            System.out.println(
                    "   No available slots at the moment");
        }

        System.out.println("\n⏳ OCCUPIED SLOTS:");

        boolean hasOccupied = false;

        for (ChargingSlot slot : slots) {
            if (!slot.isAvailable()) {
                hasOccupied = true;

                System.out.println(
                        "   " +
                        slot.getDetailedStatus());
            }
        }

        if (!hasOccupied) {
            System.out.println(
                    "   No occupied slots");
        }
    }

    public int getAvailableSlotsCount() {
        int count = 0;

        for (ChargingSlot slot : slots) {
            if (slot.isAvailable()) {
                count++;
            }
        }

        return count;
    }

    // Getters
    public String getStationId() {
        return stationId;
    }

    public String getLocation() {
        return location;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public double getPowerRating() {
        return powerRating;
    }

    public List<ChargingSlot> getSlots() {
        return slots;
    }

    public double getRatePerKwh() {
        return ratePerKwh;
    }

    public ChargingSlot getSlot(int slotNumber) {
        if (slotNumber >= 1 &&
                slotNumber <= totalSlots) {

            return slots.get(slotNumber - 1);
        }

        return null;
    }
}