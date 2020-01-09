package bgu.spl.net.impl.command.ClientFrames;

import bgu.spl.net.impl.command.ServerFrames.ReceiptFrame;
import bgu.spl.net.srv.BookClub;
import bgu.spl.net.srv.ConnectionsImp;

public class UnsubscribeFrame implements ClientFrame {

    private BookClub bookClub = BookClub.getInstance();
    private String subsId;
    private String receiptId;
    private ConnectionsImp<String> connections;

    public UnsubscribeFrame(String subsId, String receiptId) {
        this.subsId = subsId;
        this.receiptId = receiptId;
    }

    @Override
    public void execute(int connectionId) {
        String genre = bookClub.getUser(connectionId).getGenreById(subsId);
        bookClub.exitGenre(genre, bookClub.getUser(connectionId));
        bookClub.getUser(connectionId).unsubscribe(subsId);
        connections.send(connectionId, new ReceiptFrame(receiptId));
    }

    @Override
    public void setConnections(ConnectionsImp<String> connections) {
        this.connections = connections;
    }
}
