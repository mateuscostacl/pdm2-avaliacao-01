package com.example.pdm2_avaliacao_01.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pdm2_avaliacao_01.MainActivity;
import com.example.pdm2_avaliacao_01.R;
import com.example.pdm2_avaliacao_01.dao.PokemonDao;
import com.example.pdm2_avaliacao_01.pojo.Pokemon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JogarFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = "ExternalStorage";

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    private final String fileName = "ARQUIVO_DE_TESTE.txt";

    View root;

    TextView tvNome;
    TextView tvForca;
    ImageView ivImagem;

    FloatingActionButton fabJogar;

    String resultadoDoJogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_jogar, container, false);

        conectarComViewport();
        pegarVencedor();

        fabJogar.setOnClickListener(this);

        return root;
    }

    private void conectarComViewport() {
        fabJogar = root.findViewById(R.id.fab_jogar);
        tvNome = root.findViewById(R.id.tv_nome);
        tvForca = root.findViewById(R.id.tv_forca);
        ivImagem = root.findViewById(R.id.iv_imagem);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_jogar) {
//            ((MainActivity)getActivity()).askPermissionAndWriteFile();
            askPermissionAndWriteFile();
        }
    }

    private void pegarVencedor() {
        //cria uma instancia do DAO que dá acesso ao Banco de Dados
        PokemonDao pokemonDao = new PokemonDao(root.getContext());

        //pega a lista de pokemons do Banco de Dados
        List<Pokemon> pokemonList = pokemonDao.retornarTodos();

        //cria uma instancia para armazenar os daos do pokemon mais forte
        Pokemon pokemonMaisForte = new Pokemon();

        //como a experiencia base é a informação a ser comparada,
        // este atributo é inicializado com o menor valor possível
        // para que este possa ser substituído corretamente.
        pokemonMaisForte.setExpBasica(String.valueOf(Integer.MIN_VALUE));

        //variável auxiliar
        int exp;

        //percorrer a lista de pokemons procurando o que tem a maior experiência base
        for (int i = 0; i < pokemonList.size(); i++) {

            //passa a experiência base para a variável auxiliar
            exp = Integer.parseInt(pokemonList.get(i).getExpBasica());

            //compara a experiencia do pokemon atual com a do mais forte até o momento
            if (exp > Integer.parseInt(pokemonMaisForte.getExpBasica()))
                //guarda o pokemon mais forte na comparação atual
                pokemonMaisForte = pokemonList.get(i);
        }

        //Exibe o nome do pokemon mais forte
        tvNome.setText(pokemonMaisForte.getNome() + " é o vencedor!");
        //Exibe a imagem do pokemon mais forte
        Picasso.get().load(pokemonMaisForte.getImagem()).resize(600, 600).into((ImageView) ivImagem);
        //Exibe a experiencia base do pokemon mais forte
        tvForca.setText("Com sua força em "+pokemonMaisForte.getExpBasica() + " ele supera os demais.");

        //guarda as informações do pokemon mais forte para uso posterior (salvar no BD)
        resultadoDoJogo = tvNome.getText().toString() +"\n\n"+tvForca.getText().toString();

        //exibe uma animação
        MainActivity.animar(R.raw.usa_confetti, 1);
    }

    private void askPermissionAndWriteFile() {
        //recebe se o app tem permissao de escrita em armazenamento externo
        boolean canWrite = askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //se não tiver permissao, exibe uma mensagem e encerra o metodo
        if (!canWrite) {
            Toast.makeText(getActivity(),
                    "Você não tem permissão para escrever arquivos.", Toast.LENGTH_LONG).show();
            return;
        }
        //se tiver permissao, chama o metodo para escrever em arquivo
        writeFile();
    }

    // With Android Level >= 23, you have to ask the user
    // for permission with device (For example read/write data on the device).
    private boolean askPermission(int requestId, String permissionName) {


        Log.i(LOG_TAG, "Ask for Permission: " + permissionName);
        Log.i(LOG_TAG, "Build.VERSION.SDK_INT: " + android.os.Build.VERSION.SDK_INT);

        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(getActivity(), permissionName);

            Log.i(LOG_TAG, "permission: " + permission);
            Log.i(LOG_TAG, "PackageManager.PERMISSION_GRANTED: " + PackageManager.PERMISSION_GRANTED);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                requestPermissions(
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
            Toast.makeText(getActivity(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }

    // IMPORTANT!!
    public File getAppExternalFilesDir() {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            // /storage/emulated/0/Android/data/org.o7planning.externalstoragedemo/files
            return getActivity().getExternalFilesDir(null);
        } else {
            // @Deprecated in API 29.
            // /storage/emulated/0
            return Environment.getExternalStorageDirectory();
        }
    }


    private void writeFile() {
        try {
            File extStore = getAppExternalFilesDir();

            boolean canWrite = extStore.canWrite();
            Log.i(LOG_TAG, "Can write: " + extStore.getAbsolutePath() + " : " + canWrite);

            // ==> /storage/emulated/0/note.txt  (API < 29)
            // ==> /storage/emulated/0/Android/data/org.o7planning.externalstoragedemo/files/note.txt (API >=29)
            String path = extStore.getAbsolutePath() + "/" + fileName;
            Log.i(LOG_TAG, "Save to: " + path);

//            String data = editText.getText().toString();
            String data = resultadoDoJogo;
            Log.i(LOG_TAG, "Data: " + data);

            File myFile = new File(path);
            FileOutputStream fOut = new FileOutputStream(myFile);
            fOut.write(data.getBytes("UTF-8"));
            fOut.close();

            Toast.makeText(getActivity(), fileName + " foi salvo", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Write Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(getActivity(), "Read Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Read Error: " + e.getMessage());
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), fileContent, Toast.LENGTH_LONG).show();
    }
}