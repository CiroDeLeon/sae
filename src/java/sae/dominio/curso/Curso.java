/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.curso;

/**
 *
 * @author Usuario1
 */
public class Curso {
   private long id;
   private sae.dominio.actor.Actor responsable;
   private sae.dominio.corte.CorteEscolar corte;   
   private sae.dominio.docente.Docente docente;
   private int cupo;
   private sae.dominio.asignatura.Asignatura asignatura;

    public Curso(long id) {
        this.id=id;
    }

    public Curso() {
    }
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the responsable
     */
    public sae.dominio.actor.Actor getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(sae.dominio.actor.Actor responsable) {
        this.responsable = responsable;
    }

    /**
     * @return the corte
     */
    public sae.dominio.corte.CorteEscolar getCorte() {
        return corte;
    }

    /**
     * @param corte the corte to set
     */
    public void setCorte(sae.dominio.corte.CorteEscolar corte) {
        this.corte = corte;
    }

    /**
     * @return the grupo
     */
    

    /**
     * @return the docente
     */
    public sae.dominio.docente.Docente getDocente() {
        return docente;
    }

    /**
     * @param docente the docente to set
     */
    public void setDocente(sae.dominio.docente.Docente docente) {
        this.docente = docente;
    }

   

    /**
     * @return the asignatura
     */
    public sae.dominio.asignatura.Asignatura getAsignatura() {
        return asignatura;
    }

    /**
     * @param asignatura the asignatura to set
     */
    public void setAsignatura(sae.dominio.asignatura.Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    /**
     * @return the cupo
     */
    public int getCupo() {
        return cupo;
    }

    /**
     * @param cupo the cupo to set
     */
    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    @Override
    public String toString() {
        if(this.id!=0){
           return this.getAsignatura().getDescripcion()+","+this.getDocente().toString();
        }else{
           return "Ninguno"; 
        }
    }

}
