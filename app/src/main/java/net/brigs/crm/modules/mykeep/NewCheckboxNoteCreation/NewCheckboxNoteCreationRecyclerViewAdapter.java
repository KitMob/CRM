package net.brigs.crm.modules.mykeep.NewCheckboxNoteCreation;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.brigs.crm.R;

import java.util.ArrayList;
import java.util.List;

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
            holder.checkBox.setBackgroundColor(Color.parseColor(currentCheckboxNoteCreationObjects.get_color()));
            holder.imageButtonDell.setBackgroundColor(Color.parseColor(currentCheckboxNoteCreationObjects.get_color()));
            _parent.setBackground(Drawable.createFromPath(currentCheckboxNoteCreationObjects.get_color()));
        }

    }

    @Override
    public int getItemCount() {

        if (checkboxNoteList != null)
            return checkboxNoteList.size();
        return 0;
    }
    public void setList(ArrayList<NewCheckboxNoteCreationObjects> itemList){
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

        public NewCheckboxNoteCreationViewHolders(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.new_checkbox_note_creation_check_box);
            text = itemView.findViewById(R.id.new_checkbox_note_creation_text_check_box);
            imageButtonDell = itemView.findViewById(R.id.image_button_dell_check_box);
            imageButtonDell.setOnClickListener(this);
            checkBox.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }


        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View view) {

            if (checkBox.isChecked() == true) {

                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else if (checkBox.isChecked() == false) {
                //TODO color
                Log.d("MyLog", " in");
                text.setPaintFlags(0);
            }

            if (view.equals(imageButtonDell)) {
                this.text.setText("");
                DeleteItem(getLayoutPosition());
            }
        }


    }

    private void DeleteItem(int position) {
        deleteItem(position);
        notifyItemRemoved(position);
    }

}


