package com.example.retrohub.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrohub.R;
import com.example.retrohub.model.ConsoleItem;
import com.example.retrohub.storage.ConsoleCatalog;
import com.example.retrohub.storage.RomStorageManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RomStorageManager storageManager;
    private List<ConsoleItem> consoles;
    private ConsoleAdapter adapter;

    private final ActivityResultLauncher<Intent> folderPickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri treeUri = result.getData().getData();
                    if (treeUri != null) {
                        storageManager.saveRootFolder(treeUri);
                        storageManager.ensureConsoleFolders(consoles);
                        Toast.makeText(this, "Folder został zapisany.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageManager = new RomStorageManager(this);
        consoles = ConsoleCatalog.getConsoles(this);

        setupRecycler();
        askForFolderIfNeeded();
    }

    private void setupRecycler() {
        RecyclerView recycler = findViewById(R.id.consolesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(layoutManager);

        adapter = new ConsoleAdapter(consoles, this::openConsole);
        recycler.setAdapter(adapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recycler);

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getChildCount() == 0) return;
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
                if (lm == null) return;
                android.view.View center = snapHelper.findSnapView(lm);
                if (center != null) {
                    int pos = recyclerView.getChildAdapterPosition(center);
                    adapter.setFocusedPosition(pos);
                }
            }
        });
        recycler.post(() -> adapter.setFocusedPosition(0));
    }

    private void askForFolderIfNeeded() {
        if (storageManager.getRootUri() != null) {
            storageManager.ensureConsoleFolders(consoles);
            return;
        }
        Toast.makeText(this, getString(R.string.pick_root_folder), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        folderPickerLauncher.launch(intent);
    }

    private void openConsole(ConsoleItem consoleItem) {
        if ("phone_games".equals(consoleItem.getId())) {
            startActivity(new Intent(this, PhoneGamesActivity.class));
            return;
        }

        if (storageManager.getRootUri() == null) {
            askForFolderIfNeeded();
            return;
        }

        Intent intent = new Intent(this, RomBrowserActivity.class);
        intent.putExtra(RomBrowserActivity.EXTRA_CONSOLE_ID, consoleItem.getId());
        intent.putExtra(RomBrowserActivity.EXTRA_CONSOLE_NAME, consoleItem.getDisplayName());
        intent.putExtra(RomBrowserActivity.EXTRA_EMULATOR_PKG, consoleItem.getEmulatorPackage());
        intent.putExtra(RomBrowserActivity.EXTRA_ICON_RES, consoleItem.getIconResId());
        startActivity(intent);
    }
}
