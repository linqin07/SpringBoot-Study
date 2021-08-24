process=$1
#$1表示第一个参数
pid=$(ps x | grep $process | grep -v grep | awk '{print $1}')
echo $pid
if [ -n "$pid" ]
then
    echo "kill -9 的pid:" $pid
    kill -9 $pid
fi
sleep 0
#执行jar，并将进程挂起，保存进程ID到 pid文件
echo "Execute shell Finish"
nohup java -jar ./spring-boot-application-config-location.jar >>log.log  & echo "输出的pid：$!" > pid