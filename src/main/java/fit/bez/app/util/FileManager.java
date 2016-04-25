package fit.bez.app.util;

import java.io.*;

public class FileManager {

    public void writeEncryptData(byte[] encryptedData, String fileName) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.write(encryptedData);
        fileOutputStream.close();
    }

    public byte[] readEncryptData(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] fileData = new byte[(int) file.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        dis.readFully(fileData);
        dis.close();

        return fileData;
    }

    public String readTextFile(String fileName) throws IOException {
        String result = "";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while(line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            result = sb.toString();
        } finally {
            br.close();
        }

        return result;
    }

    public void writeTextFile(String fileName, String dataToWrite) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(dataToWrite);
        bw.flush();
    }

}
