package com.github.wojTechM.threadsDemo;

/**
 * @author Makiela Wojciech
 */
class ZwłokiKsięciaZBajki implements Wskrzeszable, Runnable {

    private Thread thread;

    @Override
    public void run() {
        while (true) {
            if (!żyje()) {
                // nic nie robi. Umar
            }
            if (żyje()) { // sprawdzenie dla pewności
                System.out.println("Móóózzgiiiii");
                break;
            }
        }
    }

    private boolean żyje() {
        return thread.interrupted();
    }

    @Override
    public void wskrześ() {
        thread = new Thread(this);
        thread.start();
        thread.interrupt();
    }
}
