/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function setAjaxOutput() {
        document.getElementById('ajax').innerHTML = xmlhttpObject.responseText;
}

function ManejarPeticion() {
        if (xmlhttpObject.readyState == 4) {
                if (xmlhttpObject.status == 200) {
                        setAjaxOutput();
                } else {
                        alert("Error during AJAX call. Please try again");
                }
        }
}

// Implement business logic
function PeticionAjax() {
        xmlhttpObject = getXMLHTTPObject();
        if (xmlhttpObject != null) {
                var parametros=document.getElementById('parametros').value;
                var suscripcion=document.getElementById('suscripcion').value;
                var busqueda=document.getElementById('busqueda').value;
                var URL = "GestionDeEstudiantesServlet?busqueda="+busqueda+"&accion=6&suscripcion="+suscripcion+parametros;                                                
                xmlhttpObject.open("POST", URL,true);
                xmlhttpObject.send(null);
                xmlhttpObject.onreadystatechange = ManejarPeticion;
        }
}

