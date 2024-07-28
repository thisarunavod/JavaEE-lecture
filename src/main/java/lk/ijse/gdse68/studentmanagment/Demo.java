package lk.ijse.gdse68.studentmanagment;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.studentmanagment.DTO.StudentDTO;
import lk.ijse.gdse68.studentmanagment.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/demo")
public class Demo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        List<StudentDTO> studentList = jsonb.fromJson(req.getReader(), new ArrayList<StudentDTO>() {
        }.getClass()
                .getGenericSuperclass());

        for (StudentDTO list:studentList
             ) {
            list.setId(Util.IdGenerate());
        }
        studentList.forEach(System.out::println);

    }
}
