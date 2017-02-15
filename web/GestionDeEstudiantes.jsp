<%-- 
    Document   : GestionDeActores
    Created on : 12/01/2017, 01:02:04 AM
    Author     : Usuario1
--%>

<%@page import="sae.dominio.estudiante.Estudiante"%>
<%@page import="sae.dominio.estudiante.EstudianteService"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
           EstudianteService service=new EstudianteService();
           Estudiante a=new Estudiante();
           int accion=0;
           boolean ingresando=true;
           long estudiante_seleccionado=0;
           if(request.getParameter("estudiante_seleccionado")!=null){
              estudiante_seleccionado=Long.parseLong(request.getParameter("estudiante_seleccionado").toString());                            
              Estudiante seleccionado=service.getDao().Buscar(estudiante_seleccionado);
              if(seleccionado!=null){
                 a=seleccionado;
              }
              accion=3;
              ingresando=false;              
           }else{
              accion=1; 
              if(request.getParameter("id")!=null){ 
                 long id=Long.parseLong(request.getParameter("id"));
                 a.setId(id);
              }else{
                 long id=0;
                 a.setId(id);
              }
              a.setNombres((""+request.getParameter("nombres")).toString().replace("null",""));
              a.setApellidos((""+request.getParameter("apellidos")).toString().replace("null",""));
              a.setCorreo((""+request.getParameter("correo")).toString().replace("null",""));
              a.setClave((""+request.getParameter("clave")).toString().replace("null",""));
              a.setResponsable((""+request.getParameter("responsable")).toString().replace("null",""));
              a.setDireccion((""+request.getParameter("direccion")).toString().replace("null",""));
              a.setTelefono((""+request.getParameter("telefono")).toString().replace("null",""));
           }
       %> 
       <div id="contenedor">
           <div id="cabecera">                                
           </div><!-- cabecera -->
           
           <div id="izquierda">                
           </div>
           
           <div id="contenido">
               <h1 class="alineados">Gestion De Estudiantes</h1> 
               <form method="post" action="GestionDeEstudiantesServlet">                                      
                 <br><label class="jlabel">Cedula  </label><br>
                 <input type="hidden" id="estudiante_seleccionado" name="estudiante_seleccionado"  value="<%=estudiante_seleccionado%>"/>
                 <input type="text" id="id" name="id" class="jtextfield" autofocus="true" value="<%=a.getId()%>"/>          
                 <br><label class="jlabel">Nombres  </label><br>
                 <input type="text" id="nombres" name="nombres" class="jtextfield" value="<%=a.getNombres()%>"/>                                                     
                 <br><label class="jlabel">Apellidos  </label><br>
                 <input type="text" id="apellidos" name="apellidos" class="jtextfield" value="<%=a.getApellidos()%>" />           
                 <br><label class="jlabel">Correo  </label><br>
                 <input type="text" id="correo" name="correo" class="jtextfield" value="<%=a.getCorreo()%>"/>              
                 <br><label class="jlabel">Clave  </label><br>
                 <input type="text" id="clave" name="clave" class="jtextfield" value="<%=a.getClave()%>"/>               
                 <br><label class="jlabel">Direccion  </label> <br>                  
                 <input type="text" id="direccion" name="direccion" class="jtextfield" value="<%=a.getDireccion()%>"/>               
                 <br><label class="jlabel">Telefono  </label> <br>                  
                 <input type="text" id="telefono" name="telefono" class="jtextfield" value="<%=a.getTelefono()%>"/>               
                 <br>      
                 <br>
                 <input type="hidden" id="suscrpcion" name="suscripcion" value="GestionDeEstudiantes.jsp"/>
                 <input type="hidden" id="accion" name="accion" value="<%=accion%>"/>
                 <% if(ingresando){ %>
                    <input type="submit" value="Ingresar" onclick="AsignarAccion(1)"/>                 
                    <input type="submit" value="Busqueda" onclick="AsignarAccion(2)"/>
                    <input type="submit" disabled value="Modificar" onclick="AsignarAccion(3)"/>
                    <input type="submit" disabled value="Eliminar" onclick="AsignarAccion(4)"/>                            
                    <input type="submit" value="limpiar" onclick="AsignarAccion(5)"/>
                 <% }else{ %>
                    <input type="submit" disabled value="Ingresar" onclick="AsignarAccion(1)"/>                 
                    <input type="submit" value="Busqueda" onclick="AsignarAccion(2)"/>
                    <input type="submit"  value="Modificar" onclick="AsignarAccion(3)"/>
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
