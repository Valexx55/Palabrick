package edu.val.palabrick.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.val.palabrick.R;
import edu.val.palabrick.adapter.AdapterListaAutores;
import edu.val.palabrick.model.AutorCretidos;

public class CreditosActivity extends AppCompatActivity {


    private RecyclerView recyclerViewCreditos;
    private AdapterListaAutores adapterListaAutores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);

        List<AutorCretidos> lista_autores = new ArrayList<>();

        AutorCretidos autor1 = new AutorCretidos(R.drawable.foto_al, "ALBERTO MORALES", "https://github.com/anmoraque", "https://www.linkedin.com/in/alberto-morales-ab373a235/" );
        AutorCretidos autor2 = new AutorCretidos(R.drawable.foto_jess, "JESSICA POZO", "https://github.com/pozito31", "https://www.linkedin.com/in/jessica-pozo-martin-28a1ab185/" );
        /*AutorCretidos autor3 = new AutorCretidos(R.drawable.foto_al, "ALBERTO MORALES", "https://github.com/anmoraque", );
        AutorCretidos autor4 = new AutorCretidos(R.drawable.foto_al, "ALBERTO MORALES", "https://github.com/anmoraque", );
        AutorCretidos autor5 = new AutorCretidos(R.drawable.foto_al, "ALBERTO MORALES", "https://github.com/anmoraque", );*/

        //lista_autores.add(autor1);
        //lista_autores.add(autor2);

        lista_autores.addAll(Arrays.asList(autor1, autor2));


        this.recyclerViewCreditos = findViewById(R.id.recicler_creditos);
        this.adapterListaAutores = new AdapterListaAutores(lista_autores);
        this.recyclerViewCreditos.setAdapter(this.adapterListaAutores);

        //deifno el estilo de la lista / la distribución
        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.recyclerViewCreditos.setLayoutManager(layoutManager);

    }
}