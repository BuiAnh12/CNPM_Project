package Dao;

import Entity.Staff;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StaffIpml implements IStaff {
    private DBConnection db = new DBConnection();
    private ResultSet resultSet;
    @Override
    public Staff setConnecter(String username, String password) {
        Staff staff = null;
        String sql = "SELECT * FROM staff WHERE Username = ? AND Password = ?";
        try {
        db.initPrepar(sql);
        db.getPreparedStatement().setString(1, username);
        db.getPreparedStatement().setString(2, password);
        resultSet = db.executeSelect();
        if (resultSet.next()) {
            staff = new Staff();
            staff.setId(resultSet.getInt("StaffId"));
            staff.setPassword(resultSet.getString("Password"));
            staff.setUsername(resultSet.getString("Username"));
//            user.setDateOfBirth();
        }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
        return staff;
    }

    @Override
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff";
        try {
            db.initPrepar(sql);
            resultSet = db.executeSelect();
            while (resultSet.next()) {
                Staff staff = new Staff();
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(resultSet.getBoolean("Flag"));
                staff.setId(resultSet.getInt("StaffId"));

                staff.setPassword(resultSet.getString("Password"));
                staff.setUsername(resultSet.getString("Username"));
                staff.setDateOfBirth(resultSet.getDate("DOB"));
                staff.setFlag(checkBox);
                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        // Update the flag field of the Staff object when CheckBox value changes
                        updateFlag(newValue, staff.getId());
                    }
                });
                staff.setPhoneNumber(resultSet.getString("PhoneNumber"));
                staff.setPermissionId(resultSet.getInt("PermissionId"));
                staff.setName(resultSet.getString("Name"));
                staffList.add(staff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
        return staffList;
    }
    @Override
    public boolean updateFlag(boolean flag, int staffId) {
        String sql = "UPDATE staff " +
                "SET Flag = ? " +
                "WHERE StaffId = ?";
        try {
            db.initPrepar(sql);
            // Thiết lập các tham số cho câu lệnh SQL
            db.getPreparedStatement().setBoolean(1, flag);
            db.getPreparedStatement().setInt(2, staffId);
            // Thực thi truy vấn
            int rowsUpdated = db.getPreparedStatement().executeUpdate();
            // Trả về true nếu cập nhật thành công ít nhất một hàng
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về false nếu có lỗi xảy ra khi cập nhật
            return false;
        } finally {
            db.closeConnection(); // Đảm bảo kết nối được đóng sau khi sử dụng xong
        }
    }

    @Override
    public boolean updateAllFlag(boolean flag) {
        String sql = "UPDATE staff " +
                "SET Flag = ? ";
        try {
            db.initPrepar(sql);
            // Thiết lập các tham số cho câu lệnh SQL
            db.getPreparedStatement().setBoolean(1, flag);
            // Thực thi truy vấn
            int rowsUpdated = db.getPreparedStatement().executeUpdate();
            // Trả về true nếu cập nhật thành công ít nhất một hàng
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về false nếu có lỗi xảy ra khi cập nhật
            return false;
        } finally {
            db.closeConnection(); // Đảm bảo kết nối được đóng sau khi sử dụng xong
        }
    }

    @Override
    public List<Staff> getAllStaffSelected() {
        String selectSql = "SELECT * FROM staff WHERE Flag = 1";
        List<Staff> staffList = new ArrayList<>();
        try {
            db.initPrepar(selectSql);
            resultSet = db.executeSelect();

            // Lặp qua kết quả truy vấn để lấy ra danh sách ID nhân viên cần xóa
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setUsername(resultSet.getString("Username"));
                staff.setId(resultSet.getInt("StaffId"));
                staffList.add(staff);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(); // Đảm bảo kết nối được đóng sau khi sử dụng xong
        }
        return staffList;
    }
    @Override
    public boolean deleteStaffSelected(List<Staff> staffList) {

        String deleteSql = "DELETE FROM staff WHERE StaffId = ?";

        try {


            // Xóa các nhân viên trong danh sách
            db.initPrepar(deleteSql);

            for (Staff staff : staffList) {
                db.getPreparedStatement().setInt(1, staff.getId());
                db.getPreparedStatement().addBatch();
            }

            // Thực thi lô xử lý để xóa hàng loạt
            int[] affectedRows = db.getPreparedStatement().executeBatch();

            // Trả về true nếu tất cả các câu lệnh xóa đều thành công
            for (int rows : affectedRows) {
                if (rows <= 0) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.closeConnection(); // Đảm bảo kết nối được đóng sau khi sử dụng xong
        }
    }

    @Override
    public boolean updateStaff(Staff staff) {
        String sql = "UPDATE staff " +
                "SET Username = ?, Password = ?, PhoneNumber = ?, DOB = ?, Name = ? " +
                "WHERE StaffId = ?";
        try {
            db.initPrepar(sql);
            // Thiết lập các tham số cho câu lệnh SQL
            db.getPreparedStatement().setString(1, staff.getUsername());
            db.getPreparedStatement().setString(2, staff.getPassword());
            db.getPreparedStatement().setString(3, staff.getPhoneNumber());
            db.getPreparedStatement().setDate(4, new java.sql.Date(staff.getDateOfBirth().getTime())); // Chuyển đổi Date sang java.sql.Date
            db.getPreparedStatement().setString(5, staff.getName());
            db.getPreparedStatement().setInt(6, staff.getId());

            // Thực thi truy vấn
            int rowsUpdated = db.getPreparedStatement().executeUpdate();

            // Trả về true nếu cập nhật thành công ít nhất một hàng
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về false nếu có lỗi xảy ra khi cập nhật
            return false;
        } finally {
            db.closeConnection(); // Đảm bảo kết nối được đóng sau khi sử dụng xong
        }
    }
    @Override
    public boolean addStaff(Staff staff) {
        String sql = "INSERT INTO staff (Username, Password, PermissionId, PhoneNumber, DOB, Flag, Name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            db.initPrepar(sql);
            // Thiết lập các tham số cho câu lệnh SQL
            db.getPreparedStatement().setString(1, staff.getUsername());
            db.getPreparedStatement().setString(2, staff.getPassword());
            db.getPreparedStatement().setInt(3, 1);
            db.getPreparedStatement().setString(4, staff.getPhoneNumber());
            db.getPreparedStatement().setDate(5, new java.sql.Date(staff.getDateOfBirth().getTime()));
            // Thiết lập giá trị cho Flag tùy theo kiểu dữ liệu của cột Flag trong cơ sở dữ liệu
            db.getPreparedStatement().setBoolean(6, false); // Ví dụ: staff.getFlag().toString() là một chuỗi
            db.getPreparedStatement().setString(7, staff.getName());
            // Thực thi truy vấn
            db.getPreparedStatement().executeUpdate();

            // Trả về true nếu thêm nhân viên thành công
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về false nếu có lỗi xảy ra khi thêm nhân viên
            return false;
        } finally {
            db.closeConnection(); // Đảm bảo kết nối được đóng sau khi sử dụng xong
        }
    }
}
