package cz.pikadorama.lakatos.app;

import java.util.ArrayList;
import java.util.List;

import cz.pikadorama.lakatos.R;

public enum Sound {
    ABYCH(R.raw.abych_mohl_toto, "abych_mohl_toto", "Abys mohl toto!", "http://milujipraci.cz/#15"),
    ANIOCKO(R.raw.ani_ocko_nenasadis, "ani_ocko_nenasadis", "Ani očko nenasadíš!", "http://milujipraci.cz/#16"),
    ANIZAKOKOT(R.raw.ani_za_kokot_vole, "ani_za_kokot_vole", "Ani za kokot vole!", "http://milujipraci.cz/#00"),
    BANALNI(R.raw.banalni_vec, "banalni_vec", "Banální věc!", "http://milujipraci.cz/#17"),
    DOPICE(R.raw.do_pice, "do_pice", "Do piče!", "http://milujipraci.cz/#01"),
    HAZLI(R.raw.hajzli_jedni, "hajzli_jedni", "Hajzli jedni!", "http://milujipraci.cz/#02"),
    HOSI(R.raw.hosi_to_je_neuveritelne, "hosi_to_je_neuveritelne", "Hoši, toto je neuvěřitelné!", "http://milujipraci.cz/#03"),
    JASEPOJEBAT(R.raw.ja_se_z_toho_musim_pojebat, "ja_se_z_toho_musim_pojebat", "Já se z toho musim pojebat!", "http://milujipraci.cz/#04"),
    JATOMRDAM(R.raw.ja_to_mrdam, "ja_to_mrdam", "Já to mrdám!", "http://milujipraci.cz/#05"),
    JATONEJDUDELAT(R.raw.ja_to_nejdu_delat, "ja_to_nejdu_delat", "Já to nejdu dělat!", "http://milujipraci.cz/#18"),
    JEDINOUPICOVINKU(R.raw.jedinou_picovinku, "jedinou_picovinku", "Jedinou pičovinku!", "http://milujipraci.cz/#06"),
    JEDUDOPICI(R.raw.jedu_do_pici_stadyma, "jedu_do_pici_stadyma", "Jedu do piči!", "http://milujipraci.cz/#07"),
    KURVA(R.raw.kurva, "kurva", "KURVA!", "http://milujipraci.cz/#08"),
    KURVADOPICE(R.raw.kurva_do_pice_to_neni_mozne, "kurva_do_pice_to_neni_mozne", "To není možné!", "http://milujipraci.cz/#09"),
    KURVAUZ(R.raw.kurva_uz, "kurva_uz", "Kurva už!", "http://milujipraci.cz/#19"),
    NENENASADIS(R.raw.ne_nenasadis_ho, "ne_nenasadis_ho", "Ne, nenasadíš ho!", "http://milujipraci.cz/#20"),
    NEBUDU(R.raw.nebudu_to_delat, "nebudu_to_delat", "Nebudu to dělat!", "http://milujipraci.cz/#10"),
    NEJVETSI(R.raw.nejvetsi_blbec_na_zemekouli, "nejvetsi_blbec_na_zemekouli", "Největší blbec na zeměkouli!", "http://milujipraci.cz/#21"),
    NENASADIM(R.raw.nenasadim, "nenasadim", "Nenasadím!", "http://milujipraci.cz/#22"),
    NERESITELNY(R.raw.neresitelny_problem_hosi, "neresitelny_problem_hosi", "Neřešitelný problém hoši!", "http://milujipraci.cz/#23"),
    NEVIMJAK(R.raw.nevim_jak, "nevim_jak", "Nevim jak!", "http://milujipraci.cz/#24"),
    OKAMZITE(R.raw.okamzite_zabit_ty_kurvy, "okamzite_zabit_ty_kurvy", "Okamžitě zabít ty kurvy!", "http://milujipraci.cz/#25"),
    PASTVEDLE(R.raw.past_vedle_pasti_pico, "past_vedle_pasti_pico", "Past vedle pasti!", "http://milujipraci.cz/#11"),
    POCKEJKAMO(R.raw.pockej_kamo, "pockej_kamo", "Počkej kámo!", "http://milujipraci.cz/#26"),
    TADYMUSIS(R.raw.tady_musis_vsechno_rozdelat, "tady_musis_vsechno_rozdelat", "Tady musíš všechno rozdělat!", "http://milujipraci.cz/#27"),
    TOJEPICO(R.raw.to_je_pico_nemozne, "to_je_pico_nemozne", "To je nemožné!", "http://milujipraci.cz/#12"),
    TONENI(R.raw.to_neni_normalni_kurva, "to_neni_normalni_kurva", "To neni normální!", "http://milujipraci.cz/#13"),
    TOSOU(R.raw.to_sou_nervy_ty_pico, "to_sou_nervy_ty_pico", "To sou nervy ty pičo!", "http://milujipraci.cz/#14"),
    TUTOPICU(R.raw.tuto_picu_potrebuju_utahnout, "tuto_picu_potrebuju_utahnout", "Tuto piču potřebuju utáhnout!", "http://milujipraci.cz/#28"),
    ZASRANE(R.raw.zasrane_zamrdane, "zasrane_zamrdane", "Zasrané, zamrdané!", "http://milujipraci.cz/#29");

    private final int soundId;
    private final String soundName;
    private final String message;
    private final String url;

    Sound(int soundId, String soundName, String message, String url) {
        this.soundId = soundId;
        this.soundName = soundName;
        this.message = message;
        this.url = url;
    }

    public int getSoundId() {
        return soundId;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    public String getSoundName() {
        return soundName;
    }

    public static List<String> getMessages() {
        List<String> messages = new ArrayList<>();
        for (Sound sound : Sound.values()) {
            messages.add(sound.getMessage());
        }
        return messages;
    }
}