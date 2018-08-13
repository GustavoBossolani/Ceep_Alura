package br.com.alura.gustavo.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.alura.gustavo.ceep.R;
import br.com.alura.gustavo.ceep.dao.NotaDAO;
import br.com.alura.gustavo.ceep.model.Nota;

import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.CHAVE_NOTA;
import static br.com.alura.gustavo.ceep.ui.activity.interfaces.NotasActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

public class FormularioNotaActivity extends AppCompatActivity {


    private NotaDAO dao;
    private EditText titulo;
    private EditText descriao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        dao = new NotaDAO();
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
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        setResult(CODIGO_RESULTADO_NOTA_CRIADA, resultadoInsercao);
    }

    @NonNull
    private Nota criaNota() {
        confiraCampos();
        return new Nota(titulo.getText().toString(), descriao.getText().toString());
    }

    private void confiraCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descriao = findViewById(R.id.formulario_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
