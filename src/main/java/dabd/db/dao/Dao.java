package dabd.db.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public abstract class Dao<T>
{
    protected EntityManager em;

    public Dao() {}

    public Dao(EntityManager em) {
        this.em = em;
    }

    // create
    public void add(T entity) {
        EntityTransaction t = em.getTransaction();
        t.begin();
        try
        {
            em.persist(entity);
            t.commit();
        }
        catch (Exception e)
        {
            if (t.isActive())
                t.rollback();
            throw e;
        }
    }

    public void addMany(List<T> entities)
    {
        EntityTransaction t = em.getTransaction();
        t.begin();
        try
        {
            for (T entity : entities) {
                em.persist(entity);
            }
            t.commit();
        }
        catch (Exception e)
        {
            if (t.isActive())
                t.rollback();
            throw e;
        }
    }

    // read
    /* Nota: existe uma certa duplicação de código nas implementações
     * DocDao e PatientDao, pois não sei como generalizar esses métodos.
     */
    public abstract List<T> findByPk(String primaryKey);
    public abstract T find(String primaryKey);
    public abstract boolean exists(String primaryKey);

    // update
    public void update(T entity) {
        EntityTransaction t = em.getTransaction();
        t.begin();
        try
        {
            em.merge(entity);
            t.commit();
        }
        catch (Exception e)
        {
            if (t.isActive())
                t.rollback();
            throw e;
        }
    }

    // delete
    public void delete(T entity) {
        EntityTransaction t = em.getTransaction();
        t.begin();
        try
        {
            em.remove(entity);
            t.commit();
        }
        catch (Exception e)
        {
            if (t.isActive())
                t.rollback();
            throw e;
        }
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
