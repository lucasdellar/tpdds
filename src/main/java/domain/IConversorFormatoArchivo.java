package domain;


public interface IConversorFormatoArchivo {
	
	
    String aFormatoArchivo(Object obj);
    <T> T deFormatoArchivo(String json, Class<T> claseObjeto);
}
