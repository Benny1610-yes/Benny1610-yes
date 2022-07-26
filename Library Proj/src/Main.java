import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JPanel implements ActionListener {

    ////////////////////////////////////////////////////////////////////
    // this main panel is for loading in.
    //its Layout is a JPanel north
    //and a JPanel in the center
    //this creates a panel effect for login logout features, to even a
    //search bar for users to find a book
    ////////////////////////////////////////////////////////////////////


    //will contain, and pass all user information when logged in or
    // logged out

    //will also handle searches for books,
    //books they have borrowed (temp removal from storage)
    private UserUI user_ui = new UserUI();

    //librarian will see total books,
    //add books to storage, delete books from storage
    //genres of books in total etc.
    private LibrarianUI librarian_UI = new LibrarianUI();

    //basic login, using email, password.
    //once login confirmed, return to Main_blank_UI for loading
    //extra user data is retrieved here, sent to Userui or LibrarianUI,
    private Login login_UI = new Login();
    public boolean login_check = false;
    private boolean user_check = false;
    private boolean librarian_check = false;

    //data inputs
    //Check size of database - is USERID + 1
    //email name password basic inputs
    //added to database

    //UserID from current Librarian can be inputted to create new librarian account

    //blank screen again for loading user data
    //send to LoginUI, this is to confirm it is on the database
    private Create_Account create_account_UI = new Create_Account();
    public static boolean create_account_check = false;


    //main layout
    //north is always going to have a logout button, if a login is true

    //NORTH BUTTONS
    private JButton Logout_button = new JButton("LOGOUT");
    private JButton Profile_button = new JButton("Profile");

    //user
    private JButton Books_button = new JButton("Books");
    private JButton Borrow_button = new JButton("Borrow");

    //librarian


    //CENTRE MAIN UI OUTPUTS
    JPanel center_panel = new JPanel();
    private JButton login_button = new JButton("Login");

    private JButton create_account_button = new JButton("Create Account");


    //constructor
    Main(){
        login_button.addActionListener(this);
        create_account_button.addActionListener(this);


        //set layout of the class of jpanel

        if (create_account_check == false || login_check == false){
            //no login or create account, show option

            this.setLayout(new GridLayout(1,2));
            //two buttons, to login or create account
            this.add(login_button);
            this.add(create_account_button);
            //then load login.java class jpanel in center
            //or load create_accounte.java class jpanel in center
            //change checks to true

            //if create account return complete, load login
            //if login return complete, show UI type, load in center.
        }
        else if (create_account_check == true){
            this.setLayout(new BorderLayout());
            this.add(center_panel, BorderLayout.CENTER);
            center_panel.add(login_UI);
        }

        else if (login_check == true){
            this.setLayout(new BorderLayout());
            this.add(center_panel, BorderLayout.CENTER);
            //check for USER OR LIBRARIAN
            if(user_check == true){
                center_panel.add(new UserUI());
            }
            else if(librarian_check == true){
                center_panel.add(new LibrarianUI());
            }


        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == login_button){
            this.remove(login_button);
            this.remove(create_account_button);
            this.setLayout(new BorderLayout());
            this.add(center_panel, BorderLayout.CENTER);
            System.out.println(("login button pressed"));
            center_panel.add(login_UI);
            this.revalidate();
        }
        else if (source == create_account_button){
            this.remove(login_button);
            this.remove(create_account_button);
            this.setLayout(new BorderLayout());
            this.add(center_panel, BorderLayout.CENTER);
            System.out.println("Create acvouint pressed");
            center_panel.add(create_account_UI);
            this.revalidate();
        }
    }
}
