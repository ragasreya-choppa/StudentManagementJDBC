package com.sreya.studentmanagement;
import com.sreya.studentmanagement.model.Student;
import com.sreya.studentmanagement.dao.StudentDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main
{
    public static void main(String[]args)
    {
        // Check whether the database password id configured
        String password = System.getenv("DB_PASSWORD");

        if(password==null || password.isBlank())
        {
            System.out.println("Database password is not configured!");
            System.out.println("Please set the DB_PASSWORD environment variable.");
            return;
        }

        System.out.println("========== STUDENT MANAGEMENT SYSTEM ==========");
        StudentDAO dao = new StudentDAO();
        Scanner sc= new Scanner(System.in);

        boolean running=true;
        while(running)
        {
            System.out.println("Enter Your Choice-");
            System.out.println("1.Add Student");
            System.out.println("2.View All Students");
            System.out.println("3.Search Student by Id");
            System.out.println("4.Update Student");
            System.out.println("5.Delete Student");
            System.out.println("6.Exit");
            if(!sc.hasNextInt())
            {
                System.out.println("Invalid Choice! Enter a Number!");
                sc.nextLine();
                continue;
            }

            int userChoice = sc.nextInt();
            sc.nextLine();

            if(userChoice<=0)
            {
                System.out.println("Enter Positive Number Choices Only!");
                continue;
            }

            switch(userChoice)
            {
                case 1:
                    System.out.println("Add Student Selected!");
                    System.out.println("Enter Student ID to add- ");
                    if(!sc.hasNextInt())
                    {
                        System.out.println("Invalid ID! ID must be in Numbers!");
                        sc.nextLine();
                        break;
                    }
                    int userInputId = sc.nextInt();
                    sc.nextLine();
                    if(userInputId<=0)
                    {
                        System.out.println("Enter Positive ID's Only!");
                        break;
                    }

                    System.out.println("Enter Student Name to add- ");
                    String userInputName = sc.nextLine().trim();
                    if(userInputName.isEmpty())
                    {
                        System.out.println("Invalid Name! Try Entering Again ");
                        break;
                    }

                    System.out.println("Enter Student Course to add-");
                    String userInputCourse = sc.nextLine().trim();
                    if(userInputCourse.isEmpty())
                    {
                        System.out.println("Invalid Course! Try Entering Again");
                        break;
                    }

                    System.out.println("Enter Age to add- ");
                    if(!sc.hasNextInt())
                    {
                        System.out.println("Invalid Age! Try adding Age in Numbers");
                        sc.nextLine();
                        break;
                    }
                    int userInputAge = sc.nextInt();
                    sc.nextLine();

                    if(userInputAge<1 || userInputAge>120)
                    {
                        System.out.println("Age must be between 1 and 120");
                        break;
                    }

                    Student student = new Student(userInputId,userInputName,userInputCourse,userInputAge);

                    try
                    {
                        boolean added = dao.addStudent(student);
                        if(added)
                        {
                            System.out.println("Student Added SuccessFully!");
                        }
                        else {
                            System.out.println("Student Not Found!");
                        }
                    }
                    catch(SQLException e)
                    {
                        System.out.println("Error while adding Student!");
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("View All Students Selected!");
                    try
                    {
                        List<Student> gettingAllStudents = dao.getAllStudents();
                        if(gettingAllStudents.isEmpty())
                        {
                            System.out.println("No Students Found!");
                        }
                        else {
                            for(Student s:gettingAllStudents)
                            {
                                System.out.println("ID: "+ s.getStudentID() +" "+ "|" + " "+"Name: "+ s.getStudentName() +" "+ "|" + " "+"Course: "+ s.getStudentCourse() + " "+"|" + " "+"Age: "+ s.getStudentAge());
                                System.out.println("----------");
                            }
                        }
                    }
                    catch(SQLException e)
                    {
                        System.out.println("Error While Getting All Students!");
                        System.out.println(e);
                    }
                    break;
                case 3:
                    System.out.println("Search Student by ID Selected!");
                    System.out.println("Enter Id to Search:- ");
                    if(!sc.hasNextInt())
                    {
                        System.out.println("Enter Numbers to Search!");
                        sc.nextLine();
                        break;
                    }
                    int idToSearch = sc.nextInt();
                    sc.nextLine();

                    if(idToSearch<=0)
                    {
                        System.out.println("Enter Only Positive Id's!");
                        break;
                    }

                    try
                    {
                        Student searchById = dao.getStudentById(idToSearch);

                        if(searchById!=null)
                        {
                            System.out.println("Student Details:- ");
                            System.out.println("Student ID: "+searchById.getStudentID());
                            System.out.println("Student Name: "+searchById.getStudentName());
                            System.out.println("Student Course: "+ searchById.getStudentCourse());
                            System.out.println("Student Age: "+ searchById.getStudentAge());
                        }
                        else {
                            System.out.println("Student Not Found!");
                        }
                    }
                    catch(SQLException e)
                    {
                        System.out.println("Error While getting Student by ID");
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Update Student Selected!");
                    System.out.println("Enter Student ID to Update:- ");
                    if(!sc.hasNextInt())
                    {
                        System.out.println("Enter Only Number ID's to Update!");
                        sc.nextLine();
                        break;
                    }
                    int idUpdate = sc.nextInt();
                    sc.nextLine();

                    if(idUpdate<=0)
                    {
                        System.out.println("Enter only Positive Number ID's !");
                        break;
                    }
                    try{
                        Student studentToShow = dao.getStudentById(idUpdate);
                        if(studentToShow==null)
                        {
                            System.out.println("No Student Found!");
                            break;
                        }
                        System.out.println("Current Student Details-");
                        System.out.println("Student Name: "+studentToShow.getStudentName());
                        System.out.println("Student Course: "+studentToShow.getStudentCourse());
                        System.out.println("Student Age: "+studentToShow.getStudentAge());


                        System.out.println("Enter Your New Student Name to Update: ");
                        String nameToUpdate = sc.nextLine().trim();
                        if(nameToUpdate.isEmpty())
                        {
                            System.out.println("Enter Proper Student Name to Update!");
                            break;
                        }

                        System.out.println("Enter Your New Student Course to Update: ");
                        String courseToUpdate = sc.nextLine().trim();
                        if(courseToUpdate.isEmpty())
                        {
                            System.out.println("Enter Proper Student Course to Update!");
                            break;
                        }

                        System.out.println("Enter Your New Age to Update: ");
                        if(!sc.hasNextInt())
                        {
                            System.out.println("Enter only Numbers to Update Age!");
                            sc.nextLine();
                            break;
                        }
                        int ageToUpdate = sc.nextInt();
                        sc.nextLine();
                        if(ageToUpdate<1 || ageToUpdate>120)
                        {
                            System.out.println("Age Must be between 1 and 120");
                            break;
                        }
                        Student studentToUpdate = new Student(idUpdate,nameToUpdate,courseToUpdate,ageToUpdate);

                        boolean updated = dao.updateStudent(studentToUpdate);
                        if(updated)
                        {
                            System.out.println("Student Updated SuccessFully!");
                        }
                        else {
                            System.out.println("Student Not Found!");
                        }
                    }
                    catch(SQLException e)
                    {
                        System.out.println("Error while Updating Student!");
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Delete Student Selected!");
                    System.out.println("Enter Student ID to Delete:- ");
                    if(!sc.hasNextInt())
                    {
                        System.out.println("Enter valid number Student Id to Delete!");
                        sc.nextLine();
                        break;
                    }
                    int deleteId = sc.nextInt();
                    sc.nextLine();
                    if(deleteId<=0)
                    {
                        System.out.println("Enter Positive number Student Id's to Delete!");
                        break;
                    }
                    System.out.println("Are You Sure want to delete Student with this- " + " "+deleteId+ " "+"Student Id" + "? (yes/no)");
                    String confirmation = sc.nextLine().trim();
                    if(!confirmation.equalsIgnoreCase("yes"))
                    {
                        System.out.println("Student Deletion Cancelled!");
                        break;
                    }
                    try
                    {
                        boolean deleted = dao.deleteStudent(deleteId);
                        if(deleted)
                        {
                            System.out.println("Student Deleted SuccessFully!");
                        }
                        else {
                            System.out.println("Student Not Found!");
                        }
                    }
                    catch(SQLException e)
                    {
                        System.out.println("Error while Deleting Student!");
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Exiting Student Management");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid Choice! Please Select between 1-6 :-");
                    break;
            }

        }

    }
}
