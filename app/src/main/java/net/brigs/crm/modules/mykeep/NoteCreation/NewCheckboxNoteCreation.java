package net.brigs.crm.modules.mykeep.NoteCreation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import net.brigs.crm.R;

public class NewCheckboxNoteCreation extends AppCompatActivity implements OnClickListener {
    private EditText editText, textCheckBox;
    private TextView editTexttextAddCheckBox, textViewAdCheckBox;
    private CheckBox checkBox;
    private ImageView imageViewAddCheckBox, imageViewDelCheckBox;

    private TableLayout newCheckboxNoteCreationTableLayout;


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


        imageViewAddCheckBox.setOnClickListener(this);
        textViewAdCheckBox.setOnClickListener(this);
//TODO

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_add_check_box:
                addChecboxPoint();

                break;

            case R.id.text_view__add_check_box:
                addChecboxPoint();
                break;

        }

    }

    private void addChecboxPoint() {
        CheckBox newCheckBox = new CheckBox(this);

        TextView newTextCheckBox = new TextView(this);
        newTextCheckBox.setBackground(textCheckBox.getBackground());


        ImageView newImageViewDelCheckBox = new ImageView(this);
        newImageViewDelCheckBox.setImageResource(R.drawable.checkbox_dell);
        newImageViewDelCheckBox.setClickable(true);

        int count = 0;
//                newCheckboxNoteCreationTableLayout.removeView(imageViewAddCheckBox);
//                newCheckboxNoteCreationTableLayout.removeView(editTexttextAddCheckBox);
        newCheckboxNoteCreationTableLayout.addView(newCheckBox);
        newCheckboxNoteCreationTableLayout.addView(newTextCheckBox);
        newCheckboxNoteCreationTableLayout.addView(newImageViewDelCheckBox);
    }
}
