<%-- 
    Document   : GestionDeActores
    Created on : 12/01/2017, 01:02:04 AM
    Author     : Usuario1
--%>

<%@page import="sae.dominio.actor.Actor"%>
<%@page import="sae.dominio.actor.ActorService"%>
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
           ActorService service=new ActorService();
           Actor a=new Actor();
           int accion=0;
           boolean ingresando=true;
           long actor_seleccionado=0;
           if(request.getParameter("actor_seleccionado")!=null){
              actor_seleccionado=Long.parseLong(request.getParameter("actor_seleccionado").toString());                            
              Actor seleccionado=service.getDao().Buscar(actor_seleccionado);
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
              a.setTipo((""+request.getParameter("tipo")).toString().replace("null",""));
           }
       %> 
       <div id="contenedor">
           <div id="cabecera">                                
           </div><!-- cabecera -->
           
           <div id="izquierda">                
           </div>
           
           <div id="contenido">
               <h1 class="alineados">Gestion De Actores</h1> 
               <form method="post" action="GestionDeActoresServlet">                                      
                 <br><label class="jlabel">Cedula  </label><br>
                 <input type="hidden" id="actor_seleccionado" name="actor_seleccionado"  value="<%=actor_seleccionado%>"/>
                 <input type="text" id="id" name="id" class="jtextfield" value="<%=a.getId()%>"/>          
                 <br><label class="jlabel">Nombres  </label><br>
                 <input type="text" id="nombres" name="nombres" class="jtextfield" value="<%=a.getNombres()%>"/>                                                     
                 <br><label class="jlabel">Apellidos  </label><br>
                 <input type="text" id="apellidos" name="apellidos" class="jtextfield" value="<%=a.getApellidos()%>" />           
                 <br><label class="jlabel">Correo  </label><br>
                 <input type="text" id="correo" name="correo" class="jtextfield" value="<%=a.getCorreo()%>"/>              
                 <br><label class="jlabel">Clave  </label><br>
                 <input type="text" id="clave" name="clave" class="jtextfield" value="<%=a.getClave()%>"/>               
                 <br><label class="jlabel">Privilegios  </label> <br>                  
                 <select id="tipo" name="tipo" class="jtextfield" value="<%=a.getTipo()%>">
                    <option <%if(a.getTipo().equals("Director")) out.print("selected");; %>>Director</option>
                    <option <%if(a.getTipo().equals("Auxiliar")) out.print("selected");; %>>Auxiliar</option>
                 </select>           
                 <br>      
                 <br>
                 <input type="hidden" id="suscrpcion" name="suscripcion" value="GestionDeActores.jsp"/>
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
