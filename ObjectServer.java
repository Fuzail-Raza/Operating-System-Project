import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectServer {

    ServerSocket ss;
    Socket s;
    ObjectInputStream in;
    String[] framesRecord;
    private int faults;

    private int frame;
    ObjectServer(int port) {
        try {
            ss = new ServerSocket(port);
            while(true) {
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");

                s = ss.accept();
                System.out.println("Client accepted");

                in = new ObjectInputStream(s.getInputStream());
                Pages st = (Pages) in.readObject();

                if (st.isFIFO) {
                    performFIFO(st.noOfFrames, st.inputStrings);
                } else {
                    performLRU(st.noOfFrames, st.inputStrings);
                }
                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                st.setFramesRecord(framesRecord);
                st.faults = faults;
                out.writeObject(st);
                out.flush();

            }

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void performFIFO(int frames,String[] input) {
        int i = 0, j = 0, k = 0, il = 0, m, n, flag = 1;
        int[] rs, p;

        m = frames;


        String[] inputStrings = input;
        int[] inputNumbers = new int[inputStrings.length];

//                n = Integer.parseInt(lengthInput.getText());
        n = inputStrings.length;

        rs = new int[n];
        p = new int[m];


        for (int l = 0; l < inputStrings.length; l++) {
            inputNumbers[l] = Integer.parseInt(inputStrings[l]);
        }


        for (i = 0; i < n; i++)
            rs[i] = inputNumbers[i];

        for (i = 0; i < m; i++)
            p[i] = -1;

        System.out.println("\tRef string\t\tPage frames");

        framesRecord=new String[n];
        for (i = 0; i < n; i++) {

            framesRecord[i]+=rs[i];
            framesRecord[i]+=",";
            System.out.print("\t" + rs[i] + "\t\t");
            flag = 1;
            for (j = 0; j < m; j++) {
                if (p[j] == rs[i]) {
                    flag = 0;
                    break;
                }
            }

            if (flag == 1) {
                p[k] = rs[i];
                k++;
            }

            for (j = 0; j < m; j++) {
                framesRecord[i] += p[j];
                framesRecord[i]+=",";
                System.out.print(p[j] + "\t");
            }

            if (flag == 1) {
                il++;
                System.out.print("PF No. " + il);
                framesRecord[i]+=il;
                framesRecord[i]+=",";
            }

            System.out.println();

            if (k == m)
                k = 0;

            faults=il;
        }

    }



    class LRU{
        private final LinkedHashMap<Integer, Integer> cache;
        private final int capacity;

        public LRU(int capacity) {
            this.capacity = capacity;
            this.cache = new LinkedHashMap<>(capacity);
//                    for(int i=0;i<capacity;i++){
//                        this.cache.put(-1,i);
//                        JOptionPane.showMessageDialog(null,i);
//                    }

        }

        public boolean referencePage(int page) {
            if (cache.containsKey(page)) {
                // Page is already in the cache, move it to the front (most recently used)
                cache.remove(page);
                cache.put(page, 0);
                return true;
            }

            if (cache.size() >= capacity) {
                // Cache is full, remove the least recently used page
                Iterator<Map.Entry<Integer, Integer>> iterator = cache.entrySet().iterator();
                iterator.next();
                iterator.remove();
            }

            // Add the new page to the cache
            cache.put(page, 0);
            return false;
        }

        public void printFrames(int fault,Boolean isPageFault) {
            System.out.print("Frames: ");
            int i=0;
            for (int key : cache.keySet()) {
                framesRecord[frame] += key + ",";
                System.out.print(key + " ");
                i++;

            }
            while(i<capacity){
                framesRecord[frame] += -1 + ",";
                System.out.print(-1 + " ");
                i++;
            }
            if(isPageFault) {
                framesRecord[frame] += fault + ",";
            }
            frame++;

        }

        void print(){
            for (String a:framesRecord){
                System.out.println(a);
            }
        }
    }




    private void performLRU(int frames,String[] input) {

        int capacity = frames;
        String[] references = input;
        framesRecord = new String[references.length];
        LRU lruCache = new LRU(capacity);
        frame=0;
        faults=0;

        int pageFaults = 0;
        for (String reference : references) {
            Boolean isFaultOccurs=false;
            int page = Integer.parseInt(reference);
            framesRecord[frame]+=reference;
            framesRecord[frame]+=",";
            if (!lruCache.referencePage(page)) {
                isFaultOccurs=true;
                pageFaults++;
            }
            lruCache.printFrames(pageFaults,isFaultOccurs);
            System.out.println();
        }
        faults=pageFaults;


        lruCache.print();

    }


    public static void main(String[] args) {
        new ObjectServer(8005);
    }
}
