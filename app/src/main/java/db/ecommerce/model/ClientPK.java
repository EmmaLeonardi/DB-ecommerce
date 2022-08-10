package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;

/**
 * This is a Client with a Cod_cliente memorized
 * */
public class ClientPK extends ClientImpl {
    
    private final int Cod_cliente;

    public ClientPK(int Cod_cliente, String codFis, String name, String surname, Date date, String email, int phoneNumber,
            String resStreet, int resNumber, String resCity, String bankInfo) {
        super(codFis, name, surname, date, email, phoneNumber, resStreet, resNumber, resCity, bankInfo);
        this.Cod_cliente=Cod_cliente;
    }
    
    public ClientPK(int Cod_cliente, Client c) {
        super(c.getCodFis(), c.getName(), c.getSurname(), c.getDateBirth(), c.getEmail(), c.getPhoneNumber(), c.getResStreet(), c.getResNumber(), c.getResCity(), c.getBankInfo());
        this.Cod_cliente=Cod_cliente;
    }

    /**
     * @return the cod_cliente
     */
    public int getCod_cliente() {
        return Cod_cliente;
    }
    
    public static Client convertToClient(final ClientPK c) {
        return new ClientImpl(c.getCodFis(), c.getName(), c.getSurname(), c.getDateBirth(), c.getEmail(), c.getPhoneNumber(), c.getResStreet(), c.getResNumber(), c.getResCity(), c.getBankInfo());
    }

    @Override
    public String toString() {
        return "ClientPK [Cod_cliente=" + Cod_cliente +super.toString()+ "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(Cod_cliente);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientPK other = (ClientPK) obj;
        return Cod_cliente == other.Cod_cliente&&convertToClient(other).equals(convertToClient(this));
    }
    
    
    
    
    

}
