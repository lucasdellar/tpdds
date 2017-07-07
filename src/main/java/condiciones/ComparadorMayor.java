package condiciones;

public class ComparadorMayor implements IComparador{

	@Override
	public Boolean comparar(int numeroUno, int numeroDos) {
		return numeroUno > numeroDos;
	}
}
