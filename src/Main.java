import javax.swing.SwingUtilities;
import View.MainFrame;
/*
* Name : Thiv Thyrith ( ធីវ ធីរិទ្ធ)
* Class : A6
* Calculator, Final project
*
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }
}
