package cloud.frizio.desktop.training.demovertx;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;

/**
 * App
 */
public class MainHttpServer {

    public static void main(String[] args) {
        
        // Creazione e Configurazione del Server
        Vertx vertx =  Vertx.vertx();    
        HttpServerOptions options = new HttpServerOptions()
                                            .setHost("localhost")
                                            .setPort(9090)
                                            //.setUseAlpn(true)
                                            //.setSsl(true)
                                            //.setKeyStoreOptions(new JksOptions().setPath("/path/to/my/keystore"))
                                    ;  
        HttpServer server = vertx.createHttpServer(options);

        // Definizione dell’handler di gestione delle richieste
        Buffer totalBuffer = Buffer.buffer();

        server.requestHandler( 
            request -> {
                System.out.println( "URI: " + request.uri() );
                System.out.println( "METHOD: " + request.method() );
                System.out.println( "VERSIONE: " + request.version() );
                System.out.println( "PATH: " + request.path() );
                System.out.println( "QUERY: " + request.query() );
                
                System.out.println("");
                System.out.println("PARAMS:");
                request.params().forEach( 
                    p -> System.out.println(p.getKey() + ": " + p.getValue()) 
                );
                System.out.println("HEADERS");
			    request.headers().forEach( 
                    h -> System.out.println(h.getKey() + ": " + h.getValue()) 
                );
                System.out.println("");
                
                /*
                request.handler(
                    buffer -> {
                        totalBuffer.appendBuffer(buffer);
                    }
                ); 
                request.endHandler( 
                    v -> {
                        System.out.println( totalBuffer.toString() );
                    }
                );
                */

                request.bodyHandler(
                    buffer -> {
                        System.out.println( "Received " + buffer.length() + " bytes");
                    }
                );

                // Invio della risposta
                request.bodyHandler(
                    buffer -> {
                        System.out.println(buffer.toString());
                        System.out.println("Received " + buffer.length() + " bytes");
                        HttpServerResponse response = request.response();
                        response.setStatusCode(200);
                        response.setChunked(true);
                        response.write("Thanks!");
                        response.end();
                });

            }
        );

        // Avvio del server
        server.listen( 
            result -> {
                if (result.succeeded()) {
                    System.out.println("Server HTTP is now listening!");
                } else {
                    System.out.println("Failed to bind!");
                }
            }
        );

    }
}