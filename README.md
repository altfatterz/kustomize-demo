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

```bash
$ kubectl get all -n dev
$ kubectl get all -n prod
```

