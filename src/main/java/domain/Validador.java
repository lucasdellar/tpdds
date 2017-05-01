package domain;

import org.apache.commons.lang.StringUtils;
import org.uqbar.commons.model.UserException;

/**
 * Created by Matias Fischer on 01/05/2017.
 */
public class ValidadorCuenta {





    public static void validarAnio(String anio) throws UserException{
       if (!IsInteger((anio))) throw new UserException("maeameeeeee");
    }

    private static Boolean IsInteger(String value){
        String intRegex = "[0-9]+";

        return value.matches(intRegex);
    }

}
