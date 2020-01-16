package domain.realtor;

import domain.User;
import domain.account.Account;

import javax.persistence.*;

/***
 * Represents a realtor
 */
@Entity
@Table(name = "realtors")
public class Realtor implements User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "realtor_id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_website")
    private String companyWebsite;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public Realtor(Account account, String firstName, String lastName, String email, String phoneNumber, String companyName,
                   String companyWebsite) {
        if (account == null) {
            throw new IllegalArgumentException("Realtor: account is null!");
        }
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.companyWebsite = companyWebsite;
    }

    public Realtor(Realtor r) {
        account = new Account(r.getAccount());
        id = r.id;
        firstName = r.firstName;
        lastName = r.lastName;
        email = r.email;
        phoneNumber = r.phoneNumber;
        companyName = r.companyName;
        companyWebsite = r.companyWebsite;
    }

    /***
     * For Hibernate
     */
    public Realtor() {

    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Realtor realtor = (Realtor) o;

        if (id != null ? !id.equals(realtor.id) : realtor.id != null) return false;
        if (firstName != null ? !firstName.equals(realtor.firstName) : realtor.firstName != null) return false;
        if (lastName != null ? !lastName.equals(realtor.lastName) : realtor.lastName != null) return false;
        if (email != null ? !email.equals(realtor.email) : realtor.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(realtor.phoneNumber) : realtor.phoneNumber != null) return false;
        if (companyName != null ? !companyName.equals(realtor.companyName) : realtor.companyName != null) return false;
        if (companyWebsite != null ? !companyWebsite.equals(realtor.companyWebsite) : realtor.companyWebsite != null)
            return false;
        return !(account != null ? !account.equals(realtor.account) : realtor.account != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (companyWebsite != null ? companyWebsite.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Realtor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyWebsite='" + companyWebsite + '\'' +
                ", account=" + account +
                '}';
    }
}
