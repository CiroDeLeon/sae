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
                var URL = "GestionDeCursosServlet?busqueda="+document.getElementById('busqueda').value+"&accion=6&suscripcion="+document.getElementById('suscripcion').value+document.getElementById('parametros').value;                                
                xmlhttpObject.open("POST", URL,true);
                xmlhttpObject.send(null);
                xmlhttpObject.onreadystatechange = ManejarPeticion;
        }
}


function PeticionAjax2(objeto) {
        xmlhttpObject = getXMLHTTPObject();
        if (xmlhttpObject != null) {
                var URL = "GestionDeCursosServlet?idcurso="+objeto.id+"&accion=15";                                                
                xmlhttpObject.open("POST", URL,true);
                xmlhttpObject.send(null);
                xmlhttpObject.onreadystatechange = ManejarPeticion2;
        }
}
function ManejarPeticion2() {
        if (xmlhttpObject.readyState == 4) {
                if (xmlhttpObject.status == 200) {
                        setAjaxOutput2();
                } else {
                        alert("Error during AJAX call. Please try again");
                }
        }
}
function setAjaxOutput2() {
        document.getElementById('ajax2').innerHTML = xmlhttpObject.responseText;
}





