package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class EditarActivity extends AppCompatActivity {

    Livro livro;
    Bitmap livroCapa;
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricao;
    int idLivro;
    private android.app.AlertDialog alerta;

    private final int COD_REQ_GALERIA = 101;

    private MyBooksDatabase mybooksDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        //Criando instancia no banco de dados
        mybooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);

        idLivro = getIntent().getIntExtra("Livro", 0);

        System.out.println(" ID pego do seliarlize: " + idLivro);

        Livro livro = mybooksDb.daoLivro().pegarLivro(idLivro);
        this.livro = livro;
        System.out.println(" ID DO lIVRO: " + livro.getId() + " Nome do livro: " + livro.getTitulo());

        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        livroCapa = BitmapFactory.decodeByteArray(livro.getCapa(), 0, livro.getCapa().length);
        txtTitulo.setText(livro.getTitulo());
        txtDescricao.setText(livro.getDescricao());
    }

    public void abrirGaleria(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(
                Intent.createChooser(intent, "Selecione uma imagem"), COD_REQ_GALERIA
        );
    }

    public void alert(String titulo, String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage("Seu Livro foi Salvo com Sucesso");

        alert.setCancelable(false);

        alert.setPositiveButton("ok", null);
        alert.create().show();
    }

    public void salvarLivro(View view) {

        //Ao salvar o livro que acabou de ser cadastrado cria-se um novo livro


        if(imgLivroCapa == null || txtDescricao.getText().toString().equals("") || txtTitulo.getText().toString().equals("")){
            alert("Erro ao cadastrar", "Por Favor preencha todos os campos", "OK", null);
        }else{


            // livroCapa = Utils.toBitmap(imgLivroCapa);

            byte[] capa = Utils.toByteArray(livroCapa);
            String titulo = txtTitulo.getText().toString();
            String descricao = txtDescricao.getText().toString();



            if(livro.getId() > 0){

//                livro.setDescricao(txtDescricao.getText().toString());
//                livro.setTitulo(txtTitulo.getText().toString());
//                //livro.setCapa(Utils.toByteArray(Utils.toBitmap(imgLivroCapa)));
//                livro.setCapa(capa);

                livro.setCapa(capa);
                livro.setTitulo(titulo);
                livro.setDescricao(descricao);

                mybooksDb.daoLivro().atualizar(livro);
                alert("Pronto", "Livro atualizado com sucesso", "OK", null);
            }else{

                livro.setCapa(capa);
                livro.setTitulo(titulo);
                livro.setDescricao(descricao);

                mybooksDb.daoLivro().inserir(livro);
                alert("Pronto", "Livro Cadastrado com sucesso", "OK", null);
            }


        }



    }
    public void alert(String titulo, String mensagem, String positive, String negative){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(titulo);
        alertDialogBuilder.setMessage(mensagem);
        alertDialogBuilder.setPositiveButton(positive, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        alertDialogBuilder.setNegativeButton(negative, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }



}





