package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Datos {
    public static void Inicial() {
        BufferedReader br = null;
        System.out.print("Inicial");
        try {
            //carga de las funcionalidades 
            br = new BufferedReader(new FileReader("public/csv/funcionalidad.csv"));
            String line = br.readLine();
            line = br.readLine();
            while (null != line) {
                String[] fields = line.split(";");
                System.out.println(Arrays.toString(fields));
                DBController.IEFS(2,Integer.parseInt(fields[0]),fields[1]);
                line = br.readLine();
            }
            br.close();
            //carga de los estados
            br = new BufferedReader(new FileReader("public/csv/estado.csv"));
            line = br.readLine();
            line = br.readLine();
            while (null != line) {
                String[] fields = line.split(";");
                System.out.println(Arrays.toString(fields));
                DBController.IEFS(1,Integer.parseInt(fields[0]),fields[1]);
                line = br.readLine();
            }
            br.close();
            //carga de las superficies
            br = new BufferedReader(new FileReader("public/csv/superficie.csv"));
            line = br.readLine();
            line = br.readLine();
            while (null != line) {
                String[] fields = line.split(";");
                System.out.println(Arrays.toString(fields));
                DBController.IEFS(3,Integer.parseInt(fields[0]),fields[1]);
                line = br.readLine();
            }
            br.close();
            //carga de los segmentos
            br = new BufferedReader(new FileReader("public/csv/segmentos.csv"));
            line = br.readLine();
            line = br.readLine();
            while (null != line) {
                String[] fields = line.split(";");
                System.out.println(Arrays.toString(fields));
                Segmento.Insertar(Integer.parseInt(fields[0]), Float.parseFloat(fields[1]), fields[2], Byte.parseByte(fields[3]));
                line = br.readLine();
            }
            br.close();
            //carga de los bordillos 
            br = new BufferedReader(new FileReader("public/csv/bordillos.csv"));
            line = br.readLine();
            line = br.readLine();
            while (null != line) {
                String[] fields = line.split(";");
                System.out.println(Arrays.toString(fields));
                Bordillo.Insertar(Integer.parseInt(fields[0]),Integer.parseInt(fields[1]),Float.parseFloat(fields[2]),Integer.parseInt(fields[3]),Float.parseFloat(fields[4]));
                line = br.readLine();
            }
            br.close();
            //carga de las calzadas
            br = new BufferedReader(new FileReader("public/csv/calzadas.csv"));
            line = br.readLine();
            line = br.readLine();
            while (null != line) {
                String[] fields = line.split(";");
                System.out.println(Arrays.toString(fields));
                Calzada.Insertar(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), Float.parseFloat(fields[3]), Float.parseFloat(fields[4]), Float.parseFloat(fields[5]));
                line = br.readLine();
            }
            br.close();
            //carga de las nomenclaturas
            br = new BufferedReader(new FileReader("public/csv/nomenclatura.csv"));
            line = br.readLine();
            line = br.readLine();
            while (null != line) {
                String[] fields = line.split(";");
                System.out.println(Arrays.toString(fields));
                Nomenclatura.Insertar(Integer.parseInt(fields[0]),fields[1],Integer.parseInt(fields[2]),fields[3],Integer.parseInt(fields[4]),fields[5],Integer.parseInt(fields[6]));
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
         }
      }
    }
}