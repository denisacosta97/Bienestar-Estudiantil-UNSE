package com.unse.bienestarestudiantil.Herramientas;

public class Validador {

    public boolean validarDNI(String dni){
        boolean isNumber = true;
        try {
            Integer.parseInt(dni);
        }catch (NumberFormatException e){
            isNumber = false;
        }
        return isNumber && dni.length() >= 7;
    }

    public boolean validarNombre(String name){
        String regex = "[ A-Za-z]+";
        return !noVacio(name) && name.matches(regex);
    }

    public boolean validarNombres(String... datos)
    {
        int i = 0;
        for (i = 0; i<datos.length; i++){
            if (!validarNombre(datos[i]))
                break;
        }
        return i<(datos.length);
    }


    public boolean validarMail(String mail){
        String regex = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return mail.matches(regex);
    }

    public boolean validarNumeroTelefono(String numero){
        String regex = "^[0-9]+$";
        return numero.matches(regex) && numero.length() >= 10;
    }

    public boolean validarNumero(String numero){
        String regex = "^[0-9]+$";
        return numero.matches(regex);
    }

    public boolean validadNumeroDecimal(String numero){
        String regex = "^[0-9]+(\\.[0-9]+$)?";
        return numero.matches(regex);
    }

    public boolean noVacio(String string){
        return string.equals("");
    }

    public boolean noVacio(String... datos)
    {
        int i = 0;
        for (i = 0; i<datos.length; i++){
            if (noVacio(datos[i]))
                break;
        }
        return i<(datos.length);
    }

    private boolean noCaracterEspecial(String dato) {
        String regex = "^[A-Za-z.]+$";
        return dato.matches(regex);
    }

    public boolean validarContraseña(String c){
        return c.length() >= 4;
    }


}
