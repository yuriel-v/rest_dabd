package dabd.db.dao;

import java.util.List;

import javax.persistence.EntityManager;

import dabd.db.model.Patient;

public class PatientDao extends Dao<Patient>
{
    public PatientDao(EntityManager em) {
        super(em);
    }

    @Override
    public List<Patient> findByPk(String primaryKey) {
        return Dao.castList(Patient.class, this.em
            .createNativeQuery(  // SQL: JPA não define um método para acha mais de 1 registro
                String.format("SELECT * FROM patients WHERE crm LIKE \"%%%s%%\"", primaryKey)
            )
            .getResultList());
    }

    @Override
    public Patient find(String primaryKey) {
        return this.em.find(Patient.class, primaryKey);
    }

    @Override
    public boolean exists(String primaryKey) {
        return this.em.find(Patient.class, primaryKey) != null;
    }

    public List<Patient> findAll() {
        return this.em.createQuery(
            "SELECT patient FROM Patient patients", 
            Patient.class
        ).getResultList();
    }
}
