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
    private boolean Gui;
    private boolean png;

    public Request(String urlString, String method, HashMap<String, String> body,
            HashMap<String, String> requestsHeaders, String file, boolean i_showResponseHeaders, boolean f_redirect,
            boolean o_saveResponseBody, String o_fileName, boolean h_requestsHeaders, boolean d_formdataMesssageBody,
            boolean Gui) {
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
        this.Gui = Gui;

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

        if (connection.getHeaderField("Content-Type") != null) {
            png = connection.getHeaderField("Content-Type").equals("image/png");
            // System.out.println(png);
        }

        // InputStream in2 = connection.getInputStream();

        // byte[] bytes = null;
        // try {
        // bytes = in2.readAllBytes();

        // if (png) {
        // FileOutputStream pngfile = new FileOutputStream("GuiPreview.png");
        // pngfile.write(bytes);
        // pngfile.close();
        // }
        // if (o_saveResponseBody) {
        // String pathFile = "./responseBody/";
        // if (o_fileName == null || o_fileName.isEmpty()) {
        // String name = Integer.toString(new Random().nextInt(1000000000)) +
        // ".insomnia";
        // // fileSaveResponseBody = new File("./responseBody/" + name);
        // pathFile += name;

        // } else {
        // //fileSaveResponseBody = new File("./responseBody/" + o_fileName +
        // ".insomnia");
        // // fileSaveResponseBody.createNewFile();
        // pathFile = pathFile + o_fileName + ".insomnia";
        // }
        // FileOutputStream saveResponsdeBodyFile = new FileOutputStream(pathFile);
        // saveResponsdeBodyFile.write(bytes);
        // saveResponsdeBodyFile.close();
        // }

        // if(Gui){
        // FileOutputStream GuiResponseBody = new
        // FileOutputStream("./GuiResponseBody.txt");
        // GuiResponseBody.write(bytes);
        // GuiResponseBody.close();
        // }
        // if(!Gui){

        // for(int k = 0 ; k < bytes.length ; k++)
        // System.out.print((char)bytes[k]);

        // System.out.println("\n\n");
        // }
        // } catch (IOException e) {
        // }

        File fileSaveResponseBody = null;
        BufferedWriter writer = null;
        BufferedWriter GuiWriter = null;
        File GuiResponseBody = null;

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

        if (Gui) {
            GuiResponseBody = new File("GuiResponseBody.txt");
            GuiResponseBody.createNewFile();
        }

        InputStream inputStream = null;
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        String inputLine;
        StringBuffer content = new StringBuffer();

        if (o_saveResponseBody)
            writer = new BufferedWriter(new FileWriter(fileSaveResponseBody));

        if (Gui) {
            GuiWriter = new BufferedWriter(new FileWriter(GuiResponseBody));
        }

        while ((inputLine = in.readLine()) != null) {
            if (o_saveResponseBody)
                writer.write(inputLine);
            if (Gui) {
                GuiWriter.write(inputLine + "\n");
            } else {
                content.append(inputLine);
            }

        }

        in.close();
        if (!Gui) {
            System.out.println(content.toString());
            System.out.println("\n");
        }

        if (o_saveResponseBody)
            writer.close();
        if (Gui)
            GuiWriter.close();
        // if(png){
        // pngWriter.flush();
        // pngWriter.close();
        // }
        if (png) {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("GuiPreview.png"));
            BufferedInputStream in2 = new BufferedInputStream(new FileInputStream("GuiResponseBody.txt"));
            byte[] pngbyte = new byte[4096];
            int count;
            while (in2.available() > 0) {
                count = in2.read(pngbyte);
                out.write(pngbyte, 0, count);
            }
            out.flush();
            in2.close();
            out.close();
        }

    }

    public void printResponseHeaders() throws IOException {
        File GuiResponseHeaders = null;
        BufferedWriter GuiWriter = null;

        if (Gui) {
            GuiResponseHeaders = new File("./GuiResponseHeaders.txt");
            GuiResponseHeaders.createNewFile();

            GuiWriter = new BufferedWriter(new FileWriter(GuiResponseHeaders));
        }

        if (Gui) {
            GuiWriter.write("\n\n");
        } else
            System.out.println("\n");

        // print result
        if (Gui) {
            GuiWriter.write("Request Method: " + connection.getRequestMethod() + "\n");
        } else {
            System.out.println("Request Method: " + connection.getRequestMethod());
        }

        Map<String, List<String>> map = connection.getHeaderFields();

        for (String key : map.keySet()) {
            // if(!key.equals("null"))
            if (Gui) {
                GuiWriter.write(key + ": ");
            } else {
                System.out.print(key + ": ");
            }

            List<String> values = map.get(key);

            for (String aValue : values) {
                if (Gui) {
                    GuiWriter.write("\t" + aValue + "\n");
                } else {
                    System.out.println("\t" + aValue);
                }
            }

        }
        if (Gui) {
            GuiWriter.write("\n\n");
            GuiWriter.close();
        } else {
            System.out.println("\n");
        }

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
            if (file != null)
                uploadBinary();
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
            if(bufferedOutputStream != null)
            bufferedOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void formData() throws IOException {
        BufferedWriter GuiWriter = null;
        File GuiFormData = null;

        if (Gui) {
            GuiFormData = new File("./GuiFormData.txt");
            GuiFormData.createNewFile();

            GuiWriter = new BufferedWriter(new FileWriter(GuiFormData));
        }

        try {
            String boundary = System.currentTimeMillis() + "";
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            BufferedOutputStream request = new BufferedOutputStream(connection.getOutputStream());
            bufferOutFormData(boundary, request);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            if (Gui) {
                GuiWriter.write(new String(bufferedInputStream.readAllBytes()));
            } else {
                System.out.println(new String(bufferedInputStream.readAllBytes()));
            }
            GuiWriter.close();
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