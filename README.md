GT5-Unofficial
===
## gregtech-gtnh2.6.1-patch1
開発版でのいくつかの変更や修正を適応したGTNH2.6.1用GT5です。\
[Release](https://github.com/Kogepan229/GT5-Unofficial/releases)からgtnh2.6.1-patch1.jarをダウンロードして、既存のgregtech-5.09.45.168.jarを置き換えてください。
また、[TecTech](https://github.com/Kogepan229/TecTech/releases)と[Bartworks](https://github.com/Kogepan229/bartworks/releases)も同様にして置き換える必要があります。

### 変更点
- Stocking Busのクラッシュ修正(GTNewHorizons#2658)
- ランダムにStocking Hatchの液体抽出に失敗するバグの修正(GTNewHorizons#2655)
- Hatch類を自動設置しないようにする`gt_no_hatch`チャンネルの追加(GTNewHorizons#2881)
- Auto-Taping Maintenance Hatchを壁共有可能にする(GTNewHorizons#2735)
- Data Orbを使用してマシンのStocking Bus/Hatch、Crafting Input Slaveの設定をコピーできるように(GTNewHorizons#2965, GTNewHorizons#3115)
- Circuit ImprintでCALを左クリックすることでGUIを開かずにImprintできるように(GTNewHorizons#2965)

## About

GT5-Unofficial is a decompiled and modified version of GT5.07.07. The goal of the mod is to maintain and extend the end
game of GT5. This version has been heavily modified for use with the GTNH modpack.

## Downloads

Builds can be found on the [GTNH Jenkins Server](http://jenkins.usrv.eu:8080/job/Gregtech-5-Unofficial/).

## Installation

GT5U requires IndustrialCraft2-experimental. Version 2:2.2.828-experimental is recommended.
Forge versions 1428-1480 are known to break multiplayer. 1614 is recommended.
Place the downloaded jar file into your mods/ folder. A number of other mods may be required, see dependencies.gradle

## Issues

Please report any issues you to find to the main GTNH issue tracker. Include as much information as possible including
as version and steps to reproduce.

## Contribution

Please do! However, please take a note of
[current issues](https://github.com/GTNewHorizons/GT-New-Horizons-Modpack/issues) and what is currently being worked on.

It is suggested to run `./gradlew build` inside your cloned repository before importing it to your IDE. This will reduce
the chance of strange errors.

## Attribution

Some textures/ideas have been taken from future versions of GT and texture pack authors for GTNH. Credit goes to Jimbno
for the UU-Tex texture pack and its contributions to the base pack here: https://github.com/Jimbno/UU-Tex.

## License

GT5-Unofficial is free software: you can redistribute it and/or modify it under the terms of the
GNU Lesser General Public License as published by the Free Software Foundation, either version 3
of the License, or (at your option) any later version.

GT5-Unofficial is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with GT5-Unofficial.
If not, see <http://www.gnu.org/licenses/>.
