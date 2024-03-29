package Admin.Model.Event;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EventModelDashboard {
    private final StringProperty name;
    private final StringProperty occurDate;

    public EventModelDashboard(String name, LocalDate occurDate) {
        this.name = new SimpleStringProperty(name);
        this.occurDate = new SimpleStringProperty(occurDate.toString());
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getOccurDate() {
        return occurDate.get();
    }

    public void setOccurDate(String occurDate) {
        this.occurDate.set(occurDate);
    }

    public StringProperty occurDateProperty() {
        return occurDate;
    }
}
