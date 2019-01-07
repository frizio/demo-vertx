package cloud.frizio.desktop.training.demovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class RandomNumberConsumerVerticle extends AbstractVerticle {

    private static final Integer LIMIT_VALUE = 50;
    
    @Override
    public void start() throws Exception {
        
        EventBus eventBus = getVertx().eventBus();
		
        eventBus.<Integer>consumer(
            "the.channel", 
            message -> {
                final Integer received_value = message.body();
                if ( received_value >= LIMIT_VALUE ) {
                    System.out.println("CONSUMER: I DON'T LIKE " + received_value);
                    message.fail(0, message.body() + " is KOO!");
                } else {
                    System.out.println("CONSUMER: I LIKE " + received_value);
                    message.reply(received_value + " is OKK!");
                }
            }
        );
    }
    
}