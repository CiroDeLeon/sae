/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.actor;

import java.util.logging.Level;
import java.util.logging.Logger;
import sae.dominio.exceptions.NonExistenceEntityException;
import sae.dominio.exceptions.RepeatEntityException;

/**
 *
 * @author Usuario1
 */
public class ActorService {
    private sae.dominio.actor.IActorDAO dao;    
    public ActorService() {
        dao=new sae.infraestructura.jdbc.impl.mysql.ActorDAO();        
    }
    public void Ingresar(Actor a) throws RepeatEntityException{
        try {
            this.Buscar(a.getId());
            throw new sae.dominio.exceptions.RepeatEntityException("Actor Repetido");
        } catch (NonExistenceEntityException ex) {
            getDao().Persistir(a);            
        }
    }
    public Actor Buscar(long id) throws sae.dominio.exceptions.NonExistenceEntityException{
        Actor a=dao.Buscar(id);
        if(a!=null){
            return a;
        }else{
            throw new sae.dominio.exceptions.NonExistenceEntityException("No Existe Ese Actor");
        }
    }
    public void Modificar(long id,Actor a) throws sae.dominio.exceptions.RepeatEntityException{                            
                if(a.getId()!=id){
                   Actor act=this.getDao().Buscar(a.getId());
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
    public sae.dominio.actor.IActorDAO getDao() {
        return dao;
    }

}
