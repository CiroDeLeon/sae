/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.curso.nota;

/**
 *
 * @author Usuario1
 */
public class NotaService {
    private sae.dominio.curso.nota.INotaDAO dao;
    public NotaService() {
        dao=new sae.infraestructura.jdbc.impl.mysql.NotaDAO();
    }

    /**
     * @return the dao
     */
    public sae.dominio.curso.nota.INotaDAO getDao() {
        return dao;
    }

}
