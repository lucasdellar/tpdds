package comparadores;

public class ComparadorMenor implements IComparador{

	@Override
	public Boolean comparar(double numeroUno, double numeroDos) {
		return numeroUno < numeroDos;
	}
}
