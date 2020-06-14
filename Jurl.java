import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileWriter;

public class Jurl {

    public static void main(String[] args) {

        String urlString;
        boolean i_showResponseHeaders = false;
        String Method = "GET";
        boolean f_redirect = false;
        boolean o_saveResponseBody = false;
        String o_fileName = null;
        HashMap<String, String> headersHashMap = null;
        boolean h_requestsHeaders = false;
        boolean d_formdataMesssageBody = false;
        HashMap<String, String> body = new HashMap<>();
        boolean Gui = false;
        ArrayList<String> input = new ArrayList<>();
        ArrayList<String> argInput = new ArrayList<>();
        argInput.add("--method");
        argInput.add("--headers");
        argInput.add("-i");
        argInput.add("--help");
        argInput.add("-f");
        argInput.add("--output");
        argInput.add("--save");
        argInput.add("--data");
        argInput.add("--json");
        argInput.add("--upload");
        argInput.add("Gui");
        argInput.add("--binary");

        int count = 0;
        while (count < args.length && args[count] != null) {
            input.add(args[count]);
            // System.out.println(args[count]);
            count++;
        }
        urlString = input.get(0);

        if (input.contains("Gui"))
            Gui = true;

        if (input.contains("-i"))
            i_showResponseHeaders = true;
        BufferedWriter GuiWriter = null;
        File GuiError = null;
        if (Gui) {
            try {
                GuiError = new File("GuiError.txt");
                GuiError.createNewFile();

                GuiWriter = new BufferedWriter(new FileWriter(GuiError));
            } catch (IOException e) {
            }
        }

        if (input.contains("--method")) {
            int index = input.indexOf("--method");
            if (index + 1 < input.size()) {
                String methodName = input.get(index + 1);
                if (methodName.equals("GET") || methodName.equals("POST") || methodName.equals("PUT")
                        || methodName.equals("DELETE")) {
                    if (methodName.equals("POST") || methodName.equals("PUT") || methodName.equals("DELETE")) {
                        if (!(input.contains("--data") || input.contains("--binary") || input.contains("--upload"))) {
                            if (Gui) {
                                try {
                                    GuiWriter.write("no message body.");
                                    GuiWriter.flush();
                                    GuiWriter.close();
                                    return;
                                } catch (IOException ex) {
                                }
                            } else {
                                System.out.println("No message body.");
                                return;
                            }
                        }
                    }
                    Method = methodName;
                } else {
                    System.out.println("Invalid method name.");
                    return;
                }
            } else {
                System.out.println("method name is not entered.");
                return;
            }
        }

        if (input.contains("-f"))
            f_redirect = true;

        if (input.contains("list")) {
            int index = input.indexOf("list");
            if (index + 1 < input.size()) {

                String folderName = input.get(index + 1);
                try {
                    showRequestsInList(folderName);
                    return;
                } catch (Exception e) {
                    // e.printStackTrace();
                    System.out.println("No request save in " + folderName);
                    return;
                }

            } else {
                showList();
                return;
            }
        }

        if (input.contains("--output")) {
            int index = input.indexOf("--output");
            o_saveResponseBody = true;
            if (index + 1 < input.size()) {
                if (!argInput.contains(input.get(index + 1))) {
                    o_fileName = input.get(index + 1);
                }
            }
        }

        if (input.contains("--headers")) {
            int index = input.indexOf("--headers");

            if (index + 1 < input.size()) {
                if (argInput.contains(input.get(index + 1))) {
                    System.out.println("headers are not given.");
                    return;
                } else {
                    headersHashMap = new HashMap<>();
                    String headers = input.get(index + 1);
                    System.out.println(headers);
                    // headers = headers.substring(1, headers.length() - 1);
                    String[] keyValue = headers.split(";");
                    for (int i = 0; i < keyValue.length; i++) {
                        String[] seprateKeyValue = keyValue[i].split(":");
                        if (seprateKeyValue.length != 2 || seprateKeyValue[0].equals("new Header")
                                || seprateKeyValue[1].equals("new value")) {
                            try {
                                if (Gui) {
                                    GuiWriter.write("Invalid header");
                                    GuiWriter.flush();
                                    GuiWriter.close();
                                } else
                                    System.out.println("invalid header.");
                            } catch (IOException e) {
                            }
                            return;
                        } else {
                            headersHashMap.put(seprateKeyValue[0], seprateKeyValue[1]);
                            // System.out.println(seprateKeyValue[0]);
                            // System.out.println(seprateKeyValue[1]);
                        }
                    }
                    h_requestsHeaders = true;

                }
            } else {
                System.out.println("headers are not given");
                return;
            }
        }

        if (input.contains("--data")) {
            int index = input.indexOf("--data");

            if (index + 1 < input.size()) {

                if (argInput.contains(input.get(index + 1))) {
                    System.out.println("form data is not given.");
                    return;
                } else {
                    String formdatas = input.get(index + 1);
                    // formdatas = formdatas.substring(1, formdatas.length() - 1);
                    String[] keyValue = formdatas.split("&");
                    for (int i = 0; i < keyValue.length; i++) {
                        String[] seprateKeyValue = keyValue[i].split("=");
                        if (seprateKeyValue.length != 2 || seprateKeyValue[0].equals("name")
                                || seprateKeyValue[1].equals("value")) {
                            if (Gui) {
                                try {
                                    GuiWriter.write("Invalid form data.");
                                    GuiWriter.flush();
                                    GuiWriter.close();
                                } catch (IOException e) {
                                }
                            } else
                                System.out.println("invalid form data.");
                            return;
                        } else {
                            body.put(seprateKeyValue[0], seprateKeyValue[1]);
                        }
                    }
                    d_formdataMesssageBody = true;

                }
            } else {
                System.out.println("form data is not given.");
                return;
            }
        }

        if (input.contains("--upload")) {
            int index = input.indexOf("--upload");
            if (index + 1 < input.size()) {
                if (argInput.contains(input.get(index + 1))) {
                    System.out.println("file address is not entered.");
                    return;
                } else {
                    d_formdataMesssageBody = true;
                    body.put("file", input.get(index + 1));
                }

            } else {
                System.out.println("file address is not entered.");
                return;
            }

        }
        String file = null;
        if (input.contains("--binary")) {
            if (input.contains("--method")) {
                if (!input.contains("Get")) {
                    if (input.contains("--data") || input.contains("--upload")) {
                        System.out.println("cant not send formdata with binary file");
                        return;
                    }

                    int index = input.indexOf("--binary");
                    if (index + 1 < input.size()) {
                        file = input.get(index + 1);
                    }
                } else {
                    System.out.println("Wrong method.");
                    return;
                }
            } else {
                System.out.println("Wrong method.");
                return;
            }

        }

        Request request = new Request(urlString, Method, body, headersHashMap, file, i_showResponseHeaders, f_redirect,
                o_saveResponseBody, o_fileName, h_requestsHeaders, d_formdataMesssageBody, Gui);

        if (input.contains("--save")) {
            int index = input.indexOf("--save");

            if (index + 1 < input.size()) {

                if (argInput.contains(input.get(index + 1))) {
                    System.out.println("Please enter a folder name to save.");
                    return;
                } else {
                    String nameDirectory = input.get(index + 1);
                    try {
                        saveRequest(request, nameDirectory);
                    } catch (IOException e) {
                        // e.printStackTrace();
                        System.out.println("Cannot open Directory" + nameDirectory);
                        return;
                    }
                }

            } else {
                System.out.println("Please enter a folder name to save.");
                return;

            }

        }

        if (input.contains("fire")) {
            int index = input.indexOf("fire");
            if (index + 1 < input.size()) {
                if (input.get(index + 1).equals("create")) {

                    String nameCreateFolder = input.get(index +2);
                    createFolder(nameCreateFolder);
                    return;
                } else {

                    if (index + 2 < input.size()) {
                        String nameDirectory = input.get(index + 1);
                        ArrayList<Integer> requestNum = new ArrayList<>();
                        for (int i = index + 2; i < input.size(); i++) {
                            requestNum.add(Integer.parseInt(input.get(i)));
                        }
                        try {
                            fireRequest(requestNum, nameDirectory);
                            return;
                        } catch (Exception e) {
                            // e.printStackTrace();
                            System.out.println("Cannot open Directory " + nameDirectory);
                            return;
                        }
                    } else {
                        System.out.println("request number is not entered");
                        return;
                    }
                }
            } else {
                System.out.println("folder name is not entered");
                return;
            }
        }

        try {
            request.run();
        } catch (IOException e) {
            if (Gui) {
                try {
                    GuiWriter.write("Cannot open " + urlString);
                    GuiWriter.flush();
                    GuiWriter.close();
                    return;
                } catch (IOException ex) {
                }
            }
            System.out.println("Cannot open " + urlString);
            return;
        }

    }

    public static void saveRequest(Request request, String nameDirectory) throws IOException {

        File saveRequestDirectory = new File("./requests/" + nameDirectory);
        saveRequestDirectory.mkdir();

        String name = Integer.toString(new Random().nextInt(1000000000)) + ".txt";

        File saveRequestFile = new File("./requests/" + nameDirectory + "/" + name);
        saveRequestFile.createNewFile();
        FileSave saveRequest = new FileSave();
        saveRequest.save(request, saveRequestFile);
    }

    public static void showList() {
        File Directory = new File("./requests");
        File[] filesList = Directory.listFiles();
        for (int i = 0; i < filesList.length; i++) {
            System.out.println((i + 1) + ": " + filesList[i].getName());
        }
    }

    public static void showRequestsInList(String nameDirectory) throws Exception {
        File nameFile = new File("./requests/" + nameDirectory);
        if (nameFile.exists()) {
            ObjectInputStream in = null;
            File[] requestList = nameFile.listFiles();
            for (int i = 0; i < requestList.length; i++) {

                in = new ObjectInputStream(new FileInputStream(requestList[i]));
                Request request = (Request) in.readObject();
                System.out.println((i + 1) + ". url: " + request.getUrlString() + " | method: " + request.getMethod()
                        + ((!request.isH_requestsHeaders()) ? ""
                                : (" | headers : " + request.getRequestsHeaders().toString()))
                        + ((!request.isD_formdataMesssageBody()) ? ""
                                : (" | formData : " + request.getBody().toString())));

            }
            in.close();

        }
    }

    public static void fireRequest(ArrayList<Integer> requestNum, String nameDirectory) throws Exception {
        File nameFile = new File("./requests/" + nameDirectory);
        if (nameFile.exists()) {
            ObjectInputStream in = null;
            File[] requestList = nameFile.listFiles();
            for (int i = 0; i < requestNum.size(); i++) {
                System.out.println("\nRequest" + requestNum.get(i) + ":\n");
                in = new ObjectInputStream(new FileInputStream(requestList[requestNum.get(i) - 1]));
                Request request = (Request) in.readObject();
                try {
                    request.run();
                } catch (IOException e) {
                    System.out.println("cannot run request");
                }

            }

        }

    }

    public static void createFolder(String nameFolder) {
        File nameFile = new File("./requests/" + nameFolder);
        if (nameFile.exists()) {
            System.out.println(nameFolder + " Already exist.");
            return;
        } else {
            nameFile.mkdir();
            System.out.println(nameFolder+" Folder created");
            return;
        }

    }

}