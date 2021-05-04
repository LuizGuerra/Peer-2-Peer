import java.net.InetAddress;

public class Info {
    String message;
    InetAddress address;
    Integer msgPort;
    public Info(String _message, InetAddress _address, Integer _msgPort) {
        message = _message;
        address = _address;
        msgPort = _msgPort;
    }
}