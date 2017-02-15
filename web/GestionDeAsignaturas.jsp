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
           AsignaturaService service=new AsignaturaService();
           Asignatura a=new Asignatura();
           int accion=0;
           boolean ingresando=true;
           long asignatura_seleccionada=0;
           if(request.getParameter("asignatura_seleccionada")!=null){
              asignatura_seleccionada=Long.parseLong(request.getParameter("asignatura_seleccionada").toString());                            
              Asignatura seleccionado=service.getDao().Buscar(asignatura_seleccionada);
              if(seleccionado!=null){
                 a=seleccionado;
              }
              accion=3;
              ingresando=false;              
           }else{
              accion=1; 
              if(request.getParameter("idasignatura")!=null){ 
                 long id=Long.parseLong(request.getParameter("idasignatura"));
                 a.setId(id);
              }else{
                 long id=0;
                 a.setId(id);
              }
              a.setDescripcion((""+request.getParameter("descripcion")).toString().replace("null",""));
              a.setCreditos(Integer.parseInt((""+request.getParameter("creditos")).toString().replace("null","0")));
              
           }
       %> 
        <div id="contenedor">
           <div id="cabecera">                               
           </div><!-- cabecera -->
           <div id="izquierda">                
           </div><!-- izquierda-->
           <div id="contenido">
               <h1 class="alineados">GESTION DE ASIGNATURAS</h1> 
               <form method="post" action="GestionDeAsignaturasServlet">                                      
                 <br><label class="jlabel">Codigo</label><br>
                 <input type="text" id="idasignatura" name="idasignatura" class="jtextfield" value="<%=a.getId()%>" disabled />                            
                 <br><label class="jlabel">Descripcion  </label><br>
                 <input type="text" id="descripcion" name="descripcion" class="jtextfield"  autofocus="true" value="<%=a.getDescripcion()%>" />                            
                 <br><label class="jlabel">Creditos  </label><br>
                 <input type="text" id="creditos" name="creditos" class="jtextfield" value="<%=a.getCreditos()%>" />              
                 <input type="hidden" id="suscripcion" name="suscripcion" class="jtextfield" value="GestionDeAsignaturas.jsp" />
                 <input type="hidden" id="asignatura_seleccionada" name="asignatura_seleccionada" class="jtextfield" value="<%=a.getId()%>" />
                 <br>      
                 <br>                 
                 <input type="hidden" id="accion" name="accion" value="<%=accion%>" />                 
                 <% if(ingresando){ %>
                 <input type="submit" value="Ingresar" onclick="AsignarAccion(1)"/>                 
                 <input type="submit" value="Busqueda" onclick="AsignarAccion(2)"/>
                 <input type="submit" disabled value="Modificar" onclick="AsignarAccion(3)"/>
                 <input type="submit" disabled value="Eliminar" onclick="AsignarAccion(4)"/>                            
                 <input type="submit" value="limpiar" onclick="AsignarAccion(5)"/>     
                 <% }else{ %>
                 <input type="submit" disabled value="Ingresar" onclick="AsignarAccion(1)"/>                 
                 <input type="submit" value="Busqueda" onclick="AsignarAccion(2)"/>
                 <input type="submit" value="Modificar" onclick="AsignarAccion(3)"/>
                 <input type="submit" value="Eliminar" onclick="AsignarAccion(4)"/>                            
                 <input type="submit" value="limpiar" onclick="AsignarAccion(5)"/>     
                 <%}%>                                            
               </form>
               <% if(request.getParameter("mensaje")!=null){%>
               <label class="jlabel" id="mensaje"><%out.print(request.getParameter("mensaje"));%></label>                  
               <%}else{%>
               <label id="mensaje" class="jlabel"></label>
               <%}%>
               <br>
               <br>
               <a href="MenuPrincipal.jsp">REGRESAR Menu Principal</a><br><br>               
           </div><!--contenido -->     
           <div id="derecha"></div>           
           <div id="pie">                                           
           </div><!-- pie -->
      </div><!--contenedor-->      
    </body>
</html>
