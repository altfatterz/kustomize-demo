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

### ConfigMap and Secret

```bash
$ kubectl create configmap app-config --from-literal=greeter.foo=bar
$ kubectl get configmap app-config -o yaml
```

```yaml
apiVersion: v1
kind: ConfigMap
data:
  app.foo: bar
metadata:
  name: app-config
  namespace: default
```

```
$ kubectl create secret generic db-secret --from-literal=db.username=user --from-literal=db.password=p455w0rd
$ kubectl get secret db-secret -o yaml
```

```yaml
apiVersion: v1
kind: Secret
type: Opaque
data:
  db.password: cDQ1NXcwcmQ=
  db.username: dXNlcg==
metadata:
  name: db-secret
  namespace: default
```

### Customise resource names

```bash
$ kustomize edit set nameprefix 'prod-'
```

### Create patches


### kind

https://kind.sigs.k8s.io/docs/user/quick-start/

```bash
$ brew install kind
```

```bash
$ kind create cluster --config=kind-cluster-config.yaml
```

```bash
$ kind get clusters
$ kind delete cluster --name=<cluster-name>
```
 
Load an image into your cluster

```bash
$ kind load docker-image kustomize-demo:0.0.1-SNAPSHOT --name my-cluster
altfatterz@zoltan-altfatter-zrh:~/projects/demos/kustomize-demo|master⚡ ⇒  kind load docker-image kustomize-demo:0.0.1-SNAPSHOT --name my-cluster
Image: "kustomize-demo:0.0.1-SNAPSHOT" with ID "sha256:adb9bc431439dea21dc31155587da79183bda9f8636d80127d08d0dbdce6d4c7" not yet present on node "my-cluster-worker", loading...
Image: "kustomize-demo:0.0.1-SNAPSHOT" with ID "sha256:adb9bc431439dea21dc31155587da79183bda9f8636d80127d08d0dbdce6d4c7" not yet present on node "my-cluster-control-plane", loading...
Image: "kustomize-demo:0.0.1-SNAPSHOT" with ID "sha256:adb9bc431439dea21dc31155587da79183bda9f8636d80127d08d0dbdce6d4c7" not yet present on node "my-cluster-worker2", loading...
```
 
You can get a list of images present on a cluster node by using docker exec:
 
```bash
$ docker exec -it 067290147e33 crictl images
``` 

Resources:

1. https://spring.io/projects/spring-cloud-kubernetes#learn
2. https://docs.spring.io/spring-cloud-kubernetes/docs/1.1.6.RELEASE/reference/html/#configmap-propertysource
3. https://stackoverflow.com/questions/55052565/retrieve-kubernetes-secrets-mounted-as-volumes
4. https://developers.redhat.com/blog/2017/10/04/configuring-spring-boot-kubernetes-secrets/