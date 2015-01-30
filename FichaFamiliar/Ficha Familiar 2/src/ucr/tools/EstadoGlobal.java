package ucr.tools;


/**
 * Created by Batman on 03/11/2014.
 * mem comp entre fragments
 */
public class EstadoGlobal {

    private static final EstadoGlobal INSTANCE = new EstadoGlobal();
    public static EstadoGlobal getInstance (){
        return INSTANCE;
    }

    private int codPersona = -2;
    private int codGenero = -3;
    //private int codGenero = -3; //1 = Mujer, 2 = Hombre;

    public void setCodGenero(char genero) {
        if(genero == 'M'){
            codGenero = 2;
        } else {
            codGenero = 1;
        }
    }

    public int getCodGenero(){
        return codGenero;
    }

    public void setCodPersona(int codPer){
        codPersona = codPer;
    }

    public int getCodPersona(){
        return codPersona;
    }





    private int codigoVivienda = -25;

    public void setCodViv(int codViv){
        codigoVivienda = codViv;
    }

    public int getCodViv(){
        return codigoVivienda;
    }

}
