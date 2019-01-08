package cloud.frizio.desktop.training.demovertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;

/**
 * MainHttpClient
 */
public class MainHttpClient {

    public static void main(String[] args) {
		
        Vertx vertx =  Vertx.vertx();       
        HttpClientOptions options = new HttpClientOptions()
                                    .setDefaultHost("localhost")
                                    .setDefaultPort(9090);
        HttpClient client = vertx.createHttpClient(options);

        HttpClientRequest request = client.request(
                HttpMethod.PUT, 
                "/httpvertx?q=1&q=2", 
                response -> {
                    System.out.println("Received response with status code " + response.statusCode());
            }
        );

        request.putHeader("content-length", "12")
               .putHeader("content-type", "text/plain")
               .write("Hello world!")
               .end();
        
    }

}