import javax.swing.*;

public class Runner extends JFrame {
    public static void main(String[] args)
    {
        // create a window pop up
        JFrame window = new JFrame();
        
        //program will terminate if the window is closed,
        //if a process iss being completed, won't force close till process is complete.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //sets the content of the window to the jframe of main
        window.setContentPane(new Main());
        //set the size by pixels? think its pixels
        window.setSize(700,600);
        //title, top left of window
        window.setTitle("Library");
        //make sure it appears and is visible rather than a phantom window
        //that you cannot see
        window.setVisible(true);
    }
}
