package com.ulima.sw.pizzaplanetac.listado;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.ulima.sw.pizzaplanetac.R;
import com.ulima.sw.pizzaplanetac.adapter.ListadoPizzasAdapter;
import com.ulima.sw.pizzaplanetac.beans.Pizza;
import com.ulima.sw.pizzaplanetac.ingredientes.IngredientesActivity;

import java.io.Serializable;
import java.util.List;

public class ListadoPizzasActivity extends AppCompatActivity implements ListadoPizzasView {

    private ListadoPizzasPresenter lPresenter;
    private ListView lstPizzas;
    private ProgressDialog dialog;
    private int pos;
    private String usuario;
    private String distrito;
    private ActionBar supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Listado Pizzas");

        supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intentPasado = getIntent();
        switch(intentPasado.getIntExtra("idestado",0)){
            case 0:
                supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(239,65,54)));
                break;
            case 1:
                supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(246,222,54)));
                break;
            case 2:
                supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(140,198,62)));
                break;
        }


        setContentView(R.layout.activity_listado_pizzas);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Cargando... Por favor espere");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        lstPizzas = (ListView)findViewById(R.id.lstPizza);


        setPresenter(new ListadoPizzasPresenterImp(this));

        pos = intentPasado.getIntExtra("idpizza",0);
        usuario = intentPasado.getStringExtra("usuario");
        distrito = intentPasado.getStringExtra("distrito");
        lPresenter.obtenerListaP(pos);


    }

    @Override
    public void setPresenter(ListadoPizzasPresenter presenter) {
        this.lPresenter = presenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void mostrarPizzas(final List<Pizza> Pizzas) {
        ListadoPizzasAdapter adapter = new ListadoPizzasAdapter(Pizzas,this);
        lstPizzas.setAdapter(adapter);
        dialog.dismiss();
        lstPizzas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListadoPizzasActivity.this, IngredientesActivity.class);
                intent.putExtra("ingredientes",(Serializable)Pizzas.get(position).getIng());
                startActivity(intent);

            }

        });
    }

    @Override
    public void toAst(int num) {
        switch(num){
            case 1:
                Toast.makeText(this, "En Proceso", Toast.LENGTH_SHORT).show();
                //lstPizzas.setBackgroundColor(Color.rgb(246,222,54));
                supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(246,222,54)));
                break;
            case 2:
                Toast.makeText(this, "Preparado", Toast.LENGTH_SHORT).show();
                //lstPizzas.setBackgroundColor(Color.rgb(140,198,62));
                supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(140,198,62)));
                /*try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                finish();
                break;
            case 3:
                Toast.makeText(this, "En Camino", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "Entregado", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(this, "Entregado", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    Log.i("TAG", "You have forgotten to specify the parentActivityName in the AndroidManifest!");
                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            case R.id.men_op1:
                lPresenter.actualizarEstado(pos,usuario,distrito);
        }
        return super.onOptionsItemSelected(item);
    }


}
