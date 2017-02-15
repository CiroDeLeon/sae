<%-- 
    Document   : GestionDeCohortes
    Created on : 16/01/2017, 08:37:48 PM
    Author     : Usuario1
--%>

<%@page import="sae.dominio.asignatura.Asignatura"%>
<%@page import="sae.dominio.asignatura.AsignaturaService"%>
 <% 
           long idusuario = 0;
           boolean esEstudiante=false;
           boolean esDocente=false;
           boolean esDirector=false;
           boolean esAuxiliar=false; 
           HttpSession objSesion = request.getSession(false);            
           if(objSesion==null || objSesion.getAttribute("usuario")==null){
               response.sendRedirect(request.getContextPath()+"/index.jsp");               
           }else{
               idusuario = (Long)objSesion.getAttribute("usuario");
               esEstudiante=(Boolean)objSesion.getAttribute("esEstudiante");
               esDocente=(Boolean)objSesion.getAttribute("esDocente");
               esDirector=(Boolean)objSesion.getAttribute("esDirector");
               esAuxiliar=(Boolean)objSesion.getAttribute("esAuxiliar"); 
           }          
       %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="estilos/estilos.css">
        <script type="text/javascript" src="js/sae.js"></script>        
    </head>
    <body>       
        <div id="contenedor">
           <div id="cabecera">                               
           </div><!-- cabecera -->
           <div id="izquierda">                
           </div><!-- izquierda-->
           <div id="contenido">
               <h1 class="alineados">MENU PRINCIPAL</h1>           
               <br>                        
               <br>
               <%
               if(esDirector){
               %>               
               <a href="GestionDeEstudiantes.jsp" class="menu">Gestion de Estudiantes</a><br><br>
               <a href="GestionDeDocentes.jsp" class="menu">Gestion de Docentes</a><br><br>
               <a href="GestionDeAsignaturas.jsp" class="menu">Gestion de Asignaturas</a><br><br>
               <a href="GestionDeGrupos.jsp" class="menu">Gestion de Grupos</a><br><br>
               <a href="GestionDeCursos.jsp" class="menu">Gestion de Cursos</a><br><br>
               <a href="GestionDeCortesEscolares.jsp" class="menu">Gestion de Cortes Academicos</a><br><br>
               <a href="AsignarCorteEscolarActual.jsp" class="menu">Asignar Corte Academico Actual</a><br><br>
               <a href="GestionDeActores.jsp" class="menu">Gestion de Actores</a><br><br>
               <a href="LoginServlet?accion=16" class="menu">SALIR</a><br><br>
               <%
               }   
               %>    
               
                 <%
               if(esAuxiliar){
               %>               
               <a href="GestionDeEstudiantes.jsp" class="menu">Gestion de Estudiantes</a><br><br>
               <a href="GestionDeDocentes.jsp" class="menu">Gestion de Docentes</a><br><br>
               <a href="GestionDeAsignaturas.jsp" class="menu">Gestion de Asignaturas</a><br><br>
               <a href="GestionDeGrupos.jsp" class="menu">Gestion de Grupos</a><br><br>
               <a href="GestionDeCursos.jsp" class="menu">Gestion de Cursos</a><br><br>               
               <a href="LoginServlet?accion=16" class="menu">SALIR</a><br><br>
               <%
               }   
               %>    
               <%
               if(esEstudiante){
               %>
               <a href="MatricularCursos.jsp" class="menu">Matricular Cursos</a><br><br>               
               <%}%>
               
           </div><!--contenido -->     
           <div id="derecha"></div>           
           <div id="pie">                                           
           </div><!-- pie -->
      </div><!--contenedor-->      
    </body>
</html>
