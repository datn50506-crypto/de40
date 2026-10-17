package com.example.de40;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView imgLogo;
    private TextView txtSchoolNameVN;
    private TextView txtDepartment;
    private ImageView imgIllustration1;
    private ImageView imgIllustration2;
    private ImageView imgIllustration3;
    private TextView txtDocumentTitle;
    private VideoView videoView;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // UI Initialization
        imgLogo = findViewById(R.id.imgLogo);
        txtSchoolNameVN = findViewById(R.id.txtSchoolNameVN);
        txtDepartment = findViewById(R.id.txtDepartment);
        imgIllustration1 = findViewById(R.id.imgIllustration1);
        imgIllustration2 = findViewById(R.id.imgIllustration2);
        imgIllustration3 = findViewById(R.id.imgIllustration3);
        txtDocumentTitle = findViewById(R.id.txtDocumentTitle);
        videoView = findViewById(R.id.videoView);

        imgLogo.setOnClickListener(v -> Toast.makeText(this, "UNETI - Trường Đại học Kinh tế - Kỹ thuật Công nghiệp", Toast.LENGTH_SHORT).show());

        txtSchoolNameVN.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://uneti.edu.vn"))));

        txtDepartment.setOnClickListener(v -> startActivity(new Intent(this, IntroActivity.class)));

        // MP3 Smooth Playback
        imgIllustration1.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                Toast.makeText(this, "Tạm dừng MP3", Toast.LENGTH_SHORT).show();
            } else if (mediaPlayer != null) {
                mediaPlayer.start();
                Toast.makeText(this, "Tiếp tục phát MP3", Toast.LENGTH_SHORT).show();
            } else {
                startMediaPlayer();
            }
        });

        // MP4 Full Screen Playback
        imgIllustration2.setOnClickListener(v -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.nvdmp4;
            Intent intent = new Intent(MainActivity.this, FullScreenVideoActivity.class);
            intent.putExtra("VIDEO_PATH", videoPath);
            startActivity(intent);
        });

        // Click LẬP TRÌNH DI ĐỘNG mở video YouTube (chỉ hiển thị duy nhất khung video phát)
        View.OnClickListener youtubeVideoListener = v -> {
            Intent intent = new Intent(MainActivity.this, WebVideoActivity.class);
            startActivity(intent);
        };

        imgIllustration3.setOnClickListener(youtubeVideoListener);
        txtDocumentTitle.setOnClickListener(youtubeVideoListener);
    }

    private void startMediaPlayer() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(this, R.raw.nvdmp3);
            mediaPlayer.setOnCompletionListener(mp -> {
                Toast.makeText(MainActivity.this, "Kết thúc bài nhạc", Toast.LENGTH_SHORT).show();
                mediaPlayer.release();
                mediaPlayer = null;
            });
            mediaPlayer.start();
            Toast.makeText(this, "Đang phát nhạc MP3...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Không thể phát nhạc", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
