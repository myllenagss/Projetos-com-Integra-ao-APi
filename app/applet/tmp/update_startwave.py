with open('app/src/main/assets/index.html', 'r', encoding='utf-8') as f:
    text = f.read()

target = """  isBossWave = (waveNum % 4 === 0);

  if (isBossWave) {
    activeBoss = new Boss(waveNum);
    document.getElementById('boss-name').textContent = activeBoss.name;
    document.getElementById('boss-bar-fill').style.width = '100%';
    bossHud.style.display = 'flex';
    banner(`CHEFE: ${activeBoss.name}!`);
  } else {
    activeBoss = null;
    bossHud.style.display = 'none';
    waveTotalEnemies = 12 + waveNum * 4;
    // Guaranteed 10 to 15 coins across the entire normal wave
    waveTargetCoins = 10 + Math.floor(Math.random() * 6);
    waveCoinsDropped = 0;
    banner(`ONDA ${waveNum}`);
  }"""

replacement = """  isBossWave = (waveNum % 4 === 0);

  if (isBossWave) {
    activeBoss = new Boss(waveNum);
    activeBoss.x = V_W / 2;
    activeBoss.y = -70; // Starts from top of screen for dramatic Mega Man fall
    activeBoss.introTargetY = V_H * 0.35;
    activeBoss.inIntro = true;
    bossIntro = {
      active: true,
      timer: 0,
      boss: activeBoss,
      landed: false,
      hpPct: 0,
      sirenPlayed: false,
      siren2Played: false,
      gongPlayed: false
    };
    document.getElementById('boss-name').textContent = activeBoss.name;
    document.getElementById('boss-bar-fill').style.width = '0%';
    bossHud.style.display = 'none';
  } else {
    activeBoss = null;
    bossIntro = null;
    bossHud.style.display = 'none';
    waveTotalEnemies = 12 + waveNum * 4;
    // Guaranteed 10 to 15 coins across the entire normal wave
    waveTargetCoins = 10 + Math.floor(Math.random() * 6);
    waveCoinsDropped = 0;
    banner(`ONDA ${waveNum}`);
  }"""

if target in text:
    text = text.replace(target, replacement)
    with open('app/src/main/assets/index.html', 'w', encoding='utf-8') as f:
        f.write(text)
    print("SUCCESS")
else:
    print("TARGET NOT FOUND")
