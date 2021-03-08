package com.gcccolaps.gccpubsubdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

import com.gcccolaps.gccpubsubdemo.consumer.GccpubsubReceiverdemoApplication;
import com.gcccolaps.gccpubsubdemo.dto.DtoAdn;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.integration.annotation.MessagingGateway;

@SpringBootApplication
public class GccpubsubdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GccpubsubdemoApplication.class, args);
		//SpringApplication.run(GccpubsubReceiverdemoApplication.class, args);
	}
		
		@Bean
		@ServiceActivator(inputChannel = "demoOutputChannel")
		public MessageHandler messageSender(PubSubTemplate pubSubTemplate){
			return new PubSubMessageHandler(pubSubTemplate, "topicdemo");
		}
		
		@MessagingGateway(defaultRequestChannel = "demoOutputChannel")
		public interface PubsubOutboundGateway {
			void sendToPubSub(String text);
			
		}
		   //void sendToPubSub(DtoAdn dtoadn);									
}

