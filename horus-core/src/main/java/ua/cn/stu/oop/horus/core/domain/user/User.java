package ua.cn.stu.oop.horus.core.domain.user;

import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
public class User extends GenericDomain {

    private String login;
    private byte[] salt;
    private String hashedPassword;
    private String email;
    private boolean emailConfirmed;
    private AvailableLocale preferredLocale;
    private Timestamp registrationDate;
    private short timeZoneUTC;
    private DBFile avatar;

    public short getTimeZoneUTC() {
        return timeZoneUTC;
    }

    public void setTimeZoneUTC(short timeZoneUTC) {
        this.timeZoneUTC = timeZoneUTC;
    }

    public DBFile getAvatar() {
        return avatar;
    }

    public void setAvatar(DBFile avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public AvailableLocale getPreferredLocale() {
        return preferredLocale;
    }

    public void setPreferredLocale(AvailableLocale preferredLocale) {
        this.preferredLocale = preferredLocale;
    }
}
