# 设置基础镜像为官方的OpenJDK镜像，版本为17
FROM openjdk:17-ea-jdk-slim
# 设置工作目录
WORKDIR /app
# 复制应用程序的jar包到容器中的/app目录下
COPY forge-admin/target/*.jar /app/admin-app.jar
# 暴漏应用程序的默认端口
EXPOSE 8080
# 设置容器启动是执行的命令，启动应用程序
CMD ["java", "-jar", "/app/admin-app.jar"]

