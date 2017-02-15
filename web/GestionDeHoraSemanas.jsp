<%-- 
    Document   : GestionDeCohortes
    Created on : 16/01/2017, 08:37:48 PM
    Author     : Usuario1
--%>

<%@page import="sae.dominio.curso.horasemana.HoraSemana"%>
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
    <body onload>
        <% 
           CursoService service=new CursoService();
           long idcurso=0;
           if(request.getParameter("idcurso")!=null){
              idcurso=Long.parseLong(request.getParameter("idcurso"));
           }
           Curso curso=service.getDao().Buscar(idcurso);
           
           HoraSemana a=new HoraSemana();
           int accion=0;
           boolean ingresando=true;
           long horasemana_seleccionada=0;
           if(request.getParameter("horasemana_seleccionada")!=null){
              horasemana_seleccionada=Long.parseLong(request.getParameter("horasemana_seleccionada").toString());                            
              HoraSemana seleccionado=service.getDao().BuscarHoraSemana(horasemana_seleccionada);
              if(seleccionado!=null){
                 a=seleccionado;
              }
              a.setCurso(curso);
              accion=3;
              ingresando=false;              
           }else{
              accion=1; 
              if(request.getParameter("idhorasemana")!=null){ 
                 long id=Long.parseLong(request.getParameter("idhorasemana"));
                 a.setId(id);
              }else{
                 long id=0;
                 a.setId(id);
              }
              
              a.setCurso(curso);                
              if(request.getParameter("dia")!=null){
              a.setDia(Integer.parseInt(request.getParameter("dia")));
              }
              if(request.getParameter("hora")!=null){
              a.setHora(Integer.parseInt(request.getParameter("hora")));
              }
              if(request.getParameter("cuartoinicial")!=null){
              a.setCuarto_inicial(Integer.parseInt(request.getParameter("cuartoinicial")));
              }
              if(request.getParameter("cuartofinal")!=null){
              a.setCuarto_final(Integer.parseInt(request.getParameter("cuartofinal")));
              }
              if(request.getParameter("aula")!=null){
              a.setAula(Integer.parseInt(request.getParameter("aula")));              
              }
              
           }
       %> 
        <div id="contenedor">
           <div id="cabecera">                               
           </div><!-- cabecera -->
           <div id="izquierda">                
           </div><!-- izquierda-->
           <div id="contenido">
               <h1 class="alineados">GESTION DE HORAS DE CURSO</h1> 
               <form method="post" action="GestionDeHoraSemanasServlet">                                      
                 <br><label class="jlabel">Curso</label><br>
                 <input type="hidden" id="idcurso" name="idcurso"  value="<%=curso.getId()%>" />                            
                 <input type="text" class="jtextfield" value="<%=curso.getId()%>" disabled />                            
                 <input type="text" class="jtextfield" value="<%=curso.toString()%>" disabled />                            
                 <br><br><h3 class="alineados">HORA SEMANA</h3>                 
                 <input type="hidden" id="idhorasemana" name="idhorasemana" class="jtextfield" value="<%=a.getId()%>" disabled />                            
                 <label class="jlabel">Dia  </label><br>
                 <select class="jtextfield" id="dia" name="dia">
                     <%                     
                     String dias[]={"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"}; 
                     for(int i=0;i<dias.length;i++){
                         String selected="";
                         if(i+1==a.getDia()){
                            selected="selected";  
                         }
                         out.println("<option value='"+(i+1)+"' "+selected+">"+dias[i]+"</option>");
                     }
                     %>
                 </select>
                 <br><label class="jlabel">Hora  </label><br>
                 <select class="jtextfield" id="hora" name="hora">
                    <%
                       for(int i=0;i<24;i++){
                          String selected="";
                          if(i==a.getHora()){
                              selected="selected";
                          }
                          out.println("<option value='"+i+"' "+selected+">"+i+"</option>");
                       }
                    %>
                 </select>           
                 <br><label class="jlabel">Minuto Inicial  </label><br>
                 <select class="jtextfield" id="cuartoinicial" name="cuartoinicial">
                     <%
                        String cuartos_de_hora_iniciales[]={"0","15","30","45"}; 
                        for(int i=0;i<4;i++){
                          String selected="";
                          if(i+1==a.getCuarto_inicial()){
                              selected="selected";
                          }
                          out.println("<option value='"+(i+1)+"' "+selected+">"+cuartos_de_hora_iniciales[i]+"</option>");
                       }
                     %>                     
                 </select>
                 <br><label class="jlabel">Minuto Final  </label><br>
                 <select class="jtextfield" id="cuartofinal" name="cuartofinal">
                     <%
                        String cuartos_de_hora_finales[]={"15","30","45","59"}; 
                        for(int i=0;i<4;i++){
                          String selected="";
                          if(i+1==a.getCuarto_final()){
                              selected="selected";
                          }
                          out.println("<option value='"+(i+1)+"' "+selected+">"+cuartos_de_hora_finales[i]+"</option>");
                       }
                     %>                     
                 </select>
                 <br><label class="jlabel">Aula  </label><br>
                 <input tupe="text" id="aula" name="aula" class="jtextfield" value="<%=a.getAula()%>" />
                 <input type="hidden" id="suscripcion" name="suscripcion" class="jtextfield" value="GestionDeHoraSemanas.jsp" />
                 <input type="hidden" id="horasemana_seleccionada" name="horasemana_seleccionada" class="jtextfield" value="<%=a.getId()%>" />
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
               <a href="GestionDeCursos.jsp?curso_seleccionado=<%=curso.getId()%>">REGRESAR</a><br><br>               
           </div><!--contenido -->     
           <div id="derecha"></div>           
           <div id="pie">                                           
           </div><!-- pie -->
      </div><!--contenedor-->      
    </body>
</html>
