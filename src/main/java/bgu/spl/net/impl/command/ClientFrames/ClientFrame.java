package bgu.spl.net.impl.command.ClientFrames;

import bgu.spl.net.srv.ConnectionsImp;

public interface ClientFrame {
    void execute(int connectionId);

    void setConnections(ConnectionsImp<String> connections);
}
