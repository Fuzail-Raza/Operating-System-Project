package MemoryManagment;

import MenuFrames.MenuFrame;
import ProcessManagmentFrame.ProcessManagmentFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class MemoryManagmentFrame {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JTextField noOfFramesInput;
    private JTextField lengthInput;
    private JTextField stringInput;
    private JLabel noOfFramesLabel;
    private JLabel lenghtLabel;
    private JLabel stringLabel;
    private JButton LRUButton;
    private JButton FIFO;
    private JButton backButton;
    Socket s;
    ObjectOutputStream out;

    private JLabel pageFaultHeading;
    private String[] framesRecord;
    private int frame = 0;

    public MemoryManagmentFrame(){

        initGUI();

    }

    private void initGUI() {

        mainFrame=new JFrame();

        mainPanel=new JPanel(null);

        noOfFramesInput = new JTextField (5);
        lengthInput = new JTextField (5);
        stringInput = new JTextField (5);
        noOfFramesLabel = new JLabel ("No of Frames");
        lenghtLabel = new JLabel ("No of String Lenght");
        stringLabel = new JLabel ("Reference String");
        LRUButton = new JButton ("LRU");
        FIFO = new JButton ("FIFO");
        backButton=new JButton("Back");
        pageFaultHeading = new JLabel ("Page ");

        Font f1=new Font("Arial",Font.BOLD,16);
        pageFaultHeading.setFont(f1);
        pageFaultHeading.setVisible(false);

        mainPanel.add (noOfFramesInput);
        mainPanel.add (lengthInput);
        mainPanel.add (stringInput);
        mainPanel.add (noOfFramesLabel);
        mainPanel.add (lenghtLabel);
        mainPanel.add (stringLabel);
        mainPanel.add (LRUButton);
        mainPanel.add (FIFO);
        mainPanel.add (pageFaultHeading);
        mainPanel.add(backButton);

        noOfFramesInput.setBounds (230, 60, 100, 25);
        lengthInput.setBounds (230, 90, 100, 25);
        stringInput.setBounds (230, 120, 200, 25);
        noOfFramesLabel.setBounds (80, 60, 100, 25);
        lenghtLabel.setBounds (80, 90, 115, 25);
        stringLabel.setBounds (80, 120, 100, 25);
        LRUButton.setBounds (120, 175, 100, 25);
        FIFO.setBounds (245, 175, 100, 25);
        backButton.setBounds(365,175,100,25);
        pageFaultHeading.setBounds (130, 210, 390, 35);

//        mainPanel.add(displaySchedulingProcesses(schedulingQueue));

        mainFrame.add(mainPanel);
        mainFrame.setSize(873,603);
        mainFrame.setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new MenuFrame();
            }
        });


        LRUButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {

            if(isFormValid()) {


                int faults=0;
                try {
                    s = new Socket("127.0.0.1", 8005);
                    out = new ObjectOutputStream(s.getOutputStream());
                    Pages pg=new Pages(Integer.parseInt(noOfFramesInput.getText().trim()),stringInput.getText().split("\\s+"),false);
                    out.writeObject(pg);
                    ObjectInputStream in =new ObjectInputStream(s.getInputStream());
                    Pages finalPage= (Pages) in.readObject();
                    framesRecord=finalPage.framesRecord;
                    faults=finalPage.faults;


                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(mainFrame,"Unable to Connect to Server");
                }

                pageFaultHeading.setText("The number of page faults using LRU are: " + faults);
                pageFaultHeading.setVisible(true);
                System.out.println("The number of page faults using LRU are: " + faults);
                mainPanel.add(displayLRUFrames());
                mainPanel.repaint();
                mainPanel.validate();



//                performFIFO();
//                int faults=0;
//                frame=0;
//
//                pageFaultHeading.setText("The number of page faults using LRU are: " + faults);
//
//                pageFaultHeading.setVisible(true);
//                mainPanel.add(displayLRUFrames());
//                mainPanel.repaint();
//                mainPanel.validate();
//                performLRU();
            }
            }

                                                                                                                                            //            class LRU{
                                                                                                                                            //                private final LinkedHashMap<Integer, Integer> cache;
                                                                                                                                            //                private final int capacity;
                                                                                                                                            //
                                                                                                                                            //                public LRU(int capacity) {
                                                                                                                                            //                    this.capacity = capacity;
                                                                                                                                            //                    this.cache = new LinkedHashMap<>(capacity);
                                                                                                                                            ////                    for(int i=0;i<capacity;i++){
                                                                                                                                            ////                        this.cache.put(-1,i);
                                                                                                                                            ////                        JOptionPane.showMessageDialog(null,i);
                                                                                                                                            ////                    }
                                                                                                                                            //
                                                                                                                                            //                }
                                                                                                                                            //
                                                                                                                                            //                public boolean referencePage(int page) {
                                                                                                                                            //                    if (cache.containsKey(page)) {
                                                                                                                                            //                        // Page is already in the cache, move it to the front (most recently used)
                                                                                                                                            //                        cache.remove(page);
                                                                                                                                            //                        cache.put(page, 0);
                                                                                                                                            //                        return true;
                                                                                                                                            //                    }
                                                                                                                                            //
                                                                                                                                            //                    if (cache.size() >= capacity) {
                                                                                                                                            //                        // Cache is full, remove the least recently used page
                                                                                                                                            //                        Iterator<Map.Entry<Integer, Integer>> iterator = cache.entrySet().iterator();
                                                                                                                                            //                        iterator.next();
                                                                                                                                            //                        iterator.remove();
                                                                                                                                            //                    }
                                                                                                                                            //
                                                                                                                                            //                    // Add the new page to the cache
                                                                                                                                            //                    cache.put(page, 0);
                                                                                                                                            //                    return false;
                                                                                                                                            //                }
                                                                                                                                            //
                                                                                                                                            //                public void printFrames(int fault,Boolean isPageFault) {
                                                                                                                                            //                    System.out.print("Frames: ");
                                                                                                                                            //                    int i=0;
                                                                                                                                            //                    for (int key : cache.keySet()) {
                                                                                                                                            //                        framesRecord[frame] += key + ",";
                                                                                                                                            //                        System.out.print(key + " ");
                                                                                                                                            //                        i++;
                                                                                                                                            //
                                                                                                                                            //                    }
                                                                                                                                            //                    while(i<capacity){
                                                                                                                                            //                        framesRecord[frame] += -1 + ",";
                                                                                                                                            //                        System.out.print(-1 + " ");
                                                                                                                                            //                        i++;
                                                                                                                                            //                    }
                                                                                                                                            //                    if(isPageFault) {
                                                                                                                                            //                        framesRecord[frame] += fault + ",";
                                                                                                                                            //                    }
                                                                                                                                            //                    frame++;
                                                                                                                                            //
                                                                                                                                            //                }
                                                                                                                                            //
                                                                                                                                            //                void print(){
                                                                                                                                            //                    for (String a:framesRecord){
                                                                                                                                            //                        System.out.println(a);
                                                                                                                                            //                    }
                                                                                                                                            //                }
                                                                                                                                            //            }
                                                                                                                                            //
                                                                                                                                            //
                                                                                                                                            //            private void performLRU() {
                                                                                                                                            //
                                                                                                                                            //                int capacity = Integer.parseInt(noOfFramesInput.getText().trim());
                                                                                                                                            //                String[] references = stringInput.getText().split("\\s+");
                                                                                                                                            //                framesRecord = new String[references.length];
                                                                                                                                            //                LRU lruCache = new LRU(capacity);
                                                                                                                                            //
                                                                                                                                            //                int pageFaults = 0;
                                                                                                                                            //                for (String reference : references) {
                                                                                                                                            //                    Boolean isFaultOccurs=false;
                                                                                                                                            //                    int page = Integer.parseInt(reference);
                                                                                                                                            //                    framesRecord[frame]+=reference;
                                                                                                                                            //                    framesRecord[frame]+=",";
                                                                                                                                            //                    if (!lruCache.referencePage(page)) {
                                                                                                                                            //                        isFaultOccurs=true;
                                                                                                                                            //                        pageFaults++;
                                                                                                                                            //                    }
                                                                                                                                            //                    lruCache.printFrames(pageFaults,isFaultOccurs);
                                                                                                                                            //                    System.out.println();
                                                                                                                                            //                }
                                                                                                                                            //
                                                                                                                                            //                pageFaultHeading.setText("The number of page faults using LRU are: " + pageFaults);
                                                                                                                                            //                lruCache.print();
                                                                                                                                            //
                                                                                                                                            //                pageFaultHeading.setVisible(true);
                                                                                                                                            //                mainPanel.add(displayLRUFrames());
                                                                                                                                            //                mainPanel.repaint();
                                                                                                                                            //                mainPanel.validate();
                                                                                                                                            //
                                                                                                                                            //            }


        });

        FIFO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isFormValid()) {
                    int faults=0;
                    try {
                        s = new Socket("127.0.0.1", 8005);
                        out = new ObjectOutputStream(s.getOutputStream());
                        Pages pg=new Pages(Integer.parseInt(noOfFramesInput.getText().trim()),stringInput.getText().split("\\s+"),true);
                        out.writeObject(pg);
                        ObjectInputStream in =new ObjectInputStream(s.getInputStream());
                        Pages finalPage= (Pages) in.readObject();
                        framesRecord=finalPage.framesRecord;
                        faults=finalPage.faults;


                    }
                    catch (Exception ex){
                        JOptionPane.showMessageDialog(mainFrame,"Unable to Connect to Server");
                    }

                    pageFaultHeading.setText("The number of page faults using FIFO are: " + faults);
                    pageFaultHeading.setVisible(true);
                    System.out.println("The number of page faults using FIFO are: " + faults);
                    mainPanel.add(displayFIFOFrames());
                    mainPanel.repaint();
                    mainPanel.validate();
//                    performFIFO();

                }
            }
        });
    }



    private boolean isFormValid() {

        try{
            Integer.parseInt(noOfFramesInput.getText().trim());
            Integer.parseInt(lengthInput.getText());

            String[] inputStrings = stringInput.getText().split("\\s+");
            int[] inputNumbers = new int[inputStrings.length];

            for (int l = 0; l < inputStrings.length; l++) {
                inputNumbers[l] = Integer.parseInt(inputStrings[l]);
            }
            return true;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(mainFrame,"Input Data is not in Numerical Form");
            return false;
        }


    }

                                                                                    //    private void performFIFO() {
                                                                                    //        int i = 0, j = 0, k = 0, il = 0, m, n, flag = 1;
                                                                                    //        int[] rs, p;
                                                                                    //
                                                                                    //        m = Integer.parseInt(noOfFramesInput.getText().trim());
                                                                                    //
                                                                                    //
                                                                                    //        String[] inputStrings = stringInput.getText().split("\\s+");
                                                                                    //        int[] inputNumbers = new int[inputStrings.length];
                                                                                    //
                                                                                    ////                n = Integer.parseInt(lengthInput.getText());
                                                                                    //        n = inputStrings.length;
                                                                                    //
                                                                                    //        rs = new int[n];
                                                                                    //        p = new int[m];
                                                                                    //
                                                                                    //
                                                                                    //        for (int l = 0; l < inputStrings.length; l++) {
                                                                                    //            inputNumbers[l] = Integer.parseInt(inputStrings[l]);
                                                                                    //        }
                                                                                    //
                                                                                    //
                                                                                    //        for (i = 0; i < n; i++)
                                                                                    //            rs[i] = inputNumbers[i];
                                                                                    //
                                                                                    //        for (i = 0; i < m; i++)
                                                                                    //            p[i] = -1;
                                                                                    //
                                                                                    //        System.out.println("\tRef string\t\tPage frames");
                                                                                    //
                                                                                    //        framesRecord=new String[n];
                                                                                    //        for (i = 0; i < n; i++) {
                                                                                    //
                                                                                    //            framesRecord[i]+=rs[i];
                                                                                    //            framesRecord[i]+=",";
                                                                                    //            System.out.print("\t" + rs[i] + "\t\t");
                                                                                    //            flag = 1;
                                                                                    //            for (j = 0; j < m; j++) {
                                                                                    //                if (p[j] == rs[i]) {
                                                                                    //                    flag = 0;
                                                                                    //                    break;
                                                                                    //                }
                                                                                    //            }
                                                                                    //
                                                                                    //            if (flag == 1) {
                                                                                    //                p[k] = rs[i];
                                                                                    //                k++;
                                                                                    //            }
                                                                                    //
                                                                                    //            for (j = 0; j < m; j++) {
                                                                                    //                framesRecord[i] += p[j];
                                                                                    //                framesRecord[i]+=",";
                                                                                    //                System.out.print(p[j] + "\t");
                                                                                    //            }
                                                                                    //
                                                                                    //            if (flag == 1) {
                                                                                    //                il++;
                                                                                    //                System.out.print("PF No. " + il);
                                                                                    //                framesRecord[i]+=il;
                                                                                    //                framesRecord[i]+=",";
                                                                                    //            }
                                                                                    //
                                                                                    //            System.out.println();
                                                                                    //
                                                                                    //            if (k == m)
                                                                                    //                k = 0;
                                                                                    //        }
                                                                                    //
                                                                                    //        pageFaultHeading.setText("The number of page faults using FIFO are: " + il);
                                                                                    //        pageFaultHeading.setVisible(true);
                                                                                    //        System.out.println("The number of page faults using FIFO are: " + il);
                                                                                    //        mainPanel.add(displayFIFOFrames());
                                                                                    //        mainPanel.repaint();
                                                                                    //        mainPanel.validate();
                                                                                    //    }

    private JScrollPane displayFIFOFrames() {

        for (String a:framesRecord){
            System.out.println(a);
        }

        DefaultTableModel model=new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane scrollBar=new JScrollPane(table);

        scrollBar.setBounds(20,250,820,310);
        model.addColumn("Ref String");

        for (int i=0;i<Integer.parseInt(noOfFramesInput.getText().trim());i++){
            model.addColumn("Frame "+i);
        }
        model.addColumn("Fault No");


        for (String eachFrame:framesRecord) {
//            JOptionPane.showMessageDialog(null,eachFrame);
            Vector<String> row = new Vector<>();
            String temp="";
            for(int j=4;j<eachFrame.length();j++) {
                if(String.valueOf(eachFrame.charAt(j)).equals(",")){
                    row.add(temp);
                    temp="";
                }
                else{
                    temp+=String.valueOf(eachFrame.charAt(j));
                }
            }
            model.addRow(row);

        }


        return scrollBar;
    }

    private JScrollPane displayLRUFrames() {

        for (String a:framesRecord){
            System.out.println(a);
        }

        DefaultTableModel model=new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane scrollBar=new JScrollPane(table);

        scrollBar.setBounds(20,250,820,310);
        model.addColumn("Ref String");

        for (int i=0;i<Integer.parseInt(noOfFramesInput.getText().trim());i++){
            model.addColumn("Frame "+i);
        }
        model.addColumn("Fault No");


        for (String eachFrame:framesRecord) {
//            JOptionPane.showMessageDialog(null,eachFrame);
            Vector<String> row = new Vector<>();
            String temp="";
            for(int j=4;j<eachFrame.length();j++) {
                if(String.valueOf(eachFrame.charAt(j)).equals(",")){
                    row.add(temp);
                    temp="";
                }
                else{
                    temp+=String.valueOf(eachFrame.charAt(j));
                }
            }
            model.addRow(row);

        }


        return scrollBar;
    }


    public static void main(String[] args) {
        new MemoryManagmentFrame();
    }

}
