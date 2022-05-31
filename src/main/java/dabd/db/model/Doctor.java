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
@Table(name = "doctors")
public class Doctor implements Serializable
{
    public static final long serialVersionUID = 1L;
    public static final Pattern crmPattern = Pattern.compile("^[0-9]{8}-[0-9]$");

    @Id
    private String crm;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialty;

    @OneToMany(mappedBy = "doc")
    private Set<Appointment> appointments = new HashSet<>();

    public Doctor() {}

    public Doctor(String name, String crm, String specialty)
    {
        this.name = name;
        this.crm = crm;
        this.specialty = specialty;
    }

    // ordinary getters/setters
    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
