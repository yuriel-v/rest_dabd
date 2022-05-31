package dabd.routes;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dabd.controller.Stub;
import dabd.exception.InvalidFormatException;

@RequestScoped
@Path("/test")
public class Test
{
    @Inject
    private Stub stub;
    private static final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String testEndpoint()
    {
        return gson.toJson(Map.of("test", "okay!"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sample/{cpf}")
    //@Transactional
    public Response testGetSample(@PathParam("cpf") String cpf)
    {
        try
        {
            String doc = stub.findSample(cpf);
            int code;
            if (doc != "{}") { code = 200; }
            else { code = 204; }

            return Response.status(code).entity(doc).build();
        }
        catch (InvalidFormatException e)
        {
            return Response
                .status(400)
                .entity(gson.toJson(Map.of("error", e.getMessage())))
                .build();
        }
        catch (Exception e)
        {
            System.err.println(e.getStackTrace());
            return Response
                .status(500)
                .entity(gson.toJson(Map.of("error", "Internal server error")))
                .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("doctor/{crm}")
    //@Transactional
    public Response testGetDoc(@PathParam("crm") String crm)
    {
        try
        {
            String doc = stub.findDoctor(crm);
            int code;
            if (doc != "{}") { code = 200; }
            else { code = 204; }

            return Response.status(code).entity(doc).build();
        }
        catch (InvalidFormatException e)
        {
            return Response
                .status(400)
                .entity(gson.toJson(Map.of("error", e.getMessage())))
                .build();
        }
        catch (Exception e)
        {
            System.err.println(e.getStackTrace());
            return Response
                .status(500)
                .entity(gson.toJson(Map.of("error", "Internal server error")))
                .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("doctor")
    //@Transactional
    public Response testAddDoc(HashMap<String, String> newDoc)
    {
        try
        {
            int result = stub.addDoctor(newDoc.get("crm"), newDoc.get("name"), newDoc.get("specialty"));
            if (result == 0)
            {
                return Response
                    .status(201)
                    .entity(gson.toJson(Map.of("created", newDoc.get("crm"))))
                    .build();
            }
            else
            {
                return Response
                    .status(409)
                    .entity(gson.toJson(Map.of("error", String.format("Doctor with CRM '%s' already exists", newDoc.get("crm")))))
                    .build();
            }
        }
        catch (InvalidFormatException e)
        {
            return Response
                .status(400)
                .entity(gson.toJson(Map.of("error", e.getMessage())))
                .build();
        }
        catch (Exception e)
        {
            System.err.println(e.getStackTrace());
            return Response
                .status(500)
                .entity(gson.toJson(Map.of("error", "Internal server error")))
                .build();
        }

    }
}
