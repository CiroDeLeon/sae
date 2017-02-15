/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.corte;

/**
 *
 * @author Usuario1
 */
public class CorteEscolarService {
   private sae.dominio.corte.ICorteEscolarDAO dao;   
    public CorteEscolarService() {
        dao=new sae.infraestructura.jdbc.impl.mysql.CorteEscolarDAO();
    }
    public boolean AsignarVigente(long id){
    CorteEscolar ce=getDao().Buscar(id);
    ce.setActual(true);
       if(getDao().Modificar(id,ce)==1){
           this.getDao().Desmarcar(id);
               return true;           
       }
       return false;
    }
    /**
     * @return the dao
     */
    public sae.dominio.corte.ICorteEscolarDAO getDao() {
        return dao;
    }

}
