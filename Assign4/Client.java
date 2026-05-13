
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static String formatTime(long millis) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date(millis));
    }

    public static void main(String[] args) {
        int port = 8080;

        try (Socket socket = new Socket("127.0.0.1", port)) {
            System.out.println("Connected to clock server");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread sendTimeThread = new Thread(() -> sendLocalTime(out));
            Thread receiveTimeThread = new Thread(() -> receiveSynchronizedTime(in));

            sendTimeThread.start();
            receiveTimeThread.start();

            sendTimeThread.join();
            receiveTimeThread.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendLocalTime(PrintWriter out) {
        while (true) {
            try {
                long currentTime = System.currentTimeMillis();
                out.println(currentTime);
                System.out.println("Sent local time: " + formatTime(currentTime));
                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.println("Error while sending time");
                break;
            }
        }
    }

    private static void receiveSynchronizedTime(BufferedReader in) {
        while (true) {
            try {
                String line = in.readLine();
                if (line == null) {
                    System.out.println("Server disconnected");
                    break;
                }

                long syncedTime = Long.parseLong(line.trim());
                System.out.println("Synchronized time received: " + formatTime(syncedTime));

            } catch (Exception e) {
                System.out.println("Error while receiving synchronized time");
                break;
            }
        }
    }
}