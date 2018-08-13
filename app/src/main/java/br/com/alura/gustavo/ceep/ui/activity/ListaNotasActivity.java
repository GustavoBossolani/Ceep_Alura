package br.com.alura.gustavo.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.alura.gustavo.ceep.R;
import br.com.alura.gustavo.ceep.dao.NotaDAO;
import br.com.alura.gustavo.ceep.model.Nota;
import br.com.alura.gustavo.ceep.recycler.adapter.ListaNotasAdapter;

import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.CHAVE_DE_REQUISICAO;
import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.CHAVE_NOTA;
import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

public class ListaNotasActivity extends AppCompatActivity {

    private NotaDAO dao;
    private List<Nota> notas;
    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        dao = new NotaDAO();

        notas = buscarTodasNotas();
        configurarRecyclerView(notas);
        configuraBotao();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ehReultadoComNota(requestCode, resultCode, data)){
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            adciona(notaRecebida);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void adciona(Nota nota) {
        dao.insere(nota);
        adapter.adciona(nota);
    }

    private boolean ehReultadoComNota(int requestCode, int resultCode, Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                ehCodigoResultadoNotaCriada(resultCode) &&
                data.hasExtra(CHAVE_NOTA);
    }

    private boolean ehCodigoResultadoNotaCriada(int resultCode) {
        return resultCode == CODIGO_RESULTADO_NOTA_CRIADA;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CHAVE_DE_REQUISICAO;
    }

    private void configuraBotao() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        vaiParaFormulario(botaoInsereNota);
    }

    private void vaiParaFormulario(TextView botaoInsereNota) {
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaFormulario =
                        new Intent(ListaNotasActivity.this,
                                FormularioNotaActivity.class);
                startActivityForResult(vaiParaFormulario, CHAVE_DE_REQUISICAO);
            }
        });
    }
    private void configurarRecyclerView(List<Nota> notas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_lista);
        adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);
    }

    private List<Nota> buscarTodasNotas() {
        dao.insere(new Nota("Insira um novo Título Bacana para sua anotação",
                "E também não se esqueça da descrição!"));
        return dao.buscarNotasSalvas();
    }
}
