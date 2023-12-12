package ProcessManagmentFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ProcessManagmentFrame  {

    private JButton createProcessButton;
    private JFrame frame;
    private JPanel mainPanel;
    private JButton destroyProcessButton;
    private JButton suspendProcessButton;
    private JButton processCommunicationWithOtherProcessesButton;
    private JButton resumeProcessButton;
    private JButton blockProcessButton;
    private JButton changeProcessPriorityButton;
    private JButton dispatchProcessButton;
    private JButton wakeupProcessButton;
    private JButton displayProcessButton;

    private JLabel heading;

    // For creating processes
    private JLabel numberOfProcesses;
    private JTextField numberOfProcessInput;
    private JButton create;
    private JLabel processesArrivalTime;
    private JLabel processesBurstTime;
    private JTextField[] processesArrivalTimeInput;
    private JTextField[] processesBurstTimeInput;
    private int processesInput;
    private int i = 0;
    private JButton next;
    Process[] processes;

    ///For Suspend Process

    private JLabel suspendInputLabel;
    private JTextField suspendIDInput;
    private JButton suspend;

    // Process class with ID
    private static class Process {
        private int id;
        private int arrivalTime;
        private int burstTime;
        private String status;
        private String ownerOfProcess;
        private int priority;
        private int processor;


        public Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.status="Ready";
            this.priority=0;
            this.processor=0;
            this.ownerOfProcess="null";

        }
    }

    public ProcessManagmentFrame () {
        initGUI();
    }

    public void initGUI() {
        mainPanel = addContent();
        frame = new JFrame("Process Management Frame");
        frame.setSize(784, 449);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    JPanel createProcessPanel() {
        JPanel temp = new JPanel(null);
        numberOfProcesses = new JLabel("Enter Number of Processes");
        numberOfProcessInput = new JTextField(5);
        create = new JButton("Create");

        temp.add(numberOfProcesses);
        temp.add(numberOfProcessInput);
        temp.add(create);

        numberOfProcesses.setBounds(130, 75, 195, 45);
        numberOfProcessInput.setBounds(335, 85, 185, 30);
        create.setBounds(250, 130, 150, 35);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processesInput = Integer.parseInt(numberOfProcessInput.getText());
                processesArrivalTimeInput = new JTextField[processesInput];
                processesBurstTimeInput = new JTextField[processesInput];
                frame.remove(temp);
                frame.add(new JPanel().add(inputProcesses()));
                frame.revalidate();
                frame.repaint();
            }
        });

        return temp;
    }JPanel inputProcesses() {
        JPanel temp = new JPanel(null);
        processesArrivalTime = new JLabel("Enter Arrival Time of Process " + (i + 1) + " :");
        temp.add(processesArrivalTime);
        processesBurstTime = new JLabel("Enter Burst Time of Process " + (i + 1) + " :");
        temp.add(processesBurstTime);

        processesArrivalTimeInput[i] = new JTextField();
        processesBurstTimeInput[i] = new JTextField();

        temp.add(processesArrivalTimeInput[i]);
        temp.add(processesBurstTimeInput[i]);

        if (i == processesInput - 1) {
            next = new JButton("Submit");
        } else {
            next = new JButton("Next");
        }

        processesArrivalTime.setBounds(155, 100, 205, 30);
        next.setBounds(280, 195, 135, 30);
        processesBurstTime.setBounds(150, 145, 205, 30);
        processesArrivalTimeInput[i].setBounds(375, 100, 130, 30);
        processesBurstTimeInput[i].setBounds(375, 145, 130, 30);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                i++;
                if (i < processesInput) {
                    frame.remove(temp);
                    frame.add(inputProcesses());
                    frame.revalidate();
                    frame.repaint();
                }

                if (e.getActionCommand().equals("Submit")) {
                    frame.remove(temp);
                    frame.add(addContent());
                    frame.revalidate();
                    frame.repaint();
                    createPCB();
                }
            }
        });

        temp.add(next);
        return temp;
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
        displayProcessButton=new JButton("Display Processes");

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
        temp.add(displayProcessButton);
        temp.add(heading);


        createProcessButton.setBounds(80, 100, 170, 45);
        destroyProcessButton.setBounds(300, 100, 170, 45);
        suspendProcessButton.setBounds(80, 170, 170, 45);
        processCommunicationWithOtherProcessesButton.setBounds(225, 310, 335, 50);
        resumeProcessButton.setBounds(510, 100, 170, 45);
        blockProcessButton.setBounds(300, 170, 170, 45);
        changeProcessPriorityButton.setBounds(515, 170, 170, 45);
        dispatchProcessButton.setBounds(80 ,240, 170, 45);
        wakeupProcessButton.setBounds(300, 240, 170, 45);
        displayProcessButton.setBounds(515,240,170,45);
        heading.setBounds(245, 20, 305, 50);

        createProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(mainPanel);
                frame.add(createProcessPanel());
                frame.revalidate();
                frame.repaint();
            }
        });

        destroyProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(suspendProcessPanel("Destroy"));
                frame.revalidate();
                frame.repaint();
            }
        });

        resumeProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(suspendProcessPanel("Resume"));
                frame.revalidate();
                frame.repaint();
            }
        });
        dispatchProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(suspendProcessPanel("Dispatch"));
                frame.revalidate();
                frame.repaint();
            }
        });

        wakeupProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(suspendProcessPanel("WakeUp"));
                frame.revalidate();
                frame.repaint();
            }
        });

        blockProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(suspendProcessPanel("Block"));
                frame.revalidate();
                frame.repaint();
            }
        });

        suspendProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(suspendProcessPanel("Suspend"));
                frame.revalidate();
                frame.repaint();
            }
        });

        displayProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(displayProcesses());
                frame.revalidate();
                frame.repaint();
            }


        });
        return temp;
    }

    private JPanel displayProcesses() {
        JPanel temp=new JPanel(null);

        DefaultTableModel model=new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane scrollBar=new JScrollPane(table);

        scrollBar.setBounds(10,170,750,300);
        model.addColumn("Process ID");
        model.addColumn("Arrival Time");
        model.addColumn("Burst Time");
        model.addColumn("Process Status");


        int i=0;
        while(i<3) {
            Vector<String> row=new Vector<>();
            row.add(String.valueOf(processes[i].id));
            row.add(String.valueOf(processes[i].arrivalTime));
            row.add(String.valueOf(processes[i].burstTime));
            row.add(String.valueOf(processes[i].status));
            model.addRow(row);
            i++;
        }



        temp.add(scrollBar);
        return temp;
    }
    ////Suspend Procss
    private JPanel suspendProcessPanel(String stateChange) {
        JPanel temp=new JPanel(null);

        suspendInputLabel = new JLabel ("Enter Process Id To "+stateChange+" : ");
        suspendIDInput = new JTextField (5);
        suspend = new JButton (stateChange);

        temp.add (suspendInputLabel);
        temp.add (suspendIDInput);
        temp.add (suspend);


        suspendInputLabel.setBounds (200, 110, 200, 35);
        suspendIDInput.setBounds (430, 115, 100, 25);
        suspend.setBounds (295, 175, 125, 30);

        suspend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isSuspended=false;
                for(Process process : processes){
                    if(process.id==Integer.parseInt(suspendIDInput.getText())) {
                        process.status = stateChange;
                        JOptionPane.showMessageDialog(temp,"Process "+stateChange+" Successfully.",stateChange+" Process",JOptionPane.INFORMATION_MESSAGE);
                        isSuspended=true;
                        frame.remove(temp);
                        frame.add(addContent());
                        frame.revalidate();
                        frame.repaint();
                    }
                    print();
                }
                if(!isSuspended) {
                    JOptionPane.showMessageDialog(temp,"Process "+stateChange+" Not Successfully . No Process Found",stateChange+" Process",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return temp;

    }
    void createPCB(){
        processes = new Process[processesInput];
        for (int j = 0; j < processesInput; j++) {
            processes[j] = new Process(j + 1, Integer.parseInt(processesArrivalTimeInput[j].getText()),
                    Integer.parseInt(processesBurstTimeInput[j].getText()));
        }

    }
    void print(){
        for (int j = 0; j < processesInput; j++) {
            System.out.println(processes[j].id + "Arrival : " + processes[j].arrivalTime+" Burst Time " + processes[j].burstTime+ " Status " + processes[j].status );
        }
    }
    public static void main(String[] args) {
        new ProcessManagmentFrame ();
    }
}
