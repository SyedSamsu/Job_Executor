@echo off

REM Set the log file path
set LOG_FILE=logs/system_memory.log

REM Get the system memory
for /f "tokens=1,2" %%a in ('systeminfo ^| findstr "Total Physical Memory"') do set MEMORY=%%b

REM Append the system memory to the log file
echo %DATE% %TIME% Memory: %MEMORY% >> "%LOG_FILE%"