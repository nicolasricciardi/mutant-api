package com.gcccolaps.gccpubsubdemo.entity;

public class CadenaDna {
	
	private String[] dna;
	char matriz[][] = new char[6][6];
	public String[] getDna() {
		return dna;
	}
	public void setDna(String[] dna) {
		this.dna = dna;
	}
	public char[][] getMatriz() {
		return matriz;
	}
	public void setMatriz(char[][] matriz) {
		this.matriz = matriz;
	}
	
	public boolean matrizcorrecta()
	{
	        boolean matrizOK = false;	        					
			//1 - CONTROL del largo total de la cadena = DEBE CONTENER 6 ESLABONES 	        
			if(dna.length == 6)
	        {
				matrizOK = true;
				//2 - CONTROL Eslabon = DEBEN CONTENER 6 LETRAS c/u 
				char [] vectorEslabon;
				for (int i = 0; i < dna.length; i++){				    																
					vectorEslabon = dna[i].toCharArray();
					if(vectorEslabon.length != 6)					
			        {
						matrizOK = false;											
						break;
			        }																																																
				}				
	        }									
	        return matrizOK;
	}
	
	public char[][] inicializarmatriz()
	{	        						        
		    String eslabon = "";				    		
			char [] vectorEslabon;
			for (int i = 0; i < dna.length; i++){				    																
				vectorEslabon = dna[i].toCharArray();											   																			
				eslabon = dna[i];										
				vectorEslabon = eslabon.toCharArray(); 				
							
				for (int contEsl = 0; contEsl < dna[i].length(); contEsl++){
					matriz[i][contEsl] = vectorEslabon[contEsl];																				
				}								   	
			}					        							
	        return matriz;
	}
	
	public boolean esmutante()
	{
	        boolean esmutante = false;
	        int cantitadpositivostotal= 0;	        	        
			int numerodebusqueda = 1;
				       
			while (true) {
	        	cantitadpositivostotal=cantitadpositivostotal + buscar(numerodebusqueda);	        	
	        	if (cantitadpositivostotal > 1) {	
		        	esmutante = true;
		        }	        
	        	if (esmutante) { break;}
	        	if (numerodebusqueda > 7) { break;}
	        	numerodebusqueda = numerodebusqueda + 1;
	        }															
	        return esmutante;
    }
	
	public int buscar(int numerodebusqueda) {		
			int secuenciaspositiva = 0;                        
	        switch (numerodebusqueda) 
	        {     		
	            case 1:  secuenciaspositiva = buscoenfila(); //BUSCO EN FILAS                                        
	                     break;
	            case 2:  secuenciaspositiva = buscoencolumna(); //BUSCO EN COLUMNAS                    
	                     break;
	            case 3:  secuenciaspositiva = buscoendiagonalde4arriba(); //BUSCO EN DIAGONAL DE 4 POS DE ARRIBA                    
	                     break;
	            case 4:  secuenciaspositiva = buscoendiagonalde4abajo(); //BUSCO EN DIAGONAL DE 4 POS DE ABAJO                    
	                     break;
	            case 5:  secuenciaspositiva = buscoendiagonalde5arriba(); //BUSCO EN DIAGONAL DE 5 POS DE ARRIBA                     
	                     break;
	            case 6:  secuenciaspositiva = buscoendiagonalde5abajo(); //BUSCO EN DIAGONAL DE 5 POS DE ABAJO                     
	                     break;
	            case 7:  secuenciaspositiva = buscoendiagonalcentral(); //BUSCO EN DIAGONAL CENTRAL                     
	                     break;           
	        }
	        return secuenciaspositiva; 						
	}
	    
	    private int buscoenfila()
		{			
			int positivoenfila = 0;		    
		    int maxlargo = 6;
		    for (int cont = 0; cont < maxlargo; cont++){					        		        		        	
				if (matriz[cont][2]==matriz[cont][3]) {
					if ((matriz[cont][2]==matriz[cont][1] && matriz[cont][2]==matriz[cont][0]) || (matriz[cont][2]== matriz[cont][1] && matriz[cont][2]==matriz[cont][4]) || (matriz[cont][2]== matriz[cont][4] && matriz[cont][2]==matriz[cont][5])){		   	
						positivoenfila = positivoenfila + 1;
					}
				}			
			}	        						
		    return positivoenfila;
		}	           

	    private int buscoencolumna()
	   	{   		  	
	   		 int positivoencolumna = 0;		    
	   	     int maxlargo = 6;
	   	     for (int cont = 0; cont < maxlargo; cont++){					        	   	       		        	
	   			if (matriz[2][cont]==matriz[3][cont]) {
	   				if ((matriz[2][cont]==matriz[1][cont] && matriz[2][cont]==matriz[0][cont]) || (matriz[2][cont]== matriz[1][cont] && matriz[2][cont]==matriz[4][cont]) || (matriz[2][cont]== matriz[4][cont] && matriz[2][cont]==matriz[5][cont])){		   	
	   					positivoencolumna = positivoencolumna + 1;
	   			    }												
	   			}				 
	   		}	       							
	   	    return positivoencolumna;
	   	}
	    
	    private int buscoendiagonalde4arriba(){
			
			int positivoendiagonalde4arriba = 0;		
			if (matriz[0][2]==matriz[1][3] && matriz[0][2]==matriz[2][4] && matriz[0][2]== matriz[3][5]){		   	
				positivoendiagonalde4arriba = positivoendiagonalde4arriba + 1;
			}		
		    return positivoendiagonalde4arriba; 
		}
	    
	    private int buscoendiagonalde4abajo(){
			
			int positivoendiagonalde4abajo = 0;		
			if (matriz[2][0]==matriz[3][1] && matriz[2][0]==matriz[4][2] && matriz[2][0]== matriz[5][3]){		   	
				positivoendiagonalde4abajo = positivoendiagonalde4abajo + 1;
			}	
		    return positivoendiagonalde4abajo; 
		}

	    private int buscoendiagonalde5arriba(){		
			int positivoendiagonalde5arriba = 0;		
			if (matriz[2][1]==matriz[3][2]){		   				
		    	if ((matriz[2][1]==matriz[1][0] && matriz[2][1]==matriz[4][3]) || (matriz[2][1]== matriz[4][3] && matriz[2][1]==matriz[5][4])){		   	
					positivoendiagonalde5arriba = positivoendiagonalde5arriba + 1;
			    }	
		    }		    												
			return positivoendiagonalde5arriba; 
	    }
	    
	    private int buscoendiagonalde5abajo(){	
	    	int positivoendiagonalde5abajo = 0;		
	    	if (matriz[2][3]==matriz[3][4]){		   	    		    	
	    		if ((matriz[2][3]==matriz[1][2] && matriz[2][3]==matriz[0][1]) || (matriz[2][3]== matriz[4][3] && matriz[1][2]==matriz[4][5])){		   	
	    			positivoendiagonalde5abajo = positivoendiagonalde5abajo + 1;
	    		}
	        }		    												
		    return positivoendiagonalde5abajo; 
	    }
	    
	    private int buscoendiagonalcentral(){		
			int positivoendiagonalcentral = 0;		
			if (matriz[2][2]==matriz[3][3]){		   				
		    	if ((matriz[2][2]==matriz[1][1] && matriz[2][2]==matriz[0][0]) || (matriz[2][2]== matriz[1][1] && matriz[2][2]==matriz[4][4]) || (matriz[2][2]== matriz[4][4] && matriz[2][2]==matriz[5][5])){		   	
					positivoendiagonalcentral = positivoendiagonalcentral + 1;
			    }		
		    }		    												
			return positivoendiagonalcentral; 
	    }    

}
