package cloud.frizio.desktop.training.demovertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class RandomNumberVertxMain {

	private static final Integer MAX_TIME = 6000;
	private static final Integer NUMBER_OF_PRODUCERS = 1;
	private static final Integer MAX_VALUE = 30;


	private Vertx vertx =  Vertx.vertx();
	private String producerId;
	private String consumerId;
	
	public void startProducer(int instances, int max_value) {

		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(instances);
		
		JsonObject config = new JsonObject().put("the_max_value", max_value);
    	options.setConfig(config);
		
		vertx.deployVerticle( 
			RandomNumberProducerVerticle.class, 
			options, 
			result -> {
				if ( result.succeeded() ) {
					producerId = result.result();
					System.out.println("Producer deployed with id: " + producerId );
				} else {
					System.out.println("Producer deployment failed!");
				}
				System.out.println(" ");
			}
		);
	}
	
	public void startConsumer(Integer limit_value) {
		DeploymentOptions options = new DeploymentOptions();

		JsonObject config = new JsonObject().put("the_limit_value", limit_value);
    	options.setConfig(config);

		vertx.deployVerticle( 
			RandomNumberConsumerVerticle.class, 
			options, 
			result -> {
				if ( result.succeeded() ) {
					consumerId = result.result();
					System.out.println("Consumer deployed with id: " + consumerId );
				} else {
					System.out.println("Consumer deployment failed!");
				}
				System.out.println(" ");
			}
		);
	}
	
	public void stopAll() {
		vertx.setTimer( 
			MAX_TIME, 
			id -> {
				System.out.println("\nIt's time to undeploy");
				vertx.undeploy(
					producerId, 
					result -> {
						if (result.succeeded()) {
				    		System.out.println("OK, Undeployed producer with ID " + producerId);
						} else {
							System.out.println("FAIL Undeploy producer with ID " + producerId);
						}
					}
				);
				vertx.undeploy(
					consumerId, 
					result -> {
						if (result.succeeded()) {
							System.out.println("OK, Undeployed consumer with ID " + consumerId);
						} else {
							System.out.println("FAIL Undeploy consumer with ID " + consumerId);
						}
					}
				);
			}
		);
	}


	public static void main(String[] args) {
		RandomNumberVertxMain main = new  RandomNumberVertxMain();
		main.startProducer(NUMBER_OF_PRODUCERS, MAX_VALUE);
		main.startConsumer(MAX_VALUE/2);
		main.stopAll();
	}

}