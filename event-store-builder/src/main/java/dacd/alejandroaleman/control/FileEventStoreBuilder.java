package dacd.alejandroaleman.control;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dacd.alejandroaleman.control.exceptions.ReceiverException;
import dacd.alejandroaleman.control.exceptions.SaveException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FileEventStoreBuilder implements EventStoreBuilder{

    private final String userPath;

    public FileEventStoreBuilder(String userPath){
        this.userPath = userPath;
    }

    public void save(String message) throws SaveException{
            Gson gson = new Gson();
            JsonObject event = gson.fromJson(message, JsonObject.class);
            String ss = event.get("ss").getAsString();
            String ts = convertDate(event.get("ts").getAsString());
            String directoryPath = userPath + "/eventstore/prediction.Weather/" + ss;
            String filePath = directoryPath + "/" + ts + ".events";
            try {
                storeEventInFile(directoryPath, filePath, message);
                System.out.println("[Event: ]" + message + "[Stored at:] " + filePath);
            }
            catch (IOException e) {
                throw new SaveException("Error saving the event", e);
            }
    }

    private void storeEventInFile(String directoryPath, String filePath, String content) throws IOException {
        File directory = new File(directoryPath);
        File file = new File(filePath);
        if (!directory.exists()){
            directory.mkdirs();
            try {
                file.createNewFile();
            } catch (SaveException e){
                System.out.println("ERROR: " + e);
                throw new RuntimeException(e);
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(content + "\n");
        writer.close();
    }
    private String convertDate(String inputString) {
        Instant instant = Instant.parse(inputString);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, java.time.ZoneOffset.UTC);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return zonedDateTime.format(outputFormatter);
    }
}
