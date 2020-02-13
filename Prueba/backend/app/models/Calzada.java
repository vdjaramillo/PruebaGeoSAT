package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.*;
public class Calzada{
    public static void CrearTabla() throws SQLException{
        DBController.DDLConn("CREATE TABLE CALZADA("
                    +"ID_SEGMENTO NUMBER NOT NULL,"
                    +"FUNCIONALIDAD NUMBER(2) NOT NULL,"
                    +"SUPERFICIE NUMBER(2) NOT NULL,"
                    +"MDR NUMBER(10,2) NOT NULL,"
                    +"OPI NUMBER(10,2) NOT NULL,"
                    +"IRI NUMBER(10,2) NOT NULL,"
                    +"CONSTRAINT ID2_SFK FOREIGN KEY (ID_SEGMENTO) REFERENCES segmento(ID) ON DELETE CASCADE,"
                    +"CONSTRAINT cf_FK FOREIGN KEY (FUNCIONALIDAD) REFERENCES FUNCIONALIDAD(ID),"
                    +"CONSTRAINT cs_FK FOREIGN KEY (SUPERFICIE) REFERENCES SUPERFICIE(ID)"
                    +")"
                );
    }
    public static void Insertar(int ID_SEGMENTO, int FUNCIONALIDAD, int SUPERFICIE, float MDR, float OPI, float IRI){
        try{
            DBController.Query("INSERT INTO CALZADA VALUES("+ID_SEGMENTO+","+FUNCIONALIDAD+","+SUPERFICIE+","+MDR+","+OPI+","+IRI+")");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static JSONArray getBySegmento(int ID) throws SQLException{
        JSONArray JSA = new JSONArray();
        JSONObject JSO = null;
        ResultSet rs = DBController.Query("SELECT FUNCIONALIDAD.FUNCIONALIDAD AS FUNCIONALIDAD, SUPERFICIE.SUPERFICIE AS SUPERFICIE, MDR, OPI, IRI FROM CALZADA " 
                +"INNER JOIN FUNCIONALIDAD "
                +"ON CALZADA.FUNCIONALIDAD = FUNCIONALIDAD.ID "
                +"INNER JOIN SUPERFICIE "
                +"ON CALZADA.SUPERFICIE = SUPERFICIE.ID "
                +"WHERE ID_SEGMENTO = "+ID+"");
        while(rs.next()){
            JSO = new JSONObject();
            JSO.put("FUNCIONALIDAD",rs.getString("FUNCIONALIDAD"));
            JSO.put("SUPERFICIE",rs.getString("SUPERFICIE"));
            JSO.put("MDR",rs.getFloat("MDR"));
            JSO.put("OPI",rs.getFloat("OPI"));
            JSO.put("IRI",rs.getFloat("IRI"));
            JSA.put(JSO);
        }
        return JSA;
    }
}