@echo off

set count=0

:loop
if %count%==10 goto end

set current_time=%time%
echo Current time: %current_time% >>"logs/timesfor10min.txt"

set /A count+=1
timeout /t 60 > nul
goto loop

:end
echo Done logging times. >>"logs/timesfor10min.txt"