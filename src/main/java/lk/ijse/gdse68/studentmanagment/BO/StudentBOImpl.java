package lk.ijse.gdse68.studentmanagment.BO;

import lk.ijse.gdse68.studentmanagment.DAO.StudentDAOImpl;
import lk.ijse.gdse68.studentmanagment.DTO.StudentDTO;

import java.sql.Connection;

public class StudentBOImpl implements StudentBO{


    @Override
    public String saveStudent(StudentDTO student, Connection connection) throws Exception {
        var studentDAOImpl = new StudentDAOImpl();
        return studentDAOImpl.savaStudent(student, connection);
    }

    @Override
    public boolean deleteStudent(String id, Connection connection) throws Exception {
        var studentDAOImpl = new StudentDAOImpl();
        return studentDAOImpl.deleteStudent(id, connection);
    }

    @Override
    public boolean updateStudent(String id, StudentDTO student, Connection connection) throws Exception {
        var studentDAOIMPL = new StudentDAOImpl();
        return studentDAOIMPL.updateStudent(id, student, connection);
    }

    @Override
    public StudentDTO getStudent(String id, Connection connection) throws Exception {
        var studentDAOImpl = new StudentDAOImpl();
        return studentDAOImpl.getStudent(id, connection);
    }
}
