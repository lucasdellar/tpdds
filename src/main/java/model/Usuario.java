package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Usuarios")
@Entity
public class Usuario {
	
	@Id
	@Column(name = "usuario")
	private String usuario;
	@Column(name = "password")
	private String password;
	
	private Usuario(){}
	
	public Usuario(String user, String pass){
		this.usuario = user;
		this.password = pass;
	}

	public String getNombre() {
		return this.usuario;
	}

	public String getPassword() {
		return this.password;
	}

}
