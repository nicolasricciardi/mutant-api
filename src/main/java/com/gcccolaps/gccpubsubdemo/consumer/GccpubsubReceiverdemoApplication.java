package com.gcccolaps.gccpubsubdemo.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcccolaps.gccpubsubdemo.dao.AdnsDAO;
import com.gcccolaps.gccpubsubdemo.dto.DtoAdn;
import com.gcccolaps.gccpubsubdemo.dto.DtoError;
import com.gcccolaps.gccpubsubdemo.entity.Adn;
import com.gcccolaps.gccpubsubdemo.entity.CadenaDna;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;



//@SpringBootApplication
@Service
public class GccpubsubReceiverdemoApplication {
	
	@Autowired
	private AdnsDAO adnDAO;
	
	public static void main(String[] args) {
		SpringApplication.run(GccpubsubReceiverdemoApplication.class, args);
						
	}
	
	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(
			@Qualifier("demoInputChannel") MessageChannel inputChannel,
			PubSubTemplate pubSubTemplate){

		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate
		, "topicdemosuscripcion");
		adapter.setOutputChannel(inputChannel);
		return adapter;

	}
	
	@Bean
	public MessageChannel demoInputChannel(){
		return new DirectChannel();
	}
	
	@ServiceActivator(inputChannel = "demoInputChannel")
	public ResponseEntity<Adn> messageReceiver(String message){	
		DtoAdn dtoadn = new DtoAdn();;
    	String jsonData = message;
    	ObjectMapper mapper = new ObjectMapper();
    	try{
    		dtoadn= mapper.readValue(jsonData, DtoAdn.class);
    	}
    	catch (Exception e) {
    	    e.printStackTrace();
    	}
    	
    	String[] cadena = dtoadn.getDna();     	
    	CadenaDna cadenaDna = new CadenaDna();
    	cadenaDna.setDna(cadena);
    	
    	boolean matrizcorrecta = false;
		matrizcorrecta= cadenaDna.matrizcorrecta();											
		
		DtoError nuevoError = new DtoError();
		Adn newAdn = new Adn();
		
		if (!matrizcorrecta) {    			
			nuevoError.setCodigo(1);
			nuevoError.setDescripcion("Cadena enviada INCORRECTA, debe ser de 6x6");    			    					
		}
		else {																			
			//Utilizo metodos de adn
			Adn Adnguardo = new Adn();
			boolean esmutante = false;  
			char matriz[][] = new char[6][6];
			matriz= cadenaDna.inicializarmatriz();
			esmutante = cadenaDna.esmutante();						
			if (esmutante) {    				
				Adnguardo.setTipo("M");
				nuevoError.setCodigo(200);
    			nuevoError.setDescripcion("200-OK");
			}else {	    			    		
				Adnguardo.setTipo("H");
    			nuevoError.setCodigo(403);
    			nuevoError.setDescripcion("403-Forbidden");
			}    			
			newAdn = adnDAO.save(Adnguardo);    																							
		}  
		newAdn.setTipo(nuevoError.getDescripcion());
		return ResponseEntity.ok(newAdn); 
		
		//System.out.println("Message arrived ---> " + message);
	}
	

	
	
}
