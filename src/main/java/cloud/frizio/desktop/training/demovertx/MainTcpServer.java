package cloud.frizio.desktop.training.demovertx;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

/**
 * App
 */
public class MainTcpServer {

    public static void main(String[] args) {
        
        Vertx vertx =  Vertx.vertx();
        
        // Creazione di un oggetto di tipo NetServer (che rappresenta un server TCP)
        NetServerOptions options = new NetServerOptions()
                                        .setHost("localhost")                                
                                        .setPort(9090);   
        NetServer server = vertx.createNetServer(options);
        
        // Definizione di un handler che viene invocato dal server ogni volta che una nuova connessione viene aperta da un client
        server.connectHandler( 
            socket -> {
                socket.handler(
                    buffer -> {
                        System.out.println("Open new connection");
                        System.out.println("SERVER BUFFER: " + buffer.toString());
                    }
                );
            }
        );

        //
        server.listen( 
            result -> {
                if (result.succeeded()) {
                    System.out.println("Server is now listening...");
                } else {
                    System.out.println("Failed to bind!");
                }
            }
        );

    }

}