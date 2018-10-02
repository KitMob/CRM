package net.brigs.crm.modules.mykeep.NewCheckboxNoteCreation;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.brigs.crm.R;

import java.util.ArrayList;

public class NewCheckboxNoteCreationRecyclerViewAdapter extends RecyclerView.Adapter<NewCheckboxNoteCreationRecyclerViewAdapter.NewCheckboxNoteCreationViewHolders> {

    private ArrayList<NewCheckboxNoteCreationObjects> checkboxNoteList;
    private ViewGroup _parent;
    public static OnItemClickListener listener;

    public NewCheckboxNoteCreationRecyclerViewAdapter(ArrayList<NewCheckboxNoteCreationObjects> itemList) {

        checkboxNoteList = itemList;
    }

    public ArrayList<NewCheckboxNoteCreationObjects> getCheckboxNoteList() {
        return checkboxNoteList;
    }

    @Override
    public NewCheckboxNoteCreationViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams")
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_checkbox_note_creation_list, null);


        _parent = parent;
        return new NewCheckboxNoteCreationViewHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(NewCheckboxNoteCreationViewHolders holder, int position) {

        NewCheckboxNoteCreationObjects currentCheckboxNoteCreationObjects = checkboxNoteList.get(position);
        if (currentCheckboxNoteCreationObjects.get_color() != null) {
            holder.text.setBackgroundColor(Color.parseColor(currentCheckboxNoteCreationObjects.get_color()));
            holder.text.requestFocus();
            holder.checkBox.setBackgroundColor(Color.parseColor(currentCheckboxNoteCreationObjects.get_color()));
            holder.imageButtonDell.setBackgroundColor(Color.parseColor(currentCheckboxNoteCreationObjects.get_color()));
            holder.color = currentCheckboxNoteCreationObjects.get_color();

            _parent.setBackground(Drawable.createFromPath(currentCheckboxNoteCreationObjects.get_color()));
        }

    }

    @Override
    public int getItemCount() {

        if (checkboxNoteList != null)
            return checkboxNoteList.size();
        return 0;
    }

    public void setList(ArrayList<NewCheckboxNoteCreationObjects> itemList) {
        this.checkboxNoteList = itemList;
    }


    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public void addItem(int position, NewCheckboxNoteCreationObjects newCheckboxNoteCreationObjects) {
        this.checkboxNoteList.add(position, newCheckboxNoteCreationObjects);
    }

    public void deleteItem(int position) {
        this.checkboxNoteList.remove(position);
    }


    public class NewCheckboxNoteCreationViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout linearLayoutNewCheckboxNoteCreationViewHolders;
        private CheckBox checkBox;
        private EditText text;
        private ImageView imageButtonDell;
        private String color;

        private int newPosition;

        public NewCheckboxNoteCreationViewHolders(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.new_checkbox_note_creation_check_box);
            text = itemView.findViewById(R.id.new_checkbox_note_creation_text_check_box);
            imageButtonDell = itemView.findViewById(R.id.image_button_dell_check_box);
            imageButtonDell.setOnClickListener(this);
            checkBox.setOnClickListener(this);


            creationOfNewPointOfTheListOnClickingOfTheButtonEnter();


            itemView.setOnClickListener(this);
        }

        private void creationOfNewPointOfTheListOnClickingOfTheButtonEnter() {
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if ((s.length() > start) && (s.charAt(start) == '\n')) {
                        newItem(getAdapterPosition());
                    }

                }


                @Override
                public void afterTextChanged(Editable s) {

                }
            };

            text.addTextChangedListener(textWatcher);
            text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        imageButtonDell.setVisibility(View.GONE);
                    }if (hasFocus){
                        imageButtonDell.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        private void newItem(int layoutPosition) {
            newPosition = layoutPosition + 1;
            AdItem(newPosition, color);
        }


        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View view) {

            if (checkBox.isChecked() == true) {

                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else if (checkBox.isChecked() == false) {
                //TODO color
                text.setPaintFlags(0);
            }

            if (view.equals(imageButtonDell)) {
                this.text.setText("");
                DeleteItem(getLayoutPosition());
            }
            if (view.equals(text)) {
                Log.d("myLog", "text");
//                imageButtonDell.setVisibility(View.VISIBLE);
            }
        }


    }

    private void DeleteItem(int position) {
        deleteItem(position);
        notifyItemRemoved(position);
    }

    private void AdItem(int position, String color) {
        NewCheckboxNoteCreationObjects newCheckboxNoteCreationObjects = new NewCheckboxNoteCreationObjects("", position, color);
        addItem(position, newCheckboxNoteCreationObjects);
        notifyItemInserted(position);
    }


}


