/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.estudiante;

import java.util.List;

/**
 *
 * @author Usuario1
 */
public interface IEstudianteDAO {
   public int Persistir(sae.dominio.estudiante.Estudiante estudiante) throws sae.dominio.exceptions.RepeatEntityException;
   public int Modificar(long id,sae.dominio.estudiante.Estudiante estudiante);
   public Estudiante Buscar(long id);
   public int Eliminar(long id)throws sae.dominio.exceptions.NonExistenceEntityException;
   public List<Estudiante> Busqueda(final String busqueda);    
   public int AnexarEstudianteEnGrupo(long idestudiante,long idgrupo);
   List<Estudiante> ObtenerEstudiantes(long idgrupo);
   public int EliminarEstudianteDeGrupo(long idestudiante,long idgrupo);
}
