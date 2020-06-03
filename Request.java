import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Request implements Serializable {
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
    private final boolean h_requestsHeaders;
    private final boolean d_formdataMesssageBody;

    public Request(String urlString, String method, HashMap<String, String> body,
            HashMap<String, String> requestsHeaders, String file, boolean i_showResponseHeaders, boolean f_redirect,
            boolean o_saveResponseBody, String o_fileName, boolean h_requestsHeaders, boolean d_formdataMesssageBody) {
        this.urlString = urlString;
        Method = method;
        this.body = body;
        this.requestsHeaders = requestsHeaders;
        this.file = file;
        this.i_showResponseHeaders = i_showResponseHeaders;
        this.f_redirect = f_redirect;
        this.o_saveResponseBody = o_saveResponseBody;
        this.o_fileName = o_fileName;
        this.h_requestsHeaders = h_requestsHeaders;
        this.d_formdataMesssageBody = d_formdataMesssageBody;

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

    public boolean isH_requestsHeaders() {
        return h_requestsHeaders;
    }

    public boolean isD_formdataMesssageBody() {
        return d_formdataMesssageBody;
    }

    public String getUrlString() {
        return urlString;
    }


    public void run() throws IOException {
        String protocolString = urlString.substring(0, 4);
        if (!protocolString.equals("http")) {
            urlString = "http://" + urlString;
        }

        url = new URL(urlString);
        connection = (HttpURLConnection) url.openConnection();
        sendMethod();

        if (isF_redirect()) {
            while (connection.getResponseCode() > 299 && connection.getResponseCode() < 400) {
                String location = connection.getHeaderField("Location");
                url = new URL(location);
                connection = (HttpURLConnection) url.openConnection();
                sendMethod();

            }

        }

    }

    public void printResponseBody() throws IOException {
       // if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) { // success

            File fileSaveResponseBody = null;
            BufferedWriter writer = null;

            if (o_saveResponseBody) {

                if (o_fileName == null || o_fileName.isEmpty()) {
                    String name = Integer.toString(new Random().nextInt(1000000000)) + ".insomnia";
                    fileSaveResponseBody = new File("./responseBody/" + name);
                    fileSaveResponseBody.createNewFile();

                } else {
                    fileSaveResponseBody = new File("./responseBody/" + o_fileName + ".insomnia");
                    fileSaveResponseBody.createNewFile();
                }
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            if (o_saveResponseBody)
                writer = new BufferedWriter(new FileWriter(fileSaveResponseBody));

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                if (o_saveResponseBody)
                    writer.write(inputLine);
            }

            in.close();
            System.out.println(content.toString());
            System.out.println("\n");
            if (o_saveResponseBody)
                writer.close();
        // } else {
        //     System.out.println("request not worked");
        // }
    }

    public void printResponseHeaders() throws IOException {
       // if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("\n");
            // print result
            System.out.println("Request Method: " + connection.getRequestMethod());
            Map<String, List<String>> map = connection.getHeaderFields();

            for (String key : map.keySet()) {
                //if(!key.equals("null"))
                System.out.print(key + ": ");

                List<String> values = map.get(key);

                for (String aValue : values) {
                    System.out.println("\t" + aValue);
                }
            }
            System.out.println("\n");

        // } else

        // {
        //     System.out.println("request not worked");
        // }
    }

    public void requestHeaders() {

        for (String key : requestsHeaders.keySet()) {

            connection.setRequestProperty(key, requestsHeaders.get(key));
        }

    }

    // public void sendGet() throws IOException {
    // connection.setRequestMethod(Method);
    // connection.setRequestProperty("User-Agent", "insomnia/7.1.1");
    // connection.setConnectTimeout(5000);
    // if (isI_showResponseHeaders())
    // printResponseHeaders();

    // printResponseBody();
    // }

    public void sendMethod() throws IOException {
        connection.setRequestMethod(Method);
        connection.setRequestProperty("User-Agent", " insomnia/7.1.1");
        connection.setConnectTimeout(5000);

        if (!Method.equals("GET")) {
            connection.setDoOutput(true);
            if (d_formdataMesssageBody) {
                formData();
            }
        }

        if (h_requestsHeaders) {
            requestHeaders();
        }

        if (i_showResponseHeaders)
            printResponseHeaders();

        printResponseBody();
    }


    public void bufferOutFormData(String boundary, BufferedOutputStream bufferedOutputStream) throws IOException {
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