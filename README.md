# springboot-k8s-cm

1. Create a new project "k8s"
2. Create a new application "spring-test"
3. Expose a route
4. Create a ConfigMap for the preperty files, application and mysql
5. mount the ConfigMap to the deployment
6. Set the path of the properties. The default "deployments/config" doesn't work, so I need to assign it manually.
   Refer to this: https://developers.redhat.com/blog/2017/10/03/configuring-spring-boot-kubernetes-configmap
   It's not working, so I set the JAVA_ARGS

```
oc new-project k8s 
oc new-app java:openjdk-11-el7~https://github.com/dyangcht/springboot-k8s-cm.git --labels="app.kubernetes.io/part-of=spring-app,app=spring-test,version=v1" --name=spring-test -n k8s
oc expose svc spring-test

cat << EOF | oc apply -f -
apiVersion: v1
kind: ConfigMap
metadata:
  name: demo-cm
data:
  application.properties: |
    env=local
    msg=this is a local env
  mysql.properties: |
    mysql.hostname=10.0.0.1
    mysql.port=3306
EOF

oc set volume --add deploy/spring-test -t configmap --mount-path=/deployments/config \
                         --configmap-name=demo-cm \
                         --name=demo-config
oc set env deploy/spring-test JAVA_ARGS=--spring.config.location=file:/deployments/config/application.properties,/deployments/config/mysql.properties
```
