/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.asignatura;

import java.util.List;

/**
 *
 * @author Usuario1
 */
public interface IAsignaturaDAO {
   public int Persistir(sae.dominio.asignatura.Asignatura asignatura);
   public int Modificar(long id,sae.dominio.asignatura.Asignatura asignatura);
   public sae.dominio.asignatura.Asignatura Buscar(long id);
   public int Eliminar(long id);
   public List<sae.dominio.asignatura.Asignatura> Busqueda(final String busqueda);
}
