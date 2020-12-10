@echo off
cls
call npx tsc
call node .\dist\index.js
