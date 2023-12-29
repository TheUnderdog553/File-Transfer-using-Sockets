import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner kb = new Scanner(System.in);
            System.out.print("Enter ip address: ");
            String ip = kb.nextLine();
            System.out.println("Enter File Location: ");
            String filePath = kb.nextLine();

            Socket s = new Socket(ip, 3535);
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            Scanner sc = new Scanner(is);
            PrintWriter pw = new PrintWriter(os);

            String command = "READ " + filePath;
            pw.println(command);

            pw.flush();


            String sizeStr = sc.nextLine();
            int size = Integer.parseInt(sizeStr);
            if (size == -1) {
                System.out.println("File " + filePath + ": Not found.");
            } else if (size == 0) {
                System.out.println("File " + filePath + ": Empty.");
            } else {
                System.out.println("Enter new location to save: ");
                String newFileLocation = kb.nextLine();
                FileOutputStream fos = new FileOutputStream(newFileLocation);
                byte[] b = new byte[10000];
                int sum = 0;
                DataInputStream dis = new DataInputStream(is);

                do {
                    int n = dis.read(b, 0, 10000);
                    fos.write(b, 0, n);
                    sum += n;
                    System.out.println(sum + "bytes downloaded");
                } while (sum != size);

                System.out.println("Downloaded File Successfully.");
                fos.close();
                dis.close();
            }
        } catch (UnknownHostException e) {
            System.out.println("Can't connect to ip Sever");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
        } catch (IOException e) {
            System.out.println("IO Exception.");
        }
    }
}
