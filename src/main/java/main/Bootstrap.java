package main;

import java.util.ArrayList;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import empresas.Empresa;
import model.Cuenta;
import model.Usuario;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {
    withTransaction(() -> {
    	
    	Usuario user = new Usuario("admin", "utnso");
    	persist(user);
    	
    	Empresa coke = new Empresa("Coca-Cola");
    	coke.setCuentas(new ArrayList<Cuenta>());
    	coke.agregarCuenta(new Cuenta("Fanta", "2017", "300000"));
    	coke.agregarCuenta(new Cuenta("7 arriba", "2010", "104400"));
    	coke.agregarCuenta(new Cuenta("Agua", "2020", "77586124"));
    	
    	Empresa globant = new Empresa("Globant");
    	globant.setCuentas(new ArrayList<Cuenta>());
    	globant.agregarCuenta(new Cuenta("Patrimonio Neto", "2015", "0"));
    	globant.agregarCuenta(new Cuenta("ROE", "2140", "1"));
    	globant.agregarCuenta(new Cuenta("Humanos", "2010", "666"));
    	
    	persist(globant);
    	persist(coke);
    	
    });
  }

}
