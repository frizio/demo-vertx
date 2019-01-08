package cloud.frizio.desktop.training.demovertx;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

/**
 * MainTcpClient
 */
public class MainTcpClient {

    public static void main(String[] args) {
		
        Vertx vertx =  Vertx.vertx();
            
        NetClientOptions options = new NetClientOptions()
                                        .setConnectTimeout(10000)
                                        .setReconnectAttempts(10)
                                        .setReconnectInterval(500);
        NetClient client = vertx.createNetClient(options);

        client.connect(
            9090, 
            "localhost", 
            result -> {
                if (result.succeeded()) {
                    System.out.println("Connected!");
                    NetSocket socket = result.result();
                    socket.write( "Hi I'm the TCP client." );
                } else {
                    System.out.println("Failed to connect: " + result.cause().getMessage());
                }
            }
        );

    }

}