package bgu.spl.net.impl.command.ServerFrames;

public class ReceiptFrame implements ServerFrame {
    private String recipetID;

    public ReceiptFrame(String receiptId){
        this.recipetID = receiptId;
    }

    public String toFrame(){
        String output = "RECEIPT\nreceipt-id:" + recipetID + "\n\n" + '\u0000';
        return output;
    }
}
