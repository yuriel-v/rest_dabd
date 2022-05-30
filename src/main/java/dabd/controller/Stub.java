package dabd.controller;

//import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.google.gson.Gson;

import dabd.db.dao.DocDao;
import dabd.db.dao.SampleDao;
import dabd.db.model.Doctor;
import dabd.db.model.Patient;
import dabd.db.model.SampleUser;
import dabd.exception.InvalidFormatException;

@RequestScoped
public class Stub
{
    @Inject
    private DocDao docDao;

    @Inject
    private SampleDao sampleDao;

    /**
     * Adiciona um novo doutor. <p>
     *  
     * @param crm O CRM do doutor
     * @param name O nome do doutor
     * @param specialty A especialidade do doutor
     * @return Inteiro com o resultado da operação:
     * 
     * <ul>
     * <li> 0 = OK
     * <li> 1 = doutor já existe
     * </ul>
     * @throws InvalidFormatException Quando algum parâmetro é vazio ou formatado incorretamente
     */
    public int addDoctor(String crm, String name, String specialty) throws InvalidFormatException
    {
        if (crm.isEmpty() || name.isEmpty() || specialty.isEmpty() || !Doctor.crmPattern.matcher(crm).find()) {
            throw new InvalidFormatException("One or more parameters empty OR formatted incorrectly");
        }

        if (docDao.exists(crm)) {
            return 1;
        }

        else try
        {
            Doctor newDoctor = new Doctor(name, crm, specialty);
            docDao.add(newDoctor);
            return 0;
        }
        catch (Exception e) {  // só pra não deixar passar despercebido
            System.err.println(e.getMessage());
            throw e;
        }
    }

    /**
     * Procura por um doutor existente.
     * @param crm O CRM do doutor
     * @return A instância de Doctor em uma string JSON
     * @throws InvalidFormatException Quando o formato do CRM não é o esperado
     */
    public String findDoctor(String crm) throws InvalidFormatException
    {
        if (!Doctor.crmPattern.matcher(crm).find()) {
            throw new InvalidFormatException("Doctor CRM format is invalid");
        }

        Doctor doc = docDao.find(crm);
        if (doc == null) {
            return "{}";
        }
        else { 
            Gson gson = new Gson();
            return gson.toJson(doc);
        }
    }

    /**
     * Procura por um SampleUser existente.
     * @param cpf O CPF do SampleUser
     * @return A instância de SampleUser em uma string JSON
     * @throws InvalidFormatException Quando o formato do CPF não é o esperado
     */
    public String findSample(String cpf) throws InvalidFormatException
    {
        if (cpf == null || !Patient.cpfPattern.matcher(cpf).find()) {
            throw new InvalidFormatException("Doctor CRM format is invalid");
        }

        SampleUser sample = sampleDao.find(cpf);
        if (sample == null) {
            return "{}";
        }
        else { 
            Gson gson = new Gson();
            return gson.toJson(sample);
        }
    }
}
