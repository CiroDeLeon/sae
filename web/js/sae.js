/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 function AsignarAccion(accion){    
    document.getElementById("accion").value=accion;    
 }
 function LimpiarMensaje(){
    document.getElementById("mensaje").innerHTML='';     
 } 
 function getXMLHTTPObject() {
        var xmlhttpObject = null;
        try {
                // For Old Microsoft Browsers
                xmlhttpObject = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
                try {
                        // For Microsoft IE 6.0+
                        xmlhttpObject = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e1) {
                        // No Browser accepts the XMLHTTP Object then false
                        xmlhttpObject = false;
                }
        }
        if (!xmlhttpObject && typeof XMLHttpRequest !== 'undefined') {
                // For Mozilla, Opera Browsers
                xmlhttpObject = new XMLHttpRequest();
        }
        // Mandatory Statement returning the ajax object created
        return xmlhttpObject;
}