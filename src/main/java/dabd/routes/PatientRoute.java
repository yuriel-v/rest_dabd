package dabd.routes;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

import dabd.controller.Patientctl;
import dabd.exception.InvalidFormatException;

@RequestScoped
@Path("/patient")
public class PatientRoute
{
    @Inject
    private Patientctl pctl;

    @Inject
    private Gson gson;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPatients()
    {
        String all = pctl.findAllPatients();
        int code = (all == "[]") ? 204 : 200; // reminder: 204 if empty else 200
        return Response
            .status(code)
            .entity(all)
            .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{cpf:[0-9]{11}}")
    public Response getPatient(@PathParam("cpf") String cpf)
    {
        String body = pctl.findPatient(cpf);
        int code = (body == "{}") ? 204 : 200;
        return Response
            .status(code)
            .entity(body)
            .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPatient(HashMap<String, String> newPatient, @Context UriInfo uriInfo)
    {
        try
        {
            pctl.createPatient(
                newPatient.get("cpf"),
                newPatient.get("name"),
                newPatient.get("address")
            );

            URI newResource = uriInfo
                .getAbsolutePathBuilder()
                .path(newPatient.get("cpf"))
                .build();
            return Response.created(newResource).build();
        }
        catch (Exception e)
        {
            if (e instanceof EntityExistsException || e instanceof InvalidFormatException)
                return Response
                    .status(409)
                    .entity(gson.toJson(Map.of("error",e.getMessage())))
                    .build();
            else
                throw e;
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{cpf:[0-9]{11}}")
    public Response updatePatient(@PathParam("cpf") String cpf, HashMap<String, String> body)
    {
        try
        {
            pctl.updatePatient(cpf, body.get("name"), body.get("address"));
            return Response
                .ok(gson.toJson(Map.of("updated", cpf)))
                .build();
        }
        catch (Exception e)
        {
            if (e instanceof EntityNotFoundException || e instanceof InvalidFormatException)
                return Response
                    .status(409)
                    .entity(gson.toJson(Map.of("error",e.getMessage())))
                    .build();
            else
                throw e;
        }
    }
}
