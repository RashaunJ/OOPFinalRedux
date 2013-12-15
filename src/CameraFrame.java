import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

//import com.github.sarxos.webcam.Webcam;
//import com.github.sarxos.webcam.WebcamPanel;

@SuppressWarnings("serial")
public class CameraFrame extends JFrame implements ActionListener{

  //  public Webcam webcam;
    Boolean enabled = true;
    CameraFrame frame;

    private JButton btnSaveVerso;
    private JLabel lblVerso;

    private JButton btnEnable;
    private JButton btnQuit;
    private JPanel mainPanel;
  //  private WebcamPanel streamPanel;

    public static void main(String[] args){
        CameraFrame frame = new CameraFrame();
        frame.setVisible(true);
    }

    public CameraFrame() {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                setLocationRelativeTo(null);
                setResizable(false);
                setMinimumSize(new Dimension(800, 600));
                setPreferredSize(new Dimension(800,600));
                buildPanel();
                setContentPane(mainPanel);
            }
        });
    }

    public void buildPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        Border blackline = BorderFactory.createLineBorder(Color.black, 1, true);
/*
        webcam = Webcam.getDefault();
        webcam.open();

        streamPanel = new WebcamPanel(webcam);
        streamPanel.setPreferredSize(new Dimension(webcam.getViewSize()));
        streamPanel.setMaximumSize(new Dimension(webcam.getViewSize()));
*/
        btnSaveVerso = new JButton("Take pic");
        btnSaveVerso.setActionCommand("take");
        btnSaveVerso.addActionListener(this);

        lblVerso = new JLabel("Here will be the pic taken by the camera");
        lblVerso.setBorder(blackline);

        btnEnable = new JButton("Disable");
        btnEnable.setActionCommand("disable");
        btnEnable.addActionListener(this);

        btnQuit = new JButton("Quit");
        btnQuit.setActionCommand("quit");
        btnQuit.addActionListener(this);

 //       mainPanel.add(streamPanel);
        mainPanel.add(btnSaveVerso);
        mainPanel.add(lblVerso);
        mainPanel.add(btnEnable);
        mainPanel.add(btnQuit);

    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        Thread newThread = new Thread(){
            public void run(){
                if(e.getActionCommand().equals("take")){
    //                ImageIcon icon = new ImageIcon(webcam.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH ));
           //         lblVerso.setIcon(new ImageIcon(icon.getImage()));
                    lblVerso.revalidate();
                    lblVerso.repaint();
                }
                else if(e.getActionCommand().equals("disable")){
                    if(enabled){
                        lblVerso.setEnabled(false);
                        enabled = false;
                        btnEnable.setText("Enable");
                    }
                    else{
                        lblVerso.setEnabled(true);
                        enabled = true;
                        btnEnable.setText("Disable");
                    }
                }
            }
        };
        newThread.run();
        if(e.getActionCommand().equals("quit")){
       //     webcam.close();
            this.setVisible(false);
        }
    }
}