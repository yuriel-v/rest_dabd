package dabd.db.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
@IdClass(AppointmentId.class)
public class Appointment implements Serializable
{
    public static final long serialVersionUID = 3L;

    @Id
    @ManyToOne
    @MapsId("crm")
    private Doctor doc;

    @Id
    @ManyToOne
    @MapsId("cpf")
    private Patient patient;

    @Id
    private LocalDate appointmentDate;

    public Doctor getDoc() {
        return doc;
    }

    public void setDoc(Doctor doc) {
        this.doc = doc;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
