package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;

public class ProducerImpl implements Producer{
    
    private final String codFis;
    private final String name;
    private final String surname;
    private final Date birthday;
    private final String piva;

    
    
    /**
     * @param codFis
     * @param name
     * @param surname
     * @param birthday
     * @param piva
     */
    public ProducerImpl(String codFis, String name, String surname, Date birthday, String piva) {
        this.codFis = codFis;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.piva = piva;
    }

    @Override
    public String getCodFis() {
        return codFis;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public Date getDateBirth() {
        return birthday;
    }

    @Override
    public String getPIVA() {
        return piva;
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, codFis, name, piva, surname);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProducerImpl other = (ProducerImpl) obj;
        return Objects.equals(birthday, other.birthday) && Objects.equals(codFis, other.codFis)
                && Objects.equals(name, other.name) && Objects.equals(piva, other.piva)
                && Objects.equals(surname, other.surname);
    }

    @Override
    public String toString() {
        return "ProducerImpl [codFis=" + codFis + ", name=" + name + ", surname=" + surname + ", birthday=" + birthday
                + ", piva=" + piva + "]";
    }
    
    

}
