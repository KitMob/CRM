package net.brigs.crm.modules.mykeep.NoteCreation.NewCheckboxNoteCreation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import net.brigs.crm.R;

import java.util.ArrayList;

public class NewCheckboxNoteCreation extends AppCompatActivity  {

    private RecyclerView recyclerViewNewCheckboxCoteCreationList;
    private NewCheckboxNoteCreationObjects newCheckboxNoteCreationObjects;
    private NewCheckboxNoteCreationRecyclerViewAdapter newCheckboxNoteCreationRecyclerViewAdapter;

    private TextView  textViewAdCheckBox;
    private ImageView imageViewAddCheckBox;

    private TableLayout newCheckboxNoteCreationTableLayout;
    private String lastTitle;
    private String lastContent;
    private String color;
    private String creationDateString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_checkbox_note_creation);

        Intent editionIntent = getIntent();
        lastTitle = editionIntent.getStringExtra("title");
        lastContent = editionIntent.getStringExtra("content");
        color = editionIntent.getStringExtra("color");
        creationDateString = editionIntent.getStringExtra("creationDate");

        recyclerViewNewCheckboxCoteCreationList = findViewById(R.id.new_checkbox_note_creation_recycler_view);
        ArrayList<NewCheckboxNoteCreationObjects> newCheckboxNoteCreationObjects = new ArrayList<>();
        newCheckboxNoteCreationObjects.add(new NewCheckboxNoteCreationObjects(lastContent));

        newCheckboxNoteCreationRecyclerViewAdapter = new NewCheckboxNoteCreationRecyclerViewAdapter(newCheckboxNoteCreationObjects);
        recyclerViewNewCheckboxCoteCreationList.setAdapter(newCheckboxNoteCreationRecyclerViewAdapter);
        recyclerViewNewCheckboxCoteCreationList.setLayoutManager(new LinearLayoutManager(this));

//        newCheckboxNoteCreationTableLayout = findViewById(R.id.new_checkbox_note_creation_liner_layout);

//        editText = findViewById(R.id.new_checkbox_note_creation_title_edit_text);
//
//        checkBox = findViewById(R.id.new_checkbox_note_creation_check_box);
//        textCheckBox = findViewById(R.id.new_checkbox_note_creation_text_check_box);
//        imageViewDelCheckBox = findViewById(R.id.image_button_dell_check_box);
//
//
//        tableRowAddCheckbox = findViewById(R.id.new_checkbox_note_creation__table_row_add_check_box);
//        imageViewAddCheckBox = findViewById(R.id.imageView_add_check_box);
//        textViewAdCheckBox = findViewById(R.id.text_view__add_check_box);
//


//        imageViewAddCheckBox.setOnClickListener(this);
//        textViewAdCheckBox.setOnClickListener(this);
//
//
//        id = 0;
//TODO

    }

    public ArrayList<NewCheckboxNoteCreationObjects> getList(){
        ArrayList<NewCheckboxNoteCreationObjects> list = new ArrayList<>();

        return list;
    }

}
