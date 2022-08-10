package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;

public class ClientImpl implements Client {

    private final String codFis;
    private final String name;
    private final String surname;
    private final Date birthday;
    private final String email;
    private final int phoneNumber;
    private final String resStreet;
    private final int resNumber;
    private final String resCity;
    private final String bankInfo;

    /**
     * @param codClient
     * @param name
     * @param surname
     * @param date
     * @param email
     * @param phoneNumber
     * @param resStreet
     * @param resNumber
     * @param resCity
     * @param bankInfo
     */
    public ClientImpl(String codFis, String name, String surname, Date date, String email,
            int phoneNumber, String resStreet, int resNumber, String resCity, String bankInfo) {
        super();
        this.codFis = codFis;
        this.name = name;
        this.surname = surname;
        this.birthday = date;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.resStreet = resStreet;
        this.resNumber = resNumber;
        this.resCity = resCity;
        this.bankInfo = bankInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCodFis() {
        return this.codFis;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSurname() {
        return this.surname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getDateBirth() {
        return this.birthday;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResStreet() {
        return this.resStreet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getResNumber() {
        return this.resNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResCity() {
        return this.resCity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBankInfo() {
        return this.bankInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(bankInfo, birthday, codFis, email, name, phoneNumber, resCity, resNumber, resStreet,
                surname);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientImpl other = (ClientImpl) obj;
        return Objects.equals(bankInfo, other.bankInfo) && Objects.equals(birthday, other.birthday)
                && Objects.equals(codFis, other.codFis) && Objects.equals(email, other.email)
                && Objects.equals(name, other.name) && Objects.equals(phoneNumber, other.phoneNumber)
                && Objects.equals(resCity, other.resCity) && Objects.equals(resNumber, other.resNumber)
                && Objects.equals(resStreet, other.resStreet) && Objects.equals(surname, other.surname);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ClientImpl [codClient=" + codFis + ", name=" + name + ", surname=" + surname + ", birthday="
                + birthday + ", email=" + email + ", phoneNumber=" + phoneNumber + ", resStreet=" + resStreet
                + ", resNumber=" + resNumber + ", resCity=" + resCity + ", bankInfo=" + bankInfo + "]";
    }

}
