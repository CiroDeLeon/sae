/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.grupo;

import java.util.List;

/**
 *
 * @author Usuario1
 */
public interface IGrupoDAO {
   public int Persistir(sae.dominio.grupo.Grupo grupo);
   public int Modificar(long id,sae.dominio.grupo.Grupo grupo);
   public sae.dominio.grupo.Grupo Buscar(long id);
   public int Eliminar(long id);
   public List<sae.dominio.grupo.Grupo> Busqueda(final String busqueda,long idcorte_academico);    
   public int AnexarCursoEnGrupo(long idgrupo,long idcurso);
   public int EliminarCursoEnGrupo(long idgrupo,long idcurso);
   public int CantidadDeIntegrantes(long idgrupo);
}
