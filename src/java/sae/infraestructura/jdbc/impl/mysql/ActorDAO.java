/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.infraestructura.jdbc.impl.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sae.dominio.actor.Actor;
import sae.dominio.exceptions.NonExistenceEntityException;
import sae.infraestructura.jdbc.Pool;

/**
 *
 * @author Usuario1
 */
public class ActorDAO implements sae.dominio.actor.IActorDAO{

    @Override
    public int Persistir(Actor actor) {       
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into actor (idactor,nombres,apellidos,correo,clave,tipo) values (?,?,?,?,?,?)");
            ps.setLong(1,actor.getId());
            ps.setString(2,actor.getNombres());
            ps.setString(3,actor.getApellidos());
            ps.setString(4,actor.getCorreo());
            ps.setString(5,actor.getClave());
            ps.setString(6,actor.getTipo());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ActorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
       
    }

    @Override
    public int Modificar(long id, Actor actor) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("update actor set idactor=?,nombres=?,apellidos=?,correo=?,clave=?,tipo=? where(idactor="+id+")");
            ps.setLong(1,actor.getId());
            ps.setString(2,actor.getNombres());
            ps.setString(3,actor.getApellidos());
            ps.setString(4,actor.getCorreo());
            ps.setString(5,actor.getClave());
            ps.setString(6,actor.getTipo());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ActorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Actor Buscar(long id){
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql=" select idactor,nombres,apellidos,correo,clave,tipo from actor where(idactor="+id+")";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            Actor a=new Actor();
            if(rs.next()){                
                a.setId(rs.getLong(1));
                a.setNombres(rs.getString(2));
                a.setApellidos(rs.getString(3));
                a.setCorreo(rs.getString(4));
                a.setClave(rs.getString(5));
                a.setTipo(rs.getString(6));
                rs.close();
                ps.close();
                return a;
            }    
            return null;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }       
    }

    @Override
    public int Eliminar(long id) throws sae.dominio.exceptions.NonExistenceEntityException{
       if(this.Buscar(id)!=null) {
          java.sql.Connection  con=null;
          con=Pool.ObtenerConexion();
          String sql=" delete from actor where(idactor="+id+")";
          PreparedStatement ps;
           try {
              ps = con.prepareStatement(sql);
              int result=ps.executeUpdate();            
              return result;
           } catch (SQLException ex) {
              Logger.getLogger(ActorDAO.class.getName()).log(Level.SEVERE, null, ex);
              return -1;
           }finally{
             Pool.LiberarConexion(con);
           }       
       }else{
            throw new sae.dominio.exceptions.NonExistenceEntityException("No Existe Esta Entidad Actor");                        
       }
    }

    @Override
    public List<Actor> Busqueda(String busqueda) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql =" select idactor,nombres,apellidos,correo,clave,tipo from actor ";
              sql+="where(";      
              sql+=" idactor like ? or (";        
              sql+=" concat_ws(' ',nombres,apellidos) like ? or ";        
              sql+=" tipo like ? ) ";
              sql+=") order by apellidos,nombres limit 20  ";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,""+busqueda+"%");
            ps.setString(2,"%"+busqueda+"%");
            ps.setString(3,"%"+busqueda+"%");
            ResultSet rs=ps.executeQuery();            
            List<Actor> lista=new ArrayList(); 
            while(rs.next()){              
                Actor a=new Actor();
                a.setId(rs.getLong(1));
                a.setNombres(rs.getString(2));
                a.setApellidos(rs.getString(3));
                a.setCorreo(rs.getString(4));
                a.setClave(rs.getString(5));
                a.setTipo(rs.getString(6));
                lista.add(a);                
            }                        
            rs.close();
            ps.close();
            return lista;
        } catch (SQLException ex) {            
            Logger.getLogger(ActorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<Actor>();
        }finally{
            Pool.LiberarConexion(con);
        }       
    }
    
}
