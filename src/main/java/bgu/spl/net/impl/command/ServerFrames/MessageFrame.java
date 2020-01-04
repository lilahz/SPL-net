package bgu.spl.net.impl.command.ServerFrames;


import bgu.spl.net.srv.ConnectionsImp;

import java.util.concurrent.atomic.AtomicInteger;

public class MessageFrame implements ServerFrame {
    private String subscriptionId;
    private static AtomicInteger messageId;
    private String genre;
    private String msg;

    public MessageFrame(String subscriptionId, String genre, String msg) {
        this.subscriptionId = subscriptionId;
        this.genre = genre;
        this.msg = msg;
        this.messageId = new AtomicInteger(0);
    }

    public String toFrame() {
        String output = "MESSAGE\nsubscription:" + subscriptionId + "\nMessage-id:" + messageId
        +"\ndestination:" + genre + "\n\n" + msg +"\n" + '\u0000';
        int oldVal, newVal;
        do {
            oldVal = messageId.intValue();
            newVal = messageId.intValue() + 1;
        } while (!messageId.compareAndSet(oldVal, newVal));
        return output;
    }
}
