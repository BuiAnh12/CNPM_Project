package Admin.Controller.Staff;

import Admin.Model.Staff.Staff;

import java.util.List;

public interface IStaff {
    Staff setConnecter(String username, String password);
    List<Staff> getAllStaff();
    boolean updateStaff(Staff staff);
    boolean addStaff(Staff staff);
    boolean updateFlag(boolean flag, int staffId);

    boolean deleteStaffSelected(List<Staff> staffList);

    boolean updateAllFlag(boolean flag);

    List<Staff> getAllStaffSelected();
}
