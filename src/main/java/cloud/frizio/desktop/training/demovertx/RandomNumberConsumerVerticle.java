package cloud.frizio.desktop.training.demovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class RandomNumberConsumerVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        
        EventBus eventBus = getVertx().eventBus();
        
        Integer limit_value = config().getInteger("the_limit_value");

        eventBus.<Integer>consumer(
            "the.channel", 
            message -> {
                final Integer received_value = message.body();
                if ( received_value >= limit_value ) {
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