@echo off
setlocal
cd /d "%~dp0"

call "%~dp0scripts\bootstrap.bat"
if errorlevel 1 goto :fail

echo [FakeLag] Building mod jar...
call "%GRADLE%" build --console=plain
if errorlevel 1 goto :fail

echo.
echo [FakeLag] Done: build\libs\
dir /b "build\libs\*.jar"
pause
exit /b 0

:fail
echo [FakeLag] Build failed.
pause
exit /b 1
