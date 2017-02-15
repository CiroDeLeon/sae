<%-- 
    Document   : GestionDeCohortes
    Created on : 16/01/2017, 08:37:48 PM
    Author     : Usuario1
--%>

<%@page import="sae.dominio.curso.Curso"%>
<%@page import="sae.dominio.curso.CursoService"%>
<%@page import="java.util.Iterator"%>
<%@page import="sae.dominio.estudiante.Estudiante"%>
<%@page import="sae.dominio.estudiante.EstudianteService"%>
<%@page import="sae.dominio.grupo.Grupo"%>
<%@page import="sae.dominio.grupo.GrupoService"%>
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
           int accion=0; 
           Grupo g; 
           if(request.getParameter("idgrupo")!=null){ 
              long idgrupo=Long.parseLong(request.getParameter("idgrupo"));           
              GrupoService gservice=new GrupoService();
              g=gservice.getDao().Buscar(idgrupo);
           }else{
              g=new Grupo(0); 
           }
           Curso e;
           if(request.getParameter("curso_seleccionado")!=null){
              CursoService eservice=new sae.dominio.curso.CursoService();
              long idestudiante=Long.parseLong(request.getParameter("curso_seleccionado"));           
              e=eservice.getDao().Buscar(idestudiante);
           }else{
              e=new Curso(0);
           }
       %> 
        <div id="contenedor">
           <div id="cabecera">                               
           </div><!-- cabecera -->
           <div id="izquierda">                
           </div><!-- izquierda-->
           <div id="contenido">
               <h1 class="alineados">Anexar Cursos A Un Grupo</h1> 
               <form method="post" action="GestionDeCursosServlet">                                      
                 <br><label class="jlabel">Grupo</label><br>
                 <input type="hidden" id="accion" name="accion" value="<%=accion%>"/>
                 <input type="hidden" id="suscripcion" name="suscripcion" value="ConfigurarCursosDeGrupo.jsp" />
                 <input type="hidden" id="idgrupo" name="idgrupo" class="jtextfield" value="<%=g.getId()%>" />                            
                 <input type="text" class="jtextfield" value="<%=g.getId()%>" disabled />                            
                 <input type="text" class="jtextfield" value="<%=g.getDescripcion()%>" disabled/>                 
                 
                 <br>      
                 <br><label class="jlabel">Curso</label><br>
                 <input type="hidden" id="idcurso" name="idcurso" class="jtextfield" value="<%=e.getId()%>" />                            
                 <input type="text"  class="jtextfield" value="<%=e.getId()%>" disabled />                            
                 <input type="text"  class="jtextfield" value="<%=e.toString()%>" disabled/>
                 <input type="submit" value="Buscar" onclick="AsignarAccion(2)" />                            
                 <br>                 
                 <br>
                 <input type="submit" value="Anexar" onclick="AsignarAccion(12)" /> 
                 <br>
                 <br>
                 <table>
                     <tr class="jlabel">
                         <th class="jlabel">Cod</th>
                         <th class="jlabel">Descripcion</th>
                         <th class="jlabel">OP</th>
                     </tr>
                     <%
                        CursoService service=new CursoService();
                        Iterator<Curso> it=service.getDao().Listar(g.getId()).iterator();
                        while(it.hasNext()){
                            Curso dto=it.next();                           
                     %>
                     <tr class="jlabel">
                         <td class="jlabel"><%=dto.getId()%></td>
                         <td class="jlabel"><%=dto.toString()%></td>
                         <td class="jlabel"><a href='GestionDeCursosServlet?idgrupo=<%=g.getId()%>&idcurso=<%=dto.getId()%>&accion=13'>Eliminar</a></td>
                     </tr>
                     <%
                         }                     
                     %>
                 </table>
               </form>
               <br>  
               <% if(request.getParameter("mensaje")!=null){%>
               <label class="jlabel" id="mensaje"><%out.print(request.getParameter("mensaje"));%></label>                  
               <%}else{%>
               <label id="mensaje" class="jlabel"></label>
               <%}%>
               <br>
               <br>
               <a href="GestionDeGrupos.jsp?grupo_seleccionado=<%=g.getId() %>">REGRESAR</a><br><br>
           </div><!--contenido -->     
           <div id="derecha"></div>           
           <div id="pie">                                           
           </div><!-- pie -->
      </div><!--contenedor-->      
    </body>
</html>
