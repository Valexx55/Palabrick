package edu.val.palabrick.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.val.palabrick.R;
import edu.val.palabrick.model.AutorCretidos;

public class AdapterListaAutores extends RecyclerView.Adapter<AutorViewHolder> {

    private List<AutorCretidos> lista_autores;

    public AdapterListaAutores (List<AutorCretidos> list_autores)
    {
        this.lista_autores = list_autores;
    }

    //este método "infla un holder"
    @NonNull
    @Override
    public AutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AutorViewHolder autorViewHolder = null;

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View fila_autor = layoutInflater.inflate(R.layout.fila_creditos, parent, false);
            autorViewHolder = new AutorViewHolder(fila_autor);

        return autorViewHolder;
    }



    ////este método "rellena un holder" - lo recicla
    @Override
    public void onBindViewHolder(@NonNull AutorViewHolder holder, int position) {
        AutorCretidos autorCretidos = lista_autores.get(position);
        holder.cargarAutorEnVieHolder(autorCretidos);
    }

    @Override
    public int getItemCount() {
        return lista_autores.size();
    }
}
