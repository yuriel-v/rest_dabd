package dabd.db.dao;

import java.util.List;

import javax.persistence.EntityManager;

import dabd.db.model.Doctor;

public class DocDao extends Dao<Doctor>
{
    public DocDao(EntityManager em) {
        super(em);
    }

    @Override
    public List<Doctor> findByPk(String primaryKey) {
        return Dao.castList(Doctor.class, this.em
            .createNativeQuery(  // SQL: JPA não define um método para acha mais de 1 registro
                String.format("SELECT * FROM doctors WHERE crm LIKE \"%%%s%%\"", primaryKey)
            )
            .getResultList());
    }

    @Override
    public Doctor find(String primaryKey) {
        return this.em.find(Doctor.class, primaryKey);
    }

    @Override
    public boolean exists(String primaryKey) {
        return this.em.find(Doctor.class, primaryKey) != null;
    }

    public List<Doctor> findAll() {
        return this.em.createQuery(
            "SELECT doc FROM Doctor doctors", 
            Doctor.class
        ).getResultList();
    }
}
