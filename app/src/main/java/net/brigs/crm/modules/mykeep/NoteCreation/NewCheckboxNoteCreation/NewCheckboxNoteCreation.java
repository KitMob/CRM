package net.brigs.crm.modules.mykeep.NoteCreation.NewCheckboxNoteCreation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import net.brigs.crm.R;

import java.util.ArrayList;

public class NewCheckboxNoteCreation extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewNewCheckboxCoteCreationList;
    private NewCheckboxNoteCreationObjects newCheckboxNoteCreationObjects;
    private NewCheckboxNoteCreationRecyclerViewAdapter newCheckboxNoteCreationRecyclerViewAdapter;

    private TextView textViewAdCheckBox;
    private ImageView imageViewAddCheckBox;

    private TableLayout newCheckboxNoteCreationTableLayout;
    private String lastTitle;
    private String lastContent;
    private String color;
    private String creationDateString;
    private int id;
    private ArrayList<NewCheckboxNoteCreationObjects> list;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_checkbox_note_creation);

        Intent editionIntent = getIntent();
        lastTitle = editionIntent.getStringExtra("title");
        lastContent = editionIntent.getStringExtra("content");
        color = editionIntent.getStringExtra("color");
        creationDateString = editionIntent.getStringExtra("creationDate");

        id = 0;
        recyclerViewNewCheckboxCoteCreationList(lastTitle, id);

        imageViewAddCheckBox = findViewById(R.id.imageView_add_check_box);
        textViewAdCheckBox = findViewById(R.id.text_view_add_check_box);


        imageViewAddCheckBox.setOnClickListener(this);
        textViewAdCheckBox.setOnClickListener(this);


//TODO

    }


    @Override
    public void onClick(View v) {
//        id++;
        position = newCheckboxNoteCreationRecyclerViewAdapter.getItemCount();
        AdItem(position);

    }


    private void recyclerViewNewCheckboxCoteCreationList(String lastTitle, int id) {
        recyclerViewNewCheckboxCoteCreationList = findViewById(R.id.new_checkbox_note_creation_recycler_view);
        list = getList(lastTitle, id);
        newCheckboxNoteCreationRecyclerViewAdapter = new NewCheckboxNoteCreationRecyclerViewAdapter(list);
        recyclerViewNewCheckboxCoteCreationList.setAdapter(newCheckboxNoteCreationRecyclerViewAdapter);
        recyclerViewNewCheckboxCoteCreationList.setLayoutManager(new LinearLayoutManager(this));
    }


    public ArrayList<NewCheckboxNoteCreationObjects> getList(String text, int id) {
        ArrayList<NewCheckboxNoteCreationObjects> list = new ArrayList<>();

        NewCheckboxNoteCreationObjects newCheckboxNoteCreationObjects = new NewCheckboxNoteCreationObjects(text, id);
        list.add(newCheckboxNoteCreationObjects);


        return list;
    }


    private void AdItem(int position) {
        //Скопируем элемент с индексом position и вставим копию в следующую позицию
        NewCheckboxNoteCreationObjects newCheckboxNoteCreationObjects = new NewCheckboxNoteCreationObjects("", position);

        newCheckboxNoteCreationRecyclerViewAdapter.addItem(position, newCheckboxNoteCreationObjects);
        newCheckboxNoteCreationRecyclerViewAdapter.notifyItemInserted(position);
        this.position = newCheckboxNoteCreationRecyclerViewAdapter.getItemCount();
    }


}
