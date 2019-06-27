package com.github.wojTechM.threadsDemo;

/**
 * @author Makiela Wojciech
 */
class Xardas implements NekromantaBehaviour {

    @Override
    public void wskrześ(Wskrzeszable zwłoki) {
        System.out.println("O nie! Zwłoki księcia! Szybko! Wskrześ dziada!");
        zwłoki.wskrześ();
        System.out.println("It's ALIVE!");
    }

    @Override
    public void śmiejSięDemonicznie() {
        System.out.println("BUHAHAHAHAHAHA");
    }
}
