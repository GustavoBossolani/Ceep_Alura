package br.com.alura.gustavo.ceep.recycler.callback;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import br.com.alura.gustavo.ceep.dao.NotaDAO;
import br.com.alura.gustavo.ceep.recycler.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListaNotasAdapter adapter;
    private NotaDAO dao;

    public NotaItemTouchHelperCallback(ListaNotasAdapter adapter) {
        this.adapter = adapter;
        dao = new NotaDAO();
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int marcacaoDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int marcacaoArrastar = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(marcacaoArrastar, marcacaoDeslize);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        trocarNota(posicaoInicial, posicaoFinal);
        return true;
    }

    private void trocarNota(int posicaoInicial, int posicaoFinal) {
        dao.troca(posicaoInicial, posicaoFinal);
        adapter.troca(posicaoInicial, posicaoFinal);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int posicao = viewHolder.getAdapterPosition();
        removerNota(posicao);
    }

    private void removerNota(int posicao) {
        dao.remove(posicao);
        adapter.remove(posicao);
    }
}
