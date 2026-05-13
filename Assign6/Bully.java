import java.util.*;

public class Bully {
    int coordinator;
    int max_processes;
    boolean processes[];

    public Bully(int max) {
        max_processes = max;
        processes = new boolean[max_processes];
        coordinator = max;

        System.out.println("\n--- System Initialization ---");
        System.out.println("Creating " + max + " processes...");
        for (int i = 0; i < max; i++) {
            processes[i] = true;
            System.out.println("Process [P" + (i + 1) + "] is starting up and active.");
        }
        System.out.println("--> Process [P" + coordinator + "] is automatically elected as the initial coordinator.\n");
    }

    void displayProcesses() {
        System.out.println("\n--- Current System State ---");
        for (int i = 0; i < max_processes; i++) {
            if (processes[i]) {
                System.out.println("Process [P" + (i + 1) + "] : UP (Active)");
            } else {
                System.out.println("Process [P" + (i + 1) + "] : DOWN (Inactive)");
            }
        }
        System.out.println("--> Current Coordinator is Process [P" + coordinator + "]\n");
    }

    void upProcess(int process_id) {
        if (!processes[process_id - 1]) {
            processes[process_id - 1] = true;
            System.out.println("\n[SUCCESS] Process [P" + process_id + "] has been started and is now UP.");
        } else {
            System.out.println("\n[INFO] Process [P" + process_id + "] is already UP.");
        }
    }

    void downProcess(int process_id) {
        if (!processes[process_id - 1]) {
            System.out.println("\n[INFO] Process [P" + process_id + "] is already DOWN.");
        } else {
            processes[process_id - 1] = false;
            System.out.println("\n[WARNING] Process [P" + process_id + "] has crashed and is now DOWN.");
        }
    }

    void runElection(int process_id) {
        System.out.println("\n--- Election Initiated by Process [P" + process_id + "] ---");
        coordinator = process_id;
        boolean keepGoing = true;

        for (int i = process_id; i < max_processes && keepGoing; i++) {
            System.out.println("[P" + process_id + "] sends ELECTION message to higher priority process [P" + (i + 1) + "]");
            if (processes[i]) {
                System.out.println("  -> [P" + (i + 1) + "] responds with OK to [P" + process_id + "]");
                System.out.println("  -> [P" + process_id + "] stops its election and waits.");
                keepGoing = false;
                runElection(i + 1);
            } else {
                System.out.println("  -> No response from [P" + (i + 1) + "] (Process is DOWN)");
            }
        }
        
        if (keepGoing) {
            System.out.println("\n*** ELECTION RESULT ***");
            System.out.println("--> Process [P" + coordinator + "] has won the election and announces it is the new COORDINATOR!");
        }
    }

    public static void main(String args[]) {
        Bully bully = null;
        int max_processes = 0, process_id = 0;
        int choice = 0;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=================================");
            System.out.println("      Bully Algorithm Menu");
            System.out.println("=================================");
            System.out.println("1. Initialize processes");
            System.out.println("2. Display system state");
            System.out.println("3. Bring a process UP");
            System.out.println("4. Bring a process DOWN");
            System.out.println("5. Run Election algorithm");
            System.out.println("6. Exit Program");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the total number of processes: ");
                    max_processes = sc.nextInt();
                    bully = new Bully(max_processes);
                    break;
                case 2:
                    if (bully != null) bully.displayProcesses();
                    else System.out.println("\n[ERROR] Initialize processes first! (Option 1)");
                    break;
                case 3:
                    System.out.print("Enter the process number to bring UP: ");
                    process_id = sc.nextInt();
                    if (bully != null) bully.upProcess(process_id);
                    else System.out.println("\n[ERROR] Initialize processes first! (Option 1)");
                    break;
                case 4:
                    System.out.print("Enter the process number to bring DOWN: ");
                    process_id = sc.nextInt();
                    if (bully != null) bully.downProcess(process_id);
                    else System.out.println("\n[ERROR] Initialize processes first! (Option 1)");
                    break;
                case 5:
                    System.out.print("Enter the process number that will initiate the election: ");
                    process_id = sc.nextInt();
                    if (bully != null) {
                        bully.runElection(process_id);
                        bully.displayProcesses();
                    } else {
                        System.out.println("\n[ERROR] Initialize processes first! (Option 1)");
                    }
                    break;
                case 6:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\n[ERROR] Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
