package Admin.Model.Event;
import java.time.LocalDate;
public class EventModel {
    private int eventId;
    private String eventName;
    private String place;
    private String organizationName;
    private int numberOfAttendance;
    private LocalDate deadline;

    private LocalDate occurDate;

    private int maxSlot;

    private  boolean status;

    private int checkBy =  -1;

    private int createBy;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(int checkBy) {
        this.checkBy = checkBy;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public LocalDate getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(LocalDate occurDate) {
        this.occurDate = occurDate;
    }

    public int getMaxSlot() {
        return maxSlot;
    }

    public void setMaxSlot(int maxSlot) {
        this.maxSlot = maxSlot;
    }

    public String getDetail() {
        return detail;
    }

    public EventModel(int eventId, String eventName, String place, String organizationName, int numberOfAttendance, LocalDate deadline, LocalDate occurDate, int maxSlot, String detail) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.place = place;
        this.organizationName = organizationName;
        this.numberOfAttendance = numberOfAttendance;
        this.deadline = deadline;
        this.occurDate = occurDate;
        this.maxSlot = maxSlot;
        this.detail = detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String detail;


    // Constructor
    public EventModel(int eventId, String eventName, String place, String organizationName,
                      int numberOfAttendance, LocalDate deadline) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.place = place;
        this.organizationName = organizationName;
        this.numberOfAttendance = numberOfAttendance;
        this.deadline = deadline;
    }

    public EventModel(int eventId, String eventName, String place, String organizationName, int numberOfAttendance, LocalDate deadline, LocalDate occurDate, int maxSlot, boolean status, int createBy, int checkBy, String detail) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.place = place;
        this.organizationName = organizationName;
        this.numberOfAttendance = numberOfAttendance;
        this.deadline = deadline;
        this.occurDate = occurDate;
        this.maxSlot = maxSlot;
        this.status = status;
        this.createBy = createBy;
        this.checkBy = checkBy;
        this.detail = detail;
    }

    // Getters and setters


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getNumberOfAttendance() {
        return numberOfAttendance;
    }

    public void setNumberOfAttendance(int numberOfAttendance) {
        this.numberOfAttendance = numberOfAttendance;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", place='" + place + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", numberOfAttendance=" + numberOfAttendance +
                ", deadline=" + deadline +
                '}';
    }
}
