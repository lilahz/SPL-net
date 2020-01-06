package bgu.spl.net.impl.command.ClientFrames;

import bgu.spl.net.impl.command.ServerFrames.ReceiptFrame;
import bgu.spl.net.srv.BookClub;
import bgu.spl.net.srv.ConnectionsImp;
import bgu.spl.net.srv.User;

public class DisconnectFrame implements ClientFrame {

    private String receiptId;
    private BookClub bookClub = BookClub.getInstance();
    private ConnectionsImp connections;

    public DisconnectFrame(String receiptId){
        this.receiptId = receiptId;
    }



    @Override
    public void execute(int connectionId) {
        User tmpUser = bookClub.getUser(connectionId);
        tmpUser.unSubscribeAll();
        bookClub.removeUser(tmpUser);
        connections.send(connectionId, new ReceiptFrame(receiptId));

    }

    @Override
    public void setConnections(ConnectionsImp<String> connections) {
        this.connections = connections;
    }
}
