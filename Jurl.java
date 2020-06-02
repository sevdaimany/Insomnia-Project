import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Jurl {

    public static void main(String[] args) {

    }

    public void saveRequest(Request request, String nameDirectory) throws IOException {

        File saveRequestDirectory = new File("./requests/" + nameDirectory);
        saveRequestDirectory.mkdir();

        String name = Integer.toString(new Random().nextInt(1000000000)) + ".txt";

        File saveRequestFile = new File("./requests/" + nameDirectory + "/" + name);
        saveRequestFile.createNewFile();
        FileSave saveRequest = new FileSave();
        saveRequest.save(request, saveRequestFile);
    }

    public void showList() {
        File Directory = new File("./requests");
        File[] filesList = Directory.listFiles();
        for (int i = 0; i < filesList.length; i++) {
            System.out.println((i + 1) + ": " + filesList[i].getName());
        }
    }

    public void showRequestsInList(String nameDirectory) throws Exception {
        File nameFile = new File("./requests/" + nameDirectory);
        if (nameFile.exists()) {
            ObjectInputStream in =null;
            File[] requestList = nameFile.listFiles();
            for (int i = 0; i < requestList.length; i++) {

                in = new ObjectInputStream(new FileInputStream(requestList[i]));
                Request request = (Request)in.readObject();
                System.out.println((i + 1) + ". url: " +request.getUrl()+" | method: "+request.getMethod() + ((request.getRequestsHeaders().size() == 0)?"":(" | headers : "+ request.getRequestsHeaders().toString()) ) +((request.getBody().size() == 0)?"":(" | formData : "+ request.getBody().toString()) ) ); 

            }
            in .close();

        }
    }

}