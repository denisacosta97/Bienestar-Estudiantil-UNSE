package com.unse.bienestarestudiantil.Modelos;

public class ItemDatoPileta extends ItemListado {
    public ItemDatoPileta(PiletaIngreso piletaIngreso){
        this.mPiletaIngreso = piletaIngreso;
    }

    private PiletaIngreso mPiletaIngreso;

    public PiletaIngreso getPiletaIngreso() {
        return mPiletaIngreso;
    }

    public void setPiletaIngreso(PiletaIngreso piletaIngreso) {
        mPiletaIngreso = piletaIngreso;
    }

    @Override
    public int getTipo() {
        return TIPO_DATO;
    }
}
