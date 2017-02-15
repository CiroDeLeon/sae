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
import sae.dominio.curso.estudiante_has_curso.Estudiante_Has_Curso;
import sae.dominio.curso.horasemana.HoraSemana;
import sae.dominio.docente.Docente;
import sae.infraestructura.jdbc.Pool;

/**
 *
 * @author Usuario1
 */
public class CursoDAO implements sae.dominio.curso.ICursoDAO{

    @Override
    public int Persistir(Curso curso) {
               java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into curso (idasignatura,idresponsable,idcorteescolar,iddocente,cupo) values (?,?,?,?,?) ");            
            ps.setLong(1,curso.getAsignatura().getId());
            ps.setLong(2,curso.getResponsable().getId());
            ps.setLong(3,curso.getCorte().getId());
            ps.setLong(4,curso.getDocente().getId());
            ps.setInt(5,curso.getCupo());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public int Modificar(long id, Curso curso) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("update curso set idcurso="+id+",idasignatura=?,idresponsable=?,idcorteescolar=?,iddocente=?,cupo=? where(idcurso="+id+") ");            
            ps.setLong(1,curso.getAsignatura().getId());
            ps.setLong(2,curso.getResponsable().getId());
            ps.setLong(3,curso.getCorte().getId());
            ps.setLong(4,curso.getDocente().getId());
            ps.setInt(5,curso.getCupo());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public Curso Buscar(long id) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql =" select ";
              sql+=" curso.idcurso, ";            
              sql+=" curso.idasignatura, ";            
              sql+=" curso.idresponsable, ";            
              sql+=" curso.idcorteescolar, ";            
              sql+=" curso.iddocente, ";            
              sql+=" curso.cupo,  ";            
              sql+=" asignatura.idAsignatura, ";            
              sql+=" asignatura.Descripcion,  ";            
              sql+=" asignatura.Creditos, ";            
              sql+=" docente.idDocente, ";            
              sql+=" docente.Nombres, ";            
              sql+=" docente.Apellidos  ";            
              sql+=" from ";            
              sql+=" curso,asignatura,docente  ";            
              sql+=" where( ";            
              sql+="    curso.idcurso="+id+" and  ";
              sql+="    curso.iddocente=docente.iddocente and  ";            
              sql+="    curso.idasignatura=asignatura.idasignatura  ";                          
              sql+=" ) ";            
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            Curso a=new Curso();
            if(rs.next()){                
                a.setId(rs.getLong(1));
                a.setAsignatura(new sae.dominio.asignatura.Asignatura(rs.getLong(2)));
                a.setResponsable(new sae.dominio.actor.Actor(rs.getLong(3)));
                a.setCorte(new sae.dominio.corte.CorteEscolar(rs.getLong(4)));
                a.setDocente(new sae.dominio.docente.Docente(rs.getLong(5)));
                a.setCupo(rs.getInt(6));
                Asignatura b=new Asignatura();
                b.setId(rs.getLong(7));
                b.setDescripcion(rs.getString(8));
                b.setCreditos(rs.getInt(9));
                a.setAsignatura(b);
                Docente c=new Docente();
                c.setId(rs.getLong(10));
                c.setNombres(rs.getString(11));
                c.setApellidos(rs.getString(12));
                a.setDocente(c);                
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
            PreparedStatement ps=con.prepareStatement("delete from curso where(idcurso="+id+")  ");                                 
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
    public List<Curso> Busqueda(String busqueda,long id_corte_escolar) {
         java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();              
       String sql =" select ";
              sql+=" curso.idcurso, ";            
              sql+=" curso.idasignatura, ";            
              sql+=" curso.idresponsable, ";            
              sql+=" curso.idcorteescolar, ";            
              sql+=" curso.iddocente, ";            
              sql+=" curso.cupo,  ";            
              sql+=" asignatura.idAsignatura, ";            
              sql+=" asignatura.Descripcion,  ";            
              sql+=" asignatura.Creditos, ";            
              sql+=" docente.idDocente, ";            
              sql+=" docente.Nombres, ";            
              sql+=" docente.Apellidos  ";            
              sql+=" from ";            
              sql+=" curso,asignatura,docente  ";            
              sql+=" where( ";            
              sql+="    curso.idcorteescolar="+id_corte_escolar+" and  ";
              sql+="    curso.iddocente=docente.iddocente and  ";            
              sql+="    curso.idasignatura=asignatura.idasignatura and ";            
              sql+="    ( ";            
              sql+="        curso.idcurso like ? or ";
              sql+="        docente.iddocente like ? or ";            
              sql+="        asignatura.descripcion like ? or ";
              sql+="        docente.nombres like ?  or ";            
              sql+="        docente.apellidos like ? or ";
              sql+="        concat_ws(' ',docente.Nombres,docente.Apellidos) like ?  ";            
              sql+="     )";
              sql+=" )order by idcurso ";            
              
              
        //System.out.println(sql);
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,busqueda+"%");
            ps.setString(2,busqueda+"%");
            ps.setString(3,"%"+busqueda+"%");           
            ps.setString(4,"%"+busqueda+"%");           
            ps.setString(5,"%"+busqueda+"%");           
            ps.setString(6,"%"+busqueda+"%");           
            ResultSet rs=ps.executeQuery();            
            List<Curso>lista=new ArrayList();
            while(rs.next()){   
                Curso a=new Curso();
                a.setId(rs.getLong(1));
                a.setAsignatura(new sae.dominio.asignatura.Asignatura(rs.getLong(2)));
                a.setResponsable(new sae.dominio.actor.Actor(rs.getLong(3)));
                a.setCorte(new sae.dominio.corte.CorteEscolar(rs.getLong(4)));
                a.setDocente(new sae.dominio.docente.Docente(rs.getLong(5)));
                a.setCupo(rs.getInt(6));
                Asignatura b=new Asignatura();
                b.setId(rs.getLong(7));
                b.setDescripcion(rs.getString(8));
                b.setCreditos(rs.getInt(9));
                a.setAsignatura(b);
                Docente c=new Docente();
                c.setId(rs.getLong(10));
                c.setNombres(rs.getString(11));
                c.setApellidos(rs.getString(12));
                a.setDocente(c);
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
    public List<Curso> Listar(long id_grupo) {
         java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();              
       String sql =" select distinct ";
              sql+=" curso.idcurso, ";            
              sql+=" curso.idasignatura, ";            
              sql+=" curso.idresponsable, ";            
              sql+=" curso.idcorteescolar, ";            
              sql+=" curso.iddocente, ";            
              sql+=" curso.cupo,  ";            
              sql+=" asignatura.idAsignatura, ";            
              sql+=" asignatura.Descripcion,  ";            
              sql+=" asignatura.Creditos, ";            
              sql+=" docente.idDocente, ";            
              sql+=" docente.Nombres, ";            
              sql+=" docente.Apellidos  ";            
              sql+=" from ";            
              sql+=" curso,asignatura,docente,grupo_has_curso  ";            
              sql+=" where( ";            
              sql+="    curso.idcurso=grupo_has_curso.idcurso and  ";
              sql+="    grupo_has_curso.idgrupo="+id_grupo+" and  ";
              sql+="    curso.iddocente=docente.iddocente and  ";            
              sql+="    curso.idasignatura=asignatura.idasignatura ";            
              sql+=" )order by idcurso ";            
              
              
        //System.out.println(sql);
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);            
            ResultSet rs=ps.executeQuery();            
            List<Curso>lista=new ArrayList();
            while(rs.next()){   
                Curso a=new Curso();
                a.setId(rs.getLong(1));
                a.setAsignatura(new sae.dominio.asignatura.Asignatura(rs.getLong(2)));
                a.setResponsable(new sae.dominio.actor.Actor(rs.getLong(3)));
                a.setCorte(new sae.dominio.corte.CorteEscolar(rs.getLong(4)));
                a.setDocente(new sae.dominio.docente.Docente(rs.getLong(5)));
                a.setCupo(rs.getInt(6));
                Asignatura b=new Asignatura();
                b.setId(rs.getLong(7));
                b.setDescripcion(rs.getString(8));
                b.setCreditos(rs.getInt(9));
                a.setAsignatura(b);
                Docente c=new Docente();
                c.setId(rs.getLong(10));
                c.setNombres(rs.getString(11));
                c.setApellidos(rs.getString(12));
                a.setDocente(c);
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
    public int PersistirHoraSemana(HoraSemana hora_semana) {
        java.sql.Connection  con=null;
        con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into hora_semana (idcurso,dia,hora,cuartodehorainicial,cuartodehorafinal,aula) values (?,?,?,?,?,?) ");            
            ps.setLong(1,hora_semana.getCurso().getId());
            ps.setInt(2,hora_semana.getDia());
            ps.setInt(3,hora_semana.getHora());
            ps.setInt(4,hora_semana.getCuarto_inicial());
            ps.setInt(5,hora_semana.getCuarto_final());
            ps.setInt(6,hora_semana.getAula());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public HoraSemana BuscarHoraSemana(long id_hora_semana) {
       java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql =" select idhorasemana,idcurso,dia,hora,cuartodehorainicial,cuartodehorafinal,aula from hora_semana where(idhorasemana="+id_hora_semana+")";              
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            HoraSemana a=new HoraSemana();
            if(rs.next()){                
                a.setId(rs.getLong(1));
                a.setCurso(new Curso(rs.getLong(2)));
                a.setDia(rs.getInt(3));
                a.setHora(rs.getInt(4));                
                a.setCuarto_inicial(rs.getInt(5));
                a.setCuarto_final(rs.getInt(6));
                a.setAula(rs.getInt(7));
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
    public int ModificarHoraSemana(long id, HoraSemana hora_semana) {
        java.sql.Connection  con=null;
        con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("update hora_semana set idcurso=?,dia=?,hora=?,cuartodehorainicial=?,cuartodehorafinal=?,aula=? where(idhorasemana="+id+") ");            
            ps.setLong(1,hora_semana.getCurso().getId());
            ps.setInt(2,hora_semana.getDia());
            ps.setInt(3,hora_semana.getHora());
            ps.setInt(4,hora_semana.getCuarto_inicial());
            ps.setInt(5,hora_semana.getCuarto_final());
            ps.setInt(6,hora_semana.getAula());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public List<HoraSemana> ObtenerHorasDeCurso(long idcurso) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql =" select hora_semana.idhorasemana,hora_semana.idcurso,hora_semana.dia,hora_semana.hora,hora_semana.cuartodehorainicial,hora_semana.cuartodehorafinal,hora_semana.aula,curso.iddocente,curso.idasignatura,curso.cupo from hora_semana,curso where( curso.idcurso=hora_semana.idcurso and curso.idcurso="+idcurso+" )";              
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            List<sae.dominio.curso.horasemana.HoraSemana> lista=new ArrayList();
            while(rs.next()){                
                HoraSemana a=new HoraSemana();
                Curso c=new Curso(rs.getLong(2));
                a.setId(rs.getLong(1));                
                a.setDia(rs.getInt(3));
                a.setHora(rs.getInt(4));                
                a.setCuarto_inicial(rs.getInt(5));
                a.setCuarto_final(rs.getInt(6));
                a.setAula(rs.getInt(7));
                c.setDocente(new Docente(rs.getLong(8)));
                c.setAsignatura(new Asignatura(rs.getLong(9)));
                c.setCupo(rs.getInt(10));
                a.setCurso(c);
                lista.add(a);                
            }    
            rs.close();
            ps.close();
            return lista;
       
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return new ArrayList<sae.dominio.curso.horasemana.HoraSemana>();
        }finally{
            Pool.LiberarConexion(con);
        }       
    }

    @Override
    public int EliminarHoraSemana(long id) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("delete from hora_semana where(idhorasemana="+id+")  ");                                 
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
    public List<HoraSemana> ObtenerHoras(long idcorte) {
        String sql =" select ";
               sql+=" hora_semana.idHoraSemana,";
               sql+=" hora_semana.idCurso, ";
               sql+=" hora_semana.Dia, ";
               sql+=" hora_semana.hora, ";
               sql+=" hora_semana.CuartoDeHoraInicial, ";
               sql+=" hora_semana.CuartoDeHoraFinal, ";
               sql+=" hora_semana.aula,  ";
               sql+=" curso.idDocente, ";
               sql+=" curso.idAsignatura, ";
               sql+=" curso.cupo ";
               sql+=" from ";
               sql+=" curso,hora_semana ";
               sql+=" where( ";
               sql+="   curso.idCurso=hora_semana.idCurso and   ";
               sql+="   curso.idCorteEscolar="+idcorte+"  ";
               sql+=" )";
               java.sql.Connection  con=null;
               con=Pool.ObtenerConexion();
               PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            List<sae.dominio.curso.horasemana.HoraSemana> lista=new ArrayList();
            while(rs.next()){                
                HoraSemana a=new HoraSemana();
                Curso c=new Curso();
                a.setId(rs.getLong(1));                                                
                c.setId(rs.getLong(2));
                a.setDia(rs.getInt(3));
                a.setHora(rs.getInt(4));                
                a.setCuarto_inicial(rs.getInt(5));
                a.setCuarto_final(rs.getInt(6));
                a.setAula(rs.getInt(7));                
                c.setDocente(new Docente(rs.getLong(8)));
                c.setAsignatura(new Asignatura(rs.getLong(9)));
                c.setCupo(rs.getInt(10));
                a.setCurso(c);
                lista.add(a);                
            }    
            rs.close();
            ps.close();
            return lista;       
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return new ArrayList<sae.dominio.curso.horasemana.HoraSemana>();
        }finally{
            Pool.LiberarConexion(con);
        }       
    }

    @Override
    public int Persistir(Estudiante_Has_Curso ehc) {
        java.sql.Connection  con=null;
        con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("insert into estudiante_has_curso (idestudiante,idcurso) values (?,?) ");                    
            ps.setLong(1,ehc.getEstudiante().getId());
            ps.setLong(2,ehc.getCurso().getId());
            int result=ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }finally{
            Pool.LiberarConexion(con);
        }
    }

    @Override
    public List<Curso> ObtenerCursos(long idestudiante, long idcohorte) {
        java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();              
       String sql =" select distinct ";
              sql+=" curso.idcurso, ";            
              sql+=" curso.idasignatura, ";            
              sql+=" curso.idresponsable, ";            
              sql+=" curso.idcorteescolar, ";            
              sql+=" curso.iddocente, ";            
              sql+=" curso.cupo,  ";            
              sql+=" asignatura.idAsignatura, ";            
              sql+=" asignatura.Descripcion,  ";            
              sql+=" asignatura.Creditos, ";            
              sql+=" docente.idDocente, ";            
              sql+=" docente.Nombres, ";            
              sql+=" docente.Apellidos  ";            
              sql+=" from ";            
              sql+=" curso,estudiante_has_curso,estudiante,asignatura,docente  ";            
              sql+=" where( ";            
              sql+=" curso.idCurso=estudiante_has_curso.idCurso and  ";
              sql+=" curso.idAsignatura=asignatura.idAsignatura and  ";
              sql+=" estudiante.idEstudiante=estudiante_has_curso.idEstudiante  and ";            
              sql+=" docente.idDocente=curso.idDocente and ";
              sql+=" curso.idCorteEscolar="+idcohorte+" and ";
              sql+=" estudiante.idEstudiante="+idestudiante+" ";            
              sql+=" )order by idcurso ";            
              
              
        //System.out.println(sql);
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);            
            ResultSet rs=ps.executeQuery();            
            List<Curso>lista=new ArrayList();
            while(rs.next()){   
                Curso a=new Curso();
                a.setId(rs.getLong(1));
                a.setAsignatura(new sae.dominio.asignatura.Asignatura(rs.getLong(2)));
                a.setResponsable(new sae.dominio.actor.Actor(rs.getLong(3)));
                a.setCorte(new sae.dominio.corte.CorteEscolar(rs.getLong(4)));
                a.setDocente(new sae.dominio.docente.Docente(rs.getLong(5)));
                a.setCupo(rs.getInt(6));
                Asignatura b=new Asignatura();
                b.setId(rs.getLong(7));
                b.setDescripcion(rs.getString(8));
                b.setCreditos(rs.getInt(9));
                a.setAsignatura(b);
                Docente c=new Docente();
                c.setId(rs.getLong(10));
                c.setNombres(rs.getString(11));
                c.setApellidos(rs.getString(12));
                a.setDocente(c);
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
    public int EliminarEstudianteHasCurso(long idestudiante, long idcurso) {
         java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
        try {
            PreparedStatement ps=con.prepareStatement("delete from estudiante_has_curso where(idestudiante="+idestudiante+" and idcurso="+idcurso+")  ");                                 
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
    public List<HoraSemana> ObtenerHoras(long idcorte, long idestudiante) {
         String sql =" select ";
               sql+=" hora_semana.idHoraSemana,";
               sql+=" hora_semana.idCurso, ";
               sql+=" hora_semana.Dia, ";
               sql+=" hora_semana.hora, ";
               sql+=" hora_semana.CuartoDeHoraInicial, ";
               sql+=" hora_semana.CuartoDeHoraFinal, ";
               sql+=" hora_semana.aula,  ";
               sql+=" curso.idDocente, ";
               sql+=" curso.idAsignatura, ";
               sql+=" curso.cupo ";
               sql+=" from ";
               sql+=" curso,hora_semana,estudiante_has_curso,estudiante ";
               sql+=" where( ";
               sql+="   curso.idCurso=hora_semana.idCurso and   ";
               sql+="   curso.idCorteEscolar="+idcorte+" and ";
               sql+="   estudiante.idestudiante=estudiante_has_curso.idestudiante and ";
               sql+="   curso.idcurso=estudiante_has_curso.idcurso and ";
               sql+="   estudiante.idestudiante="+idestudiante+" ";
               sql+=" )";
               java.sql.Connection  con=null;
               con=Pool.ObtenerConexion();
               PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            List<sae.dominio.curso.horasemana.HoraSemana> lista=new ArrayList();
            while(rs.next()){                
                HoraSemana a=new HoraSemana();
                Curso c=new Curso();
                a.setId(rs.getLong(1));                                                
                c.setId(rs.getLong(2));
                a.setDia(rs.getInt(3));
                a.setHora(rs.getInt(4));                
                a.setCuarto_inicial(rs.getInt(5));
                a.setCuarto_final(rs.getInt(6));
                a.setAula(rs.getInt(7));                
                c.setDocente(new Docente(rs.getLong(8)));
                c.setAsignatura(new Asignatura(rs.getLong(9)));
                c.setCupo(rs.getInt(10));
                a.setCurso(c);
                lista.add(a);                
            }    
            rs.close();
            ps.close();
            return lista;       
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return new ArrayList<sae.dominio.curso.horasemana.HoraSemana>();
        }finally{
            Pool.LiberarConexion(con);
        }     
    }

    @Override
    public int ObtenerNumeroDeEstudiantesMatriculados(long idcurso) {
         java.sql.Connection  con=null;
       con=Pool.ObtenerConexion();
       String sql =" select distinct";              
              sql+=" count(estudiante.idestudiante)  ";            
              sql+=" from ";            
              sql+=" curso,estudiante,estudiante_has_curso  ";            
              sql+=" where( ";            
              sql+="    curso.idcurso="+idcurso+" and  ";
              sql+="    curso.idcurso=estudiante_has_curso.idcurso and  ";            
              sql+="    estudiante.idestudiante=estudiante_has_curso.idestudiante  ";                          
              sql+=" ) ";            
       PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            Curso a=new Curso();
            if(rs.next()){        
                int r=rs.getInt(1);
                rs.close();
                ps.close();
                return r;
            }    
            return 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 999999;
        }finally{
            Pool.LiberarConexion(con);
        }       
    }

    @Override
    public List<HoraSemana> ObtenerHorasDeGrupo(long idgrupo) {
        String sql =" select distinct ";
               sql+=" hora_semana.idHoraSemana,";
               sql+=" hora_semana.idCurso, ";
               sql+=" hora_semana.Dia, ";
               sql+=" hora_semana.hora, ";
               sql+=" hora_semana.CuartoDeHoraInicial, ";
               sql+=" hora_semana.CuartoDeHoraFinal, ";
               sql+=" hora_semana.aula,  ";
               sql+=" curso.idDocente, ";
               sql+=" curso.idAsignatura, ";
               sql+=" curso.cupo ";
               sql+=" from ";
               sql+=" curso,hora_semana,grupo_has_curso,grupo ";
               sql+=" where( ";
               sql+="   curso.idCurso=hora_semana.idCurso and   ";               
               sql+="   grupo.idgrupo=grupo_has_curso.idgrupo and ";
               sql+="   curso.idcurso=grupo_has_curso.idcurso and ";
               sql+="   grupo.idgrupo="+idgrupo+" ";
               sql+=" ) ";
               java.sql.Connection  con=null;
               con=Pool.ObtenerConexion();
               PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            List<sae.dominio.curso.horasemana.HoraSemana> lista=new ArrayList();
            while(rs.next()){                
                HoraSemana a=new HoraSemana();
                Curso c=new Curso();
                a.setId(rs.getLong(1));                                                
                c.setId(rs.getLong(2));
                a.setDia(rs.getInt(3));
                a.setHora(rs.getInt(4));                
                a.setCuarto_inicial(rs.getInt(5));
                a.setCuarto_final(rs.getInt(6));
                a.setAula(rs.getInt(7));                
                c.setDocente(new Docente(rs.getLong(8)));
                c.setAsignatura(new Asignatura(rs.getLong(9)));
                c.setCupo(rs.getInt(10));
                a.setCurso(c);
                lista.add(a);                
            }    
            rs.close();
            ps.close();
            return lista;       
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return new ArrayList<sae.dominio.curso.horasemana.HoraSemana>();
        }finally{
            Pool.LiberarConexion(con);
        }     
    }

    
    
}
