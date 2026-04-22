@echo off
setlocal

set "port=4200"

rem Find the process using the specified port
for /f "tokens=5" %%i in ('netstat -ano ^| findstr :%port%') do (
    set "pid=%%i"
    goto :process_found
)

echo No process found with port %port%.
goto :end

:process_found
echo Process found with port %port%. Killing the process...
taskkill /PID %pid% /F
echo Process with ID %pid% has been terminated.

:end
endlocal
