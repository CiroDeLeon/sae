/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.actor;

import java.util.List;

/**
 *
 * @author Usuario1
 */
public interface IActorDAO {
   public int Persistir(sae.dominio.actor.Actor actor) throws sae.dominio.exceptions.RepeatEntityException;
   public int Modificar(long id,sae.dominio.actor.Actor actor);
   public Actor Buscar(long id);
   public int Eliminar(long id)throws sae.dominio.exceptions.NonExistenceEntityException;
   public List<Actor> Busqueda(final String busqueda);
}
