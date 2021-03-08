#API: mutants-rest

La Api Rest recibe una array de String del cual debe detectar si es ADN Mutante o no.
El array de string forma una matriz donde la combinacion de 2 secuencias de cuatro letras iguales, horizontal, vertical o en diagonal representan ADN perteneciente a un Mutante.

##Servicios

Descripcion detallada de los servicios: 
https://mutants-rest.rj.r.appspot.com/swagger-ui.html

Framework utilizado para realizar la API: 
##Spring Tool Suite 4

Base de datos utilizada: 
##MySQL

##Algoritmo 

Para la busqueda de ADN mutante se definio que la matriz donde se va a buscar el ADN es de 6x6.
EL algoritmo es una busqueda que va eliminando las posibles combinaciones para formar los resultados positivos de 4 letras iguales.

En la busqueda por filas y por las columnas recorre las mismas y si el par de letras centrales no son iguales, ya descarta la fila o la columna. 
Con la diagonales lo mismo, con la diferencias que no recorre las diagonales de menos de 4 posiciones.

Minetras va realizando las distintas busquedas va sumando los casos positivos, en el caso de llegar a dos finaliza la busqueda y retorna que es Mutante.

Si retorna que No es Mutante, es porque realizo el total de las busquedas posibles, que son 7 busquedas.
Las busquedas son:
Por filas.
Por columnas.
Por diagonal central.
Dos por diagonales de 5 posiciones.
Dos por diagonales de 4 posiciones.

##Fluctuaciones agresivas de trafico

Para resolver las fluctuaciones aresivas de trafico, entre 100 y 1 millon por minuto, se resolvio utilizar un intermediario para poder gestionar el trafico masivo de transacciones.

La idea es dejar en manos del intermediario la capacidad de respuesta de la aplicacion. 
Se utilizo el servicio Pub/Sub de mensajeria que brinda google cloud. Es un servicio de mensajería asíncrona que separa los servicios que producen eventos de servicios que procesan eventos. Ofrece almacenamiento de mensajes duradero y entrega de mensajes en tiempo real con alta disponibilidad y rendimiento coherente a gran escala. Se creo el topico necesario para manejar los mensajes y poder hacer frente a las fluctuaciones de trafico.

Se definen Topicos en donde las peticiones se publican y luego son atendidas por un Consumidor.


##Cloud
La API esta hosteada en Google cloud

https://mutants-rest.rj.r.appspot.com/swagger-ui.html

En google cloud se debn configurar la base de datos My SQL y el servicio de mensajeria con le nombre de Topico a utilizar

Ejecucion de servicios

##Envio de adn

POST https://mutants-rest.rj.r.appspot.com/mutant

MUTANTE CON 2 FILAS

{
  "dna":["TCAGCT","ACTGAT","CGAAAA","ACTTAA","ACCCCT","TGGATA"]	   
}


 MUTANTE CON 2 POSITIVOS, uno en diagonal de 4 posiciones, y la otra de DIAGONAL CENTRAL
{
  "dna":["TCAGCT","ACTAAT","CGTGAA","ACGTAA","AGCCAT","TGGATA"]	   
}

 NO-MUTANTE
{
  "dna":["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]	   
}


##Calculo de cantidad de mutantes
https://mutants-rest.rj.r.appspot.com/stats

##Servicio para probar la fluctuacion de envios
POST https://mutants-rest.rj.r.appspot.com/mutants/100000

El numero pasado es la cantidad de cargas de adn a realizar.
En es caso se ejecutan 100 mil envios 

Para enviar 10 envios:
POST https://mutants-rest.rj.r.appspot.com/mutants/10

Para enviar 1 millon de  envios:
POST https://mutants-rest.rj.r.appspot.com/mutants/1000000


