package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.BookClub;
import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.ConnectionsImp;

public class StompMessagingProtocolImp<T> implements StompMessagingProtocol<T> {

    private BookClub bookClub = BookClub.getInstance();
    private int connectionId;
    private Connections<String> connections;
    private boolean shouldTerminate = false;
//    private String version;
//    private String host;
//    private String userName;
//    private String password;
//    private String genre;
//    private int subsId;


    @Override
    public void start(int connectionId, Connections<String> connections) {
        this.connectionId = connectionId;
        this.connections = connections;
    }

    @Override
    public void process(T message) {
        ((ServerFrame)message).setConnections((ConnectionsImp<String>) connections);
        ((ServerFrame)message).execute(connectionId);

//        if (message instanceof ){
//            //TODO : implement this.
//        }
//        String[] messageSplited = message.split("\n");
//        String command = messageSplited[0];
//        if (command == "CONNECT") {
//            this.version = messageSplited[1].split(":")[1];
//            this.host = messageSplited[2].split(":")[1];
//            this.userName = messageSplited[3].split(":")[1];
//            this.password = messageSplited[4].split(":")[1];
//            //TODO: understand how to send the host and port to the command
//            ClientFrame loginCommand = new ConnectFrame(Integer.parseInt(host), userName, password);
//            loginCommand.execute(this.connectionId);
//        }
//        else if (command == "SUBSCRIBE") {
//            this.genre = messageSplited[1].split(":")[1];
//            this.subsId = Integer.parseInt(messageSplited[2].split(":")[1]);
//            ClientFrame joinCommand = new SubscribeFrame(genre);
//            joinCommand.execute(connectionId);
//        }
//        else if(command == "UNSUBSCRIBE") {
//            this.subsId = Integer.parseInt(messageSplited[1].split(":")[1]);
//            ClientFrame exitCommand = new UnsubscribeFrame(genre);
//            exitCommand.execute(connectionId);
//        }
//        else if (command == "SEND") {
//            this.genre = messageSplited[1].split(":")[1];
//            String[] messageFromClient = messageSplited[2].split(" ");
//
//        }

    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
