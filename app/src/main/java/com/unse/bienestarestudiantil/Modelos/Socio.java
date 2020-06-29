package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Socio  extends Usuario implements Parcelable {

    private String fechaRegistro;
    private int validez;

    public Socio(Usuario usuario, String fechaRegistro, int validez) {
        super(usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(), usuario.getTipoUsuario());
        this.fechaRegistro = fechaRegistro;
        this.validez = validez;
    }

    protected Socio(Parcel in) {
        setIdUsuario(in.readInt());
        setTipoUsuario(in.readInt());
        setValidez(in.readInt());
        setNombre(in.readString());
        setApellido(in.readString());
        setPais(in.readString());
        setProvincia(in.readString());
        setLocalidad(in.readString());
        setDomicilio(in.readString());
        setBarrio(in.readString());
        setTelefono(in.readString());
        setSexo(in.readString());
        setMail(in.readString());
        setFechaNac(in.readString());
        setFechaRegistro(in.readString());
        setFechaModificacion(in.readString());

        fechaRegistro = in.readString();
        validez = in.readInt();
    }

    public static final Creator<Socio> CREATOR = new Creator<Socio>() {
        @Override
        public Socio createFromParcel(Parcel in) {
            return new Socio(in);
        }

        @Override
        public Socio[] newArray(int size) {
            return new Socio[size];
        }
    };

    @NonNull
    @Override
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    @Override
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public int getValidez() {
        return validez;
    }

    @Override
    public void setValidez(int validez) {
        this.validez = validez;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getIdUsuario());
        dest.writeInt(getTipoUsuario());
        dest.writeInt(getValidez());
        dest.writeString(getNombre());
        dest.writeString(getApellido());
        dest.writeString(getPais());
        dest.writeString(getProvincia());
        dest.writeString(getLocalidad());
        dest.writeString(getDomicilio());
        dest.writeString(getBarrio());
        dest.writeString(getTelefono());
        dest.writeString(getSexo());
        dest.writeString(getMail());
        dest.writeString(getFechaNac());
        dest.writeString(getFechaRegistro());
        dest.writeString(getFechaModificacion());

        dest.writeString(fechaRegistro);
        dest.writeInt(validez);
    }
}
