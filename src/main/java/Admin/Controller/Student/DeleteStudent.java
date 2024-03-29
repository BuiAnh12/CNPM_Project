package Admin.Controller.Student;

import Admin.Model.Student.StudentModel;

public class DeleteStudent {
    private StudentDAO studentDAO;

    public DeleteStudent() {
        this.studentDAO = new StudentDAO();
    }

    public void deleteStudent(StudentModel student) {
        String studentId = student.getStudentId(); // Lấy studentId từ đối tượng StudentModel
        studentDAO.deleteStudent(studentId); // Truyền studentId vào phương thức deleteStudent của StudentDAO
    }
}
