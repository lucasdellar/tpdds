package condiciones;

public class ComparadorMenor implements IComparador{

	@Override
	public Boolean comparar(int numeroUno, int numeroDos) {
		return numeroUno < numeroDos;
	}

}
