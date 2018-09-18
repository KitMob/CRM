package net.brigs.crm.modules.mykeep.NoteCreation.NewCheckboxNoteCreation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.brigs.crm.R;

public class NewCheckboxNoteCreationViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    private CheckBox checkBox;
    private EditText text;
    private ImageView imageButtonDell;

    public NewCheckboxNoteCreationViewHolders(View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.new_checkbox_note_creation_check_box);
        text = itemView.findViewById(R.id.new_checkbox_note_creation_text_check_box);
        imageButtonDell = itemView.findViewById(R.id.image_button_dell_check_box);
    }


    @Override
    public void onClick(View view) {

        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();


        //TODO
//        Intent simpleNoteEditionIntent = new Intent(view.getContext(), SimpleNoteCreation.class);
//
//        simpleNoteEditionIntent.putExtra("photo", image.getTag().toString());
//        simpleNoteEditionIntent.putExtra("title", title.getText());
//        simpleNoteEditionIntent.putExtra("content", content.getText());
//        simpleNoteEditionIntent.putExtra("color", color);
//        simpleNoteEditionIntent.putExtra("creationDate", creationDate);
//        simpleNoteEditionIntent.putExtra("position", getPosition());
//        ((Activity) view.getContext()).startActivityForResult(simpleNoteEditionIntent, 1);
    }
}
