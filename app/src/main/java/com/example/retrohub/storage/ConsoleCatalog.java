package com.example.retrohub.storage;

import android.content.Context;

import com.example.retrohub.R;
import com.example.retrohub.model.ConsoleItem;

import java.util.ArrayList;
import java.util.List;

public final class ConsoleCatalog {

    private ConsoleCatalog() {
    }

    public static List<ConsoleItem> getConsoles(Context context) {
        List<ConsoleItem> list = new ArrayList<>();
        list.add(create(context, "phone_games", "Gry telefonowe", null));
        list.add(create(context, "game_boy", "Game Boy", "com.fastemulator.gbemu"));
        list.add(create(context, "n64", "Nintendo 64", "org.mupen64plusae.v3.fzurita"));
        list.add(create(context, "gamecube", "GameCube", "org.dolphinemu.dolphinemu"));
        list.add(create(context, "wii", "Wii", "org.dolphinemu.dolphinemu"));
        list.add(create(context, "sega_genesis", "Sega Genesis", "org.retroarch.aarch64"));
        list.add(create(context, "nes", "NES", "com.explusalpha.NesEmu"));
        list.add(create(context, "snes", "SNES", "com.explusalpha.Snes9xPlus"));
        list.add(create(context, "xbox_360", "Xbox 360", null));
        list.add(create(context, "xbox_og", "Xbox OG", null));
        list.add(create(context, "gameboy_advance", "Game Boy Advance", "it.dbtecno.pizzaboypro"));
        list.add(create(context, "ps1", "PlayStation 1", "com.github.stenzek.duckstation"));
        list.add(create(context, "ps2", "PlayStation 2", "xyz.aethersx2.android"));
        list.add(create(context, "ps3", "PlayStation 3", null));
        list.add(create(context, "dreamcast", "Sega Dreamcast", "io.recompiled.redream"));
        list.add(create(context, "psp", "PSP", "org.ppsspp.ppsspp"));
        return list;
    }

    private static ConsoleItem create(Context context, String id, String name, String emulatorPackage) {
        int iconRes = context.getResources().getIdentifier("icon_" + id, "drawable", context.getPackageName());
        if (iconRes == 0) {
            iconRes = R.drawable.ic_console_placeholder;
        }
        return new ConsoleItem(id, name, iconRes, emulatorPackage);
    }
}
