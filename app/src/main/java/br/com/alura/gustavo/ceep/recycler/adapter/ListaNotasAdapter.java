package br.com.alura.gustavo.ceep.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import br.com.alura.gustavo.ceep.R;
import br.com.alura.gustavo.ceep.model.Nota;
import br.com.alura.gustavo.ceep.recycler.adapter.listener.OnItemClickListener;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {


    private final List<Nota> notas;
    private final Context context;
    private OnItemClickListener onItemClickListener;


    public ListaNotasAdapter(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

//    a abordagem do onCreateViewHolder()
//    é justamente criar todas as views necessárias para que o Adapter
//    seja capaz de reutilizá-las conforme a ação de scroll é realizada.
    @Override
    public NotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).
                inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(viewCriada);
    }


//    O onBindViewHolder() serve para vincular as informações de um objeto na view.
//    A ideia desse membro do adapter é computar as informações nas views que foram criadas para serem reutilizadas.
//
//    O onBindViewHolder() faz uso da posição para buscar o objeto que será apresentado na view.
//    O parâmetro de posição possibilita a reutilização das views de acordo com o objeto
//    que precisa ser apresentado conforme o movimento de scroll.
    @Override
    public void onBindViewHolder(NotaViewHolder holder, int position) {
        Nota nota = this.notas.get(position);
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return this.notas.size();
    }

    public void adciona(Nota nota){
        this.notas.add(nota);
        //Notfica a RecyclerView quando a lista sofre alteração
        notifyDataSetChanged();
    }

    public void alterar(int posicao, Nota nota) {
        notas.set(posicao, nota);
        notifyDataSetChanged();
    }

    public void remove(int posicao) {
        notas.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public void troca(int posicaoInicial, int posicaoFinal) {
        Collections.swap(notas, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView TITULO;
        private final TextView DESCRICAO;
        private  Nota nota;

        public NotaViewHolder(View viewCriada) {
            super(viewCriada);

            TITULO = itemView.findViewById(R.id.item_nota_titulo);
            DESCRICAO = itemView.findViewById(R.id.item_nota_descricao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(nota, getAdapterPosition());
                }
            });
        }

        private void vincula(Nota nota){
            this.nota = nota;
            preencherCampo(nota);
        }

        public void preencherCampo(Nota nota) {
            TITULO.setText(nota.getTitulo());
            DESCRICAO.setText(nota.getDescricao());
        }
    }
}
