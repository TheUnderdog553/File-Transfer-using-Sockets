import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(3535);
            while (true) {
                Socket s = ss.accept();

                InputStream is = s.getInputStream();
                OutputStream os = s.getOutputStream();

                Scanner sc = new Scanner(is);
                PrintWriter pw = new PrintWriter(os);

                String command = sc.nextLine();
                String fileName = command.substring(5);

                File f = new File(fileName);
                if (f.exists() && f.isFile()) {
                    int size = (int) f.length();
                    pw.println(size);
                    pw.flush();
                    if (size > 0) {
                        FileInputStream fis = new FileInputStream(f);
                        DataInputStream dis = new DataInputStream(fis);
                        byte[] b = new byte[size];
                        dis.readFully(b);
                        System.out.println("Read file success");
                        fis.close();

                        DataOutputStream dos = new DataOutputStream(os);
                        dos.write(b);
                        System.out.println("Send file success");
                    }
                } else {
                    pw.println(-1);
                    pw.flush();
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket error: " + e);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
        } catch (IOException e) {
            System.out.println("IO Exception.");
        }
    }
}
