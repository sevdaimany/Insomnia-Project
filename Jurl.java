import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Jurl {

    public static void main(String[] args) {

    }

    public void saveRequest(Request request, String nameDirectory) throws IOException {

        File saveRequestDirectory = new File("./requests/" + nameDirectory);
        saveRequestDirectory.mkdir();

        String name = Integer.toString(new Random().nextInt(1000000000)) + ".txt";

        File saveRequestFile = new File("./requests/" + "sevda" + "/" + name);
        saveRequestFile.createNewFile();
        FileSave saveRequest = new FileSave();
        saveRequest.save(request, saveRequestFile);
    }

}