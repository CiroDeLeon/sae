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
import sae.dominio.docente.Docente;
import sae.dominio.estudiante.Estudiante;
import sae.dominio.exceptions.NonExistenceEntityException;
import sae.dominio.exceptions.RepeatEntityException;
import sae.infraestructura.jdbc.Pool;

/**
 *
 * @author Usuario1
 */
public class DocenteDAO implements sae.dominio.docente.IDocenteDAO{

    @Override
    public int Persistir(Docente docente) throws RepeatEntityException {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into docente (iddocente,nombres,apellidos,correo,clave,responsable,creacion,direccion,telefono) values (?,?,?,?,?,?,now(),?,?) ");
            ps.setLong(1,docente.getId());
            ps.setString(2,docente.getNombres());
            ps.setString(3,docente.getApellidos());
            ps.setString(4,docente.getCorreo());
            ps.setString(5,docente.getClave());
            ps.setString(6,docente.getResponsable());
            ps.setString(7,docente.getDireccion());
            ps.setString(8,docente.getTelefono());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(DocenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public int Modificar(long id, Docente docente) {
          java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("update docente set iddocente=?,nombres=?,apellidos=?,correo=?,clave=?,responsable=?,direccion=?,telefono=? where(iddocente="+id+") ");
            ps.setLong(1,docente.getId());
            ps.setString(2,docente.getNombres());
            ps.setString(3,docente.getApellidos());
            ps.setString(4,docente.getCorreo());
            ps.setString(5,docente.getClave());
            ps.setString(6,docente.getResponsable());
            ps.setString(7,docente.getDireccion());
            ps.setString(8,docente.getTelefono());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(DocenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Docente Buscar(long id) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql=" select iddocente,nombres,apellidos,correo,clave,responsable,direccion,telefono,creacion from docente where(iddocente="+id+")";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            Docente a=new Docente();
            if(rs.next()){                
                a.setId(rs.getLong(1));
                a.setNombres(rs.getString(2));
                a.setApellidos(rs.getString(3));
                a.setCorreo(rs.getString(4));
                a.setClave(rs.getString(5));
                a.setResponsable(rs.getString(6));
                a.setDireccion(rs.getString(7));
                a.setTelefono(rs.getString(8));
                a.setCreacion(rs.getTimestamp(9));
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
    public int Eliminar(long id) throws NonExistenceEntityException {
        if(this.Buscar(id)!=null) {
          java.sql.Connection  con=null;
          con=Pool.ObtenerConexion();
          String sql=" delete from docente where(iddocente="+id+")";
          PreparedStatement ps;
           try {
              ps = con.prepareStatement(sql);
              int result=ps.executeUpdate();            
              return result;
           } catch (SQLException ex) {
              Logger.getLogger(DocenteDAO.class.getName()).log(Level.SEVERE, null, ex);
              return -1;
           }finally{
             Pool.LiberarConexion(con);
           }       
       }else{
            throw new sae.dominio.exceptions.NonExistenceEntityException("No Existe Esta Entidad Docente");                        
       }
    }

    @Override
    public List<Docente> Busqueda(String busqueda) {
          java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql =" select iddocente,nombres,apellidos,correo,clave,responsable,direccion,telefono,creacion from docente ";
              sql+="where(";      
              sql+=" iddocente like ? or (";        
              sql+=" concat_ws(' ',nombres,apellidos) like ? or ";        
              sql+=" correo like ? ) ";
              sql+=") order by apellidos,nombres limit 20  ";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,""+busqueda+"%");
            ps.setString(2,"%"+busqueda+"%");
            ps.setString(3,"%"+busqueda+"%");
            ResultSet rs=ps.executeQuery();            
            List<Docente> lista=new ArrayList(); 
            while(rs.next()){              
                Docente a=new Docente();
                a.setId(rs.getLong(1));
                a.setNombres(rs.getString(2));
                a.setApellidos(rs.getString(3));
                a.setCorreo(rs.getString(4));
                a.setClave(rs.getString(5));
                a.setResponsable(rs.getString(6));
                a.setDireccion(rs.getString(7));
                a.setTelefono(rs.getString(8));
                a.setCreacion(rs.getTimestamp(9));
                lista.add(a);                
            }                        
            rs.close();
            ps.close();
            return lista;
        } catch (SQLException ex) {            
            Logger.getLogger(DocenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<Docente>();
        }finally{
            Pool.LiberarConexion(con);
        }       
    }
    
}
