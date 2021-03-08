package com.gcccolaps.gccpubsubdemo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcccolaps.gccpubsubdemo.GccpubsubdemoApplication;
import com.gcccolaps.gccpubsubdemo.GccpubsubdemoApplication.PubsubOutboundGateway;
import com.gcccolaps.gccpubsubdemo.dao.AdnsDAO;
import com.gcccolaps.gccpubsubdemo.dto.DtoAdn;
import com.gcccolaps.gccpubsubdemo.dto.DtoStats;
import com.gcccolaps.gccpubsubdemo.entity.*;

@RestController
@RequestMapping("/")
public class PubSubMessageController {

	@Autowired 
	private GccpubsubdemoApplication.PubsubOutboundGateway messagingGateway;		
	
	@PostMapping("/mutant")
    public RedirectView mutantMessage(@RequestBody DtoAdn dtoadn){
				
    	ObjectMapper mapper = new ObjectMapper();
    	try{    		    		
    		String strdtoadn = mapper.writeValueAsString(dtoadn);   
    		messagingGateway.sendToPubSub(strdtoadn);
    	}
    	catch (Exception e) {
    	    e.printStackTrace();
    	}
				
        //messagingGateway.sendToPubSub(message);
        //return new ResponseEntity<>(HttpStatus.OK);
        return new RedirectView("/");
	}
	
	 @PostMapping("/mutants/{carga}")
	 public void postadns(@RequestBody DtoAdn dtoadn,@PathVariable("carga") final int carga) {   	    	    
	  		
		    ObjectMapper mapper = new ObjectMapper();
	    	try{    		    		
	    		String strdtoadn = mapper.writeValueAsString(dtoadn);   	    			    		
	    		int numerodecargas = carga;		 
	   		 	int contadordecargas = 0;
	   		 	while (true) {	   		 		
	   		 	    messagingGateway.sendToPubSub(strdtoadn);
	   		 		if (contadordecargas > numerodecargas) { break;}
	   		 		contadordecargas = contadordecargas + 1;
	   		 	}		 		    		
	    	}
	    	catch (Exception e) {
	    	    e.printStackTrace();
	    	}		 		 		 	 		   			 
	 }	 	 	
		
	@Autowired
	private AdnsDAO adnDAO;
	
	@GetMapping("/all") 
	public ResponseEntity<List<Adn>> getAdn(){
		
		List<Adn> adns = adnDAO.findAll();				
		return ResponseEntity.ok(adns);		
		
	}	
	
	@RequestMapping(value="stats",method=RequestMethod.GET)
   	public ResponseEntity<DtoStats> stats(){
    	
    	DtoStats dtoStats = new DtoStats();    	
    	
    	long cantMutantes = 0;
    	long cantHumanos = 0;
    	//long cantTotal = 0;
    	double ratio = 0;    	    	   
    	    	           	
    	cantMutantes = adnDAO.cantidadDeMutantes();
    	cantHumanos = adnDAO.cantidadDeHumanos();
    	//cantTotal = adnDAO.count();	
    	
    	double cantMutantesCal = cantMutantes;
    	double cantHumanosCal = cantHumanos;    	
    	ratio= cantMutantesCal/cantHumanosCal;    	        	
    	
    	dtoStats.setCount_mutant_dna(cantMutantes);
    	dtoStats.setCount_human_dna(cantHumanos);
    	dtoStats.setRatio(ratio);
    	//dtoStats.setTotal(cantTotal);
    	
    	return ResponseEntity.ok(dtoStats);
    	   			
   	}		
				
	@RequestMapping("/")
    public ResponseEntity<?> home() {
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
}
