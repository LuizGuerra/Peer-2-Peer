public class Peer {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Peer <server> \"<message>\" <localport>");
            System.exit(1);
        }
        try {
            (new PeerThread(args)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
