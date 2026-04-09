package com.example.retrohub.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrohub.R;
import com.example.retrohub.emulator.EmulatorLauncher;
import com.example.retrohub.model.ConsoleItem;
import com.example.retrohub.storage.RomStorageManager;
import com.google.android.material.textfield.TextInputEditText;

public class RomBrowserActivity extends AppCompatActivity {

    public static final String EXTRA_CONSOLE_ID = "extra_console_id";
    public static final String EXTRA_CONSOLE_NAME = "extra_console_name";
    public static final String EXTRA_EMULATOR_PKG = "extra_emulator_pkg";
    public static final String EXTRA_ICON_RES = "extra_icon";

    private RomStorageManager storageManager;
    private RomAdapter adapter;
    private ConsoleItem consoleItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rom_browser);

        String id = getIntent().getStringExtra(EXTRA_CONSOLE_ID);
        String name = getIntent().getStringExtra(EXTRA_CONSOLE_NAME);
        String emulatorPackage = getIntent().getStringExtra(EXTRA_EMULATOR_PKG);
        int iconRes = getIntent().getIntExtra(EXTRA_ICON_RES, R.drawable.ic_console_placeholder);
        consoleItem = new ConsoleItem(id, name, iconRes, emulatorPackage);

        TextView title = findViewById(R.id.consoleTitle);
        title.setText(name);

        storageManager = new RomStorageManager(this);
        RecyclerView recyclerView = findViewById(R.id.romRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RomAdapter(rom -> new EmulatorLauncher().openRom(this, consoleItem, rom.getUri()));
        recyclerView.setAdapter(adapter);

        TextInputEditText searchInput = findViewById(R.id.searchInput);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reloadList(s == null ? "" : s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        reloadList("");
    }

    private void reloadList(String query) {
        adapter.submit(storageManager.searchRoms(consoleItem.getId(), query));
    }
}
