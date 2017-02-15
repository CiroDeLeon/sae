/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae;

import java.util.Iterator;
import sae.dominio.actor.Actor;
import sae.dominio.estudiante.EstudianteService;
import sae.dominio.exceptions.NonExistenceEntityException;

/**
 *
 * @author Usuario1
 */
public class ActorDAO {
    
    
    public static void ProbarInsert(){                
        Actor a=new Actor();
        a.setId(10784054);
        a.setNombres("ciro cayo");
        a.setApellidos("de leon burgos");
        a.setCorreo("elalumnopc@hotmail.com");
        a.setClave("3145814781papa");
        a.setTipo("Director");
        sae.infraestructura.jdbc.impl.mysql.ActorDAO dao=new sae.infraestructura.jdbc.impl.mysql.ActorDAO();
        int sw=dao.Persistir(a);
        System.out.println(sw);        
    }
    public static void ProbarUpdate(){
        Actor a=new Actor();
        a.setId(10784054);
        a.setNombres("ciro cayo");
        a.setApellidos("de leon burgos");
        a.setCorreo("elalumnopc@hotmail.com");
        a.setClave("3145814781papa");
        a.setTipo("Director");
        sae.infraestructura.jdbc.impl.mysql.ActorDAO dao=new sae.infraestructura.jdbc.impl.mysql.ActorDAO();
        int sw=dao.Modificar(10784054,a);
        System.out.println(sw);        
    }
    public static void ProbarSelect() throws NonExistenceEntityException{
        sae.infraestructura.jdbc.impl.mysql.ActorDAO dao=new sae.infraestructura.jdbc.impl.mysql.ActorDAO();
        Actor a=dao.Buscar(19);
    }
    public static void ProbarBusqueda(){
        Iterator<Actor> it=new sae.infraestructura.jdbc.impl.mysql.ActorDAO().Busqueda("").iterator();
        while(it.hasNext()){
            Actor a=it.next();
            System.out.println(a.getId()+" "+a.toString());
        }
        
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NonExistenceEntityException {
        // TODO code application logic here
        System.out.println(new EstudianteService().getDao().ObtenerEstudiantes(4).size());
    }
    
    
}
