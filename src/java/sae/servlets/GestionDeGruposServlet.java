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
import sae.dominio.grupo.Grupo;

/**
 *
 * @author Usuario1
 */
public class GestionDeGruposServlet extends HttpServlet {
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
protected Grupo ConstruirGrupo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {              
       Grupo grupo=new Grupo();       
       grupo.setDescripcion(request.getParameter("descripcion"));
       grupo.setGrado(request.getParameter("grado"));
       grupo.setJornada(request.getParameter("jornada"));
       grupo.setResponsable(request.getParameter("responsable"));
       long idcorte=Long.parseLong(request.getParameter("corte_escolar_seleccionado"));
       grupo.setCorte_escolar(new sae.dominio.corte.CorteEscolar(idcorte));
       return grupo; 
}
protected void Ingresar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
   sae.dominio.grupo.GrupoService service=new sae.dominio.grupo.GrupoService();
   Grupo ce=this.ConstruirGrupo(request, response);
   service.getDao().Persistir(ce);
   response.sendRedirect(request.getContextPath()+"/GestionDeGrupos.jsp?mensaje=Ingresado Con Exito");
}
protected void Modificar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
   sae.dominio.grupo.GrupoService service =new sae.dominio.grupo.GrupoService();
        Enumeration<String> parameters=request.getParameterNames();
        String parametros="";
           while(parameters.hasMoreElements()){
               String key=parameters.nextElement();
               String value=request.getParameter(key);
               if(key.equals("accion")==false && key.equals("grupo_seleccionado")==false){
                  parametros+="&"+key+"="+value;
               }
           }                                       
        Grupo ce=this.ConstruirGrupo(request, response);
        long id=Long.parseLong(request.getParameter("grupo_seleccionado"));
        ce.setId(id);        
        parametros+="&grupo_seleccionado="+id;

        service.getDao().Modificar(ce.getId(),ce);
        response.sendRedirect(request.getContextPath()+"/GestionDeGrupos.jsp?mensaje=Modificado con Exito"+parametros);
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
       response.sendRedirect("BuscarGrupo.jsp"+parametros);       
}
protected void Eliminar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    sae.dominio.grupo.GrupoService service=new sae.dominio.grupo.GrupoService();
    long id=Long.parseLong(request.getParameter("grupo_seleccionado"));
    if(service.getDao().Eliminar(id)==1){
        response.sendRedirect(request.getContextPath()+"/GestionDeGrupos.jsp?mensaje=Eliminado con Exito");
    }else{
        response.sendRedirect(request.getContextPath()+"/GestionDeGrupos.jsp?mensaje=Error No se Pudo Eliminar");
    }
}

protected void Limpiar(HttpServletRequest request, HttpServletResponse response) throws IOException{
   response.sendRedirect(request.getContextPath()+"/"+request.getParameter("suscripcion"));
}
protected void BusquedaIncremental(HttpServletRequest request, HttpServletResponse response) throws IOException{
    sae.dominio.grupo.GrupoService service=new sae.dominio.grupo.GrupoService();
        String busqueda=(""+request.getParameter("busqueda")).replace("null","");                
        String parametros="";        
        Enumeration<String> en=request.getParameterNames();
        while(en.hasMoreElements()){
            String key=en.nextElement();
            String value=request.getParameter(key);
            if(key.equals("accion")==false && key.equals("suscripcion")==false && key.equals("busqueda")==false && key.equals("corte_escolar_seleccionado")==false && key.equals("grupo_seleccionado")==false){
                parametros+="&"+key+"="+value;
            }
        }
        sae.dominio.corte.CorteEscolar ce=new sae.dominio.corte.CorteEscolarService().getDao().ObtenerCorteVigente();        
        long id=0;
        if(ce!=null){
            id=ce.getId();
        }
        Iterator<Grupo> it=service.getDao().Busqueda(busqueda,id).iterator();
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
           Grupo grupo=it.next();    
           out.println("   <tr>");
           out.println("   <td class=\"jlabel\">"+grupo.getId()+"</td>");
           out.println("   <td class=\"jlabel\">"+grupo.getDescripcion()+"</td>");
           out.println("   <td class=\"jlabel\"><a "+disable+" href='"+suscripcion+"?grupo_seleccionado="+grupo.getId()+parametros+"'>seleccionar</a></td>");
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
