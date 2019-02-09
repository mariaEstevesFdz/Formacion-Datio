import org.apache.log4j.Logger;
import java.util.Scanner;

public class Principal {

    final static Logger log = Logger.getLogger(Principal.class);

    public static void main(String [] args){

        ReadFile  read = new ReadFile();
        Scanner sc = new Scanner(System.in);

        boolean exitoso = false;
        // /home/peabaonza/Software/practicas_tribu/primera_practica/Sacramentorealestatetransactions1.csv

        while(exitoso != true) {
            try {
                System.out.println("Ingresar URL");
                String archivoURL = sc.nextLine();

                if(archivoURL.equals("exit")){
                    exitoso = true;
                    break;
                }
                if(read.leerArchivo(archivoURL) == 0){
                    exitoso = true;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                exitoso = false;
            }
        }
    }
}