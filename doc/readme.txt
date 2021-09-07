docker login --username=sunyanfeng1987 registry.cn-qingdao.aliyuncs.com

docker build -t registry.cn-qingdao.aliyuncs.com/fridayplus/halo:0.2.9 .
docker push registry.cn-qingdao.aliyuncs.com/fridayplus/halo:0.2.9