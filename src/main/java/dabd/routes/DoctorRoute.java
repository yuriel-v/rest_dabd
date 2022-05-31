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

import dabd.controller.Doctorctl;
import dabd.exception.InvalidFormatException;

@RequestScoped
@Path("/doctor")
public class DoctorRoute
{
    @Inject
    private Doctorctl dctl;

    @Inject
    private Gson gson;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPatients()
    {
        String all = dctl.findAllDoctors();
        int code = (all == "[]") ? 204 : 200; // reminder: 204 if empty else 200
        return Response
            .status(code)
            .entity(all)
            .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{crm:[0-9]{8}-[0-9]}")
    public Response getPatient(@PathParam("crm") String crm)
    {
        String body = dctl.findDoctor(crm);
        int code = (body == "{}") ? 204 : 200;
        return Response
            .status(code)
            .entity(body)
            .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPatient(HashMap<String, String> newDoctor, @Context UriInfo uriInfo)
    {
        try
        {
            dctl.createDoctor(
                newDoctor.get("crm"),
                newDoctor.get("name"),
                newDoctor.get("specialty")
            );

            URI newResource = uriInfo
                .getAbsolutePathBuilder()
                .path(newDoctor.get("crm"))
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
    @Path("{crm:[0-9]{8}-[0-9]}")
    public Response updatePatient(@PathParam("crm") String crm, HashMap<String, String> body)
    {
        if (body.isEmpty() || !(body.containsKey("name") || body.containsKey("specialty")))
            return Response.noContent().build();
        try
        {
            dctl.updateDoctor(crm, body.get("name"), body.get("specialty"));
            return Response
                .ok(gson.toJson(Map.of("updated", crm)))
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
