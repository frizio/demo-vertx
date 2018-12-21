package cloud.frizio.desktop.training;

import java.util.Random;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class RandomNumberProducerVerticle extends AbstractVerticle {
    Random random = new Random(); 

    public void start() throws Exception {
        
        EventBus eventBus = getVertx().eventBus();

        getVertx().setPeriodic( 
            1000,
            id -> {
                eventBus.<Integer>send(
                    "javaboss.channel", 
                    random.nextInt(100), 
                    asyncResult -> {
                        if ( asyncResult.succeeded() ) {
                            System.out.println( "PRODUCER: Received reply: " + asyncResult.result().body());
                        } else {
                            System.out.println( "PRODUCER: Received exception: " + asyncResult.cause().getMessage() );
                        }
                    }
                );
            }
        );
    }

}