function validarDatos(){
	var errores = 0;
	var nombre_cuenta = document.getElementById("nombreCuenta").value;
	var periodo = document.getElementById("periodoCuenta").value;
	var valor = document.getElementById("valorCuenta").value;

	
	document.getElementById("feedback1").innerHTML = "";	
	document.getElementById("feedback1").style.color = "red";
	document.getElementById("feedback2").innerHTML = "";	
	document.getElementById("feedback2").style.color = "red";
	document.getElementById("feedback3").innerHTML = "";	
	document.getElementById("feedback3").style.color = "red";
	if(nombre_cuenta == null || nombre_cuenta.length == 0){
		document.getElementById("feedback1").innerHTML = "Debe ingresar un nombre";
		errores++;
	}
	if(!/^[a-zA-Z\s]*$/.test(nombre_cuenta)){
		document.getElementById("feedback1").innerHTML = "El nombre solo puede contener letras y espacios";
		errores++;
	}
	if(periodo == null || periodo.length == 0){
		document.getElementById("feedback2").innerHTML = "Debe ingresar un periodo";
		errores++;
	}
	if(valor == null || valor.length == 0){
		document.getElementById("feedback3").innerHTML = "Debe ingresar un valor";
		errores++;
	}

	if(errores > 0)
		return false;
	return true;
}
