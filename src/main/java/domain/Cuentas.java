package domain;

import java.util.ArrayList;

public class Cuentas {
	
	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
	/*
	public Cuentas(ArrayList<Cuenta> cuentas){
		this.cuentas = cuentas;
	}
	*/
	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(ArrayList<Cuenta> cuentas) {
		this.cuentas = cuentas;
	} 
	
	void agregarCuenta(Cuenta unaCuenta){
		cuentas.add(unaCuenta);
	}

	public void ClearCuentas() {
		cuentas.clear();
	}

}
