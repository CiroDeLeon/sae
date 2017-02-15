/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.asignatura;

/**
 *
 * @author Usuario1
 */
public class AsignaturaService {
    private sae.dominio.asignatura.IAsignaturaDAO dao;
    public AsignaturaService() {
        dao=new sae.infraestructura.jdbc.impl.mysql.AsignaturaDAO();
    }

    /**
     * @return the dao
     */
    public sae.dominio.asignatura.IAsignaturaDAO getDao() {
        return dao;
    }

}
