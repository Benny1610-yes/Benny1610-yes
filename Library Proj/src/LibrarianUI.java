import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class LibrarianUI extends JPanel implements ActionListener {
    //get  data from the databse
    //post em in JLabels - simple
    LibrarianUI() {
        try {
            //stats on total genres etc.

            // uses sql library
            Class.forName("org.sqlite.JDBC");
            // creates a connection by getting a connection to the test using the library
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Lib_DataBase.db");

            // to create statments
            java.sql.Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM Bookshelf;");

            System.out.println(rs);

            List<String> result_list_genre = new ArrayList<>();
            List<Integer> result_list_page_count = new ArrayList<>();
            List<String> result_list_author = new ArrayList<>();
            List<Integer> result_list_bookID = new ArrayList<>();

            while (rs.next()) {
                result_list_page_count.add(rs.getInt("Num_Of_Pages"));
                result_list_genre.add(rs.getString("Genre"));
                result_list_author.add(rs.getString("Author"));
                result_list_bookID.add(rs.getInt("BookID"));
            }
            System.out.println(result_list_genre);
            System.out.println(result_list_page_count);

            List<String> unique_list_genre = result_list_genre.stream().distinct().collect(
                    Collectors.toList());
            List<String> unique_list_author = result_list_author.stream().distinct().collect(
                    Collectors.toList());
            int highest_page_count = 0;
            int lowest_page_count = result_list_page_count.get(0);
            int average_page_count = 0;
            for (Integer integer : result_list_page_count) {
                average_page_count = average_page_count + integer;
                if (highest_page_count < integer) {
                    highest_page_count = integer;
                } else if (lowest_page_count > integer) {
                    lowest_page_count = integer;
                }
            }
            average_page_count = average_page_count/result_list_page_count.size();

            System.out.println(average_page_count);
            System.out.println(lowest_page_count);
            System.out.println(highest_page_count);

            System.out.println(unique_list_author);
            System.out.println(unique_list_genre);
            this.setLayout(new GridLayout(5,5));
            this.add(new JLabel("total number of books:"+result_list_bookID.size()));

            this.add(new JLabel("Averge number of pages in books: "+average_page_count));
            this.add(new JLabel("Highest number of pages in books: "+highest_page_count));
            this.add(new JLabel("Lowest number of pages in books: "+lowest_page_count));

            this.add(new JLabel("Authors:"+unique_list_author));
            this.add(new JLabel("Genres:"+unique_list_genre));
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
