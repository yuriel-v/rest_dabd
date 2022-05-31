package dabd.db.dao;

import java.util.List;

import javax.persistence.EntityManager;

import dabd.db.model.SampleUser;

public class SampleDao extends Dao<SampleUser>
{
    public SampleDao(EntityManager em) {
        super(em);
    }

    @Override
    public List<SampleUser> findByPk(String primaryKey) {
        return Dao.castList(SampleUser.class, this.em
            .createNativeQuery(  // SQL: JPA não define um método para acha mais de 1 registro
                String.format("SELECT * FROM test WHERE cpf LIKE \"%%%s%%\"", primaryKey)
            )
            .getResultList());
    }

    @Override
    public SampleUser find(String primaryKey) {
        return this.em.find(SampleUser.class, primaryKey);
    }

    @Override
    public boolean exists(String primaryKey) {
        return this.em.find(SampleUser.class, primaryKey) != null;
    }
}
