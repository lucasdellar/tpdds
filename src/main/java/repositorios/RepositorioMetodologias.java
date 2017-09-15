package repositorios;

import domain.Metodologia;

public class RepositorioMetodologias extends Repositorio<Metodologia> {
	
	@Override
	public void agregar(Metodologia una){
		this.getLista().add(una);
	}

}
