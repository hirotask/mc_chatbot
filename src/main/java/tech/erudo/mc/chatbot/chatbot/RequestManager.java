package tech.erudo.mc.chatbot.chatbot;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    public String send() throws IOException, InterruptedException, ExecutionException {
        HttpClient client = HttpClient.newHttpClient();

        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(getRequest(), HttpResponse.BodyHandlers.ofString());

        return responseFuture.get().body();
    }

    private HttpRequest getRequest() {
        return HttpRequest.newBuilder(
                URI.create(uri))
                .header("accept", "application/json")
                .build();
    }

}
