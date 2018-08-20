package br.com.alura.gustavo.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.alura.gustavo.ceep.R;
import br.com.alura.gustavo.ceep.dao.NotaDAO;
import br.com.alura.gustavo.ceep.model.Nota;

import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.CHAVE_NOTA;
import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {


    public static final String TITULO_APP_BAR_DETALHES = "Detalhes";
    private NotaDAO dao;
    private int posicao = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        setTitle(TITULO_APP_BAR_DETALHES);

        dao = new NotaDAO();
        inicializarCampos();

        Intent dadoRecebido = getIntent();
        if (dadoRecebido.hasExtra(CHAVE_NOTA)){
            Nota notaRecebida = (Nota)
                    dadoRecebido.getSerializableExtra(CHAVE_NOTA);
            posicao = dadoRecebido.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencherCampos(notaRecebida);
        }
    }

    @Override
    public void onBackPressed() {
        retornarNotaCancelada();
    }

    private void retornarNotaCancelada() {
        Intent resultadoCancelamento = new Intent();
        setResult(Activity.RESULT_CANCELED, resultadoCancelamento);
        finish();
    }

    private void preencherCampos(Nota notaRecebida) {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_salvar_nota:
                Nota notaCriada = criaNota();
                retornarNota(notaCriada);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornarNota(Nota nota) {
        dao.insere(nota);
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicao);
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        setResult(Activity.RESULT_OK, resultadoInsercao);
    }

    @NonNull
    private Nota criaNota() {
        return new Nota(titulo.getText().toString(),
                descricao.getText().toString());
    }

    private void inicializarCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
