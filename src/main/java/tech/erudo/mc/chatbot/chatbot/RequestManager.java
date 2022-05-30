package tech.erudo.mc.chatbot.chatbot;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestManager {

    private String uri;

    public RequestManager(String appKey) {
        this.uri = "https://app.cotogoto.ai/webapi/noby.json?appkey=" + appKey + "&study=1";
    }

    public RequestManager addParameter(String param) {
        this.uri = this.uri + "&" + param;
        return this;
    }

    /**
     *
     * @return レスポンス
     */
    public String send() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(getRequest(), HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private HttpRequest getRequest() {
        return HttpRequest.newBuilder(
                URI.create(uri))
                .header("accept", "application/json")
                .build();
    }

}
