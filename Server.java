import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.IOException;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        String receivedString;
        InetAddress address;
        int port;
        byte[] txt = new byte[1024];
        byte[] msg = new byte[1024];
        DatagramSocket socket = new DatagramSocket(8989);
        DatagramPacket packet;
        List<Info> userList = new ArrayList<>();

        while(true) {
            try {
                // receive datagram
                packet = new DatagramPacket(txt, txt.length);
                socket.setSoTimeout(500);
                socket.receive(packet);
                System.out.println("\nReceived packet");
                // process received data
                receivedString = new String(packet.getData(), 0, packet.getLength());
                String vars[] = receivedString.split("\\s");
                address = packet.getAddress();
                port = packet.getPort();
                System.out.println("\n" + vars[0]);

                if(vars[0].equals("login") && vars.length > 1) {
                    final Integer p = port;
                    if(userList.stream().map(a -> a.msgPort == p).filter(a -> a).collect(Collectors.toList()).size() > 0) { continue; }
                    userList.add(new Info(vars[1], address, port));
                    System.out.println("\nRegistered: " + vars[1]);
                    // send back ok message
                    msg = "OK".getBytes();
                    packet = new DatagramPacket(msg, msg.length, address, port);
                    socket.send(packet);
                } else if(vars[0].equals("list") && vars.length > 1) {
                    for(Info info : userList) {
                        if(info.message.equals(vars[1])) {
                            System.out.println("\nItems: " + userList.size());

                            for(Info otherInfo : userList) {
                                String msg_str = new String(otherInfo.message + " " +
                                 otherInfo.address.toString() + " " + otherInfo.msgPort.toString());
                                System.out.println("\n" + msg_str);
                                packet = new DatagramPacket(msg_str.getBytes(), msg_str.getBytes().length, address, port);
                                socket.send(packet);
                            }
                            break;
                        }
                    }
                }

            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
    }
}