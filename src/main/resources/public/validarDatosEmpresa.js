function validarDatos(){
	var errores = 0;
	var nombre_empresa = document.getElementById("nuevaEmpresa").value;
	
	document.getElementById("feedback").innerHTML = "";	
	document.getElementById("feedback").style.color = "red";
	if(nombre_empresa == null || nombre_empresa.length == 0){
		document.getElementById("feedback").innerHTML = "Debe ingresar un nombre";
		errores++;
	}
	if(!/^[a-zA-Z\s]*$/.test(nombre_empresa)){
		document.getElementById("feedback").innerHTML = "El nombre solo puede contener letras y espacios";
		
		errores++;
	}
	
	if(errores > 0)
		return false;
	return true;
}
