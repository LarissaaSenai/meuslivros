package br.com.senaijandira.mybooks.fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.adapter.LivroAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class fragmentLeu extends Fragment {
    ListView listaLivros;
    LivroAdapter adapterlivros;
    MyBooksDatabase mybooksDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_leu,container,false);

        listaLivros = v.findViewById(R.id.lstViewLivros);
        mybooksDb = Room.databaseBuilder(getContext(),MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        adapterlivros = new LivroAdapter(getContext(),mybooksDb);

        listaLivros.setAdapter(adapterlivros);

        return v ;
    }

    @Override
    public void onResume() {
        super.onResume();
        Livro[] listLivros= mybooksDb.daoLivro().selecionarTodos();
        adapterlivros.addAll(listLivros);

    }
}

