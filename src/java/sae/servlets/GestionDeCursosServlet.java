/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sae.dominio.actor.Actor;
import sae.dominio.asignatura.Asignatura;
import sae.dominio.corte.CorteEscolar;
import sae.dominio.curso.Curso;
import sae.dominio.curso.CursoService;
import sae.dominio.curso.horasemana.HoraSemana;
import sae.dominio.docente.Docente;
import sae.dominio.estudiante.Estudiante;
import sae.dominio.grupo.GrupoService;

/**
 *
 * @author Usuario1
 */
public class GestionDeCursosServlet extends HttpServlet {
/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            int accion=Integer.parseInt(request.getParameter("accion"));            
            switch(accion){
                case sae.util.Accion.INGRESAR  : this.Ingresar (request, response);break;
                case sae.util.Accion.BUSQUEDA  : this.Busqueda (request, response);break;    
                case sae.util.Accion.MODIFICAR : this.Modificar(request, response);break;        
                case sae.util.Accion.ELIMINAR  : this.Eliminar(request, response);break;    
                case sae.util.Accion.LIMPIAR : this.Limpiar(request, response);break;
                case sae.util.Accion.BUSQUEDA_INCREMENTAL:this.BusquedaIncremental(request, response);break;
                case sae.util.Accion.REDIRECCIONAR_BUSQUEDA_DE_ASIGNATURA:this.RedireccionarBusquedaDeAsignatura(request, response);break;
                case sae.util.Accion.REDIRECCIONAR_BUSQUEDA_DE_DOCENTE:this.RedireccionarBusquedaDeDocente(request, response);break;
                case sae.util.Accion.ANEXAR_CURSO_A_GRUPO:this.AnexarCursoAGrupo(request, response);break;
                case sae.util.Accion.ELIMINAR_CURSO_DE_GRUPO:this.EliminarCursoAGrupo(request, response);break;
                case sae.util.Accion.PETICION_AJAX_HORARIO:this.CargarHorario(request, response);break;
                case sae.util.Accion.ANEXAR_CURSO_A_ESTUDIANTE:AnexarCursoEnEstudiante(request,response);break;
                case sae.util.Accion.ELIMINAR_CURSO_DE_ESTUDIANTE:EliminarCursoDeEstudiante(request,response);break;
            }            
        }
    }
private void EliminarCursoDeEstudiante(HttpServletRequest request, HttpServletResponse response) throws IOException {
    long idusuario = 0;           
           HttpSession objSesion = request.getSession(false);            
           if(objSesion==null || objSesion.getAttribute("usuario")==null){
               response.sendRedirect(request.getContextPath()+"/index.jsp");               
           }else{
               idusuario = (Long)objSesion.getAttribute("usuario");           
           }       
   long idcurso=Long.parseLong(request.getParameter("idcurso"));
   long idestudiante=idusuario;
   CursoService cs=new CursoService();
   cs.getDao().EliminarEstudianteHasCurso(idestudiante, idcurso);
   response.sendRedirect("MatricularCursos.jsp");
}
protected void EliminarCursoAGrupo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
    long idcurso=Long.parseLong(request.getParameter("idcurso"));
    long idgrupo=Long.parseLong(request.getParameter("idgrupo"));
    GrupoService service=new GrupoService();
    service.getDao().EliminarCursoEnGrupo(idgrupo,idcurso);
    response.sendRedirect("ConfigurarCursosDeGrupo.jsp?idcurso="+idcurso+"&idgrupo="+idgrupo);
}      
protected void AnexarCursoAGrupo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
    long idcurso=Long.parseLong(request.getParameter("idcurso"));
    long idgrupo=Long.parseLong(request.getParameter("idgrupo"));
    GrupoService service=new GrupoService();
    CursoService cs=new CursoService();
    Curso c=cs.getDao().Buscar(idcurso);
    if(cs.PuedeAnexarCursoEnGrupo(idgrupo,c)){
       service.getDao().AnexarCursoEnGrupo(idgrupo,idcurso);
       response.sendRedirect("ConfigurarCursosDeGrupo.jsp?idcurso="+idcurso+"&idgrupo="+idgrupo);
    }else{
       response.sendRedirect("ConfigurarCursosDeGrupo.jsp?curso_seleccionado="+idcurso+"&idgrupo="+idgrupo+"&mensaje="+cs.getMensaje()); 
    }
}   
protected void RedireccionarBusquedaDeDocente(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
    String parametros="";        
        Enumeration<String> en=request.getParameterNames();
        while(en.hasMoreElements()){
            String key=en.nextElement();
            String value=request.getParameter(key);
            if(key.equals("accion")==false){
                parametros+="&"+key+"="+value;
            }
        }        
       response.sendRedirect("GestionDeDocentesServlet?accion="+sae.util.Accion.BUSQUEDA+parametros);
} 
protected void RedireccionarBusquedaDeAsignatura(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
       String parametros="";        
        Enumeration<String> en=request.getParameterNames();
        while(en.hasMoreElements()){
            String key=en.nextElement();
            String value=request.getParameter(key);
            if(key.equals("accion")==false){
                parametros+="&"+key+"="+value;
            }
        }        
       response.sendRedirect("GestionDeAsignaturasServlet?accion="+sae.util.Accion.BUSQUEDA+parametros);
}
protected Curso ConstruirCurso(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
       

       Curso curso=new Curso();       
       long idasignatura=Long.parseLong(request.getParameter("idasignatura"));
       curso.setAsignatura(new Asignatura(idasignatura));
       
       long iddocente=Long.parseLong(request.getParameter("iddocente"));
       curso.setDocente(new Docente(iddocente));
      
       long idactor=0;
       if(request.getParameter("idactor")!=null){
          idactor=Long.parseLong(request.getParameter("idactor"));
       }
       curso.setResponsable(new Actor(idactor));
       curso.setCorte(new sae.dominio.corte.CorteEscolarService().getDao().ObtenerCorteVigente());
       
       int cupo=Integer.parseInt(request.getParameter("cupo"));
       curso.setCupo(cupo);
       return curso; 
}
protected void Ingresar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
   CursoService service=new CursoService();
   Curso ce=this.ConstruirCurso(request, response);
   service.getDao().Persistir(ce);
   response.sendRedirect("GestionDeCursos.jsp?mensaje=Ingresado Con Exito");
}
protected void Modificar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
   CursoService service=new CursoService();
        Enumeration<String> parameters=request.getParameterNames();
        String parametros="";
           while(parameters.hasMoreElements()){
               String key=parameters.nextElement();
               String value=request.getParameter(key);
               if(key.equals("accion")==false && key.equals("curso_seleccionado")==false){
                  parametros+="&"+key+"="+value;
               }
           }                                       
        Curso ce=this.ConstruirCurso(request, response);
        long id=Long.parseLong(request.getParameter("curso_seleccionado"));
        ce.setId(id);        
        parametros+="&curso_seleccionado="+id;

        service.getDao().Modificar(ce.getId(),ce);
        response.sendRedirect("GestionDeCursos.jsp?mensaje=Modificado con Exito"+parametros);
}
protected void Busqueda(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {       
Enumeration<String> parameters=request.getParameterNames();
           String parametros="?suscripcion="+request.getParameter("suscripcion");
           while(parameters.hasMoreElements()){
               String key=parameters.nextElement();
               String value=request.getParameter(key);
               if(key.equals("accion")==false){
                  parametros+="&"+key+"="+value;
               }
           }                    
       response.sendRedirect("BuscarCurso.jsp"+parametros);       
}
protected void Eliminar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    CursoService service=new CursoService();
    long id=Long.parseLong(request.getParameter("curso_seleccionado"));
    if(service.getDao().Eliminar(id)==1){
        response.sendRedirect(request.getContextPath()+"/GestionDeAsignaturas.jsp?mensaje=Eliminado con Exito");
    }else{
        response.sendRedirect(request.getContextPath()+"/GestionDeAsignaturas.jsp?mensaje=Error No se Pudo Eliminar");
    }
}

protected void Limpiar(HttpServletRequest request, HttpServletResponse response) throws IOException{
   response.sendRedirect(request.getContextPath()+"/"+request.getParameter("suscripcion"));
}
protected void BusquedaIncremental(HttpServletRequest request, HttpServletResponse response) throws IOException{
    CursoService service=new CursoService();
        String busqueda=(""+request.getParameter("busqueda")).replace("null",""); 
        CorteEscolar corte=new sae.dominio.corte.CorteEscolarService().getDao().ObtenerCorteVigente();
        if(corte==null){
            corte=new CorteEscolar(0);
        }
        String parametros="";        
        Enumeration<String> en=request.getParameterNames();
        while(en.hasMoreElements()){
            String key=en.nextElement();
            String value=request.getParameter(key);
            if(key.equals("accion")==false && key.equals("suscripcion")==false && key.equals("busqueda")==false && key.equals("corte_escolar_seleccionado")==false && key.equals("curso_seleccionado")==false){
                parametros+="&"+key+"="+value;
            }
        }
        Iterator<Curso> it=service.getDao().Busqueda(busqueda,corte.getId()).iterator();
        PrintWriter out=response.getWriter();        
        String suscripcion="";
        boolean enable=false;
        String disable="";
        if(request.getParameter("suscripcion")!=null){
           suscripcion=request.getParameter("suscripcion").toString();
           enable=true;
        }else{
           disable="disabled"; 
        }        
           
           out.println("<table>");
           out.println("   <tr>");
           out.println("         <th class=\"jlabel \">CODIGO</th>");
           out.println("         <th class=\"jlabel \">DESCRIPCION</th>");
           out.println("         <th class=\"jlabel \">OP</th>");
           out.println("   </tr>");
        
        while(it.hasNext()){
           Curso curso=it.next();               
           out.println("   <tr>");
           out.println("   <td class=\"jlabel\">"+curso.getId()+"</td>");
           out.println("   <td class=\"jlabel\">"+curso.toString()+"</td>");           
           
           String seleccionar="<a "+disable+" href='"+suscripcion+"?curso_seleccionado="+curso.getId()+parametros+"' id='"+curso.getId()+"' onMouseOver='PeticionAjax2(this)'>seleccionar</a>";           
       
           
           out.println("   <td class=\"jlabel\">"+seleccionar+"</td>");
           out.println("   </tr> ");           
        }
        out.println("</table>");        

}
private void CargarHorario(HttpServletRequest request, HttpServletResponse response) throws IOException {
   long idcurso=Long.parseLong(request.getParameter("idcurso"));
   CursoService cs=new CursoService();
   Curso curso=cs.getDao().Buscar(idcurso);
   Iterator<HoraSemana> it=cs.getDao().ObtenerHorasDeCurso(idcurso).iterator();
   String rta="";
   PrintWriter out=response.getWriter();
   out.println("<h4 class='alineados'>"+curso.toString().toUpperCase()+" CODIGO No "+curso.getId()+"</h4>");
   out.println("<table border=1 class='jlabel'>");   
   out.println("<tr>");
   out.println("<th>Dia </th>");
   out.println("<th>Hora</th>");
   out.println("<th>Aula</th>");
   out.println("</tr>");
   while(it.hasNext()){
       HoraSemana hs=it.next();
       out.println("<tr>");
       out.println("<td>"+HoraSemana.getDiaEnLetras(hs.getDia())+"</td>");
       out.println("<td>"+hs.getHora()+" "+HoraSemana.getCuartoInicialEnMinutos(hs.getCuarto_inicial())+"-"+HoraSemana.getCuartoFinalEnMinutos(hs.getCuarto_final())+"</td>");
       out.println("<td>"+hs.getAula()+"</td>");
       out.println("</tr>");
   }
   out.println("</table>");
}
 private void AnexarCursoEnEstudiante(HttpServletRequest request, HttpServletResponse response) throws IOException {
           long idusuario = 0;           
           HttpSession objSesion = request.getSession(false);            
           if(objSesion==null || objSesion.getAttribute("usuario")==null){
               response.sendRedirect(request.getContextPath()+"/index.jsp");               
           }else{
               idusuario = (Long)objSesion.getAttribute("usuario");           
           }       
     CursoService cs=new CursoService();
     
     long idcurso=Long.parseLong(request.getParameter("idcurso"));
     long idcorte=new sae.dominio.corte.CorteEscolarService().getDao().ObtenerCorteVigente().getId();
     Curso c=new CursoService().getDao().Buscar(idcurso);
     Estudiante e=new Estudiante(idusuario);
     sae.dominio.curso.estudiante_has_curso.Estudiante_Has_Curso ehc=new sae.dominio.curso.estudiante_has_curso.Estudiante_Has_Curso();
     ehc.setCurso(c);
     ehc.setEstudiante(e);
     if(cs.PuedeMatricularCurso(idusuario,c)){
        cs.getDao().Persistir(ehc);
        response.sendRedirect("MatricularCursos.jsp");
     }else{
        response.sendRedirect("MatricularCursos.jsp?mensaje="+cs.getMensaje()+"&curso_seleccionado="+idcurso);
     }     
 }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    

  

    

}
