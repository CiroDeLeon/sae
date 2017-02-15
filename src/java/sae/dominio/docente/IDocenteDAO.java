/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.docente;

import java.util.List;

/**
 *
 * @author Usuario1
 */
public interface IDocenteDAO {
   public int Persistir(sae.dominio.docente.Docente docente) throws sae.dominio.exceptions.RepeatEntityException;
   public int Modificar(long id,sae.dominio.docente.Docente docente);
   public sae.dominio.docente.Docente Buscar(long id);
   public int Eliminar(long id)throws sae.dominio.exceptions.NonExistenceEntityException;
   public List<sae.dominio.docente.Docente> Busqueda(final String busqueda);    
}
