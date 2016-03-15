package com.alexs7;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by ar1v13 on 15/03/16.
 */
public class ResultFileWriter {

    private File runResultsFile;

    public ResultFileWriter(String fileName) {
        String userHome = System.getProperty("user.home")+File.separator+fileName;

        this.runResultsFile = new File(userHome);

        if(runResultsFile.exists()){
            runResultsFile.delete();
            createFile();

        }else{
            createFile();
        }
    }

    private void createFile() {
        try {
            runResultsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeResultToFile(String imageName, String category) {
        String line = imageName+" "+category+"\n";
        try {
            Files.write(Paths.get(runResultsFile.getAbsolutePath()),
                       line.getBytes(),
                       StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
