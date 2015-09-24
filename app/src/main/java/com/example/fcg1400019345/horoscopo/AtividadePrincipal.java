package com.example.fcg1400019345.horoscopo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("deprecation")
public class AtividadePrincipal extends ActionBarActivity {
    private ArrayAdapter<String> mAdaptador;
    private Map<Integer, Long> mapaIds = new HashMap<Integer, Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_principal);

        List<String> signos = new ArrayList<>();

        //CRIAR O ADAPTADOR
        mAdaptador = new ArrayAdapter<>(
                getApplicationContext(), //contexto atual
                R.layout.item_lista_principal, //nome do ID do layout
                R.id.item_texto, //ID do TextView a ser prenchido
                signos); // Quais os dados
        //

        ListView listView = (ListView) findViewById(R.id.lista_principal);
        listView.setAdapter(mAdaptador);

        listView.setOnItemClickListener(new ItemClicado());
    }

    @Override
    protected void onStart() {
        super.onStart();
        atualizaInterface();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_atividade_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent configlIntent = new Intent(getApplicationContext(), AtividadeConfiguracao.class);
                startActivity(configlIntent);
                return true;

            case R.id.action_refresh:
                atualizar();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    private void atualizar() {
        PegaDadosDoServidor pega = new PegaDadosDoServidor();
        pega.execute();

    }


    public class PegaDadosDoServidor extends AsyncTask<Void, Void, String[][]> {

        @Override
        protected String[][] doInBackground(Void... voids) {
            ServidorFalso servidor = new ServidorFalso();
            return servidor.pegaDados();

        }

        @Override
        protected void onPostExecute(String[][] result) {
            if (result != null) {

                SQLiteOpenHelper dbHelper = new HoroscopoDBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // LIMPA A TABELA INTEIRA!!!!
                db.delete(ContratoDB.Horoscopo.NOME_TABELA, null, null);
                for (String linha[] : result) {
                    ContentValues entrada = new ContentValues();
                    entrada.put(ContratoDB.Horoscopo.COLUNA_DATA, linha[0]);
                    entrada.put(ContratoDB.Horoscopo.COLUNA_SIGNO, linha[1]);
                    entrada.put(ContratoDB.Horoscopo.COLUNA_PREVISAO, linha[2]);

                    db.insert(ContratoDB.Horoscopo.NOME_TABELA, null, entrada);

                }

                db.close();
                dbHelper.close();
            }

            atualizaInterface();
        }

    }

    private void atualizaInterface() {
        SQLiteOpenHelper dbHelper = new HoroscopoDBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(
                ContratoDB.Horoscopo.NOME_TABELA, // Tabela
                null, // colunas (todas)
                null, // colunas para o where
                null, // valores para o where
                null, // group by
                null, // having
                null  // sort by
        );

        mAdaptador.clear();
        mapaIds.clear();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(ContratoDB.Horoscopo._ID));
            long data = cursor.getLong(cursor.getColumnIndex(ContratoDB.Horoscopo.COLUNA_DATA));
            String titulo = cursor.getString(cursor.getColumnIndex(ContratoDB.Horoscopo.COLUNA_SIGNO));
            String texto = cursor.getString(cursor.getColumnIndex(ContratoDB.Horoscopo.COLUNA_PREVISAO));

            String dataBonita = new SimpleDateFormat("dd/MM/yyyy").format(new Date(data * 1000));

            // Associa a posição do item ao id dele
            mapaIds.put(mAdaptador.getCount(), id);

            // Não estou usando o texto, mas poderia
            mAdaptador.add(id + " - " + dataBonita + " - " + titulo);
        }

        db.close();
        dbHelper.close();
    }


    private class ItemClicado implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent detailIntent = new Intent(getApplicationContext(), AtividadeDetalhes.class);
            detailIntent.putExtra(Intent.EXTRA_TEXT, mapaIds.get(position));
            startActivity(detailIntent);

        }
    }
}
