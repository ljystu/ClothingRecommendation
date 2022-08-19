package dhu.rs.controller;

import dhu.rs.common.Constants;
import dhu.rs.util.SslUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.client.methods.HttpPost;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.*;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取token类
 */
public class AuthService {
    private static HttpPost method = null;
    private static HttpResponse response = null;

    /**
     * 获取权限token
     *
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public static String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "l0tRxjmV69GTXH98HF7QcGfV";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "gGy4qd6BpNtbLeqvsGkUTqf5iFj6drli";
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            if ("https".equalsIgnoreCase(realUrl.getProtocol())) {
                SslUtils.ignoreSsl();
            }
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static Map<String, Object> words(String params) throws IOException, URISyntaxException {
        String access_token = getAuth();
        List<String> list = new ArrayList<String>();
        System.out.println(access_token);
        if (access_token == null)
            return null;
        try {
            String POST_URL = "https://aip.baidubce.com/rpc/2.0/nlp/v1/lexer?charset=UTF-8&access_token=" + access_token;

            SslUtils.ignoreSsl();
            SSLContext sslContext = SSLContextBuilder.create().useProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((x, y) -> true).build();
            RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
            HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setSSLContext(sslContext).setSSLHostnameVerifier((x, y) -> true).build();

//            HttpClient httpClient = new DefaultHttpClient();
            params = "{\"text\":\"" + params + "\"}";
            method = new HttpPost(POST_URL);
            method.addHeader("Content-type", "application/json; charset=utf-8");
            method.setHeader("Accept", "application/json");
            method.setEntity(new StringEntity(params, Charset.forName("UTF-8")));

            response = httpClient.execute(method);
            String licenseStr = EntityUtils.toString(response.getEntity());
            System.out.println(licenseStr);
            JSONObject jsonObject = new JSONObject(licenseStr);
            JSONArray items = jsonObject.getJSONArray("items");
//            System.out.println(items);
            System.out.println(params);
            for (int i = 0; i < items.toList().size(); i++) {
                JSONObject sub = (JSONObject) items.get(i);
                System.out.println(sub.get("item"));
                list.add(sub.get("item").toString());
            }
            Map<String, Object> map = getAllCategory(list);
            return map;
            //            return list;
        } catch (Exception e) {
            System.err.printf("获取结果失败！");
            e.printStackTrace(System.err);
        }

        return null;
    }

    public static Map<String, Object> getAllCategory(List<String> list) {
        Map<String, Object> categories = new HashMap<String, Object>();
        List<String> casual = new ArrayList<String>();
        List<String> sport = new ArrayList<String>();
        List<String> work = new ArrayList<String>();
        List<String> formal = new ArrayList<String>();
        List<String> outdoors = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            if (Constants.MALE.contains(list.get(i)) && !categories.containsKey("gender")) {
                categories.put("gender", "M");
            } else if (Constants.FEMALE.contains(list.get(i)) && !categories.containsKey("gender"))
                categories.put("gender", "F");
            if (Constants.CASUAL.contains(list.get(i))) {
                casual.add(list.get(i));
            }
            if (Constants.SPORT.contains(list.get(i))) {
                sport.add(list.get(i));
            }
            if (Constants.FORMAL_OCCASION.contains(list.get(i)))
                formal.add(list.get(i));
            if (Constants.WORK.contains(list.get(i)))
                work.add(list.get(i));
            if (Constants.OUTDOORS.contains(list.get(i)))
                outdoors.add(list.get(i));
        }

        List<Integer> length = new ArrayList<Integer>();
        length.add(casual.size());
        length.add(sport.size());
        length.add(work.size());
        length.add(formal.size());
        length.add(outdoors.size());
        int len=0;
        for (int i = 0; i < length.size(); i++) {
            len+=length.get(i);
        }
        if(len==0){
            return categories;
        }
        int[] indexes = max(length);
        if (indexes[2] == 1) {
            categories.put("goodsCategoryId", Constants.CATEGORY[indexes[0]][0]);
            categories.put("subCategoryId", Constants.CATEGORY[indexes[1]][0]);
        } else {
            categories.put("goodsCategoryId", Constants.CATEGORY[indexes[0]][0]);
            categories.put("subCategoryId", Constants.CATEGORY[indexes[0]][1]);
        }


        return categories;
    }

    public static int[] max(List<Integer> list) {

        List<Integer> indexlist = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++)
            indexlist.add(list.get(i));

        for (int i = 0; i < indexlist.size(); i++) {
            for (int j = i; j < indexlist.size(); j++) {
                if (indexlist.get(i) < indexlist.get(j)) {
                    int temp = indexlist.get(i);
                    indexlist.set(i, indexlist.get(j));
                    indexlist.set(j, temp);
                }
            }
        }
        System.out.println(indexlist);
        System.out.println(list);
        int[] indexes = new int[3];
        System.out.println(indexlist.get(0));
        indexes[0] = list.indexOf(indexlist.get(0));
        indexes[1] = list.indexOf(indexlist.get(1));
        if (indexes[1] == indexes[0]) {
            for (int i = indexes[1] + 1; i < list.size(); i++) {
                if (list.get(i) == indexes[1])
                    indexes[1] = i;
            }
            indexes[2] = 1;
        } else
            indexes[2] = 0;
        System.out.println(indexes[0]);
        System.out.println(indexes[1]);
        return indexes;
    }

    public static void main(String[] args) {
        try {
            long now = System.currentTimeMillis();
            Map<String, Object> list = words("女士正装工作");
            System.out.println("耗时：" + (System.currentTimeMillis() - now) / 1000 + "s");
            System.out.println(list);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
//        List<Integer> list = new ArrayList<Integer>();
//        for (int i = 0; i < 5; i++)
//            list.add((int) (1+Math.random()*10));
//        System.out.println(list);
//        System.out.println(max(list));
    }
}