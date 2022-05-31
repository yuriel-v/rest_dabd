package dabd.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import com.google.gson.Gson;

import dabd.db.dao.DocDao;
import dabd.db.model.Doctor;
import dabd.exception.InvalidFormatException;

@RequestScoped
public class Doctorctl
{
    @Inject
    private DocDao ddao;

    @Inject
    private Gson gson;

    public String findAllDoctors() {
        return gson.toJson(ddao.findAll());
    }

    public String findDoctor(String crm)
    {
        Doctor patient = ddao.find(crm);
        if (patient == null)
            return "{}";
        else
            return gson.toJson(patient);
    }

    public void createDoctor(String crm, String name, String specialty) throws InvalidFormatException, EntityExistsException
    {
        if (crm == null || !Doctor.crmPattern.matcher(crm).find())
            throw new InvalidFormatException("Doctor CRM is null or not in correct format");
        
        if (name == null || name.isEmpty())
            throw new InvalidFormatException("Doctor name must not be blank");
        
        if (specialty == null || specialty.isEmpty())
            throw new InvalidFormatException("Doctor specialty must not be blank");

        if (ddao.exists(crm))
            throw new EntityExistsException(String.format("Doctor with CRM '%s' already exists", crm));

        Doctor newDoctor = new Doctor(name, crm, specialty);
        ddao.add(newDoctor);
    }

    public void updateDoctor(String crm, String name, String specialty) throws InvalidFormatException, EntityNotFoundException
    {
        Doctor target = ddao.find(crm);
        if (target == null)
            throw new EntityNotFoundException(String.format("Doctor with CRM '%s' does not exist", crm));

        if (name != null)
        {
            if (name.isEmpty())
                throw new InvalidFormatException("Doctor name must not be blank");
            else
                target.setName(name);
        }

        if (specialty != null)
        {
            if (specialty.isEmpty())
                throw new InvalidFormatException("Doctor specialty must not be blank");
            else
                target.setSpecialty(specialty);
        }

        ddao.update(target);
    }
}
