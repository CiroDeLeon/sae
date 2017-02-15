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
import sae.dominio.corte.CorteEscolar;
import sae.dominio.corte.CorteEscolarService;

/**
 *
 * @author Usuario1
 */
public class GestionDeCortesEscolaresServlet extends HttpServlet {

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
                case sae.util.Accion.ASIGNAR_CORTE_ACTUAL:this.AsignarCorteActual(request, response);break;    
            }            
        }
    }
protected CorteEscolar ConstruirCorteEscolar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
       

       CorteEscolar corte=new CorteEscolar();       
       corte.setDescripcion(request.getParameter("Descripcion"));
       corte.setMaxima_nota(Double.parseDouble(request.getParameter("MaximaNota")));
       corte.setMinima_nota_para_aprobar(Double.parseDouble(request.getParameter("MinimaNotaParaAprobar")));       
       corte.setActual(Boolean.parseBoolean(request.getParameter("Actual")));
       return corte; 
}
protected void Ingresar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
   sae.dominio.corte.CorteEscolarService service=new sae.dominio.corte.CorteEscolarService();
   CorteEscolar ce=this.ConstruirCorteEscolar(request, response);
   service.getDao().Persistir(ce);
   response.sendRedirect(request.getContextPath()+"/GestionDeCortesEscolares.jsp?mensaje=Ingresado Con Exito");
}
protected void Modificar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
   sae.dominio.corte.CorteEscolarService service =new sae.dominio.corte.CorteEscolarService();
        Enumeration<String> parameters=request.getParameterNames();
        String parametros="";
           while(parameters.hasMoreElements()){
               String key=parameters.nextElement();
               String value=request.getParameter(key);
               if(key.equals("accion")==false && key.equals("corte_escolar_seleccionado")==false){
                  parametros+="&"+key+"="+value;
               }
           }                                       
        CorteEscolar ce=this.ConstruirCorteEscolar(request, response);
        long id=Long.parseLong(request.getParameter("corte_escolar_seleccionado"));
        ce.setId(id);        
        parametros+="&corte_escolar_seleccionado="+id;

        service.getDao().Modificar(ce.getId(),ce);
        response.sendRedirect(request.getContextPath()+"/GestionDeCortesEscolares.jsp?mensaje=Modificado con Exito"+parametros);
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
       response.sendRedirect("BuscarCorteEscolar.jsp"+parametros);       
}
protected void Eliminar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    sae.dominio.corte.CorteEscolarService service=new sae.dominio.corte.CorteEscolarService();
    long id=Long.parseLong(request.getParameter("corte_escolar_seleccionado"));
    if(service.getDao().Eliminar(id)==1){
        response.sendRedirect(request.getContextPath()+"/GestionDeCortesEscolares.jsp?mensaje=Eliminado con Exito");
    }else{
        response.sendRedirect(request.getContextPath()+"/GestionDeCortesEscolares.jsp?mensaje=Error No se Pudo Eliminar");
    }
}
protected void AsignarCorteActual(HttpServletRequest request, HttpServletResponse response) throws IOException{
    Enumeration<String> parameters=request.getParameterNames();
        String parametros="";
           while(parameters.hasMoreElements()){
               String key=parameters.nextElement();
               String value=request.getParameter(key);
               if(key.equals("accion")==false && key.equals("corte_escolar_seleccionado")==false){
                  parametros+="&"+key+"="+value;
               }
           }                                               
        long id=Long.parseLong(request.getParameter("corte_escolar_seleccionado"));        
        parametros+="&corte_escolar_seleccionado="+id;     
        sae.dominio.corte.CorteEscolarService service=new sae.dominio.corte.CorteEscolarService();   
        if(service.AsignarVigente(id)==true){
           response.sendRedirect(request.getContextPath()+"/"+request.getParameter("suscripcion")+"?mensaje=asignado con exito"+parametros);
        }else{
           response.sendRedirect(request.getContextPath()+"/"+request.getParameter("suscripcion")+"?mensaje=Error no se pudo asignar"+parametros); 
        }
}
protected void Limpiar(HttpServletRequest request, HttpServletResponse response) throws IOException{
   response.sendRedirect(request.getContextPath()+"/"+request.getParameter("suscripcion"));
}
protected void BusquedaIncremental(HttpServletRequest request, HttpServletResponse response) throws IOException{
    CorteEscolarService service=new CorteEscolarService();
        String busqueda=(""+request.getParameter("busqueda")).replace("null","");                
        Enumeration<String> parameters=request.getParameterNames();
        String parametros="";
           while(parameters.hasMoreElements()){
               String key=parameters.nextElement();
               String value=request.getParameter(key);
               if(key.equals("accion")==false && key.equals("corte_escolar_seleccionado")==false){
                  parametros+="&"+key+"="+value;
               }
           }                                               
        Iterator<CorteEscolar> it=service.getDao().Busqueda(busqueda).iterator();
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
           CorteEscolar corte=it.next();    
           out.println("   <tr>");
           out.println("   <td class=\"jlabel\">"+corte.getId()+"</td>");
           out.println("   <td class=\"jlabel\">"+corte.getDescripcion()+"</td>");
           out.println("   <td class=\"jlabel\"><a "+disable+" href='"+suscripcion+"?corte_escolar_seleccionado="+corte.getId()+parametros+"'>seleccionar</a></td>");
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
