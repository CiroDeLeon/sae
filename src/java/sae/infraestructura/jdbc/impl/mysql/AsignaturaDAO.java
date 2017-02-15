/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.infraestructura.jdbc.impl.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sae.dominio.asignatura.Asignatura;
import sae.dominio.grupo.Grupo;
import sae.infraestructura.jdbc.Pool;

/**
 *
 * @author Usuario1
 */
public class AsignaturaDAO implements sae.dominio.asignatura.IAsignaturaDAO{

    @Override
    public int Persistir(Asignatura asignatura) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into asignatura (descripcion,creditos) values (?,?) ");            
            ps.setString(1,asignatura.getDescripcion());
            ps.setInt(2,asignatura.getCreditos());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(AsignaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public int Modificar(long id, Asignatura asignatura) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("update asignatura set idasignatura=?,descripcion=?,creditos=?  where(idasignatura="+id+") ");            
            ps.setLong(1,asignatura.getId());
            ps.setString(2,asignatura.getDescripcion());
            ps.setInt(3,asignatura.getCreditos());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(AsignaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Asignatura Buscar(long id) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql=" select idasignatura,descripcion,creditos from asignatura where(idasignatura="+id+")";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            Asignatura a=new Asignatura();
            if(rs.next()){                
                a.setId(rs.getLong(1));
                a.setDescripcion(rs.getString(2));
                a.setCreditos(rs.getInt(3));
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
    public int Eliminar(long id) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("delete from asignatura where(idasignatura="+id+")  ");                                 
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(AsignaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public List<Asignatura> Busqueda(String busqueda) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();              
       String sql=" select idasignatura,descripcion,creditos from asignatura where( idasignatura like ? or descripcion like ? )order by descripcion ";
       //System.out.println(sql);
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,busqueda+"%");
            ps.setString(2,"%"+busqueda+"%");           
            ResultSet rs=ps.executeQuery();            
            List<Asignatura>lista=new ArrayList();
            while(rs.next()){                
                Asignatura a=new Asignatura();
                a.setId(rs.getLong(1));                
                a.setDescripcion(rs.getString(2));
                a.setCreditos(rs.getInt(3));
                lista.add(a);
            }               
            rs.close();
            ps.close();            
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }       
    }
    
}
