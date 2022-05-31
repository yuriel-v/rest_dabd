package dabd.db.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient implements Serializable
{
    public static final long serialVersionUID = 2L;
    public static final Pattern cpfPattern = Pattern.compile("^[0-9]{11}$");

    @Id
    private String cpf;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments = new HashSet<>();

    public Patient() {}
    public Patient(String cpf, String name, String address) {
        this.cpf = cpf;
        this.name = name;
        this.address = address;
    }
    

    // ordinary getters/setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
