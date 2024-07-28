package lk.ijse.gdse68.studentmanagment;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.studentmanagment.DAO.StudentDAOImpl;
import lk.ijse.gdse68.studentmanagment.DTO.StudentDTO;
import lk.ijse.gdse68.studentmanagment.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(urlPatterns = "/student",loadOnStartup = 3) /*load onstrap eken wenne --> req ekak nattn eken servlet ekak hada gannawa */
public class Student extends HttpServlet {

    static Logger logger = LoggerFactory.getLogger(Student.class.getName());
    Connection connection;
    public static String SAVE_STUDENT = "INSERT INTO students(id,name,email,city,level) VALUES (?,?,?,?,?)";
    public static String GET_STUDENT = " select * from students where id = ? ";
    public static String UPDATE_STUDENT = " update students set name = ? , email = ?, city = ?,level = ? where id = ? ";
    public static String DELETE_STUDENT = " DELETE FROM students WHERE id = ? ";

    @Override
    public void init() throws ServletException {
        logger.info("Init method invoked");

        /*var initParameter = getServletContext().getInitParameter("myparam");
        System.out.println(initParameter);*/

        try {

            /*var dbClass          = getServletContext().getInitParameter("db-class");
            var dbUrl            = getServletContext().getInitParameter("dburl");
            var dbUsername       = getServletContext().getInitParameter("db-username");
            var dbPassword         = getServletContext().getInitParameter("db-password");
            Class.forName(dbClass);  */

            var ctx = new InitialContext();   // connection pool eke connection ekak balala aragena tya ganna tana //
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/studentRegisPortal"); //look up method ekata pluwn api resources ekaka name ekak dunnama eeke dewal ganna //
            this.connection = pool.getConnection();
            logger.info("Connection initialized",this.connection);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //To Save
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        /*from params*/
        /*var id = req.getParameter("id");
        var name = req.getParameter("name");
        var email = req.getParameter("email");
        var level = req.getParameter("level");

        System.out.println(id  + "   " + name);*/

        /*JSON*/
        /*

        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();

        reader.lines().forEach(line -> sb.append(line).append("\n"));
        System.out.println(sb); */


        /*Process the JSON*/
        /*JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();    //<----   Json Object hadala eekata read karagannawa//
        String email = jsonObject.getString("email");
        System.out.println(email);*/

        /*To send Data to Client*/
        /*PrintWriter writer = resp.getWriter();
        writer.write(email);

        */

        /*<<---- meka tmy entry point eka*/

        /*
        JsonReader reader = Json.createReader(req.getReader());
        JsonArray jsonArray = reader.readArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            var jsonObject = jsonArray.getJsonObject(i);
            System.out.println(jsonObject.getString("name"));
            System.out.println(jsonObject.getString("email"));
        }*/


        /*Object Binding of the Json */
        /*<--- Object Binding krddi mekata JSON b type eke handle karanna oona */
        /*Jsonb  jsonb = JsonbBuilder.create();
        StudentDTO student = jsonb.fromJson(req.getReader(), StudentDTO.class);
        student.setId(Util.IdGenerate());
        System.out.println(student);


                            // create response //
        //content type eka set karanawaa
        resp.setContentType("application/json") ;
        jsonb.toJson(student,resp.getWriter()) ;*/



        // 2024.7.14 //

        /*try(var writer = resp.getWriter()) {
            Jsonb  jsonb = JsonbBuilder.create();
            StudentDTO student = jsonb.fromJson(req.getReader(), StudentDTO.class);
            student.setId(Util.IdGenerate());
            // save data into DB
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1,student.getId());
            ps.setString(2,student.getName());
            ps.setString(3,student.getEmail());
            ps.setString(4,student.getCity());
            ps.setString(5,student.getLevel());

            if (ps.executeUpdate() != 0 ){
                resp.setStatus(HttpServletResponse.SC_CREATED);
                writer.write("Save Student Successfully !!");
            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("Faild Save Student  !!");
            }


            // create response //
            *//*resp.setContentType("application/json") ;
            jsonb.toJson(student,resp.getWriter()) ;*//*

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //layerd version
        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            var studentDAOIMPL = new StudentDAOImpl();
            StudentDTO studentDto = jsonb.fromJson(req.getReader(), StudentDTO.class);
            studentDto.setId(Util.IdGenerate());
            //Save data in the DB
            writer.write(studentDAOIMPL.savaStudent(studentDto,connection));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException   {
        // database eke values postman eke balanna //
        /*try (var writer = resp.getWriter()){

            var studentID =req.getParameter("id");
            Jsonb  jsonb = JsonbBuilder.create();
            StudentDTO dto = new StudentDTO();
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1,studentID);
            var rst = ps.executeQuery();

            while (rst.next()){
                dto.setId(rst.getString("id"));
                dto.setName(rst.getString("name"));
                dto.setEmail(rst.getString("email"));
                dto.setCity(rst.getString("city"));
                dto.setLevel(rst.getString("level"));
            }


            // create response //
            resp.setContentType("application/json") ;
            jsonb.toJson(dto,writer) ;  // DeSerlization and Serlization gana blnna


        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }*/

        // layerd version //

        try(var writer = resp.getWriter()) {
            var studentDAOImpl = new StudentDAOImpl();
            Jsonb jsonb = JsonbBuilder.create();
            //DB Process
            var studentId = req.getParameter("studentId");;
            resp.setContentType("application/json");
            jsonb.toJson(studentDAOImpl.getStudent(studentId,connection),writer);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //To Update
        /*try (var writer = resp.getWriter()){
            var studentID = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            StudentDTO dto = jsonb.fromJson(req.getReader(),StudentDTO.class);

            //SQL Process
            var ps = connection.prepareStatement(UPDATE_STUDENT);
            ps.setString(1,dto.getName());
            ps.setString(2,dto.getEmail());
            ps.setString(3,dto.getCity());
            ps.setString(4,dto.getLevel());
            ps.setString(5,studentID);

            if (ps.executeUpdate() != 0){
                writer.write("Update Student Successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Update failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }


        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }*/

        //Layerd type //

        try (var writer = resp.getWriter()) {
            var studentDAOImpl = new StudentDAOImpl();
            var studentId = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            StudentDTO studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class);
            if(studentDAOImpl.updateStudent(studentId,studentDTO,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Update failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // To Delete
        /*try (var writer = resp.getWriter()){
            var studentID = req.getParameter("id");

            //SQL Process
            var ps = connection.prepareStatement(DELETE_STUDENT);
            ps.setString(1,studentID);

            if (ps.executeUpdate() != 0){
                writer.write("Delete Student Successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Delete failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }


        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }*/

        //Layerd type //
        try (var writer = resp.getWriter()) {
            var studentId = req.getParameter("id");
            var studentDAOImpl = new StudentDAOImpl();
            if(studentDAOImpl.deleteStudent(studentId,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Delete failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }





    }

}
