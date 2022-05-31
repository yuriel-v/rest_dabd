package dabd.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import com.google.gson.Gson;

import dabd.db.dao.PatientDao;
import dabd.db.model.Patient;
import dabd.exception.InvalidFormatException;

@RequestScoped
public class Patientctl
{
    @Inject
    private PatientDao pdao;

    @Inject
    private Gson gson;

    public String findAllPatients() {
        return gson.toJson(pdao.findAll());
    }

    public String findPatient(String cpf)
    {
        Patient patient = pdao.find(cpf);
        if (patient == null)
            return "{}";
        else
            return gson.toJson(patient);
    }

    public void createPatient(String cpf, String name, String address) throws InvalidFormatException, EntityExistsException
    {
        if (cpf == null || !Patient.cpfPattern.matcher(cpf).find())
            throw new InvalidFormatException("Patient CPF is null or not in correct format");
        
        if (name == null || name.isEmpty())
            throw new InvalidFormatException("Patient name must not be blank");

        if (pdao.exists(cpf))
            throw new EntityExistsException(String.format("Patient with CPF '%s' already exists", cpf));

        Patient newPatient = new Patient(cpf, name, address);
        pdao.add(newPatient);
    }

    public void updatePatient(String cpf, String name, String address) throws InvalidFormatException, EntityNotFoundException
    {
        Patient target = pdao.find(cpf);
        if (target == null)
            throw new EntityNotFoundException(String.format("Patient with CPF '%s' does not exist", cpf));

        if (name != null)
        {
            if (name.isEmpty())
                throw new InvalidFormatException("Patient name must not be blank");
            else
                target.setName(name);
        }

        if (address != null)
        {
            if (address.isEmpty())
                target.setAddress(null);
            else
                target.setAddress(address);
        }

        pdao.update(target);
    }
}
