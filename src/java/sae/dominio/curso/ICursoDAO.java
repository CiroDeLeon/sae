/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.curso;

import java.util.List;
import sae.dominio.curso.horasemana.HoraSemana;

/**
 *
 * @author Usuario1
 */
public interface ICursoDAO {
   public int Persistir(sae.dominio.curso.Curso curso);
   public int Modificar(long id,sae.dominio.curso.Curso curso);
   public sae.dominio.curso.Curso Buscar(long id);
   public int Eliminar(long id);
   public List<sae.dominio.curso.Curso> Busqueda(final String busqueda,long id_corte_escolar);    
   public List<sae.dominio.curso.Curso> Listar(long id_grupo);    
   public int PersistirHoraSemana(HoraSemana hora_semana);
   public HoraSemana BuscarHoraSemana(long id_hora_semana);
   public int ModificarHoraSemana(long id,sae.dominio.curso.horasemana.HoraSemana horasemana);
   public List<HoraSemana> ObtenerHorasDeCurso(long idcurso);   
   public List<HoraSemana> ObtenerHorasDeGrupo(long idgrupo);   
   public List<HoraSemana> ObtenerHoras(long idcorte);
   public List<HoraSemana> ObtenerHoras(long idcorte,long idestudiante);
   public int EliminarHoraSemana(long id);
   public int Persistir(sae.dominio.curso.estudiante_has_curso.Estudiante_Has_Curso ehc);
   public int EliminarEstudianteHasCurso(long idestudiante,long idcurso);
   List<sae.dominio.curso.Curso> ObtenerCursos(long idestudiante,long idcohorte);
   public int ObtenerNumeroDeEstudiantesMatriculados(long idcurso);
}
