package jatin;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.sql.*;
import java.util.*;



/**
 *
 * @author katar
 */
public class JavaApplication1 {

    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        //Class.forName(com.mysql.jdbc.Driver);
        String url = "jdbc:mysql://localhost:3306/library_managment";
        String uname = "root";
        String pass = "Jatin@123";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,uname,pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select*from library_managment.student1");
        rs.next();
        
        int rollno = rs.getInt("rollno");
        String Sname = rs.getString("Sname");
        
        System.out.println("Roll_no " + + rollno);
        rs.next();
        System.out.println("Name " + Sname);
        
        st.close();
        con.close();
    }
    
}
