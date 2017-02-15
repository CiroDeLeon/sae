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
import sae.dominio.corte.CorteEscolar;
import sae.dominio.exceptions.NonExistenceEntityException;
import sae.dominio.exceptions.RepeatEntityException;
import sae.infraestructura.jdbc.Pool;

/**
 *
 * @author Usuario1
 */
public class CorteEscolarDAO implements sae.dominio.corte.ICorteEscolarDAO{

    @Override
    public int Persistir(CorteEscolar corte){
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into CorteEscolar (descripcion,MaximaNota,MinimaNotaParaAprobar,responsable,actual) values (?,?,?,?,?) ");            
            ps.setString(1,corte.getDescripcion());
            ps.setDouble(2,corte.getMaxima_nota());
            ps.setDouble(3,corte.getMinima_nota_para_aprobar());
            ps.setString(4,corte.getResponsable());            
            ps.setBoolean(5,corte.isActual());
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
    public int Modificar(long id, CorteEscolar corte) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("update CorteEscolar set descripcion=?,MaximaNota=?,MinimaNotaParaAprobar=?,responsable=?,actual=? where(idCorteEscolar="+id+") ");            
            ps.setString(1,corte.getDescripcion());
            ps.setDouble(2,corte.getMaxima_nota());
            ps.setDouble(3,corte.getMinima_nota_para_aprobar());
            ps.setString(4,corte.getResponsable());            
            ps.setBoolean(5,corte.isActual());
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
    public CorteEscolar Buscar(long id) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql=" select idCorteEscolar,descripcion,MaximaNota,MinimaNotaParaAprobar,responsable,actual from CorteEscolar where(idCorteEscolar="+id+")";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            CorteEscolar a=new CorteEscolar();
            if(rs.next()){                
                a.setId(rs.getLong(1));
                a.setDescripcion(rs.getString(2));
                a.setMaxima_nota(rs.getDouble(3));
                a.setMinima_nota_para_aprobar(rs.getDouble(4));
                a.setResponsable(rs.getString(5));
                a.setActual(rs.getBoolean(6));
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
            PreparedStatement ps=con.prepareStatement("delete from CorteEscolar where(idCorteEscolar="+id+")  ");                     
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
    public List<CorteEscolar> Busqueda(String busqueda) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql=" select idCorteEscolar,descripcion,MaximaNota,MinimaNotaParaAprobar,responsable,actual from CorteEscolar where(idCorteEscolar like ? or (descripcion like ?) )order by idCorteEscolar desc ";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,busqueda+"%");
            ps.setString(2,"%"+busqueda+"%");
            ResultSet rs=ps.executeQuery();
            CorteEscolar a=new CorteEscolar();
            List<CorteEscolar>lista=new ArrayList();
            while(rs.next()){                
                a=new CorteEscolar();
                a.setId(rs.getLong(1));
                a.setDescripcion(rs.getString(2));
                a.setMaxima_nota(rs.getDouble(3));
                a.setMinima_nota_para_aprobar(rs.getDouble(4));
                a.setResponsable(rs.getString(5));
                a.setActual(rs.getBoolean(6));                
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

    @Override
    public CorteEscolar ObtenerCorteVigente() {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql=" select idCorteEscolar,descripcion,MaximaNota,MinimaNotaParaAprobar,responsable,actual from CorteEscolar where(actual=true) order by idCorteEscolar desc limit 1 ";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            CorteEscolar a=new CorteEscolar();
            if(rs.next()){                
                a.setId(rs.getLong(1));
                a.setDescripcion(rs.getString(2));
                a.setMaxima_nota(rs.getDouble(3));
                a.setMinima_nota_para_aprobar(rs.getDouble(4));
                a.setResponsable(rs.getString(5));
                a.setActual(rs.getBoolean(6));
                rs.close();
                ps.close();
                return a;
            }    
            return a;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }finally{
            Pool.LiberarConexion(con);
        }       
    }

    @Override
    public int Desmarcar(long id) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement(" update CorteEscolar set actual=false where(idCorteEscolar!="+id+") ");                    
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
    
}
