package net.brigs.crm.modules;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
//import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import net.brigs.crm.R;
import net.brigs.crm.adapters.ExpandableListAdapter;
import net.brigs.crm.adapters.MenuModel;
import net.brigs.crm.modules.mykeep.GridSpacingItemDecoration;
import net.brigs.crm.modules.mykeep.ItemObjects;
import net.brigs.crm.modules.mykeep.NewCheckboxNoteCreation.NewCheckboxNoteCreation;
import net.brigs.crm.modules.mykeep.NoteCreation.SimpleNoteCreation;
import net.brigs.crm.modules.mykeep.SolventRecyclerViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static final int REQUEST_TAKE_PHOTO = 2;
    private static final String TAG = "myLog";

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    private Uri photoURI;
    private String mCurrentPhotoPath;

    List<ItemObjects> staggeredList;
    SolventRecyclerViewAdapter rcAdapter;
    List<ItemObjects> listViewItems;
    RecyclerView recyclerView;
    // Drag and drop
    ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            Collections.swap(staggeredList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            rcAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //TODO
        }

        // Defines the enabled move directions in each state (idle, swiping, dragging).
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

            return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                    ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        expandableListView = findViewById(R.id.dynamic);
        prepareMenuData();
        populateExpandableList();

        if (Build.VERSION.SDK_INT >= 23) {
            if (!checkPermissionForReadExternalStorage())
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            if (!checkPermissionForWriteExternalStorage())
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // Check orientation to put the good amount of columns
        int column = 2;
        if (getResources().getConfiguration().orientation == 2)
            column = 3;

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(column, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        // Prevent the loss of items
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);


        final float scale = getResources().getDisplayMetrics().density;
        int spacing = (int) (1 * scale + 0.5f);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spacing));

        // Load notes from internal storage
        staggeredList = loadNotes();

        rcAdapter = new SolventRecyclerViewAdapter(staggeredList);
        recyclerView.setAdapter(rcAdapter);

        // Drag and drop
        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView(recyclerView);

        // Create a simple note button click listenersimpleNoteIntent
        Button createSimpleNoteButton = findViewById(R.id.create_new_note);
        createSimpleNoteButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            public void onClick(View v) {
                Intent simpleNoteIntent = new Intent(getApplicationContext(), SimpleNoteCreation.class);
                simpleNoteIntent.putExtra("title", "");
                simpleNoteIntent.putExtra("content", "");
                simpleNoteIntent.putExtra("color", getResources().getString(R.color.colorNoteDefault));
                simpleNoteIntent.putExtra("creationDate", "");
                simpleNoteIntent.putExtra("position", -1);
                startActivityForResult(simpleNoteIntent, 1);
            }
        });

        //create new checkbox note
        ImageButton createNewNoteChekboxNote = findViewById(R.id.create_new_checkbox_note);
        createNewNoteChekboxNote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            public void onClick(View v) {
                //TODO create_new_checkbox_note
                Intent NewCheckboxNoteCreationNoteIntent = new Intent(getApplicationContext(), NewCheckboxNoteCreation.class);
                NewCheckboxNoteCreationNoteIntent.putExtra("title", "");
                NewCheckboxNoteCreationNoteIntent.putExtra("content", "");
                NewCheckboxNoteCreationNoteIntent.putExtra("color", getResources().getString(R.color.colorNoteDefault));
                NewCheckboxNoteCreationNoteIntent.putExtra("creationDate", "");
                NewCheckboxNoteCreationNoteIntent.putExtra("position", -1);
                startActivityForResult(NewCheckboxNoteCreationNoteIntent, 1);
            }
        });

        // create_new_photo_note
        ImageButton createNewPhotoNote = findViewById(R.id.create_new_photo_note);
        createNewPhotoNote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            public void onClick(View v) {

                dispatchTakePictureIntent();
            }
        });
    }

    // Get the data from the note creation
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        fotoFromCameraCoise(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            String noteJSON = data.getStringExtra("noteJSON");

            try {
                JSONObject json = new JSONObject(noteJSON);

                String noteLastFoto = json.getString("imageViewPhoto");
                Log.d(TAG, "noteLastFoto = " + noteLastFoto);

                String noteTitle = json.getString("noteTitle");
                String noteContent = json.getString("noteContent");
                String noteColor = json.getString("noteColor");
                String noteLastUpdateDate = json.getString("noteLastUpdateDate");
                String noteCreationDate = json.getString("noteCreationDate");
                int notePosition = json.getInt("notePosition");

                saveNote(noteJSON, noteCreationDate);
                Log.d(TAG, "Get the data from the note creation: " + noteJSON.toString());

                if (notePosition > -1) {

                    listViewItems.remove(notePosition);

                    listViewItems.add(notePosition, new ItemObjects(noteTitle, noteContent, noteLastFoto, noteColor, noteLastUpdateDate, noteCreationDate));
                    rcAdapter.notifyItemChanged(notePosition);
                } else {
                    listViewItems.add(new ItemObjects(noteTitle, noteContent, noteLastFoto, noteColor, noteLastUpdateDate, noteCreationDate));
                    rcAdapter.notifyDataSetChanged();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // Load notes from internal storage
    private List<ItemObjects> loadNotes() {

        listViewItems = new ArrayList<>();
        String[] allNotes = fileList();

        Boolean secure = false;

        for (String allNote : allNotes) {

            FileInputStream fis = null;
            try {
                fis = getBaseContext().openFileInput(allNote);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            InputStreamReader isr = null;
            if (fis != null) {
                isr = new InputStreamReader(fis);
            }
            BufferedReader bufferedReader = null;
            if (isr != null) {
                bufferedReader = new BufferedReader(isr);
            }
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                if (bufferedReader != null) {
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            JSONObject json = null;
            try {
                if (sb.toString().length() > 0)
                    secure = true;
                json = new JSONObject(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String noteTitle = null;
            try {
                if (json != null) {
                    noteTitle = json.getString("noteTitle");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String noteContent = null;
            try {
                if (json != null) {
                    noteContent = json.getString("noteContent");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String noteColor = null;
            try {
                if (json != null) {
                    noteColor = json.getString("noteColor");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String noteLastUpdateDate = null;
            try {
                if (json != null) {
                    noteLastUpdateDate = json.getString("noteLastUpdateDate");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String noteCreationDate = null;
            try {
                if (json != null) {
                    noteCreationDate = json.getString("noteCreationDate");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String notePoto = null;
            try {
                if (json != null) {
                    notePoto = json.getString("imageViewPhoto");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (secure) {

                Log.d(TAG, "notePoto: " + notePoto);
                listViewItems.add(new ItemObjects(noteTitle, noteContent, notePoto, noteColor,
                        noteLastUpdateDate, noteCreationDate));
            }
        }
        return listViewItems;
    }

    // Save notes to internal storage
    public void saveNote(String note, String noteCreationDate) {

        // Name file with current date
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(noteCreationDate, Context.MODE_PRIVATE);
            outputStream.write(note.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        //TODO обработать
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Check for read in external storage
    public boolean checkPermissionForReadExternalStorage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int result = getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    // Check for write in external storage
    public boolean checkPermissionForWriteExternalStorage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int result = getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            // Handle the camera action
        } else if (id == R.id.dashbrd) {

        } else if (id == R.id.sales) {

        } else if (id == R.id.contacts) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Android WebView Tutorial", true, false, ""); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Java Tutorials", true, true, ""); //Menu of Java Tutorials
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Core Java Tutorial", false, false, "https://www.journaldev.com/7153/core-java-tutorial");
        childModelsList.add(childModel);

        childModel = new MenuModel("Java FileInputStream", false, false, "https://www.journaldev.com/19187/java-fileinputstream");
        childModelsList.add(childModel);

        childModel = new MenuModel("Java FileReader", false, false, "https://www.journaldev.com/19115/java-filereader");
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            Log.d("API123", "here");
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Python Tutorials", true, true, ""); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Python AST – Abstract Syntax Tree", false, false, "https://www.journaldev.com/19243/python-ast-abstract-syntax-tree");
        childModelsList.add(childModel);

        childModel = new MenuModel("Python Fractions", false, false, "https://www.journaldev.com/19226/python-fractions");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {

                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if (model.url.length() > 0) {

                    }
                }

                return false;
            }
        });
    }


//for foto from camera


    private void fotoFromCameraCoise(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            startSimpleNoteIntentForFoto(photoURI);
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_CANCELED) {

            Log.d(TAG, "Canceled");
        }
    }


    @SuppressLint("ResourceType")
    private void startSimpleNoteIntentForFoto(Uri photo) {
        Intent simpleNoteIntent = new Intent(getApplicationContext(), SimpleNoteCreation.class);
        simpleNoteIntent.putExtra("photo", photo.toString());
        simpleNoteIntent.putExtra("title", "");
        simpleNoteIntent.putExtra("content", "");
        simpleNoteIntent.putExtra("color", getResources().getString(R.color.colorNoteDefault));
        simpleNoteIntent.putExtra("creationDate", "");
        simpleNoteIntent.putExtra("position", -1);
        startActivityForResult(simpleNoteIntent, 1);
    }


    //part of a code is taken from here: https://startandroid.ru/ru/uroki/vse-uroki-spiskom/138-urok-75-hranenie-dannyh-rabota-s-fajlami.html
    private File writeFileSD() {
        File sdPath = null;
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            sdPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            Log.d(TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
        } else {
            // получаем путь к SD
            sdPath = Environment.getExternalStorageDirectory();
            // добавляем свой каталог к пути
            sdPath = new File(sdPath.getAbsolutePath() + "/" + "." + getPackageName() + "/" + Environment.DIRECTORY_PICTURES);
            // создаем каталог
            if (!sdPath.exists()) {
                sdPath.mkdirs();
            }
            return sdPath;
        }
        return sdPath;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        } else {
            timeStamp = String.valueOf(System.currentTimeMillis());
        }
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = writeFileSD();
        Log.d(TAG, "storageDir: " + storageDir);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Error occurred while creating the File!", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, "net.brigs.crm.provider",
                        photoFile);
                Log.d(TAG, "photoURI: " + photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}
