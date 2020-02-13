package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.*;
import com.fasterxml.jackson.databind.JsonNode;
import io.vavr.collection.List;

public class Segmento{
    public static void CrearTabla() throws SQLException{
        DBController.DDLConn("CREATE TABLE SEGMENTO( "
                    +"ID NUMBER(4) NOT NULL, " 
                    +"LONGITUD NUMBER(20,4) NOT NULL, "
                    +"TIPO_VIA VARCHAR(20) CHECK (TIPO_VIA IN ('Calle','Avenida','Autopista','Carrera','Diagonal','Circular')) NOT NULL, "
                    +"ESTRATO NUMBER(1) CHECK(ESTRATO IN ( 1,2,3,4,5,6 )) NOT NULL, "
                    +"CONSTRAINT ID_pk PRIMARY KEY (ID) "
                    +")"
                );
    }
    public static void Crear(JsonNode JS){
        int ID = Insertar(
            Float.parseFloat(JS.get("LONGITUD").toString().replace("\"","")),
            JS.get("TIPO_VIA").toString().replace("\"",""),
            Byte.parseByte(JS.get("ESTRATO").toString().replace("\"",""))
        );
        Nomenclatura.Insertar(ID,
            JS.get("NOMENCLATURA").get("VIA_GENERADORA").toString().replace("\"","").toLowerCase(),
            Integer.parseInt(JS.get("NOMENCLATURA").get("N_VIA_GEN").toString()),
            JS.get("NOMENCLATURA").get("CRUCE_DESDE").toString().replace("\"","").toLowerCase(),
            Integer.parseInt(JS.get("NOMENCLATURA").get("N_CRUCE_DESDE").toString()),
            JS.get("NOMENCLATURA").get("CRUCE_HASTA").toString().replace("\"","").toLowerCase(),
            Integer.parseInt(JS.get("NOMENCLATURA").get("N_CRUCE_HASTA").toString())
        );
        for(int i = 0 ; i<JS.withArray("CALZADAS").size();i++){
            Calzada.Insertar(
                ID, 
                Integer.parseInt(JS.withArray("CALZADAS").get(i).get("FUNCIONALIDAD").toString().replace("\"","")), 
                Integer.parseInt(JS.withArray("CALZADAS").get(i).get("SUPERFICIE").toString().replace("\"","")), 
                Float.parseFloat(JS.withArray("CALZADAS").get(i).get("MDR").toString().replace("\"","")),
                Float.parseFloat(JS.withArray("CALZADAS").get(i).get("OPI").toString().replace("\"","")), 
                Float.parseFloat(JS.withArray("CALZADAS").get(i).get("IRI").toString().replace("\"","")) 
                );

        }
        for(int i = 0; i<JS.withArray("BORDILLOS").size(); i++){
            Bordillo.Insertar(ID,
             Integer.parseInt(JS.withArray("BORDILLOS").get(i).get("ESTADO").toString().replace("\"","")),
             Float.parseFloat(JS.withArray("BORDILLOS").get(i).get("LONGITUD").toString().replace("\"","")),
             Integer.parseInt(JS.withArray("BORDILLOS").get(i).get("ORDEN").toString().replace("\"","")),
             Float.parseFloat(JS.withArray("BORDILLOS").get(i).get("INDICE_CONDICION").toString().replace("\"","")));
        }
        DBController.closeConn();
    }
    public static void Eliminar(int ID){
        try {
            DBController.Query("DELETE FROM SEGMENTO WHERE ID = "+ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int Insertar(float LONG, String TIPO_VIA, byte ESTRATO){
        int ID = 0;
        try {
            ResultSet rs = DBController.Query("SELECT COUNT(*) AS CONTEO FROM SEGMENTO");
            rs.next();
            ID = rs.getInt("CONTEO")+1;
            Insertar(ID, LONG, TIPO_VIA, ESTRATO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ID;
    }
    public static void Insertar(int ID, float LONG, String TIPO_VIA, byte ESTRATO){
        try{
            DBController.Query("INSERT INTO SEGMENTO VALUES("+ID+", "+LONG+", '"+TIPO_VIA+"', "+ESTRATO+")");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static JSONArray getJSON(){
        JSONArray Segmentos = new JSONArray();
        JSONObject JSO = null;
        try {
            ResultSet rs = DBController.Query("SELECT * FROM SEGMENTO INNER JOIN NOMENCLATURA ON NOMENCLATURA.ID_SEGMENTO = SEGMENTO.ID");
            while(rs.next()){
                JSO = new JSONObject();
                JSO.put("ID",rs.getInt("ID"));
                JSO.put("LONGITUD",rs.getFloat("LONGITUD"));
                JSO.put("TIPO_VIA",rs.getString("TIPO_VIA"));
                JSO.put("ESTRATO", rs.getInt("ESTRATO"));
                JSO.put("NOMENCLATURA", rs.getString("VIA_GENERADORA") +" "+rs.getInt("N_VIA_GEN")+" entre "+rs.getString("CRUCE_DESDE")+" "+rs.getString("N_CRUCE_DESDE")+" y "+rs.getString("CRUCE_HASTA")+" "+rs.getString("N_CRUCE_HASTA"));
                JSO.put("CALZADAS",Calzada.getBySegmento(rs.getInt("ID")));
                JSO.put("BORDILLOS",Bordillo.getBySegmento(rs.getInt("ID")));
                Segmentos.put(JSO);
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        DBController.closeConn();
        return Segmentos;
    }
    public static JSONObject getById(int ID){
        JSONObject segm = new JSONObject();
        try {
            ResultSet rs = DBController.Query("SELECT ID, LONGITUD, TIPO_VIA, ESTRATO, VIA_GENERADORA, N_VIA_GEN, CRUCE_DESDE, N_CRUCE_DESDE, CRUCE_HASTA, N_CRUCE_HASTA FROM SEGMENTO INNER JOIN NOMENCLATURA ON ID_SEGMENTO = ID WHERE ID = "+ID);
            rs.next();
            segm.put("ID",rs.getInt("ID"));
            segm.put("LONGITUD",rs.getFloat("LONGITUD"));
            segm.put("TIPO_VIA",rs.getString("TIPO_VIA"));
            segm.put("ESTRATO",rs.getInt("ESTRATO"));
            segm.put("VIA_GENERADORA",rs.getString("VIA_GENERADORA"));
            segm.put("N_VIA_GEN",rs.getInt("N_VIA_GEN"));
            segm.put("CRUCE_DESDE",rs.getString("CRUCE_DESDE"));
            segm.put("N_CRUCE_DESDE",rs.getString("N_CRUCE_DESDE"));
            segm.put("CRUCE_HASTA",rs.getString("CRUCE_HASTA"));
            segm.put("N_CRUCE_HASTA",rs.getString("N_CRUCE_HASTA"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return segm;
    }
    public static JSONArray getTipo_via(){
        JSONArray TIPOS = new JSONArray();
        try {
            ResultSet rs = DBController.Query("SELECT TIPO_VIA FROM SEGMENTO GROUP BY TIPO_VIA");
            while(rs.next()){
                TIPOS.put(rs.getString("TIPO_VIA"));                
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
        return TIPOS;
    } 
    public static void updateById(JsonNode segm){
        try {
            //actualización segmento
            DBController.Query("UPDATE SEGMENTO SET "
            +"LONGITUD = "+Float.parseFloat(segm.get("LONGITUD").toString().replace("\"",""))+","
            +" TIPO_VIA = '"+segm.get("TIPO_VIA").toString().replace("\"","")+"',"
            +" ESTRATO = "+ Integer.parseInt(segm.get("ESTRATO").toString().replace("\"",""))
            +" WHERE ID = "+ Integer.parseInt(segm.get("ID").toString().replace("\"","")));
            //actualización nomenclatura
            Nomenclatura.updateById(Integer.parseInt(segm.get("ID").toString().replace("\"","")),
            segm.get("VIA_GENERADORA").toString().replace("\"","").toLowerCase(), 
            Integer.parseInt(segm.get("N_VIA_GEN").toString().replace("\"","")),
            segm.get("CRUCE_DESDE").toString().replace("\"","").toLowerCase(), 
            Integer.parseInt(segm.get("N_CRUCE_DESDE").toString().replace("\"","")),
            segm.get("CRUCE_HASTA").toString().replace("\"","").toLowerCase(), 
            Integer.parseInt(segm.get("N_CRUCE_HASTA").toString().replace("\"",""))
);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}