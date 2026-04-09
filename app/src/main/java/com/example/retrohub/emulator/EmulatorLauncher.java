package com.example.retrohub.emulator;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.retrohub.R;
import com.example.retrohub.model.ConsoleItem;

public class EmulatorLauncher {

    public boolean openRom(Context context, ConsoleItem console, Uri romUri) {
        if (console.getEmulatorPackage() == null || console.getEmulatorPackage().isEmpty()) {
            Toast.makeText(context, "Brak gotowej integracji dla tej konsoli na Androidzie.", Toast.LENGTH_LONG).show();
            return false;
        }

        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setData(romUri);
        viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        viewIntent.setPackage(console.getEmulatorPackage());

        try {
            context.startActivity(viewIntent);
            return true;
        } catch (ActivityNotFoundException ex) {
            String msg = context.getString(R.string.emulator_missing, console.getDisplayName());
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
