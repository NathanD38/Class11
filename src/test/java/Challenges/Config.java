package Challenges;

import com.google.gson.JsonElement;

public class Config {
    private JsonElement url;
    private JsonElement browser;

    public Config() {}

    public Config(JsonElement url) {
        super();
        this.url = url;
    }


    public String getUrl() {
        return url.getAsString();
    }
}
