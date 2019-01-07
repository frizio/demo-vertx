package cloud.frizio.desktop.training.demovertx;

import java.util.Random;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class RandomNumberProducerVerticle extends AbstractVerticle {
    
    private static final Integer MAX_VALUE = 100;

    Random random = new Random(); 

    @Override
    public void start() throws Exception {
        
        EventBus eventBus = getVertx().eventBus();

        getVertx().setPeriodic( 
            1000,
            id -> {
                final Integer value = random.nextInt(MAX_VALUE);
                System.out.println("PRODUCER: Emit a random number: " + value +  ". Sending them via a message");
                eventBus.<Integer>send(
                    "the.channel", 
                    value, 
                    asyncResult -> {
                        if ( asyncResult.succeeded() ) {
                            System.out.println( "PRODUCER: Received reply: " + asyncResult.result().body());
                        } else {
                            System.out.println( "PRODUCER: Received exception: " + asyncResult.cause().getMessage() );
                        }
                        System.out.println(" ");
                    }
                );
            }
        );
    }

}