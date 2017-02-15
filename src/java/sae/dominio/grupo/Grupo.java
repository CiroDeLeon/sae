/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.grupo;

/**
 *
 * @author Usuario1
 */
public class Grupo {
   private long id;
   private sae.dominio.corte.CorteEscolar corte_escolar;
   private String grado;
   private String descripcion="ninguno";
   private String jornada;
   private String responsable;
    
    public Grupo() {
        
    }
    public Grupo(long id) {
        this.id=id;
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
     * @return the corte_escolar
     */
    public sae.dominio.corte.CorteEscolar getCorte_escolar() {
        return corte_escolar;
    }

    /**
     * @param corte_escolar the corte_escolar to set
     */
    public void setCorte_escolar(sae.dominio.corte.CorteEscolar corte_escolar) {
        this.corte_escolar = corte_escolar;
    }

    /**
     * @return the grado
     */
    public String getGrado() {
        return grado;
    }

    /**
     * @param grado the grado to set
     */
    public void setGrado(String grado) {
        this.grado = grado;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the jornada
     */
    public String getJornada() {
        return jornada;
    }

    /**
     * @param jornada the jornada to set
     */
    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    /**
     * @return the responsable
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
   

    @Override
    public String toString() {
        return this.getDescripcion(); //To change body of generated methods, choose Tools | Templates.
    }

}
