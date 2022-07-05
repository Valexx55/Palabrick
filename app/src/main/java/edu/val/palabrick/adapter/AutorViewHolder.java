package edu.val.palabrick.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.val.palabrick.R;
import edu.val.palabrick.model.AutorCretidos;


/**
 * esta clase es una vista invisible que contiene a cada fila visible
 * en un momento dado
 */
public class AutorViewHolder extends RecyclerView.ViewHolder {

    private ImageView imagen_autor;
    private ImageView logo_linkedin;
    private ImageView logo_github;
    private TextView textView_nombre_autor;

    //itemView es la FILA! (el linear(
    public AutorViewHolder(@NonNull View linearPadreFila) {
        super(linearPadreFila);
        this.imagen_autor = linearPadreFila.findViewById(R.id.foto_autor);
        this.logo_github = linearPadreFila.findViewById(R.id.logo_github);
        this.logo_linkedin = linearPadreFila.findViewById(R.id.logo_linkedin);
        this.textView_nombre_autor = linearPadreFila.findViewById(R.id.nombre_autor);

    }

    //"RELLENAR EL HOLDER" con la información de una fila visible
    public void cargarAutorEnVieHolder (AutorCretidos autorCretidos)
    {
        this.imagen_autor.setImageResource(autorCretidos.getFoto());
        this.textView_nombre_autor.setText(autorCretidos.getNombre());
        this.logo_linkedin.setImageResource(R.drawable.linkedin);
        this.logo_linkedin.setTag(autorCretidos.getUrl_linkedin());
        this.logo_github.setImageResource(R.drawable.logogithub);
        this.logo_github.setTag(autorCretidos.getUrl_github());
        this.logo_linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_linkedin = (String) view.getTag();
                Intent intent_abrir_web = new Intent();
                intent_abrir_web.setAction(Intent.ACTION_VIEW);
                intent_abrir_web.setData(Uri.parse(url_linkedin));
                view.getContext().startActivity(intent_abrir_web);
            }
        });
        this.logo_github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_linkedin = (String) view.getTag();
                Intent intent_abrir_web = new Intent();
                intent_abrir_web.setAction(Intent.ACTION_VIEW);
                intent_abrir_web.setData(Uri.parse(url_linkedin));
                view.getContext().startActivity(intent_abrir_web);
            }
        });
    }
}
