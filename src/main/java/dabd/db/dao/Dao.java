package dabd.db.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

public abstract class Dao<T>
{
    protected EntityManager em;

    public Dao() {}

    public Dao(EntityManager em) {
        this.em = em;
    }

    // create
    public void add(T entity) {
        em.persist(entity);
    }

    public void addMany(List<T> entities)
    {
        for (T entity : entities) {
            em.persist(entity);
        }
    }

    // read
    /* Nota: existe uma certa duplicação de código nas implementações
     * DocDao e PatientDao, pois não sei como generalizar esses métodos.
     */
    public abstract List<T> findByPk(String primaryKey);
    public abstract T find(String primaryKey);

    // update
    public void update(T entity) {
        em.merge(entity);
    }

    // delete
    public void delete(T entity) {
        em.remove(entity);
    }

    // auxiliary
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c)
    {
        List<T> r = new ArrayList<T>(c.size());
        for(Object o: c)
          r.add(clazz.cast(o));
        return r;
    }
}
