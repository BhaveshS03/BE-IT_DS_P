import java.util.Scanner;

class Tring {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Token Ring Network Simulation ---");
        System.out.print("Enter the number of nodes: ");
        int n = sc.nextInt();

        // Decides the number of nodes forming the ring
        int token = 0;

        System.out.print("Initialized Ring: ");
        for (int i = 0; i < n; i++) {
            System.out.print(i + " -> ");
        }
        System.out.println("0");

        try {
            while (true) {
                System.out.println("\n-----------------------------------------");
                System.out.print("Enter sender node: ");
                int s = sc.nextInt();
                System.out.print("Enter receiver node: ");
                int r = sc.nextInt();
                System.out.print("Enter data to send: ");
                String d = sc.next();

                System.out.println("\n[Phase 1: Token Passing]");
                System.out.print("Passing token: ");
                // current token not equal to sender, increment i by 1 and j by j+1%n
                for (int i = token, j = token; (i % n) != s; i++, j = (j + 1) % n) {
                    System.out.print(j + " -> ");
                }
                System.out.println(s);
                System.out.println("Node " + s + " has received the token!");

                System.out.println("\n[Phase 2: Data Transmission]");
                System.out.println("Sender " + s + " is sending data: [" + d + "]");

                // start forwarding from node after sender until it becomes equal to receiver
                // and increment by i+1%n
                for (int i = (s + 1) % n; i != r; i = (i + 1) % n) {
                    System.out.println("  -> Data [" + d + "] forwarded by node " + i);
                }
                System.out.println("Receiver " + r + " successfully received data: [" + d + "]");
                
                token = s;
            }
        } catch (Exception e) {
            System.out.println("\nSimulation terminated or error occurred: " + e.getMessage());
        }
    }
}
