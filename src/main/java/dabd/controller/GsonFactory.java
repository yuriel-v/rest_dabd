package dabd.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@ApplicationScoped
public class GsonFactory
{
    private Gson gson;

    public GsonFactory()
    {
        this.gson = new GsonBuilder().create();
    }

    @Produces
    public Gson createGson() {
        return this.gson;
    }
}
