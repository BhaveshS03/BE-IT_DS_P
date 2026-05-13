
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static class ClientInfo {
        long clientTime;
        long timeDifference;
        PrintWriter out;

        ClientInfo(PrintWriter out) {
            this.out = out;
        }
    }

    private static final Map<String, ClientInfo> clientData = new ConcurrentHashMap<>();

    private static String formatTime(long millis) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date(millis));
    }

    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Clock server started on port " + port);

            Thread acceptThread = new Thread(() -> acceptClients(serverSocket));
            acceptThread.start();

            Thread syncThread = new Thread(Server::synchronizeAllClocks);
            syncThread.start();

            acceptThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void acceptClients(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                String clientId = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
                System.out.println(clientId + " connected successfully");

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ClientInfo info = new ClientInfo(out);
                clientData.put(clientId, info);

                Thread clientThread = new Thread(() -> receiveClockTime(socket, clientId, info));
                clientThread.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void receiveClockTime(Socket socket, String clientId, ClientInfo info) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                long clientTime = Long.parseLong(line.trim());
                long serverTime = System.currentTimeMillis();
                long diff = serverTime - clientTime;

                info.clientTime = clientTime;
                info.timeDifference = diff;

                System.out.println("Updated data from " + clientId +
                        " | clientTime=" + formatTime(clientTime) +
                        " | diff=" + diff + " ms");
            }
        } catch (Exception e) {
            System.out.println(clientId + " disconnected");
        } finally {
            clientData.remove(clientId);
            try {
                socket.close();
            } catch (Exception ignored) {
            }
        }
    }

    private static long getAverageClockDiff() {
        if (clientData.isEmpty()) {
            return 0;
        }

        long sum = 0;
        for (ClientInfo info : clientData.values()) {
            sum += info.timeDifference;
        }
        return sum / clientData.size();
    }

    private static void synchronizeAllClocks() {
        while (true) {
            try {
                Thread.sleep(5000);

                System.out.println("\nNew synchronization cycle started.");
                System.out.println("Number of clients: " + clientData.size());

                if (clientData.isEmpty()) {
                    System.out.println("No client data. Synchronization not needed.\n");
                    continue;
                }

                long averageDiff = getAverageClockDiff();
                long synchronizedTime = System.currentTimeMillis() + averageDiff;

                for (Map.Entry<String, ClientInfo> entry : clientData.entrySet()) {
                    try {
                        entry.getValue().out.println(synchronizedTime);
                    } catch (Exception e) {
                        System.out.println("Could not send time to " + entry.getKey());
                    }
                }

                System.out.println("Sent synchronized time: " + formatTime(synchronizedTime) + "\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}