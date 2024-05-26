package Admin.Model.Event;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ReportModel {
    private final IntegerProperty totalEvent;
    private final IntegerProperty totalStudent;

    public ReportModel(int totalEvent, int totalStudent) {
        this.totalEvent = new SimpleIntegerProperty(totalEvent);
        this.totalStudent = new SimpleIntegerProperty(totalStudent);
    }

    public int getTotalEvent() {
        return totalEvent.get();
    }

    public IntegerProperty totalEventProperty() {
        return totalEvent;
    }

    public void setTotalEvent(int totalEvent) {
        this.totalEvent.set(totalEvent);
    }

    public int getTotalStudent() {
        return totalStudent.get();
    }

    public IntegerProperty totalStudentProperty() {
        return totalStudent;
    }

    public void setTotalStudent(int totalStudent) {
        this.totalStudent.set(totalStudent);
    }
}

