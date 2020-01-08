package bgu.spl.net.impl.command.ClientFrames;

import bgu.spl.net.impl.command.ServerFrames.ConnectedFrame;
import bgu.spl.net.impl.command.ServerFrames.ErrorFrame;
import bgu.spl.net.srv.BookClub;
import bgu.spl.net.srv.ConnectionsImp;
import bgu.spl.net.srv.User;

public class ConnectFrame implements ClientFrame {

    private BookClub bookClub = BookClub.getInstance();
    private String version;
    private String userName;
    private String password;
    private ConnectionsImp<String> connections;

    public ConnectFrame(String version, String userName, String password) {
        this.version = version;
        this.userName = userName;
        this.password = password;
    }

    public void execute(int connectionId) {
        //TODO: Socket error: connection error. in this case the output should be "Could not connect to server"

        // Search the user in the users list
        User user = bookClub.getUser(userName);
        if (user != null) {
            // Check if the password matches
            if (password == user.getPassword()) {
                // Check if the user is already active
                if (user.isActive()) {
                    connections.send(connectionId, new ErrorFrame("User already logged in"));
                }
                else {
                    user.setActive(connectionId);
                    connections.send(connectionId, new ConnectedFrame(version));
                }
            }
            // If the password does not match
            else {
                connections.send(connectionId, new ErrorFrame("Wrong password"));
            }
        }
        // If user does not exist
        else {
            bookClub.addUser(new User(connectionId, userName, password));
            connections.send(connectionId, new ConnectedFrame(version));
        }
    }

    public void setConnections(ConnectionsImp<String> connections) {
        this.connections = connections;
    }
}
