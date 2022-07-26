import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserUI extends JPanel implements ActionListener {

    private JTextField searchable = new JTextField(10);
    private JButton searchB = new JButton("Search");
    private JTable result = new JTable();
    private JScrollPane scrollPane = new JScrollPane(result);

    private JButton borrow_book = new JButton("Borrow book");
    private JButton return_book = new JButton("return book");
    private JTextField book_ID_search = new JTextField(10);

    UserUI(){
        System.out.println("user UI");
        this.add(searchable);
        this.add(searchB);
        this.add(scrollPane);

        this.add(book_ID_search);
        this.add(borrow_book);
        this.add(return_book);
        borrow_book.addActionListener(this);
        return_book.addActionListener(this);

        searchB.addActionListener(e -> result.setModel(DbUtils.resultSetToTableModel(
                new DataBase().search(searchable.getText(), this))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("action taken");
        Object source = e.getSource();
        try {
            if (source == borrow_book) {
                System.out.println("book borrow");
                int bookID = Integer.parseInt(book_ID_search.getText());
                // uses sql library
                Class.forName("org.sqlite.JDBC");
                // creates a connection by getting a connection to the test using the library
                Connection conn = DriverManager.getConnection("jdbc:sqlite:Lib_DataBase.db");

                // to create statments
                java.sql.Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery("SELECT BookID FROM Bookshelf;");

                List<Integer> book_list = new ArrayList<>();

                //get all books to chek it exists then add to list
                while (rs.next()) {
                    book_list.add(rs.getInt("BookID"));
                }
                for(int i = 0; i<book_list.size(); i++) {
                    if (bookID == book_list.get(i)) {
                        System.out.println("book in library");
                        //check that book isnt borrowed in borrow table
                        rs = stat.executeQuery("SELECT BookID FROM Borrow_Book;");

                        List<Integer> book_list_borrw = new ArrayList<>();

                        //get all books to chek it exists then add to list
                        while (rs.next()) {
                            book_list_borrw.add(rs.getInt("BookID"));
                        }
                        if (!book_list_borrw.contains(bookID)){
                            System.out.println("Book in borrow list");
                            //if book not in borrow then borrow book.
                            int userID = Login.UserID;
                            int borrowID = 0;
                            rs = stat.executeQuery("SELECT BorrowID FROM Borrow_Book;");
                            //need to get the length of how many users there are so can make a new Unique ID
                            while (rs.next()) {
                                borrowID++;
                            }
                            //need it to be the next one on from the max as its a new entry into the database
                            borrowID++;
                            rs.close();
                            PreparedStatement prep = conn.prepareStatement(
                                    "INSERT INTO" +
                                            " Borrow_Book " +
                                            "(UserID, BookID, BorrowID)" +
                                            " VALUES" +
                                            " ("+bookID+", '"+userID+"', '"+borrowID+"');");
                            //from there the ? are treated as variables that can be changed
                            prep.executeUpdate();
                            conn.close();
                        }
                    }
                }

            }
            else if (source == return_book){
                int bookID = Integer.parseInt(book_ID_search.getText());
                // uses sql library
                Class.forName("org.sqlite.JDBC");
                // creates a connection by getting a connection to the test using the library
                Connection conn = DriverManager.getConnection("jdbc:sqlite:Lib_DataBase.db");

                // to create statments
                java.sql.Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery("SELECT BookID FROM Bookshelf;");

                List<Integer> book_list = new ArrayList<>();

                //get all books to chek it exists then add to list
                while (rs.next()) {
                    book_list.add(rs.getInt("BookID"));
                }
                for(int i = 0; i<book_list.size(); i++) {
                    if (bookID == book_list.get(i)) {
                        System.out.println("book in library");
                        //check that book isnt borrowed in borrow table
                        rs = stat.executeQuery("SELECT BookID FROM Borrow_Book;");

                        List<Integer> book_list_borrw = new ArrayList<>();

                        //get all books to chek it exists then add to list
                        while (rs.next()) {
                            book_list_borrw.add(rs.getInt("BookID"));
                        }
                        if (book_list_borrw.contains(bookID)){
                            System.out.println("remove");
                            //if book not in borrow then borrow book.
                            int userID = Login.UserID;
                            int borrowID = 0;
                            rs = stat.executeQuery("SELECT BorrowID FROM Borrow_Book;");
                            //need to get the length of how many users there are so can make a new Unique ID
                            while (rs.next()) {
                                borrowID++;
                            }
                            //need it to be the next one on from the max as its a new entry into the database
                            borrowID++;
                            rs.close();
                            PreparedStatement prep = conn.prepareStatement(
                                    "DELETE FROM" +
                                            " Borrow_Book " +
                                            "WHERE BookID=" +
                                            +bookID+
                                            " AND UserID="+userID+";");
                            //from there the ? are treated as variables that can be changed
                            prep.executeUpdate();
                            conn.close();
                        }
                    }
                }

            }
        } catch (SQLException | ClassNotFoundException p) {
            System.err.println(p);
        }
    }
}
