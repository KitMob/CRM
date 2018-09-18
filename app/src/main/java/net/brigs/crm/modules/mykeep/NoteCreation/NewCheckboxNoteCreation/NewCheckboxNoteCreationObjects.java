package net.brigs.crm.modules.mykeep.NoteCreation.NewCheckboxNoteCreation;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class NewCheckboxNoteCreationObjects {
    private CheckBox _newCheckbox;
    private String _text;
    private ImageView _imageButtonDell;

    public NewCheckboxNoteCreationObjects(CheckBox newCheckbox, String text, ImageView imageButtonDell) {
        _newCheckbox = newCheckbox;
        _text = text;
        _imageButtonDell = imageButtonDell;
    }

    public NewCheckboxNoteCreationObjects(String lastContent) {
        _text = lastContent;
    }
}
