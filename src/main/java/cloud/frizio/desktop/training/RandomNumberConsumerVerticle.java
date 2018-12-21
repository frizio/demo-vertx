package cloud.frizio.desktop.training;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * RandomNumberConsumerVericle
 */
public class RandomNumberConsumerVerticle extends AbstractVerticle {
    
    @Override
    public void start() throws Exception {
        
        EventBus eventBus = getVertx().eventBus();
		
        eventBus.<Integer>consumer(
            "javaboss.channel", 
            message -> {
                System.out.println("CONSUMER: I have received the message: " + message.body());
                if ( message.body() >= 50 ) {
                    message.fail( 0, "I don't like it!");
                } else {
                    message.reply( "ok!" );
                }
            }
        );
    }
    
}