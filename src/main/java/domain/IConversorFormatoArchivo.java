package domain;


public interface IConversorFormatoArchivo<T> {
	
	
    String aFormatoArchivo(Object obj);
    T deFormatoArchivo(String json, Class<T> claseObjeto);
}
