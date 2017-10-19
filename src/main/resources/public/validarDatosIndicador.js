function validarDatos(){
	var errores = 0;
	var nombre = document.getElementById("nombre").value;
	var formula = document.getElementById("formula").value;
	document.getElementById("feedback1").innerHTML = "";	
	document.getElementById("feedback1").style.color = "red";
	document.getElementById("feedback2").innerHTML = "";	
	document.getElementById("feedback2").style.color = "red";
	if(nombre == null || nombre.length == 0){
		document.getElementById("feedback1").innerHTML = "Debe ingresar un nombre";
		errores++;
	}
	if(!/^[a-zA-Z\s]*$/.test(nombre)){
		document.getElementById("feedback1").innerHTML = "El nombre solo puede contener letras y espacios";
		errores++;
	}
	if(formula == null || formula.length == 0){
		document.getElementById("feedback2").innerHTML = "Debe ingresar una formula";
		errores++;
	}

	if(errores > 0)
		return false;
	return true;
}