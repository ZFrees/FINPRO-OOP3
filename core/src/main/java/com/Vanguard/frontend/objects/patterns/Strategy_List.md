# Dokumentasi Strategy Patterns — Project Vanguard

Dokumen ini merangkum arsitektur dan cara kerja seluruh kelas serta antarmuka yang menerapkan **Strategy Pattern** dalam pengembangan game *shoot 'em up* (shmup) **Vanguard** (berbasis package `com.Vanguard.frontend.objects.patterns`).

---

## 1. `bulletStrategy` (Pola Pergerakan Peluru)
Package: `com.Vanguard.frontend.objects.patterns.bulletStrategy`

Bertugas mengatur lintasan gerak proyektil (`Bullet`) secara independen setelah peluru ditembakkan.

* **`BulletMovementPattern` (Interface)**
  * **Kontrak:** `void move(Bullet bullet, float delta)`
  * **Peran:** Antarmuka utama yang mendefinisikan standar pembaruan posisi peluru pada setiap *frame* (`delta`).
* **`LinearBulletMovement`**
  * **Cara Kerja:** Menggerakkan peluru lurus ke satu arah tetap berdasarkan sudut (`angle`) dan kecepatan (`speed`) awal menggunakan perhitungan trigonometri ($\cos$ dan $\sin$). Cocok untuk tembakan standar seperti *Vulcan* atau *Laser*.
* **`HomingBulletMovement`**
  * **Cara Kerja:** Peluru pencari target dinamis ala *Touhou 6 (EoSD)*. Memindai musuh aktif terdekat dari daftar entitas (`entities`), melakukan *steering* (berbelok mengejar target) selama durasi `maxHomingDuration`, lalu mengunci arah terakhir dan berakselerasi hingga kecepatan maksimal. Sangat cocok untuk senjata *Plasma* di *Raiden X*.
* **`FantasySealMovement`**
  * **Cara Kerja:** Pola peluru pelacak dua fase. Pada **Fase 1 (*Radial Drift*)**, peluru meluncur lurus mengikuti vektor kecepatan awalnya (`vx`, `vy`) selama `driftDuration`. Pada **Fase 2 (*Homing Lock*)**, peluru mengunci musuh terdekat dan berbelok tajam mengejarnya. Ideal untuk *Homing Missiles*.
* **`StopAndTurnMovement`**
  * **Cara Kerja:** Peluru melambat hingga berhenti sejenak pada waktu `stopTime`, mengubah sudut arahnya (berbelok relatif sebesar `turnDegrees` atau membidik ulang target spesifik `aimTarget`), lalu melesat kembali dengan kecepatan baru (`newSpeed`).
* **`AcceleratingBulletMovement`**
  * **Cara Kerja:** Mengubah kecepatan peluru secara bertahap setiap *frame* berdasarkan nilai percepatan (`acceleration`), dengan batasan antara `minSpeed` dan `maxSpeed`.
* **`SineWaveMovement`**
  * **Cara Kerja:** Peluru bergerak maju mengikuti sudut utamanya sambil berayun ke kiri dan kanan membentuk gelombang sinus menggunakan parameter `amplitude` dan `frequency`.
* **`SpiralMovement`**
  * **Cara Kerja:** Memutar sudut lintasan peluru secara konstan (`angularVelocity`) sambil meluncur keluar (dengan opsi percepatan radial), menghasilkan lintasan melengkung atau pusaran spiral.
* **`BoomerangMovement`**
  * **Cara Kerja:** Peluru meluncur keluar, melambat akibat deselerasi hingga berhenti (`speed <= 0`), lalu berbalik arah ($180^\circ$ atau mengarah ke `returnTarget`) dan berakselerasi kembali.
* **`DelayedSpawnMovement`**
  * **Cara Kerja:** Peluru diam di tempat (`speed = 0`) selama `delayDuration` menyerupai ranjau udara, lalu tiba-tiba melesat dengan kecepatan tinggi (`launchSpeed`) ke arah sudut awal atau membidik `aimTarget`.

---

## 2. `entityStrategy` (Pola Pergerakan Entitas / Musuh)
Package: `com.Vanguard.frontend.objects.patterns.entityStrategy`

Bertugas mengendalikan pergerakan pesawat musuh (*Enemy*), bos, maupun objek entitas lainnya di layar.

* **`EntityMovementPattern` (Interface)**
  * **Kontrak:** `void move(GameObject entity, float delta)`
  * **Peran:** Antarmuka utama bagi seluruh strategi pergerakan entitas.
* **`FixedMovement`**
  * **Cara Kerja:** Mempertahankan posisi entitas agar tetap diam di tempat (*stationary*). Cocok untuk *turret* stasioner atau komponen bos yang menempel.
* **`LinearEntityMovement`**
  * **Cara Kerja:** Menggerakkan entitas secara lurus dengan kecepatan horizontal (`vx`) dan vertikal (`vy`) yang konstan.
* **`TargetPointMovement`**
  * **Cara Kerja:** Menggerakkan entitas lurus menuju koordinat tujuan spesifik `(targetX, targetY)` dengan kecepatan konstan dan berhenti tepat saat tiba (`hasArrived`).
* **`SCurveMovement`**
  * **Cara Kerja:** Mengubah kecepatan sudut (`w`) entitas secara bertahap berdasarkan *timer* untuk membentuk lintasan melengkung menyerupai huruf "S" (mendukung opsi `mirrored` untuk formasi simetris).
* **`StopAndGoMovement`**
  * **Cara Kerja:** Pola serang-dan-kabur tiga fase: entitas masuk ke layar (*move*), berhenti melayang sejenak (*hover/pause*) untuk melepaskan tembakan, lalu berbelok dan berakselerasi keluar layar (*exit*).
* **`DiveAndBankMovement`**
  * **Cara Kerja:** Entitas menukik lurus selama `diveDuration`, kemudian berbelok melengkung secara halus (*banking*) sesuai `bankAngularVelocity` selama `bankDuration`.
* **`SineWaveEntityMovement`**
  * **Cara Kerja:** Entitas melaju ke arah sudut utama (`angle` dalam radian) sambil berayun ke kiri dan kanan secara osilasi membentuk gelombang sinus.
* **`OrbitMovement`**
  * **Cara Kerja:** Menggerakkan entitas memutar mengitari titik pusat statis atau entitas induk (`centerEntity`) pada jarak `radius` tertentu. Sangat pas untuk *drone* pelindung bos.
* **`BezierMovement`**
  * **Cara Kerja:** Menggerakkan entitas mengikuti jalur kurva Bézier kubik yang mulus dari titik awal $P_0$ hingga titik akhir $P_3$ melalui titik kontrol $P_1$ dan $P_2$ dalam durasi waktu tertentu.
* **`PlayerTrackingMovement`**
  * **Cara Kerja:** Entitas secara perlahan memutar sudut hadapnya (dalam satuan radian) untuk mengejar posisi pemain (`target`) secara terus-menerus.

---

## 3. `shootingStrategy` (Pola Tembakan / Formasi Peluru)
Package: `com.Vanguard.frontend.objects.patterns.shootingStrategy`

Bertugas mengatur titik *spawn*, jumlah proyektil, sudut penyebaran, serta menyematkan strategi pergerakan (`BulletMovementPattern`) pada peluru saat ditembakkan.

* **`FrontShot`**
  * **Cara Kerja:** Menembakkan satu proyektil lurus ke depan ($90^\circ$ untuk pemain ke atas, $270^\circ$ untuk musuh ke bawah).
* **`SpreadShot`**
  * **Cara Kerja:** Menembakkan sekumpulan peluru menyebar berbentuk kipas (*fan shot*) selebar `spreadAngleDegrees`. Mendukung pembidikan otomatis ke target (`target`), sudut offset, jarak horizontal antar-peluru, serta pelapisan kecepatan bertahap (*multi-layer*).
* **`RingShot`**
  * **Cara Kerja:** Menembakkan peluru melingkar penuh $360^\circ$ dari titik pusat. Dapat diarahkan ke target tertentu (`aimedAt`) dan mendukung pelapisan cincin ganda dengan variasi kecepatan.
* **`AlternatingRingShot`**
  * **Cara Kerja:** Menembakkan beberapa lapisan cincin peluru secara bersamaan dengan variasi warna sprite bergilir, selisih kecepatan (`speedStep`), serta fase rotasi bertahap.
* **`SideShot`**
  * **Cara Kerja:** Menembakkan peluru dari sisi kiri dan kanan (*flank*) pesawat dengan jarak offset dan sudut keluar tertentu (seperti sistem *options* pada *Touhou* atau *Raiden*).
* **`CompositeShootingPattern`**
  * **Cara Kerja:** Menggabungkan beberapa `ShootingPattern` sekaligus ke dalam satu daftar untuk dieksekusi bersamaan dalam satu kali pemicuan serangan.
* **`SpiralShot`**
  * **Cara Kerja:** Menembakkan peluru secara beruntun sambil menambah sudut tembak (`angleIncrement`) pada setiap tembakan, membentuk pola pusaran api (*spiral barrage*).
* **`LaserBeamShot`**
  * **Cara Kerja:** Menembakkan beberapa proyektil secara rapat dan berkecepatan ultra-tinggi dalam satu garis lurus untuk mensimulasikan tembakan sinar laser pekat (*continuous beam*).
* **`ShotgunScatterShot`**
  * **Cara Kerja:** Menembakkan sejumlah peluru (`pelletCount`) secara acak di dalam kerucut sudut tertentu (`spreadConeDegrees`) dengan variasi kecepatan acak pada tiap peluru.
* **`VFormationShot`**
  * **Cara Kerja:** Menembakkan formasi lima peluru yang tersusun menyerupai panah berbentuk huruf "V" ganda.

---

## 4. `itemStrategy` (Pola Pergerakan Item Drop)
Package: `com.Vanguard.frontend.objects.patterns.itemStrategy`

Bertugas mengatur perilaku item (*power-up*, poin, atau *health pack*) selama melayang di layar sebelum diambil oleh pemain.

* **`ItemMovementPattern` (Interface)**
  * **Kontrak:** `void move(GameObject item, float delta)`
  * **Peran:** Antarmuka utama bagi seluruh strategi pergerakan item.
* **`FloatingBobbingMovement`**
  * **Cara Kerja:** Item turun perlahan ke bawah sambil berosilasi ke kiri dan kanan secara halus, memberikan efek mengapung di udara.
* **`MagneticHomingMovement`**
  * **Cara Kerja:** Item jatuh perlahan seperti biasa, namun begitu pemain masuk ke dalam jarak `triggerRadius`, item akan otomatis berbelok dan melesat cepat mengejar pemain (*auto-collect*).
* **`BounceAndRestMovement`**
  * **Cara Kerja:** Item bergerak dengan kecepatan awal tertentu, memantul balik dengan redaman ketika menyentuh batas layar kiri/kanan (`minX` / `maxX`), dan perlahan berhenti mengapung akibat redaman gravitasi.
* **`SineWaveDriftMovement`**
  * **Cara Kerja:** Item melayang turun sambil bergeser membentuk gelombang sinus lebar ke kiri dan kanan agar mudah dijangkau pemain.