
echo off

set "inkExe=C:\Program Files\Inkscape\inkscape.exe"
set "inFile=icon.svg"
set "outPath=."
set "fileName=ic_launcher.png"
REM set "fileName=ic_launcher_round.png"

md "%outPath%/mipmap-hdpi/"
md "%outPath%/mipmap-mdpi/"
md "%outPath%/mipmap-xhdpi/"
md "%outPath%/mipmap-xxhdpi/"
md "%outPath%/mipmap-xxxhdpi/"

"%inkExe%" -z -f %inFile% -e "%outPath%/mipmap-hdpi/%fileName%" -h 72 -w 72
"%inkExe%" -z -f %inFile% -e "%outPath%/mipmap-mdpi/%fileName%" -h 48 -w 48
"%inkExe%" -z -f %inFile% -e "%outPath%/mipmap-xhdpi/%fileName%" -h 96 -w 96
"%inkExe%" -z -f %inFile% -e "%outPath%/mipmap-xxhdpi/%fileName%" -h 144 -w 144
"%inkExe%" -z -f %inFile% -e "%outPath%/mipmap-xxxhdpi/%fileName%" -h 192 -w 192
