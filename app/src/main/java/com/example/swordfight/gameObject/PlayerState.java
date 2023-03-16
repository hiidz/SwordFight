package com.example.swordfight.gameObject;

public class PlayerState {

    public enum State {
        NOT_MOVING,
        IS_MOVING
    }

    private Player player;
    private State state;

    public PlayerState(Player player) {
        this.player = player;
        this.state = State.NOT_MOVING;
    }

    public State getState() {
        return state;
    }

    public void update() {
        switch (state) {
            case NOT_MOVING:
                if (player.getVelocity().getX() != 0 || player.getVelocity().getY() != 0)
                    state = State.IS_MOVING;
                break;
            case IS_MOVING:
                if (player.getVelocity().getX() == 0 && player.getVelocity().getY() == 0)
                    state = State.NOT_MOVING;
                break;
            default:
                break;
        }
    }
}
