package com.dayson.backend.manager;
import com.dayson.backend.manager.EnemyManager;

public class WaveManager {

    private int currentWave;
    private int remainingEnemies;

    private float spawnTimer;
    private float spawnInterval;

    private float intermissionTimer;
    private final float intermissionDuration = 3f;

    private boolean waitingForNextWave;

    public WaveManager() {
        currentWave = 0;
        remainingEnemies = 0;

        spawnTimer = 0;
        spawnInterval = 1.5f;

        intermissionTimer = 0;
        waitingForNextWave = true;
    }

    private void startNextWave() {

        currentWave++;

        remainingEnemies = 5 + (currentWave - 1) * 3;

        spawnInterval = Math.max(
            0.4f,
            1.5f - (currentWave - 1) * 0.1f
        );

        spawnTimer = 0;
        waitingForNextWave = false;

        System.out.println("WAVE " + currentWave + " STARTED!");
    }

    // Update sistem wave
    public void update(float delta, EnemyManager enemyManager) {

        // Menunggu sebelum wave berikutnya
        if (waitingForNextWave) {

            intermissionTimer += delta;

            if (intermissionTimer >= intermissionDuration) {

                intermissionTimer = 0;

                startNextWave();
            }

            return;
        }

        // Spawn musuh berdasarkan interval
        if (remainingEnemies > 0) {

            spawnTimer += delta;

            if (spawnTimer >= spawnInterval) {

                enemyManager.spawnEnemy();

                remainingEnemies--;

                spawnTimer = 0;
            }
        }

        // Semua musuh sudah muncul dan dikalahkan
        if (remainingEnemies == 0 && enemyManager.isEmpty()) {

            waitingForNextWave = true;

            intermissionTimer = 0;

            System.out.println(
                "WAVE " + currentWave + " COMPLETED!"
            );
        }
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public int getRemainingEnemies() {
        return remainingEnemies;
    }

    public boolean isWaitingForNextWave() {
        return waitingForNextWave;
    }
}
