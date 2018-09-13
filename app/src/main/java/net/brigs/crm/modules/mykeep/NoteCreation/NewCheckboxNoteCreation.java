package net.brigs.crm.modules.mykeep.NoteCreation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.brigs.crm.R;

public class NewCheckboxNoteCreation extends AppCompatActivity implements OnClickListener {
    private EditText editText, textCheckBox;
    private TextView editTexttextAddCheckBox, textViewAdCheckBox;
    private CheckBox checkBox;
    private ImageView imageViewAddCheckBox, imageViewDelCheckBox;

    private TableLayout newCheckboxNoteCreationTableLayout;
    TableRow tableRowAddCheckbox;
    private long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_checkbox_note_creation);

        newCheckboxNoteCreationTableLayout = findViewById(R.id.new_checkbox_note_creation_table_layout);

        editText = findViewById(R.id.new_checkbox_note_creation_title_edit_text);

        checkBox = findViewById(R.id.new_checkbox_note_creation_check_box);
        textCheckBox = findViewById(R.id.new_checkbox_note_creation_text_check_box);
        imageViewDelCheckBox = findViewById(R.id.image_button_dell_check_box);

        imageViewAddCheckBox = findViewById(R.id.imageView_add_check_box);
        textViewAdCheckBox = findViewById(R.id.text_view__add_check_box);
        editTexttextAddCheckBox = findViewById(R.id.text_view__add_check_box);

        tableRowAddCheckbox = findViewById(R.id.create_new_checkbox_note)


        imageViewAddCheckBox.setOnClickListener(this);
        textViewAdCheckBox.setOnClickListener(this);



        id = 0;
//TODO

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_add_check_box:
                addChecboxPoint(id);
                break;

            case R.id.text_view__add_check_box:
                addChecboxPoint(id);

                break;

        }

    }

    @SuppressLint("ResourceAsColor")
    private void addChecboxPoint(long id) {

//creation of point of the list

        creationOfPointOfTheList((int) id);
        id++;

    }

    @SuppressLint("ResourceAsColor")
    private void creationOfPointOfTheList(int id) {
        CheckBox newCheckBox = new CheckBox(this);

        EditText newTextCheckBox = new EditText(this);
        newTextCheckBox.setBackground(textCheckBox.getBackground());
        newTextCheckBox.setMaxWidth(R.dimen.new_checkbox_note_creation_text_check_box_min_width);
        newCheckBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);


        ImageView newImageViewDelCheckBox = new ImageView(this);
        newImageViewDelCheckBox.setImageDrawable(imageViewDelCheckBox.getDrawable());
        newImageViewDelCheckBox.setClickable(true);

        TableRow newTableRow = new TableRow(this);


        newCheckboxNoteCreationTableLayout.addView(newTableRow);
        newTableRow.setId(id);
        newTableRow.addView(newCheckBox);
        newTableRow.addView(newTextCheckBox);
        newTableRow.addView(newImageViewDelCheckBox);

        newCheckboxNoteCreationTableLayout.addView(newTextCheckBox);
        newCheckboxNoteCreationTableLayout.addView(newImageViewDelCheckBox);
    }
}
