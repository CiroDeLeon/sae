/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.grupo.estudiante_has_grupo;

import sae.dominio.estudiante.Estudiante;
import sae.dominio.grupo.Grupo;

/**
 *
 * @author Usuario1
 */
public class Estudiante_has_Grupo {
    private Grupo grupo;
    private Estudiante estudiante;
    private java.sql.Timestamp creacion;
    private String responsable;

    /**
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the estudiante
     */
    public Estudiante getEstudiante() {
        return estudiante;
    }

    /**
     * @param estudiante the estudiante to set
     */
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    /**
     * @return the creacion
     */
    public java.sql.Timestamp getCreacion() {
        return creacion;
    }

    /**
     * @param creacion the creacion to set
     */
    public void setCreacion(java.sql.Timestamp creacion) {
        this.creacion = creacion;
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
    
}
