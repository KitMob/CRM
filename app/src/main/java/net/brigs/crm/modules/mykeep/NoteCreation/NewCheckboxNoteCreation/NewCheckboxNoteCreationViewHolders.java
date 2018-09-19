package net.brigs.crm.modules.mykeep.NoteCreation.NewCheckboxNoteCreation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.brigs.crm.R;

public class NewCheckboxNoteCreationViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final RecyclerView recyclerViewNewCheckboxCoteCreationList;
    private CheckBox checkBox;
    private EditText text;
    private ImageView imageButtonDell;

    public NewCheckboxNoteCreationViewHolders(View itemView) {
        super(itemView);

        recyclerViewNewCheckboxCoteCreationList = itemView.findViewById(R.id.new_checkbox_note_creation_recycler_view);
        checkBox = itemView.findViewById(R.id.new_checkbox_note_creation_check_box);
        checkBox.setId(getLayoutPosition());
        text = itemView.findViewById(R.id.new_checkbox_note_creation_text_check_box);
        imageButtonDell = itemView.findViewById(R.id.image_button_dell_check_box);
        imageButtonDell.setOnClickListener(this);
        checkBox.setOnClickListener(this);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        //TODO dell

        Toast.makeText(view.getContext(), "Clicked Position  = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

//        switch (view.getId()) {
//            case R.id.button1:
//                textView.setText(R.string.text1);
//
//                break;
//            case R.id.button2:
//                textView.setText(R.string.text2);
//                break;
//            case R.id.button3:
//                textView.setText(R.string.text3);
//                break;
//            case R.id.textView:
//                button3.setText(R.string.textButton);
//                break;
//
//        }

//        if (view.equals(imageButtonDell)) {
//            removeAt(getPosition());
//        }
    }

    private void removeAt(int position) {
        recyclerViewNewCheckboxCoteCreationList.removeView(checkBox);
        recyclerViewNewCheckboxCoteCreationList.removeView(text);
        recyclerViewNewCheckboxCoteCreationList.removeView(imageButtonDell);
    }


}




