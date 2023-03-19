package jatin;






import java.sql.*;

class Data{
    int Bid;
    String title;
    String author;
    int pages;
    String publisher;
    int year;
    String language;
    
    Data(int Bid,String title,String author,int pages,String publisher,int year,String language){
    this.Bid=Bid;
    this.title=title;
    this.author=author;
    this.pages=pages;
    this.publisher= publisher;
    this.year= year;
    this.language=language;
    
    
}
}

public class Driver{
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/library_managment";
    static final String uname = "root";
    static final String pass = "Jatin@123";
    
    public static Connection getConnection() throws Exception{
        Class.forName(JDBC_DRIVER);
        return DriverManager.getConnection(url,uname,pass);
    }
    
    static void createTable(Statement Stmt){

        try{
            String sql = "CREATE TABLE Book1 " +
            "(Bid Int not NULL, " +
            "Title VARCHAR(50), " +
            "author VARCHAR(50), " +
            "pages INTEGER, " +
            "publisher VARCHAR(50), " +
            "year INT, " +
            "language VARCHAR(50), " +
            "PRIMARY KEY (Bid))";


              Stmt.executeUpdate(sql);
                    
        }catch(Exception e){
            
            System.out.println(e);
        }
    }
    
    static void insertBookDetail(Statement stmt,Data newData){
        try{
            String sql = "INSERT INTO Book1(Bid, Title, author, pages, publisher, year, language) " +
             "VALUES(" + newData.Bid + ", '" + 
                    newData.title + "', '" + newData.author + 
                    "', " + newData.pages + ", '" + 
                    newData.publisher + "', '" + newData.year + 
                    "', '" + newData.language + "')";


                
            stmt.executeUpdate(sql);
                           
        }catch(Exception e){
           
            System.out.println(e);
        }
    }
    
    static ResultSet searchData(Statement stmt){
        try{
            String sql = "SELECT Bid"+
                    ",title,author,pages,publisher,year,language from Book";
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    
    static void updateBookDetail(Statement stmt ,Data updatedData){
        try{
            String sql = "UPDATE Book1 " +
             "SET title = '" + updatedData.title + "', " +
             "author = '" + updatedData.author + "', " +
             "pages = " + updatedData.pages + ", " +
             "publisher = '" + updatedData.publisher + "', " +
             "year = " + updatedData.year + ", " +
             "language = '" + updatedData.language + "' " +
             "WHERE Bid = " + updatedData.Bid;

            stmt.executeUpdate(sql);
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    static void deleteBookDetail(Statement stmt ,int Bid){
        try{
            String sql = "DELETE FROM Book1 WHERE Bid "+
                    " = " + Bid;
            stmt.executeUpdate(sql);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    static ResultSet searchData(Statement stmt,String searchString){
        ResultSet rs = null;
        try{
            String sql = "SELECT Bid, title, author, pages, year,publisher, language FROM Book1 " +
             "WHERE Bid LIKE '%" + searchString + 
             "%' OR title LIKE '%" + searchString + 
             "%' OR author LIKE '%" + searchString + 
             "%' OR pages LIKE '%" + searchString + 
             "%' OR year LIKE '%" + searchString + 
             "%' OR publisher LIKE '%" + searchString + 
             "%' OR language LIKE '%" + searchString + "%'";


            rs = stmt.executeQuery(sql);
                    
        }catch(Exception e){
            System.out.println(e);
        }
        return rs;
    }
    
    static String getSearchResult(ResultSet rs) throws Exception {
    StringBuilder searchResult = new StringBuilder();
    while (rs.next()) {
        String title = rs.getString("title");
        String author = rs.getString("author");
        int pages = rs.getInt("pages");
        int year = rs.getInt("year");
        String publisher=rs.getString("publisher");
        String language = rs.getString("language");
        
        if (title != null && author != null && language != null) {
            searchResult.append(title).append("|");
            searchResult.append(author).append("|");
            searchResult.append(pages).append("|");
            searchResult.append(year).append("|");
            searchResult.append(language).append("\n");
        }
    }
    return searchResult.toString();
}

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt=null;
        ResultSet rs = null;
        
        try{
            conn =getConnection();
            stmt = conn.createStatement();
            //createTable(stmt);
//           insertBookDetail(stmt, new Data(1, "Data mining", "Manish shah", 365, "Techanical Publisher", 1985, "English"));
//           insertBookDetail(stmt, new Data(2, "Java", "Rahul shah", 400, "Techanical Publisher", 1876, "English"));
//           insertBookDetail(stmt, new Data(3, "Python", "Milan kumar", 350, "Techanical Publisher", 2002, "English"));
//           insertBookDetail(stmt, new Data(4, "Software Engineering", "Anirudh Suri", 665, "Techanical Publisher", 2005, "English"));
//           insertBookDetail(stmt, new Data(5, "Advance Java", "Neil Strauss", 465, "Techanical Publisher", 2005, "English"));
//           insertBookDetail(stmt, new Data(6, "Theory of Computation", "Suzanne Collins", 500, "Techanical Publisher", 2009, "English"));
//           insertBookDetail(stmt, new Data(7, "Data visualization", "Eric Berne", 700, "Techanical Publisher", 2008, "English"));
//           insertBookDetail(stmt, new Data(8, "MicroProcessor and Interface", "Greg Rajaram", 500, "Techanical Publisher", 2000, "English"));
           //updateBookDetail(stmt,new Data(1,"TOC","Manish Shah",365,"Parul Publisher",2015,"English"));
          //deleteBookDetail(stmt,8);
           
           rs=searchData(stmt,"");
           
           System.out.println(getSearchResult(rs));
            stmt.close();
            conn.close();
            
        }catch (SQLException se) {
            se.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            // close resources
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
}
    }}





