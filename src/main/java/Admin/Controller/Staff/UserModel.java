package Admin.Controller.Staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class UserModel {
    private static ObservableList<User> users = FXCollections.observableArrayList();

    public static void addUser(User user) {
        users.add(user);
    }

    public static ObservableList<User> getUsers() {
        return users;
    }

    public static void removeSelectedUser() {
        ObservableList<User> newUsersList = users.stream().filter(user -> !user.getSelect().isSelected())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        users.setAll(newUsersList);
    }
}
