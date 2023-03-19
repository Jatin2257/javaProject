package jatin;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MaterialServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Display the user interface using HTML forms
        out.println("<html><body>");
        out.println("<h2>Book Detail</h2>");
        out.println("<form action=\"MaterialServlet\" method=\"post\">");
        out.println("Bid: <input type=\"text\" name=\"Bid\"><br>");
        out.println("title: <input type=\"text\" name=\"title\"><br>");
        out.println("author: <input type=\"text\" name=\"author\"><br>");
        out.println("pages: <input type=\"text\" name=\"pages\"><br>");
        out.println("publisher: <input type=\"text\" name=\"publisher\"><br>");
        out.println("language: <input type=\"text\" name=\"language\"><br>");
        out.println("year: <input type=\"text\" name=\"year\"><br>");
        out.println("Search : <input type=\"text\" name=\"search\"><br>");
        out.println("Operation:");
        out.println("<select name=\"operation\">");
        out.println("<option value=\"add\">Add</option>");
        out.println("<option value=\"view\">View</option>");
        out.println("<option value=\"modify\">Modify</option>");
        out.println("<option value=\"delete\">Delete</option>");
        out.println("</select><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Retrieve the user's input
         String BidStr = request.getParameter("Bid");
        int Bid;
         if (BidStr == null || BidStr.isEmpty()) {
            Bid = 0;
        } else {
            Bid = Integer.parseInt(BidStr);
        }
        String Title = request.getParameter("title");
        String Author = request.getParameter("author");
        int pages = Integer.parseInt(request.getParameter("pages"));
        String publisher = request.getParameter("publisher");
        int year = Integer.parseInt(request.getParameter("year"));
        String language = request.getParameter("language");
        String operation = request.getParameter("operation");
        String search = request.getParameter("search");

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = Driver.getConnection();
            stmt = conn.createStatement();

            // Perform the appropriate operation based on the selected option
            if (operation.equals("add")) {
                Driver.insertBookDetail(stmt, new Data(Bid, Title, Author, pages, publisher, year, language));
                out.println("<html><body>");
                out.println("<h2>Book Added Successfully!</h2>");
                out.println("</body></html>");
            } else if (operation.equals("view")) {
                ResultSet rs = Driver.searchData(stmt, search);
                String searchResult = Driver.getSearchResult(rs);
                out.println("<html><body>");
                out.println("<h2>Book Details</h2>");
                out.println("<pre>" + searchResult + "</pre>");
                out.println("</body></html>");
            } else if (operation.equals("modify")) {
                Driver.updateBookDetail(stmt, new Data(Bid, Title, Author, pages, publisher, year, language));
                out.println("<html><body>");
                out.println("<h2>Book Modified Successfully!</h2>");
                out.println("</body></html>");
            } else if (operation.equals("delete")) {
                Driver.deleteBookDetail(stmt, Bid);
                out.println("<html><body>");
                out.println("<h2>Book Deleted Successfully!</h2>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("<h2>Invalid operation!</h2>");
                out.println("</body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
