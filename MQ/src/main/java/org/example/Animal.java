package org.example;

import com.baidu.ai.aip.utils.Base64Util;
import com.baidu.ai.aip.utils.FileUtil;
import com.baidu.ai.aip.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * 动物识别
 */
public class Animal {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String animal() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
        try {
            //String base64String="/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAsJCQcJCQcJCQkJCwkJCQkJCQsJCwsMCwsLDA0QDBEODQ4MEhkSJRodJR0ZHxwpKRYlNzU2GioyPi0pMBk7IRP/2wBDAQcICAsJCxULCxUsHRkdLCwsLCwsLCwsLCwsLCwsLCwsLCwsLCwsLCwsLCwsLCwsLCwsLCwsLCwsLCwsLCwsLCz/wAARCAEhASADASIAAhEBAxEB/8QAGwABAAEFAQAAAAAAAAAAAAAAAAECAwQFBgf/xABDEAABAwIEBAQEAggEBAcBAAABAAIDBBEFEiExQVFhcQYTIoEUkaGxMlIVIzNCYsHR8ENygpIkY+HxFiU0U1RzdLL/xAAaAQEAAwEBAQAAAAAAAAAAAAAAAQMEBQIG/8QALBEAAgIBBAIBAwMEAwAAAAAAAAECAxEEEiExE0EyFCJRBWFxFYGh8EKxwf/aAAwDAQACEQMRAD8A9Wu6+5UXdzKcSiAXdzKXdzKhRvtt90BNyeJ0vZBdo/ESSdSSmiICbnmUu7mVTt2+ylATd3Mpd3MqEQE3dzKXdzKhEBN3cyhceZUIUAJcbalLne5vZALIgJueZS7uZUdfoiAm7uZS7uZVIfGXyRh7C+PL5jGuaXMzDM3O0G4uNQsCrxjDaOuw3DpXuNXiEgZFHGA7ywQ4tfLrcBxBA0+guBKTfCNjd3Mpd3MrSY/4io8AZRumifPJUyOJiicBJHSxi8tQRY6N0sNL81uGPZIyOSNwcyRjZGOGzmuAcCO6jKzgOLSyV3dzKXdzKhO2/wBlJBJJ2udteypsb5iSbbDl1U6BEBNzzKXdzKi3Ee6aHZATd3Mpd3MqEQE3dzKXdzKhEBN3cyl3cyoRAC423Pe6lt7i543UW1Ujcd0A4lQjja5QEEXCAjft91KIgCIiAKNu32UogCKNu32UoAiJ04/ZANeHv0TRavE8ewTB7MrKn/iHC8dJTtM1XJttEzUdzYLlavxfj9VduH0tPh0Owlq7VVWRwIjbaJvvdVztjD5MurpnZ8Ud/YnYG3ZW3T07HZXTQNde2V0sYN+xN15VUGvrSTiGJYhV31LJKh8cIPSKHKz6LHGHYb/8SAnm5gcfcuuVmesj6RsWgk+2evSSQwxmaaWKKFoBdLLIxkYHMucbLj8Y8bUkbXQYL/xErj5fxrmXpWPOlqdrreY/l+6NybaHk/gqG7CaeI5Pwgglo7NJy/RVvp6eTKZIYnlos3OxpyjkLhVy1mekWQ0KTzJ5NnhmNfoahq4aV7cU8RYlUOqsQqMxkoqSVwyNZLUDR7mDcNJF762Guuw6rp6fEjjmITSVb6aSZ8DQQanF8WfH5X6po2ihaSM2wvYXtZRLE6SIxNkMTSMpMTWh2U6ZQToPkrdPSU1Lfy2esjKXvN3kcr8ugVb1GeS9adJNL2TWHFcWqJXSgTYvjR+Apoox+qpoXbxx8mRi5ceJ146+s08Apqelpm3LaeCGBpItcRsDAfovJ3U1PK4ukZ5j3ANBfu0DYMtsFkU82K4eWmgxOupw038symopz0MVRmb8rL3TqIxy5dsq1GmlNJQ6R6p23+yDZcPR+NK2nLWYzRNliFr1mGB129ZaZ5v/ALXey6+hxDDsSgFTQVMVRDexdEdWO/LI0+oHoQFvhZGfxZzLKp18SRkoiL2VBRtt7qUQBFG2vz6qd9UAREugF7IBbfdBzO/2RAFI3HdQpG47oC3q88QRve9rXVYAGw5JoDbhrboiAlFCICUUIgJUIiAJoB0RYGK4ph2FUclVWvIZcRxRxjNNPMdWxQt4uP03OiPglJvhGVU1NJSU8tVVTxwU8Tc8ksrg1jR3PHkFwuJeK8SxHPBhHmUFAdDWyNtW1A4mBh0Y08Cdey1lfW1+NTRz17WxxRHNS0LHZoYDwdJwc/r/AGLa512qfxgdWjRpfdYWoYIYS8saS95LpJHuL5ZHHUl73eon3V1EWFtvs6KWAiIoJCIiAJv3RVDTNoduCkhvBUGgC9+Gv9FQXX7ck";
            String base64String="";
            byte[] imageData=Base64.getDecoder().decode(base64String);
            try {
                FileOutputStream outputStream = new FileOutputStream("image.jpg");
                outputStream.write(imageData);
                outputStream.close();
                System.out.println("Image saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving image: " + e.getMessage());
            }
            // 本地文件路径
            //String filePath = "D:\\图片管理\\得力木\\dog.jpg";
            String filePath="image.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            //System.out.println(imgParam);

            //String imgParam="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";
            String param = "image=" + imgParam;
            String api_key="hhF526QnIx7FiF8Aw0uBcvEP";
            String api_secret="Aa08pOmtVrtpuwk1EahpQhragn42KahB";
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            Sample sample=new Sample();
            String accessToken=sample.getAccessToken(api_key,api_secret);
            System.out.println("accessToken="+accessToken);
            //String accessToken = "[调用鉴权接口获取的token]";

            String result = HttpUtil.post(url, accessToken, param);
//            Gson gson=new Gson();
//            JsonObject jsonObject=gson.fromJson(result, JsonObject.class);
//            //获取result数组
//            JsonArray resultArray=jsonObject.getAsJsonArray("result");
//            double maxScore=Double.MIN_VALUE;
//            String topName="";
//            //遍历整个数组
//            for(int i=0;i<resultArray.size();i++){
//                JsonObject resultObject=resultArray.get(i).getAsJsonObject();
//                double score=Double.parseDouble(resultObject.get("score").getAsString());
//                if(score>maxScore){
//                    maxScore=score;
//                    topName=resultObject.get("name").getAsString();
//                }
//            }
            System.out.println(result);
//            System.out.println("与图片最相似的动物是:"+topName);
//            return topName;
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Animal.animal();
    }
}



class Sample {

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public String getAccessToken(String api_key,String api_secret) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token?client_id="+api_key+"&client_secret="+api_secret+"&grant_type=client_credentials")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        //System.out.println(response.body().string());
        String jsonString=response.body().string();
        Gson gson=new Gson();
        JsonObject jsonObject=gson.fromJson(jsonString, JsonObject.class);
        String accessToken= String.valueOf(jsonObject.get("access_token"));
        return accessToken;
    }
}