package alice.cheshire.services;

import alice.cheshire.util.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.objects.wall.responses.PostResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vk {
    private static final int APP_ID = 0;
    private static final String CLIENT_SECRET = "";
    private static final String EMAIL = "";
    private static final String PASS = "";
    private static final String REDIRECT_URI = "";
    private final TransportClient transportClient;
    private final VkApiClient vk;
    private UserActor actor;
    private UserAuthResponse authResponse;
    private CloseableHttpClient client = HttpClients.createDefault();
    private String code;
    private String cookie;

    public Vk() {
        transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
    }

    public void authUser() throws Exception {
        post("http://vk.com/login.php",
                Maps.of(
                        "m", "1",
                        "email", EMAIL,
                        "pass", PASS));
        String remixsid = cookie.split("remixsid=")[1].split(";")[0].split(",")[0];
        String body = get("http://vk.com/login.php",
                Maps.of("app", String.valueOf(APP_ID),
                        "layout", "popup",
                        "type", "browser",
                        "settings", "32767"
                ));
        cookie = "remixsid=" + remixsid;
        String auth_hash = body.split("var auth_hash = '")[1].split("';")[0];
        String rights_hash = body.split("var app_settings_hash = '")[1].split("';")[0];
        int app_rights = 65535;
        Map<String, String> map = new HashMap<>();
        map.put("app", String.valueOf(APP_ID));
        map.put("act", "a_save_settings");
        map.put("addMember", "1");
        map.put("hash", rights_hash);
        int mask = 1;
        for (int i = 0; i < 14; i++) {
            map.put("app_settings_" + mask + "", (app_rights & mask) == 0 ? "0" : "1");
            mask *= 2;
        }
        get("http://vk.com/apps.php", map);
        String auth_json = get("http://vk.com/login.php", Maps.of(
                "app", String.valueOf(APP_ID),
                "act", "a_auth",
                "permanent", "1",
                "hash", auth_hash
        ));
        JsonObject json = (JsonObject) new JsonParser().parse(new JsonReader(new StringReader(auth_json)));
        code = json.get("code").getAsString();
        authResponse = vk.oauth().userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code).execute();
        actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
    }

    private String post(String url, Map<String, String> params) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.addParameter(entry.getKey(), entry.getValue());
        }
        HttpPost post = new HttpPost(builder.build());
        post.addHeader("Cookie", cookie);
        String body;
        try (CloseableHttpResponse resp = client.execute(post)) {
            HttpEntity entity = resp.getEntity();
            body = EntityUtils.toString(entity);
        }
        return body;
    }

    private String get(String url, Map<String, String> params) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.addParameter(entry.getKey(), entry.getValue());
        }
        HttpGet get = new HttpGet(builder.build());
        if (cookie != null) {
            get.addHeader("Cookie", cookie);
        }
        String body;
        try (CloseableHttpResponse resp = client.execute(get)) {
            Header[] headers = resp.getHeaders("Set-Cookie");
            if (headers.length != 0) {
                cookie = headers[0].getValue();
            }
            HttpEntity entity = resp.getEntity();
            body = EntityUtils.toString(entity);
        }
        return body;
    }

    public void send(Path path) throws com.vk.api.sdk.exceptions.ApiException, com.vk.api.sdk.exceptions.ClientException {
        PhotoUpload serverResponse = vk.photos().getWallUploadServer(actor).execute();
        WallUploadResponse uploadResponse = vk.upload().photoWall(serverResponse.getUploadUrl(), path.toFile()).execute();
        List<Photo> photoList = vk.photos().saveWallPhoto(actor, uploadResponse.getPhoto())
                .server(uploadResponse.getServer())
                .hash(uploadResponse.getHash())
                .execute();
        Photo photo = photoList.get(0);
        String attachId = "photo" + photo.getOwnerId() + "_" + photo.getId();
        PostResponse postResponse = vk.wall().post(actor).attachments(attachId).execute();
        postResponse.getPostId();
    }
}
