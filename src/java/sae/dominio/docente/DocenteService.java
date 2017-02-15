/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.docente;

import sae.dominio.estudiante.*;
import sae.dominio.actor.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import sae.dominio.exceptions.NonExistenceEntityException;
import sae.dominio.exceptions.RepeatEntityException;

/**
 *
 * @author Usuario1
 */
public class DocenteService {
    private sae.dominio.docente.IDocenteDAO dao;    
    public DocenteService() {
        dao=new sae.infraestructura.jdbc.impl.mysql.DocenteDAO();        
    }
    public void Ingresar(Docente a) throws RepeatEntityException{
        try {
            this.Buscar(a.getId());
            throw new sae.dominio.exceptions.RepeatEntityException("Estudiante Repetido");
        } catch (NonExistenceEntityException ex) {
            getDao().Persistir(a);            
        }
    }
    public Docente Buscar(long id) throws sae.dominio.exceptions.NonExistenceEntityException{
        Docente a=dao.Buscar(id);
        if(a!=null){
            return a;
        }else{
            throw new sae.dominio.exceptions.NonExistenceEntityException("No Existe Ese Docente");
        }
    }
    public void Modificar(long id,Docente a) throws sae.dominio.exceptions.RepeatEntityException{                            
                if(a.getId()!=id){
                   Docente act=this.getDao().Buscar(a.getId());
                   if(act==null){
                      dao.Modificar(id,a);                      
                   }else{ 
                      throw new sae.dominio.exceptions.RepeatEntityException("Ese Id Ya ha sido Utilizado");
                   }
                }else{
                   dao.Modificar(id,a);
                }             
    }

    /**
     * @return the dao
     */
    public sae.dominio.docente.IDocenteDAO getDao() {
        return dao;
    }

}
