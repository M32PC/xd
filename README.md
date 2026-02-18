# Retro Hub Launcher (Java / Android Studio)

Launcher do gier i ROM-ów napisany w **Java** pod Android Studio.

## Co już robi projekt
- Ekran startowy w orientacji poziomej (landscape) z przewijaniem konsol bokiem.
- Kafelki konsol powiększają się, gdy są na środku (efekt focus).
- Ikony konsol są ładowane automatycznie z `res/drawable/icon_(id_konsoli).png`.
- Przy pierwszym uruchomieniu aplikacja pyta o folder główny ROM-ów i tworzy podfoldery konsol.
- Widok ROM-ów ma **search bar** do filtrowania dużych bibliotek.
- Sekcja „Gry telefonowe” pokazuje aplikacje z ikoną (85% wysokości kafla) + nazwę na dole.
- Kliknięcie ROM-a próbuje odpalić zewnętrzny emulator przez `ACTION_VIEW`.

## Struktura folderów ROM
Po wybraniu folderu głównego aplikacja tworzy m.in.:
- `game_boy`
- `n64`
- `gamecube`
- `wii`
- `sega_genesis`
- `nes`
- `snes`
- `xbox_360`
- `xbox_og`
- `gameboy_advance`
- `ps1`
- `ps2`
- `ps3`
- `dreamcast`
- `psp`

## Ikony konsol
Wrzuć własne pliki PNG do `app/src/main/res/drawable/` wg schematu:
- `icon_game_boy.png`
- `icon_n64.png`
- `icon_gamecube.png`
- itd.

## Emulatory: czy trzeba instalować osobno?
**Tak.** Ten launcher integruje się z emulatorami, ale ich nie zawiera.

Powód:
- emulatory mają własne silniki i licencje,
- część platform (np. PS3 / Xbox / Xbox 360) nie ma stabilnych/emulowalnych rozwiązań na Androida.

Aplikacja próbuje otwierać ROM-y przez paczkę emulatora (np. PPSSPP, Dolphin, DuckStation). Jeśli emulatora nie ma, dostaniesz komunikat.

## Kompatybilność urządzeń
- UI jest oparte na ConstraintLayout + RecyclerView, więc skaluje się dobrze na różnych rozdzielczościach.
- Min SDK: 24 (Android 7.0), target: 34.
- Powinno działać m.in. na Redmi 9C i Galaxy S21, o ile emulator dla danej platformy wspiera urządzenie.

