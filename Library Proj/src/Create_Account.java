import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Create_Account extends JPanel implements ActionListener {

    public static int UserID;

    private JTextField fname = new JTextField("bob");
    private JTextField lname = new JTextField("bob");
    private JTextField email = new JTextField("bob.email.com");
    private JPasswordField password = new JPasswordField("123");
    private JTextField age = new JTextField("18");
    private JTextField gender = new JTextField("other");
    private JTextField address = new JTextField("gl502rh");

    JButton submit = new JButton("CLICK ME TO SUBMIT");

    Create_Account(){
        submit.addActionListener(this);
        //addded to the jpanel
        this.setLayout(new GridLayout(8,2));
        JLabel fname_label = new JLabel("First name: ");
        this.add(fname_label);
        this.add(fname);
        JLabel lname_label = new JLabel("Last name: ");
        this.add(lname_label);
        this.add(lname);
        JLabel email_label = new JLabel("Email: ");
        this.add(email_label);
        this.add(email);
        JLabel password_label = new JLabel("Password: ");
        this.add(password_label);
        this.add(password);
        JLabel age_label = new JLabel("Age: ");
        this.add(age_label);
        this.add(age);

        //Jcombo box, for gender types?
        JLabel gender_label = new JLabel("Gender: ");
        this.add(gender_label);
        this.add(gender);
        //this for simplicity now

        JLabel address_label = new JLabel("Postcode: ");
        this.add(address_label);
        this.add(address);

        this.add(submit);
    }

    public void InsertDataCreateAccount(String fname_db, String lname_db, String email_db, String password_db, int age_db, String gender_db, String address_db, int typeID){
        try{
            //uses sql library
            Class.forName("org.sqlite.JDBC");
            //creates a connection by getting a connection to the test using the library
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Lib_DataBase.db");

            //to create statments
            java.sql.Statement stat = conn.createStatement();
            //allows for staements to be prapered in advanced then all done in one go

            conn.setAutoCommit(true);

            ResultSet rs = stat.executeQuery("SELECT UserID FROM User;");
            //need to get the length of how many users there are so can make a new Unique ID
            while (rs.next()) {
                UserID++;
            }
            //need it to be the next one on from the max as its a new entry into the database
            UserID++;
            rs.close();

            PreparedStatement prep = conn.prepareStatement(
                    "INSERT INTO" +
                            " User " +
                            "(UserID, Email, Password, Fname, Lname, Age, Gender, Address, TypeID)" +
                            " VALUES" +
                            " ("+UserID+", '"+email_db+"', '"+password_db+
                            "','"+fname_db+"','"+lname_db+"',"
                            +age_db+",'"+gender_db+"','"
                            +address_db+"',"+typeID+");");
            //from there the ? are treated as variables that can be changed
            prep.executeUpdate();
            conn.close();
            System.out.println("commit complete");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Main.create_account_check = true;
        this.removeAll();
        this.add(new Login());
        this.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == submit){
            //get all info from text boxes to submit to data base
            System.out.println("submit pressed");
            String fname_data = fname.getText();
            String lname_data = lname.getText();
            String email_data = email.getText();
            String password_data = String.valueOf(password.getPassword());
            int age_data = Integer.parseInt(age.getText());
            String gender_data = gender.getText();
            String address_data = address.getText();

            //type = 0 user
            //type = 1 staff
            int typeID=0;
            InsertDataCreateAccount(fname_data,lname_data,email_data,password_data,age_data,gender_data,address_data,typeID);


            //check for email and password in the database
        }
    }
}
