package bgu.spl.net.impl.command.ClientFrames;

import bgu.spl.net.impl.command.ServerFrames.ReceiptFrame;
import bgu.spl.net.srv.BookClub;
import bgu.spl.net.srv.ConnectionsImp;

public class SubscribeFrame implements ClientFrame {

    private BookClub bookClub = BookClub.getInstance();

    private ConnectionsImp<String> connections;
    private String genre;
    private String subscriptionId;
    private String receiptId;

    public SubscribeFrame(String genre, String subscriptionId, String receiptId) {
        this.genre = genre;
        this.subscriptionId = subscriptionId;
        this.receiptId = receiptId;
    }

    @Override
    public void execute(int connectionId) {
        bookClub.joinGenre(genre, bookClub.getUser(connectionId));
        bookClub.getUser(connectionId).subscribe(subscriptionId, genre);
        connections.send(connectionId, new ReceiptFrame(receiptId));
    }

    @Override
    public void setConnections(ConnectionsImp<String> connections) {
        this.connections = connections;
    }
}
