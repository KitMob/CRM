package net.brigs.crm.modules.mykeep;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import net.brigs.crm.R;
import net.brigs.crm.modules.mykeep.NoteCreation.SimpleNoteCreation;

public class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView    title;
    TextView    content;
    ImageView   image;
    String      color;
    String      creationDate;

    SolventViewHolders(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        title = itemView.findViewById(R.id.note_title);
        content = itemView.findViewById(R.id.note_content);
        image = itemView.findViewById(R.id.note_image);
    }

    @Override
    public void onClick(View view) {

        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Intent simpleNoteEditionIntent = new Intent(view.getContext(), SimpleNoteCreation.class);

        simpleNoteEditionIntent.putExtra("photo", image.getTag().toString());
        simpleNoteEditionIntent.putExtra("title", title.getText());
        simpleNoteEditionIntent.putExtra("content", content.getText());
        simpleNoteEditionIntent.putExtra("color", color);
        simpleNoteEditionIntent.putExtra("creationDate", creationDate);
        simpleNoteEditionIntent.putExtra("position", getPosition());
        ((Activity) view.getContext()).startActivityForResult(simpleNoteEditionIntent, 1);
    }
}
