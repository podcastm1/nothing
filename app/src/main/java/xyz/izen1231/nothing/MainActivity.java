package xyz.izen1231.nothing;

import android.annotation.SuppressLint;
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
                File savePath = new File(Environment.getExternalStorageDirectory() , "/king");

                final RelativeLayout capture = (RelativeLayout) findViewById(R.id.relative);

                if(!savePath.exists()){
                    savePath.mkdirs();
                    Toast.makeText(MainActivity.this, "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();
                }

                capture.setDrawingCacheEnabled(true);
                Bitmap screenBitmap = capture.getDrawingCache();

                @SuppressLint("SimpleDateFormat") String fileDate = new SimpleDateFormat("yyyy.MM.dd(E) a hh:mm:ss").format(new Date());
                String filename = fileDate + ".png";

                File file = new File(Environment.getExternalStorageDirectory() + "/king", filename);
                FileOutputStream os = null;

                try {
                    os = new FileOutputStream(file);
                    screenBitmap.compress(Bitmap.CompressFormat.PNG, 90, os); //비트맵을 PNG파일로 변환
                    Toast.makeText(MainActivity.this, "저장완료", Toast.LENGTH_SHORT).show();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                capture.setDrawingCacheEnabled(false);
            }
        });

    }
}

