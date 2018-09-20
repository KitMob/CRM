package net.brigs.crm.modules.mykeep.NoteCreation.NewCheckboxNoteCreation;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.brigs.crm.R;

import java.util.List;

public class NewCheckboxNoteCreationRecyclerViewAdapter extends RecyclerView.Adapter<NewCheckboxNoteCreationViewHolders> {

    private List<NewCheckboxNoteCreationObjects> checkboxNoteList;
    private ViewGroup _parent;
    public static OnItemClickListener listener;

    public NewCheckboxNoteCreationRecyclerViewAdapter(List<NewCheckboxNoteCreationObjects> itemList) {

        checkboxNoteList = itemList;
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

    }

    @Override
    public int getItemCount() {

        if (checkboxNoteList != null)
            return checkboxNoteList.size();
        return 0;
    }




    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }



    public void addItem(int position, NewCheckboxNoteCreationObjects newCheckboxNoteCreationObjects){
        this.checkboxNoteList.add(position, newCheckboxNoteCreationObjects);
    }

    public void deleteItem(int position){
        this.checkboxNoteList.remove(position);
    }
}