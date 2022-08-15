package db.ecommerce.model;

import java.util.Objects;

public class VehicleImpl implements Vehicle {

    private final String targa;
    private final String stato;
    private final String marca;
    private final String tipo_veicolo;

    /**
     * @param targa
     * @param stato
     * @param marca
     * @param tipo_veicolo
     */
    public VehicleImpl(String targa, String stato, String marca, String tipo_veicolo) {
        this.targa = targa;
        this.stato = stato;
        this.marca = marca;
        this.tipo_veicolo = tipo_veicolo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTarga() {
        return targa;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStato() {
        return stato;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarca() {
        return marca;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTipo_veicolo() {
        return tipo_veicolo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, stato, targa, tipo_veicolo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VehicleImpl other = (VehicleImpl) obj;
        return Objects.equals(marca, other.marca) && Objects.equals(stato, other.stato)
                && Objects.equals(targa, other.targa) && Objects.equals(tipo_veicolo, other.tipo_veicolo);
    }

    @Override
    public String toString() {
        return "VehicleImpl [targa=" + targa + ", stato=" + stato + ", marca=" + marca + ", tipo_veicolo="
                + tipo_veicolo + "]";
    }

}
