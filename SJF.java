import java.util.*;

class Process {
    public int pid;
    public int arrivalTime;
    public int burstTime;
    public int turnAroundTime;
    public int waitTime;
    public int remainingTime;
    public int completionTime; // Added to track completion time

    public Process() {
        this.completionTime = -1;
    }
}

class Schedule {
    private int noOfProcess;
    private int timer = 0;
    private ArrayList<Process> processes;
    private ArrayList<Process> remainingProcess;
    private ArrayList<Integer> gantChart;
    private float burstAll;
    private Map<Integer, ArrayList<Process>> arrivals;

    Schedule() {
        Scanner in = new Scanner(System.in);

        processes = new ArrayList<>();
        remainingProcess = new ArrayList<>();
        gantChart = new ArrayList<>();
        arrivals = new HashMap<>();

        System.out.print("Enter the no. of processes: ");
        noOfProcess = in.nextInt();
        System.out.println("Enter the arrival and burst time of processes");
        for (int i = 0; i < noOfProcess; i++) {
            Process p = new Process();
            p.pid = i;
            p.arrivalTime = in.nextInt();
            p.burstTime = in.nextInt();
            p.turnAroundTime = 0;
            p.waitTime = 0;
            p.remainingTime = p.burstTime;

            if (arrivals.get(p.arrivalTime) == null) {
                arrivals.put(p.arrivalTime, new ArrayList<>());
            }
            arrivals.get(p.arrivalTime).add(p);
            processes.add(p);
            burstAll += p.burstTime;
        }
        in.close();
    }

    void startScheduling() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        while (!(arrivals.isEmpty() && remainingProcess.isEmpty())) {
            removeFinishedProcess();
            if (arrivals.containsKey(timer)) {
                remainingProcess.addAll(arrivals.get(timer));
                arrivals.remove(timer);
            }

            remainingProcess.sort(Comparator.comparingInt(p -> p.remainingTime));

            int k = timeElapsed(timer);
            ageing(k);
            timer++;
        }

        System.out.println("Total time required: " + (timer - 1));
    }

    void removeFinishedProcess() {
        ArrayList<Integer> completed = new ArrayList<>();
        for (int i = 0; i < remainingProcess.size(); i++) {
            if (remainingProcess.get(i).remainingTime == 0) {
                completed.add(i);
            }
        }

        for (int i = 0; i < completed.size(); i++) {
            int pid = remainingProcess.get(completed.get(i)).pid;
            processes.get(pid).waitTime = remainingProcess.get(completed.get(i)).waitTime;
            processes.get(pid).completionTime = timer - 1; // Update completion time
            remainingProcess.remove(remainingProcess.get(completed.get(i)));
        }
    }

    public int timeElapsed(int i) {
        if (!remainingProcess.isEmpty()) {
            gantChart.add(i, remainingProcess.get(0).pid);
            remainingProcess.get(0).remainingTime--;
            return 1;
        }
        return 0;
    }

    public void ageing(int k) {
        for (int i = k; i < remainingProcess.size(); i++) {
            remainingProcess.get(i).waitTime++;
        }
    }

    public void solve() {
        System.out.println("Gantt chart:");
        for (int i = 0; i < gantChart.size(); i++) {
            System.out.print(gantChart.get(i) + " ");
        }
        System.out.println();

        float waitTimeTot = 0;
        float tatTime = 0;

        System.out.println("Process\tArrival\tBurst\tCompletion\tWait\tTurnaround");
        for (int i = 0; i < noOfProcess; i++) {
            processes.get(i).turnAroundTime = processes.get(i).waitTime + processes.get(i).burstTime;

            waitTimeTot += processes.get(i).waitTime;
            tatTime += processes.get(i).turnAroundTime;

            System.out.println(processes.get(i).pid + "\t\t" + processes.get(i).arrivalTime + "\t\t" +
                    processes.get(i).burstTime + "\t\t" + processes.get(i).completionTime + "\t\t" +
                    processes.get(i).waitTime + "\t\t" + processes.get(i).turnAroundTime);
        }

        System.out.println("Average Waiting Time: " + waitTimeTot / noOfProcess);
        System.out.println("Average Turnaround Time: " + tatTime / noOfProcess);
        System.out.println("Throughput: " + (float) noOfProcess / (timer - 1));
    }
}

public class SJF {
    public static void main(String[] args) {
        Schedule s = new Schedule();
        s.startScheduling();
        s.solve();
    }
}
