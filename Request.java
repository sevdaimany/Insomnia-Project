import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.*;

public class Request {
    private String urlString;
    private URL url;
    private transient HttpURLConnection connection;
    private final String Method;
    private HashMap<String, String> body;
    private HashMap<String, String> requestsHeaders;
    private String file;
    private final boolean i_showResponseHeaders;
    private final boolean f_redirect;
    private final boolean o_saveResponseBody;
    private final String o_fileName;
    private final boolean s_saveReguest;

    public Request(String urlString, String method, HashMap<String, String> body,
            HashMap<String, String> requestsHeaders, String file, boolean i_showResponseHeaders, boolean f_redirect,
            boolean o_saveResponseBody, String o_fileName, boolean s_saveReguest) {
        this.urlString = urlString;
        Method = method;
        this.body = body;
        this.requestsHeaders = requestsHeaders;
        this.file = file;
        this.i_showResponseHeaders = i_showResponseHeaders;
        this.f_redirect = f_redirect;
        this.o_saveResponseBody = o_saveResponseBody;
        this.o_fileName = o_fileName;
        this.s_saveReguest = s_saveReguest;

    }

    public URL getUrl() {
        return url;
    }

    public String getMethod() {
        return Method;
    }

    public HashMap<String, String> getBody() {
        return body;
    }

    public void setBody(HashMap<String, String> body) {
        this.body = body;
    }

    public HashMap<String, String> getRequestsHeaders() {
        return requestsHeaders;
    }

    public void setRequestsHeaders(HashMap<String, String> requestsHeaders) {
        this.requestsHeaders = requestsHeaders;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }

    public boolean isI_showResponseHeaders() {
        return i_showResponseHeaders;
    }

    public boolean isF_redirect() {
        return f_redirect;
    }

    public boolean isO_saveResponseBody() {
        return o_saveResponseBody;
    }

    public String getO_fileName() {
        return o_fileName;
    }

    public boolean isS_saveReguest() {
        return s_saveReguest;
    }

    public void run() throws IOException {

        if (isF_redirect()) {
            String location = connection.getHeaderField("Location");
            url = new URL(location);
        } else {
            url = new URL(urlString);
        }

        if (url.getProtocol().equals("http")) {
            connection = (HttpURLConnection) url.openConnection();
        } else if (url.getProtocol().equals("https")) {
            connection = (HttpsURLConnection) url.openConnection();
        } else {
            System.err.println("Unsupported protocol");
            return;
        }

    }

    public void printResponseBody() throws IOException {
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

    public void printResponseHeaders() throws IOException {
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("RESPONCE");
            // print result
            System.out.println("Request Method: " + connection.getRequestMethod());
            Map<String, List<String>> map = connection.getHeaderFields();

            for (String key : map.keySet()) {
                System.out.print(key + ": ");

                List<String> values = map.get(key);

                for (String aValue : values) {
                    System.out.println("\t" + aValue);
                }
            }
            System.out.println("\n");

        } else

        {
            System.out.println("request not worked");
        }
    }

    public void redirect(HttpURLConnection connection) throws IOException {
        System.out.println("REDIRECT");
        String location = connection.getHeaderField("Location");
        URL newUrl = new URL(location);
        connection = (HttpURLConnection) newUrl.openConnection();

    }

    public void requestHeaders() {

        for (String key : requestsHeaders.keySet()) {

            connection.setRequestProperty(key, requestsHeaders.get(key));
        }

    }

    public void sendGet() throws IOException {
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "insomnia/7.1.1");
        connection.setConnectTimeout(5000);
        int status = connection.getResponseCode();

        if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
            redirect(connection);
        }

    }

    public void sendPostPutDelete() throws IOException {
        connection.setRequestMethod(Method);
        connection.setRequestProperty("User-Agent", " insomnia/7.1.1");
        connection.setDoOutput(true);
        printResponseHeaders();
        printResponseBody();
        connection.setConnectTimeout(5000);

    }

    public void bufferOutFormData(String boundary, BufferedOutputStream bufferedOutputStream) throws IOException {
        for (String key : body.keySet()) {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            if (key.contains("file")) {
                bufferedOutputStream.write(("Content-Disposition: form-data; filename=\""
                        + (new File(body.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());
                try {
                    BufferedInputStream tempBufferedInputStream = new BufferedInputStream(
                            new FileInputStream(new File(getBody().get(key))));
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

    public void uploadBinary() {
        try {
            connection.setRequestProperty("Content-Type", "application/octet-stream");
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

    public void formData() {
        try {
            String boundary = System.currentTimeMillis() + "";
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            BufferedOutputStream request = new BufferedOutputStream(connection.getOutputStream());
            bufferOutFormData(boundary, request);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            System.out.println(new String(bufferedInputStream.readAllBytes()));
            // System.out.println(connection.getResponseCode());
            // System.out.println(connection.getHeaderFields());
        } catch (Exception e) {

        }
    }

    public void postJSON() {
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
    }

    public static void main(String[] args) throws IOException {
        // uploadBinary();
        // formData();
        // sendPost();
    }

}