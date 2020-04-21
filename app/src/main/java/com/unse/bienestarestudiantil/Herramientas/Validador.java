package com.unse.bienestarestudiantil.Herramientas;

import android.content.Context;
import android.widget.EditText;

import com.unse.bienestarestudiantil.R;

public class Validador {

    private Context mContext;

    public Validador(Context context) {
        this.mContext = context;
    }

    public boolean validarNombresEdt(EditText... datos) {
        int i = 0;
        for (i = 0; i < datos.length; i++) {
            if (!validarNombres(datos[i]))
                break;
        }
        return i < (datos.length);
    }

    public boolean validarNumero(EditText editText) {
        if (noVacio(editText.getText().toString().trim())) {
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (validarNumero(editText.getText().toString().trim())) {
            return true;
        } else {
            editText.setError(mContext.getString(R.string.numeroNoValido));
            return false;
        }
    }

    public boolean validarRedes(EditText editText) {
        if (noVacio(editText.getText().toString().trim())) {
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (editText.getText().toString().equals("N/A") || !editText.getText().toString().equals("")) {
            return true;
        } else {
            editText.setError(mContext.getString(R.string.numeroNoValido));
            return false;
        }
    }

    public boolean validarNumeroDecimal(EditText editText) {
        if (noVacio(editText.getText().toString().trim())) {
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (validadNumeroDecimal(editText.getText().toString().trim())) {
            return true;
        } else {
            editText.setError(mContext.getString(R.string.numeroNoValido));
            return false;
        }
    }


    public boolean validarDNI(EditText editText) {
        if (noVacio(editText.getText().toString().trim())) {
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (validarDNI(editText.getText().toString().trim())) {
            return true;
        } else {
            editText.setError(mContext.getString(R.string.dniInvalido));
            return false;
        }
    }

    public boolean validarNombres(EditText editText) {
        if (noVacio(editText.getText().toString().trim())) {
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (validarNombre(editText.getText().toString().trim())) {

            if (lengthMore(editText.getText().toString().trim())) {
                return true;
            }else{
                editText.setError(mContext.getString(R.string.campoNoGrande));
                return false;
            }
        } else {
            editText.setError(mContext.getString(R.string.campoNoFormato));
            return false;
        }
    }

    public boolean validarDomicilio(EditText editText) {
        if (noVacio(editText.getText().toString().trim())) {
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (validarDomicilio(editText.getText().toString().trim())
            && !validarNumero(editText.getText().toString().trim())) {
                return true;
        } else {
            editText.setError(mContext.getString(R.string.domicilioInvalido));
            return false;
        }
    }

    public boolean validarFecha(EditText editText){
        if (noVacio(editText.getText().toString().trim())){
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (validarFechaFormato(editText.getText().toString().trim())){
            return true;
        }else{
            editText.setError(mContext.getString(R.string.fechaInvalida));
            return false;
        }
    }

    public boolean validarTelefono(EditText editText){
        if (noVacio(editText.getText().toString().trim())){
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (validarNumeroTelefono(editText.getText().toString().trim())){
            return true;
        }else{
            editText.setError(mContext.getString(R.string.numeroTelInvalido));
            return false;
        }

    }

    public boolean validarAnio(EditText editText){
        if (noVacio(editText.getText().toString().trim())){
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (validarNumero(editText.getText().toString().trim()) &&
        editText.getText().toString().trim().length() == 4){
            return true;
        }else{
            editText.setError(mContext.getString(R.string.anioIngesoNoValido));
            return false;
        }

    }

    public boolean validarMail(EditText editText){
        if (noVacio(editText.getText().toString().trim())){
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (validarMail(editText.getText().toString().trim())){
            return true;
        }else{
            editText.setError(mContext.getString(R.string.emailInvalido));
            return false;
        }

    }

    public boolean validarLegajo(EditText editText){
        if (noVacio(editText.getText().toString().trim())){
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (isLegajo(editText.getText().toString().trim())){
            return true;
        }else{
            editText.setError(mContext.getString(R.string.legajoInvalido));
            return false;
        }

    }

    public boolean validarContraseña(EditText editText, EditText editText2){
        if (noVacio(editText.getText().toString().trim())){
            editText.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (noVacio(editText2.getText().toString().trim())){
            editText2.setError(mContext.getString(R.string.campoVacio));
            return false;
        }
        if (!validarContraseña(editText.getText().toString().trim())){
            editText.setError(mContext.getString(R.string.contraseniaMinimo));
            return false;
        }
        if (!validarContraseña(editText2.getText().toString().trim())){
            editText2.setError(mContext.getString(R.string.contraseniaMinimo));
            return false;
        }
        if (editText.getText().toString().trim().equalsIgnoreCase(
                editText2.getText().toString().trim())){
            return true;
        }else{
            editText2.setError(mContext.getString(R.string.contraseniaNoIguales));
            return false;
        }

    }

    public boolean validarNumeroTelefono(String numero) {
        String regex = "[0-9]{8,}";
        return numero.matches(regex);
    }

    private boolean validarFechaFormato(String trim) {
        String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        return trim.matches(regex);
    }
    public boolean noVacio(String string) {
        return string.equalsIgnoreCase("");
    }

    public boolean validarDNI(String dni) {
        boolean isNumber = true;
        try {
            Integer.parseInt(dni);
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        return isNumber && dni.length() >= 7;
    }

    public boolean validarNombre(String name) {
        String regex = "[ a-zA-ZÀ-ÿ\\u00f1\\u00d1]+";
        return name.matches(regex);
    }

    public boolean validarDomicilio(String name) {
        String regex = "[ a-zA-ZÀ-ÿ\\u00f1\\u00d10-9]+";
        return name.matches(regex);
    }

    public boolean validarHora(String name) {
        String regex = "[0-9]{2}:[0-9]{2}-[0-9]{2}:[0-9]{2}";
        return !noVacio(name) && name.matches(regex);
    }



    public boolean validarMail(String mail) {
        String regex = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return mail.matches(regex);
    }


    public boolean validarNumero(String numero) {
        String regex = "^[0-9]+$";
        return numero.matches(regex);
    }

    public boolean validadNumeroDecimal(String numero) {
        String regex = "^[0-9]+(\\.[0-9]+$)?";
        return numero.matches(regex);
    }

    public boolean validadAltura(String numero) {
        float num = Float.parseFloat(numero);
        if (num < 54)
            return false;
        return num <= 280;
    }

    public boolean validadPeso(String numero) {
        float num = Float.parseFloat(numero);
        return (num > 0);
    }



    public boolean isLegajo(String leg) {
        String regex = Utils.PATRON_LEGAJO;
        return leg.matches(regex);
    }


    public boolean lengthMore(String string) {
        return string.length() >= 3;
    }

    public boolean validarContraseña(String c) {
        return c.length() >= 4;
    }

}
