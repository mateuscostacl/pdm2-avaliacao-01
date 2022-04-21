package com.example.pdm2_avaliacao_01;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pdm2_avaliacao_01.databinding.ActivityMainBinding;
import com.example.pdm2_avaliacao_01.ui.DescricaoFragment;
import com.example.pdm2_avaliacao_01.ui.MapsActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = MainActivity.class.getSimpleName();

    private static final String LOG_TAG = "ExternalStorageDemo";

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    private final String fileName = "ARQUIVO_DE_TESTE.txt";

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        drawerConfiguration();

        MainActivity.lottieAnimationView = findViewById(R.id.animation_view);
    }

    private Context getContext() {
        return this;
    }

    public void drawerConfiguration() {
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_descricao,
                R.id.nav_detalhes,
                R.id.nav_maior_menor,
                R.id.nav_jogar)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void localizarReitoria() {
        Intent i = new Intent(getContext(), MapsActivity.class);
        startActivity(i);
    }

    public static LottieAnimationView lottieAnimationView;

    public static void animar(int rawId, int repetir) {
        MainActivity.lottieAnimationView.setAnimation(rawId);
        MainActivity.lottieAnimationView.setRepeatCount(repetir);
        MainActivity.lottieAnimationView.setVisibility(View.VISIBLE);
        MainActivity.lottieAnimationView.playAnimation();
        MainActivity.lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                MainActivity.lottieAnimationView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.fab) {
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_loc_reitoria:
                localizarReitoria();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!canWrite) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        Toast.makeText(getApplicationContext(),
                    "You do not allow this app to write files.", Toast.LENGTH_LONG).show();
            return;
        }
        //
        this.writeFile();
    }

    public void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        //
        if (!canRead) {
            Toast.makeText(getApplicationContext(),
                    "You do not allow this app to read files.", Toast.LENGTH_LONG).show();
            return;
        }
        //
        this.readFile();
    }

    // With Android Level >= 23, you have to ask the user
    // for permission with device (For example read/write data on the device).
    private boolean askPermission(int requestId, String permissionName) {


        Log.i(LOG_TAG, "Ask for Permission: " + permissionName);
        Log.i(LOG_TAG, "Build.VERSION.SDK_INT: " + android.os.Build.VERSION.SDK_INT);

        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);

            Log.i(LOG_TAG, "permission: " + permission);
            Log.i(LOG_TAG, "PackageManager.PERMISSION_GRANTED: " + PackageManager.PERMISSION_GRANTED);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }


    // As soon as the user decides, allows or doesn't allow.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        // Note: If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_READ_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        readFile();
                    }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        writeFile();
                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }

    // IMPORTANT!!
    public File getAppExternalFilesDir() {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            // /storage/emulated/0/Android/data/org.o7planning.externalstoragedemo/files
            return this.getExternalFilesDir(null);
        } else {
            // @Deprecated in API 29.
            // /storage/emulated/0
            return Environment.getExternalStorageDirectory();
        }
    }


    private void writeFile() {
        try {
            File extStore = this.getAppExternalFilesDir();

            boolean canWrite = extStore.canWrite();
            Log.i(LOG_TAG, "Can write: " + extStore.getAbsolutePath() + " : " + canWrite);

            // ==> /storage/emulated/0/note.txt  (API < 29)
            // ==> /storage/emulated/0/Android/data/org.o7planning.externalstoragedemo/files/note.txt (API >=29)
            String path = extStore.getAbsolutePath() + "/" + fileName;
            Log.i(LOG_TAG, "Save to: " + path);

//            String data = editText.getText().toString();
            String data = "TEXTO EDITADO NO CODIGO";
            Log.i(LOG_TAG, "Data: " + data);


            File myFile = new File(path);
            FileOutputStream fOut = new FileOutputStream(myFile);
            fOut.write(data.getBytes("UTF-8"));
            fOut.close();

            Toast.makeText(getApplicationContext(), fileName + " saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Write Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Write Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void readFile() {

        File extStore = this.getAppExternalFilesDir();

        // ==> /storage/emulated/0/note.txt  (API < 29)
        // ==> /storage/emulated/0/Android/data/org.o7planning.externalstoragedemo/note.txt (API >=29)
        String path = extStore.getAbsolutePath() + "/" + fileName;
        Log.i(LOG_TAG, "Read file: " + path);

        String s = "";
        String fileContent = "";
        try {
            File myFile = new File(path);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));

            while ((s = myReader.readLine()) != null) {
                fileContent += s + "\n";
            }
            myReader.close();

//            this.textView.setText(fileContent);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Read Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Read Error: " + e.getMessage());
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), fileContent, Toast.LENGTH_LONG).show();
    }

    private void listExternalStorages() {
        StringBuilder sb = new StringBuilder();

        sb.append("Data Directory: ").append("\n - ")
                .append(Environment.getDataDirectory().toString()).append("\n");

        sb.append("Download Cache Directory: ").append("\n - ")
                .append(Environment.getDownloadCacheDirectory().toString()).append("\n");

        sb.append("External Storage State: ").append("\n - ")
                .append(Environment.getExternalStorageState().toString()).append("\n");

        sb.append("External Storage Directory: ").append("\n - ")
                .append(Environment.getExternalStorageDirectory().toString()).append("\n");

        sb.append("Is External Storage Emulated?: ").append("\n - ")
                .append(Environment.isExternalStorageEmulated()).append("\n");

        sb.append("Is External Storage Removable?: ").append("\n - ")
                .append(Environment.isExternalStorageRemovable()).append("\n");

        sb.append("External Storage Public Directory (Music): ").append("\n - ")
                .append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString()).append("\n");

        sb.append("Download Cache Directory: ").append("\n - ")
                .append(Environment.getDownloadCacheDirectory().toString()).append("\n");

        sb.append("Root Directory: ").append("\n - ")
                .append(Environment.getRootDirectory().toString()).append("\n");

        Log.i(TAG, sb.toString());
//        this.textView.setText(sb.toString());
    }

}