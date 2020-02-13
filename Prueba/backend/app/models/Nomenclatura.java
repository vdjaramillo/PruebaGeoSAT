package models;

import java.sql.SQLException;

public class Nomenclatura{
    public static void CrearTabla() throws SQLException{
        DBController.DDLConn("CREATE TABLE NOMENCLATURA("
                    +"ID_SEGMENTO NUMBER NOT NULL,"
                    +"VIA_GENERADORA VARCHAR(20) CHECK (VIA_GENERADORA IN ('calle','avenida','autopista','carrera','diagonal', 'circular')) NOT NULL,"
                    +"N_VIA_GEN NUMBER(10) NOT NULL,"
                    +"CRUCE_DESDE VARCHAR(20) CHECK (CRUCE_DESDE IN ('calle','avenida','autopista','carrera','diagonal', 'circular')) NOT NULL,"
                    +"N_CRUCE_DESDE NUMBER(10) NOT NULL,"
                    +"CRUCE_HASTA VARCHAR(20) CHECK (CRUCE_HASTA IN ('calle','avenida','autopista','carrera','diagonal', 'circular')) NOT NULL,"
                    +"N_CRUCE_HASTA NUMBER(10) NOT NULL,"
                    +"CONSTRAINT ID_NFK PRIMARY KEY (ID_SEGMENTO), "
                    +"CONSTRAINT ID_SFK FOREIGN KEY (ID_SEGMENTO) REFERENCES segmento(ID) ON DELETE CASCADE"
                    +")"
                );
    }
    public static void Insertar(int ID_SEGMENTO, String VIA_GENERADORA, int N_VIA_GEN, String CRUCE_DESDE, int N_CRUCE_DESDE, String CRUCE_HASTA, int N_CRUCE_HASTA){
        try {
            DBController.Query("INSERT INTO NOMENCLATURA VALUES("+ID_SEGMENTO+",'"+VIA_GENERADORA+"',"+N_VIA_GEN+",'"+CRUCE_DESDE+"','"+N_CRUCE_DESDE+"','"+CRUCE_HASTA+"','"+N_CRUCE_HASTA+"')");
        } catch (Exception e) {
            e.printStackTrace();        }
    }
    public static void updateById(int ID_SEGMENTO, String VIA_GENERADORA, int N_VIA_GEN, String CRUCE_DESDE, int N_CRUCE_DESDE, String CRUCE_HASTA, int N_CRUCE_HASTA){
        try {
            DBController.Query("UPDATE NOMENCLATURA SET "
            +"VIA_GENERADORA = '"+VIA_GENERADORA+"', "
            +"N_VIA_GEN ="+N_VIA_GEN+", "
            +"CRUCE_DESDE = '"+CRUCE_DESDE+"', "
            +"N_CRUCE_DESDE = "+N_CRUCE_DESDE+", "
            +"CRUCE_HASTA = '"+CRUCE_HASTA+"', "
            +"N_CRUCE_HASTA = "+N_CRUCE_HASTA+" "
            +"WHERE ID_SEGMENTO = "+ID_SEGMENTO);
        } catch (Exception e) {
            e.printStackTrace();
        }     
    }
}