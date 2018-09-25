package net.brigs.crm.modules.mykeep.NewCheckboxNoteCreation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import net.brigs.crm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewCheckboxNoteCreation extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewNewCheckboxCoteCreationList;
    private NewCheckboxNoteCreationRecyclerViewAdapter newCheckboxNoteCreationRecyclerViewAdapter;
    private RadioGroup colorPickerRadioGroup;


    private TextView textViewAdCheckBox;
    private ImageView imageViewAddCheckBox;

    private TableLayout newCheckboxNoteCreationTableLayout, bottomToolbar;
    private String lastTitle;
    private String lastContent;
    private String noteColor, color;
    private String creationDateString;
    private int id;
    private ArrayList<NewCheckboxNoteCreationObjects> list;
    private int position;
    private LinearLayout noteActionsLayout;
    private ImageButton noteActionsButton;
    private String lastUpdateDateString;
    private TextView lastUpdateDateTextView;
    private EditText title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_checkbox_note_creation);

        Intent editionIntent = getIntent();
        lastTitle = editionIntent.getStringExtra("title");
        lastContent = editionIntent.getStringExtra("content");
        color = editionIntent.getStringExtra("color");
        creationDateString = editionIntent.getStringExtra("creationDate");

        recyclerViewNewCheckboxCoteCreationList = findViewById(R.id.new_checkbox_note_creation_recycler_view);

        title = findViewById(R.id.new_checkbox_note_creation_title_edit_text);
        lastUpdateDateTextView = findViewById(R.id.last_modification_date);


        colorPickerRadioGroup = findViewById(R.id.new_checkbox_color_picker_radio_group);


        imageViewAddCheckBox = findViewById(R.id.imageView_add_check_box);
        textViewAdCheckBox = findViewById(R.id.text_view_add_check_box);


        imageViewAddCheckBox.setOnClickListener(this);
        textViewAdCheckBox.setOnClickListener(this);

        newCheckboxNoteCreationTableLayout = findViewById(R.id.new_checkbox_note_creation_table_layout);
        noteActionsLayout = findViewById(R.id.new_checkbox_note_actions_layout);
        noteActionsButton = findViewById(R.id.note_actions_button);
        bottomToolbar = findViewById(R.id.bottom_toolbar);


        // Set activity default color
        newCheckboxNoteCreationTableLayout.setBackgroundColor(Color.parseColor(color));
        noteActionsLayout.setBackgroundColor(Color.parseColor(color));
        bottomToolbar.setBackgroundColor(Color.parseColor(color));
        title.setBackgroundColor(Color.parseColor(color));
        position = 0;
        recyclerViewNewCheckboxCoteCreationList(lastTitle, position, color);
        recyclerViewNewCheckboxCoteCreationList.setBackgroundColor(Color.parseColor(color));


        noteColor = color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(darkenNoteColor(Color.parseColor(noteColor), 0.7f));
        }


        // Get date
        if (creationDateString.isEmpty())
            creationDateString = new SimpleDateFormat("ddMMyyyyhhmmss", Locale.getDefault()).format(new Date());
        lastUpdateDateString = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());


        // Set "Last Update" field content
        lastUpdateDateTextView.setText("Last update : " + lastUpdateDateString);


        // Hide note actions by default
        noteActionsLayout.setVisibility(View.GONE);

        // Hide/Show note actions
        noteActionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (noteActionsLayout.getVisibility() == View.GONE) {
                    noteActionsLayout.setVisibility(View.VISIBLE);
                    noteActionsButton.setBackgroundColor(darkenNoteColor(Color.parseColor(noteColor), 0.9f));
                } else if (noteActionsLayout.getVisibility() == View.VISIBLE) {
                    noteActionsButton.setBackgroundColor(Color.parseColor(noteColor));
                    noteActionsLayout.setVisibility(View.GONE);
                }
            }
        });


        // Check the color picker
        colorPickerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.default_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteDefault);

                } else if (checkedId == R.id.red_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteRed);
                } else if (checkedId == R.id.orange_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteOrange);
                } else if (checkedId == R.id.yellow_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteYellow);
                } else if (checkedId == R.id.green_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteGreen);
                } else if (checkedId == R.id.cyan_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteCyan);
                } else if (checkedId == R.id.light_blue_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteLightBlue);
                } else if (checkedId == R.id.dark_blue_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteDarkBlue);
                } else if (checkedId == R.id.purple_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNotePurple);
                } else if (checkedId == R.id.pink_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNotePink);
                } else if (checkedId == R.id.brown_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteBrow);
                } else if (checkedId == R.id.grey_color_checkbox) {
                    noteColor = getResources().getString(R.color.colorNoteGrey);
                }
                title.setBackgroundColor(Color.parseColor(noteColor));
                newCheckboxNoteCreationTableLayout.setBackgroundColor(Color.parseColor(noteColor));
                noteActionsLayout.setBackgroundColor(Color.parseColor(noteColor));
                bottomToolbar.setBackgroundColor(Color.parseColor(noteColor));
                recyclerViewNewCheckboxCoteCreationList.setBackgroundColor(Color.parseColor(noteColor));

                getWindow().setStatusBarColor(darkenNoteColor(Color.parseColor(noteColor), 0.7f));
                //TODO change color of list


                noteActionsButton.setBackgroundColor(darkenNoteColor(Color.parseColor(noteColor), 0.9f));
                recyclerViewNewCheckboxCoteCreationList("", 1, noteColor);

            }
        });


    }


    public static int darkenNoteColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }


    @Override
    public void onClick(View v) {

        //TODO if push enter
        position = newCheckboxNoteCreationRecyclerViewAdapter.getItemCount();
        AdItem(position);

    }


    private void recyclerViewNewCheckboxCoteCreationList(String lastTitle, int position, String color) {

        // Open keyboard and put focus on the content point
        openKeyboardAndPutFocusOnTheContentPoint(lastTitle);

        list = getList(lastTitle, position, color);
        newCheckboxNoteCreationRecyclerViewAdapter = new NewCheckboxNoteCreationRecyclerViewAdapter(list);
        recyclerViewNewCheckboxCoteCreationList.setAdapter(newCheckboxNoteCreationRecyclerViewAdapter);
        recyclerViewNewCheckboxCoteCreationList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void openKeyboardAndPutFocusOnTheContentPoint(String lastTitle) {
        if (lastTitle.isEmpty() && lastContent.isEmpty()) {
            recyclerViewNewCheckboxCoteCreationList.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.showSoftInput(recyclerViewNewCheckboxCoteCreationList, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    private ArrayList<NewCheckboxNoteCreationObjects> getList(String text, int position, String color) {
        list = new ArrayList<>();
        NewCheckboxNoteCreationObjects newCheckboxNoteCreationObjects = new NewCheckboxNoteCreationObjects(text, position, color);
        list.add(newCheckboxNoteCreationObjects);


        return list;
    }


    private void AdItem(int position) {
        NewCheckboxNoteCreationObjects newCheckboxNoteCreationObjects = new NewCheckboxNoteCreationObjects("", position, noteColor);

        newCheckboxNoteCreationRecyclerViewAdapter.addItem(position, newCheckboxNoteCreationObjects);
        newCheckboxNoteCreationRecyclerViewAdapter.notifyItemInserted(position);
        this.position = newCheckboxNoteCreationRecyclerViewAdapter.getItemCount();
    }


}
