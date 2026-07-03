@echo off
chcp 65001 >nul
echo 正在删除旧的序列化文件...
if exist "data\source.cls" del /f "data\source.cls"
echo 启动程序...
java -Dfile.encoding=utf-8 -cp "bin;." school.jiemian.Run
pause

