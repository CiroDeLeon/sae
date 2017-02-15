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
import sae.dominio.grupo.Grupo;
import sae.infraestructura.jdbc.Pool;

/**
 *
 * @author Usuario1
 */
public class GrupoDAO implements sae.dominio.grupo.IGrupoDAO{
    @Override
    public int Persistir(Grupo grupo){
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into Grupo (idcorteescolar,grado,descripcion,jornada,responsable) values (?,?,?,?,?) ");            
            ps.setLong(1,grupo.getCorte_escolar().getId());
            ps.setString(2,grupo.getGrado());
            ps.setString(3,grupo.getDescripcion());
            ps.setString(4,grupo.getJornada());            
            ps.setString(5,grupo.getResponsable());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public int Modificar(long id, Grupo grupo) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("update grupo set idcorteescolar=?,grado=?,descripcion=?,jornada=?,responsable=? where(idGrupo="+id+") ");            
            ps.setLong(1,grupo.getCorte_escolar().getId());
            ps.setString(2,grupo.getGrado());
            ps.setString(3,grupo.getDescripcion());
            ps.setString(4,grupo.getJornada());            
            ps.setString(5,grupo.getResponsable());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Grupo Buscar(long id) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql=" select idgrupo,idcorteescolar,grado,descripcion,jornada,responsable from grupo where(idGrupo="+id+")";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            Grupo a=new Grupo();
            if(rs.next()){                
                a.setId(rs.getLong(1));
                a.setCorte_escolar(new sae.dominio.corte.CorteEscolar(rs.getLong(2)));
                a.setGrado(rs.getString(3));
                a.setDescripcion(rs.getString(4));
                a.setJornada(rs.getString(5));
                a.setResponsable(rs.getString(6));
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
            PreparedStatement ps=con.prepareStatement("delete from grupo where(idgrupo="+id+")  ");                     
            System.out.println(id);
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public List<Grupo> Busqueda(String busqueda,long idcorte_academico) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql_add="";
       if(idcorte_academico!=0){
           sql_add=" idcorteescolar="+idcorte_academico+" and ";
       }
       String sql=" select idgrupo,idcorteescolar,grado,descripcion,jornada,responsable from grupo where( "+sql_add+"  (idgrupo like ? or descripcion like ? or grado like ? or jornada like ?) )order by idgrupo desc ";
       System.out.println(sql);
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,busqueda+"%");
            ps.setString(2,"%"+busqueda+"%");
            ps.setString(3,"%"+busqueda+"%");
            ps.setString(4,"%"+busqueda+"%");
            ResultSet rs=ps.executeQuery();            
            List<Grupo>lista=new ArrayList();
            while(rs.next()){                
                Grupo a=new Grupo();
                a.setId(rs.getLong(1));
                a.setCorte_escolar(new sae.dominio.corte.CorteEscolar(rs.getLong(2)));
                a.setGrado(rs.getString(3));
                a.setDescripcion(rs.getString(4));
                a.setJornada(rs.getString(5));
                a.setResponsable(rs.getString(6));
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
    public int AnexarCursoEnGrupo(long idgrupo, long idcurso) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into Grupo_has_curso (idgrupo,idcurso) values (?,?) ");            
            ps.setLong(1,idgrupo);
            ps.setLong(2,idcurso);            
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public int EliminarCursoEnGrupo(long idgrupo, long idcurso) {
         java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("delete from Grupo_has_curso where(idcurso="+idcurso+" and idgrupo="+idgrupo+") ");                                  
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public int CantidadDeIntegrantes(long idgrupo) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql =" select distinct       ";
              sql+=" count(estudiante.idestudiante) as cantidad ";
              sql+=" from    ";
              sql+=" grupo,estudiante,estudiante_has_grupo ";
              sql+=" where(  ";
              sql+=" grupo.idgrupo="+idgrupo+" and ";
              sql+=" grupo.idgrupo=estudiante_has_grupo.idgrupo and ";
              sql+=" estudiante.idestudiante=estudiante_has_grupo.idestudiante ";
              sql+=" ) ";
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();            
            if(rs.next()){                
                int a=rs.getInt(1);
                rs.close();
                ps.close();
                return a;
            }          
            return 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -99999;
        }finally{
            Pool.LiberarConexion(con);
        }       
    }
}
