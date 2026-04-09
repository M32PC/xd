package com.example.retrohub.model;

import android.net.Uri;

public class RomEntry {
    private final String name;
    private final Uri uri;

    public RomEntry(String name, Uri uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public Uri getUri() {
        return uri;
    }
}
