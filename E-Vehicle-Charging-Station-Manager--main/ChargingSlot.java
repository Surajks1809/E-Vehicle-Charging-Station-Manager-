import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class ChargingSlot {
    private int slotNumber;
    private boolean isAvailable;
    private User bookedBy;
    private LocalDateTime bookingTime;
    private LocalDateTime endTime;
    private int durationHours;
    
    public ChargingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.isAvailable = true;
        this.bookedBy = null;
        this.bookingTime = null;
        this.endTime = null;
        this.durationHours = 0;
    }
    
    public void bookSlot(User user, int durationHours) {
        this.isAvailable = false;
        this.bookedBy = user;
        this.bookingTime = LocalDateTime.now();
        this.durationHours = durationHours;
        this.endTime = this.bookingTime.plusHours(durationHours);
    }
    
    public void cancelSlot() {
        this.isAvailable = true;
        this.bookedBy = null;
        this.bookingTime = null;
        this.endTime = null;
        this.durationHours = 0;
    }
    
    public String getRemainingTime() {
        if (isAvailable || endTime == null) {
            return "Available";
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(endTime)) {
            return "Completed";
        }
        
        Duration duration = Duration.between(now, endTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        
        return String.format("%d hours %d minutes remaining", hours, minutes);
    }
    
    public String getBookingTimeRange() {
        if (isAvailable || bookingTime == null || endTime == null) {
            return "Available";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return bookingTime.format(formatter) + " - " + endTime.format(formatter);
    }
    
    // Getters
    public int getSlotNumber() { return slotNumber; }
    public boolean isAvailable() { return isAvailable; }
    public User getBookedBy() { return bookedBy; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public int getDurationHours() { return durationHours; }
    
    public String getBookingInfo() {
        if (isAvailable) {
            return "Slot " + slotNumber + " is available";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return "Slot " + slotNumber + " booked by " + bookedBy.getName() + 
                   " at " + bookingTime.format(formatter) + 
                   " for " + durationHours + " hours" +
                   " (Ends: " + endTime.format(formatter) + ")";
        }
    }
    
    public String getDetailedStatus() {
        if (isAvailable) {
            return "Slot " + slotNumber + " - ✅ AVAILABLE";
        } else {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            return String.format(
                "Slot %d - ⏳ OCCUPIED by %s\n" +
                "        Time: %s (%d hours)\n" +
                "        Remaining: %s",
                slotNumber, 
                bookedBy.getName(),
                getBookingTimeRange(),
                durationHours,
                getRemainingTime()
            );
        }
    }
}