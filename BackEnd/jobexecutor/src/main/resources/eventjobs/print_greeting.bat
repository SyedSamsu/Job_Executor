@echo off

REM Get the current hour
set HOUR=%time:~0,2%

REM Check if it's morning (before noon)
if %HOUR% LSS 12 (
    REM If it's morning, print "Good morning"
    echo Good morning! Syed >>"logs\greeting.txt"
) else (
    REM If it's afternoon or later, print "Good day"
    echo Good day! Syed >>"logs\greeting.txt"
)