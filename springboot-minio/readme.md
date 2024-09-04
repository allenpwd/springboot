单机无纠删码模式：只指定一个磁盘目录，bucket下直接就是源文件
```shell
docker run -d \
  -p 9000:9000 \
  -p 9001:9001 \
  -e "MINIO_ROOT_USER=admin" \
  -e "MINIO_ROOT_PASSWORD=admin@123" \
  -v /usr/local/docker/minio/data:/data \
  minio/minio server /data --console-address ":9001"
```
