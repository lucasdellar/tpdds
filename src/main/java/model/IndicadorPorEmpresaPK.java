package model;

import java.io.Serializable;

public class IndicadorPorEmpresaPK implements Serializable {

	 	protected Long empresa_id;
	    protected Long indicador_id;
	    protected String periodo;

	    public IndicadorPorEmpresaPK() {}

	    public IndicadorPorEmpresaPK(long l, long m, String periodo) {
	        this.empresa_id = l;
	        this.indicador_id = m;
	        this.periodo = periodo;
	    }
	
}
