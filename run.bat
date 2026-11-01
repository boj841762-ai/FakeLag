@echo off
setlocal
title FakeLag Client 1.8.9
cd /d "%~dp0"

call "%~dp0scripts\bootstrap.bat"
if errorlevel 1 goto :fail

echo [FakeLag] Starting Minecraft 1.8.9 + Forge + FakeLag Client...
echo [FakeLag] Extra Forge 1.8.9 mods: put .jar files into the "mods" folder.
echo [FakeLag] First launch downloads Minecraft/Forge and may take a few minutes.
call "%GRADLE%" runClient --console=plain
if errorlevel 1 goto :fail
exit /b 0

:fail
echo.
echo [FakeLag] Launch failed. See the log above.
pause
exit /b 1
