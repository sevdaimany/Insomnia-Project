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
            System.out.println("request not worked");
        }
    }

    public static void printResponseHeaders(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("RESPNCE");
            // print result
            // long date = connection.getDate();
            // long expiration = connection.getExpiration();
            // long lastModified = connection.getLastModified();
             System.out.println("Request Method: "+connection.getRequestMethod());
            // System.out.println("Response Code: " + connection.getResponseCode());
            // System.out.println("Response Message: " + connection.getResponseMessage());
            // System.out.println("Content Type: " + connection.getContentType());
            // System.out.println("Content Encoding: " + connection.getContentEncoding());
            // System.out.println("Content Length: " + connection.getContentLength());
            // System.out.println("Date: " + new Date(date));
            // System.out.println("Expiration: " + new Date(expiration));
            // System.out.println("Last Modified: " + new Date(lastModified));
            // System.out.println("\n");

           Map<String, List<String>> map = connection.getHeaderFields();

            for (String key : map.keySet()) {
            System.out.print(key + ": ");

            List<String> values = map.get(key);

            for (String aValue : values) {
            System.out.println("\t" + aValue);
            }
            }


        } else

        {
            System.out.println("request not worked");
        }
    }

    public static void sendGet() throws IOException {

        URL url = new URL("https://www.google.com/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
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
        URL url = new URL("http://apapi.haditabatabaei.ir/tests/post/formdata");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        printResponseHeaders(connection);
        printResponseBody(connection);

        connection.setConnectTimeout(5000);

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

    public static void uploadBinary(String file, HttpURLConnection connection) {
        try {

            File haditabatabaei = new File(file);
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

    public static void formData(HashMap<String, String> fooBody, HttpURLConnection connection) {
        try {
            String boundary = System.currentTimeMillis() + "";
            // connection.setRequestProperty("Content-Type", "multipart/form-data;
            // boundary=" + boundary);
            BufferedOutputStream request = new BufferedOutputStream(connection.getOutputStream());
            bufferOutFormData(fooBody, boundary, request);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            System.out.println(new String(bufferedInputStream.readAllBytes()));
            // System.out.println(connection.getResponseCode());
            System.out.println(connection.getHeaderFields());
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws IOException {
        // uploadBinary();
        // formData();
         sendPost();
    }
}