import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {

    final static Logger log = Logger.getLogger(ReadFile.class);

    public ReadFile(){

    }


    /**
     * Lee el archivo y loggea las lineas que concuerdan con la búqueda
     * @param nombrearchivo
     * @return
     */
    public int leerArchivo(String rutaArchivo) throws FileNotFoundException, IOException {

        BufferedReader br = null;
        String fila = "";
        String wordSearch = "Multi-Family";

        try{
            double start = System.currentTimeMillis();
            br = new BufferedReader(new FileReader(rutaArchivo));
            int cont = 1;

            while((fila = br.readLine()) != null){
                if(fila.contains(wordSearch)){
                    log.info(cont + ".- "+fila);
                    cont++;
                }
            }
            br.close();
            log.info("Registros encontrados: " + (cont - 1));
            log.info("Tiempo de ejecución: " + (System.currentTimeMillis() - start) / 1000 + " seg");
        }catch (FileNotFoundException e){

            log.error(e.getMessage());
            throw new FileNotFoundException(e.getMessage());

        } catch (IOException e){

            log.error(e.getMessage());
            throw new IOException(e.getMessage());

        }
        return 0;
    }
}
