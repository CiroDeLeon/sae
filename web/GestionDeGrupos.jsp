<%-- 
    Document   : GestionDeCohortes
    Created on : 16/01/2017, 08:37:48 PM
    Author     : Usuario1
--%>

<%@page import="sae.dominio.corte.CorteEscolar"%>
<%@page import="sae.dominio.corte.CorteEscolarService"%>
<%@page import="sae.dominio.grupo.Grupo"%>
<%@page import="sae.dominio.grupo.GrupoService"%>

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
           GrupoService service=new GrupoService();
           Grupo a=new Grupo();
           int accion=0;
           boolean ingresando=true;
           long grupo_seleccionado=0;
           if(request.getParameter("grupo_seleccionado")!=null){
              grupo_seleccionado=Long.parseLong(request.getParameter("grupo_seleccionado").toString());                            
              Grupo seleccionado=service.getDao().Buscar(grupo_seleccionado);
              CorteEscolarService ce_service=new CorteEscolarService();
              CorteEscolar ce=ce_service.getDao().Buscar(seleccionado.getCorte_escolar().getId());              
              if(seleccionado!=null){
                 a=seleccionado;
                 if(ce!=null){
                    a.setCorte_escolar(ce);
                 }
              }
              accion=3;
              ingresando=false;              
           }else{
              accion=1; 
              if(request.getParameter("idgrupo")!=null){ 
                 long id=Long.parseLong(request.getParameter("idgrupo"));
                 a.setId(id);
              }else{
                 long id=0;
                 a.setId(id);
                 CorteEscolarService ce_service=new CorteEscolarService();
                 CorteEscolar ce=ce_service.getDao().ObtenerCorteVigente();
                 if(ce==null){
                     ce=new CorteEscolar(0);
                 }
                 a.setCorte_escolar(ce);
              }
              a.setDescripcion((""+request.getParameter("descripcion")).toString().replace("null",""));
              a.setGrado((""+request.getParameter("grado")).toString().replace("null",""));
              a.setJornada((""+request.getParameter("jornada")).toString().replace("null",""));
              a.setResponsable((""+request.getParameter("responsable")).toString().replace("null",""));
           }
       %> 
        <div id="contenedor">
           <div id="cabecera">                               
           </div><!-- cabecera -->
           <div id="izquierda">                
           </div><!-- izquierda-->
           <div id="contenido">
               <h1 class="alineados">GESTION DE GRUPOS</h1> 
               <form method="post" action="GestionDeGruposServlet">                                      
                 <br><label class="jlabel">Codigo</label><br>
                 <input type="text" id="idgrupo" name="idgrupo" class="jtextfield" value="<%=a.getId()%>" disabled />                            
                 <br><label class="jlabel">Descripcion  </label><br>
                 <input type="text" id="descripcion" name="descripcion" class="jtextfield"  autofocus="true" value="<%=a.getDescripcion()%>" />                            
                 <br><label class="jlabel">Grado  </label><br>
                 <input type="text" id="grado" name="grado" class="jtextfield" value="<%=a.getGrado()%>" />
                 <br><label class="jlabel">Corte Escolar  </label><br>
                 <input type="text" id="corte_escolar" name="corte_escolar" class="jtextfield" value="<%=a.getCorte_escolar().getDescripcion()%>" disabled="disabled" />                 
                 <input type="hidden" id="corte_escolar_seleccionado" name="corte_escolar_seleccionado" class="jtextfield" value="<%=a.getCorte_escolar().getId()%>"  />
                 <br><label class="jlabel">Jornada  </label><br>
                 <input type="text" id="jornada" name="jornada" class="jtextfield" value="<%=a.getJornada()%>" />                 
                 <input type="hidden" id="responsable" name="responsable" class="jtextfield" value="<%=a.getResponsable()%>"/>                 
                 <input type="hidden" id="grupo_seleccionado" name="grupo_seleccionado" class="jtextfield" value="<%=a.getId()%>" />
                 <input type="hidden" id="suscripcion" name="suscripcion" class="jtextfield" value="GestionDeGrupos.jsp" />
                 <br>      
                 <br>                 
                 <input type="hidden" id="accion" name="accion" value="<%=accion%>" />                 
                 <% if(ingresando){ %>
                 <input type="submit" value="Ingresar" onclick="AsignarAccion(1)"/>                 
                 <input type="submit" value="Busqueda" onclick="AsignarAccion(2)"/>
                 <input type="submit" disabled value="Modificar" onclick="AsignarAccion(3)"/>
                 <input type="submit" disabled value="Eliminar" onclick="AsignarAccion(4)"/>                                             
                 <input type="submit" value="limpiar" onclick="AsignarAccion(5)"/>     
                 <br>
                 <br>                
                 <% }else{ %>
                 <input type="submit" disabled value="Ingresar" onclick="AsignarAccion(1)"/>                 
                 <input type="submit" value="Busqueda" onclick="AsignarAccion(2)"/>
                 <input type="submit" value="Modificar" onclick="AsignarAccion(3)"/>
                 <input type="submit" value="Eliminar" onclick="AsignarAccion(4)"/>                            
                 <input type="submit" value="limpiar" onclick="AsignarAccion(5)"/>     
                 <br>
                 <br>
                 <a href="ConfigurarEstudiantesDeGrupo.jsp?idgrupo=<%=a.getId()%>">anexar estudiantes</a><br>
                 <a href="ConfigurarCursosDeGrupo.jsp?idgrupo=<%=a.getId()%>">anexar cursos</a><br>
                 <%}%>                                  
               </form>
               <% if(request.getParameter("mensaje")!=null){%>
               <label class="jlabel" id="mensaje"><%=request.getParameter("mensaje")%></label>                  
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
