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
import sae.dominio.estudiante.Estudiante;
import sae.dominio.estudiante.EstudianteService;
import sae.dominio.exceptions.NonExistenceEntityException;
import sae.dominio.exceptions.RepeatEntityException;

/**
 *
 * @author Usuario1
 */
public class GestionDeEstudiantesServlet extends HttpServlet {

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
            throws ServletException, IOException{
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
                case sae.util.Accion.ANEXAR_ESTUDIANTE_A_GRUPO:this.AnexarEstudianteEngrupo(request,response);break;
                case sae.util.Accion.ELIMINAR_ESTUDIANTE_DE_GRUPO:this.EliminarEstudianteDeGrupo(request,response);break;    
            }            
        }
    }
    protected Estudiante ConstruirEstudiante(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
       long id=Long.parseLong(request.getParameter("id"));
       Estudiante a=new Estudiante();
       a.setId(id);
       a.setNombres(request.getParameter("nombres"));
       a.setApellidos(request.getParameter("apellidos"));
       a.setCorreo(request.getParameter("correo"));
       a.setClave(request.getParameter("clave"));
       a.setResponsable(request.getParameter("responsable"));
       a.setDireccion(request.getParameter("direccion"));
       a.setTelefono(request.getParameter("telefono"));
       return a; 
    }
    protected void Ingresar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {        
        sae.dominio.estudiante.EstudianteService service =new sae.dominio.estudiante.EstudianteService();
        Estudiante a=this.ConstruirEstudiante(request,response);
        try {
            service.Ingresar(a);                       
            response.sendRedirect(request.getContextPath()+"/GestionDeEstudiantes.jsp?mensaje=Ingresado con Exito");
        } catch (RepeatEntityException ex) {
            response.sendRedirect(request.getContextPath()+"/GestionDeEstudiantes.jsp?mensaje=No se pudo Ingresar Ya Existe");
            //Logger.getLogger(GestionDeActoresServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected void Modificar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        sae.dominio.estudiante.EstudianteService service =new sae.dominio.estudiante.EstudianteService();
        Enumeration<String> parameters=request.getParameterNames();
        String parametros="";
           while(parameters.hasMoreElements()){
               String key=parameters.nextElement();
               String value=request.getParameter(key);
               if(key.equals("accion")==false && key.equals("estudiante_seleccionado")==false){
                  parametros+="&"+key+"="+value;
               }
           }                                       
        Estudiante a=this.ConstruirEstudiante(request,response);
        parametros+="&estudiante_seleccionado="+a.getId();
        long id=Long.parseLong(request.getParameter("estudiante_seleccionado"));        
        try {                               
          service.Modificar(id,a);
          response.sendRedirect(request.getContextPath()+"/GestionDeEstudiantes.jsp?mensaje=Modificado con Exito"+parametros);
        }catch (RepeatEntityException ex) {
            response.sendRedirect(request.getContextPath()+"/GestionDeEstudiantes.jsp?mensaje=Error no se pudo modificar.Ese id Ya Existe"+parametros);
        }
    }
protected void Busqueda(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {       
           Enumeration<String> parameters=request.getParameterNames();
           String parametros="?";//?suscripcion="+request.getParameter("suscripcion");
           while(parameters.hasMoreElements()){
               String key=parameters.nextElement();
               String value=request.getParameter(key);
               if(key.equals("accion")==false){
                  parametros+="&"+key+"="+value;
               }
           }                    
       response.sendRedirect("BuscarEstudiante.jsp"+parametros);
    }
    protected void Eliminar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        EstudianteService service=new EstudianteService();
        long id=Long.parseLong(request.getParameter("id"));
        try {        
           if(id!=0){ 
              service.getDao().Eliminar(id);
              response.sendRedirect(request.getContextPath()+"/GestionDeEstudiantes.jsp?mensaje=Eliminado con Exito");
           }else{
              response.sendRedirect(request.getContextPath()+"/GestionDeEstudiantes.jsp?mensaje=Ese id No puede Ser Eliminado"); 
           }
        } catch (NonExistenceEntityException ex) {
           response.sendRedirect(request.getContextPath()+"/GestionDeEstudiantes.jsp?mensaje=No se pudo eliminar ese actor No Existe"); 
        }
   
    }
    protected void Limpiar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.sendRedirect(request.getContextPath()+"/"+request.getParameter("suscripcion"));
    }
    protected void BusquedaIncremental(HttpServletRequest request, HttpServletResponse response) throws IOException{
        EstudianteService service=new EstudianteService();
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
        Iterator<Estudiante> it=service.getDao().Busqueda(busqueda).iterator();
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
           Estudiante a=it.next();    
           out.println("   <tr>");
           out.println("   <td class=\"jlabel\">"+a.getId()+"</td>");
           out.println("   <td class=\"jlabel\">"+a.toString()+"</td>");
           out.println("   <td class=\"jlabel\"><a "+disable+" href='"+suscripcion+"?estudiante_seleccionado="+a.getId()+parametros+"'>seleccionar</a></td>");
           out.println("   </tr> ");           
        }
        out.println("</table>");        
    }
    public void AnexarEstudianteEngrupo(HttpServletRequest request, HttpServletResponse response)throws IOException{
        EstudianteService service=new EstudianteService();
        sae.dominio.grupo.estudiante_has_grupo.Estudiante_has_Grupo obj=new sae.dominio.grupo.estudiante_has_grupo.Estudiante_has_Grupo();
        long id=Long.parseLong(request.getParameter("idestudiante"));
        long idg=Long.parseLong(request.getParameter("idgrupo"));
        int result=service.getDao().AnexarEstudianteEnGrupo(id,idg);
        if(result>0){
            response.sendRedirect("ConfigurarEstudiantesDeGrupo.jsp?idgrupo="+idg+"&mensaje=Ingresado con Exito");
        }else{
            response.sendRedirect("ConfigurarEstudiantesDeGrupo.jsp?idgrupo="+idg+"&estudiante_seleccionado="+id+"&mensaje=No Se Pudo Ingresar");
        }        
    }
    public void EliminarEstudianteDeGrupo(HttpServletRequest request, HttpServletResponse response)throws IOException{
        EstudianteService service=new EstudianteService();
        sae.dominio.grupo.estudiante_has_grupo.Estudiante_has_Grupo obj=new sae.dominio.grupo.estudiante_has_grupo.Estudiante_has_Grupo();
        long id=Long.parseLong(request.getParameter("idestudiante"));
        long idg=Long.parseLong(request.getParameter("idgrupo"));
        int result=service.getDao().EliminarEstudianteDeGrupo(id, idg);
        if(result>0){
            response.sendRedirect("ConfigurarEstudiantesDeGrupo.jsp?idgrupo="+idg);
        }else{
            response.sendRedirect("ConfigurarEstudiantesDeGrupo.jsp?idgrupo="+idg+"&mensaje=No Se Pudo Eliminar al estudiante "+id);
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
