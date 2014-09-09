@echo off

:: first run Build.xml ->jar

::playgame
set playgame="%~dp0aichallenge_tools\ants\playgame.py"

::map
::set map="%~dp0\aichallenge_tools\ants\maps\maze\maze_02p_02.map"
::set map="%~dp0\src\de\htwg_konstanz\antbots\karten\angreifen\ameise_beim_bodensee.map"
::set map="%~dp0\src\de\htwg_konstanz\antbots\karten\angreifen\ameisen_haben_sich_lieb.map"
::set map="%~dp0\src\de\htwg_konstanz\antbots\karten\angreifen\simpletest.map"
::set map="%~dp0\src\de\htwg_konstanz\antbots\karten\angreifen\attackRadiusTest_2.map"
set map="%~dp0\src\de\htwg_konstanz\antbots\karten\angreifen\wallpunch.map"

::set map="C:\Users\benny\Desktop\Teamprojekt\newWorkspace\AntBotsProject\src\de\htwg_konstanz\antbots\karten\erkunden\exploration_1.map"

::players
set player1="java -jar %~dp0bots\AttackBot.jar"
::set player2="java -jar %~dp0bots\DummyBot.jar"
::set player3="java -jar %~dp0bots\AttackBot.jar"
::set player2="java -jar %~dp0bots\AttackBot.jar"
::set player1="java -jar %~dp0bots\ExplorerBot.jar"

set player2="python %~dp0aichallenge_tools\ants\dist\sample_bots\python\HunterBot.py"
::set player3="python %~dp0aichallenge_tools\ants\dist\sample_bots\python\LeftyBot.py"
::set player4="python %~dp0aichallenge_tools\ants\dist\sample_bots\python\LeftyBot.py"

::visualizer
set visualizer="%~dp0aichallenge_tools/ants/visualizer/build/deploy/visualizer.jar"

:: simulation (in some test maps)
set scenario="--scenario --food none"
::set scenario=""

:: Parameter
echo Map: %map%
echo Player1: %player1%

::start
::python %playgame% --verbose -e -So --scenario --food none --engine_seed 42 --player_seed 42 --end_wait=0 --log_dir %~dp0bots\game_logs --turns 200 --map_file %map% %* %player1% %player2% | java -jar %visualizer%
::python %playgame% --verbose -e -So --player_seed 42 --player_seed 42 --end_wait=0 --log_dir %~dp0bots\game_logs --turns 75 --map_file %map% %* %player1% %player2%
python %playgame% --verbose -e --scenario --food none --player_seed 42 --end_wait=0 --log_dir %~dp0bots\game_logs --turntime 50000 --turns 75 --map_file %map% %* %player1% %player2%


:: Pause?
pause