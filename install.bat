@echo off
setlocal
cd /d "%~dp0"
rem Builds the client and installs it into the regular Minecraft launcher
rem (profile Forge 1.8.9 must already be installed).

call "%~dp0scripts\bootstrap.bat"
if errorlevel 1 goto :fail

call "%GRADLE%" build --console=plain
if errorlevel 1 goto :fail

set "MC_MODS=%APPDATA%\.minecraft\mods"
if not exist "%MC_MODS%" mkdir "%MC_MODS%"
del /q "%MC_MODS%\FakeLag-*.jar" 2>nul
copy /y "build\libs\*.jar" "%MC_MODS%\" >nul
if errorlevel 1 goto :fail

echo.
echo [FakeLag] Installed to %MC_MODS%
echo [FakeLag] Start the "forge 1.8.9" profile in your launcher.
pause
exit /b 0

:fail
echo [FakeLag] Install failed.
pause
exit /b 1
