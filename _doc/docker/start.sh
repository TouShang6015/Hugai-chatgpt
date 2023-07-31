IMAGE_NAME=chatgpt-hugai:v1
CONTAINER_NAME=chatgpt-hugai
MAP_PORT=7100:7100
JAR_FILE_NAME=chatgpt-hugai.jar
DOCKERFILE_DIR=.
HOME=$(cd `dirname $0`;pwd)
ACTIVE_ENV=test


init(){
build
create
}

build(){
docker build --build-arg JAR_FILE=$JAR_FILE_NAME --build-arg ACTIVE_ENV=$ACTIVE_ENV -t $IMAGE_NAME $DOCKERFILE_DIR
}

rmi(){
docker rmi $IMAGE_NAME
}

rebuild(){
stop
rm
rmi
build
}

create(){
docker run -id --name=$CONTAINER_NAME --restart=always -v $HOME/logs:/logs -v /softWare/project/hugai/upload/:/softWare/project/hugai/upload/ -p $MAP_PORT $IMAGE_NAME
}

rm(){
docker rm $CONTAINER_NAME
}

start(){
docker start $CONTAINER_NAME
}

stop(){
docker stop $CONTAINER_NAME
}

log(){
docker logs -f $CONTAINER_NAME
}

restart(){
rebuild
create
}


usage() {
    echo "Usage: sh 执行脚本.sh [init|build|rebuild|rmi|rm|create|start|stop|restart|log]"
    exit 1
}

case "$1" in
        "init")
                init
        ;;
        "rebuild")
                rebuild
                ;;
        "rmi")
                rmi
                ;;
        "build")
                build
                ;;
        "rmi")
                rmi
                ;;
        "rm")
                rm
                ;;
        "create")
                  create
                ;;
        "start")
                start
                ;;
        "restart")
                restart
                ;;
        "stop")
                stop
                ;;
        "log")
                log
                ;;
        *)
                usage
                ;;
esac
