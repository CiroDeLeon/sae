<%-- 
    Document   : GestionDeCohortes
    Created on : 16/01/2017, 08:37:48 PM
    Author     : Usuario1
--%>

<%@page import="sae.dominio.docente.Docente"%>
<%@page import="sae.dominio.docente.DocenteService"%>
<%@page import="sae.dominio.actor.Actor"%>
<%@page import="sae.dominio.actor.ActorService"%>
<%@page import="sae.dominio.corte.CorteEscolar"%>
<%@page import="sae.dominio.corte.CorteEscolarService"%>
<%@page import="sae.dominio.grupo.GrupoService"%>
<%@page import="sae.dominio.grupo.Grupo"%>
<%@page import="sae.dominio.curso.Curso"%>
<%@page import="sae.dominio.curso.CursoService"%>
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
           CursoService service=new CursoService();
           Curso a=new Curso();
           int accion=0;
           boolean ingresando=true;
           long curso_seleccionado=0;
           long idactor=0;
           if(request.getParameter("idactor")!=null){
              idactor=Long.parseLong(request.getParameter("idactor"));
           }
           
           if(request.getParameter("curso_seleccionado")!=null && request.getParameter("curso_seleccionado").equals("0")==false){
              curso_seleccionado=Long.parseLong(request.getParameter("curso_seleccionado").toString());                            
              Curso seleccionado=service.getDao().Buscar(curso_seleccionado);                            
              
              CorteEscolarService ce_service=new CorteEscolarService();
              CorteEscolar ce=ce_service.getDao().Buscar(seleccionado.getCorte().getId());                            
              seleccionado.setCorte(ce);
              
              AsignaturaService as_service=new AsignaturaService();
              Asignatura as=as_service.getDao().Buscar(seleccionado.getAsignatura().getId());
              seleccionado.setAsignatura(as);
              
              ActorService act_service=new ActorService();
              Actor act=act_service.Buscar(seleccionado.getResponsable().getId());
              seleccionado.setResponsable(act);
              
              DocenteService do_service=new DocenteService();
              Docente doc=do_service.Buscar(seleccionado.getDocente().getId());
              seleccionado.setDocente(doc);
              
              if(seleccionado!=null){
                 a=seleccionado;
              }
              accion=3;
              ingresando=false;                               
    
           }else{
              accion=1; 
              if(request.getParameter("idcurso")!=null && request.getParameter("idcurso").equals("0")==false ){ 
                 long id=Long.parseLong(request.getParameter("idcurso"));
                 a.setId(id);
              }else{
                  
                 long id=0;
                 a.setId(id);
                 
                 CorteEscolarService ce_service=new CorteEscolarService();
                 CorteEscolar ce=ce_service.getDao().ObtenerCorteVigente();
                 if(ce==null){
                     ce=new CorteEscolar(0);
                 }
                 a.setCorte(ce);                                                   
                 
                 if(request.getParameter("asignatura_seleccionada")==null || request.getParameter("asignatura_seleccionada").equals("0")==true){
                   a.setAsignatura(new sae.dominio.asignatura.Asignatura(0));
                 }else{
                   long ida=Long.parseLong(request.getParameter("asignatura_seleccionada"));
                   AsignaturaService as_service=new AsignaturaService();
                   a.setAsignatura(as_service.getDao().Buscar(ida));
                 }
                 if(request.getParameter("docente_seleccionado")==null || request.getParameter("docente_seleccionado").equals("0")==true ){
                   a.setDocente(new sae.dominio.docente.Docente(0));
                 }else{
                   long ida=Long.parseLong(request.getParameter("docente_seleccionado"));
                   DocenteService do_service=new DocenteService();
                   a.setDocente(do_service.Buscar(ida));
                 }                                  
                 a.setResponsable(new sae.dominio.actor.Actor(0));
              }
              
              
              a.setResponsable(new Actor(idactor));
              a.setCupo(Integer.parseInt((""+request.getParameter("cupo")).toString().replace("null","0")));
              
           }
       %> 
        <div id="contenedor">
           <div id="cabecera">                               
           </div><!-- cabecera -->
           <div id="izquierda">                
           </div><!-- izquierda-->
           <div id="contenido">
               <h1 class="alineados">GESTION DE CURSOS</h1> 
               <form method="post" action="GestionDeCursosServlet" >                                      
                 <br><label class="jlabel">Codigo</label><br>
                 <input type="text" id="idcurso" name="idcurso" class="jtextfield" value="<%=a.getId()%>" disabled />                            
                 <br><br><label class="jlabel">Corte Academico  </label><br>
                 <input type="text" id="corte_escolar" name="corte_escolar" class="jtextfield" value="<%=a.getCorte().getId()%>"/>
                 <input type="text" class="jtextfield" value="<%=a.getCorte().getDescripcion()%>"/>
                 
                 <br><br><label class="jlabel">Asignatura  </label><br>
                 <input type="hidden" id="asignatura_seleccionada" name="asignatura_seleccionada" class="jtextfield" value="<%=a.getAsignatura().getId()%>" />
                 <input type="text" id="idasignatura" name="idasignatura" class="jtextfield"  autofocus="true" value="<%=a.getAsignatura().getId()%>" />                            
                 <input type="text"  class="jtextfield"  autofocus="true" value="<%=a.getAsignatura().getDescripcion()%>" />                            
                 <input type="submit" value="buscar" onclick="AsignarAccion(10)"/><br>
                 <br><label class="jlabel">Docente  </label><br>
                 <input type="hidden" id="docente_seleccionado" name="docente_seleccionado" class="jtextfield" value="<%=a.getDocente().getId()%>" />
                 <input type="text" id="iddocente" name="iddocente" class="jtextfield"  autofocus="true" value="<%=a.getDocente().getId()%>" />                            
                 <input type="text"  class="jtextfield"  autofocus="true" value="<%=a.getDocente().toString() %>" />                            
                 <input type="submit" value="buscar" onclick="AsignarAccion(11)"/><br>
                 <br><label class="jlabel">Cupo  </label><br>       
                 <input type="text" id="cupo" name="cupo" class="jtextfield"  autofocus="true" value="<%=a.getCupo()%>" />                            
                 
                 
                 <input type="hidden" id="suscripcion" name="suscripcion" class="jtextfield" value="GestionDeCursos.jsp" />
                 <input type="hidden" id="curso_seleccionado" name="curso_seleccionado" class="jtextfield" value="<%=a.getId()%>" />  
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
                 <br>
                 <br>
                 <a href="GestionDeHoraSemanas.jsp?idcurso=<%=a.getId()%>">Gestion de Horas</a>
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
