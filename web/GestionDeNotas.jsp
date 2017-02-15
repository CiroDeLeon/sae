<%-- 
    Document   : GestionDeCohortes
    Created on : 16/01/2017, 08:37:48 PM
    Author     : Usuario1
--%>

<%@page import="sae.dominio.curso.Curso"%>
<%@page import="sae.dominio.curso.CursoService"%>
<%@page import="sae.dominio.curso.nota.Nota"%>
<%@page import="sae.dominio.curso.nota.NotaService"%>
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
           CursoService cs=new CursoService();
           long idcurso=0;
           if(request.getParameter("idcurso")!=null){
              idcurso=Long.parseLong(request.getParameter("idcurso"));
           }
           Curso curso=cs.getDao().Buscar(idcurso);
           NotaService service=new NotaService();
           Nota a=new Nota();
           int accion=0;
           boolean ingresando=true;
           long nota_seleccionada=0;
           if(request.getParameter("nota_seleccionada")!=null){
              nota_seleccionada=Long.parseLong(request.getParameter("nota_seleccionada").toString());                            
              Nota seleccionado=service.getDao().Buscar(nota_seleccionada);
              if(seleccionado!=null){
                 a=seleccionado;
              }
              a.setCurso(curso);
              accion=3;
              ingresando=false;              
           }else{
              accion=1; 
              if(request.getParameter("idnota")!=null){ 
                 long id=Long.parseLong(request.getParameter("idnota"));
                 a.setId(id);
              }else{
                 long id=0;
                 a.setId(id);
              }
              a.setDescripcion((""+request.getParameter("descripcion")).toString().replace("null",""));
              a.setCurso(curso);                
              a.setPeriodo((""+request.getParameter("periodo")).toString().replace("null",""));
           }
       %> 
        <div id="contenedor">
           <div id="cabecera">                               
           </div><!-- cabecera -->
           <div id="izquierda">                
           </div><!-- izquierda-->
           <div id="contenido">
               <h1 class="alineados">ANEXAR NOTAS A CURSO</h1> 
               <form method="post" action="GestionDeNotasServlet">                                      
                 <br><label class="jlabel">Curso</label><br>
                 <input type="hidden" id="idcurso" name="idcurso"  value="<%=a.getCurso().getId()%>" />                            
                 <input type="text" class="jtextfield" value="<%=a.getCurso().getId()%>" disabled />                            
                 <input type="text" class="jtextfield" value="<%=a.getCurso().toString()%>" disabled />                            
                 <br><br><h3 class="alineados">NOTA</h3>
                 <label class="jlabel">Codigo</label><br>
                 <input type="text" id="idnota" name="idnota" class="jtextfield" value="<%=a.getId()%>" disabled />                            
                 <br><label class="jlabel">Descripcion  </label><br>
                 <input type="text" id="descripcion" name="descripcion" class="jtextfield"  autofocus="true" value="<%=a.getDescripcion()%>" />                            
                 <br><label class="jlabel">Peso  </label><br>
                 <input type="text" id="peso" name="peso" class="jtextfield" value="<%=a.getPeso()%>" />              
                 <br><label class="jlabel">Periodo  </label><br>
                 <input type="text" id="periodo" name="periodo" class="jtextfield" value="<%=a.getPeriodo()%>" />              
                 
                 <input type="hidden" id="suscripcion" name="suscripcion" class="jtextfield" value="GestionDeNotas.jsp" />
                 <input type="hidden" id="nota_seleccionada" name="nota_seleccionada" class="jtextfield" value="<%=a.getId()%>" />
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
                 <br>
                 <br>
               </form>
               <% if(request.getParameter("mensaje")!=null){%>
               <label class="jlabel" id="mensaje"><%out.print(request.getParameter("mensaje"));%></label>                  
               <%}else{%>
               <label id="mensaje" class="jlabel"></label>
               <%}%>
           </div><!--contenido -->     
           <div id="derecha"></div>           
           <div id="pie">                                           
           </div><!-- pie -->
      </div><!--contenedor-->      
    </body>
</html>
