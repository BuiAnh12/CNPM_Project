package Admin.Model.Staff;

import javax.management.relation.Role;
import java.util.List;

public class RoleModel {
    int id;
    String name;



    public RoleModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name; // Return the name for display in the ComboBox
    }
}
