<%-- 
    Document   : BuscarActor
    Created on : 15/01/2017, 08:49:49 AM
    Author     : Usuario1
--%>
<%@page import="java.util.Iterator"%>
<%@page import="sae.dominio.curso.CursoService"%>
<%@page import="sae.dominio.curso.Curso"%>
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



<%@page import="java.util.Enumeration"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="estilos/estilos.css">
        <script type="text/javascript" src="js/sae.js"></script>
        <script type="text/javascript" src="js/BuscarAsignatura.js"></script>
    </head>
    <body onload="PeticionAjax()">
        <%                     
                    Enumeration<String> parameters=request.getParameterNames();
                    String parametros="";
                    while(parameters.hasMoreElements()){
                        String key=parameters.nextElement();
                        String value=request.getParameter(key);
                        if(key.equals("accion")==true || key.equals("suscripcion")==true){
                           
                        }else{
                            parametros+="&"+key+"="+value;
                        }
                    }                    
        %>
        <div id="contenedor">
           <div id="cabecera">                               
           </div><!-- cabecera -->
           <div id="izquierda">                
           </div><!-- izquierda-->
           <div id="contenido">
               <h1 class="alineados">Matricular Cursos</h1> 
               <form method="post" action="GestionDeCursosServlet">                                                       
                 <%
                    CursoService cs=new CursoService();
                    Curso a=new Curso();
                    if(request.getParameter("curso_seleccionado")!=null){
                        long id=Long.parseLong(request.getParameter("curso_seleccionado"));
                        a=cs.getDao().Buscar(id);                        
                    }
                 %>
                 <br><label class="jlabel">Curso  </label><br>                 
                 <input type="hidden" id="idcurso" name="idcurso" value="<%=a.getId()%>"/>                   
                 <input type="text" disabled class="jtextfield" value="<%=a.getId()%>"/>
                 <input type="text" disabled class="jtextfield" value="<%=a.toString()%>"/>       
                 <input type="hidden" id="parametros" value="<%=parametros%>"/>
                 <input type="hidden" id="suscripcion" name="suscripcion" value="MatricularCursos.jsp"/>       
                 <input type="hidden" id="accion" name="accion" value="2"/>
                 <input type="submit" value="Busqueda" onclick="AsignarAccion(2)"/>
                 <br> 
                 <br>
                 <%if(a.getId()==0){ %>
                    <input type="submit" value="Matricular" onclick="AsignarAccion(17)" disabled/>
                 <% }else{ %>
                    <input type="submit" value="Matricular" onclick="AsignarAccion(17)" />
                 <%}%>
                 <br>
                 <br>
                 <div id="ajax">
                     <table class="jlabel">
                         <tr>
                             <th>COD</th>
                             <th>DESCRIPCION</th>
                             <th>OP</th>
                         </tr>
                     <%
                     Iterator<Curso> it=cs.getDao().ObtenerCursos(idusuario,new sae.dominio.corte.CorteEscolarService().getDao().ObtenerCorteVigente().getId()).iterator();                     
                     while(it.hasNext()){
                         Curso obj=it.next();
                     %>
                          <tr>
                            <td><%=obj.getId()%></td>
                            <td><b><%=obj.getAsignatura().getDescripcion()+"-"+obj.getDocente().toString()%></b></td>
                            <td><a href="<%="GestionDeCursosServlet?accion=18&idcurso="+obj.getId()%>">Eliminar</a></td>
                          </tr>    
                     <%
                     }
                     %>
                     </table>
                 </div>    
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
