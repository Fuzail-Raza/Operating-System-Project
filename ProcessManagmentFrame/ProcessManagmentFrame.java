package ProcessManagmentFrame;

import javax.swing.*;
import java.awt.*;


public class ProcessManagmentFrame {

    private JButton createProcessButton;
    private JFrame frame;
    public JPanel mainPanel;
    private JButton destroyProcessButton;
    private JButton suspendProcessButton;
    private JButton processCommunicationWithOtherProcessesButton;
    private JButton resumeProcessButton;
    private JButton blockProcessButton;
    private JButton changeProcessPriorityButton;
    private JButton dispatchProcessButton;
    private JButton wakeupProcessButton;
    private JLabel heading;

    public ProcessManagmentFrame() {
        initGUI();
    }

    public void initGUI() {

        mainPanel=addContent();

        frame = new JFrame("Process Management Frame");
        frame.setSize(784, 449);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    JPanel addContent(){
        JPanel temp=new JPanel(null);

        createProcessButton = new JButton("Create a process");
        destroyProcessButton = new JButton("Destroy a process");
        suspendProcessButton = new JButton("Suspend a Process");
        processCommunicationWithOtherProcessesButton = new JButton("Process communication with other Processes");
        resumeProcessButton = new JButton("Resume a Process");
        blockProcessButton = new JButton("Block a Process");
        changeProcessPriorityButton = new JButton("Change process Priority");
        dispatchProcessButton = new JButton("Dispatch a Process");
        wakeupProcessButton = new JButton("Wakeup a Process");

        heading = new JLabel("Operating System");
        Font boldFont = new Font(heading.getFont().getName(), Font.BOLD, 28);
        heading.setFont(boldFont);
        heading.setHorizontalAlignment(SwingConstants.CENTER);


        // Add components to the temp
        temp.add(createProcessButton);
        temp.add(destroyProcessButton);
        temp.add(suspendProcessButton);
        temp.add(processCommunicationWithOtherProcessesButton);
        temp.add(resumeProcessButton);
        temp.add(blockProcessButton);
        temp.add(changeProcessPriorityButton);
        temp.add(dispatchProcessButton);
        temp.add(wakeupProcessButton);
        temp.add(heading);


        createProcessButton.setBounds(80, 100, 170, 45);
        destroyProcessButton.setBounds(300, 100, 170, 45);
        suspendProcessButton.setBounds(80, 170, 170, 45);
        processCommunicationWithOtherProcessesButton.setBounds(225, 310, 335, 50);
        resumeProcessButton.setBounds(510, 100, 170, 45);
        blockProcessButton.setBounds(300, 170, 170, 45);
        changeProcessPriorityButton.setBounds(515, 170, 170, 45);
        dispatchProcessButton.setBounds(192, 240, 170, 45);
        wakeupProcessButton.setBounds(410, 240, 170, 45);
        heading.setBounds(245, 20, 305, 50);

        return temp;
    }

    public static void main(String[] args) {
        new ProcessManagmentFrame();
    }
}
