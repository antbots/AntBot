@echo off

:: first run Build.xml ->jar

::playgame
set playgame="%~dp0aichallenge_tools\ants\playgame.py"

::map
set map="%~dp0\aichallenge_tools\ants\maps\maze\maze_04p_02.map"
::set map="%~dp0\src\de\htwg_konstanz\antbots\karten\erkunden\exploration_1.map"

::set map="C:\Users\benny\Desktop\Teamprojekt\newWorkspace\AntBotsProject\src\de\htwg_konstanz\antbots\karten\erkunden\exploration_1.map"

::players
set player1="java -jar %~dp0bots\MyBot.jar"
set player2="python %~dp0aichallenge_tools\ants\dist\sample_bots\python\HunterBot.py"
set player3="python %~dp0aichallenge_tools\ants\dist\sample_bots\python\LeftyBot.py"
set player4="python %~dp0aichallenge_tools\ants\dist\sample_bots\python\LeftyBot.py"

::visualizer
set visualizer="%~dp0aichallenge_tools/ants/visualizer/build/deploy/visualizer.jar"


:: Parameter
echo Map: %map%
echo Player1: %player1%



::start
python %playgame% -e -So --engine_seed 42 --player_seed 42 --end_wait=0.25 --log_dir %~dp0bots\game_logs --turns 1000 --map_file %map% %* %player1% %player2% %player3% %player4% | java -jar %visualizer%

::python %playgame% -e -So --engine_seed 42 --player_seed 42 --viewradius 11 --end_wait=0.25 --log_dir %~dp0bots\game_logs --turns 1000 --map_file %map% %* %player1% %player2% | java -jar %visualizer%



:: Pause?
pause