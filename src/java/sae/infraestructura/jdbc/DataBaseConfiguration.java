/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.infraestructura.jdbc;

/**
 *
 * @author FANNY BURGOS
 */
public class DataBaseConfiguration {
    private static String driverclass="com.mysql.jdbc.Driver";
    private static String user="root";
    private static String password="arrozdoncabe";
    private static String url="jdbc:mysql://192.168.0.101/plataformacontable_corgranos2013";    
    static DataBaseConfiguration instancia=null;
    private DataBaseConfiguration(){        
        super();
    }
    public static DataBaseConfiguration ObtenerInstancia(){
        instancia=new DataBaseConfiguration();        
        return instancia;
    }
    /**
     * @return the driverclass
     */
    public String getDriverclass() {
        return DataBaseConfiguration.driverclass;
    }

    /**
     * @param driverclass the driverclass to set
     */
    public void setDriverclass(String driverclass) {
        DataBaseConfiguration.driverclass = driverclass;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return DataBaseConfiguration.user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        DataBaseConfiguration.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return DataBaseConfiguration.password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        DataBaseConfiguration.password = password;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return DataBaseConfiguration.url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        DataBaseConfiguration.url = url;
    }
    
}
