package com.eet.pma.maria.shoppinglist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {
    private ArrayList<String> item_list; //llista amb tots els elements; després l'utilitzarem juntament amb l'adapter
    private ArrayAdapter<String> adapter;

    private ListView list;
    private Button btn_add;
    private EditText edit_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        list = (ListView) findViewById(R.id.llista);
        btn_add = (Button) findViewById(R.id.btn_afegir);
        edit_item = (EditText) findViewById(R.id.edit_item); //text per afegir elements

        item_list = new ArrayList<>(); //creem una llista per els elements
        item_list.add("Patates"); //afagim elements inicials
        item_list.add("Sabó");
        item_list.add("Formatge");

                                            //this = contexto; layout = creado por android
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,item_list);

        edit_item.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                addItem(); //afegir element amb el teclat('done')
                return true;
            }
        });

        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //accio de borrar element
            @Override
            public boolean onItemLongClick(AdapterView<?> list, View item, int pos, long id) {
                maybeRemoveItem(pos);
                return true;
            }
        });
    }


    public void afegir(View view) { //afegir element amb el botó de '+'
        addItem();
    }

    private void addItem() {
        String item_text = edit_item.getText().toString();

        if(!item_text.isEmpty()) {     //alternativa = !item_text.equals("")
            item_list.add(item_text);
            adapter.notifyDataSetChanged();
            edit_item.setText(""); //després d'afegir un element, es borra lo que tenim posat a la caixeta de text
        }
    }


    private void maybeRemoveItem(final int pos) { //preguntem si volem borrar element, si és així ho elimina
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        String fmt = getResources().getString(R.string.missatge);
        builder.setMessage(String.format(fmt,item_list.get(pos)));

        builder.setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                item_list.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton(android.R.string.cancel,null);
        builder.create().show();
    }

}
