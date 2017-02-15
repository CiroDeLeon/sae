<%-- 
    Document   : GestionDeCohortes
    Created on : 16/01/2017, 08:37:48 PM
    Author     : Usuario1
--%>

<%@page import="sae.dominio.corte.CorteEscolar"%>
<%@page import="sae.dominio.corte.CorteEscolarService"%>
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
           CorteEscolarService service=new CorteEscolarService();
           CorteEscolar a=new CorteEscolar();
           int accion=0;
           boolean ingresando=true;
           long actor_seleccionado=0;
           if(request.getParameter("corte_escolar_seleccionado")!=null){
              actor_seleccionado=Long.parseLong(request.getParameter("corte_escolar_seleccionado").toString());                            
              CorteEscolar seleccionado=service.getDao().Buscar(actor_seleccionado);
              if(seleccionado!=null){
                 a=seleccionado;
              }
              accion=3;
              ingresando=false;              
           }else{
              accion=1; 
              if(request.getParameter("idCorteEscolar")!=null){ 
                 long id=Long.parseLong(request.getParameter("idCorteEscolar"));
                 a.setId(id);
              }else{
                 long id=0;
                 a.setId(id);
              }
              a.setDescripcion((""+request.getParameter("Descripcion")).toString().replace("null",""));
              a.setMaxima_nota(Double.parseDouble((""+request.getParameter("MaximaNota")).toString().replace("null","0")));
              a.setMinima_nota_para_aprobar(Double.parseDouble((""+request.getParameter("MinimaNotaParaAprobar")).toString().replace("null","0")));
              a.setResponsable((""+request.getParameter("Responsable")).toString().replace("null",""));
              a.setActual(Boolean.parseBoolean((""+request.getParameter("Actual")).toString().replace("null","false")));
           }
       %> 
        <div id="contenedor">
           <div id="cabecera">                               
           </div><!-- cabecera -->
           <div id="izquierda">                
           </div><!-- izquierda-->
           <div id="contenido">
               <h1 class="alineados">GESTION DE CORTES ACADEMICOS</h1> 
               <form method="post" action="GestionDeCortesEscolaresServlet">                                      
                 <br><label class="jlabel">Codigo</label><br>
                 <input type="text" id="idCorteEscolar" name="idCorteEscolar" class="jtextfield" value="<%=a.getId()%>" disabled />                            
                 <br><label class="jlabel">Descripcion  </label><br>
                 <input type="text" id="Descripcion" name="Descripcion" class="jtextfield"  autofocus="true" value="<%=a.getDescripcion()%>" />                            
                 <br><label class="jlabel">Maxima Nota  </label><br>
                 <input type="text" id="MaximaNota" name="MaximaNota" class="jtextfield" value="<%=a.getMaxima_nota()%>" />
                 <br><label class="jlabel">Minima Nota Para Aprobacion  </label><br>
                 <input type="text" id="MinimaNotaParaAprobar" name="MinimaNotaParaAprobar" class="jtextfield" value="<%=a.getMinima_nota_para_aprobar()%>" />
                 <input type="hidden" id="Responsable" name="Responsable" class="jtextfield" value="<%=a.getResponsable()%>"/>
                 <input type="hidden" id="Actual" name="Actual" class="jtextfield" value="<%=a.isActual()%>" />
                 <input type="hidden" id="corte_escolar_seleccionado" name="corte_escolar_seleccionado" class="jtextfield" value="<%=a.getId()%>" />
                 <input type="hidden" id="suscripcion" name="suscripcion" class="jtextfield" value="GestionDeCortesEscolares.jsp" />
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
