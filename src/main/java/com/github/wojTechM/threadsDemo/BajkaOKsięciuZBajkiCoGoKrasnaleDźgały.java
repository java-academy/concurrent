package com.github.wojTechM.threadsDemo;

/**
 * Zadanie bardzo proste. Masz kod obsługujący zwłoki księcia z bajki.
 * Możesz
 * a) dostawić 2 znaki/litery/liczby/symbole w kodzie i kod ma zadziałać (dostawianie w printach i komentarzach się nie liczy)
 * b) usunąć 2 znaki w kodzie i kod ma zadziałać (usuwanie w printach i komentarzach się nie liczy)
 * c) zamienić kolejność 2 linijek
 * i w ten sposób sprawić, że książe wróci do świata żywych.
 *
 *
 * W konsoli masz mieć komunikat:
 *
 * O nie! Zwłoki księcia! Szybko! Wskrześ dziada!
 * It's ALIVE!
 * Móóózzgiiiii
 *
 * !SPOILER!
 * Zadanie da się rozwiązać tylko na 2 z 3 podanych sposobów.
 * Które nie działa? Ty mi powiedz! :P
 *
 * wojciech.śmiejSięDemonicznie();
 *
 * @author Makiela Wojciech
 */
class BajkaOKsięciuZBajkiCoGoKrasnaleDźgały {

    public static void main(String[] args) {
        Wskrzeszable zwłoki = new ZwłokiKsięciaZBajki();
        NekromantaBehaviour nekromanta = new Xardas();
        nekromanta.wskrześ(zwłoki);
    }
}
