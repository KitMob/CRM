package net.brigs.crm.modules.mykeep.NewCheckboxNoteCreation;

import android.widget.CheckBox;
import android.widget.ImageView;

public class NewCheckboxNoteCreationObjects {
    private CheckBox _newCheckbox;
    private String _text;
    private String _color;
    private int _id;

    public CheckBox get_newCheckbox() {
        return _newCheckbox;
    }

    public String get_text() {
        return _text;
    }

    public String get_color() {
        return _color;
    }

    public int get_id() {
        return _id;
    }

    public NewCheckboxNoteCreationObjects(String _text, int _id) {
        this._text = _text;
        this._id = _id;
    }

    public NewCheckboxNoteCreationObjects(CheckBox newCheckbox, String text, String color) {
        _newCheckbox = newCheckbox;
        _text = text;
        _color = color;
    }

    public NewCheckboxNoteCreationObjects(String lastContent) {
        _text = lastContent;
    }

    public NewCheckboxNoteCreationObjects(String text, int id, String color) {
        this._text = _text;
        this._id = _id;
        this._color = color;
    }
}
