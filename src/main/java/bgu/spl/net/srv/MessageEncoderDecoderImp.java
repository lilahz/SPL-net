package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.command.ClientFrames.*;
import bgu.spl.net.impl.command.ServerFrames.ServerFrame;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageEncoderDecoderImp implements MessageEncoderDecoder {

    private byte[] bytes;
    private int length;

    public MessageEncoderDecoderImp() {
        this.bytes = new byte[1 << 10];
        this.length = 0;
    }

    @Override
    public Object decodeNextByte(byte nextByte) {
        // TODO: understand which end byte use
        if (nextByte == '\u0000') {
            String msg = new String(bytes, 0, length, StandardCharsets.UTF_8);
            if (msg.charAt(0) == '\n') //TODO : maybe we can remove \n at the encoder
               msg = msg.substring(1);
            length = 0;
            return createFrame(msg);
        }
        pushByte(nextByte);
        return null;
    }

    @Override
    public byte[] encode(Object message) {
        //TODO the message doesnt sent good to send in NBCH
        String msg = ((ServerFrame)message).toFrame();
        System.out.println("sending: ");
        System.out.println(msg);
        return (msg).getBytes();
    }

    public void pushByte(byte nextByte) {
        if (length >= bytes.length) {
            bytes = Arrays.copyOf(bytes, length*2);
        }
        bytes[length++] = nextByte;
    }

    public ClientFrame createFrame(String msg) {
        // Connect frame fields
        String version;
        String host;
        String userName;
        String password;
        // Subscribe frame fields
        String genre;
        String subscriptionId;
        String receiptId;
        // Send frame fields
        String message;

        String[] msgSplit = msg.split("\n");
        String frameType = msgSplit[0];
        if (frameType.equals("CONNECT")) {
            version = msgSplit[1].split(":")[1];
            userName = msgSplit[3].split(":")[1];
            password = msgSplit[4].split(":")[1];
            return  (new ConnectFrame(version, userName, password));
        }
        else if (frameType.equals("SUBSCRIBE")) {
            genre = msgSplit[1].split(":")[1];
            subscriptionId = msgSplit[2].split(":")[1];
            receiptId = msgSplit[3].split(":")[1];
            return (new SubscribeFrame(genre, subscriptionId, receiptId));
        }
        else if (frameType.equals("UNSUBSCRIBE")){
            subscriptionId = msgSplit[1].split(":")[1];
            receiptId = msgSplit[2].split(":")[1];
            return (new UnsubscribeFrame(subscriptionId, receiptId));
        }
        else if (frameType.equals("SEND")) {
            genre = msgSplit[1].split(":")[1];
            message = msgSplit[3];
            return (new SendFrame(genre, message));
        }
        else if (frameType.equals("DISCONNECT")){
         receiptId = msgSplit[1].split(":")[1];
         return (new DisconnectFrame(receiptId));

        }
        return null;
    }
}
