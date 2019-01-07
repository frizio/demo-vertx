package cloud.frizio.desktop.training.demovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * RandomNumberConsumerVericle
 */
public class RandomNumberConsumerVerticle extends AbstractVerticle {

    private static final Integer LIMIT_VALUE = 50;
    
    @Override
    public void start() throws Exception {
        
        EventBus eventBus = getVertx().eventBus();
		
        eventBus.<Integer>consumer(
            "the.channel", 
            message -> {
                System.out.println("CONSUMER: I have received the message: " + message.body());
                if ( message.body() >= LIMIT_VALUE ) {
                    System.out.println("CONSUMER: I don'like it!. Sending them...");
                    message.fail( 0, "KOO!");
                } else {
                    System.out.println("CONSUMER: I like it!. Sending them...");
                    message.reply( "OKK!" );
                }
            }
        );
    }
    
}