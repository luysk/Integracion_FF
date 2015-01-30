package ucr.database;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by prozac on 17/11/14.
 */
public class DBExportImport {
    File data = Environment.getDataDirectory();
    File sd = Environment.getExternalStorageDirectory();
    String pathBD = "/data/ucr.ff/databases/";
    String bdNombre = "FF.sqlite";
    String exdir = "/FF/export/FF.sqlite";
    String indir = "/FF/import/FF.sqlite";

    //View view = new View(this);

    public void exportDB(Context contexto){
        FileChannel origen = null;
        FileChannel destino = null;
        File bdAct = new File(data, pathBD+bdNombre);
        File bdBkp = new File(sd, exdir);

        try{
            origen = new FileInputStream(bdAct).getChannel();
            destino = new FileOutputStream(bdBkp).getChannel();
            destino.transferFrom(origen,0,origen.size());
            origen.close();
            destino.close();
            Toast.makeText(contexto, "Base de datos exportada!", Toast.LENGTH_LONG).show();

            final Context ctxt = contexto;
            new Thread(new Runnable() {
                public void run() {
                    new FileTransfer().upload2();
                }
            }).start();


            Toast.makeText(contexto, "Base de datos cargada al servidor remoto", Toast.LENGTH_LONG).show();

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void importBD(Context contexto){
        try{
            if(sd.canWrite()){
                File bdBkp = new File(data, pathBD+bdNombre);
                File bdAct = new File(sd, indir);

                FileChannel org = new FileInputStream(bdAct).getChannel();
                FileChannel dst = new FileOutputStream(bdBkp).getChannel();

                dst.transferFrom(org,0,org.size());

                org.close();
                dst.close();

                Toast.makeText(contexto, "Nueva base de datos importada!", Toast.LENGTH_LONG).show();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void mkdir(){
        File FFdir = new File("/sdcard/FF");
        File exdir = new File("/sdcard/FF/export");
        File imdir = new File("/sdcard/FF/import");
        if(!FFdir.exists()){
                FFdir.mkdirs();
                exdir.mkdirs();
                imdir.mkdirs();
        }
    }

}



