package lk.ijse.gdse68.studentmanagment.DAO;

import lk.ijse.gdse68.studentmanagment.DTO.StudentDTO;
import lk.ijse.gdse68.studentmanagment.Student;

import java.sql.Connection;
import java.sql.SQLException;

public sealed interface  StudentDAO permits StudentDAOImpl {
    String  savaStudent(StudentDTO studentDTO, Connection connection);
    boolean deleteStudent(String id,Connection connection) throws SQLException;
    boolean updateStudent(String id, StudentDTO studentDTO,Connection connection) throws SQLException;

    StudentDTO getStudent(String id,Connection connection) throws SQLException;

}
