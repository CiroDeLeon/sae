/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.curso;

import java.util.Iterator;
import sae.dominio.curso.horasemana.HoraSemana;
import sae.dominio.grupo.GrupoService;

/**
 *
 * @author Usuario1
 */
public class CursoService {
    private sae.dominio.curso.ICursoDAO dao;
    private String mensaje="";    
    public CursoService() {
        dao=new sae.infraestructura.jdbc.impl.mysql.CursoDAO();
    }
    public boolean PuedeAsignarHoraSemanaEnCorte(HoraSemana hs,long idcurso,long idcorte){        
        Iterator<HoraSemana> it=this.getDao().ObtenerHoras(idcorte).iterator();
        Curso curso=this.getDao().Buscar(idcurso);
        int i=0;
        while(it.hasNext()){
            HoraSemana h=it.next();
            i++;
            if(h.getId()!=hs.getId() && hs.HayColisionTemporal(h)){
                if(h.getCurso().getDocente().getId()!=curso.getDocente().getId()){
                   if(h.getAula()==hs.getAula()){
                       mensaje="COLISION EN AULA "+h.getAula()+" CON CURSO "+h.getCurso().getId()+" "+h.toString();
                       return false;
                   } 
                }else{
                   mensaje="COLISION CON HORARIO DE DOCENTE CURSO Cod "+h.getCurso().getId(); 
                   return false;
                }
            }
        }
        mensaje="NO HAY COLISIONES "+i;
        return true;
    }
    public boolean ExisteAsignaturaEnHorarioDeEstudiante(long idAsignatura,long idestudiante,long idcorte){
        Iterator<Curso> it=dao.ObtenerCursos(idestudiante, idcorte).iterator();
        while(it.hasNext()){
            Curso c=it.next();
            if(c.getAsignatura().getId()==idAsignatura){
                return true;
            }
        }
        return false;
    }
    public boolean ExisteAsignaturaEnHorarioDeGrupo(long idasignatura,long idgrupo,long idcorte){
        Iterator<Curso> it=dao.Listar(idgrupo).iterator();
        while(it.hasNext()){
            Curso c=it.next();
            if(c.getAsignatura().getId()==idasignatura){                
                return true;
            }
        }
        return false;
    }
    public boolean PuedeAnexarCursoEnGrupo(long idgrupo,Curso curso){        
       GrupoService gs=new GrupoService();
       int integrantes=gs.getDao().CantidadDeIntegrantes(idgrupo);
       if(integrantes>curso.getCupo()){
           this.mensaje="No Hay Suficientes Cupos En Este Curso\n  Para ser Anexado en Este Grupo Modificar Cupo del curso a "+integrantes;
           return false;
       }
       if(this.ExisteAsignaturaEnHorarioDeGrupo(curso.getAsignatura().getId(), idgrupo,curso.getCorte().getId())){
          this.mensaje="COLISION DE ASIGNATURA COD "+curso.getAsignatura().getId()+" "+curso.getAsignatura().getDescripcion(); 
          return false;      
       }
       Iterator <HoraSemana> it=dao.ObtenerHorasDeCurso(curso.getId()).iterator();        
        while(it.hasNext()){
            HoraSemana hs=it.next();            
            Iterator<HoraSemana> it2=dao.ObtenerHorasDeGrupo(idgrupo).iterator();
            while(it2.hasNext()){
                HoraSemana hs2=it2.next();                                    
                if(hs.HayColisionTemporal(hs2) && hs.getCurso().getId()!=hs2.getCurso().getId()){
                    this.mensaje="Colision con Curso COD "+hs2.getCurso().getId()+" "+HoraSemana.getDiaEnLetras(hs2.getDia())+" "+hs2.getHora()+" "+HoraSemana.getCuartoInicialEnMinutos(hs2.getCuarto_inicial())+"-"+HoraSemana.getCuartoFinalEnMinutos(hs2.getCuarto_final());
                    return false;
                }
            }
        }       
       return true;   
    }
    public boolean PuedeMatricularCurso(long idestudiante,Curso curso){                
        int cantidad=this.dao.ObtenerNumeroDeEstudiantesMatriculados(curso.getId());
        if(cantidad+1>curso.getCupo()){
            this.mensaje="NO PUEDES MATRICULAR EL CURSO "+curso.getId()+" "+curso.getAsignatura().getDescripcion()+" CUPO LLENO "+curso.getCupo()+" ESTUDIANTES";
            return false;
        }
        boolean result=true;       
        if(this.ExisteAsignaturaEnHorarioDeEstudiante(curso.getAsignatura().getId(),idestudiante,curso.getCorte().getId())){
            this.mensaje="Colision con Asignatura "+curso.getId()+" "+curso.getAsignatura().getDescripcion();
            return false;
        }
        Iterator <HoraSemana> it=dao.ObtenerHorasDeCurso(curso.getId()).iterator();        
        while(it.hasNext()){
            HoraSemana hs=it.next();            
            Iterator<HoraSemana> it2=dao.ObtenerHoras(curso.getCorte().getId(),idestudiante).iterator();
            while(it2.hasNext()){
                HoraSemana hs2=it2.next();                                    
                if(hs.HayColisionTemporal(hs2) && hs.getCurso().getId()!=hs2.getCurso().getId()){
                    this.mensaje="Colision con Curso COD "+hs2.getCurso().getId()+" "+HoraSemana.getDiaEnLetras(hs2.getDia())+" "+hs2.getHora()+" "+HoraSemana.getCuartoInicialEnMinutos(hs2.getCuarto_inicial())+"-"+HoraSemana.getCuartoFinalEnMinutos(hs2.getCuarto_final());
                    return false;
                }
            }
        }
        return result;
    }
    /**
     * @return the dao
     */
    public sae.dominio.curso.ICursoDAO getDao() {
        return dao;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

}
