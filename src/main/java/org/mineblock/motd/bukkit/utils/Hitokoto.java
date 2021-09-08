package org.mineblock.motd.bukkit.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Hitokoto{
    private String hitokoto;
    private String hitokotofrom;

    public Hitokoto() throws IOException{
        URL url = new URL("https://v1.hitokoto.cn/");
        HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
        httpUrlConn.setDoInput(true);
        httpUrlConn.setRequestMethod("GET");
        httpUrlConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; DreamVoid; DigExt)");
        InputStream input = httpUrlConn.getInputStream();
        InputStreamReader read = new InputStreamReader(input, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(read);
        String data = br.readLine();
        while (data != null) {
            HashMap map = new Gson().fromJson(data, HashMap.class);
            hitokoto = (String) map.get("hitokoto");
            hitokotofrom = (String) map.get("from");
            data = br.readLine();
        }
        br.close();
        read.close();
        input.close();
        httpUrlConn.disconnect();
    }

    public String getHitokoto(){
        return hitokoto;
    }

    public String getHitokotoFrom(){
        return hitokotofrom;
    }
}
