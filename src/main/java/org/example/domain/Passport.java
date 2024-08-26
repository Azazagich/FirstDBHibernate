package org.example.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "passport")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "dateOfBirth")
    private Date dateOfBirth;

    @Column(name = "hometown")
    private String hometown;

    @Column(name = "sex")
    private String sex;

    @Column(name = "passport_code")
    private String passportCode;

    @OneToOne (mappedBy = "passport", orphanRemoval = true)
    private User user;

    public Passport(){ }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Passport id(Integer id){
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Passport fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Passport dateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public Passport hometown(String hometown) {
        this.hometown = hometown;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Passport sex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getPassportCode() {
        return passportCode;
    }

    public void setPassportCode(String passport_code) {
        this.passportCode = passport_code;
    }

    public Passport passport_code(String passport_code) {
        this.passportCode = passport_code;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Passport user(User user){
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", hometown='" + hometown + '\'' +
                ", sex='" + sex + '\'' +
                ", passport_code='" + passportCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return Objects.equals(id, passport.id) && Objects.equals(fullName, passport.fullName) && Objects.equals(dateOfBirth, passport.dateOfBirth) && Objects.equals(hometown, passport.hometown) && Objects.equals(sex, passport.sex) && Objects.equals(passportCode, passport.passportCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, dateOfBirth, hometown, sex, passportCode);
    }
}
