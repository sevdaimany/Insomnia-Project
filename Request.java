import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    public static void printResponseBody(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content.toString());
        } else {
            System.out.println("GET request not worked");
        }
    }

    public static void printResponseHeaders(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("RESPNCE");
            // print result
            long date = connection.getDate();
            long expiration = connection.getExpiration();
            long lastModified = connection.getLastModified();
            System.out.println("Response Code: " + connection.getResponseCode());
            System.out.println("Response Message: " + connection.getResponseMessage());
            System.out.println("Content Type: " + connection.getContentType());
            System.out.println("Content Encoding: " + connection.getContentEncoding());
            System.out.println("Content Length: " + connection.getContentLength());
            System.out.println("Date: " + new Date(date));
            System.out.println("Expiration: " + new Date(expiration));
            System.out.println("Last Modified: " + new Date(lastModified));

            // Map<String, List<String>> map = connection.getHeaderFields();

            // for (String key : map.keySet()) {
            // System.out.println(key + ":");

            // List<String> values = map.get(key);

            // for (String aValue : values) {
            // System.out.println("\t" + aValue);
            // }
            // }
            System.out.println("\n");

        } else

        {
            System.out.println("GET request not worked");
        }
    }

    public static void sendGet() throws IOException {

        URL url = new URL("https://www.google.com/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // connection.setDoOutput(true);
        // Map<String, String> parameters = new HashMap<>();
        // parameters.put("param1", "val");
        // DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        // out.writeBytes(getParamsString(parameters));
        // out.flush();
        // out.close();

        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setConnectTimeout(5000);

        int status = connection.getResponseCode();

        if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
            System.out.println("REDIRECT");
            String location = connection.getHeaderField("Location");
            URL newUrl = new URL(location);
            connection = (HttpURLConnection) newUrl.openConnection();
        }

    }

    public static void sendPost() throws IOException {

    }

    public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }

    public static void bufferOutFormData(HashMap<String, String> body, String boundary,
            BufferedOutputStream bufferedOutputStream) throws IOException {
        for (String key : body.keySet()) {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            if (key.contains("file")) {
                bufferedOutputStream.write(("Content-Disposition: form-data; filename=\""
                        + (new File(body.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());
                try {
                    BufferedInputStream tempBufferedInputStream = new BufferedInputStream(
                            new FileInputStream(new File(body.get(key))));
                    byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                    bufferedOutputStream.write(filesBytes);
                    bufferedOutputStream.write("\r\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                bufferedOutputStream.write((body.get(key) + "\r\n").getBytes());
            }
        }
        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    public static void uploadBinary() {
        try {
            URL url = new URL("http://apapi.haditabatabaei.ir/tests/post/binary");
            File haditabatabaei = new File("haditabatabaei.txt");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(haditabatabaei));
            bufferedOutputStream.write(fileInputStream.readAllBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            System.out.println(new String(bufferedInputStream.readAllBytes()));
            System.out.println(connection.getResponseCode());
            System.out.println(connection.getHeaderFields());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void formData() {
        HashMap<String, String> fooBody = new HashMap<>();
        fooBody.put("name", "hadi");
        fooBody.put("lastName", "tabatabaei");
        // fooBody.put("file", "pic2.png");
        fooBody.put("file2", "result.png");
        try {
            URL url = new URL("http://apapi.haditabatabaei.ir/tests/post/formdata");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String boundary = System.currentTimeMillis() + "";
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            BufferedOutputStream request = new BufferedOutputStream(connection.getOutputStream());
            bufferOutFormData(fooBody, boundary, request);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            System.out.println(new String(bufferedInputStream.readAllBytes()));
            System.out.println(connection.getResponseCode());
            System.out.println(connection.getHeaderFields());
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws IOException {
        // uploadBinary();
        // formData();
        sendGet();
    }
}