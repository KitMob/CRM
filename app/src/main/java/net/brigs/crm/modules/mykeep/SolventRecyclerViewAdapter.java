package net.brigs.crm.modules.mykeep;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.brigs.crm.R;

import java.util.List;

public class SolventRecyclerViewAdapter extends RecyclerView.Adapter<SolventViewHolders> {

    private List<ItemObjects> _itemList;
    private ViewGroup _parent;

    public SolventRecyclerViewAdapter(List<ItemObjects> itemList) {

        _itemList = itemList;
    }

    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams") View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, null);

        _parent = parent;
        return new SolventViewHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(SolventViewHolders holder, int position) {

        holder.title.setText(_itemList.get(position).getTitle());
        // If title is empty, hide title edit text
        if (holder.title.getText().toString().isEmpty()) {
            holder.title.setHeight(0);
        }
        holder.content.setText(_itemList.get(position).getContent());
        // If content is empty, hide content edit text
        if (holder.content.getText().toString().isEmpty()) {
            holder.content.setHeight(0);
        }

        if (_itemList.get(position).getImage() != null) {
            Log.d("myLog", "getImage: " + _itemList.get(position).getImage());
            holder.image.setImageURI(Uri.parse(_itemList.get(position).getImage()));
        }

        if (_itemList.get(position).getImage() != null || (!holder.title.getText().toString().isEmpty() && holder.content.getText().toString().isEmpty())) {

            final float scale = _parent.getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (8 * scale + 0.5f);
            holder.title.setPadding(pixels, pixels, pixels, pixels);
        }

        if (holder.content.getText().toString().length() > 0 && holder.content.getText().toString().length() < 6) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_thin);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(70);
        }
        if (holder.content.getText().toString().length() >= 6 && holder.content.getText().toString().length() < 10) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_light);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(50);
        }
        if (holder.content.getText().toString().length() >= 10 && holder.content.getText().toString().length() < 13) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_light);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(36);
        }
        if (holder.content.getText().toString().length() >= 13 && holder.content.getText().toString().length() < 19) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_light);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(24);
        }
        if (holder.content.getText().toString().length() >= 19 && holder.content.getText().toString().length() < 24) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_regular);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(18);
        }
        if (holder.content.getText().toString().length() >= 24) {
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = _parent.getContext().getResources().getFont(R.font.roboto_slab_regular);
            }
            holder.content.setTypeface(typeface);
            holder.content.setTextSize(16);
        }
        if (!holder.title.getText().toString().isEmpty())
            holder.title.setBackgroundColor(Color.parseColor(_itemList.get(position).getColor()));
        if (!holder.content.getText().toString().isEmpty())
            holder.content.setBackgroundColor(Color.parseColor(_itemList.get(position).getColor()));
        holder.color = _itemList.get(position).getColor();
        holder.creationDate = _itemList.get(position).getCreationDate();
    }

    @Override
    public int getItemCount() {

        if (_itemList != null)
            return _itemList.size();
        return 0;
    }
}