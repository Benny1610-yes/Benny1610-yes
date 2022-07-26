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

public class Login extends JPanel implements ActionListener {
    public static int UserID;

    private JTextField email = new JTextField("Email: ");
    private JPasswordField password = new JPasswordField("Password: ");
    JButton submit = new JButton("CLICK ME TO SUBMIT");

    Login(){
        //actionlisteners
        submit.addActionListener(this);


        //addded to the jpanel
        this.setLayout(new GridLayout(7,2));

        JLabel email_label = new JLabel("EMAIL");
        this.add(email_label);
        this.add(email);
        JLabel password_label = new JLabel("PASSWORD");
        this.add(password_label);
        this.add(password);

        this.add(submit);

    }

    public void check_password(String Email, String Password) {
        try {
            // uses sql library
            Class.forName("org.sqlite.JDBC");
            // creates a connection by getting a connection to the test using the library
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Lib_DataBase.db");

            // to create statments
            java.sql.Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT Email FROM User;");

            List<String> emaillist = new ArrayList<>();

            //get all emails to chek it exists then add to list
            while (rs.next()) {
                emaillist.add(rs.getString("Email"));
            }
            System.out.println(emaillist);
            for (int i = 0; i <= (emaillist.size() - 1); i++) {
                System.out.println(emaillist.get(i));
                if (Email.equals("" + emaillist.get(i))) {

                    Email = emaillist.get(i);

                    rs = conn.createStatement().executeQuery("SELECT Password FROM User WHERE Email = '" + Email + "';");

                    while (rs.next()) {
                        String gotpassword = rs.getString("Password");
                        System.out.println(gotpassword);
                        if (Password.equals("" + gotpassword)) {
                            rs = conn.createStatement().executeQuery("SELECT UserID FROM User WHERE Email = '" + Email + "';");
                            UserID = rs.getInt("UserID");
                            System.out.println(UserID + " this is the users ID LOGIN");

                            rs = conn.createStatement().executeQuery("SELECT typeID FROM User WHERE UserID = '" + UserID + "';");
                            if(rs.getInt("typeID") == 0){
                                this.removeAll();
                                this.add(new UserUI());
                                this.revalidate();
                            }
                            else{
                                this.removeAll();
                                this.add(new LibrarianUI());
                                this.revalidate();
                            }
                            rs.close();
                            stat.close();
                            conn.close();


                        }
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e);
            System.out.println("here1");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == submit){
            //get all info from text boxes to submit to data base

            String email_data = email.getText();
            String password_data = String.valueOf(password.getPassword());
            System.out.println(password_data);
            System.out.println("[]" + email_data);
            //check for email and password in the database
            check_password(email_data, password_data);

        }
    }
}
