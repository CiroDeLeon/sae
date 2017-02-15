/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.corte;

/**
 *
 * @author Usuario1
 */
public class CorteEscolar {
    private long    id=0;
    private String  descripcion="Ninguno";
    private double  maxima_nota;
    private double  minima_nota_para_aprobar;
    private String  responsable;
    private boolean actual;


    public CorteEscolar() {
    }
    public CorteEscolar(long id) {
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
     * @return the maxima_nota
     */
    public double getMaxima_nota() {
        return maxima_nota;
    }

    /**
     * @param maxima_nota the maxima_nota to set
     */
    public void setMaxima_nota(double maxima_nota) {
        this.maxima_nota = maxima_nota;
    }

    /**
     * @return the minima_nota_para_aprobar
     */
    public double getMinima_nota_para_aprobar() {
        return minima_nota_para_aprobar;
    }

    /**
     * @param minima_nota_para_aprobar the minima_nota_para_aprobar to set
     */
    public void setMinima_nota_para_aprobar(double minima_nota_para_aprobar) {
        this.minima_nota_para_aprobar = minima_nota_para_aprobar;
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

    /**
     * @return the actual
     */
    public boolean isActual() {
        return actual;
    }

    /**
     * @param actual the actual to set
     */
    public void setActual(boolean actual) {
        this.actual = actual;
    }
}
