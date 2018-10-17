package br.com.senaijandira.mybooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.senaijandira.mybooks.fragments.fragmentGeral;
import br.com.senaijandira.mybooks.fragments.fragmentLer;
import br.com.senaijandira.mybooks.fragments.fragmentLeu;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;

    TabLayout tab_menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        //** CÓDIGO PARA TAB MENU**/
        tab_menu = findViewById(R.id.tab_menu);

        abrirBibiotecaGeral();

        tab_menu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            //quando seleciona
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() ==0){
                    abrirBibiotecaGeral();
                }

                if (tab.getPosition() ==1){
                    abrirLeu();
                }
                if (tab.getPosition() ==2){
                    abrirLer();
                }

            }
            //Quando deseleciona
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }


            //seleciona novamente
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    public void abrirBibiotecaGeral() {
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frame_layout,new fragmentGeral());

        ft.commit();
    }

    public void abrirLer() {
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frame_layout,new fragmentLer());

        ft.commit();
    }


    public void abrirLeu() {
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frame_layout,new fragmentLeu());

        ft.commit();
    }


    public void abrirCadastro(View v){
        startActivity(new Intent(this,
                cadastro_Activity.class));
    }

}
