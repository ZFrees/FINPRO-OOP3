package com.Vanguard.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter {

    private Main game;
    private PauseMenu pauseMenu;

    private boolean paused = false;

    public GameScreen(Main game, boolean continueGame) {

        this.game = game;

        pauseMenu = new PauseMenu(
            new PauseMenu.PauseActions() {

                @Override
                public void resume() {
                    paused = false;
                }

                @Override
                public void save() {
                    saveGame();
                }

                @Override
                public void mainMenu() {
                    game.setScreen(new com.Vanguard.frontend.UI.GameScreen(game));
                    dispose();
                }

                @Override
                public void exit() {
                    Gdx.app.exit();
                }
            }
        );
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;
        }


        if (paused) {
            pauseMenu.render();
            return;
        }

    }

    private void saveGame() {
        Gdx.app.getPreferences("GalacticVanguard")
            .putBoolean("hasSave", true)
            .flush();

        System.out.println("GAME SAVED!");
    }

    @Override
    public void dispose() {
        pauseMenu.dispose();
    }
}
