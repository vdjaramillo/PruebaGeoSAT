package models;

import java.sql.*;
import org.json.*;
public class DBController {
    static Connection conn=null;
    public static ResultSet Query(String sql) throws SQLException{
        if(conn==null){
            conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","C##PRUEBA","geosat");  
        }Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }
    public static int DDLConn(String sql) throws SQLException{
        if(conn==null){
            conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","C##PRUEBA","geosat");  
        }
        Statement stmt = conn.createStatement();
        int i = stmt.executeUpdate(sql);
        return i;
    }
    public static void closeConn(){
        try{conn.close();conn=null;}catch(Exception e){System.out.println(e);}
    }
    public static void Verify(){
        try{
            if(!Query("SELECT TABLE_NAME FROM USER_TABLES WHERE TABLE_NAME='SEGMENTO'").next()){
                DDLConn("CREATE TABLE FUNCIONALIDAD("
                    +"ID NUMBER(2) NOT NULL,"
                    +"FUNCIONALIDAD VARCHAR(20) NOT NULL,"
                    +"CONSTRAINT funcionalidad_pk PRIMARY KEY (ID)"
                    +")");
                DDLConn("CREATE TABLE ESTADO("
                    +"ID NUMBER(2) NOT NULL,"
                    +"ESTADO VARCHAR(20) NOT NULL,"
                    +"CONSTRAINT estado_pk PRIMARY KEY (ID)"
                    +")");
                DDLConn("CREATE TABLE SUPERFICIE("
                    +"ID NUMBER(2) NOT NULL,"
                    +"SUPERFICIE VARCHAR(20) NOT NULL,"
                    +"CONSTRAINT superficie_pk PRIMARY KEY (ID)"
                    +")");
                Segmento.CrearTabla();                
                Nomenclatura.CrearTabla();
                Calzada.CrearTabla();
                Bordillo.CrearTabla();
                Datos.Inicial();
                closeConn();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public static void IEFS(int EFS, int ID, String CARACTERISTICA){
        try {
            switch (EFS) {
                case 1:
                    Query("INSERT INTO ESTADO VALUES ("+ID+",'"+CARACTERISTICA+"')");
                    break;
                case 2: 
                    Query("INSERT INTO FUNCIONALIDAD VALUES ("+ID+",'"+CARACTERISTICA+"')");
                    break;
                case 3: 
                    Query("INSERT INTO SUPERFICIE VALUES ("+ID+",'"+CARACTERISTICA+"')");
                    break; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static JSONArray getEstados() {
        JSONObject Estado = null;
        JSONArray Estados = new JSONArray();
        try {
            ResultSet rs = Query("SELECT * FROM ESTADO");
            while(rs.next()){
                Estado = new JSONObject();
                Estado.put("ID",rs.getInt("ID"));
                Estado.put("ESTADO", rs.getString("ESTADO"));
                Estados.put(Estado);
            }
            rs.close();
            closeConn();
              
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Estados;
    }
    public static JSONArray getFuncionalidades() {
        JSONObject Funcion = null;
        JSONArray Funcions = new JSONArray();
        try {
            ResultSet rs = Query("SELECT * FROM FUNCIONALIDAD");
            while(rs.next()){
                Funcion = new JSONObject();
                Funcion.put("ID", rs.getInt("ID"));
                Funcion.put("FUNCIONALIDAD", rs.getString("FUNCIONALIDAD"));
                Funcions.put(Funcion);
            }
            rs.close();
            closeConn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Funcions;

    }
    public static JSONArray getSuperficies() {
        
        JSONObject Super = null;
        JSONArray Supers = new JSONArray();
        try {
            ResultSet rs = Query("SELECT * FROM SUPERFICIE");
            while(rs.next()){
                Super = new JSONObject();
                Super.put("ID",rs.getInt("ID"));
                Super.put("SUPERFICIE",rs.getString("SUPERFICIE"));
                Supers.put(Super);
            }
            rs.close();
            closeConn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Supers;
    }
    
}