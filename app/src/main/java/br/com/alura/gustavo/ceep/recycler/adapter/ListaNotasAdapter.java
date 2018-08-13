package br.com.alura.gustavo.ceep.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.alura.gustavo.ceep.R;
import br.com.alura.gustavo.ceep.model.Nota;

public class ListaNotasAdapter extends RecyclerView.Adapter<NotaViewHolder> {


    private final List<Nota> notas;
    private final Context context;

    public ListaNotasAdapter(Context context, List<Nota> notas){
        this.context = context;
        this.notas = notas;
    }

//    a abordagem do onCreateViewHolder()
//    é justamente criar todas as views necessárias para que o Adapter
//    seja capaz de reutilizá-las conforme a ação de scroll é realizada.
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewCriada = LayoutInflater.from(context).
                inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(viewCriada);
    }

//    O onBindViewHolder() serve para vincular as informações de um objeto na view.
//    A ideia desse membro do adapter é computar as informações nas views que foram criadas para serem reutilizadas.

//    O onBindViewHolder() faz uso da posição para buscar o objeto que será apresentado na view.
//    O parâmetro de posição possibilita a reutilização das views de acordo com o objeto
//    que precisa ser apresentado conforme o movimento de scroll.
    @Override
    public void onBindViewHolder(NotaViewHolder holder, int position) {
        Nota nota = this.notas.get(position);
        holder.PreencherCampo(nota);
    }

    @Override
    public int getItemCount() {
        return this.notas.size();
    }

    public void adciona(Nota notaRecebida) {
        this.notas.add(notaRecebida);

        //Notfica a RecyclerView quando a lista sofre alteração
        notifyDataSetChanged();
    }
}