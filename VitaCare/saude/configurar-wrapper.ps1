# Configura o Gradle Wrapper oficial sem instalar o Gradle no Windows.
$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
$versao = 'v9.2.1'
$base = "https://raw.githubusercontent.com/gradle/gradle/$versao"
$destino = Join-Path $PSScriptRoot 'gradle\wrapper'
New-Item -ItemType Directory -Force -Path $destino | Out-Null
$arquivos = @(
  @{url="$base/gradlew"; destino='gradlew'},
  @{url="$base/gradlew.bat"; destino='gradlew.bat'},
  @{url="$base/gradle/wrapper/gradle-wrapper.jar"; destino='gradle/wrapper/gradle-wrapper.jar'},
  @{url="$base/gradle/wrapper/gradle-wrapper.properties"; destino='gradle/wrapper/gradle-wrapper.properties'}
)
foreach ($arquivo in $arquivos) {
  Write-Host "Baixando $($arquivo.destino)..."
  $saida = Join-Path $PSScriptRoot $arquivo.destino
  Invoke-WebRequest -Uri $arquivo.url -OutFile $saida
}
# Garante a distribuicao correspondente a versao do Wrapper.
$props = Join-Path $destino 'gradle-wrapper.properties'
$texto = Get-Content -Raw $props
$texto = [regex]::Replace($texto, '(?m)^distributionUrl=.*$', 'distributionUrl=https\://services.gradle.org/distributions/gradle-9.2.1-bin.zip')
Set-Content -Path $props -Value $texto -Encoding ascii
Write-Host 'Wrapper preparado. Testando...' -ForegroundColor Green
& .\gradlew.bat --version
if ($LASTEXITCODE -ne 0) { throw 'O teste do Gradle Wrapper falhou.' }
Write-Host 'Pronto! Use .\gradlew.bat build' -ForegroundColor Green
