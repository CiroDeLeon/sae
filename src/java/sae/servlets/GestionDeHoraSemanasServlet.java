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
import sae.dominio.actor.Actor;
import sae.dominio.asignatura.Asignatura;
import sae.dominio.corte.CorteEscolar;
import sae.dominio.corte.CorteEscolarService;
import sae.dominio.curso.Curso;
import sae.dominio.curso.CursoService;
import sae.dominio.curso.horasemana.HoraSemana;
import sae.dominio.docente.Docente;


/**
 *
 * @author Usuario1
 */
public class GestionDeHoraSemanasServlet extends HttpServlet {
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
            }            
        }
    }


protected HoraSemana ConstruirHoraSemana(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
       

       HoraSemana hora_semana=new HoraSemana();              
       long idcurso=0;
       if(request.getParameter("idcurso")!=null){
          idcurso=Long.parseLong(request.getParameter("idcurso"));
       }
       long idhorasemana=0;
       if(request.getParameter("idhorasemana")!=null){
          idhorasemana=Long.parseLong(request.getParameter("idhorasemana"));
       }
       int dia=0;
       if(request.getParameter("dia")!=null){
          dia=Integer.parseInt(request.getParameter("dia"));
       }
       int hora=0;
       if(request.getParameter("hora")!=null){
          hora=Integer.parseInt(request.getParameter("hora"));
       }
       int cuarto_inicial=0;
       if(request.getParameter("cuartoinicial")!=null){
          cuarto_inicial=Integer.parseInt(request.getParameter("cuartoinicial"));
       }
       int cuarto_final=0;
       if(request.getParameter("cuartofinal")!=null){
          cuarto_final=Integer.parseInt(request.getParameter("cuartofinal"));
       }
       int aula=0;
       if(request.getParameter("aula")!=null){
          aula=Integer.parseInt(request.getParameter("aula"));
       }
       hora_semana.setCurso(new Curso(idcurso));
       hora_semana.setDia(dia);
       hora_semana.setHora(hora);
       hora_semana.setCuarto_inicial(cuarto_inicial);
       hora_semana.setCuarto_final(cuarto_final);
       hora_semana.setAula(aula);
       return hora_semana; 
}
protected void Ingresar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
   CursoService service=new CursoService();
   HoraSemana hora=this.ConstruirHoraSemana(request, response);
   long idcurso=0;
   if(request.getParameter("idcurso")!=null){
     idcurso=Long.parseLong(request.getParameter("idcurso"));           
   }
   CorteEscolar ce=new CorteEscolarService().getDao().ObtenerCorteVigente();
   if(service.PuedeAsignarHoraSemanaEnCorte(hora,idcurso,ce.getId())){
      int op=service.getDao().PersistirHoraSemana(hora);
      if(op==1){
         response.sendRedirect("GestionDeHoraSemanas.jsp?mensaje="+service.getMensaje()+"&idcurso="+idcurso); 
      }else{
         response.sendRedirect("GestionDeHoraSemanas.jsp?mensaje=No se Pudo Ingresar ERROR&idcurso="+idcurso);  
      }
   }else{
      response.sendRedirect("GestionDeHoraSemanas.jsp?mensaje="+service.getMensaje()+"&idcurso="+idcurso);   
   }
   
   
}
protected void Modificar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
   CursoService service=new CursoService();
        Enumeration<String> parameters=request.getParameterNames();
        String parametros="";
           while(parameters.hasMoreElements()){
               String key=parameters.nextElement();
               String value=request.getParameter(key);
               if(key.equals("accion")==false && key.equals("horasemana_seleccionada")==false){
                  parametros+="&"+key+"="+value;
               }
           }                                       
        HoraSemana ce=this.ConstruirHoraSemana(request, response);
        long id=Long.parseLong(request.getParameter("horasemana_seleccionada"));
        ce.setId(id);        
        parametros+="&horasemana_seleccionada="+id;

        service.getDao().ModificarHoraSemana(ce.getId(),ce);
        response.sendRedirect("GestionDeHoraSemanas.jsp?mensaje=Modificado con Exito"+parametros);
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
       response.sendRedirect("BuscarHoraSemana.jsp"+parametros);       
}
protected void Eliminar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    CursoService service=new CursoService();
    long id=Long.parseLong(request.getParameter("horasemana_seleccionada"));
    long idcurso=0;
   if(request.getParameter("idcurso")!=null){
     idcurso=Long.parseLong(request.getParameter("idcurso"));           
   }
    if(service.getDao().EliminarHoraSemana(id)==1){
        response.sendRedirect(request.getContextPath()+"/GestionDeHoraSemanas.jsp?mensaje=Eliminado con Exito&idcurso="+idcurso);
    }else{
        response.sendRedirect(request.getContextPath()+"/GestionDeHoraSemanas.jsp?mensaje=Error No se Pudo Eliminar&idcurso="+idcurso);
    }
}

protected void Limpiar(HttpServletRequest request, HttpServletResponse response) throws IOException{
    long idcurso=0;
   if(request.getParameter("idcurso")!=null){
     idcurso=Long.parseLong(request.getParameter("idcurso"));           
   }
   response.sendRedirect(request.getContextPath()+"/"+request.getParameter("suscripcion")+"?idcurso="+idcurso);
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
            if(key.equals("accion")==false && key.equals("suscripcion")==false && key.equals("busqueda")==false && key.equals("corte_escolar_seleccionado")==false && key.equals("horasemana_seleccionada")==false){
                parametros+="&"+key+"="+value;
            }
        }
       long idcurso=0;
       if(request.getParameter("idcurso")!=null){
          idcurso=Long.parseLong(request.getParameter("idcurso"));
       }
        Iterator<HoraSemana> it=service.getDao().ObtenerHorasDeCurso(idcurso).iterator();
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
           out.println("         <th class=\"jlabel \">DESCRIPCION</th>");
           out.println("         <th class=\"jlabel \">AULA</th>");
           out.println("         <th class=\"jlabel \">OP</th>");
           out.println("   </tr>");
        
        while(it.hasNext()){
           HoraSemana horasemana=it.next();    
           out.println("   <tr>");           
           out.println("   <td class=\"jlabel\">"+horasemana.toString()+"</td>");
           out.println("   <td class=\"jlabel\">"+horasemana.getAula()+"</td>");
           out.println("   <td class=\"jlabel\"><a "+disable+" href='"+suscripcion+"?horasemana_seleccionada="+horasemana.getId()+parametros+"'>seleccionar</a></td>");
           out.println("   </tr> ");           
        }
        out.println("</table>");        

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
