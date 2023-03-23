package com.example.swordfight.gameObject;

public class EnemyState {
    public enum State {
        IDLE,
        CHASING,
        STUN,
        CAST_SKILL,
        ATTACK,
        DEAD,
        SLEEPING
    }

    private Enemy enemy;
    private State state;

    public EnemyState(Enemy enemy) {
        this.enemy = enemy;
        this.state = State.SLEEPING;
    }

    public State getState() {
        return state;
    }


    public void setState(State state) {
        this.state = state;
    }

}
