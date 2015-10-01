package com.example.fcg1400019345.horoscopo;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressWarnings("deprecation")
public class AtividadeDetalhes extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_detalhes);

        Intent intent = getIntent();


        if (intent.hasExtra(Intent.EXTRA_TEXT)) {

            long id = intent.getLongExtra(Intent.EXTRA_TEXT, 0L);


            SQLiteOpenHelper dbHelper = new HoroscopoDBHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursor;
            cursor = db.query(
                    ContratoDB.Horoscopo.NOME_TABELA, // Tabela
                    null, // colunas (todas)
                    ContratoDB.Horoscopo._ID + "=" + id, // colunas para o where
                    null, // valores para o where
                    null, // group by
                    null, // having
                    null  // sort by
            );

            if (cursor.moveToNext()) {

                long data = cursor.getLong(cursor.getColumnIndex(ContratoDB.Horoscopo.COLUNA_DATA));
                String titulo = cursor.getString(cursor.getColumnIndex(ContratoDB.Horoscopo.COLUNA_SIGNO));
                String texto = cursor.getString(cursor.getColumnIndex(ContratoDB.Horoscopo.COLUNA_PREVISAO));

                String dataBonita = new SimpleDateFormat("dd/MM/yyyy").format(new Date(data * 1000));

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String signo = prefs.getString("signo", "Touro ");


                TextView linha1 = (TextView) findViewById(R.id.text_signo);
                linha1.setText(titulo);

                TextView linha2 = (TextView) findViewById(R.id.text_data);
                linha2.setText(dataBonita);

                TextView mensagem = (TextView) findViewById(R.id.text_previsao);
                mensagem.setText(texto);
            }


        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_atividade_detalhes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent configlIntent = new Intent(getApplicationContext(), AtividadeConfiguracao.class);
            startActivity(configlIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class BotaoClicado implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);

            TextView detailTextView = (TextView) findViewById(R.id.detalhe_item_texto);

            String texto = detailTextView.getText().toString();

            intent.putExtra(SearchManager.QUERY, texto);
            if (intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);

        }

    }
}
