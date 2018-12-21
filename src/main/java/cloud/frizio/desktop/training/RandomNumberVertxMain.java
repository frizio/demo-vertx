package cloud.frizio.desktop.training;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class RandomNumberVertxMain {

	private Vertx vertx =  Vertx.vertx();
	private String producerId;
	private String consumerId;
	
	public void startProducer() {
		DeploymentOptions options = new DeploymentOptions();
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
			}
		);
	}
	
	public void startConsumer() {
		DeploymentOptions options = new DeploymentOptions().setInstances(2);
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
			}
		);
	}
	
	public void stopAll() {
		vertx.setTimer( 
			5000, 
			id -> {		
				vertx.undeploy(
					producerId, 
					result -> {
						if (result.succeeded()) {
				    		System.out.println("Undeployed ok");
						} else {
							System.out.println("Undeploy failed!");
						}
					}
				);
				vertx.undeploy(
					consumerId, 
					result -> {
						if (result.succeeded()) {
							System.out.println("Undeployed ok");
						} else {
							System.out.println("Undeploy failed!");
						}
					}
				);
			}
		);
	}


	public static void main(String[] args) {
		RandomNumberVertxMain main = new  RandomNumberVertxMain();
		main.startProducer();
		main.startConsumer();
		main.stopAll();
	}

}