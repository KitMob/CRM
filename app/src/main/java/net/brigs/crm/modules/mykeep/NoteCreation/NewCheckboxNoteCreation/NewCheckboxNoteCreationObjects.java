package net.brigs.crm.modules.mykeep.NoteCreation.NewCheckboxNoteCreation;

import android.widget.CheckBox;
import android.widget.ImageView;

public class NewCheckboxNoteCreationObjects {
    private CheckBox _newCheckbox;
    private String _text;
    private ImageView _imageButtonDell;
    private int _id;

    public NewCheckboxNoteCreationObjects(String _text, int _id) {
        this._text = _text;
        this._id = _id;
    }

    public NewCheckboxNoteCreationObjects(CheckBox newCheckbox, String text, ImageView imageButtonDell) {
        _newCheckbox = newCheckbox;
        _text = text;
        _imageButtonDell = imageButtonDell;
    }

    public NewCheckboxNoteCreationObjects(String lastContent) {
        _text = lastContent;
    }
}
