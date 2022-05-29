package dabd.db.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class DaoFactory 
{
    private static final String jdbcPass = System.getenv("JDBC_ROOTPW");
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public DaoFactory() {
        this.em.setProperty("javax.persistence.jdbc.password", DaoFactory.jdbcPass);
    }

    @Produces
    public DocDao createDocDao() {
        return new DocDao(this.em);
    }
}
