package xyz.izen1231.nothing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText edit = (EditText) findViewById(R.id.edit);
        edit.setBackgroundColor(0);

        Button save =(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/AnimationCapture";
                final RelativeLayout capture = (RelativeLayout) findViewById(R.id.relative);

                String folder ="nothing";

                File file = new File(path);
                if(!file.exists()){
                    file.mkdirs();
                    Toast.makeText(MainActivity.this, "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();
                }

                SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();
                capture.buildDrawingCache();
                Bitmap captureview = capture.getDrawingCache();

                FileOutputStream fos = null;
                try{
                    fos = new FileOutputStream(path+"/Capture"+day.format(date)+".jpg");
                    captureview.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/Capture" + day.format(date) + ".JPEG")));
                    Toast.makeText(MainActivity.this, "저장완료", Toast.LENGTH_SHORT).show();
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
