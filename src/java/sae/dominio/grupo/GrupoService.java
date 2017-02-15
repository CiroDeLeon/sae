/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.grupo;

/**
 *
 * @author Usuario1
 */
public class GrupoService {
    private sae.dominio.grupo.IGrupoDAO dao;
    

    public GrupoService() {
        dao=new sae.infraestructura.jdbc.impl.mysql.GrupoDAO();
    }

    /**
     * @return the dao
     */
    public sae.dominio.grupo.IGrupoDAO getDao() {
        return dao;
    }
}
