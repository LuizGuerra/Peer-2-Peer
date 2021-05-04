import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PeerThread extends Thread {
    protected DatagramSocket socket = null;
    protected DatagramPacket packet = null;
    protected InetAddress address = null;
    protected byte[] text = new byte[1024];
    protected int port;
    
    public PeerThread(String[] args) throws IOException {
        text = args[1].getBytes();
        address = InetAddress.getByName(args[0]);
        port = Integer.parseInt(args[2]);
        socket = new DatagramSocket(port);
    }

    public void run() {
        byte[] sendTextString = new byte[1024];
        try {
            packet = new DatagramPacket(text, text.length, address, 8989);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                // get answer
                packet = new DatagramPacket(sendTextString, sendTextString.length);
                socket.setSoTimeout(500);
                socket.receive(packet);
                // show answer
                String answer = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received: " + answer);
            } catch (IOException e) {
                // e.printStackTrace();
                break;
            }
        }
    }
}
