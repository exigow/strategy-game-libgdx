package com.me.strategy_game_libgdx.gamelogic.stepper;

public class Stepper {
    public enum Step {wait, press, hold, release}
    private Step actualStep;

    // Konstruktor.
    public Stepper() {
        actualStep = Step.wait;
    }

    // Petla stepa. Zachowuje kolejnosc.
    // WAIT -> PRESS -> HOLD -> RELEASE
    public void pickStepFrom(boolean source) {
        if (source) {
            if (actualStep == Step.press) {
                actualStep = Step.hold;
            }
            if (actualStep == Step.wait) {
                actualStep = Step.press;
            }
        } else {
            if (actualStep == Step.release) {
                actualStep = Step.wait;
            }
            if (actualStep == Step.hold) {
                actualStep = Step.release;
            }
        }
    }

    // Zwraca aktualny step.
    public Step getStep() {
        return actualStep;
    }

    // Sprawdza czy podany step jest aktualny.
    public boolean stepIs(Step step) {
        return this.actualStep == step;
    }

    // Zwraca status w postaci stringa.
    public String getStatusString() {
        String str = "";
        switch (actualStep) {
            case wait: { str = "WAIT"; break; }
            case press: { str = "PRESS"; break; }
            case hold: { str = "HOLD"; break; }
            case release: { str = "RELEASE"; break; }
        }
        return str;
    }

    // Przeznaczone na override.
    public void waitAction() {
    }
    public void pressAction() {
    }
    public void holdAction() {
    }
    public void releaseAction() {
    }
}
