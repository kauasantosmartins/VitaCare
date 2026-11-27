@echo off
setlocal
cd /d "%~dp0"
if not exist "libs\mssql-jdbc_auth-13.6.0.x64.dll" (
  echo ATENCAO: DLL de autenticacao do SQL Server nao encontrada.
  echo Copie mssql-jdbc_auth-13.6.0.x64.dll para a pasta libs.
  pause
  exit /b 1
)
if not exist "gradlew.bat" (
  echo Preparando Gradle Wrapper 9.2.1 pela primeira vez...
  "%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -ExecutionPolicy Bypass -File "%~dp0configurar-wrapper.ps1"
  if errorlevel 1 (
    echo Falha ao preparar o Gradle Wrapper.
    pause
    exit /b 1
  )
)
call "%~dp0gradlew.bat" run
if errorlevel 1 (
  echo A execucao falhou. Veja o erro acima.
  pause
)
