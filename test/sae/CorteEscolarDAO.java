/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae;

import java.util.Iterator;
import sae.dominio.corte.CorteEscolar;
import sae.dominio.curso.horasemana.HoraSemana;

/**
 *
 * @author Usuario1
 */
public class CorteEscolarDAO {
    public static void ProbarInsert(){
        sae.dominio.corte.CorteEscolar ce=new sae.dominio.corte.CorteEscolar();
        ce.setId(10784054);
        ce.setDescripcion("año 2017");
        ce.setMaxima_nota(10.0);
        ce.setMinima_nota_para_aprobar(7.0);
        ce.setResponsable("yo");
        new sae.infraestructura.jdbc.impl.mysql.CorteEscolarDAO().Persistir(ce);
    }
    public static void ProbarUpdate(){
        sae.dominio.corte.CorteEscolar ce=new sae.dominio.corte.CorteEscolar();
        ce.setId(1);
        ce.setDescripcion("año 2016");
        ce.setMaxima_nota(10.0);
        ce.setMinima_nota_para_aprobar(7.0);
        ce.setResponsable("ciro faro");
        new sae.infraestructura.jdbc.impl.mysql.CorteEscolarDAO().Modificar(1,ce);
    }
    public static void ProbarDelete(){
        sae.dominio.corte.CorteEscolar ce=new sae.dominio.corte.CorteEscolar();
        ce.setId(1);        
        new sae.infraestructura.jdbc.impl.mysql.CorteEscolarDAO().Eliminar(1);
    }
    public static void ProbarBusquedaIncremental(){        
        Iterator<CorteEscolar> it=new sae.infraestructura.jdbc.impl.mysql.CorteEscolarDAO().Busqueda("").iterator();
        while(it.hasNext()){
            CorteEscolar ce=it.next();
            System.out.println(ce.getId());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //System.out.println(""+new sae.dominio.corte.CorteEscolarService().getDao().ObtenerCorteVigente().getId());
        HoraSemana hs=new HoraSemana();
        hs.setDia(1);
        hs.setHora(7);
        hs.setCuarto_inicial(1);
        hs.setCuarto_final(3);
        
        HoraSemana hs2=new HoraSemana();
        hs2.setDia(1);
        hs2.setHora(7);
        hs2.setCuarto_inicial(4);
        hs2.setCuarto_final(4);
        
        System.out.println(hs.HayColisionTemporal(hs2));
        
        
    }
    
}
