/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.infraestructura.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author FANNY BURGOS
 */
public class Pool {
    private static DataSource datasource=null;   
    public  static Connection ObtenerConexion(){        
        Connection con= null;
        if(Pool.datasource==null)
            Pool.initPool();        
        try {
            con=datasource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Pool.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return con;
    }
    public  static void LiberarConexion(Connection conexion) {        
        try {
            if (null != conexion) {
                // En realidad no cierra, solo libera la conexion.
                conexion.close();
                //System.out.println("conexion liberada");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }    
    public  static void setDatasource(DataSource aDatasource) {
        Pool.datasource = aDatasource;
    }        
    public  static void initPool() {
        DBmanager config=new DBmanager();
        config.InicializarPropiedades();
        DataSourceFactory dsf=new DataSourceFactory();
        Pool.setDatasource(dsf.ObtenerDataSource("BasicDataSource"));
        
    }        
}
