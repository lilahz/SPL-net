package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.BaseServer;
import bgu.spl.net.srv.MessageEncoderDecoderImp;
import bgu.spl.net.srv.Server;

public class StompServer {

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        if (args[1] == "tpc") {
            Server server = Server.threadPerClient(
                    port,
                    () -> new StompMessagingProtocolImp<>(),
                    () -> new MessageEncoderDecoderImp()
            );
            server.serve();
        }

        else if (args[1] == "reactor") {

            Server server = Server.reactor(
                    Runtime.getRuntime().availableProcessors(),
                    port,
                    () -> new StompMessagingProtocolImp<>(),
                    () -> new MessageEncoderDecoderImp()
            );
        }



    }


}
