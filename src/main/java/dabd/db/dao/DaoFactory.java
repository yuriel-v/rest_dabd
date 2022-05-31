package dabd.db.dao;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@ApplicationScoped
public class DaoFactory 
{
    private static final String jdbcPass = System.getenv("JDBC_ROOTPW");

    private EntityManager em;

    public DaoFactory() {
        this.em = Persistence
            .createEntityManagerFactory(
                "default",
                Map.of("javax.persistence.jdbc.password", DaoFactory.jdbcPass))
            .createEntityManager();
    }

    @Produces
    public DocDao createDocDao() {
        return new DocDao(this.em);
    }

    @Produces
    public PatientDao createPatientDao() {
        return new PatientDao(this.em);
    }

    @Produces
    public SampleDao createSampleDao() {
        return new SampleDao(this.em);
    }
}
