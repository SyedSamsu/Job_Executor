@echo off

rem Set the news headline and content
set HEADLINE="Syed Samsudeen"
set CONTENT="Story of Syed Samsudeen"

rem Print the news with a custom format
echo "===========" >>"logs\news.txt"
echo "NEWS ALERT:" >>"logs\news.txt"
echo "===========" >>"logs\news.txt"
echo "" >>"logs\news.txt"
echo HEADLINE: %HEADLINE% >>"logs\news.txt"
echo "" >>"logs\news.txt"
echo CONTENT:%CONTENT% >>"logs\news.txt"
echo Completed >>"logs\news.txt"