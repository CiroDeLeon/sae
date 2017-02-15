/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.curso.nota;

/**
 *
 * @author Usuario1
 */
public class Estudiante_has_Nota {
    private sae.dominio.estudiante.Estudiante estudiante;
    private sae.dominio.curso.nota.Nota nota;
    private double valor_nota;

    /**
     * @return the estudiante
     */
    public sae.dominio.estudiante.Estudiante getEstudiante() {
        return estudiante;
    }

    /**
     * @param estudiante the estudiante to set
     */
    public void setEstudiante(sae.dominio.estudiante.Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    /**
     * @return the nota
     */
    public sae.dominio.curso.nota.Nota getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(sae.dominio.curso.nota.Nota nota) {
        this.nota = nota;
    }

    /**
     * @return the valor_nota
     */
    public double getValor_nota() {
        return valor_nota;
    }

    /**
     * @param valor_nota the valor_nota to set
     */
    public void setValor_nota(double valor_nota) {
        this.valor_nota = valor_nota;
    }
}
