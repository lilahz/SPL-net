package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.impl.command.ClientFrames.ClientFrame;
import bgu.spl.net.srv.BookClub;
import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.ConnectionsImp;

public class StompMessagingProtocolImp<T> implements StompMessagingProtocol<T> {

    private BookClub bookClub = BookClub.getInstance();
    private int connectionId;
    private Connections<String> connections;
    private boolean shouldTerminate = false;

    public StompMessagingProtocolImp() {}

    @Override
    public void start(int connectionId, Connections<String> connections) {
        this.connectionId = connectionId;
        this.connections = connections;
    }

    @Override
    public void process(Object message) {
        ((ClientFrame)message).setConnections((ConnectionsImp<String>) connections);
        ((ClientFrame)message).execute(connectionId);

//        if (message instanceof ){
//            //TODO : implement this.
//        }


    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
