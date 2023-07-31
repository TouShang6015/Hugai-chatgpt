# ios
from java:8
# jar params
arg JAR_FILE
# project env
arg ACTIVE_ENV
env MY_ENV=${ACTIVE_ENV}
# copy jar
copy ${JAR_FILE} chatgpt-hugai.jar
# -Dspring.profiles.active run env
entrypoint ["java","-Dspring.profiles.active=${MY_ENV}","-Duser.timezone=Asia/Shanghai","-Xms512m","-Xmx1024m","-XX:MetaspaceSize=128m","-XX:MaxMetaspaceSize=512m","-XX:+HeapDumpOnOutOfMemoryError","-jar","/chatgpt-hugai.jar"]
