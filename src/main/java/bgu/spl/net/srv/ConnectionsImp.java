package bgu.spl.net.srv;

import bgu.spl.net.impl.command.ClientFrames.ClientFrame;
import bgu.spl.net.impl.command.ServerFrames.ServerFrame;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsImp<T> implements Connections {

    private Map<Integer, ConnectionHandler<T>> connections;
    private Map<Integer, User> userSubscriptions;
    BookClub bookClub = BookClub.getInstance();
    public ConnectionsImp() {
        this.connections = new ConcurrentHashMap<>();
    }

    /**
     * Sends a message T to client represented by the given connectionId.
     * @param connectionId ID of the given active user.
     * @param msg The message to send
     * @return boolean true if the message sent
     */
    @Override
    public boolean send(int connectionId, Object msg) {
        // find the id in the map
        ConnectionHandler<T> connectionHandler = connections.get(connectionId);
        if (connectionHandler != null) {
            connectionHandler.send((T) msg);
            return true;
        }
        return false;
    }

    /**
     * Sends a message T to clients subscribed to given channel.
     * @param channel
     * @param msg
     */
    @Override
    public void send(String channel, Object msg) {
      for (User tmp : bookClub.getUsersByGenre(channel)){
         send(tmp.getUserId(), msg);
      }
    }


    /**
     * Removes
     * @param connectionId
     */
    @Override
    public void disconnect(int connectionId) {

    }

    public void addConnection(int connectionId, ConnectionHandler connection) {
        connections.put(connectionId, connection);
    }
}
