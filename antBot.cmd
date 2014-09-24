@echo off

:: first run Build.xml ->jar

::playgame
set playgame="%~dp0aichallenge_tools\ants\playgame.py"

::map
::set map="%~dp0\aichallenge_tools\ants\maps\maze\maze_04p_01.map"
set map="%~dp0\aichallenge_tools\ants\maps\maze\maze_p02_02.map"
::set map="%~dp0\src\de\htwg_konstanz\antbots\karten\erkunden\exploration_skull.map"
::set map="%~dp0\src\de\htwg_konstanz\antbots\karten\erkunden\exploration_1.map"
::set map="%~dp0aichallenge_tools/ants/maps/example/tutorial1.map"
::set map="%~dp0\src\de\htwg_konstanz\antbots\karten\angreifen\1vs1w.map"


::set map="%~dp0\aichallenge_tools\ants\maps\cell_maze\cell_maze_p02_11.map"
::set map="%~dp0\aichallenge_tools\ants\maps\cell_maze\cell_maze_p02_15.map"
::set map="%~dp0\aichallenge_tools\ants\maps\cell_maze\cell_maze_p03_01.map"



::players

set player1="java  -Xms1024m  -jar %~dp0bots\AntBot.jar"
::set player2="java -jar %~dp0bots\MyBot.jar"
::set player2="java -jar %~dp0bots\ExplorerBot.jar"
::set player2="python %~dp0aichallenge_tools\ants\dist\bots\MyBot.py"
::set player2="python %~dp0aichallenge_tools\ants\dist\bots\Platz750\MyBot.py3"
::set player2="java -jar %~dp0aichallenge_tools\ants\dist\bots\84\MyBot.jar"

set player2="java -jar %~dp0aichallenge_tools\ants\dist\bots\MyBot.jar"
::set player3="python %~dp0aichallenge_tools\ants\dist\sample_bots\python\HunterBot.py"

::set player2="python %~dp0aichallenge_tools\ants\dist\bots\300\MyBot.py"


::set player3="python %~dp0aichallenge_tools\ants\dist\sample_bots\python\LeftyBot.py"
::set player4="python %~dp0aichallenge_tools\ants\dist\sample_bots\python\GreedyBot.py"

::visualizer
set visualizer="%~dp0aichallenge_tools\ants\visualizer\build\deploy\visualizer.jar"

:: simulation (in some test maps)
set scenario="--scenario --food none"
::set scenario=""

:: Parameter
echo Map: %map%
echo Player1: %player1%

::start
::python %playgame% --verbose -e -So --engine_seed 42 --player_seed 42 --end_wait=0 --log_dir %~dp0bots\game_logs --turns 200 --map_file %map% %* %player1% %player2% | java -jar %visualizer%

python %playgame% --verbose -e --player_seed 42 --turntime 10000 --end_wait=0 --log_dir %~dp0bots\game_logs --turns 1000 --map_file %map% %* %player1% %player2%

::--scenario --food none


:: Pause?
pause
