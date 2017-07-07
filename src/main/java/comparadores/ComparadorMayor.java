package comparadores;

public class ComparadorMayor implements IComparador{

	@Override
	public Boolean comparar(double numeroUno, double numeroDos) {
		return numeroUno > numeroDos;
	}
}
