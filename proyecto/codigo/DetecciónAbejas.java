import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import java.util.ArrayList;
import java.io.PrintWriter;
/**
 * Clase para leer archivos con coordenadas de abejas y detectar cuáles de ellas están en riego de colisionar con cuáles 
 *
 * @author Juliana Henao & Gerónimo Zuluaga
 * @version Noviembre 2018
 */
public class DeteccionAbejas
{
    /**
    * Metodo para aproximar la distancia entre dos abejas roboticas
    *
    * @param  abeja1  la primera abeja
    * @param  abeja2  la segunda abeja
    * @return la distancia aproximada entre las dos abejas, aproximando 1 grado como 111111 metros
    */
    public static double distancia(Abeja abeja1, Abeja abeja2){
        return Math.sqrt(Math.pow((abeja1.x - abeja2.x)*111111,2) +
            Math.pow((abeja1.y - abeja2.y)*111111,2) +
            Math.pow(abeja1.z - abeja2.z,2)
        );
    }

    /**
    * Metodo para leer un archivo de abejas y almacenarlas en un arreglo de tipo Abeja
    *
    * @param  numeroDeAbejas  El numero de abejas a leer
    * @return un arreglo de Abeja donde cada elemento representa las coordenadas de una abeja
    */
    public static Abeja[] leerArchivo(int numeroDeAbejas){
        final String nombreDelArchivo = "ConjuntoDeDatosCon"+numeroDeAbejas+"abejas.txt";
        Abeja[] arregloDeAbejas = new Abeja[numeroDeAbejas];
        try {            
            BufferedReader br = new BufferedReader(new FileReader(nombreDelArchivo));
            String lineaActual = br.readLine();
            lineaActual = br.readLine(); // Descarta la primera linea
            int index = 0;
            while (lineaActual != null){ // Mientras no llegue al fin del archivo
                String [] cadenaParticionada = lineaActual.split(",");
                Abeja abeja = new Abeja(Double.parseDouble(cadenaParticionada[0]),
                        Double.parseDouble(cadenaParticionada[1]),
                        Double.parseDouble(cadenaParticionada[2]));
                arregloDeAbejas[index++] = abeja;
                lineaActual = br.readLine();
            }
        }
        catch(IOException ioe) {
            System.out.println("Error leyendo el archivo de entrada");
        }
        return arregloDeAbejas;
    }

    /**
    * Metodo para crear una pila con un el arreglo de Abejas
    *
    * @param  arregloDeAbejas  Un conjunto de abejas a almacenar
    * @return un Stack de Abeja donde cada elemento representa las coordenadas de una abeja
    */
    public static Stack<Abeja> hacerPila(Abeja[] arregloDeAbejas){
        Stack <Abeja> abejas = new Stack();
        for(int i = 0; i < arregloDeAbejas.length; i++){
            abejas.push(arregloDeAbejas[i]);
        }
        return abejas;
    }

    /**
    * Metodo para detectar cuáles abejas estan en peligro de colisión
    *
    * @param  arregloDeAbejas  Un conjunto de abejas a evaluar
    * @param  abejas una pila con abejas a evaluar
    */
    public static ArrayList<Abeja> collisionDetector(Abeja[] arregloDeAbejas, Stack <Abeja> abejas){
        ArrayList<Abeja> abejasConRiesgoDeColision = new ArrayList();
        while(abejas.size() != 0){
            Abeja actual = abejas.pop();
            for(int i = 0; i < arregloDeAbejas.length; i++){
                if(distancia(arregloDeAbejas[i], actual) >= 100){
                    abejasConRiesgoDeColision.add(actual);    
                    break;
                    //System.out.println(arregloDeAbejas[i].x + ", " + arregloDeAbejas[i].y + ", " + arregloDeAbejas[i].z + " colisiona con: " + actual.x +", " + actual.y +", " +actual.z);
                }
            }
        }
        return abejasConRiesgoDeColision;
    }
    
    /**
    * Metodo para escribir un archivo con la respuesta
    *
    * @param  abejasConRiesgoDeColision  Lista definida con arreglos con las abejas con riesgo de colision
    * @param  numeroDeAbejas  Numero de abejas del conjunto de datos original
    */
    public static void guardarArchivo(ArrayList<Abeja> abejasConRiesgoDeColision, int numeroDeAbejas){
          final String nombreDelArchivo = "respuestaConjuntoDeDatosCon"+numeroDeAbejas+"abejas.txt";  
          try {
             PrintWriter escritor = new PrintWriter(nombreDelArchivo, "UTF-8");
             for (Abeja abeja : abejasConRiesgoDeColision)
                escritor.println(abeja.x+","+abeja.y+","+abeja.z);
             escritor.close();
          }
          catch(IOException ioe) {
              System.out.println("Error escribiendo el archivo de salida");
          }  
    }
  
  public static void main(String [] args){
    	int numeroDeAbejas = 1000;
        Abeja[] arregloDeAbejas = leerArchivo(numeroDeAbejas);
        Stack <Abeja> abejas = hacerPila(arregloDeAbejas);
        long startTime = System.currentTimeMillis();
        ArrayList <Abeja>  abejasConRiesgoDeColision= collisionDetector(arregloDeAbejas,abejas);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("El algoritmo tomo un tiempo de: "+estimatedTime+" ms");
        guardarArchivo(abejasConRiesgoDeColision, numeroDeAbejas);
        }
          
    }

