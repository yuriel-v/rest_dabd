package dabd.db.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;

@Embeddable
public class AppointmentId implements Serializable
{
    public static final long serialVersionUID = 4L;

    private String docId;
    private String patientId;
    private LocalDate appointmentDate;

    public AppointmentId() {}

    public AppointmentId(String docId, String patientId, LocalDate appointmentDate)
    {
        super();
        this.docId = docId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
    }

    // getters/setters
    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appointmentDate == null) ? 0 : appointmentDate.hashCode());
        result = prime * result + ((docId == null) ? 0 : docId.hashCode());
        result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        AppointmentId other = (AppointmentId) obj;
        if (appointmentDate == null) {
            if (other.appointmentDate != null)
                return false;
        }
        else if (!appointmentDate.equals(other.appointmentDate))
            return false;

        if (docId == null) {
            if (other.docId != null)
                return false;
        }
        else if (!docId.equals(other.docId))
            return false;

        if (patientId == null) {
            if (other.patientId != null)
                return false;
        }
        else if (!patientId.equals(other.patientId))
            return false;

        return true;
    }
}
