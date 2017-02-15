/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.estudiante;

import sae.dominio.actor.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import sae.dominio.exceptions.NonExistenceEntityException;
import sae.dominio.exceptions.RepeatEntityException;

/**
 *
 * @author Usuario1
 */
public class EstudianteService {
    private sae.dominio.estudiante.IEstudianteDAO dao;    
    public EstudianteService() {
        dao=new sae.infraestructura.jdbc.impl.mysql.EstudianteDAO();        
    }
    public void Ingresar(Estudiante a) throws RepeatEntityException{
        try {
            this.Buscar(a.getId());
            throw new sae.dominio.exceptions.RepeatEntityException("Estudiante Repetido");
        } catch (NonExistenceEntityException ex) {
            getDao().Persistir(a);            
        }
    }
    public Estudiante Buscar(long id) throws sae.dominio.exceptions.NonExistenceEntityException{
        Estudiante a=dao.Buscar(id);
        if(a!=null){
            return a;
        }else{
            throw new sae.dominio.exceptions.NonExistenceEntityException("No Existe Ese Estudiante");
        }
    }
    public void Modificar(long id,Estudiante a) throws sae.dominio.exceptions.RepeatEntityException{                            
                if(a.getId()!=id){
                   Estudiante act=this.getDao().Buscar(a.getId());
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
    public sae.dominio.estudiante.IEstudianteDAO getDao() {
        return dao;
    }

}
