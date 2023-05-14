@echo off

REM Get the current time
set CURRENT_TIME=%TIME%

REM Log the current time
echo Current time: %CURRENT_TIME% >> "logs/times.txt"