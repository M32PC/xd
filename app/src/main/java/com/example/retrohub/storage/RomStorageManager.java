package com.example.retrohub.storage;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.documentfile.provider.DocumentFile;

import com.example.retrohub.model.ConsoleItem;
import com.example.retrohub.model.RomEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RomStorageManager {
    private static final String PREF_NAME = "retrohub_prefs";
    private static final String KEY_ROOT_URI = "root_uri";

    private final Context context;

    public RomStorageManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public void saveRootFolder(Uri rootUri) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_ROOT_URI, rootUri.toString()).apply();

        ContentResolver resolver = context.getContentResolver();
        resolver.takePersistableUriPermission(rootUri,
                android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION | android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }

    public Uri getRootUri() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String uri = prefs.getString(KEY_ROOT_URI, null);
        return uri != null ? Uri.parse(uri) : null;
    }

    public void ensureConsoleFolders(List<ConsoleItem> consoles) {
        Uri rootUri = getRootUri();
        if (rootUri == null) {
            return;
        }
        DocumentFile root = DocumentFile.fromTreeUri(context, rootUri);
        if (root == null || !root.exists()) {
            return;
        }
        for (ConsoleItem item : consoles) {
            if ("phone_games".equals(item.getId())) {
                continue;
            }
            DocumentFile sub = root.findFile(item.getId());
            if (sub == null || !sub.isDirectory()) {
                root.createDirectory(item.getId());
            }
        }
    }

    public DocumentFile getConsoleFolder(String consoleId) {
        Uri rootUri = getRootUri();
        if (rootUri == null) {
            return null;
        }
        DocumentFile root = DocumentFile.fromTreeUri(context, rootUri);
        if (root == null) {
            return null;
        }
        return root.findFile(consoleId);
    }

    public List<RomEntry> searchRoms(String consoleId, String query) {
        List<RomEntry> out = new ArrayList<>();
        DocumentFile folder = getConsoleFolder(consoleId);
        if (folder == null || !folder.exists()) {
            return out;
        }

        String normalized = query == null ? "" : query.toLowerCase(Locale.ROOT);
        walk(folder, normalized, out);
        out.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return out;
    }

    private void walk(DocumentFile dir, String query, List<RomEntry> out) {
        for (DocumentFile file : dir.listFiles()) {
            if (file.isDirectory()) {
                walk(file, query, out);
            } else if (file.isFile()) {
                String name = file.getName();
                if (name != null && name.toLowerCase(Locale.ROOT).contains(query)) {
                    out.add(new RomEntry(name, file.getUri()));
                }
            }
        }
    }
}
