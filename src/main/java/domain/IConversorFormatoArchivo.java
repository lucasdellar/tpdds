package domain;


public interface IConversorFormatoArchivo {
	
	
    String aFormatoArchivo(Object obj);
    Cuenta deFormatoArchivo(String json);
}
