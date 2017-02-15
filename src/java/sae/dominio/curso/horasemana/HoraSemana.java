/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.curso.horasemana;

import sae.dominio.curso.Curso;

/**
 *
 * @author Usuario1
 */
public class HoraSemana {
    private long id=0;
    private Curso curso;
    private int dia=0;
    private int hora=0;
    private int cuarto_inicial=0;
    private int cuarto_final=0;
    private int aula=0;

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
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * @return the dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * @return the hora
     */
    public int getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(int hora) {
        this.hora = hora;
    }

    /**
     * @return the cuarto_inicial
     */
    public int getCuarto_inicial() {
        return cuarto_inicial;
    }

    /**
     * @param cuarto_inicial the cuarto_inicial to set
     */
    public void setCuarto_inicial(int cuarto_inicial) {
        this.cuarto_inicial = cuarto_inicial;
    }

    /**
     * @return the cuarto_final
     */
    public int getCuarto_final() {
        return cuarto_final;
    }

    /**
     * @param cuarto_final the cuarto_final to set
     */
    public void setCuarto_final(int cuarto_final) {
        this.cuarto_final = cuarto_final;
    }

    /**
     * @return the aula
     */
    public int getAula() {
        return aula;
    }

    /**
     * @param aula the aula to set
     */
    public void setAula(int aula) {
        this.aula = aula;
    }
    public static String getDiaEnLetras(int dia){
        switch(dia){
            case 1 : return "Lunes";
            case 2 : return "Martes";
            case 3 : return "Miercoles";
            case 4 : return "Jueves";
            case 5 : return "Viernes";
            case 6 : return "Sabado";
            case 7 : return "Domingo";
            default : return "Invalido";    
        }
    } 
    public static int getCuartoInicialEnMinutos(int cuarto_inicial){
        switch(cuarto_inicial){
        case 1 : return 0;
        case 2 : return 15;    
        case 3 : return 30;    
        case 4 : return 45;    
        default : return -1;    
        }        
    }
    public static int getCuartoFinalEnMinutos(int cuarto_final){
        switch(cuarto_final){
        case 1 : return 15;
        case 2 : return 30;    
        case 3 : return 45;    
        case 4 : return 59;    
        default : return -1;    
        }        
    }

    @Override
    public String toString() {
        return HoraSemana.getDiaEnLetras(dia)+" "+hora+"h "+HoraSemana.getCuartoInicialEnMinutos(this.cuarto_inicial)+"m- "+hora+"h "+HoraSemana.getCuartoFinalEnMinutos(cuarto_final)+"m"; //To change body of generated methods, choose Tools | Templates.
    }
    public boolean HayColisionTemporal(HoraSemana hs){
        if(hs.getDia()==this.getDia() && hs.getHora()==this.getHora()){
           if(hs.getCuarto_final()<this.getCuarto_inicial() || hs.getCuarto_inicial()>this.getCuarto_final()){
              return false;           
           }else{
               if(hs.getCuarto_final()<this.getCuarto_inicial() || hs.getCuarto_inicial()>this.getCuarto_final()){
                  return false;     
               }
               return true;
           }
        }else{
            return false;
        }
    }
}
