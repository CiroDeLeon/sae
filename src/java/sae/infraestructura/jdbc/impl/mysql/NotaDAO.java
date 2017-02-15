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
import sae.dominio.curso.Curso;
import sae.dominio.curso.nota.INotaDAO;
import sae.dominio.curso.nota.Nota;
import sae.infraestructura.jdbc.Pool;

/**
 *
 * @author Usuario1
 */
public class NotaDAO implements INotaDAO {

    @Override
    public int Persistir(Nota nota) {
             java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into nota (idcurso,descripcion,peso,periodo) values (?,?,?,?) ");            
            ps.setLong(1,nota.getCurso().getId());
            ps.setString(2,nota.getDescripcion());
            ps.setDouble(3,nota.getPeso());
            ps.setString(4,nota.getPeriodo());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(NotaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public int Modificar(long id, Nota nota) {
            java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("update nota set idcurso=?,descripcion=?,peso=?,periodo=? where(idnota="+id+") ");            
            ps.setLong(1,nota.getCurso().getId());
            ps.setString(2,nota.getDescripcion());
            ps.setDouble(3,nota.getPeso());
            ps.setString(4,nota.getPeriodo());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(NotaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Nota Buscar(long id) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql=" select idnota,idcurso,descripcion,peso,periodo from nota where(idnota="+id+")";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            Nota a=new Nota();
            if(rs.next()){                
                a.setId(rs.getLong(1));
                a.setCurso(new Curso(rs.getLong(2)));
                a.setDescripcion(rs.getString(3));
                a.setPeso(rs.getDouble(4));
                a.setPeriodo(rs.getString(5));
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
            PreparedStatement ps=con.prepareStatement("delete from nota where(idnota="+id+")  ");                                 
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
    public List<Nota> Busqueda(long idcurso,String busqueda) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql=" select idnota,idcurso,descripcion,peso,periodo from nota where(idcurso="+idcurso+")";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            List<Nota> lista=new ArrayList();
            while(rs.next()){                
                Nota a=new Nota();
                a.setId(rs.getLong(1));
                a.setCurso(new Curso(rs.getLong(2)));
                a.setDescripcion(rs.getString(3));
                a.setPeso(rs.getDouble(4));
                a.setPeriodo(rs.getString(5));
                lista.add(a);
            }   
            rs.close();
            ps.close();
            return lista;            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            List<Nota> lista=new ArrayList();
            return lista;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    
}
