<%-- 
    Document   : BuscarActor
    Created on : 15/01/2017, 08:49:49 AM
    Author     : Usuario1
--%>


<%@page import="java.util.Enumeration"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="estilos/estilos.css">
        <script type="text/javascript" src="js/sae.js"></script>
        <script type="text/javascript" src="js/BuscarCurso.js"></script>
    </head>
    <body onload="PeticionAjax()">
        <div id="contenedor">
           <div id="cabecera">                               
           </div><!-- cabecera -->
           <div id="izquierda">                
           </div><!-- izquierda-->
           <div id="contenido">
               <h1 class="alineados">Buscar Curso</h1> 
               <form method="post">                                      
                 <br><label class="jlabel">Busqueda  </label><br>
                 <input type="text" id="busqueda" name="busqueda" class="jtextfield" onkeypress="PeticionAjax()" autofocus="true" />                            
                 <% 
                    String suscripcion=(""+request.getParameter("suscripcion")).replace("null","");
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
                 <input type="hidden" id="parametros" value="<%=parametros%>"/>
                 <input type="hidden" id="suscripcion" value="<%=suscripcion%>"/>       
                 <br>      
                 <br>
                 <div id="ajax">
                 </div>    
                 <br>
                 <div id="ajax2">
                 </div>    
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
