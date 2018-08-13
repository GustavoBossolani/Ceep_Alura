package br.com.alura.gustavo.ceep.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.alura.gustavo.ceep.R;
import br.com.alura.gustavo.ceep.model.Nota;

class NotaViewHolder extends RecyclerView.ViewHolder {

    private final TextView TITULO;
    private final TextView DESCRICAO;

    public NotaViewHolder(View viewCriada) {
        super(viewCriada);

        TITULO = itemView.findViewById(R.id.item_nota_titulo);
        DESCRICAO = itemView.findViewById(R.id.item_nota_descricao);
    }

    public void PreencherCampo(Nota nota){
        TITULO.setText(nota.getTitulo());
        DESCRICAO.setText(nota.getDescricao());
    }
}
