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
import sae.dominio.asignatura.Asignatura;
import sae.dominio.asignatura.AsignaturaService;


/**
 *
 * @author Usuario1
 */
public class GestionDeAsignaturasServlet extends HttpServlet {
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
protected Asignatura ConstruirAsignatura(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
       

       Asignatura corte=new Asignatura();       
       corte.setDescripcion(request.getParameter("descripcion"));
       corte.setCreditos(Integer.parseInt(request.getParameter("creditos")));
       
       return corte; 
}
protected void Ingresar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
   AsignaturaService service=new AsignaturaService();
   Asignatura ce=this.ConstruirAsignatura(request, response);
   service.getDao().Persistir(ce);
   response.sendRedirect("GestionDeAsignaturas.jsp?mensaje=Ingresado Con Exito");
}
protected void Modificar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
   AsignaturaService service=new AsignaturaService();
        Enumeration<String> parameters=request.getParameterNames();
        String parametros="";
           while(parameters.hasMoreElements()){
               String key=parameters.nextElement();
               String value=request.getParameter(key);
               if(key.equals("accion")==false && key.equals("asignatura_seleccionada")==false){
                  parametros+="&"+key+"="+value;
               }
           }                                       
        Asignatura ce=this.ConstruirAsignatura(request, response);
        long id=Long.parseLong(request.getParameter("asignatura_seleccionada"));
        ce.setId(id);        
        parametros+="&asignatura_seleccionada="+id;

        service.getDao().Modificar(ce.getId(),ce);
        response.sendRedirect("GestionDeAsignaturas.jsp?mensaje=Modificado con Exito"+parametros);
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
       response.sendRedirect("BuscarAsignatura.jsp"+parametros);       
}
protected void Eliminar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    AsignaturaService service=new AsignaturaService();
    long id=Long.parseLong(request.getParameter("asignatura_seleccionada"));
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
    AsignaturaService service=new AsignaturaService();
        String busqueda=(""+request.getParameter("busqueda")).replace("null","");                
        String parametros="";        
        Enumeration<String> en=request.getParameterNames();
        while(en.hasMoreElements()){
            String key=en.nextElement();
            String value=request.getParameter(key);
            if(key.equals("accion")==false && key.equals("suscripcion")==false && key.equals("busqueda")==false && key.equals("corte_escolar_seleccionado")==false && key.equals("asignatura_seleccionada")==false){
                parametros+="&"+key+"="+value;
            }
        }
        Iterator<Asignatura> it=service.getDao().Busqueda(busqueda).iterator();
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
           Asignatura corte=it.next();    
           out.println("   <tr>");
           out.println("   <td class=\"jlabel\">"+corte.getId()+"</td>");
           out.println("   <td class=\"jlabel\">"+corte.getDescripcion()+"</td>");
           out.println("   <td class=\"jlabel\"><a "+disable+" href='"+suscripcion+"?asignatura_seleccionada="+corte.getId()+parametros+"'>seleccionar</a></td>");
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
