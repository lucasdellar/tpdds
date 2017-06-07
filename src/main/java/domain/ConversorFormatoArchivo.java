package domain;

import com.google.gson.Gson;

public class ConversorFormatoArchivo implements IConversorFormatoArchivo {
    private Gson gson;

    public ConversorFormatoArchivo() {
        gson = new Gson();
    }

    public ConversorFormatoArchivo(Gson gson) {
        this.gson = gson;
    }

    @Override
    public String aFormatoArchivo(Object obj)
    {
        return gson.toJson(obj);
    }

    @Override
    public <T> T deFormatoArchivo(String json, Class<T> claseObjeto){
    	return gson.fromJson(json,claseObjeto);
}
}
