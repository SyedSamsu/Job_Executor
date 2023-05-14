@echo off

set FILE_NAME=newfile.txt
set FOLDER_NAME=newfolder

cd logs

echo Creating file %FILE_NAME%...
echo CREATE A FILE AND MOVED TO FOLDER > %FILE_NAME%

echo Creating folder %FOLDER_NAME%...

mkdir %FOLDER_NAME%

echo Moving file %FILE_NAME% to folder %FOLDER_NAME%...
move %FILE_NAME% %FOLDER_NAME%

echo Done.