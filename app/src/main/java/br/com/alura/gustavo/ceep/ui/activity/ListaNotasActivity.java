package br.com.alura.gustavo.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.gustavo.ceep.R;
import br.com.alura.gustavo.ceep.dao.NotaDAO;
import br.com.alura.gustavo.ceep.model.Nota;
import br.com.alura.gustavo.ceep.recycler.adapter.ListaNotasAdapter;
import br.com.alura.gustavo.ceep.recycler.adapter.listener.OnItemClickListener;

import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.CHAVE_NOTA;
import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.POSICAO_INVALIDA;

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
        super.onActivityResult(requestCode, resultCode, data);

        if (ehReultadoInsereNota(requestCode, resultCode, data)){

            if (resultadoOk(resultCode)){
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                adciona(notaRecebida);
            }
        }

        if (ehResultadoAlteraNota(requestCode, data)){
            int posicao = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            Nota nota = (Nota) data.getSerializableExtra(CHAVE_NOTA);

            if(ehPosicaoValida(posicao)) {
                Toast.makeText(this, "Nota alterada.",
                        Toast.LENGTH_SHORT).show();
                altera(posicao, nota);
            }else{
                Toast.makeText(this,
                        "Ocorreu um problema naalteração da nota!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void altera(int posicao, Nota nota) {
        dao.altera(posicao, nota);
        adapter.alterar(posicao, nota);
    }

    private boolean ehPosicaoValida(int posicao) {
        return posicao > POSICAO_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) &&
                data.hasExtra(CHAVE_NOTA);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private void adciona(Nota nota) {
        dao.insere(nota);
        adapter.adciona(nota);
    }

    private boolean ehReultadoInsereNota(int requestCode, int resultCode, Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                resultadoOk(resultCode) &&
                data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraBotao() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        vaiParaFormularioActivityInsere(botaoInsereNota);
    }

    private void vaiParaFormularioActivityInsere(TextView botaoInsereNota) {
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaFormulario =
                        new Intent(ListaNotasActivity.this,
                                FormularioNotaActivity.class);
                startActivityForResult(vaiParaFormulario, CODIGO_REQUISICAO_INSERE_NOTA);
            }
        });
    }
    private void configurarRecyclerView(List<Nota> notas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_lista);
        adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);

        //Implementando a interface de click listener dentro de cada ViewHolder de nossa Recycler
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int posicao) {
                vaiParaFormularioActivityAltera(nota, posicao);
            }
        });
    }

    private void vaiParaFormularioActivityAltera(Nota nota, int posicao) {
        Intent abreFormularioComNota =
                new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);

        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

    private List<Nota> buscarTodasNotas() {
        dao.insere(new Nota("Insira um novo Título Bacana para sua anotação",
                "E também não se esqueça da descrição!"));
        return dao.buscarNotasSalvas();
    }
}
