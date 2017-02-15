<%-- 
    Document   : GestionDeCohortes
    Created on : 16/01/2017, 08:37:48 PM
    Author     : Usuario1
--%>

<%@page import="sae.dominio.asignatura.Asignatura"%>
<%@page import="sae.dominio.asignatura.AsignaturaService"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="estilos/estilos.css">
        <script type="text/javascript" src="js/sae.js"></script>        
    </head>
    <body>
        <% 
           
       %> 
        <div id="contenedor">
           <div id="cabecera">                               
           </div><!-- cabecera -->
           <div id="izquierda">                
           </div><!-- izquierda-->
           <div id="contenido">
               <h1 class="alineados">INGRESO AL SISTEMA</h1>           
               <br>
               <br>
               <form method="POST" action="LoginServlet">
                   <label class="jlabel">Nit/Cedula </label>
                   <input type="text" class="jtextfield" id="idusuario" name="idusuario"/>
                   <label class="jlabel">Contraseña </label>
                   <input type="password" class="jtextfield" id="password" name="password"/>
                   <br>
                   <br>
                   <input type="submit" value="Ingresar" onclick="AsignarAccion(14)"/>                 
                   <input type="submit" value="limpiar" onclick="AsignarAccion(5)"/>     
                   <input type="hidden" id="accion" name="accion" value="0" />                             
               </form>
               <br>
               <br>
               <% if(request.getParameter("mensaje")!=null){%>
               <label class="jlabel" id="mensaje"><%out.print(request.getParameter("mensaje"));%></label>                  
               <%}else{%>
               <label id="mensaje" class="jlabel"></label>
               <%}%>
               <br>
               <br>
           </div><!--contenido -->     
           <div id="derecha"></div>           
           <div id="pie">                                           
           </div><!-- pie -->
      </div><!--contenedor-->      
    </body>
</html>
