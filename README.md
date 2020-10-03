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

Delete the resources:

```bash
$ kubectl delete -f ops/deployment.yaml
$  kubectl delete -f ops/service.yaml
```


Create the following directory structure:

```bash
├── base
│   ├── deployment.yaml
│   └── service.yaml
└── overlays
    ├── dev
    │    └── kustomization.yaml
    └── prod
        └── kustomization.yaml
```

Where the `dev/kustomization.yaml` content is 

```bash
apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization
resources:
- ../../base/deployment.yaml
- ../../base/service.yaml
namespace: dev
```

In order to see the yaml generated for the run environment we can use:

```bash
$ kustomize build --load_restrictor none k8s/overlays/dev
```
The `--load_restrictor` flag set to `none`, allows that kustomizations may load files from outside their root.

To create the resource in the dev namespace then we can pipe the output of the kustomize command to the `kubectl apply` 
 
```bash
$ kustomize build --load_restrictor none k8s/overlays/dev | kubectl apply -f -
service/kustomize-demo-service created
deployment.apps/kustomize-demo-deployment created
```

To delete the resources:

```bash
$ kustomize build --load_restrictor none k8s/overlays/dev | kubectl delete -f -
```





Resources:

1. https://spring.io/projects/spring-cloud-kubernetes#learn