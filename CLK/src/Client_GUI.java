import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client_GUI {
    private JTextField JTextFieldStuduentNumber;
    private JTextField JTextFieldFirstName;
    private JTextField JTextFieldLastName;
    private JButton JButtonConnect;
    private JPanel JPanelMain;

    public Client_GUI() {
        JButtonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"works");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student App");
        frame.setContentPane(new Client_GUI().JPanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
