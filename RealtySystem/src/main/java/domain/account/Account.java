package domain.account;

import javax.persistence.*;
import java.util.Date;

/***
 * Represents a account account
 */
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer id;

    @Column(unique = true)
    private String login;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column
    private String salt;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    /***
     * Constructor
     * @param login login
     * @param password password
     */
    public Account(String login, String password) {
        if (login == null) {
            throw new IllegalArgumentException("Account: login is null!");
        }
        if (password == null) {
            throw new IllegalArgumentException("Account: password is null!");
        }
        this.login = login;
        salt = AccountUtil.generateSalt();
        passwordHash = AccountUtil.getSHA256(password, salt);
        creationDate = new Date();
    }

    public Account(Account a) {
        this.id = a.id;
        this.login = a.login;
        this.passwordHash = a.passwordHash;
        this.salt = a.salt;
        this.creationDate = a.creationDate;
    }

    /***
     * For Hibernate
     */
    public Account() {

    }

    /***
     * Checks account password
     * @param password password
     * @return true is password is correct, false otherwise
     */
    public boolean checkPassword(String password) {
        return passwordHash.equals(AccountUtil.getSHA256(password, salt));
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.passwordHash = AccountUtil.getSHA256(password, salt);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != null ? !id.equals(account.id) : account.id != null) return false;
        if (login != null ? !login.equals(account.login) : account.login != null) return false;
        if (passwordHash != null ? !passwordHash.equals(account.passwordHash) : account.passwordHash != null) return false;
        return !(salt != null ? !salt.equals(account.salt) : account.salt != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
