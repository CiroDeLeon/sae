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
import javax.servlet.http.HttpSession;
import sae.dominio.actor.Actor;
import sae.dominio.actor.ActorService;
import sae.dominio.asignatura.Asignatura;
import sae.dominio.asignatura.AsignaturaService;
import sae.dominio.docente.Docente;
import sae.dominio.docente.DocenteService;
import sae.dominio.estudiante.Estudiante;
import sae.dominio.estudiante.EstudianteService;
import sae.dominio.exceptions.NonExistenceEntityException;

/**
 *
 * @author Usuario1
 */
public class LoginServlet extends HttpServlet {
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
                case sae.util.Accion.LOGIN  : this.HacerLogin(request, response);break;     
                case sae.util.Accion.LIMPIAR :response.sendRedirect(request.getContextPath()+"/LoginServlet.jsp");
                case sae.util.Accion.CERRAR_SESSION:this.CerrarSession(request, response);break;
            }            
        }
    }
   public void CerrarSession(HttpServletRequest request, HttpServletResponse response) throws IOException{
       HttpSession sesion = request.getSession(true);        
       //Cerrar sesion
       sesion.invalidate();        
       //Redirecciono a index.jsp
       response.sendRedirect("index.jsp");
   }
           
   public void HacerLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
       long idusuario=Long.parseLong(request.getParameter("idusuario"));
       String clave=request.getParameter("password");
       boolean esEstudiante=false;
       boolean esDocente=false;
       boolean esDirector=false;
       boolean esAuxiliar=false;
       
       EstudianteService es=new EstudianteService();
       try {
           Estudiante e=es.Buscar(idusuario);           
           if(e.getClave().equals(clave)==true){
              esEstudiante=true;
           }
       } catch (NonExistenceEntityException ex) {
           
       }
       DocenteService ds=new DocenteService();
       try {
           Docente d=ds.Buscar(idusuario);
           if(d.getClave().equals(clave)==true){
               esDocente=true;
           }
       } catch (NonExistenceEntityException ex) {
           
       }
       ActorService as=new ActorService();
       try {
           Actor a=as.Buscar(idusuario);
           if(a.getClave().equals(clave)==true){
              if(a.getTipo().equals("Director")){
                  esDirector=true;               
              }else{
                  esAuxiliar=true;
              }
           }
       } catch (NonExistenceEntityException ex) {
           
       }
       
       if(esEstudiante || esDocente || esDirector || esAuxiliar){
          HttpSession objSesion = request.getSession(true);
          objSesion.setAttribute("usuario",idusuario);
          objSesion.setAttribute("esEstudiante",esEstudiante);
          objSesion.setAttribute("esDocente",esDocente);
          objSesion.setAttribute("esDirector",esDirector);
          objSesion.setAttribute("esAuxiliar",esAuxiliar);
          response.sendRedirect("MenuPrincipal.jsp");
       }else{
          response.sendRedirect("index.jsp?mensaje=Login Erroneo "+esEstudiante+" "+esDocente+" "+esDirector+" "+esAuxiliar+idusuario);
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
