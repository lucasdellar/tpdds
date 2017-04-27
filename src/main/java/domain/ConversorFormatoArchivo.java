package domain;

import com.google.gson.Gson;

/**
 * Created by Matias Fischer on 27/04/2017.
 */


public class ConversorFormatoArchivo implements IConversorFormatoArchivo {
    private Gson gson;

    public ConversorFormatoArchivo() {
        gson = new Gson();
    }

    public ConversorFormatoArchivo(Gson gson) {
        this.gson = gson;
    }

    @Override
    public String AFormatoArchivo(Object obj)
    {
        return gson.toJson(obj);
    }

    @Override
    public <T> T DeFormatoArchivo(String json, Class<T> claseObjeto){
        return gson.fromJson(json,claseObjeto);
    }
}
