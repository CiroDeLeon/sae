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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sae.dominio.actor.Actor;
import sae.dominio.actor.ActorService;
import sae.dominio.exceptions.NonExistenceEntityException;
import sae.dominio.exceptions.RepeatEntityException;
import sae.util.Accion;

/**
 *
 * @author Usuario1
 */
public class GestionDeActoresServlet extends HttpServlet {

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
    protected Actor ConstruirActor(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
       long id=Long.parseLong(request.getParameter("id"));
       Actor a=new Actor();
       a.setId(id);
       a.setNombres(request.getParameter("nombres"));
       a.setApellidos(request.getParameter("apellidos"));
       a.setCorreo(request.getParameter("correo"));
       a.setClave(request.getParameter("clave"));
       a.setTipo(request.getParameter("tipo"));
       return a; 
    }
    protected void Ingresar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
        sae.dominio.actor.ActorService service =new sae.dominio.actor.ActorService();
        Actor a=this.ConstruirActor(request,response);
        try {
            service.Ingresar(a);                       
            response.sendRedirect(request.getContextPath()+"/GestionDeActores.jsp?mensaje=Ingresado con Exito");
        } catch (RepeatEntityException ex) {
            response.sendRedirect(request.getContextPath()+"/GestionDeActores.jsp?mensaje=No se pudo Ingresar Ya Existe");
            //Logger.getLogger(GestionDeActoresServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected void Modificar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        sae.dominio.actor.ActorService service =new sae.dominio.actor.ActorService();
        Enumeration<String> parameters=request.getParameterNames();
        String parametros="";
           while(parameters.hasMoreElements()){
               String key=parameters.nextElement();
               String value=request.getParameter(key);
               if(key.equals("accion")==false && key.equals("actor_seleccionado")==false){
                  parametros+="&"+key+"="+value;
               }
           }                                       
        Actor a=this.ConstruirActor(request,response);
        parametros+="&actor_seleccionado="+a.getId();
        long id=Long.parseLong(request.getParameter("actor_seleccionado"));        
        try {                               
          service.Modificar(id,a);
          response.sendRedirect(request.getContextPath()+"/GestionDeActores.jsp?mensaje=Modificado con Exito"+parametros);
        }catch (RepeatEntityException ex) {
            response.sendRedirect(request.getContextPath()+"/GestionDeActores.jsp?mensaje=Error no se pudo modificar.Ese id Ya Existe"+parametros);
        }
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
       response.sendRedirect("BuscarActor.jsp"+parametros);
    }
    protected void Eliminar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        ActorService service=new ActorService();
        long id=Long.parseLong(request.getParameter("id"));
        try {        
           if(id!=0){ 
              service.getDao().Eliminar(id);
              response.sendRedirect(request.getContextPath()+"/GestionDeActores.jsp?mensaje=Eliminado con Exito");
           }else{
              response.sendRedirect(request.getContextPath()+"/GestionDeActores.jsp?mensaje=Ese id No puede Ser Eliminado"); 
           }
        } catch (NonExistenceEntityException ex) {
           response.sendRedirect(request.getContextPath()+"/GestionDeActores.jsp?mensaje=No se pudo eliminar ese actor No Existe"); 
        }
   
    }
    protected void Limpiar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.sendRedirect(request.getContextPath()+"/"+request.getParameter("suscripcion"));
    }
    protected void BusquedaIncremental(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ActorService service=new ActorService();
        String busqueda=(""+request.getParameter("busqueda")).replace("null","");                
        String parametros="";
        Enumeration<String> en=request.getParameterNames();
        while(en.hasMoreElements()){
            String key=en.nextElement();
            String value=request.getParameter(key);
            if(key.equals("accion")==false && key.equals("suscripcion")==false && key.equals("busqueda")==false){
                parametros+="&"+key+"="+value;
            }
        }        
        Iterator<Actor> it=service.getDao().Busqueda(busqueda).iterator();
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
           out.println("         <th class=\"jlabel \">COD</th>");
           out.println("         <th class=\"jlabel \">DESCRIPCION</th>");
           out.println("         <th class=\"jlabel \">OP</th>");
           out.println("   </tr>");
        
        while(it.hasNext()){
           Actor a=it.next();    
           out.println("   <tr>");
           out.println("   <td class=\"jlabel\">"+a.getId()+"</td>");
           out.println("   <td class=\"jlabel\">"+a.toString()+"</td>");
           out.println("   <td class=\"jlabel\"><a "+disable+" href='"+suscripcion+"?actor_seleccionado="+a.getId()+parametros+"'>seleccionar</a></td>");
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
