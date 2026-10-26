package com.sreya.studentmanagement.dao;
import com.sreya.studentmanagement.model.Student;
import com.sreya.studentmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class StudentDAO {
    String getAllStudentsByIdSql = "SELECT * FROM students ORDER BY id ASC";
    String getStudentsByIdSql = "SELECT * FROM students WHERE id=?";
    String addStudentSql = "INSERT INTO students(id,name,course,age) VALUES (?,?,?,?)";

    String updateStudentSql = "UPDATE students SET name=?, course=?, age=? WHERE id=?";

    String deleteStudentSql = "DELETE FROM students WHERE id=?";

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllStudentsByIdSql);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String course = resultSet.getString("course");
                int age = resultSet.getInt("age");

                Student student = new Student(id, name, course, age);
                students.add(student);
            }
        }
        return students;
    }


    public Student getStudentById(int id) throws SQLException {
        Student student=null;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(getStudentsByIdSql);)
        {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery())
            {
                if(rs.next())
                {
                    int studentId = rs.getInt("id");
                    String studentName = rs.getString("name");
                    String studentCourse = rs.getString("course");
                    int studentAge=rs.getInt("age");
                    student = new Student(studentId,studentName,studentCourse,studentAge);
                }

            }
        }
        return student;
    }

    public boolean addStudent(Student student) throws SQLException
    {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(addStudentSql))
        {
            ps.setInt(1, student.getStudentID());
            ps.setString(2, student.getStudentName());
            ps.setString(3, student.getStudentCourse());
            ps.setInt(4, student.getStudentAge());

            int rowsAffected = ps.executeUpdate();
//            return rowsAffected ==1; effective way of writing below code!
            if(rowsAffected==1)
            {
                return true;
            }
            else {
                return false;
            }
       }
    }

    public boolean updateStudent(Student student) throws SQLException
    {
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(updateStudentSql)) {

            ps.setString(1, student.getStudentName());
            ps.setString(2, student.getStudentCourse());
            ps.setInt(3, student.getStudentAge());
            ps.setInt(4, student.getStudentID());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1)
            {
                return true;
            }
            else {
                return false;
            }
        }
    }


    public boolean deleteStudent(int id) throws SQLException
    {
        try(Connection connection =  DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(deleteStudentSql);)
        {

            ps.setInt(1,id);
            int rowsAffected = ps.executeUpdate();

            if(rowsAffected==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

    }
}
