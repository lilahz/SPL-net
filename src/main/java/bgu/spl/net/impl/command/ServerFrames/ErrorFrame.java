package bgu.spl.net.impl.command.ServerFrames;

import bgu.spl.net.srv.ConnectionsImp;

public class ErrorFrame implements ServerFrame {

    private ConnectionsImp connections;
    private String errorMessage;

    public ErrorFrame(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String toFrame() {
        String output = "ERROR\nmessage: " + errorMessage + "\n\n" + '\u0000';
        return output;
    }
}
