package Exception;

import javax.swing.JOptionPane;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class InputHandle {

    // Method to validate if the input is a number
    public boolean isNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid input: Not a number");
            return false;
        }
    }

    // Method to validate if the input is a valid email
    public boolean isValidEmail(String input) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (pattern.matcher(input).matches()) {
            return true;
        } else {
            showErrorDialog("Invalid input: Not a valid email address");
            return false;
        }
    }

    // Method to validate if the input is a valid date
    public boolean isValidDate(String input, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(input);
            return true;
        } catch (ParseException e) {
            showErrorDialog("Invalid input: Not a valid date");
            return false;
        }
    }
    private static final String PHONE_NUMBER_REGEX = "^\\+?[0-9. ()-]{7,15}$";

    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        return phoneNumber.matches(PHONE_NUMBER_REGEX);
    }

    // Method to compare two dates
    public boolean compareDates(String date1, String date2, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            return d1.equals(d2);
        } catch (ParseException e) {
            showErrorDialog("Error comparing dates: Invalid date format");
            return false;
        }
    }

    // Method to show error dialog
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        // Example usage
        String inputNumber = "123a";
        String inputEmail = "testexamplecom";
        String inputDate1 = "2023-05-26";
        String inputDate2 = "2023-05-27";
        String dateFormat = "yyyy-MM-dd";
        InputHandle inputHandle = new InputHandle();
//        System.out.println("Is number: " + isNumber(inputNumber));
//        System.out.println("Is valid email: " + isValidEmail(inputEmail));
        System.out.println("Is valid date: " + inputHandle.isValidDate(inputDate1, dateFormat));
        System.out.println("Are dates equal: " + inputHandle.compareDates(inputDate1, inputDate2, dateFormat));
    }
}
