package net.brigs.crm.modules.mykeep.NewCheckboxNoteCreation;

import android.widget.CheckBox;

public class NewCheckboxNoteCreationObjects {
    private CheckBox _newCheckbox;
    private String _text;
    private String _color;
    private int id;

    public void set_newCheckbox(CheckBox _newCheckbox) {
        this._newCheckbox = _newCheckbox;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public void set_color(String _color) {
        this._color = _color;
    }

    public void setId(int id) {
        this.id = id;
    }


    public CheckBox get_newCheckbox() {
        return _newCheckbox;
    }

    public String get_text() {
        return _text;
    }

    public String get_color() {
        return _color;
    }

    public int getId() {
        return id;
    }


    public NewCheckboxNoteCreationObjects(String _text, int position) {
        this._text = _text;
        this.id = position;
    }

    public NewCheckboxNoteCreationObjects(CheckBox newCheckbox, String text, String color) {
        _newCheckbox = newCheckbox;
        _text = text;
        _color = color;
    }

    public NewCheckboxNoteCreationObjects(String lastContent) {
        _text = lastContent;
    }

    public NewCheckboxNoteCreationObjects(String text, int position, String color) {
        this._text = text;
        this.id = position;
        this._color = color;
    }
}
