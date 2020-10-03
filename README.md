# Getting started with Kustomize

Create two namespaces

```bash
$ kubectl create namespace dev
$ kubectl create namespace prod
```

Create the `service`s and `deployment` 

```bash
$ kubectl create service clusterip kustomize-demo-service --tcp=8080 -o yaml 
$ kubectl create deployment kustomize-demo-deployment --image=kustomize-demo:0.0.1-SNAPSHOT -o yaml
```


Create resources:

```bash
$ kubectl apply -f ops/deployment.yaml
$ kubectl apply -f ops/service.yaml
```

Get the resources:

```bash
$ kubectl get all -n dev
```

Access the service using port-forward

```
$ kubectl port-forward deploy/kustomize-demo-deployment 8080:8080 -n dev
$ http :8080/actuator/info
```




