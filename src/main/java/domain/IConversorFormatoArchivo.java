package domain;

/**
 * Created by Matias Fischer on 27/04/2017.
 */
public interface IConversorFormatoArchivo {
    String AFormatoArchivo(Object obj);

    <T> T DeFormatoArchivo(String json, Class<T> claseObjeto);
}
