package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.*;
public class Bordillo{
    public static void CrearTabla() throws SQLException{
        DBController.DDLConn("CREATE TABLE BORDILLO("
                    +"ID_SEGMENTO NUMBER NOT NULL,"
                    +"ESTADO NUMBER(2) NOT NULL,"
                    +"LONGITUD NUMBER(10,2) NOT NULL,"
                    +"ORDEN NUMBER(2) NOT NULL,"
                    +"INDICE_CONDICION NUMBER(10,2) NOT NULL, "
                    +"CONSTRAINT ID3_SFK FOREIGN KEY (ID_SEGMENTO) REFERENCES segmento(ID) ON DELETE CASCADE,"
                    +"CONSTRAINT be_FK FOREIGN KEY (ESTADO) REFERENCES ESTADO(ID)"
                    +")");
    }
    public static void Insertar(int ID_SEGMENTO, int ESTADO, float LONGITUD, int ORDEN, float INDICE_CONDICION){
        try {
            DBController.Query("INSERT INTO BORDILLO VALUES ("+ID_SEGMENTO+","+ESTADO+","+LONGITUD+","+ORDEN+","+INDICE_CONDICION+")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static JSONArray getBySegmento(int ID) throws SQLException{
        JSONArray JSA = new JSONArray();
        JSONObject JSO = null;
        ResultSet rs = DBController.Query("SELECT ESTADO.ESTADO AS ESTADO, LONGITUD, ORDEN, INDICE_CONDICION FROM BORDILLO "
                +"INNER JOIN ESTADO "
                +"ON BORDILLO.ESTADO = ESTADO.ID "
                +"WHERE BORDILLO.ID_SEGMENTO = "+ID+"");
                while(rs.next()){
                    JSO = new JSONObject();
                    JSO.put("ESTADO",rs.getString("ESTADO"));
                    JSO.put("LONGITUD",rs.getFloat("LONGITUD"));
                    JSO.put("ORDEN",rs.getInt("ORDEN"));
                    JSO.put("INDICE_CONDICION",rs.getFloat("INDICE_CONDICION"));
                    JSA.put(JSO);
                }
        return JSA;
    }
}