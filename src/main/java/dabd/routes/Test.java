package dabd.routes;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;


@Path("/test")
public class Test
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String testEndpoint()
    {
        Gson gson = new Gson();
        return gson.toJson(Map.of("test", "okay!"));
    }
}
