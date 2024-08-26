package org.example.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

/**
 * The User class represents a user in the system.
 * A user has a unique ID, a first name, an email, a password, and an associated role.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @ManyToOne(cascade = CascadeType.ALL/*{CascadeType.REMOVE, CascadeType.PERSIST}*/ )
    @JoinColumn(name="country_id")
    private Country country;

    @OneToOne(cascade = CascadeType.ALL/*cascade = {CascadeType.REMOVE, CascadeType.PERSIST}*/,
            orphanRemoval = true)
    @JoinColumn(name="passport_id", unique = true)
    private Passport passport;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_projects",
            joinColumns = @JoinColumn(name = "user_id"),       // Зовнішній ключ на таблицю User
            inverseJoinColumns = @JoinColumn(name = "project_id")  // Зовнішній ключ на таблицю Project
    )
    private List<Project> projects;

    public Country getCountry() {
        return country;
    }

    public User country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    public User(){ }


    public Integer getId(){ return id;}

    public void setId(Integer id){
        this.id = id;
    }

    public User id(Integer id){
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User firstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User email(String email){
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public User password(String password){
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Passport getPassport() {
        return passport;
    }

    public User passport(Passport passport){
        this.passport = passport;
        return this;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public User project(List<Project> projects){
        this.projects = projects;
        return this;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", country=" + country +
                ", passport=" + passport +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                Objects.equals(country, user.country) &&
                Objects.equals(passport, user.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, email, password, role, country, passport);
    }
}
