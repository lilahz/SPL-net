package bgu.spl.net.impl.command.ClientFrames;

import bgu.spl.net.impl.command.ServerFrames.MessageFrame;
import bgu.spl.net.srv.BookClub;
import bgu.spl.net.srv.ConnectionsImp;
import bgu.spl.net.srv.User;

public class SendFrame implements ClientFrame {

    private BookClub bookClub = BookClub.getInstance();
    private ConnectionsImp connections;
    private String genre;
    private String message;

    public SendFrame(String genre, String message) {
        this.genre = genre;
        this.message = message;
    }

    @Override
    public void execute(int connectionId) {
        String[] message = this.message.split(" ");
        String subsId = bookClub.getUser(connectionId).getSubscriptionId(genre);
        connections.send(genre, new MessageFrame(subsId , genre , this.message));
    }

    @Override
    public void setConnections(ConnectionsImp<String> connections) {
        this.connections = connections;
    }
}
