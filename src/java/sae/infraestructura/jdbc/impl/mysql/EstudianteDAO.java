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
import sae.dominio.estudiante.Estudiante;
import sae.dominio.exceptions.NonExistenceEntityException;
import sae.dominio.exceptions.RepeatEntityException;
import sae.infraestructura.jdbc.Pool;

/**
 *
 * @author Usuario1
 */
public class EstudianteDAO implements sae.dominio.estudiante.IEstudianteDAO{

    @Override
    public int Persistir(Estudiante estudiante) throws RepeatEntityException {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into estudiante (idestudiante,nombres,apellidos,correo,clave,responsable,creacion,direccion,telefono) values (?,?,?,?,?,?,now(),?,?) ");
            ps.setLong(1,estudiante.getId());
            ps.setString(2,estudiante.getNombres());
            ps.setString(3,estudiante.getApellidos());
            ps.setString(4,estudiante.getCorreo());
            ps.setString(5,estudiante.getClave());
            ps.setString(6,estudiante.getResponsable());
            ps.setString(7,estudiante.getDireccion());
            ps.setString(8,estudiante.getTelefono());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public int Modificar(long id, Estudiante estudiante) {
          java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("update estudiante set idestudiante=?,nombres=?,apellidos=?,correo=?,clave=?,responsable=?,direccion=?,telefono=? where(idestudiante="+id+") ");
            ps.setLong(1,estudiante.getId());
            ps.setString(2,estudiante.getNombres());
            ps.setString(3,estudiante.getApellidos());
            ps.setString(4,estudiante.getCorreo());
            ps.setString(5,estudiante.getClave());
            ps.setString(6,estudiante.getResponsable());
            ps.setString(7,estudiante.getDireccion());
            ps.setString(8,estudiante.getTelefono());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Estudiante Buscar(long id) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql=" select idestudiante,nombres,apellidos,correo,clave,responsable,direccion,telefono,creacion from estudiante where(idestudiante="+id+")";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            Estudiante a=new Estudiante();
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
          String sql=" delete from estudiante where(idestudiante="+id+")";
          PreparedStatement ps;
           try {
              ps = con.prepareStatement(sql);
              int result=ps.executeUpdate();            
              return result;
           } catch (SQLException ex) {
              Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
              return -1;
           }finally{
             Pool.LiberarConexion(con);
           }       
       }else{
            throw new sae.dominio.exceptions.NonExistenceEntityException("No Existe Esta Entidad Estudiante");                        
       }
    }

    @Override
    public List<Estudiante> Busqueda(String busqueda) {
          java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql =" select idestudiante,nombres,apellidos,correo,clave,responsable,direccion,telefono,creacion from estudiante ";
              sql+="where(";      
              sql+=" idestudiante like ? or (";        
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
            List<Estudiante> lista=new ArrayList(); 
            while(rs.next()){              
                Estudiante a=new Estudiante();
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
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<Estudiante>();
        }finally{
            Pool.LiberarConexion(con);
        }       
    }

    @Override
    public int AnexarEstudianteEnGrupo(long idestudiante, long idgrupo) {
          java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into estudiante_has_grupo (idestudiante,idgrupo,creacion) values (?,?,now()) ");
            ps.setLong(1,idestudiante);
            ps.setLong(2,idgrupo);            
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public List<Estudiante> ObtenerEstudiantes(long idgrupo) {
          java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql =" select estudiante.idestudiante,estudiante.nombres,estudiante.apellidos,estudiante.correo,estudiante.clave,estudiante.responsable,estudiante.direccion,estudiante.telefono,estudiante.creacion from estudiante,estudiante_has_grupo  ";
              sql+=" where(";      
              sql+=" estudiante.idestudiante=estudiante_has_grupo.idestudiante and ";        
              sql+=" estudiante_has_grupo.idgrupo="+idgrupo+" ";                      
              sql+=" ) order by apellidos,nombres limit 20  ";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);            
            ResultSet rs=ps.executeQuery();            
            List<Estudiante> lista=new ArrayList(); 
            while(rs.next()){              
                Estudiante a=new Estudiante();
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
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<Estudiante>();
        }finally{
            Pool.LiberarConexion(con);
        }       
    }

    @Override
    public int EliminarEstudianteDeGrupo(long idestudiante, long idgrupo) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql =" delete from estudiante_has_grupo  ";
              sql+=" where(";      
              sql+=" idestudiante="+idestudiante+" and ";        
              sql+=" idgrupo="+idgrupo+" ";                      
              sql+=" )  ";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);            
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {            
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
           return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }
    
}
