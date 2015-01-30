package ucr.database;

import android.content.Context;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

/**
 * Created by prozac on 10/12/14.
 */
public class FileTransfer {
    FTPClient client = new FTPClient();
    FileOutputStream fos = null;
    FileInputStream fis = null;
    String filename = "FF.sqlite";
    String filetoexport = "/sdcard/FF/export/FF.sqlite";
    String pathtoexp = "/srv/";
    String pathtoimp = "/bkp/";
    String host = "10.1.129.90";
    String user = "mae";
    String pass = "tuanis";

    public boolean download(Context contexto) throws IOException {
        try {
            // Conexión al host
            client.connect(host);               /* cambiar: IP pública si se puede */
            // Autenticación
            client.login(user, pass);           /* cambiar: user + pass */
            // Flujo de archivos
            fos = new FileOutputStream(filename);
            client.retrieveFile(pathtoimp + filename, fos);
        } catch (IOException ex) {
            Toast.makeText(contexto, "Ocurrió un error a la hora de descargar la base de datos\n" +
                    "Compruebe su conexión a Internet", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                client.disconnect();
                return true;
            } catch (IOException ex) {
                Toast.makeText(contexto, "No se obtuvó ninguno archivo desde el servidor\n" +
                        "Compruebe la estructura de archivos", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
                return false;
            }
        }
    }

    public boolean upload(String filename) {

        try {
            String ftpHost = "10.1.129.90";
            String ftpUserName = "mae";
            String ftpPassword = "tuanis";
            String ftpRemoteDirectory = "bkp";

            String fileToTransmit = filename;
            final FTPClient ftp = new FTPClient();
            int reply;

            ftp.connect(ftpHost);
            reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                try {
                    ftp.disconnect();
                } catch (Exception e) {
                    //out.println("Unable to disconnect from FTP server " + "after server refused connection. "+e.toString());
                }
                throw new Exception("FTP server refused connection.");
            }

            if (!ftp.login(ftpUserName, ftpPassword)) {
                throw new Exception("Unable to login to FTP server " + "using username " + ftpUserName + " " + "and password " + ftpPassword);
            }

            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            if (ftpRemoteDirectory != null && ftpRemoteDirectory.trim().length() > 0) {

                //out.println("Changing to FTP remote dir: " + ftpRemoteDirectory);
                ftp.makeDirectory(ftpRemoteDirectory);

                ftp.changeWorkingDirectory(ftpRemoteDirectory);
                reply = ftp.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    throw new Exception("Unable to change working 5 " + "to:" + ftpRemoteDirectory);
                }
            }
            System.out.println(ftp.printWorkingDirectory());
            final File f = new File(fileToTransmit);
            System.out.println(f.exists());
            System.out.println(f.getName());
            //out.println("Storing file as remote filename: " + f.getName());
            final boolean retValue;
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        boolean st = ftp.storeFile(f.getName(), new FileInputStream(f));
                        System.out.println(st);
                    } catch (IOException e) {
                        System.out.println("------------");
                        e.printStackTrace();
                    }
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean upload2(){
        FTPClient ftpclient = new FTPClient();
        FileInputStream fis = null;
        boolean result;
        String ftpServerAddress = host;
        String userName = user;
        String password = pass;

        try {
            ftpclient.connect(ftpServerAddress);
            result = ftpclient.login(userName, password);

            if (result == true) {
                System.out.println("Logged in Successfully !");
            } else {
                System.out.println("Login Fail!");
                return false;
            }
            ftpclient.setFileType(FTP.BINARY_FILE_TYPE);

            ftpclient.changeWorkingDirectory("/");

            File file = new File(filetoexport);
            System.out.println(file.exists());
            String testName = file.getName();
            fis = new FileInputStream(file);

            // Upload file to the ftp server
            result = ftpclient.storeFile(testName, fis);

            if (result == true) {
                System.out.println("File is uploaded successfully");
            } else {
                System.out.println("File uploading failed");
            }
            ftpclient.logout();
            return true;
        } catch (FTPConnectionClosedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpclient.disconnect();
            } catch (FTPConnectionClosedException e) {
                System.out.println(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

  /*  public boolean upload(Context contexto){
        try{
            client.connect(host);
            System.out.println(host);
            boolean login = client.login(user, pass);
            System.out.println(login);
            System.out.println(user);
            System.out.println(pass);
            System.out.println(filetoexport);
            fis = new FileInputStream(filetoexport);
            System.out.println(fis.toString());
            /*if(fis.available() == 1){
                System.out.println("Available");
            }
            boolean status = client.storeFile("/",fis);
            System.out.println(status);
            client.logout();
            fis.close();
            return true;
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "No se encontró la base de datos a exportar\n" +
                    "Compruebe la estructura de archivos", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        } catch (SocketException e) {
            Toast.makeText(contexto, "Fallo de transmisión de archivos\n" +
                    "Compruebe su conexión a Internet", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            /*Toast.makeText(contexto, "Ocurrió un error a la hora de subir la base de datos\n" +
                    "Compruebe su conexión a internet", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }

}*/


}